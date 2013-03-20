package interfaceJGAL;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultListModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class FunctionViewWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FunctionViewWindow() {
		setTitle(GAL_GUI.language.progreso[2]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 307);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu(GAL_GUI.language.CommonWords[2]);
		mnArchivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmSalir = new JMenuItem(GAL_GUI.language.CommonWords[5]);
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mntmSalir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		final DefaultListModel<String> lm_OperadoresDefinidos= new DefaultListModel<String>();
		for(String name: GAL_GUI.gal.getOperatorsNames())
			lm_OperadoresDefinidos.addElement(name);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 434, 255);
		contentPane.add(tabbedPane);
		
		JPanel Aptitud = new JPanel();
		tabbedPane.addTab(GAL_GUI.language.progreso[3], null, Aptitud, null);
		Aptitud.setLayout(null);
		
		JLabel label_12 = new JLabel(GAL_GUI.language.progreso[2]);
		label_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_12.setBounds(12, 13, 241, 14);
		Aptitud.add(label_12);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 32, 407, 157);
		Aptitud.add(scrollPane);
		
		final JTextArea txt_FuncionAptitud = new JTextArea();
		txt_FuncionAptitud.setTabSize(2);
		txt_FuncionAptitud.setEditable(false);
		scrollPane.setViewportView(txt_FuncionAptitud);
		
		final JPanel pnl_Validez1 = new JPanel();
		pnl_Validez1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnl_Validez1.setBounds(399, 198, 20, 20);
		Aptitud.add(pnl_Validez1);
		
		JLabel lbl_Validez1 = new JLabel(GAL_GUI.language.FitnessAndTerminationConfiguration[1]);
		lbl_Validez1.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Validez1.setBounds(263, 200, 126, 14);
		Aptitud.add(lbl_Validez1);
		
		JPanel Terminacion = new JPanel();
		Terminacion.setLayout(null);
		tabbedPane.addTab(GAL_GUI.language.progreso[4], null, Terminacion, null);
		
		JLabel label = new JLabel(GAL_GUI.language.progreso[2]);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(12, 13, 241, 14);
		Terminacion.add(label);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 32, 407, 157);
		Terminacion.add(scrollPane_1);
		
		final JTextArea txt_FuncionTerminacion = new JTextArea();
		txt_FuncionTerminacion.setTabSize(2);
		txt_FuncionTerminacion.setEditable(false);
		scrollPane_1.setViewportView(txt_FuncionTerminacion);
		
		final JLabel lbl_windowSize = new JLabel("");
		lbl_windowSize.setBounds(12, 200, 241, 14);
		Terminacion.add(lbl_windowSize);
		
		final JPanel pnl_Validez2 = new JPanel();
		pnl_Validez2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		pnl_Validez2.setBounds(399, 198, 20, 20);
		Terminacion.add(pnl_Validez2);
		
		JLabel lbl_Validez = new JLabel(GAL_GUI.language.FitnessAndTerminationConfiguration[1]);
		lbl_Validez.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Validez.setBounds(263, 200, 126, 14);
		Terminacion.add(lbl_Validez);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				txt_FuncionAptitud.setText(GAL_GUI.gal.getInterpreter(0).getCode());
				txt_FuncionTerminacion.setText(GAL_GUI.gal.getInterpreter(1).getCode());
				lbl_windowSize.setText(GAL_GUI.language.FitnessAndTerminationConfiguration[9] + ":    " + GAL_GUI.gal.getWindowSize());
				if(GAL_GUI.gal.getInterpreter(0).getValid())
					pnl_Validez1.setBackground(new Color(0, 255, 0));
				if(GAL_GUI.gal.getInterpreter(1).getValid())
					pnl_Validez2.setBackground(new Color(0, 255, 0));
			}
		});
	}
}
