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
import java.awt.Font;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import JGAL.GAL_ChromosomeMutation;
import JGAL.GAL_ClassicRankingSelector;
import JGAL.GAL_GeneticOperator;
import JGAL.GAL_LinealRankingSelector;
import JGAL.GAL_MultiPointCrossover;
import JGAL.GAL_NaturalSelector;
import JGAL.GAL_NonLinealRankingSelector;
import JGAL.GAL_RouletteSelector;
import JGAL.GAL_SegmentCrossover;
import JGAL.GAL_TournamentSelector;
import JGAL.GAL_UniformCrossover;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

public class ConfigurationViewWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ConfigurationViewWindow() {
		final DecimalFormat df= new DecimalFormat("#.####");
		
		setTitle(GAL_GUI.language.progreso[5]);
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
		
		JPanel Selector = new JPanel();
		tabbedPane.addTab(GAL_GUI.language.botonesPrincipales[4], null, Selector, null);
		Selector.setLayout(null);
		
		JLabel label = new JLabel(GAL_GUI.language.SelectorConfiguration[0]);
		label.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label.setBounds(19, 13, 180, 14);
		Selector.add(label);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 68, 405, 2);
		Selector.add(separator_1);
		
		final JLabel lbl_SelectorReal = new JLabel("");
		lbl_SelectorReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_SelectorReal.setBounds(19, 39, 398, 16);
		Selector.add(lbl_SelectorReal);
		
		final JPanel ConfigAdicional = new JPanel();
		ConfigAdicional.setBounds(19, 83, 398, 113);
		Selector.add(ConfigAdicional);
		ConfigAdicional.setLayout(new CardLayout(0, 0));
		
		JPanel Ruleta = new JPanel();
		Ruleta.setLayout(null);
		ConfigAdicional.add(Ruleta, "Ruleta");
		
		JPanel Torneo = new JPanel();
		Torneo.setLayout(null);
		ConfigAdicional.add(Torneo, "Torneo");
		
		JLabel label_5 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setBounds(0, 0, 158, 14);
		Torneo.add(label_5);
		
		JLabel label_6 = new JLabel(GAL_GUI.language.SelectorConfiguration[7]);
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_6.setBounds(0, 27, 104, 14);
		Torneo.add(label_6);
		
		final JLabel lbl_TorneoReal = new JLabel("0");
		lbl_TorneoReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_TorneoReal.setBounds(115, 25, 70, 16);
		Torneo.add(lbl_TorneoReal);
		
		JPanel RankingClasico = new JPanel();
		RankingClasico.setLayout(null);
		ConfigAdicional.add(RankingClasico, "Rank Clasico");
		
		JLabel label_7 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_7.setBounds(0, 0, 175, 14);
		RankingClasico.add(label_7);
		
		JLabel label_8 = new JLabel(GAL_GUI.language.SelectorConfiguration[8]);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_8.setBounds(0, 27, 205, 14);
		RankingClasico.add(label_8);
		
		final JLabel lbl_RankClasicoReal = new JLabel("");
		lbl_RankClasicoReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_RankClasicoReal.setBounds(190, 25, 70, 16);
		RankingClasico.add(lbl_RankClasicoReal);
		
		JPanel OtrosRankings = new JPanel();
		OtrosRankings.setLayout(null);
		ConfigAdicional.add(OtrosRankings, "Rank Otros");
		
		JLabel label_9 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_9.setBounds(0, 0, 154, 14);
		OtrosRankings.add(label_9);
		
		JLabel label_10 = new JLabel(GAL_GUI.language.SelectorConfiguration[9]);
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_10.setBounds(0, 31, 95, 14);
		OtrosRankings.add(label_10);
		
		final JLabel lbl_RankingReal = new JLabel("");
		lbl_RankingReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_RankingReal.setBounds(65, 29, 70, 16);
		OtrosRankings.add(lbl_RankingReal);
		
		JCheckBox chckbxElitismo = new JCheckBox(GAL_GUI.language.SelectorConfiguration[10]);
		chckbxElitismo.setEnabled(false);
		chckbxElitismo.setFont(new Font("Segoe UI", Font.BOLD, 12));
		chckbxElitismo.setBounds(19, 203, 138, 23);
		chckbxElitismo.setSelected(GAL_GUI.gal.isElitist());
		Selector.add(chckbxElitismo);
		
		JPanel Elitismo = new JPanel();
		Elitismo.setLayout(null);
		Elitismo.setBounds(163, 202, 256, 24);
		Elitismo.setVisible(GAL_GUI.gal.isElitist());
		Selector.add(Elitismo);
		
		JLabel label_14 = new JLabel(""+GAL_GUI.gal.getElitistSize());
		label_14.setHorizontalAlignment(SwingConstants.RIGHT);
		label_14.setFont(new Font("Segoe UI", Font.BOLD, 12));
		label_14.setBounds(183, 4, 63, 14);
		Elitismo.add(label_14);
		
		JLabel lbl_Elitismo = new JLabel(GAL_GUI.language.SelectorConfiguration[11]);
		lbl_Elitismo.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_Elitismo.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_Elitismo.setBounds(10, 4, 163, 14);
		Elitismo.add(lbl_Elitismo);
		
		JPanel OperadoresGeneticos = new JPanel();
		tabbedPane.addTab(GAL_GUI.language.botonesPrincipales[5], null, OperadoresGeneticos, null);
		OperadoresGeneticos.setLayout(null);
		
		JPanel Configuracion = new JPanel();
		Configuracion.setBounds(2, 0, 263, 225);
		OperadoresGeneticos.add(Configuracion);
		Configuracion.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
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
		
		JLabel lblConfiguracinAdicional = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblConfiguracinAdicional.setBounds(10, 4, 192, 14);
		CruceMultiPuntos.add(lblConfiguracinAdicional);
		
		final JLabel lbl_MultiReal = new JLabel("0");
		lbl_MultiReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_MultiReal.setBounds(110, 27, 52, 16);
		CruceMultiPuntos.add(lbl_MultiReal);
		
		JPanel Otros = new JPanel();
		configuracionEspecifica.add(Otros, "Otros");
		
		JPanel CruceUniforme = new JPanel();
		CruceUniforme.setLayout(null);
		configuracionEspecifica.add(CruceUniforme, "Uniforme");
		
		JLabel lblProbDeCambio = new JLabel(GAL_GUI.language.OperatorsConfiguration[16]);
		lblProbDeCambio.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbDeCambio.setBounds(10, 29, 107, 14);
		CruceUniforme.add(lblProbDeCambio);
		
		JLabel label_1 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_1.setBounds(10, 4, 192, 14);
		CruceUniforme.add(label_1);
		
		final JLabel lbl_UniformeReal = new JLabel("0");
		lbl_UniformeReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_UniformeReal.setBounds(110, 27, 52, 16);
		CruceUniforme.add(lbl_UniformeReal);
		
		JPanel CruceSegmentos = new JPanel();
		CruceSegmentos.setLayout(null);
		configuracionEspecifica.add(CruceSegmentos, "Segmentos");
		
		JLabel lblProbSegmentos = new JLabel(GAL_GUI.language.OperatorsConfiguration[17]);
		lblProbSegmentos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbSegmentos.setBounds(10, 29, 107, 14);
		CruceSegmentos.add(lblProbSegmentos);
		
		JLabel label_2 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_2.setBounds(10, 4, 192, 14);
		CruceSegmentos.add(label_2);
		
		final JLabel lbl_SegmentoReal = new JLabel("0");
		lbl_SegmentoReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_SegmentoReal.setBounds(110, 27, 52, 16);
		CruceSegmentos.add(lbl_SegmentoReal);
		
		JPanel MutacionCromosoma = new JPanel();
		MutacionCromosoma.setLayout(null);
		configuracionEspecifica.add(MutacionCromosoma, "Mut Crom");
		
		JLabel lblProbDeMutacin = new JLabel(GAL_GUI.language.OperatorsConfiguration[18]);
		lblProbDeMutacin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblProbDeMutacin.setBounds(10, 29, 107, 14);
		MutacionCromosoma.add(lblProbDeMutacin);
		
		JLabel label_3 = new JLabel(GAL_GUI.language.SelectorConfiguration[6]);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(10, 4, 192, 14);
		MutacionCromosoma.add(label_3);
		
		final JLabel lbl_MutCromReal = new JLabel("0");
		lbl_MutCromReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_MutCromReal.setBounds(110, 27, 52, 16);
		MutacionCromosoma.add(lbl_MutCromReal);
		
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
		
		final JLabel lbl_OperadorReal = new JLabel("");
		lbl_OperadorReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_OperadorReal.setBounds(84, 33, 171, 16);
		Configuracion.add(lbl_OperadorReal);
		
		final JLabel lbl_ProbReal = new JLabel("");
		lbl_ProbReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_ProbReal.setBounds(129, 60, 52, 16);
		Configuracion.add(lbl_ProbReal);
		
		JPanel Operadores = new JPanel();
		Operadores.setBounds(266, 0, 168, 225);
		OperadoresGeneticos.add(Operadores);
		Operadores.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Operadores.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 148, 184);
		Operadores.add(scrollPane);
		final JList<String> operadoresDefinidos = new JList<String>(lm_OperadoresDefinidos);
		operadoresDefinidos.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unchecked")
			public void valueChanged(ListSelectionEvent e) {
				GAL_GeneticOperator aux= GAL_GUI.gal.getOperator(((JList<String>)e.getSource()).getSelectedIndex());
				lbl_OperadorReal.setText((String)((JList<String>)e.getSource()).getSelectedValue());
				lbl_ProbReal.setText(""+df.format(aux.getProb()));
				if(aux instanceof GAL_MultiPointCrossover){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Multi");
					lbl_MultiReal.setText(""+((GAL_MultiPointCrossover) aux).getNumberOfPoints());
				}else if(aux instanceof GAL_UniformCrossover){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Uniforme");
					lbl_UniformeReal.setText(""+df.format(((GAL_UniformCrossover) aux).getUniformProb()));
				}else if(aux instanceof GAL_SegmentCrossover){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Segmentos");
					lbl_SegmentoReal.setText(""+df.format(((GAL_SegmentCrossover) aux).getSegmetChangeProb()));
				}else if(aux instanceof GAL_ChromosomeMutation){
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Mut Crom");
					lbl_MutCromReal.setText(""+df.format(((GAL_ChromosomeMutation) aux).getSecondProb()));
				}else{
					((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
				}
			}
		});
		((CardLayout)configuracionEspecifica.getLayout()).show(configuracionEspecifica,"Otros");
		
		scrollPane.setViewportView(operadoresDefinidos);
		operadoresDefinidos.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		operadoresDefinidos.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblOperadores = new JLabel(GAL_GUI.language.OperatorsConfiguration[20]);
		lblOperadores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOperadores.setBounds(10, 7, 143, 14);
		Operadores.add(lblOperadores);
		
		JPanel Parametros = new JPanel();
		tabbedPane.addTab(GAL_GUI.language.botonesPrincipales[6], null, Parametros, null);
		Parametros.setLayout(null);
		
		JLabel label_4 = new JLabel(GAL_GUI.language.ParametersConfiguration[4]);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(12, 94, 161, 14);
		Parametros.add(label_4);
		
		JLabel label_11 = new JLabel(GAL_GUI.language.ParametersConfiguration[3]);
		label_11.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_11.setBounds(12, 67, 150, 14);
		Parametros.add(label_11);
		
		JLabel label_12 = new JLabel(GAL_GUI.language.ParametersConfiguration[0]);
		label_12.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_12.setBounds(12, 13, 66, 14);
		Parametros.add(label_12);
		
		final JLabel lbl_HandlerReal = new JLabel("");
		lbl_HandlerReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_HandlerReal.setBounds(197, 12, 161, 16);
		Parametros.add(lbl_HandlerReal);
		
		final JLabel lbl_popsizeReal = new JLabel("");
		lbl_popsizeReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_popsizeReal.setBounds(215, 65, 70, 16);
		Parametros.add(lbl_popsizeReal);
		
		final JLabel lbl_maxGenReal = new JLabel("");
		lbl_maxGenReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_maxGenReal.setBounds(215, 92, 70, 16);
		Parametros.add(lbl_maxGenReal);
		
		final JPanel pnl_modificado = new JPanel();
		pnl_modificado.setBounds(12, 121, 319, 28);
		Parametros.add(pnl_modificado);
		pnl_modificado.setLayout(null);
		
		JLabel lblNmeroDePadres = new JLabel(GAL_GUI.language.ParametersConfiguration[5]);
		lblNmeroDePadres.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNmeroDePadres.setBounds(0, 0, 180, 16);
		pnl_modificado.add(lblNmeroDePadres);
		
		final JLabel lbl_ModReal = new JLabel("");
		lbl_ModReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_ModReal.setBounds(203, -2, 70, 16);
		pnl_modificado.add(lbl_ModReal);
		
		JLabel label_13 = new JLabel(GAL_GUI.language.ParametersConfiguration[6]);
		label_13.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_13.setBounds(10, 42, 66, 14);
		Parametros.add(label_13);
		
		final JLabel lbl_InitializerReal = new JLabel("");
		lbl_InitializerReal.setFont(new Font("Segoe UI", Font.BOLD, 12));
		lbl_InitializerReal.setBounds(197, 39, 161, 16);
		Parametros.add(lbl_InitializerReal);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				GAL_NaturalSelector aux= GAL_GUI.gal.getSelector();
				if(aux!=null){
					lbl_SelectorReal.setText(GAL_GUI.gal.getSelectorName());
					if(aux instanceof GAL_RouletteSelector){
						((CardLayout)ConfigAdicional.getLayout()).show(ConfigAdicional,"Ruleta");
					}else if(aux instanceof GAL_TournamentSelector){
						((CardLayout)ConfigAdicional.getLayout()).show(ConfigAdicional,"Torneo");
						lbl_TorneoReal.setText(""+((GAL_TournamentSelector) aux).getTournamentSize());
					}else if(aux instanceof GAL_ClassicRankingSelector){
						((CardLayout)ConfigAdicional.getLayout()).show(ConfigAdicional,"Rank Clasico");
						lbl_RankClasicoReal.setText(""+((GAL_ClassicRankingSelector) aux).getMax());
					}else if(aux instanceof GAL_LinealRankingSelector){
						((CardLayout)ConfigAdicional.getLayout()).show(ConfigAdicional,"Rank Otros");
						lbl_RankingReal.setText(""+df.format(((GAL_LinealRankingSelector) aux).getQ()));
					}else if(aux instanceof GAL_NonLinealRankingSelector){
						((CardLayout)ConfigAdicional.getLayout()).show(ConfigAdicional,"Rank Otros");
						lbl_RankingReal.setText(""+df.format(((GAL_NonLinealRankingSelector) aux).getQ()));
					}
				}
				if(GAL_GUI.gal.parametersAssigned()){
					lbl_popsizeReal.setText(""+GAL_GUI.gal.getParameter("popSize"));
					lbl_maxGenReal.setText(""+GAL_GUI.gal.getParameter("maxGen"));
					if(GAL_GUI.gal.getParameter("handlerToUse")==0){
						lbl_HandlerReal.setText(GAL_GUI.language.ParametersConfiguration[1]);
						pnl_modificado.setVisible(false);
					}else{
						lbl_HandlerReal.setText(GAL_GUI.language.ParametersConfiguration[2]);
						pnl_modificado.setVisible(true);
						lbl_ModReal.setText("" + GAL_GUI.gal.getParameter("modParam"));
					}
					if(GAL_GUI.gal.getParameter("initializerToUse")==0)
						lbl_InitializerReal.setText(GAL_GUI.language.ParametersConfiguration[1]);
					else
						lbl_InitializerReal.setText(GAL_GUI.language.ParametersConfiguration[7]);
				}
			}
		});
	}
}
