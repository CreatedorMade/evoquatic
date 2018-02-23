package neurons;

import neurality.AbstractNeuron;

public class Timer extends AbstractNeuron {
	
	public Timer() {
		super();
		super.maxInputs = 0;
	}
	
	public String getName() {
		return "timer";
	}
	
	public void eval(int tick) {
		super.output = tick;
	}
}
