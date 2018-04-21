package evoquatic;

import java.awt.Color;

public class GreenFood extends AbstractNodeObject {
	
	public static final double CALORIC_DENSITY = 400;
	public static final double DENSITY = 12;
	
	public GreenFood(double radius, double x, double y) {
		this.radius = radius;
		super.x = x;
		super.y = y;
		super.mass = ((4/3)*Math.PI*(radius))*DENSITY;
		calories = ((4/3)*Math.PI*(radius))*CALORIC_DENSITY;
	}
	
	double radius = 0;
	double calories;
	
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
		return new Color(153, 252, 92);
	}

	@Override
	public double getMass() {
		return super.mass;
	}

	@Override
	public double getRadius() {
		return radius;
	}
	
	public double getCalories() {
		return calories;
	}

	@Override
	public String[] getInfo() {
		String str[] = new String[4];
		
		str[0] = "green food";
		str[1] = "radius: "+((double) (Math.round(radius*100))/100)+"m";
		str[2] = "mass: "+((double) (Math.round(mass*100))/100)+"kg";
		str[3] = "calories: "+((double) (Math.round(calories*100))/100)+"Cal";
		
		return str;
	}
}
