package interfaceJGAL;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import JGAL.GAL_BinaryGeneConfig;
import JGAL.GAL_CharacterGeneConfig;
import JGAL.GAL_Chromosome;
import JGAL.GAL_ChromosomeConfig;
import JGAL.GAL_ChromosomeMutation;
import JGAL.GAL_ClassicCrossover;
import JGAL.GAL_ClassicHandler;
import JGAL.GAL_ClassicMutation;
import JGAL.GAL_ClassicRankingSelector;
import JGAL.GAL_Configuration;
import JGAL.GAL_DefaultInitializer;
import JGAL.GAL_DoubleGeneConfig;
import JGAL.GAL_ElitistSelector;
import JGAL.GAL_GeneConfig;
import JGAL.GAL_GeneticOperator;
import JGAL.GAL_Handler;
import JGAL.GAL_Initializer;
import JGAL.GAL_IntegerGeneConfig;
import JGAL.GAL_Inversion;
import JGAL.GAL_LinealRankingSelector;
import JGAL.GAL_ModHandler;
import JGAL.GAL_MultiPointCrossover;
import JGAL.GAL_NaturalSelector;
import JGAL.GAL_NominalGeneConfig;
import JGAL.GAL_NonLinealRankingSelector;
import JGAL.GAL_OrderedCrossover;
import JGAL.GAL_PermutationsInitializer;
import JGAL.GAL_RandomCrossover;
import JGAL.GAL_RouletteSelector;
import JGAL.GAL_SegmentCrossover;
import JGAL.GAL_ShuffleMutation;
import JGAL.GAL_SwapMutation;
import JGAL.GAL_TournamentSelector;
import JGAL.GAL_UniformCrossover;
import JGAL.NotValidChromosomeException;

public class GAL_Interface {
	
	private LinkedList<String> GeneNames; //Nombre de los genes
	private LinkedList<Integer> GeneType; //Nombre de los genes
	private LinkedList<GAL_GeneConfig<?>> GeneConfig; //La configuracion de los genes
	private GAL_NaturalSelector selector; //El selector a ser usado
	private LinkedList<GAL_GeneticOperator> operators; //Los operadores a ser usado
	private LinkedList<Integer> OperatorType; //La configuracion de los genes
	private GAL_Interpreter fitnessFunctionInterpreter, terminationInterpreter; //Interpretes para la funcion de Aptitud y Terminacion
	private GAL_InterpreterNode fitness, termination;
	private int handlerToUse, initializerToUse, popSize, maxGen, modParam, windowSize, elitistSize, selectorType; //Parametros especificos
	private boolean parameters, optimizationType, elitist; //Booleano que define si los parametros especificos fueron definidos
	private GAL_Handler handler;
	private GAL_Initializer initializer;
	
	GAL_Interface(){
		GeneConfig= new LinkedList<GAL_GeneConfig<?>>();
		GeneNames= new LinkedList<String>();
		GeneType= new LinkedList<Integer>();
		operators= new LinkedList<GAL_GeneticOperator>();
		OperatorType= new LinkedList<Integer>();
		parameters= false;
		elitist= false;
		handlerToUse= 0;
		initializerToUse= 0;
		selectorType= 0;
		popSize= 1;
		maxGen= 1;
		modParam= 1;
		windowSize= 1;
		elitistSize= 1;
		selector= null;
		fitnessFunctionInterpreter= new GAL_Interpreter("",0);
		fitnessFunctionInterpreter.initializeFitness(GeneNames.toArray(new String[0]));
		terminationInterpreter= new GAL_Interpreter("",0);
		terminationInterpreter.initializeTermination(windowSize);
		initializer= null;
		handler= null;
		fitness= null;
		termination= null;
	}
	
	//Agrega una nueva configuracion de gen
	void addGeneConfig(GAL_GeneConfig<?> a, String name, int type)throws Exception{
		GeneConfig.add(a);
		GeneNames.add(name);
		GeneType.add(type);
		if(!isMutableGeneConfig()){
			if(isMutableOperatorPresent()){
				GeneConfig.removeLast();
				GeneNames.removeLast();
				throw new Exception(GAL_GUI.language.Errors[24]);
			}
		}
	}
	
	//Elimina la configuracion de gen en la pos index
	void removeGeneConfig(int index){
		GeneConfig.remove(index);
		GeneNames.remove(index);
		GeneType.remove(index);
	}
	
	//Edita la configuracion de gen en la pos index
	void editGeneConfig(int index,GAL_GeneConfig<?> a, String name, int type)throws Exception{
		GAL_GeneConfig<?> aux= GeneConfig.get(index);
		String aux2= GeneNames.get(index);
		int aux3= GeneType.get(index);
		GeneConfig.set(index, a);
		GeneNames.set(index, name);
		GeneType.set(index, type);
		if(!isMutableGeneConfig()){
			if(isMutableOperatorPresent()){
				GeneConfig.set(index, aux);
				GeneNames.set(index, aux2);
				GeneType.set(index, aux3);
				throw new Exception(GAL_GUI.language.Errors[24]);
			}
		}
	}
	
	//Agarra la pos del gen en index
	GAL_GeneConfig<?> getGeneConfig(int index){
		return GeneConfig.get(index);
	}
	
	//Retorna el tipo del gen en index
	int getGeneType(int index){
		return GeneType.get(index);
	}
	
	//Utilizado para verificar si la configuracion de genes permite mutacion individual 
	boolean isMutableGeneConfig(){
		if(GeneConfig.size()<2) return true;
		
		GAL_GeneConfig<?> anterior= GeneConfig.get(0), actual;
		
		int type= GeneType.get(0);
		
		for(int i=1;i<GeneConfig.size();i++){
			actual= GeneConfig.get(i);
			switch(type){
				case 0:
					if(!(actual instanceof GAL_IntegerGeneConfig)) return false;
					if(!((GAL_IntegerGeneConfig) actual).getMax().equals(((GAL_IntegerGeneConfig) anterior).getMax())) return false;
					if(!((GAL_IntegerGeneConfig) actual).getMin().equals(((GAL_IntegerGeneConfig) anterior).getMin())) return false;
				break;
				case 1:
					if(!(actual instanceof GAL_DoubleGeneConfig)) return false;
					if(!((GAL_DoubleGeneConfig) actual).getMax().equals(((GAL_DoubleGeneConfig) anterior).getMax())) return false;
					if(!((GAL_DoubleGeneConfig) actual).getMin().equals(((GAL_DoubleGeneConfig) anterior).getMin())) return false;
				break;
				case 2:
					if(!(actual instanceof GAL_BinaryGeneConfig)) return false;
				break;
				case 3:
					if(!(actual instanceof GAL_CharacterGeneConfig)) return false;
					if(((GAL_CharacterGeneConfig) actual).getMax()!=((GAL_CharacterGeneConfig) anterior).getMax()) return false;
					if(((GAL_CharacterGeneConfig) actual).getMin()!=((GAL_CharacterGeneConfig) anterior).getMin()) return false;
				break;
				case 4:
					if(!(actual instanceof GAL_NominalGeneConfig)) return false;
					if(!((GAL_NominalGeneConfig) actual).getAlleles().equals(((GAL_NominalGeneConfig) anterior).getAlleles())) return false;
				break;
				default:
					return false;
			}
			anterior= actual;
		}
		return true;
	}
	
	//Agarra todos los nombres de genes
	String[] getGeneNames(){
		return GeneNames.toArray(new String[0]);
	}
	
	//Agarra todos los nombres de los operadores
	String[] getOperatorsNames(){
		String[] ret= new String[operators.size()];
		for(int i=0;i<ret.length;i++){
			ret[i]= getOperatorName(i);
		}
		return ret;
	}
	
	//Agarra el nombre del operador en la pos i
	String getOperatorName(int i){
		if(operators.get(i) instanceof GAL_ClassicCrossover)
			return GAL_GUI.language.OperatorsConfiguration[2];
		if(operators.get(i) instanceof GAL_MultiPointCrossover)
			return GAL_GUI.language.OperatorsConfiguration[3];
		if(operators.get(i) instanceof GAL_UniformCrossover)
			return GAL_GUI.language.OperatorsConfiguration[4];
		if(operators.get(i) instanceof GAL_SegmentCrossover)
			return GAL_GUI.language.OperatorsConfiguration[5];
		if(operators.get(i) instanceof GAL_RandomCrossover)
			return GAL_GUI.language.OperatorsConfiguration[6];
		if(operators.get(i) instanceof GAL_OrderedCrossover)
			return GAL_GUI.language.OperatorsConfiguration[7];
		if(operators.get(i) instanceof GAL_ClassicMutation)
			return GAL_GUI.language.OperatorsConfiguration[8];
		if(operators.get(i) instanceof GAL_ChromosomeMutation)
			return GAL_GUI.language.OperatorsConfiguration[9];
		if(operators.get(i) instanceof GAL_SwapMutation)
			return GAL_GUI.language.OperatorsConfiguration[10];
		if(operators.get(i) instanceof GAL_ShuffleMutation)
			return GAL_GUI.language.OperatorsConfiguration[11];
		if(operators.get(i) instanceof GAL_Inversion)
			return GAL_GUI.language.OperatorsConfiguration[12];
		return GAL_GUI.metadatas.operator_metadatas[OperatorType.get(i)-11].name;
	}
	
	//Retorna el tipo del gen en index
	int getOperatorType(int index){
		return OperatorType.get(index);
	}
	
	//Usado para comprobar la presencia de operadores de mutacion individual
	private boolean isMutableOperatorPresent(){
		for(int i=0;i<operators.size();i++){
			if(operators.get(i) instanceof GAL_OrderedCrossover)
				return true;
			if(operators.get(i) instanceof GAL_SwapMutation)
				return true;
			if(operators.get(i) instanceof GAL_ShuffleMutation)
				return true;
			if(operators.get(i) instanceof GAL_Inversion)
				return true;
		}
		return false;
	}
	
	//Cambia el selector natural
	void setSelector(GAL_NaturalSelector a, int type){
		selector= a;
		selectorType= type;
	}
	
	//Agarra el selector natural
	GAL_NaturalSelector getSelector(){
		return selector;
	}
	
	//Consigue el nombre del selector natural
	String getSelectorName(){
		if(selector instanceof GAL_RouletteSelector)
			return GAL_GUI.language.SelectorConfiguration[1];
		if(selector instanceof GAL_TournamentSelector)
			return GAL_GUI.language.SelectorConfiguration[2];
		if(selector instanceof GAL_ClassicRankingSelector)
			return GAL_GUI.language.SelectorConfiguration[3];
		if(selector instanceof GAL_LinealRankingSelector)
			return GAL_GUI.language.SelectorConfiguration[4];
		if(selector instanceof GAL_NonLinealRankingSelector)
			return GAL_GUI.language.SelectorConfiguration[5];
		return GAL_GUI.metadatas.selector_metadatas[selectorType-5].name;
	}
	
	//Retorna el valor numerico del tipo de selector
	int getSelectorType(){
		return selectorType;
	}
	
	//Cambia entre modo elitista y normal
	boolean changeElitistState(){
		elitist= !elitist;
		return elitist;
	}
	
	boolean isElitist(){
		return elitist;
	}
	
	void setElitistSize(int elitistSize){
		this.elitistSize= elitistSize;
	}
	
	int getElitistSize(){
		return elitistSize;
	}
	
	//Agrega un operador
	void addOperator(GAL_GeneticOperator a, int type){
		operators.add(a);
		OperatorType.add(type);
	}
	
	//Agarra el operaror en index
	GAL_GeneticOperator getOperator(int index){
		return operators.get(index);
	}
	
	//Elimina el operador en index
	void removeOperator(int index){
		operators.remove(index);
	}
	
	//Edita el operador en index
	void editOperator(int index, GAL_GeneticOperator a, int type){
		operators.set(index, a);
		OperatorType.set(index,type);
	}
	
	//Asigna los parametros
	void setParameters(int handlerToUse, int initializerToUse, int popSize, int maxGen, int modParam){
		this.handlerToUse= handlerToUse;
		this.initializerToUse= initializerToUse;
		this.popSize= popSize;
		this.maxGen= maxGen;
		this.modParam= modParam>popSize?popSize:modParam;
		parameters= true;
	}
	
	//Retorna el parametro en base a su nombre
	int getParameter(String name){
		if(name.equals("handlerToUse"))
			return handlerToUse;
		if(name.equals("initializerToUse"))
			return initializerToUse;
		if(name.equals("popSize"))
			return popSize;
		if(name.equals("maxGen"))
			return maxGen;
		if(name.equals("modParam"))
			return modParam;
		return -1;
	}
	
	public void validateInitializer()throws Exception{
		if(initializerToUse==0) return;
		initializer= new GAL_PermutationsInitializer();
		initializer.initialize(new GAL_ChromosomeConfig(GeneConfig.toArray(new GAL_GeneConfig[0])), 2);
		initializer= null;
	}
	
	public void setInitializer(){
		initializerToUse= 0;
	}
	
	public void resetParameters(){
		parameters= false;
	}
	
	//Reorna si ya fueron asignados los parametros
	boolean parametersAssigned(){
		return parameters;
	}
	
	//Obten el Interprete 0 (Aptitud) o 1 (Terminacion)
	GAL_Interpreter getInterpreter(int tipo){
		if(tipo==0 && fitnessFunctionInterpreter!=null){ //Fitness Function
			return fitnessFunctionInterpreter;
		}
		if(tipo==1 && terminationInterpreter!=null){ //Termination
			return terminationInterpreter;
		}
		return null;
	}
	
	//Ingresa el codigo al interprete que se especifique
	void setInterpreter(String code, int tipo){
		if(tipo==0){
			fitnessFunctionInterpreter= new GAL_Interpreter(code);
			fitnessFunctionInterpreter.initializeFitness(GeneNames.toArray(new String[0]));
		}else{
			terminationInterpreter= new GAL_Interpreter(code);
			terminationInterpreter.initializeTermination(windowSize);
		}
	}
	
	//Valida el interprete que se espeifique
	void validateInterpreter(int tipo){
		GAL_ChromosomeConfig aux= null;
		try {
			if(tipo==0){
				aux= new GAL_ChromosomeConfig(GeneConfig.toArray(new GAL_GeneConfig<?>[0]));
				fitness= fitnessFunctionInterpreter.validateFitness(aux);	
			}else{
				aux= new GAL_ChromosomeConfig(new GAL_GeneConfig<?>[]{new GAL_BinaryGeneConfig()});
				termination= terminationInterpreter.validateTermination(aux,10,windowSize);
			}
		} catch (NotValidChromosomeException e) {
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[11]);
		}
	}
	
	//Coloca el tamano de la ventana para la funcion de terminacion
	public void setWindowSize(int size){
		windowSize= size;
	}
	
	//Obten el tamano de la ventana para la funcion de terminacion
	public int getWindowSize(){
		return windowSize;
	}
	
	//Transforma en string la informacion en GeneConfig
	private String toStringGeneConfig(){
		String ret= "";
		ret+= GeneConfig.size() + "\n";
		for(GAL_GeneConfig<?> aux: GeneConfig){
			if(aux instanceof GAL_IntegerGeneConfig)
				ret+= "0 " + aux.getName() + " " + ((GAL_IntegerGeneConfig) aux).getMin() + " " + ((GAL_IntegerGeneConfig) aux).getMax() + "\n";
			else if(aux instanceof GAL_DoubleGeneConfig)
				ret+= "1 " + aux.getName() + " " + ((GAL_DoubleGeneConfig) aux).getMin() + " " + ((GAL_DoubleGeneConfig) aux).getMax() + "\n";
			else if(aux instanceof GAL_BinaryGeneConfig)
				ret+= "2 " + aux.getName() + "\n";
			else if(aux instanceof GAL_CharacterGeneConfig)
				ret+= "3 " + aux.getName() + " " + ((GAL_CharacterGeneConfig) aux).getMin() + " " + ((GAL_CharacterGeneConfig) aux).getMax() + "\n";
			else if(aux instanceof GAL_NominalGeneConfig){
				ret+= "4 " + aux.getName();
				ret+= " " + ((GAL_NominalGeneConfig) aux).numberOfAlleles();
				for(String st: ((GAL_NominalGeneConfig) aux).getAlleles())
					ret+= " " + st;
				ret+="\n";
			}
		}
		return ret;
	}
	
	//Salva en un archivo el GeneConfig
	void saveGeneConfig(File file){
		try {
			FileWriter fw= new FileWriter(file);
			fw.write(toStringGeneConfig());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lee un GeneConfig a partir de un String
	private void readGeneConfig(Scanner fr) throws Exception{
		GeneConfig.clear();
		GeneNames.clear();
		GeneType.clear();
		int t= fr.nextInt(), i, j, type;
		for(i=0;i<t;i++){
			type = fr.nextInt();
			switch(type){
				case 0:
					GeneConfig.add(new GAL_IntegerGeneConfig(fr.next(),fr.nextInt(),fr.nextInt()));
				break;
				case 1:
					GeneConfig.add(new GAL_DoubleGeneConfig(fr.next(),Double.parseDouble(fr.next()),Double.parseDouble(fr.next())));
				break;
				case 2:
					GeneConfig.add(new GAL_BinaryGeneConfig(fr.next()));
				break;
				case 3:
					GeneConfig.add(new GAL_CharacterGeneConfig(fr.next(),fr.next().charAt(0),fr.next().charAt(0)));
				break;
				case 4:
					String name= fr.next();
					String[] alelos= new String[fr.nextInt()];
					for(j=0;j<alelos.length;j++){
						alelos[j]= fr.next();
					}
					GeneConfig.add(new GAL_NominalGeneConfig(name,alelos));
				break;
			}
			GeneNames.add(GeneConfig.getLast().getName());
			GeneType.add(type);
		}
	}
	
	//Abre un archivo para leer el GeneConfig
	void openGeneConfig(File file)throws Exception{
		Scanner fr= new Scanner(new FileReader(file));
		readGeneConfig(fr);
	}
	
	//Convierte en String los parametros
	private String toStringParameters(){
		return handlerToUse + " " + initializerToUse +" " + popSize + " " + maxGen + " " + modParam + "\n";
	}
	
	//Guarda los parametros en un archivo
	void saveParameters(File file){
		try {
			FileWriter fw= new FileWriter(file);
			fw.write(toStringParameters());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lee de un string los parametros
	private void readParameters(Scanner fr)throws Exception{
		handlerToUse= fr.nextInt();
		initializerToUse= fr.nextInt();
		popSize= fr.nextInt();
		maxGen= fr.nextInt();
		modParam= fr.nextInt();
		parameters= true;
	}
	
	//Abre un archivo con los parametros
	void openParameters(File file)throws Exception{
		Scanner fr= new Scanner(new FileReader(file));
		readParameters(fr);
	}
	
	//Convierte en string el selector
	private String toStringSelector(){
		if(selector == null)
			return "-1 0 0";
		if(selector instanceof GAL_RouletteSelector)
			return "0 " + (elitist?1:0) + " " + elitistSize + "\n";
		if(selector instanceof GAL_TournamentSelector)
			return "1 "+ ((GAL_TournamentSelector) selector).getTournamentSize() +" "+ (elitist?1:0) + " " + elitistSize + "\n";
		if(selector instanceof GAL_ClassicRankingSelector)
			return "2 "+ ((GAL_ClassicRankingSelector) selector).getMax() +" "+ (elitist?1:0) + " " + elitistSize + "\n";
		if(selector instanceof GAL_LinealRankingSelector)
			return "3 "+ ((GAL_LinealRankingSelector) selector).getQ() +" "+ (elitist?1:0) + " " + elitistSize + "\n";
		if(selector instanceof GAL_NonLinealRankingSelector)
			return "4 "+ ((GAL_NonLinealRankingSelector) selector).getQ() +" "+ (elitist?1:0) + " " + elitistSize + "\n";
		return "";
	}
	
	//Guarda en un archivo el selector
	void saveSelector(File file){
		try {
			FileWriter fw= new FileWriter(file);
			fw.write(toStringSelector());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lee de un string al selector
	private void readSelector(Scanner fr)throws Exception{
		switch(fr.nextInt()){
			case 0:
				selector= new GAL_RouletteSelector();
			break;
			case 1:
				selector= new GAL_TournamentSelector(fr.nextInt());
			break;
			case 2:
				selector= new GAL_ClassicRankingSelector(fr.nextInt());
			break;
			case 3:
				selector= new GAL_LinealRankingSelector(Double.parseDouble(fr.next()));
			break;
			case 4:
				selector= new GAL_NonLinealRankingSelector(Double.parseDouble(fr.next()));
			break;
			default:
				selector= null;
			break;
		}
		elitist= fr.nextInt()==1;
		elitistSize= fr.nextInt();
	}
	
	//Abre un archivo que contiene al selector
	void openSelector(File file)throws Exception{
		Scanner fr= new Scanner(new FileReader(file));
		readSelector(fr);
	}
	
	//Convierte en string los operadores
	private String toStringOperators(){
		String ret= "";
		ret+= operators.size() + "\n";
		for(GAL_GeneticOperator aux: operators){
			if(aux instanceof GAL_ClassicCrossover)
				ret+= "0 " + aux.getProb() + "\n";
			else if(aux instanceof GAL_MultiPointCrossover)
				ret+= "1 " + aux.getProb() + " " + ((GAL_MultiPointCrossover) aux).getNumberOfPoints() + "\n";
			else if(aux instanceof GAL_UniformCrossover)
				ret+= "2 " + aux.getProb() + " " + ((GAL_UniformCrossover) aux).getUniformProb() + "\n";
			else if(aux instanceof GAL_SegmentCrossover)
				ret+= "3 " + aux.getProb() + " " + ((GAL_SegmentCrossover) aux).getSegmetChangeProb() + "\n";
			else if(aux instanceof GAL_RandomCrossover)
				ret+= "4 " + aux.getProb() + "\n";
			else if(aux instanceof GAL_OrderedCrossover)
				ret+= "5 " + aux.getProb() + "\n";
			else if(aux instanceof GAL_ClassicMutation)
				ret+= "6 " + aux.getProb() + "\n";
			else if(aux instanceof GAL_ChromosomeMutation)
				ret+= "7 " + aux.getProb() + " " + ((GAL_ChromosomeMutation) aux).getSecondProb() +"\n";
			else if(aux instanceof GAL_SwapMutation)
				ret+= "8 " + aux.getProb() + "\n";
			else if(aux instanceof GAL_ShuffleMutation)
				ret+= "9 " + aux.getProb() + "\n";
			else if(aux instanceof GAL_Inversion)
				ret+= "10 " + aux.getProb() + "\n";
		}
		return ret;
	}
	
	//Guarda los operadores en un archivo
	void saveOperators(File file){
		try {
			FileWriter fw= new FileWriter(file);
			fw.write(toStringOperators());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lee los operadores de un string
	private void readOperators(Scanner fr) throws Exception{
		int t= fr.nextInt(), i;
		operators.clear();
		for(i=0;i<t;i++){
			switch(fr.nextInt()){
				case 0:
					operators.add(new GAL_ClassicCrossover(Double.parseDouble(fr.next())));
				break;
				case 1:
					operators.add(new GAL_MultiPointCrossover(Double.parseDouble(fr.next()),fr.nextInt()));
				break;
				case 2:
					operators.add(new GAL_UniformCrossover(Double.parseDouble(fr.next()),Double.parseDouble(fr.next())));
				break;
				case 3:
					operators.add(new GAL_SegmentCrossover(Double.parseDouble(fr.next()),Double.parseDouble(fr.next())));
				break;
				case 4:
					operators.add(new GAL_RandomCrossover(0.0,new GAL_ClassicCrossover(Double.parseDouble(fr.next()))));
				break;
				case 5:
					operators.add(new GAL_OrderedCrossover(Double.parseDouble(fr.next())));
				break;
				case 6:
					operators.add(new GAL_ClassicMutation(Double.parseDouble(fr.next())));
				break;
				case 7:
					operators.add(new GAL_ChromosomeMutation(Double.parseDouble(fr.next()),Double.parseDouble(fr.next())));
				break;
				case 8:
					operators.add(new GAL_SwapMutation(Double.parseDouble(fr.next())));
				break;
				case 9:
					operators.add(new GAL_ShuffleMutation(Double.parseDouble(fr.next())));
				break;
				case 10:
					operators.add(new GAL_Inversion(Double.parseDouble(fr.next())));
				break;
			}
		}
	}
	
	//Abre un archivo con los operadores
	void openOperators(File file)throws Exception{
		Scanner fr= new Scanner(new FileReader(file));
		readOperators(fr);
	}
	
	//Convierte en String el interprete seleccionado
	private String toStringInterpreter(int tipo){
		if(tipo==0 && fitnessFunctionInterpreter.getLines()>0) //Fitness Function
			return fitnessFunctionInterpreter.getLines() + "\n" + fitnessFunctionInterpreter.getCode()+"\n";
		if(tipo==1 && terminationInterpreter.getLines()>0) //Termination
				return terminationInterpreter.getLines() + "\n" + terminationInterpreter.getCode() + "\n" + windowSize+"\n";
		if(tipo==1)
			return "0\n" + windowSize + "\n";
		return "0\n";
	}
	
	//Guarda en un archivo el interprete seleccionado
	void saveInterpreter(File file, int tipo){
		try {
			FileWriter fw= new FileWriter(file);
			fw.write(toStringInterpreter(tipo));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Lee el interprete seleccionado desde un string
	private void readInterpreter(Scanner fr, int tipo)throws Exception{
		int t= fr.nextInt(),i;
		String ret= "";
		fr.nextLine();
		if(t!=0){
			for(i=0;i<t-1;i++)
				ret+= fr.nextLine() + "\n";
			ret+= fr.nextLine();
		}
		if(tipo==0){
			fitnessFunctionInterpreter= new GAL_Interpreter(ret, t);
			fitnessFunctionInterpreter.initializeFitness(GeneNames.toArray(new String[0]));
		}else{
			windowSize= fr.nextInt();
			terminationInterpreter= new GAL_Interpreter(ret, t);
			terminationInterpreter.initializeTermination(windowSize);
		}
		validateInterpreter(tipo);
	}
	
	//Abre un archivo que contiene el interprete seleccionado
	void openInterpreter(File file, int tipo)throws Exception{
		Scanner fr= new Scanner(new FileReader(file));
		readInterpreter(fr, tipo);
	}
	
	//Guarda toda la configuracion en un archivo
	void saveAll(File file){
		try {
			FileWriter fw= new FileWriter(file);
			fw.write(toStringGeneConfig());
			fw.write(toStringInterpreter(0));
			fw.write(toStringInterpreter(1));
			fw.write(toStringSelector());
			fw.write(toStringOperators());
			fw.write(toStringParameters());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void saveResults(File file)throws Exception{
		FileWriter fw= new FileWriter(file);
		fw.write(GAL_GUI.language.Results[0]+":\n");
		GAL_Chromosome best= handler.getBestFromAll();
		if(optimizationType)
			fw.write(GAL_GUI.language.Results[7]+": " + best.getFitness()+"\n");
		else
			fw.write(GAL_GUI.language.Results[7]+": " + (1000000000 - best.getFitness()) +"\n");
		fw.write(GAL_GUI.language.FitnessAndTerminationConfiguration[7]+":\n");
		int i;
		for(i=0;i<GeneNames.size();i++)
			fw.write(GeneNames.get(i) + ": " + best.getTrait(i)+"\n");
		fw.close();
	}
	
	//Abre toda la configuracion desde un archivo
	void openAll(File file)throws Exception{
		Scanner fr= new Scanner(new FileReader(file));
		readGeneConfig(fr);
		readInterpreter(fr, 0);
		readInterpreter(fr, 1);
		readSelector(fr);
		readOperators(fr);
		readParameters(fr);
	}
	
	//Retorna si la ejecución fue exitosa o no
	boolean runHandler(boolean optimizationType){
		try{
			this.optimizationType= optimizationType;
			handler= null;
			if(GeneNames.size()==0){
				JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[14] + "\n" + GAL_GUI.language.Errors[15]);
				return false;
			}
			if(!fitnessFunctionInterpreter.getValid()){
				JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[14] + "\n" + GAL_GUI.language.Errors[16]);
				return false;
			}
			if(selector==null){
				JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[14] + "\n" + GAL_GUI.language.Errors[18]);
				return false;
			}
			if(operators.size()==0){
				JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[14] + "\n" + GAL_GUI.language.Errors[19]);
				return false;
			}
			if(!parameters){
				JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[14] + "\n" + GAL_GUI.language.Errors[20]);
				return false;
			}
			
			if(initializerToUse==0)
				initializer= new GAL_DefaultInitializer();
			else if(initializerToUse==1)
				initializer= new GAL_PermutationsInitializer();
			else
				initializer= GAL_GUI.metadatas.initializer_metadatas[initializerToUse-2].initializerConstructor();
		
			//Creo la configuracion
			GAL_Configuration config;
			config= new GAL_Configuration
					(new GAL_ChromosomeConfig(GeneConfig.toArray(new GAL_GeneConfig[0])),
					initializer,
					terminationInterpreter.getValid()?new TerminationCondition(termination, windowSize):new TerminationCondition(windowSize),
					new FitnessFunction(fitness,optimizationType),
					elitist?new GAL_ElitistSelector(elitistSize, selector):selector,
					operators.toArray(new GAL_GeneticOperator[0]));
			
			//Creo el handler y le paso por parametro la configuracion
			if(handlerToUse==0)
				handler= new GAL_ClassicHandler(config, maxGen, popSize, windowSize);
			else
				handler= new GAL_ModHandler(config, maxGen, popSize, windowSize,modParam);
			
			//Corro el handler
			handler.runGAL();
			
			return true;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, e.getMessage());
			handler= null;
			return false;
		}
	}
	
	//Retorna el mejor Cromosoma
	public GAL_Chromosome getBestChromosome(){
		return handler.getBestFromAll();
	}
	
	//Retorna el mejor cromosoma de la generacion index
	public GAL_Chromosome getBestChromosomeFrom(int index){
		return handler.getBestFrom(index);
	}
	
	//Retorna la aptitud promedio total
	public double getAverageFitness(){
		if(optimizationType)
			return handler.getAverageFitnessFromAll();
		return 1000000000 - handler.getAverageFitnessFromAll();
	}
	
	//Retorna la aptitud promedio de la generacion index
	public double getAverageFitness(int index){
		if(optimizationType)
			return handler.getAverageFitnessFrom(index);
		return 1000000000 - handler.getAverageFitnessFrom(index);
	}
	
	//Retorna la ultima generacion que se ejecuto
	public int getLastGeneration(){
		return handler.getLastGenerationNumber();
	}
	
	//Obtiene un arreglo de doubles con el mejor fitness de cada generación
	public double[] getArrayOfBestFitness(){
		int size= handler.getLastGenerationNumber();
		double[] ret= new double[size];
		for(int i=0;i<size;i++){
			if(optimizationType)
				ret[i]= handler.getBestFrom(i).getFitness();
			else
				ret[i]= 1000000000 - handler.getBestFrom(i).getFitness();
		}
		return ret;
	}

	//Obtiene un arreglo de doubles con el fitness promedio de cada generación
	public double[] getArrayOfAverageFitness(){
		int size= handler.getLastGenerationNumber();
		double[] ret= new double[size];
		for(int i=0;i<size;i++){
			if(optimizationType)
				ret[i]= handler.getAverageFitnessFrom(i);
			else
				ret[i]= 1000000000 - handler.getAverageFitnessFrom(i);
		}
		return ret;
	}
	
	public boolean optimizationType(){
		return optimizationType;
	}
	
	public boolean executed(){
		return handler!=null;
	}
	
	public void limpiarTodo(){
		GeneConfig.clear();
		GeneNames.clear();
		GeneType.clear();
		operators.clear();
		OperatorType.clear();
		parameters= false;
		elitist= false;
		handlerToUse= 0;
		initializerToUse= 0;
		selectorType= 0;
		popSize= 1;
		maxGen= 1;
		modParam= 1;
		windowSize= 1;
		elitistSize= 1;
		selector= null;
		fitnessFunctionInterpreter= new GAL_Interpreter("",0);
		fitnessFunctionInterpreter.initializeFitness(GeneNames.toArray(new String[0]));
		terminationInterpreter= new GAL_Interpreter("",0);
		terminationInterpreter.initializeTermination(windowSize);
		initializer= null;
		handler= null;
		fitness= null;
		termination= null;
	}
	
	public void limpiarGenes(){
		GeneConfig.clear();
		GeneNames.clear();
		GeneType.clear();
	}
	
	public void limpiarOperadores(){
		operators.clear();
		OperatorType.clear();
	}
}
