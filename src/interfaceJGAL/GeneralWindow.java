package interfaceJGAL;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.CardLayout;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneralWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel real_interface;
	static JPanel pnl_ProgressCromosoma;
	static JPanel pnl_ProgressFuncion1;
	static JPanel pnl_ProgressFuncion2;
	static JPanel pnl_ProgressConfig1;
	static JPanel pnl_ProgressConfig2;
	static JPanel pnl_ProgressConfig3;
	static JPanel pnl_ProgressResultados;
	
	/**
	 * Create the frame.
	 */
	public GeneralWindow() {
		setResizable(false);
		setTitle("JGAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel buttons = new JPanel();
		buttons.setBounds(5, 5, 144, 430);
		
		JPanel progress = new JPanel();
		progress.setBounds(5, 446, 779, 101);
		progress.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 2), GAL_GUI.language.progreso[11], TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial",Font.BOLD,12), new Color(0, 0, 0)));
		
		JButton btnVerCromosoma = new JButton(GAL_GUI.language.progreso[0]);
		btnVerCromosoma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GeneViewWindow Newframe= new GeneViewWindow();
				Newframe.setVisible(true);
			}
		});
		btnVerCromosoma.setBounds(58, 18, 120, 23);
		btnVerCromosoma.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		pnl_ProgressCromosoma = new JPanel();
		pnl_ProgressCromosoma.setBounds(114, 46, 16, 16);
		pnl_ProgressCromosoma.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton btnVerFuncion = new JButton(GAL_GUI.language.progreso[2]);
		btnVerFuncion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FunctionViewWindow Newframe= new FunctionViewWindow();
				Newframe.setVisible(true);
			}
		});
		btnVerFuncion.setBounds(237, 18, 120, 23);
		btnVerFuncion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		pnl_ProgressFuncion1 = new JPanel();
		pnl_ProgressFuncion1.setBounds(282, 46, 16, 16);
		pnl_ProgressFuncion1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton btnVerConfiguracion = new JButton(GAL_GUI.language.progreso[5]);
		btnVerConfiguracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigurationViewWindow Newframe= new ConfigurationViewWindow();
				Newframe.setVisible(true);
			}
		});
		btnVerConfiguracion.setBounds(431, 18, 120, 23);
		btnVerConfiguracion.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		pnl_ProgressConfig1 = new JPanel();
		pnl_ProgressConfig1.setBounds(464, 46, 16, 16);
		pnl_ProgressConfig1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		pnl_ProgressConfig2 = new JPanel();
		pnl_ProgressConfig2.setBounds(485, 46, 16, 16);
		pnl_ProgressConfig2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		pnl_ProgressFuncion2 = new JPanel();
		pnl_ProgressFuncion2.setBounds(303, 46, 16, 16);
		pnl_ProgressFuncion2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		pnl_ProgressConfig3 = new JPanel();
		pnl_ProgressConfig3.setBounds(506, 46, 16, 16);
		pnl_ProgressConfig3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton btnVerResultados = new JButton(GAL_GUI.language.progreso[9]);
		btnVerResultados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!GAL_GUI.gal.executed())
					JOptionPane.showMessageDialog(GeneralWindow.this, GAL_GUI.language.Errors[21]);
				else{
					ResultsViewWindow Newframe= new ResultsViewWindow();
					Newframe.setVisible(true);
				}
			}
		});
		btnVerResultados.setBounds(612, 18, 120, 23);
		btnVerResultados.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		pnl_ProgressResultados = new JPanel();
		pnl_ProgressResultados.setBounds(658, 46, 16, 16);
		pnl_ProgressResultados.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblSelector = new JLabel(GAL_GUI.language.progreso[6]);
		lblSelector.setBounds(420, 48, 39, 14);
		lblSelector.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSelector.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblparametros = new JLabel(GAL_GUI.language.progreso[8]);
		lblparametros.setBounds(527, 46, 55, 14);
		lblparametros.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblOperadores = new JLabel(GAL_GUI.language.progreso[7]+" |");
		lblOperadores.setHorizontalAlignment(SwingConstants.RIGHT);
		lblOperadores.setBounds(412, 70, 83, 14);
		lblOperadores.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblTerminacin = new JLabel(GAL_GUI.language.progreso[4]);
		lblTerminacin.setBounds(324, 48, 57, 14);
		lblTerminacin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblAptitud = new JLabel(GAL_GUI.language.progreso[3]);
		lblAptitud.setBounds(242, 48, 35, 14);
		lblAptitud.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblVariables = new JLabel(GAL_GUI.language.progreso[1]);
		lblVariables.setBounds(66, 46, 43, 14);
		lblVariables.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblresultados = new JLabel(GAL_GUI.language.progreso[10]);
		lblresultados.setBounds(679, 48, 53, 14);
		lblresultados.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		final JPanel cardPanel = new JPanel();
		cardPanel.setBounds(154, 5, 630, 435);
		cardPanel.setLayout(new CardLayout(0, 0));
		cardPanel.add(new EsquemaDeTrabajo(), GAL_GUI.language.casosDeUso[0]);
		cardPanel.add(new DefinirCromosoma(), GAL_GUI.language.casosDeUso[1]);
		cardPanel.add(new DefinirFuncion(), GAL_GUI.language.casosDeUso[2]);
		cardPanel.add(new ConfigurarAlgoritmo(), GAL_GUI.language.casosDeUso[3]);
		cardPanel.add(new EjecutarAlgoritmo(), GAL_GUI.language.casosDeUso[4]);
		
		JButton btnSalir = new JButton(GAL_GUI.language.casosDeUso[5]);
		btnSalir.setBounds(3, 370, 141, 30);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnEsquemaDeTrabajo = new JButton(GAL_GUI.language.casosDeUso[0]);
		btnEsquemaDeTrabajo.setBounds(3, 20, 141, 30);
		btnEsquemaDeTrabajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! (real_interface instanceof EsquemaDeTrabajo))
					((CardLayout)cardPanel.getLayout()).show(cardPanel,GAL_GUI.language.casosDeUso[0]);
			}
		});
		btnEsquemaDeTrabajo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnDefinirCromosoma = new JButton(GAL_GUI.language.casosDeUso[1]);
		btnDefinirCromosoma.setBounds(3, 90, 141, 30);
		btnDefinirCromosoma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! (real_interface instanceof DefinirCromosoma))
					((CardLayout)cardPanel.getLayout()).show(cardPanel,GAL_GUI.language.casosDeUso[1]);
			}
		});
		btnDefinirCromosoma.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnDefinirFuncin = new JButton(GAL_GUI.language.casosDeUso[2]);
		btnDefinirFuncin.setBounds(3, 160, 141, 30);
		btnDefinirFuncin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(! (real_interface instanceof DefinirFuncion))
					((CardLayout)cardPanel.getLayout()).show(cardPanel,GAL_GUI.language.casosDeUso[2]);
			}
		});
		btnDefinirFuncin.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnConfiguraralgoritmo = new JButton(GAL_GUI.language.casosDeUso[3]);
		btnConfiguraralgoritmo.setBounds(3, 230, 141, 30);
		btnConfiguraralgoritmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! (real_interface instanceof ConfigurarAlgoritmo))
					((CardLayout)cardPanel.getLayout()).show(cardPanel,GAL_GUI.language.casosDeUso[3]);
			}
		});
		btnConfiguraralgoritmo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JButton btnEjecutarAlgoritmo = new JButton(GAL_GUI.language.casosDeUso[4]);
		btnEjecutarAlgoritmo.setBounds(3, 300, 141, 30);
		btnEjecutarAlgoritmo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(! (real_interface instanceof EjecutarAlgoritmo))
					((CardLayout)cardPanel.getLayout()).show(cardPanel,GAL_GUI.language.casosDeUso[4]);
			}
		});
		btnEjecutarAlgoritmo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.setLayout(null);
		contentPane.add(progress);
		progress.setLayout(null);
		progress.add(btnVerCromosoma);
		progress.add(btnVerFuncion);
		progress.add(lblVariables);
		progress.add(pnl_ProgressCromosoma);
		progress.add(lblAptitud);
		progress.add(pnl_ProgressFuncion1);
		progress.add(pnl_ProgressFuncion2);
		progress.add(lblTerminacin);
		progress.add(lblSelector);
		progress.add(pnl_ProgressConfig1);
		progress.add(pnl_ProgressConfig2);
		progress.add(pnl_ProgressConfig3);
		progress.add(lblparametros);
		progress.add(btnVerConfiguracion);
		progress.add(btnVerResultados);
		progress.add(pnl_ProgressResultados);
		progress.add(lblresultados);
		progress.add(lblOperadores);
		
		JButton btn_LimpiarTodo = new JButton("");
		btn_LimpiarTodo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.gal.limpiarTodo();
				pnl_ProgressCromosoma.setBackground(new Color(240, 240, 240));
				pnl_ProgressFuncion1.setBackground(new Color(240, 240, 240));
				pnl_ProgressFuncion2.setBackground(new Color(240, 240, 240));
				pnl_ProgressConfig1.setBackground(new Color(240, 240, 240));
				pnl_ProgressConfig2.setBackground(new Color(240, 240, 240));
				pnl_ProgressConfig3.setBackground(new Color(240, 240, 240));
				pnl_ProgressResultados.setBackground(new Color(240, 240, 240));
			}
		});
		btn_LimpiarTodo.setIcon(new ImageIcon(GeneralWindow.class.getResource("/Images/limpiar.png")));
		btn_LimpiarTodo.setToolTipText("Limpiar Todo");
		btn_LimpiarTodo.setBounds(15, 18, 20, 20);
		progress.add(btn_LimpiarTodo);
		
		JPanel help = new JPanel();
		help.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GAL_GUI.helpViewer.setCurrentID(GAL_GUI.language.helpTargets[4]);
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
		help.setBackground(new Color(0, 0, 0));
		help.setBounds(747, 7, 28, 28);
		progress.add(help);
		help.setLayout(null);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(GeneralWindow.class.getResource("/Images/help.png")));
		label.setBackground(Color.BLACK);
		label.setBounds(0, 0, 28, 28);
		help.add(label);
		contentPane.add(buttons);
		buttons.setLayout(null);
		buttons.add(btnEsquemaDeTrabajo);
		buttons.add(btnSalir);
		buttons.add(btnEjecutarAlgoritmo);
		buttons.add(btnConfiguraralgoritmo);
		buttons.add(btnDefinirFuncin);
		buttons.add(btnDefinirCromosoma);
		
		contentPane.add(cardPanel);
	}
}
