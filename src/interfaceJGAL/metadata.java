package interfaceJGAL;

import java.awt.Component;
import java.awt.Font;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import JGAL.GAL_GeneConfig;
import JGAL.GAL_GeneticOperator;
import JGAL.GAL_Initializer;
import JGAL.GAL_NaturalSelector;

public class metadata {
	
	String name; //Nombre de la metadata para la interfaz
	String[] params_names; //Nombre de los parámetros para la interfaz
	@SuppressWarnings("rawtypes")
	Class class_name; //Clase que se invocará
	@SuppressWarnings("rawtypes")
	Class[] params; //Parametros para el constructor
	JPanel metaPanel, viewPanel; //Panel que será usado para la metadata
	JComponent[] components; //Componentes
	JLabel[] viewLabels;
	
	public metadata(String class_name, String name, String[] params, String[] params_names, int desp) throws Exception{
		int i;
		try{
			this.class_name= Class.forName(class_name);
			this.name= name;
			this.params_names= params_names;
			this.params= new Class[params.length];
			for(i=0;i<params.length;i++)
				this.params[i]= Class.forName(params[i]);
		}catch(Exception e){
			throw new Exception("Metadata Error:\nClass not found: "+e.getMessage());
		}
		try{
			metaPanel= new JPanel();
			metaPanel.setLayout(null);
			viewPanel= new JPanel();
			viewPanel.setLayout(null);
			JLabel[] labels= new JLabel[params_names.length];
			components= new JComponent[params_names.length];
			viewLabels= new JLabel[params_names.length];
			
			if(params_names.length>0){
				JLabel lblConfiguracinAdicional = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
				lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
				lblConfiguracinAdicional.setBounds(10, 4, 192, 14);
				metaPanel.add(lblConfiguracinAdicional);
				lblConfiguracinAdicional = new JLabel(GAL_GUI.language.GeneConfiguration[8]);
				lblConfiguracinAdicional.setFont(new Font("Tahoma", Font.PLAIN, 11));
				lblConfiguracinAdicional.setBounds(10, 4, 192, 14);
				viewPanel.add(lblConfiguracinAdicional);
			}
			
			for(i=0;i<params_names.length;i++){
				labels[i]= new JLabel(params_names[i]);
				labels[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
				labels[i].setHorizontalAlignment(SwingConstants.RIGHT);
				labels[i].setBounds(10, 30*(i+1), 80, 14);
				viewPanel.add(labels[i]);
				labels[i]= new JLabel(params_names[i]);
				labels[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
				labels[i].setHorizontalAlignment(SwingConstants.RIGHT);
				labels[i].setBounds(10, 30*(i+1), 80, 14);
				metaPanel.add(labels[i]);
				if(params[i+desp].equals("java.lang.Integer")){
					components[i]= new JSpinner();
					((JSpinner)components[i]).setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
				}else if(params[i+desp].equals("java.lang.Short")){
					components[i]= new JSpinner();
					((JSpinner)components[i]).setModel(new SpinnerNumberModel(new Short((short)0), null, null, new Short((short)1)));
				}else if(params[i+desp].equals("java.lang.Byte")){
					components[i]= new JSpinner();
					((JSpinner)components[i]).setModel(new SpinnerNumberModel(new Byte((byte)0), null, null, new Byte((byte)1)));
				}else if(params[i+desp].equals("java.lang.Long")){
					components[i]= new JSpinner();
					((JSpinner)components[i]).setModel(new SpinnerNumberModel(new Long(0l), null, null, new Long(1l)));
				}else if(params[i+desp].equals("java.lang.Double")){
					components[i]= new JSpinner();
					((JSpinner)components[i]).setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(0.05)));
				}else if(params[i+desp].equals("java.lang.Float")){
					components[i]= new JSpinner();
					((JSpinner)components[i]).setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.05)));
				}else if(params[i+desp].equals("java.lang.String")){
					components[i]= new JTextField();
				}else if(params[i+desp].equals("java.lang.Character")){
					components[i]= new JTextField();
				}else{ //Error
					throw new Exception("Imposible to represent on Interface: " + params[i+desp]);
				}
				viewLabels[i]= new JLabel();
				viewLabels[i].setFont(new Font("Segoe UI", Font.BOLD, 12));
				viewLabels[i].setBounds(100, 30*(i+1)-3, 100, 20);
				viewPanel.add(viewLabels[i]);
				components[i].setFont(new Font("Tahoma", Font.PLAIN, 11));
				components[i].setBounds(100, 30*(i+1)-3, 70, 20);
				metaPanel.add(components[i]);
			}
		}catch(Exception e){
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GAL_GeneConfig<?> geneConstructor(Component[] comps,String name)throws Exception{
		Object[] values= new Object[components.length+1];
		values[0]= name;
		int i= 1;
		try{
			for(Component c: comps){
				if(c instanceof JLabel)
					continue;
				if(params[i].equals(Class.forName("java.lang.Integer")) || params[i].equals(Class.forName("java.lang.Short")) || params[i].equals(Class.forName("java.lang.Byte"))
				|| params[i].equals(Class.forName("java.lang.Long")) || params[i].equals(Class.forName("java.lang.Double")) || params[i].equals(Class.forName("java.lang.Float"))){
					values[i]= ((JSpinner) c).getValue();
				}else if(params[i].equals(Class.forName("java.lang.String"))){
					values[i]= ((JTextField) c).getText();
				}else{ //Tiene que ser character
					values[i]= new Character(((JTextField) c).getText().charAt(0));
				}
				i++;
			}
			Constructor<GAL_GeneConfig> constructor= class_name.getConstructor(params);
			return constructor.newInstance(values);
		} catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException().getMessage());
		} catch (Exception e) {
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void extractGeneData(Object invoke, Component[] comps) throws Exception{
		Object[] objects= new Object[components.length+1];
		int i;
		try{
			for(i=0;i<components.length;i++){
				objects[i+1]= class_name.getMethod("get"+params_names[i]).invoke(invoke);
			}
			i= 1;
			for(Component c: comps){
				if(c instanceof JLabel)
					continue;
				if(params[i].equals(Class.forName("java.lang.Integer")) || params[i].equals(Class.forName("java.lang.Short")) || params[i].equals(Class.forName("java.lang.Byte"))
						|| params[i].equals(Class.forName("java.lang.Long")) || params[i].equals(Class.forName("java.lang.Double")) || params[i].equals(Class.forName("java.lang.Float"))){
					((JSpinner) c).setValue(objects[i]);
				}else{ //Tiene que ser char o String
					((JTextField) c).setText(""+objects[i]);
				}
				i++;
			}
		}catch(Exception e){
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public GAL_NaturalSelector selectorConstructor(Component[] comps)throws Exception{
		Object[] values= new Object[components.length];
		int i= 0;
		try{
			for(Component c: comps){
				if(c instanceof JLabel)
					continue;
				if(params[i].equals(Class.forName("java.lang.Integer")) || params[i].equals(Class.forName("java.lang.Short")) || params[i].equals(Class.forName("java.lang.Byte"))
				|| params[i].equals(Class.forName("java.lang.Long")) || params[i].equals(Class.forName("java.lang.Double")) || params[i].equals(Class.forName("java.lang.Float"))){
					values[i]= ((JSpinner) c).getValue();
				}else if(params[i].equals(Class.forName("java.lang.String"))){
					values[i]= ((JTextField) c).getText();
				}else{ //Tiene que ser character
					values[i]= new Character(((JTextField) c).getText().charAt(0));
				}
				i++;
			}
			Constructor<GAL_NaturalSelector> constructor= class_name.getConstructor(params);
			return constructor.newInstance(values);
		} catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException().getMessage());
		} catch (Exception e) {
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void extractData(Object invoke, Component[] comps) throws Exception{
		Object[] objects= new Object[components.length];
		int i;
		try{
			for(i=0;i<components.length;i++){
				objects[i]= class_name.getMethod("get"+params_names[i]).invoke(invoke);
			}
			i= 0;
			for(Component c: comps){
				if(c instanceof JLabel)
					continue;
				if(params[i].equals(Class.forName("java.lang.Integer")) || params[i].equals(Class.forName("java.lang.Short")) || params[i].equals(Class.forName("java.lang.Byte"))
						|| params[i].equals(Class.forName("java.lang.Long")) || params[i].equals(Class.forName("java.lang.Double")) || params[i].equals(Class.forName("java.lang.Float"))){
					((JSpinner) c).setValue(objects[i]);
				}else{ //Tiene que ser char o String
					((JTextField) c).setText(""+objects[i]);
				}
				i++;
			}
		}catch(Exception e){
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public GAL_GeneticOperator operatorConstructor(Component[] comps, double prob)throws Exception{
		Object[] values= new Object[components.length+1];
		values[0]= new Double(prob);
		int i= 1;
		try{
			for(Component c: comps){
				if(c instanceof JLabel)
					continue;
				if(params[i].equals(Class.forName("java.lang.Integer")) || params[i].equals(Class.forName("java.lang.Short")) || params[i].equals(Class.forName("java.lang.Byte"))
				|| params[i].equals(Class.forName("java.lang.Long")) || params[i].equals(Class.forName("java.lang.Double")) || params[i].equals(Class.forName("java.lang.Float"))){
					values[i]= ((JSpinner) c).getValue();
				}else if(params[i].equals(Class.forName("java.lang.String"))){
					values[i]= ((JTextField) c).getText();
				}else{ //Tiene que ser character
					values[i]= new Character(((JTextField) c).getText().charAt(0));
				}
				i++;
			}
			Constructor<GAL_GeneticOperator> constructor= class_name.getConstructor(params);
			return constructor.newInstance(values);
		} catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException().getMessage());
		} catch (Exception e) {
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}

	public void extractOperatorData(Object invoke, Component[] comps)throws Exception{
		extractGeneData(invoke,comps);
	}
	
	@SuppressWarnings("unchecked")
	public void extractViewData(Object invoke, Component[] comps) throws Exception{
		Object[] objects= new Object[components.length];
		int i;
		try{
			for(i=0;i<components.length;i++){
				objects[i]= class_name.getMethod("get"+params_names[i]).invoke(invoke);
				((JLabel) comps[(i+1)*2]).setText(""+objects[i]);
			}
		}catch(Exception e){
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public GAL_Initializer initializerConstructor()throws Exception{
		try{
			Constructor<GAL_Initializer> constructor= class_name.getConstructor();
			return constructor.newInstance();
		} catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException().getMessage());
		} catch (Exception e) {
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public String toString(Object invoke)throws Exception{
		String ret="";
		try{
			for(int i=0;i<params_names.length;i++)
				ret+= " " + class_name.getMethod("get"+params_names[i]).invoke(invoke);
		}catch(Exception e){
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public Object readerConstructor(Scanner fr, int type) throws Exception{
		Object[] values;
		//type: 0-genes; 1-selector; 2-operador
		values= new Object[params.length];
		try{
			for(int i=0;i<values.length;i++){
				if(params[i].equals(Class.forName("java.lang.Integer"))){
					values[i]= new Integer(fr.nextInt());
				}else if(params[i].equals(Class.forName("java.lang.Short"))){
					values[i]= new Short(fr.nextShort());
				}else if(params[i].equals(Class.forName("java.lang.Byte"))){
					values[i]= new Byte(fr.nextByte());
				}else if(params[i].equals(Class.forName("java.lang.Long"))){
					values[i]= new Long(fr.nextLong());
				}else if(params[i].equals(Class.forName("java.lang.Double"))){
					values[i]= new Double(Double.parseDouble(fr.next()));
				}else if(params[i].equals(Class.forName("java.lang.Float"))){
					values[i]= new Float(Float.parseFloat(fr.next()));
				}else if(params[i].equals(Class.forName("java.lang.String"))){
					values[i]= fr.next();
				}else{ //Tiene que ser character
					values[i]= new Character(fr.next().charAt(0));
				}
			}
			Constructor<GAL_GeneticOperator> constructor= class_name.getConstructor(params);
			return constructor.newInstance(values);
		} catch (InvocationTargetException e) {
			throw new Exception(e.getTargetException().getMessage());
		} catch (Exception e) {
			throw new Exception("Metadata Error:\n"+e.getMessage());
		}
	}
	
	public String toString(){
		String aux= class_name.toString() + "\n" + name + "\n" + params.length;
		for(@SuppressWarnings("rawtypes") Class p: params)
			aux+= " " + p.toString();
		return aux;
	}
}
