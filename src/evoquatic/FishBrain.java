package evoquatic;

import java.util.Arrays;
import neurality.*;
import java.awt.Color;

public class FishBrain {
	
	double x = 0;
	double y = 0;
	double vx = 0;
	double vy = 0;
	double facing = 0;
	
	Color pigmentation = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
	
	Brain internal;
	FishNeuronGenerator gen;
	
	public FishBrain() {
		gen = new FishNeuronGenerator(this);
		internal = new Brain(30, 20, gen);
	}
	
	public FishBrain(Color c) {
		gen = new FishNeuronGenerator(this);
		internal = new Brain(30, 20, gen);
		pigmentation = new Color((float) Math.min(1, Math.max(0, c.getRed()+Math.random()*0.1)), (float) Math.min(1, Math.max(0, c.getGreen()+Math.random()*0.1)), (float) Math.min(1, Math.max(0, c.getBlue()+Math.random()*0.1)));
	}
	
	AbstractNeuron[] antennae = new AbstractNeuron[0];
	AbstractNeuron[] fins = new AbstractNeuron[0];
	AbstractNeuron[] mouths = new AbstractNeuron[0];
	AbstractNeuron[] stomachs = new AbstractNeuron[0];
	
	public void addNeuron(AbstractNeuron n) {
		if(n.getName() == "antenna") {
			antennae = Arrays.copyOf(antennae, antennae.length+1);
			antennae[antennae.length-1] = n;
		} else if(n.getName() == "fin") {
			fins = Arrays.copyOf(fins, fins.length+1);
			fins[fins.length-1] = n;
		} else if(n.getName() == "mouth") {
			mouths = Arrays.copyOf(mouths, mouths.length+1);
			mouths[mouths.length-1] = n;
		} else if(n.getName() == "stomachs") {
			stomachs = Arrays.copyOf(stomachs, stomachs.length+1);
			stomachs[stomachs.length-1] = n;
		}
	}	
}
