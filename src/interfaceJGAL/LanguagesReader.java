package interfaceJGAL;

import java.io.FileReader;
import java.util.Scanner;

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
	public String language;
	public String imageLanguage;
	
	public LanguagesReader() throws Exception{
		Scanner S= new Scanner(new FileReader("language.ini"));
		language= S.next();
		imageLanguage= "image_es";
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
		progreso= new String[11];
		for(i=0;i<11;i++)
			progreso[i]= S.nextLine();
		
		//Botones Principales
		S.nextLine();
		botonesPrincipales= new String[11];
		for(i=0;i<11;i++)
			botonesPrincipales[i]= S.nextLine();
		
		//Configuracion del Gen
		S.nextLine();
		GeneConfiguration= new String[16];
		for(i=0;i<16;i++)
			GeneConfiguration[i]= S.nextLine();
		
		//Configuracion de funcion aptitud/terminacion
		S.nextLine();
		FitnessAndTerminationConfiguration= new String[10];
		for(i=0;i<10;i++)
			FitnessAndTerminationConfiguration[i]= S.nextLine();

		//Configuracion de selector
		S.nextLine();
		SelectorConfiguration= new String[10];
		for(i=0;i<10;i++)
			SelectorConfiguration[i]= S.nextLine();

		//Configuracion de operadores
		S.nextLine();
		OperatorsConfiguration= new String[22];
		for(i=0;i<22;i++)
			OperatorsConfiguration[i]= S.nextLine();
		
		//Configuracion de parametros
		S.nextLine();
		ParametersConfiguration= new String[6];
		for(i=0;i<6;i++)
			ParametersConfiguration[i]= S.nextLine();

		//Configuracion de guardado
		S.nextLine();
		Questions= new String[6];
		for(i=0;i<6;i++)
			Questions[i]= S.nextLine();

		//Palabras Comunes
		S.nextLine();
		CommonWords= new String[6];
		for(i=0;i<6;i++)
			CommonWords[i]= S.nextLine();

		//Ventana de Resultados
		S.nextLine();
		Results= new String[8];
		for(i=0;i<8;i++)
			Results[i]= S.nextLine();
		
		//Errores
		S.nextLine();
		Errors= new String[22];
		for(i=0;i<22;i++)
			Errors[i]= S.nextLine();
		
	}
}
