package interfaceJGAL;

import javax.swing.JOptionPane;

import org.codehaus.jparsec.error.ParserException;

import JGAL.GAL_ChromosomeConfig;
import JGAL.GAL_Population;
import JGAL.NotValidChromosomeException;
import JGAL.NotValidPopulationException;

public class GAL_Interpreter {
	
	private int lines; //Cant. de lineas del codigo
	private String code; //Codigo
	private boolean valid; //Verificacion de validez del codigo
	private GAL_InterpreterParser parser; //Parser para crear el arbol
	private String[] variablesNames; //Nombre de las variables constantes
	private Object[] variables; //Todas las variables del problema
	
	public GAL_Interpreter(String code){
		this.code= code;
		lines= countLines();
		variablesNames= new String[0];
		variables= new Object[0];
		valid= false;
	}
	
	public GAL_Interpreter(String code, int lines){
		this.code= code;
		this.lines= lines;
		variablesNames= new String[0];
		variables= new Object[0];
		valid= false;
	}
	
	private int countLines(){
	   String[] lines = code.split("\r\n|\r|\n");
	   return  lines.length;
	}
	
	public int getLines(){
		return lines;
	}
	
	public String getCode(){
		return code;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public String[] getvariablesNames(){
		return variablesNames;
	}
	
	public Object getVariable(int index){
		return variables[index];
	}
	
	public Object setVariable(int index, Object toSet){
		variables[index]= toSet;
		return variables[index];
	}
	
	public void initializeFitness(String[] GeneNames){
		variablesNames= GeneNames;
		parser= new GAL_InterpreterParser(GeneNames);
	}
	
	public void initializeTermination(int windowExpectedSize){
		String[] names= new String[windowExpectedSize*2+1];
		names[0]= "popsize";
		for(int i=1;i<names.length;i+=2){
			names[i]= "bestFitness_"+ (i/2);
			names[i+1]= "totalFitness_"+ (i/2);
		}
		variablesNames= names;
		parser= new GAL_InterpreterParser(names);
	}
	
	public GAL_InterpreterNode validateFitness(GAL_ChromosomeConfig chrom){
		//Ejecutar Parser
		try{
			GAL_InterpreterNode ret= parser.parseFitness(code,chrom.createNewChromosome());
			variables= parser.getAllVariables();
			//System.out.println(ret.toString(""));
			//System.out.println("valor final: " + ret.operate());
			if(!(ret.operate() instanceof Double))
				throw new ClassCastException();
			valid= true;
			return ret;
		}catch(ParserException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[13] + "\n"+e.getLocation());
			valid= false;
		}catch(NotValidChromosomeException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[11]);
			valid= false;
		}catch(ClassCastException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[12]);
			valid= false;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[13].substring(0, 19));
			valid= false;
		}
		return null;
	}
	
	public GAL_InterpreterNode validateTermination(GAL_ChromosomeConfig chrom, int popsize, int windowExpectedSize){
		//Crear poblacion artificial
		GAL_Population[] window= new GAL_Population[windowExpectedSize];
		try{
			for(int i=0;i<windowExpectedSize;i++){
				window[i]= new GAL_Population(popsize, chrom); 
			}
		}catch(NotValidChromosomeException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[11]);
			valid= false;
		}catch(NotValidPopulationException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[11]);
			valid= false;
		}
		
		//Ejecutar Parser
		try{
			GAL_InterpreterNode ret= parser.parseTermination(code,window);
			variables= parser.getAllVariables();
			//System.out.println(ret.toString(""));
			//System.out.println("valor final: " + ret.operate());
			if(!(ret.operate() instanceof Boolean))
				throw new ClassCastException();
			valid= true;
			return ret;
		}catch(ParserException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[13] + "\n"+e.getLocation());
			valid= false;
		}catch(ClassCastException e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[12]);
			valid= false;
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[13].substring(0, 19));
			valid= false;
		}
		return null;
	}
}
