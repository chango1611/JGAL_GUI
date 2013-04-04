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

public class DefinirFuncion extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public DefinirFuncion() {
		setSize(630, 435);
		
		setBorder(new TitledBorder(new LineBorder(new Color(128, 0, 128), 2), GAL_GUI.language.casosDeUso[2], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(128, 0, 128)));
		
		JPanel picture = new JPanel();
		picture.setBounds(16, 136, 598, 286);
		picture.setBackground(Color.WHITE);
		
		JPanel help = new JPanel();
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[1]);
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
		help.setBackground(new Color(128, 0, 128));
		
		JButton btn_CrearFuncion = new JButton("");
		btn_CrearFuncion.setIcon(new ImageIcon(DefinirFuncion.class.getResource("/Images/btn_DefinirAptitud.png")));
		btn_CrearFuncion.setBounds(48, 28, 76, 50);
		btn_CrearFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FitnessFunctionWindow Newframe= new FitnessFunctionWindow();
				Newframe.setVisible(true);
			}
		});
		
		JLabel lblCrearFuncin = new JLabel(GAL_GUI.language.botonesPrincipales[1]);
		lblCrearFuncin.setBounds(42, 84, 89, 14);
		lblCrearFuncin.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblAptitud = new JLabel(GAL_GUI.language.botonesPrincipales[2]);
		lblAptitud.setBounds(48, 104, 76, 14);
		lblAptitud.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel label_1 = new JLabel(GAL_GUI.language.botonesPrincipales[1]);
		label_1.setBounds(162, 84, 89, 14);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btn_CrearTerminacion = new JButton("");
		btn_CrearTerminacion.setIcon(new ImageIcon(DefinirFuncion.class.getResource("/Images/btn_DefinirTerminacion.png")));
		btn_CrearTerminacion.setBounds(167, 28, 76, 50);
		btn_CrearTerminacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TerminationFunctionWindow Newframe= new TerminationFunctionWindow();
				Newframe.setVisible(true);
			}
		});
		
		JLabel lblTerminacin = new JLabel(GAL_GUI.language.botonesPrincipales[3]);
		lblTerminacin.setBounds(167, 104, 76, 14);
		lblTerminacin.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_help = new JLabel("");
		help.add(lbl_help);
		lbl_help.setBackground(Color.BLACK);
		lbl_help.setIcon(new ImageIcon(DefinirFuncion.class.getResource("/Images/help.png")));
		setLayout(null);
		
		JLabel label = new JLabel("");
		if(GAL_GUI.language.imageLanguage.equals("image_es"))
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_DefinirFuncion.png")));
		else
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_DefinirFuncion_english.png")));
		
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
		add(lblAptitud);
		add(lblCrearFuncin);
		add(btn_CrearFuncion);
		add(lblTerminacin);
		add(label_1);
		add(btn_CrearTerminacion);
		add(help);
	}
}
