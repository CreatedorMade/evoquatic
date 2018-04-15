package evoquatic;

public abstract interface Neural {
	abstract void addInput(Neural n);
	abstract Neural[] getInputs();
	abstract double getOutput();
}
