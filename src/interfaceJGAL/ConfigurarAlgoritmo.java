package interfaceJGAL;

import javax.swing.JFrame;
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

public class ConfigurarAlgoritmo extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ConfigurarAlgoritmo() {
		setSize(630, 435);
		
		setBorder(new TitledBorder(new LineBorder(new Color(255, 50, 0), 2), GAL_GUI.language.casosDeUso[3], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(255, 50, 0)));
		
		JPanel picture = new JPanel();
		picture.setBounds(16, 132, 598, 286);
		picture.setBackground(Color.WHITE);
		
		JPanel help = new JPanel();
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[2]);
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
		help.setBackground(new Color(255, 50, 0));
		
		JButton btn_selector = new JButton("");
		btn_selector.setIcon(new ImageIcon(ConfigurarAlgoritmo.class.getResource("/Images/btn_Seleccionar.png")));
		btn_selector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectorWindow Newframe= new SelectorWindow();
				Newframe.setVisible(true);
			}
		});
		btn_selector.setBounds(48, 28, 76, 50);
		
		JLabel lblSelector = new JLabel(GAL_GUI.language.botonesPrincipales[4]);
		lblSelector.setBounds(48, 84, 76, 14);
		lblSelector.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btn_Operadores = new JButton("");
		btn_Operadores.setIcon(new ImageIcon(ConfigurarAlgoritmo.class.getResource("/Images/btn_Operacion.png")));
		btn_Operadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OperatorsWindow Newframe= new OperatorsWindow();
				Newframe.setVisible(true);
			}
		});
		btn_Operadores.setBounds(166, 28, 76, 50);
		
		JLabel lblOperadores = new JLabel(GAL_GUI.language.botonesPrincipales[5]);
		lblOperadores.setBounds(166, 84, 76, 14);
		lblOperadores.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btn_Parametros = new JButton("");
		btn_Parametros.setIcon(new ImageIcon(ConfigurarAlgoritmo.class.getResource("/Images/btn_Parametros.png")));
		btn_Parametros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParametersWindow Newframe= new ParametersWindow();
				Newframe.setVisible(true);
			}
		});
		btn_Parametros.setBounds(284, 28, 76, 50);
		
		JLabel lblParmetros = new JLabel(GAL_GUI.language.botonesPrincipales[6]);
		lblParmetros.setBounds(284, 84, 76, 14);
		lblParmetros.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_help = new JLabel("");
		help.add(lbl_help);
		lbl_help.setBackground(Color.BLACK);
		lbl_help.setIcon(new ImageIcon(ConfigurarAlgoritmo.class.getResource("/Images/help.png")));
		setLayout(null);
		
		JLabel label = new JLabel("");
		if(GAL_GUI.language.imageLanguage.equals("image_es"))
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_ConfigurarAlgoritmo.png")));
		else
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_ConfigurarAlgoritmo_english.png")));
		
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
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		picture.setLayout(gl_picture);
		add(picture);
		add(lblSelector);
		add(btn_selector);
		add(lblOperadores);
		add(btn_Operadores);
		add(lblParmetros);
		add(btn_Parametros);
		add(help);
	}
}
