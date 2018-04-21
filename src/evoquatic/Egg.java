package evoquatic;

import java.awt.Color;

public class Egg extends AbstractNodeObject {
	
	public static final double DENSITY = 22.41;
	
	double progress = 0;
	double maxRadius = 1.5;
	int seconds = 300;
	double mass = 0;
	double radius = 0;
	
	public static final Color c = new Color(232, 209, 136);
	
	public Egg(double x, double y) {
		super.x = x;
		super.y = y;
	}
	
	@Override
	public void addInput(Neural n) {}

	@Override
	public Neural[] getInputs() {
		return null;
	}

	@Override
	public double getOutput() {
		return 0;
	}

	@Override
	public Color getColor() {
		return c;
	}

	@Override
	public double getMass() {
		return mass;
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public String[] getInfo() {
		String[] str = new String[4];

		str[0] = "egg";
		str[1] = "radius: "+((double) (Math.round(radius*100))/100)+"m";
		str[2] = "mass: "+((double) (Math.round(mass*100))/100)+"kg";
		str[3] = "progress: "+Math.round(progress*100)+"%";
		
		return str;
	}
	
	public void code(int time) {
		progress += 1/((double) (seconds)*60d);
		radius = (maxRadius*progress)*0.95+maxRadius*0.05;
		mass = ((4/3)*Math.PI*(radius))*DENSITY;
		
		if(progress >= 1) super.delete = true;
	}

}
