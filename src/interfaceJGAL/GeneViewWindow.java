package interfaceJGAL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;

import JGAL.GAL_BinaryGeneConfig;
import JGAL.GAL_CharacterGeneConfig;
import JGAL.GAL_DoubleGeneConfig;
import JGAL.GAL_GeneConfig;
import JGAL.GAL_IntegerGeneConfig;
import JGAL.GAL_NominalGeneConfig;

public class GeneViewWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public GeneViewWindow() {
				
		setTitle(GAL_GUI.language.progreso[0]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 442, 284);
		
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
		mnArchivo.add(mntmSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel Configuracion = new JPanel();
		Configuracion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Configuracion.setBounds(2, 0, 263, 231);
		contentPane.add(Configuracion);
		Configuracion.setLayout(null);
		
		final JPanel configuracionEspecifica = new JPanel();
		configuracionEspecifica.setBounds(5, 95, 250, 121);
		Configuracion.add(configuracionEspecifica);
		configuracionEspecifica.setLayout(new CardLayout(0, 0));
		
		JPanel conMinMax = new JPanel();
		configuracionEspecifica.add(conMinMax, "conMinMax");
		conMinMax.setLayout(null);
		
		JLabel lblMin = new JLabel(GAL_GUI.language.GeneConfiguration[9]);
		lblMin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMin.setBounds(10, 29, 35, 14);
		conMinMax.add(lblMin);
		
		JLabel lblMax = new JLabel(GAL_GUI.language.GeneConfiguration[10]);
		lblMax.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblMax.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMax.setBounds(10, 60, 35, 14);
		conMinMax.add(lblMax);
		
		JLabel lblConfiguracinAdicional = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
		lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional.setBounds(10, 4, 192, 14);
		conMinMax.add(lblConfiguracinAdicional);
		
		final JLabel lbl_MinReal = new JLabel("0");
		lbl_MinReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_MinReal.setBounds(57, 27, 52, 16);
		conMinMax.add(lbl_MinReal);
		
		final JLabel lbl_MaxReal = new JLabel("0");
		lbl_MaxReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_MaxReal.setBounds(57, 58, 52, 16);
		conMinMax.add(lbl_MaxReal);
		
		JPanel Nominal = new JPanel();
		configuracionEspecifica.add(Nominal, "Nominal");
		Nominal.setLayout(null);
		
		JLabel lblDatosAdicionales = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
		lblDatosAdicionales.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblDatosAdicionales.setBounds(10, 4, 129, 16);
		Nominal.add(lblDatosAdicionales);
		
		JLabel lblValores = new JLabel(GAL_GUI.language.GeneConfiguration[11]);
		lblValores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValores.setBounds(10, 25, 100, 14);
		Nominal.add(lblValores);
		
		final DefaultListModel<String> lm_ValoresAllele= new DefaultListModel<String>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 44, 93, 64);
		Nominal.add(scrollPane);
		final JList<String> valoresAllele2 = new JList<String>(lm_ValoresAllele);
		scrollPane.setViewportView(valoresAllele2);
		valoresAllele2.setFont(new Font("Segoe UI", Font.BOLD, 12));
		valoresAllele2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel Otros = new JPanel();
		configuracionEspecifica.add(Otros, "Otros");
		
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
		
		final JLabel lblNombreReal = new JLabel("");
		lblNombreReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lblNombreReal.setBounds(59, 34, 164, 16);
		Configuracion.add(lblNombreReal);
		
		final JLabel lbl_TipoReal = new JLabel("");
		lbl_TipoReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_TipoReal.setBounds(59, 65, 164, 16);
		Configuracion.add(lbl_TipoReal);
		
		JPanel Variables = new JPanel();
		Variables.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Variables.setBounds(266, 0, 168, 231);
		contentPane.add(Variables);
		Variables.setLayout(null);
		
		final DefaultListModel<String> lm_VariablesDefinidas= new DefaultListModel<String>();
		for(String name: GAL_GUI.gal.getGeneNames())
			lm_VariablesDefinidas.addElement(name);
		
		((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 32, 148, 184);
		Variables.add(scrollPane_1);
		final JList<String> variablesDefinidas = new JList<String>(lm_VariablesDefinidas);
		scrollPane_1.setViewportView(variablesDefinidas);
		variablesDefinidas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				@SuppressWarnings("unchecked")
				GAL_GeneConfig<?> aux= GAL_GUI.gal.getGeneConfig(((JList<String>)e.getSource()).getSelectedIndex());
				lblNombreReal.setText(aux.getName());
				if(aux instanceof GAL_BinaryGeneConfig){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
					lbl_TipoReal.setText(GAL_GUI.language.GeneConfiguration[5]);
				}else if(aux instanceof GAL_IntegerGeneConfig){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"conMinMax");
					lbl_TipoReal.setText(GAL_GUI.language.GeneConfiguration[3]);
					lbl_MinReal.setText(""+((GAL_IntegerGeneConfig) aux).getMin());
					lbl_MaxReal.setText(""+((GAL_IntegerGeneConfig) aux).getMax());
				}else if(aux instanceof GAL_DoubleGeneConfig){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"conMinMax");
					lbl_TipoReal.setText(GAL_GUI.language.GeneConfiguration[4]);
					lbl_MinReal.setText(""+((GAL_DoubleGeneConfig) aux).getMin());
					lbl_MaxReal.setText(""+((GAL_DoubleGeneConfig) aux).getMax());
				}else if(aux instanceof GAL_CharacterGeneConfig){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"conMinMax");
					lbl_TipoReal.setText(GAL_GUI.language.GeneConfiguration[6]);
					lbl_MinReal.setText(""+((GAL_CharacterGeneConfig) aux).getMin());
					lbl_MaxReal.setText(""+((GAL_CharacterGeneConfig) aux).getMax());
				}else if(aux instanceof GAL_NominalGeneConfig){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Nominal");
					lbl_TipoReal.setText(GAL_GUI.language.GeneConfiguration[7]);
					lm_ValoresAllele.clear();
					String[] alleles= ((GAL_NominalGeneConfig) aux).getAlleles();
					for(int i=0;i<alleles.length;i++)
						lm_ValoresAllele.addElement(alleles[i]);
				}
			}
		});
		
		variablesDefinidas.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		variablesDefinidas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblVariablesDefinidas = new JLabel(GAL_GUI.language.GeneConfiguration[13]);
		lblVariablesDefinidas.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblVariablesDefinidas.setBounds(10, 7, 143, 14);
		Variables.add(lblVariablesDefinidas);
		
	}
}
