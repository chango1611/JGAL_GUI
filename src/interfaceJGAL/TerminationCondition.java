package interfaceJGAL;

import JGAL.GAL_Population;
import JGAL.GAL_TerminationCondition;

public class TerminationCondition extends GAL_TerminationCondition{

	GAL_InterpreterNode interpreter;
	int windowExpectedSize;
	
	public TerminationCondition(GAL_InterpreterNode interpreter, int windowExpectedSize){
		this.interpreter= interpreter;
		this.windowExpectedSize= windowExpectedSize;
	}
	
	@Override
	public boolean verifyTerminationCondition(GAL_Population[] window) {
		GAL_Interpreter aux= GAL_GUI.gal.getInterpreter(1);
		aux.setVariable(0, window[0].size());
		int size= window.length*2+1;
		for(int i=1;i<size;i+=2){
			aux.setVariable(i, window[i/2].getBestChromosome().getFitness());
			aux.setVariable(i+1, window[i/2].getTotalFitness());
		}
		return (Boolean)interpreter.operate();
	}

}
