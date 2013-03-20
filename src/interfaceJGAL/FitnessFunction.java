package interfaceJGAL;

import JGAL.GAL_Chromosome;
import JGAL.GAL_FitnessFunction;

public class FitnessFunction extends GAL_FitnessFunction{

	GAL_InterpreterNode interpreter;
	boolean optimizationType;

	public FitnessFunction(GAL_InterpreterNode interpreter, boolean optimizationType){
		this.interpreter= interpreter;
		this.optimizationType= optimizationType;
	}
	
	@Override
	public double computeFitness(GAL_Chromosome chrom) {
		GAL_Interpreter aux= GAL_GUI.gal.getInterpreter(0);
		for(int i=0;i<chrom.size();i++)
			aux.setVariable(i, chrom.getTrait(i));
		if(optimizationType)
			return (Double) interpreter.operate();
		else
			return 1000000000 - (Double) interpreter.operate();
	}

}
