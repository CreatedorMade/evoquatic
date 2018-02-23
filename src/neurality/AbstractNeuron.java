package neurality;

public abstract class AbstractNeuron {
	public int[][] inputs = new int[0][2];
	public double[] values = new double[0];
	public double[] mods = new double[0];
	public double output = 0;
	public int maxInputs = -1;
	
	public final void addInput(int x, int y){
		if(maxInputs != -1 && inputs.length < maxInputs) {
			int[][] nInputs = new int[inputs.length+1][2];
			for(int i = 0; i < inputs.length; i++) {
				nInputs[i] = inputs[i];
			}
			nInputs[inputs.length][0] = x;
			nInputs[inputs.length][1] = y;
			inputs = nInputs;
		}
		
		values = new double[inputs.length];
	}
	
	public final void cutInput(int x, int y){
		if(inputs.length != 0) {
			int[][] nInputs = new int[inputs.length-1][2];
			boolean found = false;
			for(int i = 0; i < nInputs.length; i++) {
				if(x == inputs[i][0] && y == inputs[i][1]) found = true;
				if(!found) nInputs[i] = inputs[i];
				else nInputs[i] = inputs[i+1];
			}
		}

		values = new double[inputs.length];
	}
	
	public abstract void eval(int tick);
}
