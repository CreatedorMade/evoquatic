package evoquatic;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MeasureButton extends HudPart {
	Polygon p = new Polygon();
	
	public MeasureButton() {
		super.visible = true;
		super.bg = new Color(0.75f, 0.75f, 0.75f);
		super.x = 90;
		super.y = 10;
		super.height = 30;
		super.width = 30;
		
		p.addPoint(5, 10);
		p.addPoint(14, 10);
		p.addPoint(14, 17);
		p.addPoint(23, 17);
		p.addPoint(23, 15);
		p.addPoint(24, 15);
		p.addPoint(24, 18);
		p.addPoint(14, 18);
		p.addPoint(14, 19);
		p.addPoint(5, 19);
		p.addPoint(5, 4);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void onClick(MouseEvent e, int ox, int oy) {
		Console.log("Measure tool");
		HudPart.panel.setTool(2);
	}
	
	public void draw(Graphics2D g, int ox, int oy) {
		g.setColor(new Color(0f, 0f, 0f));
		g.drawRect(ox, oy, 30, 30);
		g.setColor(new Color(1f, 1f, 1f));
		Polygon pc = new Polygon(p.xpoints, p.ypoints, p.npoints);
		pc.translate(ox, oy);
		g.fillPolygon(pc);
	}
}
