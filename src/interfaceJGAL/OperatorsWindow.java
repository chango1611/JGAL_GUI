package interfaceJGAL;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollPane;

import JGAL.GAL_ChromosomeMutation;
import JGAL.GAL_ClassicCrossover;
import JGAL.GAL_ClassicMutation;
import JGAL.GAL_GeneticOperator;
import JGAL.GAL_Inversion;
import JGAL.GAL_MultiPointCrossover;
import JGAL.GAL_OrderedCrossover;
import JGAL.GAL_RandomCrossover;
import JGAL.GAL_SegmentCrossover;
import JGAL.GAL_ShuffleMutation;
import JGAL.GAL_SwapMutation;
import JGAL.GAL_UniformCrossover;
import javax.swing.ImageIcon;

public class OperatorsWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultListModel<String> lm_OperadoresDefinidos;
	private int editar;

	/**
	 * Create the frame.
	 */
	public OperatorsWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(GAL_GUI.gal.getOperatorsNames().length>0)
					GeneralWindow.pnl_ProgressConfig2.setBackground(new Color(255, 50, 0));
				else
					GeneralWindow.pnl_ProgressConfig2.setBackground(new Color(240, 240, 240));
			}
			@Override
			public void windowOpened(WindowEvent e) {
				actualizar();
			}
		});
		setTitle(GAL_GUI.language.botonesPrincipales[5]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 350);
		
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
					int returnVal= fc.showOpenDialog(OperatorsWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.openOperators(fc.getSelectedFile());
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
				int returnVal= fc.showSaveDialog(OperatorsWindow.this);
				if(returnVal == JFileChooser.APPROVE_OPTION)
					GAL_GUI.gal.saveOperators(fc.getSelectedFile());
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
				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[10]);
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
		
		JPanel Configuracion = new JPanel();
		Configuracion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Configuracion.setBounds(2, 0, 263, 255);
		contentPane.add(Configuracion);
		Configuracion.setLayout(null);
		
		final JPanel configuracionEspecifica = new JPanel();
		configuracionEspecifica.setBounds(5, 95, 250, 121);
		Configuracion.add(configuracionEspecifica);
		configuracionEspecifica.setLayout(new CardLayout(0, 0));
		
		JPanel CruceMultiPuntos = new JPanel();
		configuracionEspecifica.add(CruceMultiPuntos, "Multi");
		CruceMultiPuntos.setLayout(null);
		
		JLabel lblMin = new JLabel(GAL_GUI.language.OperatorsConfiguration[15]);
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMin.setBounds(10, 29, 107, 14);
		CruceMultiPuntos.add(lblMin);
		
		final JSpinner spn_MultiPuntos = new JSpinner();
		spn_MultiPuntos.setModel(new SpinnerNumberModel(new Integer(2), new Integer(2), null, new Integer(2)));
		spn_MultiPuntos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_MultiPuntos.setBounds(129, 26, 70, 20);
		CruceMultiPuntos.add(spn_MultiPuntos);
		
		JLabel lblConfiguracinAdicional = new JLabel(GAL_GUI.language.OperatorsConfiguration[14]);
		lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional.setBounds(10, 4, 192, 14);
		CruceMultiPuntos.add(lblConfiguracinAdicional);
		
		JPanel Otros = new JPanel();
		configuracionEspecifica.add(Otros, "Otros");
		
		JPanel CruceUniforme = new JPanel();
		CruceUniforme.setLayout(null);
		configuracionEspecifica.add(CruceUniforme, "Uniforme");
		
		JLabel lblProbDeCambio = new JLabel(GAL_GUI.language.OperatorsConfiguration[16]);
		lblProbDeCambio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbDeCambio.setBounds(10, 29, 107, 14);
		CruceUniforme.add(lblProbDeCambio);
		
		final JSpinner spn_uniforme = new JSpinner();
		spn_uniforme.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		spn_uniforme.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_uniforme.setBounds(129, 26, 70, 20);
		CruceUniforme.add(spn_uniforme);
		
		JLabel label_1 = new JLabel(GAL_GUI.language.OperatorsConfiguration[14]);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(10, 4, 192, 14);
		CruceUniforme.add(label_1);
		
		JPanel CruceSegmentos = new JPanel();
		CruceSegmentos.setLayout(null);
		configuracionEspecifica.add(CruceSegmentos, "Segmentos");
		
		JLabel lblProbSegmentos = new JLabel(GAL_GUI.language.OperatorsConfiguration[17]);
		lblProbSegmentos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbSegmentos.setBounds(10, 29, 107, 14);
		CruceSegmentos.add(lblProbSegmentos);
		
		final JSpinner spn_Segmentos = new JSpinner();
		spn_Segmentos.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		spn_Segmentos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_Segmentos.setBounds(129, 26, 70, 20);
		CruceSegmentos.add(spn_Segmentos);
		
		JLabel label_2 = new JLabel(GAL_GUI.language.OperatorsConfiguration[14]);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(10, 4, 192, 14);
		CruceSegmentos.add(label_2);
		
		JPanel MutacionCromosoma = new JPanel();
		MutacionCromosoma.setLayout(null);
		configuracionEspecifica.add(MutacionCromosoma, "Mut Crom");
		
		JLabel lblProbDeMutacin = new JLabel(GAL_GUI.language.OperatorsConfiguration[18]);
		lblProbDeMutacin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbDeMutacin.setBounds(10, 29, 107, 14);
		MutacionCromosoma.add(lblProbDeMutacin);
		
		final JSpinner spn_MutCrom = new JSpinner();
		spn_MutCrom.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		spn_MutCrom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_MutCrom.setBounds(129, 26, 70, 20);
		MutacionCromosoma.add(spn_MutCrom);
		
		JLabel label_3 = new JLabel(GAL_GUI.language.OperatorsConfiguration[14]);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(10, 4, 192, 14);
		MutacionCromosoma.add(label_3);
		
		final JComboBox<String> cb_TiposDeOperadores = new JComboBox<String>();
		cb_TiposDeOperadores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				int option= ((JComboBox<String>) e.getSource()).getSelectedIndex();
				if(option<11){
					switch(option){
						case 1:
							((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Multi");
						break;
						case 2:
							((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Uniforme");
						break;
						case 3:
							((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Segmentos");
						break;
						case 7:
							((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Mut Crom");
						break;
						default:
							((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
						break;	
					}
				}else{
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,GAL_GUI.metadatas.operator_metadatas[option-11].name);
				}
			}
		});
		cb_TiposDeOperadores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cb_TiposDeOperadores.setBounds(75, 32, 180, 20);
		Configuracion.add(cb_TiposDeOperadores);
		String[] tipos= new String[11+GAL_GUI.metadatas.operator_metadatas.length];
		System.arraycopy(GAL_GUI.language.OperatorsConfiguration, 2, tipos, 0, 11);
		for(int i=0;i<GAL_GUI.metadatas.operator_metadatas.length;i++){
			tipos[i+11]= GAL_GUI.metadatas.operator_metadatas[i].name;
			configuracionEspecifica.add(GAL_GUI.metadatas.operator_metadatas[i].metaPanel,tipos[i+11]);
		}
		cb_TiposDeOperadores.setModel(new DefaultComboBoxModel<String>(tipos));
		cb_TiposDeOperadores.setSelectedIndex(0);
		
		final JSpinner spn_Prob = new JSpinner();
		spn_Prob.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_Prob.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		spn_Prob.setBounds(134, 60, 70, 20);
		Configuracion.add(spn_Prob);
		
		JPanel Operadores = new JPanel();
		Operadores.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Operadores.setBounds(266, 0, 168, 255);
		contentPane.add(Operadores);
		Operadores.setLayout(null);
		
		lm_OperadoresDefinidos= new DefaultListModel<String>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 148, 184);
		Operadores.add(scrollPane);
		final JList<String> operadoresDefinidos = new JList<String>(lm_OperadoresDefinidos);
		scrollPane.setViewportView(operadoresDefinidos);
		operadoresDefinidos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		operadoresDefinidos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblOperadores = new JLabel(GAL_GUI.language.OperatorsConfiguration[20]);
		lblOperadores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOperadores.setBounds(10, 7, 118, 14);
		Operadores.add(lblOperadores);
		
		JButton btnEliminar = new JButton(GAL_GUI.language.OperatorsConfiguration[22]);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar= operadoresDefinidos.getSelectedIndex();
				if(editar!=-1){
					lm_OperadoresDefinidos.remove(editar);
					GAL_GUI.gal.removeOperator(editar);
				}
				editar= -1;
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEliminar.setBounds(81, 221, 77, 23);
		Operadores.add(btnEliminar);
		
		JButton btnEditar = new JButton(GAL_GUI.language.OperatorsConfiguration[21]);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar= operadoresDefinidos.getSelectedIndex();
				if(editar!=-1){
					GAL_GeneticOperator aux= GAL_GUI.gal.getOperator(editar);
					cb_TiposDeOperadores.setSelectedItem(GAL_GUI.gal.getOperatorName(editar));
					spn_Prob.setValue(aux.getProb());
					if(aux instanceof GAL_MultiPointCrossover){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Multi");
						spn_MultiPuntos.setValue(((GAL_MultiPointCrossover) aux).getNumberOfPoints());
					}else if(aux instanceof GAL_UniformCrossover){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Uniforme");
						spn_uniforme.setValue(((GAL_UniformCrossover) aux).getUniformProb());
					}else if(aux instanceof GAL_SegmentCrossover){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Segmentos");
						spn_Segmentos.setValue(((GAL_SegmentCrossover) aux).getSegmetChangeProb());
					}else if(aux instanceof GAL_ChromosomeMutation){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Mut Crom");
						spn_MutCrom.setValue(((GAL_ChromosomeMutation) aux).getSecondProb());
					}else{
						int type= GAL_GUI.gal.getOperatorType(editar);
						if(type>10){
							((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,GAL_GUI.metadatas.operator_metadatas[type-11].name);
							try {
								GAL_GUI.metadatas.operator_metadatas[type-11].extractOperatorData(aux,((JPanel)configuracionEspecifica.getComponent(type-6)).getComponents());
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, e1.getMessage());
							}
						}
					}
				}
			}
		});
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditar.setBounds(10, 221, 69, 23);
		Operadores.add(btnEditar);
		
		JButton clean = new JButton("");
		clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret=JOptionPane.showConfirmDialog(null, GAL_GUI.language.Questions[6],GAL_GUI.language.CommonWords[8],JOptionPane.OK_CANCEL_OPTION);
				if(ret== JOptionPane.OK_OPTION){
					GAL_GUI.gal.limpiarOperadores();
					actualizar();
				}
			}
		});
		clean.setIcon(new ImageIcon(OperatorsWindow.class.getResource("/Images/limpiar.png")));
		clean.setToolTipText(GAL_GUI.language.CommonWords[8]);
		clean.setBounds(138, 7, 20, 20);
		Operadores.add(clean);
		
		JButton btnCrear = new JButton(GAL_GUI.language.OperatorsConfiguration[19]);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tipo= cb_TiposDeOperadores.getSelectedIndex();
				double prob= (Double) spn_Prob.getValue();
				try{
					GAL_GeneticOperator aux= new GAL_ClassicCrossover(prob);
					switch(tipo){
						case 0:
							;
						break;
						case 1:
							aux= new GAL_MultiPointCrossover(prob, (Integer)spn_MultiPuntos.getValue());
						break;
						case 2:
							aux= new GAL_UniformCrossover(prob, (Double)spn_uniforme.getValue());
						break;
						case 3:
							aux= new GAL_SegmentCrossover(prob, (Double)spn_Segmentos.getValue());
						break;
						case 4:
							aux= new GAL_RandomCrossover(0.0,new GAL_ClassicCrossover(prob));
						break;
						case 5:
							if(!GAL_GUI.gal.isMutableGeneConfig())
								throw new Exception(GAL_GUI.language.Errors[23]);
							aux= new GAL_OrderedCrossover(prob);
						break;
						case 6:
							aux= new GAL_ClassicMutation(prob);
						break;
						case 7:
							aux= new GAL_ChromosomeMutation(prob, (Double) spn_MutCrom.getValue());
						break;
						case 8:
							if(!GAL_GUI.gal.isMutableGeneConfig())
								throw new Exception(GAL_GUI.language.Errors[23]);
							aux= new GAL_SwapMutation(prob);
						break;
						case 9:
							if(!GAL_GUI.gal.isMutableGeneConfig())
								throw new Exception(GAL_GUI.language.Errors[23]);
							aux= new GAL_ShuffleMutation(prob);
						break;
						case 10:
							if(!GAL_GUI.gal.isMutableGeneConfig())
								throw new Exception(GAL_GUI.language.Errors[23]);
							aux= new GAL_Inversion(prob);
						break;
						default:
							aux= GAL_GUI.metadatas.operator_metadatas[tipo-11].operatorConstructor(((JPanel)configuracionEspecifica.getComponent(tipo-6)).getComponents(),prob);
						break;
					}
					if(editar==-1){
						GAL_GUI.gal.addOperator(aux, tipo);
						lm_OperadoresDefinidos.addElement((String)cb_TiposDeOperadores.getSelectedItem());
					}else{
						GAL_GUI.gal.editOperator(editar, aux, tipo);
						lm_OperadoresDefinidos.set(editar, (String)cb_TiposDeOperadores.getSelectedItem());
						editar= -1;
					}
				}catch(Exception ex){
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnCrear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCrear.setBounds(173, 221, 80, 23);
		Configuracion.add(btnCrear);
		
		JLabel lblTipo = new JLabel(GAL_GUI.language.OperatorsConfiguration[1]);
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTipo.setBounds(20, 35, 52, 14);
		Configuracion.add(lblTipo);
		
		JLabel lblConfiguracinDeVariable = new JLabel(GAL_GUI.language.OperatorsConfiguration[0]);
		lblConfiguracinDeVariable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinDeVariable.setBounds(10, 8, 213, 14);
		Configuracion.add(lblConfiguracinDeVariable);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(5, 90, 255, 1);
		Configuracion.add(separator);
		
		JLabel lblProbDeOcurrencia = new JLabel(GAL_GUI.language.OperatorsConfiguration[13]);
		lblProbDeOcurrencia.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbDeOcurrencia.setBounds(20, 62, 113, 14);
		Configuracion.add(lblProbDeOcurrencia);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(2, 255, 432, 46);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnAceptar = new JButton(GAL_GUI.language.CommonWords[0]);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAceptar.setBounds(171, 11, 89, 23);
		panel.add(btnAceptar);
	}
	
	void actualizar(){
		editar= -1;
		lm_OperadoresDefinidos.clear();
		for(String name: GAL_GUI.gal.getOperatorsNames())
			lm_OperadoresDefinidos.addElement(name);
	}
}
