package neurons;

import neurality.AbstractNeuron;

public class Antenna extends AbstractNeuron {
	
	double data = 0;
	
	public Antenna() {
		super();
		super.maxInputs = 0;
		super.mods = new double[2];
	}
	
	public void setSensoryData(double d) {
		data = d;
	}
	
	public String getName() {
		return "antenna";
	}
	
	public void eval(int tick) {
		super.output = data;
	}

}
