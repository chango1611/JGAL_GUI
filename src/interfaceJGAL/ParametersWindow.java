package interfaceJGAL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SpinnerNumberModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ParametersWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel modHandler_panel;
	private JComboBox<String> cb_Handler;
	private JComboBox<String> cb_initializer;
	private JSpinner spn_Mod;
	private JSpinner spn_TamPob;
	private JSpinner spn_maxGen;
	
	/**
	 * Create the frame.
	 */
	public ParametersWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				actualizar();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				if(GAL_GUI.gal.parametersAssigned())
					GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(255, 50, 0));
			}
		});
		setTitle(GAL_GUI.language.botonesPrincipales[6]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 260, 240);
		
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
		
		JMenuItem mntmAbrir = new JMenuItem(GAL_GUI.language.CommonWords[3]);
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					int returnVal= fc.showOpenDialog(ParametersWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.openParameters(fc.getSelectedFile());
					actualizar();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
				}
			}
		});
		mntmAbrir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmAbrir);
		
		JMenuItem mntmGuardar = new JMenuItem(GAL_GUI.language.CommonWords[4]);
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc= new JFileChooser();
				int returnVal= fc.showSaveDialog(ParametersWindow.this);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					GAL_GUI.gal.setParameters(cb_Handler.getSelectedIndex(), cb_initializer.getSelectedIndex(), (Integer)spn_TamPob.getValue(), (Integer)spn_maxGen.getValue(), (Integer)spn_Mod.getValue());
					GAL_GUI.gal.saveParameters(fc.getSelectedFile());
				}
			}
		});
		mntmGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmGuardar);
		mntmSalir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmSalir);
		
		JMenu mnAyuda = new JMenu(GAL_GUI.language.CommonWords[6]);
		mnAyuda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnAyuda);
		
		JMenuItem mntmTutorial = new JMenuItem(GAL_GUI.language.CommonWords[7]);
		mntmTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[11]);
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
		mntmTutorial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnAyuda.add(mntmTutorial);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblManejador = new JLabel(GAL_GUI.language.ParametersConfiguration[0]);
		lblManejador.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblManejador.setBounds(12, 13, 66, 14);
		contentPane.add(lblManejador);

		modHandler_panel = new JPanel();
		
		cb_Handler = new JComboBox<String>();
		cb_Handler.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cb_Handler.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				if(((JComboBox<String>) e.getSource()).getSelectedIndex() == 0)
					modHandler_panel.setVisible(false);
				else
					modHandler_panel.setVisible(true);
			}
		});
		cb_Handler.setModel(new DefaultComboBoxModel<String>(new String[] {GAL_GUI.language.ParametersConfiguration[1], GAL_GUI.language.ParametersConfiguration[2]}));
		cb_Handler.setBounds(79, 11, 161, 20);
		contentPane.add(cb_Handler);
		
		JLabel lblTamaoDeLa = new JLabel(GAL_GUI.language.ParametersConfiguration[3]);
		lblTamaoDeLa.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTamaoDeLa.setBounds(12, 67, 150, 14);
		contentPane.add(lblTamaoDeLa);
		
		spn_TamPob = new JSpinner();
		spn_TamPob.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spn_TamPob.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_TamPob.setBounds(188, 65, 52, 20);
		contentPane.add(spn_TamPob);
		
		JLabel lblNmeroMxDe = new JLabel(GAL_GUI.language.ParametersConfiguration[4]);
		lblNmeroMxDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNmeroMxDe.setBounds(12, 94, 161, 14);
		contentPane.add(lblNmeroMxDe);
		
		spn_maxGen = new JSpinner();
		spn_maxGen.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spn_maxGen.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_maxGen.setBounds(188, 92, 52, 20);
		contentPane.add(spn_maxGen);
		
		modHandler_panel.setBounds(12, 117, 240, 29);
		contentPane.add(modHandler_panel);
		modHandler_panel.setLayout(null);
		
		JLabel lblNumeroDePadres = new JLabel(GAL_GUI.language.ParametersConfiguration[5]);
		lblNumeroDePadres.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNumeroDePadres.setBounds(0, 5, 179, 14);
		modHandler_panel.add(lblNumeroDePadres);
		
		spn_Mod = new JSpinner();
		spn_Mod.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spn_Mod.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_Mod.setBounds(176, 3, 52, 20);
		modHandler_panel.add(spn_Mod);
		
		
		JButton btnAceptar = new JButton(GAL_GUI.language.CommonWords[0]);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.gal.setParameters(cb_Handler.getSelectedIndex(), cb_initializer.getSelectedIndex(), (Integer)spn_TamPob.getValue(), (Integer)spn_maxGen.getValue(), (Integer)spn_Mod.getValue());
				dispose();
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAceptar.setBounds(84, 159, 89, 23);
		contentPane.add(btnAceptar);
		
		JLabel lblInicializador = new JLabel(GAL_GUI.language.ParametersConfiguration[6]);
		lblInicializador.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblInicializador.setBounds(12, 40, 66, 14);
		contentPane.add(lblInicializador);
		
		String[] tipos_ini= new String[2+GAL_GUI.metadatas.initializer_metadatas.length];
		tipos_ini[0]= GAL_GUI.language.ParametersConfiguration[1];
		tipos_ini[1]= GAL_GUI.language.ParametersConfiguration[7];
		for(int i=0;i<GAL_GUI.metadatas.initializer_metadatas.length;i++){
			tipos_ini[2+i]= GAL_GUI.metadatas.initializer_metadatas[i].name;
		}
		cb_initializer = new JComboBox<String>(tipos_ini);
		cb_initializer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if(cb_initializer.getSelectedIndex()==1)
						GAL_GUI.gal.validateInitializer();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[30]+"\n"+GAL_GUI.language.Errors[31]);
					cb_initializer.setSelectedIndex(0);
				}
			}
		});
		cb_initializer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cb_initializer.setBounds(79, 38, 161, 20);
		contentPane.add(cb_initializer);
	}
	
	void actualizar(){
		modHandler_panel.setVisible(cb_Handler.getSelectedIndex()!=0);
		cb_Handler.setSelectedIndex(GAL_GUI.gal.getParameter("handlerToUse"));
		cb_initializer.setSelectedIndex(GAL_GUI.gal.getParameter("initializerToUse"));
		spn_TamPob.setValue(GAL_GUI.gal.getParameter("popSize"));
		spn_maxGen.setValue(GAL_GUI.gal.getParameter("maxGen"));
		spn_Mod.setValue(GAL_GUI.gal.getParameter("modParam"));
	}
}
