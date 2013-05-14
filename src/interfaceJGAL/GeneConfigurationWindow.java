package interfaceJGAL;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Terminals;

import JGAL.GAL_BinaryGeneConfig;
import JGAL.GAL_CharacterGeneConfig;
import JGAL.GAL_DoubleGeneConfig;
import JGAL.GAL_GeneConfig;
import JGAL.GAL_IntegerGeneConfig;
import JGAL.GAL_NominalGeneConfig;
import javax.swing.ImageIcon;

public class GeneConfigurationWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt_NombreVariable;
	private JTextField valoresAllele1;
	private JTextField txt_MinCaracter;
	private JTextField txt_MaxCaracter;
	private int editar;
	private DefaultListModel<String> lm_VariablesDefinidas;
	
	/**
	 * Create the frame.
	 */
	public GeneConfigurationWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				if(GAL_GUI.gal.getGeneNames().length>0)
					GeneralWindow.pnl_ProgressCromosoma.setBackground(new Color(0, 128, 64));
				else
					GeneralWindow.pnl_ProgressCromosoma.setBackground(new Color(240, 240, 240));
			}
			@Override
			public void windowOpened(WindowEvent e) {
				actualizar();
			}
		});
		
		setTitle(GAL_GUI.language.botonesPrincipales[0]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 350);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu(GAL_GUI.language.CommonWords[2]);
		mnArchivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmSalir = new JMenuItem(GAL_GUI.language.CommonWords[5]);
		mntmSalir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JMenuItem mntmGuardar = new JMenuItem(GAL_GUI.language.CommonWords[4]);
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc= new JFileChooser();
				int returnVal= fc.showSaveDialog(GeneConfigurationWindow.this);
				if(returnVal == JFileChooser.APPROVE_OPTION)
					GAL_GUI.gal.saveGeneConfig(fc.getSelectedFile());
			}
		});
		
		JMenuItem mntmAbrir = new JMenuItem(GAL_GUI.language.CommonWords[3]);
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					int returnVal= fc.showOpenDialog(GeneConfigurationWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.openGeneConfig(fc.getSelectedFile());
					actualizar();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
				}
			}
		});
		mntmAbrir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmAbrir);
		mntmGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmGuardar);
		
		JMenu mnAyuda = new JMenu(GAL_GUI.language.CommonWords[6]);
		mnAyuda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnAyuda);
		
		JMenuItem mntmTutorial = new JMenuItem(GAL_GUI.language.CommonWords[7]);
		mntmTutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[5]);
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
		
		txt_NombreVariable = new JTextField();
		txt_NombreVariable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txt_NombreVariable.setBounds(57, 33, 196, 20);
		Configuracion.add(txt_NombreVariable);
		txt_NombreVariable.setColumns(10);
		
		final JPanel configuracionEspecifica = new JPanel();
		configuracionEspecifica.setBounds(5, 95, 250, 121);
		Configuracion.add(configuracionEspecifica);
		configuracionEspecifica.setLayout(new CardLayout(0, 0));
		
		JPanel Entero = new JPanel();
		configuracionEspecifica.add(Entero, "Entero");
		Entero.setLayout(null);
		
		JLabel lblMin = new JLabel(GAL_GUI.language.GeneConfiguration[9]);
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMin.setBounds(10, 29, 35, 14);
		Entero.add(lblMin);
		
		JLabel lblMax = new JLabel(GAL_GUI.language.GeneConfiguration[10]);
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMax.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMax.setBounds(10, 60, 35, 14);
		Entero.add(lblMax);
		
		final JSpinner spn_MinEntero = new JSpinner();
		spn_MinEntero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_MinEntero.setBounds(55, 26, 70, 20);
		Entero.add(spn_MinEntero);
		
		final JSpinner spn_MaxEntero = new JSpinner();
		spn_MaxEntero.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_MaxEntero.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spn_MaxEntero.setBounds(55, 57, 70, 20);
		Entero.add(spn_MaxEntero);
		
		JLabel lblConfiguracinAdicional = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
		lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional.setBounds(10, 4, 192, 14);
		Entero.add(lblConfiguracinAdicional);
		
		JPanel Nominal = new JPanel();
		configuracionEspecifica.add(Nominal, "Nominal");
		Nominal.setLayout(null);
		
		JLabel label = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(10, 4, 129, 16);
		Nominal.add(label);
		
		JLabel lblValores = new JLabel(GAL_GUI.language.GeneConfiguration[11]);
		lblValores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValores.setBounds(10, 25, 100, 14);
		Nominal.add(lblValores);
		
		valoresAllele1 = new JTextField();
		valoresAllele1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		valoresAllele1.setBounds(10, 44, 86, 20);
		Nominal.add(valoresAllele1);
		valoresAllele1.setColumns(10);
		
		final DefaultListModel<String> lm_ValoresAllele= new DefaultListModel<String>();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(151, 44, 99, 64);
		Nominal.add(scrollPane_1);
		final JList<String> valoresAllele2 = new JList<String>(lm_ValoresAllele);
		scrollPane_1.setViewportView(valoresAllele2);
		valoresAllele2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		valoresAllele2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton agregarAllele = new JButton(">");
		agregarAllele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String alelo= valoresAllele1.getText();
				try{
					if(alelo.length()==0)
						throw new Exception(GAL_GUI.language.Errors[6]);
					if(lm_ValoresAllele.contains(alelo))
						throw new Exception(GAL_GUI.language.Errors[7]);
					lm_ValoresAllele.addElement(alelo);
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		agregarAllele.setFont(new Font("Tahoma", Font.PLAIN, 11));
		agregarAllele.setBounds(100, 44, 46, 20);
		Nominal.add(agregarAllele);
		
		JButton eliminarAlleles = new JButton("<");
		eliminarAlleles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index= valoresAllele2.getSelectedIndex();
				if(index!=-1){
					lm_ValoresAllele.remove(index);
				}
			}
		});
		eliminarAlleles.setFont(new Font("Tahoma", Font.PLAIN, 11));
		eliminarAlleles.setBounds(100, 75, 46, 20);
		Nominal.add(eliminarAlleles);
		
		JPanel Otros = new JPanel();
		configuracionEspecifica.add(Otros, "Otros");
		
		JPanel Caracteres = new JPanel();
		Caracteres.setLayout(null);
		configuracionEspecifica.add(Caracteres, "Caracter");
		
		JLabel label_1 = new JLabel(GAL_GUI.language.GeneConfiguration[9]);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(10, 29, 35, 14);
		Caracteres.add(label_1);
		
		JLabel label_2 = new JLabel(GAL_GUI.language.GeneConfiguration[10]);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(10, 60, 35, 14);
		Caracteres.add(label_2);
		
		JLabel label_3 = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(10, 4, 192, 14);
		Caracteres.add(label_3);
		
		txt_MinCaracter = new JTextField();
		txt_MinCaracter.setBounds(60, 27, 39, 20);
		Caracteres.add(txt_MinCaracter);
		txt_MinCaracter.setColumns(10);
		
		txt_MaxCaracter = new JTextField();
		txt_MaxCaracter.setBounds(60, 56, 39, 20);
		Caracteres.add(txt_MaxCaracter);
		txt_MaxCaracter.setColumns(10);
		
		JPanel Real = new JPanel();
		Real.setLayout(null);
		configuracionEspecifica.add(Real, "Real");
		
		JLabel label_4 = new JLabel(GAL_GUI.language.GeneConfiguration[9]);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(10, 29, 35, 14);
		Real.add(label_4);
		
		JLabel label_5 = new JLabel(GAL_GUI.language.GeneConfiguration[10]);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(10, 60, 35, 14);
		Real.add(label_5);
		
		final JSpinner spn_MinReal = new JSpinner();
		spn_MinReal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_MinReal.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spn_MinReal.setBounds(55, 26, 70, 20);
		Real.add(spn_MinReal);
		
		final JSpinner spn_MaxReal = new JSpinner();
		spn_MaxReal.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		spn_MaxReal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_MaxReal.setBounds(55, 57, 70, 20);
		Real.add(spn_MaxReal);
		
		JLabel label_6 = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_6.setBounds(10, 4, 192, 14);
		Real.add(label_6);
		
		final JComboBox<String> cb_TiposDeGenes = new JComboBox<String>();
		cb_TiposDeGenes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				int option= ((JComboBox<String>) e.getSource()).getSelectedIndex();
				switch(option){
					case 0:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Entero");
					break;
					case 1:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Real");
					break;
					case 2:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
					break;
					case 3:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Caracter");
					break;
					case 4:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Nominal");
					break;
					default:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,GAL_GUI.metadatas.gene_metadatas[option-5].name);
					break;
				}
			}
		});
		cb_TiposDeGenes.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cb_TiposDeGenes.setBounds(57, 64, 196, 20);
		Configuracion.add(cb_TiposDeGenes);
		String[] tipos= new String[5+GAL_GUI.metadatas.gene_metadatas.length];
		System.arraycopy(GAL_GUI.language.GeneConfiguration, 3, tipos, 0, 5);
		for(int i=0; i<GAL_GUI.metadatas.gene_metadatas.length;i++){
			tipos[5+i]= GAL_GUI.metadatas.gene_metadatas[i].name;
			configuracionEspecifica.add(GAL_GUI.metadatas.gene_metadatas[i].metaPanel, tipos[5+i]);
		}
		cb_TiposDeGenes.setModel(new DefaultComboBoxModel<String>(tipos));
		cb_TiposDeGenes.setSelectedIndex(0);
		
		JLabel lblNombre = new JLabel(GAL_GUI.language.GeneConfiguration[1]);
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNombre.setBounds(10, 36, 37, 14);
		Configuracion.add(lblNombre);
		
		JLabel lblTipo = new JLabel(GAL_GUI.language.GeneConfiguration[2]);
		lblTipo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTipo.setBounds(10, 67, 37, 14);
		Configuracion.add(lblTipo);
		
		JLabel lblConfiguracinDeVariable = new JLabel(GAL_GUI.language.GeneConfiguration[0]);
		lblConfiguracinDeVariable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinDeVariable.setBounds(10, 8, 213, 14);
		Configuracion.add(lblConfiguracinDeVariable);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(5, 90, 255, 1);
		Configuracion.add(separator);
		
		JPanel Variables = new JPanel();
		Variables.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Variables.setBounds(266, 0, 168, 255);
		contentPane.add(Variables);
		Variables.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 148, 184);
		Variables.add(scrollPane);
		lm_VariablesDefinidas= new DefaultListModel<String>();
		final JList<String> variablesDefinidas = new JList<String>(lm_VariablesDefinidas);
		scrollPane.setViewportView(variablesDefinidas);
		variablesDefinidas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		variablesDefinidas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblVariablesDefinidas = new JLabel(GAL_GUI.language.GeneConfiguration[13]);
		lblVariablesDefinidas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVariablesDefinidas.setBounds(10, 7, 126, 14);
		Variables.add(lblVariablesDefinidas);
		
		JButton btnEliminar = new JButton(GAL_GUI.language.GeneConfiguration[15]);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar= variablesDefinidas.getSelectedIndex();
				if(editar!=-1){
					lm_VariablesDefinidas.remove(editar);
					GAL_GUI.gal.removeGeneConfig(editar);
					GAL_GUI.gal.getInterpreter(0).reset(GAL_GUI.gal.getGeneNames());
					GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(240,240,240));
				}
				editar= -1;
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEliminar.setBounds(81, 221, 77, 23);
		Variables.add(btnEliminar);
		
		JButton btnEditar = new JButton(GAL_GUI.language.GeneConfiguration[14]);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar= variablesDefinidas.getSelectedIndex();
				if(editar!=-1){
					txt_NombreVariable.setText((String)variablesDefinidas.getSelectedValue());
					GAL_GeneConfig<?> aux= GAL_GUI.gal.getGeneConfig(editar);
					if(aux instanceof GAL_BinaryGeneConfig){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
						cb_TiposDeGenes.setSelectedIndex(2);
					}else if(aux instanceof GAL_IntegerGeneConfig){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Entero");
						cb_TiposDeGenes.setSelectedIndex(0);
						spn_MinEntero.setValue(((GAL_IntegerGeneConfig) aux).getMin());
						spn_MaxEntero.setValue(((GAL_IntegerGeneConfig) aux).getMax());
					}else if(aux instanceof GAL_DoubleGeneConfig){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Real");
						cb_TiposDeGenes.setSelectedIndex(1);
						spn_MinReal.setValue(((GAL_DoubleGeneConfig) aux).getMin());
						spn_MaxReal.setValue(((GAL_DoubleGeneConfig) aux).getMax());
					}else if(aux instanceof GAL_CharacterGeneConfig){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Character");
						cb_TiposDeGenes.setSelectedIndex(3);
						txt_MinCaracter.setText(""+((GAL_CharacterGeneConfig) aux).getMin());
						txt_MaxCaracter.setText(""+((GAL_CharacterGeneConfig) aux).getMax());
					}else if(aux instanceof GAL_NominalGeneConfig){
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Nominal");
						cb_TiposDeGenes.setSelectedIndex(4);
						lm_ValoresAllele.clear();
						String[] alleles= ((GAL_NominalGeneConfig) aux).getAlleles();
						for(int i=0;i<alleles.length;i++)
							lm_ValoresAllele.addElement(alleles[i]);
					}else{
						int type= GAL_GUI.gal.getGeneType(editar);
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,GAL_GUI.metadatas.gene_metadatas[type-5].name);
						cb_TiposDeGenes.setSelectedIndex(type);
						try {
							GAL_GUI.metadatas.gene_metadatas[type-5].extractGeneData(aux,((JPanel)configuracionEspecifica.getComponent(type)).getComponents());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
						}
					}
				}
			}
		});
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnEditar.setBounds(10, 221, 69, 23);
		Variables.add(btnEditar);
		
		JButton clean = new JButton("");
		clean.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int ret=JOptionPane.showConfirmDialog(null, GAL_GUI.language.Questions[6],GAL_GUI.language.CommonWords[8],JOptionPane.OK_CANCEL_OPTION);
				if(ret== JOptionPane.OK_OPTION){
					GAL_GUI.gal.limpiarGenes();
					actualizar();
				}
			}
		});
		clean.setIcon(new ImageIcon(GeneConfigurationWindow.class.getResource("/Images/limpiar.png")));
		clean.setToolTipText(GAL_GUI.language.CommonWords[8]);
		clean.setBounds(138, 7, 20, 20);
		Variables.add(clean);
		
		JButton btnCrear = new JButton(GAL_GUI.language.GeneConfiguration[12]);
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre= txt_NombreVariable.getText();
				int tipo= cb_TiposDeGenes.getSelectedIndex();
				try{
					GAL_GeneConfig<?> aux= new GAL_BinaryGeneConfig();
					if(nombre.length()==0)
						throw new Exception(GAL_GUI.language.Errors[0]);
					if(lm_VariablesDefinidas.contains(nombre) && editar!=lm_VariablesDefinidas.indexOf(nombre))
						throw new Exception(GAL_GUI.language.Errors[1]);
					Parser<?> identifier= Terminals.Identifier.TOKENIZER;
					try{
						identifier.parse(nombre);
					}catch(Exception ex){
						throw new Exception(GAL_GUI.language.Errors[2] + "\n" +
								GAL_GUI.language.Errors[3]);
					}
					switch(tipo){
						case 0:
							int minEntero= (Integer)spn_MinEntero.getValue(),
							maxEntero= (Integer)spn_MaxEntero.getValue();
							if(minEntero>=maxEntero)
								throw new Exception("Min("+minEntero+") >= Max("+maxEntero+")");
							if(maxEntero-minEntero==1)
								throw new Exception(GAL_GUI.language.Errors[4] + minEntero + ".." + maxEntero+GAL_GUI.language.Errors[5]);
							aux= new GAL_IntegerGeneConfig(nombre,minEntero,maxEntero);
						break;
						case 1:
							double minReal= (Double)spn_MinReal.getValue(),
							maxReal= (Double)spn_MaxReal.getValue();
							if(minReal>=maxReal)
								throw new Exception("Min("+minReal+") >= Max("+maxReal+")");
							aux= new GAL_DoubleGeneConfig(nombre,minReal,maxReal);
						break;
						case 2:
							aux= new GAL_BinaryGeneConfig(nombre);
						break;
						case 3:
							String minChar=txt_MinCaracter.getText(), maxChar=txt_MaxCaracter.getText();
							if(minChar.length()!=1 || maxChar.length()!=1)
								throw new Exception(GAL_GUI.language.Errors[9]);
							if(minChar.charAt(0)>=maxChar.charAt(0))
								throw new Exception("Min("+minChar+") >= Max("+maxChar+")");
							if(maxChar.charAt(0)-minChar.charAt(0)==(char)1)
								throw new Exception(GAL_GUI.language.Errors[4]+minChar+".."+maxChar+GAL_GUI.language.Errors[5]);
							aux= new GAL_CharacterGeneConfig(nombre,minChar.charAt(0),maxChar.charAt(0));
						break;
						case 4:
							int size= valoresAllele2.getModel().getSize();
							if(size<2)
								throw new Exception(GAL_GUI.language.Errors[8]);
							String[] alelos= new String[size];
							for(int i=0;i<size;i++)
								alelos[i]= (String) valoresAllele2.getModel().getElementAt(i);
							aux= new GAL_NominalGeneConfig(nombre,alelos);
						break;
						default:
							aux= GAL_GUI.metadatas.gene_metadatas[tipo-5].geneConstructor(((JPanel)configuracionEspecifica.getComponent(tipo)).getComponents(), nombre);
						break;
					}
					if(editar<0){
						GAL_GUI.gal.addGeneConfig(aux,nombre,tipo);
						lm_VariablesDefinidas.addElement(nombre);
					}else{
						GAL_GUI.gal.editGeneConfig(editar, aux, nombre,tipo);
						lm_VariablesDefinidas.set(editar, nombre);
						editar= -1;
					}
					GAL_GUI.gal.getInterpreter(0).reset(GAL_GUI.gal.getGeneNames());
					GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(240,240,240));
					try{
						GAL_GUI.gal.validateInitializer();
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[30]+"\n"+GAL_GUI.language.Errors[31]);
						GAL_GUI.gal.setInitializer();
						GAL_GUI.gal.resetParameters();
						GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(240,240,240));
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnCrear.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCrear.setBounds(173, 221, 80, 23);
		Configuracion.add(btnCrear);
		
		JButton btnAgregarvarios = new JButton(GAL_GUI.language.GeneConfiguration[16]);
		btnAgregarvarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpinner spinner = new JSpinner(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
				int option = JOptionPane.showOptionDialog(null, spinner, GAL_GUI.language.GeneConfiguration[17], JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
				int fact_size=0, i;
				if (option == JOptionPane.CANCEL_OPTION || option== JOptionPane.CLOSED_OPTION){
				    return;
				}else if (option == JOptionPane.OK_OPTION){
					fact_size= (Integer)spinner.getValue();
				}
				String nombre= txt_NombreVariable.getText();
				int tipo= cb_TiposDeGenes.getSelectedIndex();
				try{
					GAL_GeneConfig<?> aux= new GAL_BinaryGeneConfig();
					if(nombre.length()==0)
						throw new Exception(GAL_GUI.language.Errors[0]);
					
					//Creo y reviso si todos los nombres son permitidos
					String[] nombres= new String[fact_size];
					for(i=0;i<fact_size;i++){
						nombres[i]= nombre+"_"+i;
						if(lm_VariablesDefinidas.contains(nombres[i]))
							throw new Exception(GAL_GUI.language.Errors[1]);
					}
					
					Parser<?> identifier= Terminals.Identifier.TOKENIZER;
					try{
						identifier.parse(nombre);
					}catch(Exception ex){
						throw new Exception(GAL_GUI.language.Errors[2] + "\n" +
								GAL_GUI.language.Errors[3]);
					}
					for(i=0;i<fact_size;i++){
						switch(tipo){
							case 0:
								int minEntero= (Integer)spn_MinEntero.getValue(),
								maxEntero= (Integer)spn_MaxEntero.getValue();
								if(minEntero>=maxEntero)
									throw new Exception("Min("+minEntero+") >= Max("+maxEntero+")");
								if(maxEntero-minEntero==1)
									throw new Exception(GAL_GUI.language.Errors[4] + minEntero + ".." + maxEntero+GAL_GUI.language.Errors[5]);
								aux= new GAL_IntegerGeneConfig(nombres[i],minEntero,maxEntero);
							break;
							case 1:
								double minReal= (Double)spn_MinReal.getValue(),
								maxReal= (Double)spn_MaxReal.getValue();
								if(minReal>=maxReal)
									throw new Exception("Min("+minReal+") >= Max("+maxReal+")");
								aux= new GAL_DoubleGeneConfig(nombres[i],minReal,maxReal);
							break;
							case 2:
								aux= new GAL_BinaryGeneConfig(nombres[i]);
							break;
							case 3:
								String minChar=txt_MinCaracter.getText(), maxChar=txt_MaxCaracter.getText();
								if(minChar.length()!=1 || maxChar.length()!=1)
									throw new Exception(GAL_GUI.language.Errors[9]);
								if(minChar.charAt(0)>=maxChar.charAt(0))
									throw new Exception("Min("+minChar+") >= Max("+maxChar+")");
								if(maxChar.charAt(0)-minChar.charAt(0)==(char)1)
									throw new Exception(GAL_GUI.language.Errors[4]+minChar+".."+maxChar+GAL_GUI.language.Errors[5]);
								aux= new GAL_CharacterGeneConfig(nombres[i],minChar.charAt(0),maxChar.charAt(0));
							break;
							case 4:
								int size= valoresAllele2.getModel().getSize();
								if(size<2)
									throw new Exception(GAL_GUI.language.Errors[8]);
								String[] alelos= new String[size];
								for(int j=0;j<size;j++)
									alelos[j]= (String) valoresAllele2.getModel().getElementAt(i);
								aux= new GAL_NominalGeneConfig(nombres[i],alelos);
							break;
							default:
								aux= GAL_GUI.metadatas.gene_metadatas[tipo-5].geneConstructor(((JPanel)configuracionEspecifica.getComponent(tipo)).getComponents(), nombres[i]);
							break;
						}
						GAL_GUI.gal.addGeneConfig(aux,nombres[i],tipo);
						lm_VariablesDefinidas.addElement(nombres[i]);
					}
					editar= -1;
					GAL_GUI.gal.getInterpreter(0).reset(GAL_GUI.gal.getGeneNames());
					GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(240,240,240));
					try{
						GAL_GUI.gal.validateInitializer();
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[30]+"\n"+GAL_GUI.language.Errors[31]);
						GAL_GUI.gal.setInitializer();
						GAL_GUI.gal.resetParameters();
						GeneralWindow.pnl_ProgressConfig3.setBackground(new Color(240,240,240));
					}
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnAgregarvarios.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnAgregarvarios.setBounds(57, 221, 106, 23);
		Configuracion.add(btnAgregarvarios);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(2, 256, 432, 45);
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
		mnArchivo.add(mntmSalir);
	}
	
	public void actualizar(){
		editar= -1;
		lm_VariablesDefinidas.clear();
		for(String name: GAL_GUI.gal.getGeneNames())
			lm_VariablesDefinidas.addElement(name);
	}
}
