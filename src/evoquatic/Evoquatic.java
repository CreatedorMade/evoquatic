package evoquatic;

import javax.swing.*;

public class Evoquatic {
	public static void main(String[] args) {
		for(String s : args) if(s.equalsIgnoreCase("-d") || s.equalsIgnoreCase("-debug")) Console.init(true);
		Console.init(false);
		
		Simulation sim = new Simulation();
		
		JFrame frame = new JFrame("Evoquatic");
		EPanel panel = new EPanel(sim);
		frame.add(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		sim.state = Simulation.STATE_TITLE_MAIN;
	}
}
