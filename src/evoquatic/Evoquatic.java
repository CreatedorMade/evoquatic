package evoquatic;

public class Evoquatic {

	public static void main(String[] args) {
		Simulation sim = new Simulation();
		EPanel panel = new EPanel(sim);
		EFrame frame = new EFrame(panel);
	}

}
