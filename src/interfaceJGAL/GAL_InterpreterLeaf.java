package interfaceJGAL;

public class GAL_InterpreterLeaf extends GAL_InterpreterNode{
	
	private Object value;
	
	/* 39. Number
	 * 40. Char
	 * 41. String
	 * 42. PI
	 * 43. E
	 * 44. True
	 * 45. False
	 */
	public GAL_InterpreterLeaf(Object value, int op, int interpreter){
		super(op, interpreter);
		this.value= value;
	}
	
	public GAL_InterpreterLeaf(int op, int interpreter){
		super(op, interpreter);
	}
	
	public double numberValue(){
		return (Double) value;
	}
	
	public char charValue(){
		return (Character) value;
	}
	
	public String stringValue(){
		return (String) value;
	}
}
