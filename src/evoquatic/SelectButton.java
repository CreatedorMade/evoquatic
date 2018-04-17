package evoquatic;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectButton extends HudPart {
	Polygon p = new Polygon();
	
	public SelectButton() {
		super.visible = true;
		super.bg = new Color(0.75f, 0.75f, 0.75f);
		super.x = 50;
		super.y = 10;
		super.height = 30;
		super.width = 30;
		
		p.addPoint(5, 5);
		p.addPoint(23, 12);
		p.addPoint(19, 16);
		p.addPoint(25, 25);
		p.addPoint(16, 19);
		p.addPoint(12, 23);
		p.addPoint(5, 5);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void onClick(MouseEvent e, int ox, int oy) {
		Console.log("Select tool");
		HudPart.panel.setTool(1);
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
