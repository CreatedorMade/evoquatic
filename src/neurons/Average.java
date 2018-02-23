package neurons;

import neurality.AbstractNeuron;

public class Average extends AbstractNeuron {
	public void eval(int t) {
		double sum = 0;
		for(int i = 0; i < super.values.length; i++) {
			sum += super.values[i];
		}
		super.output = sum/super.values.length;
	}
}
