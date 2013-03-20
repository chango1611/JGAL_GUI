package interfaceJGAL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

import JGAL.GAL_ClassicRankingSelector;
import JGAL.GAL_LinealRankingSelector;
import JGAL.GAL_NaturalSelector;
import JGAL.GAL_NonLinealRankingSelector;
import JGAL.GAL_RouletteSelector;
import JGAL.GAL_TournamentSelector;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SelectorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> cb_Selector;
	private JPanel configuracionEspecifica;
	private JSpinner spn_Torneo;
	private JSpinner spn_Clasico;
	private JSpinner spn_Lineal;
	private JSpinner spn_NoLineal;
	
	/**
	 * Create the frame.
	 */
	public SelectorWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				actualizar();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				if(GAL_GUI.gal.getSelector()!=null)
					GeneralWindow.pnl_ProgressConfig1.setBackground(new Color(255, 50, 0));
			}
		});
		setTitle(GAL_GUI.language.botonesPrincipales[4]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 360, 265);
		
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
					int returnVal= fc.showOpenDialog(SelectorWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.openSelector(fc.getSelectedFile());
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
				int returnVal= fc.showSaveDialog(SelectorWindow.this);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					//Guardar Selector en gal primero
					int tipo= cb_Selector.getSelectedIndex();
					try{
						GAL_GUI.gal.setSelector(createSelector(tipo));
					}catch(Exception ex){
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					//Guardar en archivo
					GAL_GUI.gal.saveSelector(fc.getSelectedFile());
				}
			}
		});
		mntmGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmGuardar);
		mntmSalir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmSalir);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		mnAyuda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnAyuda);
		
		JMenuItem mntmTutorial = new JMenuItem("Tutorial");
		mntmTutorial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnAyuda.add(mntmTutorial);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSelectorNatural = new JLabel(GAL_GUI.language.SelectorConfiguration[0]);
		lblSelectorNatural.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelectorNatural.setBounds(12, 13, 180, 14);
		contentPane.add(lblSelectorNatural);
		
		configuracionEspecifica = new JPanel();
		configuracionEspecifica.setBounds(12, 78, 327, 96);
		contentPane.add(configuracionEspecifica);
		configuracionEspecifica.setLayout(new CardLayout(0, 0));
		
		cb_Selector = new JComboBox<String>();
		cb_Selector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				int option= ((JComboBox<String>) e.getSource()).getSelectedIndex();
				switch(option){
					case 0:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Ruleta");
					break;
					case 1:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Torneo");
					break;
					case 2:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Rank Clasico");
					break;
					case 3:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Rank Lineal");
					break;
					case 4:
						((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Rank No Lineal");
					break;
				}
			}
		});
		cb_Selector.setFont(new Font("Tahoma", Font.PLAIN, 11));
		String[] tipos= new String[5];
		System.arraycopy(GAL_GUI.language.SelectorConfiguration, 1, tipos, 0, 5);
		cb_Selector.setModel(new DefaultComboBoxModel<String>(tipos));
		cb_Selector.setSelectedIndex(0);
		cb_Selector.setBounds(12, 35, 327, 20);
		contentPane.add(cb_Selector);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(5, 68, 345, 2);
		contentPane.add(separator);
		
		JPanel Ruleta = new JPanel();
		configuracionEspecifica.add(Ruleta, "Ruleta");
		Ruleta.setLayout(null);
		
		JPanel Torneo = new JPanel();
		configuracionEspecifica.add(Torneo, "Torneo");
		Torneo.setLayout(null);
		
		JLabel lblConfiguracinAdicional = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional.setBounds(0, 0, 158, 14);
		Torneo.add(lblConfiguracinAdicional);
		
		JLabel lblTamaoDelTorneo = new JLabel(GAL_GUI.language.SelectorConfiguration[7]);
		lblTamaoDelTorneo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTamaoDelTorneo.setBounds(0, 27, 115, 14);
		Torneo.add(lblTamaoDelTorneo);
		
		spn_Torneo = new JSpinner();
		spn_Torneo.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spn_Torneo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_Torneo.setBounds(0, 46, 70, 20);
		Torneo.add(spn_Torneo);
		
		JPanel RankingClasico = new JPanel();
		configuracionEspecifica.add(RankingClasico, "Rank Clasico");
		RankingClasico.setLayout(null);
		
		JLabel lblConfiguracinAdicional_1 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		lblConfiguracinAdicional_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional_1.setBounds(0, 0, 175, 14);
		RankingClasico.add(lblConfiguracinAdicional_1);
		
		JLabel lblNmeroEsperadoDe = new JLabel(GAL_GUI.language.SelectorConfiguration[8]);
		lblNmeroEsperadoDe.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNmeroEsperadoDe.setBounds(0, 27, 205, 14);
		RankingClasico.add(lblNmeroEsperadoDe);
		
		spn_Clasico = new JSpinner();
		spn_Clasico.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spn_Clasico.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_Clasico.setBounds(0, 46, 70, 20);
		RankingClasico.add(spn_Clasico);
		
		JPanel RankingLineal = new JPanel();
		configuracionEspecifica.add(RankingLineal, "Rank Lineal");
		RankingLineal.setLayout(null);
		
		JLabel lblConfiguracinAdicional_2 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		lblConfiguracinAdicional_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional_2.setBounds(0, 0, 154, 14);
		RankingLineal.add(lblConfiguracinAdicional_2);
		
		JLabel lblValorDeQ = new JLabel(GAL_GUI.language.SelectorConfiguration[9]);
		lblValorDeQ.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblValorDeQ.setBounds(0, 27, 95, 14);
		RankingLineal.add(lblValorDeQ);
		
		spn_Lineal = new JSpinner();
		spn_Lineal.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		spn_Lineal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_Lineal.setBounds(0, 46, 70, 20);
		RankingLineal.add(spn_Lineal);
		
		JPanel RankingNoLineal = new JPanel();
		RankingNoLineal.setLayout(null);
		configuracionEspecifica.add(RankingNoLineal, "Rank No Lineal");
		
		JLabel label = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(0, 0, 154, 14);
		RankingNoLineal.add(label);
		
		JLabel label_1 = new JLabel(GAL_GUI.language.SelectorConfiguration[9]);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(0, 27, 95, 14);
		RankingNoLineal.add(label_1);
		
		spn_NoLineal = new JSpinner();
		spn_NoLineal.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.05));
		spn_NoLineal.setFont(new Font("Tahoma", Font.PLAIN, 11));
		spn_NoLineal.setBounds(0, 46, 70, 20);
		RankingNoLineal.add(spn_NoLineal);
				
		JButton btnAceptar = new JButton(GAL_GUI.language.CommonWords[0]);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int tipo= cb_Selector.getSelectedIndex();
				try{
					GAL_GUI.gal.setSelector(createSelector(tipo));
					dispose();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAceptar.setBounds(126, 185, 89, 23);
		contentPane.add(btnAceptar);
	}

	public void actualizar(){
		GAL_NaturalSelector selector= GAL_GUI.gal.getSelector();
		if(selector!=null){
			if(selector instanceof GAL_RouletteSelector)
				;
			else if(selector instanceof GAL_TournamentSelector){
				cb_Selector.setSelectedIndex(1);
				((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Torneo");
				spn_Torneo.setValue(((GAL_TournamentSelector) selector).getTournamentSize());
			}else if(selector instanceof GAL_ClassicRankingSelector){
				cb_Selector.setSelectedIndex(2);
				((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Rank Clasico");
				spn_Clasico.setValue(((GAL_ClassicRankingSelector) selector).getMax());
			}else if(selector instanceof GAL_LinealRankingSelector){
				cb_Selector.setSelectedIndex(3);
				((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Rank Lineal");
				spn_Lineal.setValue(((GAL_LinealRankingSelector) selector).getQ());
			}else if(selector instanceof GAL_NonLinealRankingSelector){
				cb_Selector.setSelectedIndex(4);
				((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Rank No Lineal");
				spn_NoLineal.setValue(((GAL_NonLinealRankingSelector) selector).getQ());
			}
		}
	}
	
	public GAL_NaturalSelector createSelector(int tipo)throws Exception{
		GAL_NaturalSelector aux= new GAL_RouletteSelector();
		switch(tipo){
			case 0:
				;
			break;
			case 1:
				aux= new GAL_TournamentSelector((int)spn_Torneo.getValue());
			break;
			case 2:
				aux= new GAL_ClassicRankingSelector((int)spn_Clasico.getValue());
			break;
			case 3:
				aux= new GAL_LinealRankingSelector((double)spn_Lineal.getValue());
			break;
			case 4:
				aux= new GAL_NonLinealRankingSelector((double)spn_NoLineal.getValue());
			break;
		}
		return aux;
	}
}
