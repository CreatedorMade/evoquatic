package evoquatic;

import neurality.*;

public class FishBrain {
	
	Brain internal;
	FishNeuronGenerator gen;
	
	public FishBrain() {
		gen = new FishNeuronGenerator(this);
		
		internal = new Brain(20, 20, gen);
	}
	
}
