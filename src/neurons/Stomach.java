package neurons;

import neurality.AbstractNeuron;

public class Stomach extends AbstractNeuron {
	
	double data;
	
	public Stomach() {
		super();
		super.maxInputs = 0;
	}
	
	public String getName() {
		return "stomach";
	}
	
	public void setSensoryData(double d) {
		data = d;
	}
	
	public void eval(int tick) {
		super.output = data;
	}
}
