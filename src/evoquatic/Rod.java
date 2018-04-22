package evoquatic;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Rod extends AbstractVectorObject {
	
	double length;

	public Rod(AbstractNodeObject n1, AbstractNodeObject n2) {
		super(n1, n2);
		length = Point2D.distance(n1.x, n1.y, n2.x, n2.y);
	}

	@Override
	public Color getColor() {
		return Color.GRAY;
	}

	@Override
	public double getTargetLength() {
		return length;
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

}
