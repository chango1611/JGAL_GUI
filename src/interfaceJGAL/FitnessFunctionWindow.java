package interfaceJGAL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FitnessFunctionWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenuItem mntmAbrir;
	private JMenuItem mntmGuardar;
	private JMenuItem mntmSalir;
	private JMenu mnAyuda;
	private JMenuItem mntmSintaxis;
	private JMenuItem mntmValidar;
	private JTextArea txt_Codigo;
	private JPanel validez;

	/**
	 * Create the frame.
	 */
	public FitnessFunctionWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				actualizar();
			}
			@Override
			public void windowClosed(WindowEvent e) {
				if(GAL_GUI.gal.getInterpreter(0).getValid())
					GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(128, 0, 128));
				else
					GeneralWindow.pnl_ProgressFuncion1.setBackground(new Color(240, 240, 240));
			}
		});
		setTitle(GAL_GUI.language.botonesPrincipales[1]+" "+GAL_GUI.language.botonesPrincipales[2]);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 450);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnArchivo = new JMenu(GAL_GUI.language.CommonWords[2]);
		mnArchivo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnArchivo);
		
		mntmAbrir = new JMenuItem(GAL_GUI.language.CommonWords[3]);
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					JFileChooser fc= new JFileChooser();
					int returnVal= fc.showOpenDialog(FitnessFunctionWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION)
						GAL_GUI.gal.openInterpreter(fc.getSelectedFile(),0);
					actualizar();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null, GAL_GUI.language.Errors[10]);
				}
			}
		});
		mntmAbrir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmAbrir);
		
		mntmGuardar = new JMenuItem(GAL_GUI.language.CommonWords[4]);
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc= new JFileChooser();
				int returnVal= fc.showSaveDialog(FitnessFunctionWindow.this);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					GAL_GUI.gal.setInterpreter(txt_Codigo.getText(), 0);
					GAL_GUI.gal.saveInterpreter(fc.getSelectedFile(),0);
				}
			}
		});
		mntmGuardar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmGuardar);
		
		mntmValidar = new JMenuItem(GAL_GUI.language.FitnessAndTerminationConfiguration[2]);
		mntmValidar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.gal.setInterpreter(txt_Codigo.getText(), 0);
				GAL_GUI.gal.validateInterpreter(0);
				actualizar();
			}
		});
		mntmValidar.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmValidar);
		
		mntmSalir = new JMenuItem(GAL_GUI.language.CommonWords[5]);
		mntmSalir.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnArchivo.add(mntmSalir);
		
		mnAyuda = new JMenu("Ayuda");
		mnAyuda.setFont(new Font("Tahoma", Font.PLAIN, 11));
		menuBar.add(mnAyuda);
		
		mntmSintaxis = new JMenuItem("Sintaxis");
		mntmSintaxis.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnAyuda.add(mntmSintaxis);
		
		JMenuItem mntmTutorial = new JMenuItem("Tutorial");
		mntmTutorial.setFont(new Font("Tahoma", Font.PLAIN, 11));
		mnAyuda.add(mntmTutorial);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		ActionListener al= new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				txt_Codigo.append(((JButton)e.getSource()).getText());
				txt_Codigo.requestFocus();
			}
		};
		
		JButton btn_Parentesis1 = new JButton("(");
		btn_Parentesis1.addActionListener(al);
		btn_Parentesis1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Parentesis1.setBounds(226, 189, 42, 23);
		
		JButton button_0 = new JButton("0");
		button_0.addActionListener(al);
		button_0.setBounds(31, 276, 84, 23);
		button_0.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_0);
		
		JButton btn_Punto = new JButton(".");
		btn_Punto.addActionListener(al);
		btn_Punto.setBounds(121, 276, 39, 23);
		btn_Punto.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btn_Punto);
		
		JButton btn_Sumar = new JButton("+");
		btn_Sumar.addActionListener(al);
		btn_Sumar.setBounds(166, 276, 42, 23);
		btn_Sumar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btn_Sumar);
		
		JButton button_1 = new JButton("1");
		button_1.addActionListener(al);
		button_1.setBounds(31, 189, 39, 23);
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("2");
		button_2.addActionListener(al);
		button_2.setBounds(76, 189, 39, 23);
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("3");
		button_3.addActionListener(al);
		button_3.setBounds(121, 189, 39, 23);
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_3);
		
		JButton btn_Dividir = new JButton("/");
		btn_Dividir.addActionListener(al);
		btn_Dividir.setBounds(166, 189, 42, 23);
		btn_Dividir.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btn_Dividir);
		
		JButton btn_Parentesis2 = new JButton(")");
		btn_Parentesis2.addActionListener(al);
		btn_Parentesis2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Parentesis2.setBounds(272, 189, 42, 23);
		contentPane.add(btn_Parentesis2);
		
		JButton button_4 = new JButton("4");
		button_4.addActionListener(al);
		button_4.setBounds(31, 218, 39, 23);
		button_4.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_4);
		
		JButton button_5 = new JButton("5");
		button_5.addActionListener(al);
		button_5.setBounds(76, 218, 39, 23);
		button_5.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_5);
		
		JButton button_6 = new JButton("6");
		button_6.addActionListener(al);
		button_6.setBounds(121, 218, 39, 23);
		button_6.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_6);
		
		JButton btn_Multiplicar = new JButton("*");
		btn_Multiplicar.addActionListener(al);
		btn_Multiplicar.setBounds(166, 218, 42, 23);
		btn_Multiplicar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btn_Multiplicar);
		
		JButton button_7 = new JButton("7");
		button_7.addActionListener(al);
		button_7.setBounds(31, 247, 39, 23);
		button_7.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_7);
		
		JButton button_8 = new JButton("8");
		button_8.addActionListener(al);
		button_8.setBounds(76, 247, 39, 23);
		button_8.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_8);
		
		JButton button_9 = new JButton("9");
		button_9.addActionListener(al);
		button_9.setBounds(121, 247, 39, 23);
		button_9.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(button_9);
		
		JButton btn_Restar = new JButton("-");
		btn_Restar.addActionListener(al);
		btn_Restar.setBounds(166, 247, 42, 23);
		btn_Restar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		contentPane.add(btn_Restar);
		
		JButton btn_PointComma = new JButton(";");
		btn_PointComma.addActionListener(al);
		btn_PointComma.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_PointComma.setBounds(272, 218, 42, 23);
		contentPane.add(btn_PointComma);

		JButton btn_Comma = new JButton(",");
		btn_Comma.addActionListener(al);
		btn_Comma.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Comma.setBounds(226, 218, 42, 23);
		contentPane.add(btn_Comma);
		contentPane.add(btn_Parentesis1);
		
		final JButton btn_Optional1 = new JButton("Root");
		btn_Optional1.addActionListener(al);
		btn_Optional1.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional1.setBounds(331, 189, 64, 23);
		contentPane.add(btn_Optional1);
		
		final JButton btn_Optional2 = new JButton("**");
		btn_Optional2.addActionListener(al);
		btn_Optional2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional2.setBounds(398, 189, 64, 23);
		contentPane.add(btn_Optional2);
		
		final JButton btn_Optional3 = new JButton("%");
		btn_Optional3.addActionListener(al);
		btn_Optional3.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional3.setBounds(464, 189, 64, 23);
		contentPane.add(btn_Optional3);

		final JButton btn_Optional4 = new JButton("Max");
		btn_Optional4.addActionListener(al);
		btn_Optional4.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional4.setBounds(331, 218, 64, 23);
		contentPane.add(btn_Optional4);
		
		final JButton btn_Optional5 = new JButton("Min");
		btn_Optional5.addActionListener(al);
		btn_Optional5.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional5.setBounds(398, 218, 64, 23);
		contentPane.add(btn_Optional5);
		
		final JButton btn_Optional6 = new JButton("Abs");
		btn_Optional6.addActionListener(al);
		btn_Optional6.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional6.setBounds(464, 218, 64, 23);
		contentPane.add(btn_Optional6);
		
		final JButton btn_Optional7 = new JButton("Log");
		btn_Optional7.addActionListener(al);
		btn_Optional7.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional7.setBounds(331, 247, 64, 23);
		contentPane.add(btn_Optional7);
		
		final JButton btn_Optional8 = new JButton("");
		btn_Optional8.addActionListener(al);
		btn_Optional8.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional8.setBounds(398, 247, 64, 23);
		contentPane.add(btn_Optional8);
		
		final JButton btn_Optional9 = new JButton("");
		btn_Optional9.addActionListener(al);
		btn_Optional9.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Optional9.setBounds(464, 247, 64, 23);
		contentPane.add(btn_Optional9);

		final JComboBox<String> cb_Variables = new JComboBox<String>();
		cb_Variables.setModel(new DefaultComboBoxModel<String>(GAL_GUI.gal.getGeneNames()));
		cb_Variables.setFont(new Font("Tahoma", Font.PLAIN, 10));
		cb_Variables.setBounds(31, 318, 175, 20);	
		contentPane.setLayout(null);
		contentPane.add(cb_Variables);
		
		JButton btnAgregarVariable = new JButton(GAL_GUI.language.FitnessAndTerminationConfiguration[0]);
		btnAgregarVariable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_Codigo.append("$"+cb_Variables.getSelectedItem());
				txt_Codigo.requestFocus();
			}
		});
		btnAgregarVariable.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnAgregarVariable.setBounds(213, 317, 113, 23);
		contentPane.add(btnAgregarVariable);

		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 10));
		String[] Tipos= new String[6];
		System.arraycopy(GAL_GUI.language.FitnessAndTerminationConfiguration, 3, Tipos, 0, 6);
		comboBox.setModel(new DefaultComboBoxModel<String>(Tipos));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(331, 277, 197, 20);
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				int option= ((JComboBox<String>) e.getSource()).getSelectedIndex();
				switch(option){
					case 0:
						btn_Optional3.setText("Root");
						btn_Optional1.setText("**");
						btn_Optional2.setText("%");
						btn_Optional4.setText("Max");
						btn_Optional5.setText("Min");
						btn_Optional6.setText("Abs");
						btn_Optional7.setText("Log");
						btn_Optional8.setText("");
						btn_Optional9.setText("");
					break;
					case 1:
						btn_Optional1.setText("Sin");
						btn_Optional2.setText("Cos");
						btn_Optional3.setText("Tan");
						btn_Optional4.setText("Asin");
						btn_Optional5.setText("Acos");
						btn_Optional6.setText("Atan");
						btn_Optional7.setText("Hypot");
						btn_Optional8.setText("toDegrees");
						btn_Optional9.setText("toRadians");
					break;
					case 2:
						btn_Optional1.setText("If");
						btn_Optional2.setText("Then");
						btn_Optional3.setText("Else");
						btn_Optional4.setText("While");
						btn_Optional5.setText("End");
						btn_Optional6.setText("");
						btn_Optional7.setText("");
						btn_Optional8.setText("");
						btn_Optional9.setText("");
					break;
					case 3:
						btn_Optional1.setText("True");
						btn_Optional2.setText("False");
						btn_Optional3.setText("Not");
						btn_Optional4.setText("&&");
						btn_Optional5.setText("||");
						btn_Optional6.setText("^");
						btn_Optional7.setText(">");
						btn_Optional8.setText("<");
						btn_Optional9.setText("=");
					break;
					case 4:
						btn_Optional1.setText("$");
						btn_Optional2.setText("{");
						btn_Optional3.setText("}");
						btn_Optional4.setText(":=");
						btn_Optional5.setText("\"");
						btn_Optional6.setText("'");
						btn_Optional7.setText("");
						btn_Optional8.setText("");
						btn_Optional9.setText("");
					break;
					case 5:
						btn_Optional1.setText("Round");
						btn_Optional2.setText("Ceil");
						btn_Optional3.setText("Floor");
						btn_Optional4.setText("RandB");
						btn_Optional5.setText("RandI");
						btn_Optional6.setText("RandD");
						btn_Optional7.setText("RandC");
						btn_Optional8.setText("PI");
						btn_Optional9.setText("E");
					break;
				}
			}
		});
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 518, 167);
		contentPane.add(scrollPane);
		
		txt_Codigo = new JTextArea();
		txt_Codigo.setTabSize(2);
		scrollPane.setViewportView(txt_Codigo);
		
		JButton btnAceptar = new JButton(GAL_GUI.language.CommonWords[0]);
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.gal.setInterpreter(txt_Codigo.getText(), 0);
				GAL_GUI.gal.validateInterpreter(0);
				dispose();
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnAceptar.setBounds(225, 355, 89, 23);
		contentPane.add(btnAceptar);
		
		JButton btnCancelar = new JButton(GAL_GUI.language.CommonWords[1]);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnCancelar.setBounds(331, 355, 89, 23);
		contentPane.add(btnCancelar);
		
		validez = new JPanel();
		validez.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		validez.setBounds(508, 318, 20, 20);
		contentPane.add(validez);
		
		JLabel lblValidez = new JLabel(GAL_GUI.language.FitnessAndTerminationConfiguration[1]);
		lblValidez.setBounds(459, 321, 46, 14);
		contentPane.add(lblValidez);
		
		JButton btn_Validar = new JButton(GAL_GUI.language.FitnessAndTerminationConfiguration[2]);
		btn_Validar.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btn_Validar.setBounds(119, 355, 89, 23);
		btn_Validar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GAL_GUI.gal.setInterpreter(txt_Codigo.getText(), 0);
				GAL_GUI.gal.validateInterpreter(0);
				actualizar();
			}
		});
		contentPane.add(btn_Validar);
		
	}
	
	void actualizar(){
		if(GAL_GUI.gal.getInterpreter(0)!= null){
			txt_Codigo.setText(GAL_GUI.gal.getInterpreter(0).getCode());
			if(GAL_GUI.gal.getInterpreter(0).getValid())
				validez.setBackground(new Color(0, 255, 0));
			else
				validez.setBackground(new Color(240, 240, 240));
		}
	}
}
