package evoquatic;

public class Evoquatic {
	public static void main(String[] args) {
		Simulation sim = new Simulation();
		sim.setMode(1);
		try { Thread.sleep(10000); } catch(Exception e) {}
		sim.setMode(0);
	}
}
