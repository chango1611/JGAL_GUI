package interfaceJGAL;

import java.awt.EventQueue;
import java.net.URL;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JOptionPane;


public class GAL_GUI {
	
	static GAL_Interface gal;
	static LanguagesReader language;
	static JHelp helpViewer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		helpViewer = null;
		
		
		try{
			//Leo el lenguaje
			language= new LanguagesReader();
			
			//Abro el help
			ClassLoader cl = GAL_GUI.class.getClassLoader();
			// Use the findHelpSet method of HelpSet to create a URL referencing the helpset file.
			// Note that in this example the location of the helpset is implied as being in the same
			// directory as the program by specifying "jhelpset.hs" without any directory prefix,
			// this should be adjusted to suit the implementation.
			URL url = HelpSet.findHelpSet(cl, language.helpLanguage +"/jhelpset.hs");
			// Create a new JHelp object with a new HelpSet.
			helpViewer = new JHelp(new HelpSet(cl, url));
			// Set the initial entry point in the table of contents.
			helpViewer.setCurrentID("JGAL");
			
			//Inicializo el interface
			gal= new GAL_Interface();

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
