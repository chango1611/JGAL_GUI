package interfaceJGAL;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import JGAL.GAL_Chromosome;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class ResultsViewWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private GAL_Chromosome bestFrom;

	/**
	 * Create the frame.
	 */
	public ResultsViewWindow() {
		final DecimalFormat df= new DecimalFormat("#.####");
		final boolean optimizationType= GAL_GUI.gal.optimizationType();
		setTitle(GAL_GUI.language.progreso[9]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 640);
		
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
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(0, 284, 495, 307);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		final JPanel pnl_Grafica = new LineGraph(GAL_GUI.gal.getArrayOfBestFitness(),GAL_GUI.gal.getArrayOfAverageFitness());
		pnl_Grafica.setBounds(10, 11, 475, 285);
		panel_1.add(pnl_Grafica);
		pnl_Grafica.setBackground(Color.WHITE);
		pnl_Grafica.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel pnl_info = new JPanel();
		pnl_info.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		pnl_info.setBounds(0, 0, 494, 283);
		contentPane.add(pnl_info);
		pnl_info.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 26, 474, 55);
		pnl_info.add(scrollPane);
		
		GAL_Chromosome bestChrom= GAL_GUI.gal.getBestChromosome();
		Object[] bestTraits= new Object[bestChrom.size()];
		int i;
		for(i=0;i<bestTraits.length;i++)
			bestTraits[i]= bestChrom.getTrait(i);
		table = new JTable();
		table.setRowSelectionAllowed(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setModel(new DefaultTableModel(
			new Object[][] {
				bestTraits
			},
			GAL_GUI.gal.getGeneNames()
		) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		for(i=0;i<table.getColumnCount();i++){
			table.getColumnModel().getColumn(i).setResizable(false);
			table.getColumnModel().getColumn(i).setMinWidth(70);
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		
		JLabel lblMejorCromosoma = new JLabel(GAL_GUI.language.Results[0]);
		lblMejorCromosoma.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblMejorCromosoma.setBounds(10, 11, 474, 14);
		pnl_info.add(lblMejorCromosoma);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBounds(10, 125, 474, 150);
		pnl_info.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblGeneracin = new JLabel(GAL_GUI.language.Results[6]);
		lblGeneracin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblGeneracin.setBounds(298, 127, 86, 14);
		panel_2.add(lblGeneracin);
		lblGeneracin.setFont(new Font("Segoe UI", Font.BOLD, 11));
		
		int lastGen= GAL_GUI.gal.getLastGeneration()-1;
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(10, 31, 454, 55);
		panel_2.add(scrollPane_1);
		
		bestFrom= GAL_GUI.gal.getBestChromosomeFrom(lastGen);
		bestTraits= new Object[bestFrom.size()];
		for(i=0;i<bestTraits.length;i++)
			bestTraits[i]= bestFrom.getTrait(i);
		table_1 = new JTable();
		table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table_1.setRowSelectionAllowed(false);
		table_1.setModel(new DefaultTableModel(
			new Object[][] {
				bestTraits
			},
			GAL_GUI.gal.getGeneNames()
		) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		for(i=0;i<table_1.getColumnCount();i++){
			table_1.getColumnModel().getColumn(i).setResizable(false);
			table_1.getColumnModel().getColumn(i).setMinWidth(70);
			table_1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
		scrollPane_1.setViewportView(table_1);
		
		JLabel lblMejorCromosomaDe = new JLabel(GAL_GUI.language.Results[0] + GAL_GUI.language.Results[3]);
		lblMejorCromosomaDe.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblMejorCromosomaDe.setBounds(10, 11, 454, 14);
		panel_2.add(lblMejorCromosomaDe);
		
		JLabel lblAptitud = new JLabel(GAL_GUI.language.Results[1] + GAL_GUI.language.Results[3] + ":");
		lblAptitud.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblAptitud.setBounds(10, 90, 220, 14);
		panel_2.add(lblAptitud);
		
		JLabel lblPromedioDeAptitudes = new JLabel(GAL_GUI.language.Results[4] + GAL_GUI.language.Results[3] + ":");
		lblPromedioDeAptitudes.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblPromedioDeAptitudes.setBounds(10, 110, 220, 14);
		panel_2.add(lblPromedioDeAptitudes);
		
		final JLabel lbl_MejorAptitudGeneracionReal;
		if(optimizationType)
			lbl_MejorAptitudGeneracionReal = new JLabel(""+df.format(bestFrom.getFitness()));
		else
			lbl_MejorAptitudGeneracionReal = new JLabel(""+df.format(1000000000 - bestFrom.getFitness()));
		lbl_MejorAptitudGeneracionReal.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lbl_MejorAptitudGeneracionReal.setBounds(242, 90, 200, 14);
		panel_2.add(lbl_MejorAptitudGeneracionReal);
		
		final JLabel lbl_PromedioAptitudGeneracionReal = new JLabel(""+df.format(GAL_GUI.gal.getAverageFitness(lastGen)));
		lbl_PromedioAptitudGeneracionReal.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lbl_PromedioAptitudGeneracionReal.setBounds(242, 110, 200, 14);
		panel_2.add(lbl_PromedioAptitudGeneracionReal);
		
		JLabel lblMejorAptitud = new JLabel(GAL_GUI.language.Results[1] + ":");
		lblMejorAptitud.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblMejorAptitud.setBounds(10, 85, 220, 14);
		pnl_info.add(lblMejorAptitud);
		
		final JLabel lbl_MejorAptitudReal;
		if(optimizationType)
			lbl_MejorAptitudReal = new JLabel(""+df.format(bestChrom.getFitness()));
		else
			lbl_MejorAptitudReal = new JLabel(""+df.format(1000000000 - bestChrom.getFitness()));
		
		lbl_MejorAptitudReal.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lbl_MejorAptitudReal.setBounds(252, 85, 200, 14);
		pnl_info.add(lbl_MejorAptitudReal);
		
		JLabel lblPromedioDeTodas = new JLabel(GAL_GUI.language.Results[2] + ":");
		lblPromedioDeTodas.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lblPromedioDeTodas.setBounds(10, 105, 220, 14);
		pnl_info.add(lblPromedioDeTodas);
		
		final JLabel lbl_PromedioAptitudReal= new JLabel(""+df.format(GAL_GUI.gal.getAverageFitness()));
		lbl_PromedioAptitudReal.setFont(new Font("Segoe UI", Font.BOLD, 11));
		lbl_PromedioAptitudReal.setBounds(252, 105, 200, 14);
		pnl_info.add(lbl_PromedioAptitudReal);
		
		JSpinner spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int gen= (int)((JSpinner)e.getSource()).getValue(), i;
				//Selecciono el mejor de la generación seleccionada
				bestFrom= GAL_GUI.gal.getBestChromosomeFrom(gen);
				
				//Vuelvo a crear la tabla
				Object[] bestTraits= new Object[bestFrom.size()];
				for(i=0;i<bestTraits.length;i++)
					bestTraits[i]= bestFrom.getTrait(i);
				table_1.setModel(new DefaultTableModel(
					new Object[][] {
						bestTraits
					},
					GAL_GUI.gal.getGeneNames()
				) {
					private static final long serialVersionUID = 1L;
					public boolean isCellEditable(int row, int column) {
						return false;
					}
				});
				for(i=0;i<table_1.getColumnCount();i++){
					table_1.getColumnModel().getColumn(i).setResizable(false);
					table_1.getColumnModel().getColumn(i).setMinWidth(70);
					table_1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
				}
				
				//Asigno las variables de la generaion
				if(optimizationType)
					lbl_MejorAptitudGeneracionReal.setText(""+df.format(bestFrom.getFitness()));
				else
					lbl_MejorAptitudGeneracionReal.setText(""+df.format(1000000000 - bestFrom.getFitness()));
				
				lbl_PromedioAptitudGeneracionReal.setText(""+df.format(GAL_GUI.gal.getAverageFitness(gen)));
			}
		});
		spinner.setModel(new SpinnerNumberModel(lastGen, 0, lastGen, 1));
		spinner.setBounds(394, 124, 70, 20);
		panel_2.add(spinner);
		spinner.setFont(new Font("Tahoma", Font.PLAIN, 11));
	}
}
