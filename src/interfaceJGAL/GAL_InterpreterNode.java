package interfaceJGAL;

import java.util.Random;

public class GAL_InterpreterNode {

	private GAL_InterpreterNode[] hijos;
	private int operation;
	private int interpreter;
	
	public GAL_InterpreterNode(GAL_InterpreterNode[] hijos, int op, int interpreter){
		this.hijos= hijos;
		operation= op;
		this.interpreter= interpreter;
	}
	
	public GAL_InterpreterNode(int op, int interpreter){
		hijos= null;
		operation= op;
		this.interpreter= interpreter;
	}
	
	public void setHijos(GAL_InterpreterNode[] hijos){
		this.hijos= hijos;
	}
	
	public GAL_InterpreterNode getHijo(int i){
		return hijos[i];
	}
	
	/* 0. +
	 * 1. -
	 * 2. *
	 * 3. /
	 * 4. **
	 * 5. %
	 * 6. Max
	 * 7. Min
	 * 8. Root
	 * 9. Log
	 * 10. Hypot
	 * 11. RandI
	 * 12. RandD
	 */
	public double binary_aritmetic_operate(){
		double ret= 0;
		switch(operation){
			case 0: ret= hijos[0].aritmetic_operate() + hijos[1].aritmetic_operate(); break;
			case 1: ret= hijos[0].aritmetic_operate() - hijos[1].aritmetic_operate(); break;
			case 2: ret= hijos[0].aritmetic_operate() * hijos[1].aritmetic_operate(); break;
			case 3: ret= hijos[0].aritmetic_operate() / hijos[1].aritmetic_operate(); break;
			case 4: ret= Math.pow(hijos[0].aritmetic_operate(),hijos[1].aritmetic_operate()); break;
			case 5: ret= hijos[0].aritmetic_operate() % hijos[1].aritmetic_operate(); break;
			case 6: ret= Math.max(hijos[0].aritmetic_operate(),hijos[1].aritmetic_operate()); break;
			case 7: ret= Math.min(hijos[0].aritmetic_operate(),hijos[1].aritmetic_operate()); break;
			case 8: ret= Math.pow(hijos[0].aritmetic_operate(), 1/hijos[1].aritmetic_operate()); break;
			case 9: ret= Math.log(hijos[0].aritmetic_operate())/ Math.log(hijos[1].aritmetic_operate()); break;
			case 10: ret= Math.hypot(hijos[0].aritmetic_operate(), hijos[1].aritmetic_operate()); break;
			case 11:
				Random r= new Random();
				ret= r.nextInt((int)hijos[1].aritmetic_operate()-(int)hijos[0].aritmetic_operate()) + hijos[0].aritmetic_operate();
			break;
			case 12:
				r= new Random();
				ret= r.nextDouble()*(hijos[1].aritmetic_operate()-(int)hijos[0].aritmetic_operate()) + hijos[0].aritmetic_operate();
			break;
		};
		return ret;
	}
	
	/* 13. - (Unario)
	 * 14. Abs
	 * 15. Sin
	 * 16. Cos
	 * 17. Tan
	 * 18. Acos
	 * 19. Asin
	 * 20. Atan
	 * 21. toRadians
	 * 22. toDegrees
	 * 23. Round
	 * 24. Ceil
	 * 25. Floor
	 */
	public double unary_aritmetic_operate(){
		double ret= 0;
		switch(operation){
			case 13: ret= -hijos[0].aritmetic_operate(); break;
			case 14: ret= Math.abs(hijos[0].aritmetic_operate()); break;
			case 15: ret= Math.sin(hijos[0].aritmetic_operate()); break;
			case 16: ret= Math.cos(hijos[0].aritmetic_operate()); break;
			case 17: ret= Math.tan(hijos[0].aritmetic_operate()); break;
			case 18: ret= Math.asin(hijos[0].aritmetic_operate()); break;
			case 19: ret= Math.acos(hijos[0].aritmetic_operate()); break;
			case 20: ret= Math.atan(hijos[0].aritmetic_operate()); break;
			case 21: ret= Math.toRadians(hijos[0].aritmetic_operate()); break;
			case 22: ret= Math.toDegrees(hijos[0].aritmetic_operate()); break;
			case 23: ret= Math.round(hijos[0].aritmetic_operate()); break;
			case 24: ret= Math.ceil(hijos[0].aritmetic_operate()); break;
			case 25: ret= Math.floor(hijos[0].aritmetic_operate()); break;
		};
		return ret;
	}
	
	/*
	 * 0-12. binary_aritmetic
	 * 13-25. unary_aritmetic
	 * 39. number
	 * 42. PI
	 * 43. E
	 * 46. RandB
	 * 48. Identifier
	 * 52. Bin2Int
	 * 53. Pos
	 */
	public double aritmetic_operate(){
		if(operation<13)
			return binary_aritmetic_operate();
		if(operation<26)
			return unary_aritmetic_operate();
		if(operation==39)
			return ((GAL_InterpreterLeaf)this).numberValue();
		if(operation==42)
			return Math.PI;
		if(operation==43)
			return Math.E;
		if(operation==46)
			return (new Random()).nextDouble()>0.5?1:0;
		if(operation==52){
			String aux="";
			for(GAL_InterpreterNode hijo: hijos){
				if(hijo.aritmetic_operate()>0)
					aux+= "1";
				else
					aux+= "0";
			}
			return ((Integer) Integer.parseInt(aux,2)).doubleValue();
		}
		if(operation==53){
			return hijos[0].getHijo(0).aritmetic_operate();
		}
		Object aux= identifier();
		if(aux instanceof Double)
			return (Double) aux;
		if(aux instanceof Integer)
			return ((Integer) aux).doubleValue();
		if(aux instanceof Byte)
			return ((Byte) aux).doubleValue();
		throw new RuntimeException(GAL_GUI.language.Errors[25]);
	}
	
	/* 26. >
	 * 27. <
	 * 28. =
	 * 29. >=
	 * 30. <=
	 * 31. <>
	 */
	public boolean comparators_operate(){
		boolean ret= true;
		double comparation= 0;
		Object comparables0, comparables1;
		comparables0= hijos[0].comparables();
		comparables1= hijos[1].comparables();
		try{
			if(comparables0 instanceof Double){
				if(comparables1 instanceof Double)
					comparation= ((Double) comparables0) - ((Double) comparables1);
				if(comparables1 instanceof Integer)
					comparation= ((Double) comparables0)- ((Integer) comparables1);
				if(comparables1 instanceof Byte)
					comparation= ((Double) comparables0)- ((Byte) comparables1);
			}
			else if(comparables0 instanceof Integer){
				if(comparables1 instanceof Integer)
					comparation= ((Integer) comparables0) - ((Integer) comparables1);
				if(comparables1 instanceof Double)
					comparation= ((Integer) comparables0) - ((Double) comparables1);
				if(comparables1 instanceof Byte)
					comparation= ((Integer) comparables0) - ((Byte) comparables1);
			}
			else if(comparables0 instanceof Byte){
				if(comparables1 instanceof Byte)
					comparation= ((Byte) comparables0) - ((Byte) comparables1);
				if(comparables1 instanceof Double)
					comparation= ((Byte) comparables0) - ((Double) comparables1);
				if(comparables1 instanceof Integer)
					comparation= ((Byte) comparables0) - ((Integer) comparables1);
			}
			else if(comparables0 instanceof Character)
				comparation= ((Character) comparables0).compareTo((Character) comparables1);
			else if(comparables0 instanceof String)
				comparation= ((String) comparables0).compareTo((String) comparables1);
			switch(operation){
				case 26: ret= comparation>0.0001; break;
				case 27: ret= comparation<-0.0001; break;
				case 28: ret= Math.abs(comparation)<0.0001; break;
				case 29: ret= comparation>=0; break;
				case 30: ret= comparation<=0; break;
				case 31: ret= Math.abs(comparation)>0.0001; break; 
			};
			return ret;
		}catch(Exception e){
			throw new RuntimeException(GAL_GUI.language.Errors[26]);
		}
	}
	
	/*
	 * 0-25|39|42|43. aritmetico
	 * 40. Char
	 * 41. String  
	 * 47. RandC
	 * 48. Identifier
	 */
	public Object comparables(){
		if(operation < 26 || operation==39 || operation==42 || operation==43)
			return aritmetic_operate();
		if(operation==40)
			return ((GAL_InterpreterLeaf)this).charValue();
		if(operation==41)
			return ((GAL_InterpreterLeaf)this).stringValue();
		if(operation==47)
			return (char)((new Random()).nextInt((int)((GAL_InterpreterLeaf)hijos[0]).charValue()-(int)((GAL_InterpreterLeaf)hijos[1]).charValue())
					+ (int)((GAL_InterpreterLeaf)hijos[0]).charValue());
		return identifier();
	}
	
	/* 32. &&
	 * 33. ||
	 * 34. ^
	 */
	public boolean binary_logic_operate(){
		boolean ret= true;
		switch(operation){
			case 32: ret= hijos[0].logic_operate() && hijos[1].logic_operate(); break;
			case 33: ret= hijos[0].logic_operate() || hijos[1].logic_operate(); break;
			case 34: ret= hijos[0].logic_operate() ^ hijos[1].logic_operate(); break;
		};
		return ret;
	}
	
	/* 35. Not
	 */
	public boolean unary_logic_operate(){
		return !hijos[0].logic_operate();
	}
	
	/*
	 * 32-34. binary_logic
	 * 35. unary_logic
	 * 26-31. comparator
	 * 44. True
	 * 45. False
	 * 48. Identifier
	 */
	public boolean logic_operate(){
		if(operation>31 && operation<35)
			return binary_logic_operate();
		if(operation==35)
			return unary_logic_operate();
		if(operation>25 && operation<32)
			return comparators_operate();
		if(operation==44)
			return true;
		if(operation==45)
			return false;
		Object identifier= identifier();
		if(identifier instanceof Boolean)
			return (Boolean)identifier();
		throw new RuntimeException(GAL_GUI.language.Errors[27]);
	}
	
	/* 36. If
	 */
	public Object if_statement(){
		if(hijos[0].logic_operate())
			return hijos[1].operate();
		return null;
	}
	
	/* 37. If/Else
	 */
	public Object if_else_statement(){
		if(hijos[0].logic_operate())
			return hijos[1].operate();
		else
			return hijos[2].operate();
	}
	
	/* 38. While
	 */
	public Object while_statement(){
		Object ret= null;
		long time= System.currentTimeMillis();
		while(hijos[0].logic_operate()){
			ret= hijos[1].operate();
			if(System.currentTimeMillis() - time > 10000)
				throw new RuntimeException(GAL_GUI.language.Errors[28]);
		}
		return ret;
	}
	
	/* 48. Identifier
	 */
	public Object identifier(){
		int pos= (int) hijos[0].aritmetic_operate();
		return GAL_GUI.gal.getInterpreter(interpreter).getVariable(pos);
	}
	
	/* 49. :=
	 */
	public Object assignStatement(){
		int pos= (int) hijos[0].aritmetic_operate();
		if(interpreter==0 && pos < GAL_GUI.gal.getGeneNames().length)
			throw new RuntimeException(GAL_GUI.language.Errors[29]);
		if(interpreter==1 && pos < GAL_GUI.gal.getWindowSize()*2+1)
			throw new RuntimeException(GAL_GUI.language.Errors[29]);
		return GAL_GUI.gal.getInterpreter(interpreter).setVariable(pos, hijos[1].operate());
	}
	
	/* 51. Factory
	 */
	public Object factoryStatement(){
		Object aux= null;
		for(GAL_InterpreterNode hijo: hijos){
			aux= hijo.assignStatement();
		}
		return aux;
	}
	
	/* 50. ;
	 * 0-25|39|42|43. aritmetico
	 * 26-35|44|45. Logico
	 * 36. If
	 * 37. If/Else
	 * 38. While
	 * 49. Assign
	 * 51. Factory
	 * 48. Identifier
	 */
	public Object operate(){
		if(operation==50){
			hijos[0].operate();
			return hijos[1].operate();
		}
		if(operation < 26 || operation==39 || operation==42 || operation==43 || operation==52 || operation==53)
			return aritmetic_operate();
		if((operation > 25 && operation<36) || operation==44 || operation==45)
			return logic_operate();
		if(operation==36)
			return if_statement();
		if(operation==37)
			return if_else_statement();
		if(operation==38)
			return while_statement();
		if(operation==49)
			return assignStatement();
		if(operation==51)
			return factoryStatement();
		Object aux= identifier(); 
		if(aux instanceof Byte)
			return ((Byte) aux).doubleValue();
		if(aux instanceof Integer)
			return ((Integer) aux).doubleValue();
		return (Double)aux;
	}
	
	public String toString(String prof){
		String ret= prof+operation+"\n";
		if(hijos!=null){
			for(GAL_InterpreterNode hijo: hijos){
				ret+= hijo.toString(prof+"\t");
			}
		}
		return ret;
	}
}
