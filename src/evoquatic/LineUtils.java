package evoquatic;

import java.awt.geom.Point2D;

public class LineUtils {
	static double dtl(int px, int py, int lx1, int ly1, int lx2, int ly2) {
		if(lx1 == lx2) {
			return Math.abs(px-lx1);
		} else if(ly1 == ly2) {
			return Math.abs(py-ly1);
		} else {
			double slope = (ly1-ly2)/(lx1-lx2);
			double intercept = ly1 - slope * lx1;
			
			double pSlope = 1/slope;
			double pIntercept = py - pSlope * px;
			
			double intersectX = (pIntercept-intercept)/(slope-pSlope);
			double intersectY = slope*intersectX+intercept;
			
			return Point2D.distance(px, py, intersectX, intersectY);
		}
	}
}
