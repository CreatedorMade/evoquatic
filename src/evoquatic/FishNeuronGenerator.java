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
			n = new neurons.Antenna();
		}else if(selector == 1) {
			n = new neurons.Average();
		}else if(selector == 2) {
			n = new neurons.Fin();
		}else if(selector == 3){
			n = new neurons.Modulator();
		}else if(selector == 4) {
			n = new neurons.Mouth();
		}else if(selector == 5) {
			n = new neurons.Multiplicator();
		}else if(selector == 6) {
			n = new neurons.Negator();
		}else if(selector == 7) {
			n = new neurons.Reciprocator();
		}else if(selector == 8) {
			n = new neurons.Stomach();
		}else if(selector == 9) {
			n = new neurons.Sum();
		}else {
			n = new neurons.Timer();
		}
		
		for(int i = 0; i < n.mods.length; i++) {
			n.mods[i] = Math.random();
		}
		
		do {
			n.addInput((int) Math.round(Math.random() * x), (int) Math.round(Math.random() * boundY-1));
		} while(Math.random() < 0.10);
		
		parent.addNeuron(n);
		return n;
	}
}
