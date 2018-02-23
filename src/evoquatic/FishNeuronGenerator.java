package evoquatic;

import neurality.*;

public class FishNeuronGenerator extends NeuronGenerator {
	FishBrain parent;
	
	public FishNeuronGenerator(FishBrain parent) {
		this.parent = parent;
	}
	
	public AbstractNeuron generateNeuronSpace(int x, int y, boolean mutating, int boundX, int boundY) {
		AbstractNeuron n;
		
		int selector = (int) Math.round(Math.random()*2);
		if(selector == 0) {
			n = new neurons.Average();
		}else if(selector == 1) {
			n = new neurons.Negator();
		}else if(selector == 2) {
			n = new neurons.DorsalFin();
		}else {
			n = new neurons.Sum();
		}
		
		parent.addNeuron(n);
		return n;
	}
}
