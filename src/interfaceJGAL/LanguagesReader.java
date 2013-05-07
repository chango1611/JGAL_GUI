package interfaceJGAL;

import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class LanguagesReader {

	public String[] startPage;
	public String[] casosDeUso;
	public String[] progreso;
	public String[] botonesPrincipales;
	public String[] GeneConfiguration;
	public String[] FitnessAndTerminationConfiguration;
	public String[] SelectorConfiguration;
	public String[] OperatorsConfiguration;
	public String[] ParametersConfiguration;
	public String[] Questions;
	public String[] CommonWords;
	public String[] Results;
	public String[] Errors;
	public String[] helpTargets;
	public String language;
	public String helpLanguage;
	public String imageLanguage;
	
	public LanguagesReader() throws Exception{
		Scanner S= new Scanner(new FileReader("language.ini"));
		LinkedList<String> Languages= new LinkedList<String>();
		LinkedList<String> FileName= new LinkedList<String>();
		LinkedList<String> helpOption= new LinkedList<String>();
		LinkedList<String> ImageOption= new LinkedList<String>();
		while (S.hasNext()){
			Languages.add(S.next());
			FileName.add(S.next());
			helpOption.add(S.next());
			ImageOption.add(S.next());
		}
		String aux= (String)JOptionPane.showInputDialog(null, "Select Language:", "Language Selection", JOptionPane.PLAIN_MESSAGE, null, Languages.toArray(new String[0]), Languages.get(0));
		int pos= Languages.indexOf(aux);
		if(pos==-1)
			throw new Exception("Language not selected, the program now will close");
		language= FileName.get(pos);
		helpLanguage= helpOption.get(pos);
		imageLanguage= ImageOption.get(pos);
		if(S.hasNext())
			imageLanguage= S.next();
		S= new Scanner(new FileReader(language + ".language"));
		int i;

		//Pagina de Inicio
		S.nextLine();
		startPage= new String[3];
		for(i=0;i<3;i++)
			startPage[i]= S.nextLine();
		
		//Casos de Uso
		S.nextLine();
		casosDeUso= new String[6];
		for(i=0;i<6;i++)
			casosDeUso[i]= S.nextLine();
		
		//Progreso
		S.nextLine();
		progreso= new String[12];
		for(i=0;i<12;i++)
			progreso[i]= S.nextLine();
		
		//Botones Principales
		S.nextLine();
		botonesPrincipales= new String[11];
		for(i=0;i<11;i++)
			botonesPrincipales[i]= S.nextLine();
		
		//Configuracion del Gen
		S.nextLine();
		GeneConfiguration= new String[18];
		for(i=0;i<18;i++)
			GeneConfiguration[i]= S.nextLine();
		
		//Configuracion de funcion aptitud/terminacion
		S.nextLine();
		FitnessAndTerminationConfiguration= new String[11];
		for(i=0;i<11;i++)
			FitnessAndTerminationConfiguration[i]= S.nextLine();

		//Configuracion de selector
		S.nextLine();
		SelectorConfiguration= new String[12];
		for(i=0;i<12;i++)
			SelectorConfiguration[i]= S.nextLine();

		//Configuracion de operadores
		S.nextLine();
		OperatorsConfiguration= new String[23];
		for(i=0;i<23;i++)
			OperatorsConfiguration[i]= S.nextLine();
		
		//Configuracion de parametros
		S.nextLine();
		ParametersConfiguration= new String[8];
		for(i=0;i<8;i++)
			ParametersConfiguration[i]= S.nextLine();

		//Configuracion de guardado
		S.nextLine();
		Questions= new String[7];
		for(i=0;i<7;i++)
			Questions[i]= S.nextLine();

		//Palabras Comunes
		S.nextLine();
		CommonWords= new String[9];
		for(i=0;i<9;i++)
			CommonWords[i]= S.nextLine();

		//Ventana de Resultados
		S.nextLine();
		Results= new String[8];
		for(i=0;i<8;i++)
			Results[i]= S.nextLine();
		
		//Errores
		S.nextLine();
		Errors= new String[32];
		for(i=0;i<32;i++)
			Errors[i]= S.nextLine();
		
		//Errores
		S.nextLine();
		helpTargets= new String[12];
		for(i=0;i<12;i++)
			helpTargets[i]= S.nextLine();
		
	}
}
