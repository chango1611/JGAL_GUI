package interfaceJGAL;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

/*import JGAL.GAL_CharacterGeneConfig;
import JGAL.GAL_Chromosome;
import JGAL.GAL_ChromosomeConfig;
import JGAL.GAL_DoubleGeneConfig;
import JGAL.GAL_GeneConfig;
import JGAL.GAL_IntegerGeneConfig;
import JGAL.GAL_NominalGeneConfig;*/

public class GAL_GUI {
	
	static GAL_Interface gal;
	static LanguagesReader language;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*GAL_InterpreterParser inter= new GAL_InterpreterParser(new String[]{"hola","adios"});
		GAL_GeneConfig<?>[] aux= new GAL_GeneConfig<?>[2];
		GAL_Chromosome crom= null;
		try{
			aux[0]= new GAL_NominalGeneConfig(new String[] {"hola","adios"});
			aux[1]= new GAL_IntegerGeneConfig(0,10);
			GAL_ChromosomeConfig config= new GAL_ChromosomeConfig(aux);
			crom= config.createNewChromosome();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		System.out.println(crom.getTrait(0).toString() + " " + crom.getTrait(1).toString());
		*/
		try{
			language= new LanguagesReader();
			gal= new GAL_Interface();

			/*System.out.println(inter.parseFitness(
					"$i:=0; $ret:=0; \nWhile $i<$adios Do\n $i:=$i+1; ${3}:=$ret+$i;\n End; $ret; //Comentario", crom));*/
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						WelcomeWindow frame = new WelcomeWindow();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}
}
