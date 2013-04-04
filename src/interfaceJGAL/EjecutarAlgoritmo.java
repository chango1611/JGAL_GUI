package interfaceJGAL;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EjecutarAlgoritmo extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public EjecutarAlgoritmo() {
		setSize(630, 435);
		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 255), 2), GAL_GUI.language.casosDeUso[4], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 0, 255)));
		
		JPanel picture = new JPanel();
		picture.setBounds(16, 132, 598, 286);
		picture.setBackground(Color.WHITE);
		
		JPanel help = new JPanel();
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[3]);
				// Create a new frame.
				JFrame helpFrame = new JFrame();
				// Set it's size.
				helpFrame.setSize(800,600);
				// Add the created helpViewer to it.
				helpFrame.getContentPane().add(GAL_GUI.helpViewer);
				// Set a default close operation.
				helpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				//Ponemos en visible
				helpFrame.setVisible(true);
			}
		});
		help.setBounds(598, 7, 28, 28);
		help.setBackground(new Color(0, 0, 255));
		
		JButton btn_Ejecutar = new JButton("");
		btn_Ejecutar.setIcon(new ImageIcon(EjecutarAlgoritmo.class.getResource("/Images/btn_Ejecutar.png")));
		btn_Ejecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret= JOptionPane.showOptionDialog(EjecutarAlgoritmo.this, GAL_GUI.language.Questions[3], GAL_GUI.language.botonesPrincipales[7], 0, JOptionPane.PLAIN_MESSAGE,
						null, new String[]{GAL_GUI.language.Questions[4],GAL_GUI.language.Questions[5]}, GAL_GUI.language.Questions[4]);
				if(ret!=-1){
					if(GAL_GUI.gal.runHandler(ret==0)){
						GeneralWindow.pnl_ProgressResultados.setBackground(new Color(0, 0, 255));
						ResultsViewWindow Newframe= new ResultsViewWindow();
						Newframe.setVisible(true);
					}else{
						GeneralWindow.pnl_ProgressResultados.setBackground(new Color(240, 240, 240));
					}
				}
			}
		});
		btn_Ejecutar.setBounds(48, 28, 76, 50);
		
		JLabel lblEjecutar = new JLabel(GAL_GUI.language.botonesPrincipales[7]);
		lblEjecutar.setBounds(48, 84, 76, 14);
		lblEjecutar.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btn_VerResultados = new JButton("");
		btn_VerResultados.setIcon(new ImageIcon(EjecutarAlgoritmo.class.getResource("/Images/btn_Resultados.png")));
		btn_VerResultados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!GAL_GUI.gal.executed())
					JOptionPane.showMessageDialog(EjecutarAlgoritmo.this, GAL_GUI.language.Errors[21]);
				else{
					ResultsViewWindow Newframe= new ResultsViewWindow();
					Newframe.setVisible(true);
				}
			}
		});
		btn_VerResultados.setBounds(166, 28, 76, 50);
		
		JLabel lblVerResultados = new JLabel(GAL_GUI.language.botonesPrincipales[8]);
		lblVerResultados.setBounds(166, 84, 76, 14);
		lblVerResultados.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btn_Guardar = new JButton("");
		btn_Guardar.setIcon(new ImageIcon(EjecutarAlgoritmo.class.getResource("/Images/btn_Guardar.png")));
		btn_Guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret= JOptionPane.showOptionDialog(EjecutarAlgoritmo.this, GAL_GUI.language.Questions[0], GAL_GUI.language.botonesPrincipales[9], 0, JOptionPane.PLAIN_MESSAGE,
						null, new String[]{GAL_GUI.language.Questions[1],GAL_GUI.language.Questions[2]}, GAL_GUI.language.Questions[1]);
				if(ret==0){
					JFileChooser fc= new JFileChooser();
					int returnVal= fc.showSaveDialog(EjecutarAlgoritmo.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.saveAll(fc.getSelectedFile());
				}if(ret==1){
					try {
						if(!GAL_GUI.gal.executed())
							throw new Exception(GAL_GUI.language.Errors[22]);
						JFileChooser fc= new JFileChooser();
						int returnVal= fc.showSaveDialog(EjecutarAlgoritmo.this);
						if(returnVal == JFileChooser.APPROVE_OPTION)
							GAL_GUI.gal.saveResults(fc.getSelectedFile());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(EjecutarAlgoritmo.this, e1.getMessage());
					}
				}
			}
		});
		btn_Guardar.setBounds(284, 28, 76, 50);
		
		JLabel lblGuardar = new JLabel(GAL_GUI.language.botonesPrincipales[9]);
		lblGuardar.setBounds(284, 84, 76, 14);
		lblGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btn_AbrirConfiguracion = new JButton("");
		btn_AbrirConfiguracion.setIcon(new ImageIcon(EjecutarAlgoritmo.class.getResource("/Images/btn_AbrirConfig.png")));
		btn_AbrirConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					int returnVal= fc.showOpenDialog(EjecutarAlgoritmo.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.openAll(fc.getSelectedFile());
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
				}
				actualizar();
			}
		});
		btn_AbrirConfiguracion.setBounds(402, 28, 76, 50);
		
		JLabel lblAbrirConf = new JLabel(GAL_GUI.language.botonesPrincipales[10]);
		lblAbrirConf.setBounds(402, 84, 76, 14);
		lblAbrirConf.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_help = new JLabel("");
		help.add(lbl_help);
		lbl_help.setBackground(Color.BLACK);
		lbl_help.setIcon(new ImageIcon(EjecutarAlgoritmo.class.getResource("/Images/help.png")));
		setLayout(null);
		
		JLabel label = new JLabel("");
		if(GAL_GUI.language.imageLanguage.equals("image_es"))
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_EjecutarAlgoritmo.png")));
		else
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_EjecutarAlgoritmo_english.png")));
		
		GroupLayout gl_picture = new GroupLayout(picture);
		gl_picture.setHorizontalGroup(
			gl_picture.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_picture.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_picture.setVerticalGroup(
			gl_picture.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_picture.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		picture.setLayout(gl_picture);
		add(picture);
		add(lblEjecutar);
		add(btn_Ejecutar);
		add(lblVerResultados);
		add(btn_VerResultados);
		add(lblGuardar);
		add(btn_Guardar);
		add(lblAbrirConf);
		add(btn_AbrirConfiguracion);
		add(help);
	}
	
	void actualizar(){
		if(GAL_GUI.gal.getGeneNames().length>0)
			GeneralWindow.pnl_ProgressCromosoma.setBackground(new Color(0, 128, 64));
		else
			GeneralWindow.pnl_ProgressCromosoma.setBackground(new Color(240, 240, 240));
		
		if(GAL_GUI.gal.getInterpreter(0).getValid())
			GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(128, 0, 128));
		else
			GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(240, 240, 240));

		
		GAL_GUI.gal.validateInterpreter(1);
		if(GAL_GUI.gal.getInterpreter(1).getValid())
			GeneralWindow.pnl_ProgressFuncion2.setBackground(new Color(128, 0, 128));
		else
			GeneralWindow.pnl_ProgressFuncion2.setBackground(new Color(240, 240, 240));
		
		if(GAL_GUI.gal.getSelector()!=null)
			GeneralWindow.pnl_ProgressConfig1.setBackground(new Color(255, 50, 0));
		else
			GeneralWindow.pnl_ProgressConfig1.setBackground(new Color(240, 240, 240));
		
		if(GAL_GUI.gal.getOperatorsNames().length>0)
			GeneralWindow.pnl_ProgressConfig2.setBackground(new Color(255, 50, 0));
		else
			GeneralWindow.pnl_ProgressConfig2.setBackground(new Color(240, 240, 240));
		
		if(GAL_GUI.gal.parametersAssigned())
			GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(255, 50, 0));
		else
			GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(240, 240, 240));
		
		GeneralWindow.pnl_ProgressResultados.setBackground(new Color(240, 240, 240));
	}
}
