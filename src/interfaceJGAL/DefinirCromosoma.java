package interfaceJGAL;

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

public class DefinirCromosoma extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public DefinirCromosoma() {
		setSize(630, 435);
		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 128, 64), 2), GAL_GUI.language.casosDeUso[1], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 128, 64)));
		
		JPanel picture = new JPanel();
		picture.setBounds(20, 132, 594, 286);
		picture.setBackground(Color.WHITE);
		
		JPanel help = new JPanel();
		help.setBounds(598, 7, 28, 28);
		help.setBackground(new Color(0, 128, 64));
		
		JButton btn_DefinirGenes = new JButton("");
		btn_DefinirGenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneConfigurationWindow Newframe= new GeneConfigurationWindow();
				Newframe.setVisible(true);
			}
		});
		btn_DefinirGenes.setBounds(48, 28, 76, 50);
		btn_DefinirGenes.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/btn_DefinirGenes.png")));
		
		JLabel lblDefinirGenes = new JLabel(GAL_GUI.language.botonesPrincipales[0]);
		lblDefinirGenes.setBounds(48, 84, 76, 14);
		lblDefinirGenes.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbl_help = new JLabel("");
		help.add(lbl_help);
		lbl_help.setBackground(Color.BLACK);
		lbl_help.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/help.png")));
		setLayout(null);
		
		JLabel label = new JLabel("");
		if(GAL_GUI.language.imageLanguage.equals("image_es"))
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_DefinirCromsoma.png")));
		else
			label.setIcon(new ImageIcon(DefinirCromosoma.class.getResource("/Images/mapa_DefinirCromsoma_english.png")));
		
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
		add(lblDefinirGenes);
		add(btn_DefinirGenes);
		add(help);
	}
}
