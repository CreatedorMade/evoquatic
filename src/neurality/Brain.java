package neurality;

public class Brain {
	int tick = 0;
	
	private int sizeX = 0;
	private int sizeY = 0;
	
	public double[] inputWall;
	public double[] outputWall;
	
	public AbstractNeuron[][] neurons;
	
	public NeuronGenerator gen;
	
	public Brain(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		inputWall = new double[sizeY];
		outputWall = new double[sizeY];
		
		neurons = new AbstractNeuron[sizeX][sizeY];
	}
	
	public Brain(int sizeX, int sizeY, NeuronGenerator gen) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		inputWall = new double[sizeY];
		outputWall = new double[sizeY];
		
		neurons = new AbstractNeuron[sizeX][sizeY];
		
		this.gen = gen;
		
		for(int ix = 0; ix < sizeX; ix++) {
			for(int iy = 0; iy < sizeY; iy++) {
				neurons[ix][iy] = gen.generateNeuronSpace(ix, iy, false, sizeX, sizeY);
			}
		}
	}
	
	public int sizeX() {return sizeX;}
	public int sizeY() {return sizeY;}
	
	public double getDataFrom(int x, int y) {
		if(x == 0) { return inputWall[y]; }
		if(neurons[x-1][y] != null) {return neurons[x-1][y].output; }
		return 0;
	}
	
	public void tick() {
		for(int ix = 0; ix < sizeX; ix++) {
			for(int iy = 0; iy < sizeY; iy++) {
				if(neurons[ix][iy] != null) {
					for(int ii = 0; ii < neurons[ix][iy].inputs.length; ii++) {
						neurons[ix][iy].values[ii] = getDataFrom(neurons[ix][iy].inputs[ii][0], neurons[ix][iy].inputs[ii][1]);
					}
					neurons[ix][iy].eval(tick);
				}
			}
		}
		
		for(int i = 0; i < sizeY; i++) {
			outputWall[i] = getDataFrom(sizeX, i);
		}
		
		tick++;
	}
	
	public void mutate(int reps) {
		for(int i = 0; i < reps; i++) {
			int rx = (int) Math.round(Math.random()*(sizeX-1));
			int ry = (int) Math.round(Math.random()*(sizeY-1));
			if(neurons[rx][ry] != null) {
				int rr = (int) Math.round(Math.random()*1);
				if(rr == 0) {
					neurons[rx][ry] = gen.generateNeuronSpace(rx, ry, true, sizeX, sizeY);
				} else {
					if(neurons[rx][ry].mods.length > 0) neurons[rx][ry].mods[(int) Math.round(Math.random()*(neurons[rx][ry].mods.length-1))] = Math.random();
				}
			} else {
				neurons[rx][ry] = gen.generateNeuronSpace(rx, ry, true, sizeX, sizeY);
			}
		}
	}
}