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

public class EsquemaDeTrabajo extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public EsquemaDeTrabajo() {
		setSize(630, 435);
		
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), GAL_GUI.language.casosDeUso[0], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), Color.BLACK));
		setLayout(null);
		
		JPanel picture = new JPanel();
		picture.setBounds(16, 28, 589, 390);
		picture.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("");
		if(GAL_GUI.language.imageLanguage.equals("image_es"))
			label.setIcon(new ImageIcon(EsquemaDeTrabajo.class.getResource("/Images/mapa_ppal.png")));
		else
			label.setIcon(new ImageIcon(EsquemaDeTrabajo.class.getResource("/Images/mapa_ppal_english.png")));
		label.setBackground(new Color(255,255,255));
		GroupLayout gl_picture = new GroupLayout(picture);
		gl_picture.setHorizontalGroup(
			gl_picture.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_picture.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 578, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_picture.setVerticalGroup(
			gl_picture.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_picture.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
					.addContainerGap())
		);
		picture.setLayout(gl_picture);
		add(picture);
	}
}
