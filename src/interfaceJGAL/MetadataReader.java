package interfaceJGAL;

import java.io.FileReader;
import java.util.Scanner;

//import JGAL.GAL_GeneConfig;

public class MetadataReader {
	
	metadata[] gene_metadatas, selector_metadatas, operator_metadatas, initializer_metadatas;
	
	public MetadataReader() throws Exception{
		Scanner s= new Scanner(new FileReader("metadata.file"));
		String[] params, params_names = null;
		String classname, name;
		int n, p, i, j;
		//Genes
		s.nextLine();
		n= s.nextInt();
		gene_metadatas= new metadata[n];
		for(i=0;i<n;i++){
			s.nextLine();
			classname= s.nextLine();
			name= s.nextLine();
			p= s.nextInt();
			params= new String[p+1];
			params_names= new String[p];
			params[0]= "java.lang.String";
			for(j=1;j<=p;j++){
				params_names[j-1]= s.next();
				params[j]= s.next();
			}
			gene_metadatas[i]= new metadata(classname, name, params, params_names,1);
		}
		s.nextLine();
		//Selectores
		s.nextLine();
		n= s.nextInt();
		selector_metadatas= new metadata[n];
		for(i=0;i<n;i++){
			s.nextLine();
			classname= s.nextLine();
			name= s.nextLine();
			p= s.nextInt();
			params= new String[p];
			params_names= new String[p];
			for(j=0;j<p;j++){
				params_names[j]= s.next();
				params[j]= s.next();
			}
			selector_metadatas[i]= new metadata(classname, name, params, params_names,0);
		}
		s.nextLine();
		//Operadores
		s.nextLine();
		n= s.nextInt();
		operator_metadatas= new metadata[n];
		for(i=0;i<n;i++){
			s.nextLine();
			classname= s.nextLine();
			name= s.nextLine();
			p= s.nextInt();
			params= new String[p+1];
			params_names= new String[p];
			params[0]= "java.lang.Double";
			for(j=0;j<p;j++){
				params_names[j]= s.next();
				params[j+1]= s.next();
			}
			operator_metadatas[i]= new metadata(classname, name, params, params_names,1);
		}
		s.nextLine();
		//Inicializadores
		s.nextLine();
		n= s.nextInt();
		initializer_metadatas= new metadata[n];
		for(i=0;i<n;i++){
			s.nextLine();
			classname= s.nextLine();
			name= s.nextLine();
			p= 0;
			params= new String[p];
			params_names= new String[p];
			initializer_metadatas[i]= new metadata(classname, name, params, params_names,1);
		}
	}
}
