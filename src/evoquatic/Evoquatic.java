package evoquatic;

import java.awt.geom.Point2D;

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
	
	public static double dtl(double x1, double y1, double x2, double y2, double xp, double yp) {
		
		//Do dtp calculations if the two points are the same
		if(x1 == x2 && y1 == y2) {
			return Point2D.distance(x1, y1, xp, yp);
		}
		
		//Faster algorithm for horizontal lines
		if(y1 == y2) {
			
		}
		
		return 0;
	}
}
