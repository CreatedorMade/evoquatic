package evoquatic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;

public class InfoButton extends HudPart {
	Polygon p = new Polygon();
	
	public InfoButton() {
		super.visible = true;
		super.bg = new Color(0.75f, 0.75f, 0.75f);
		super.x = 10;
		super.y = 10;
		super.height = 30;
		super.width = 30;
		
		p.addPoint(5, 24);
		p.addPoint(10, 17);
		p.addPoint(10, 5);
		p.addPoint(24, 5);
		p.addPoint(22, 7);
		p.addPoint(12, 7);
		p.addPoint(12, 17);
		p.addPoint(22, 17);
		p.addPoint(22, 7);
		p.addPoint(24, 5);
		p.addPoint(24, 19);
		p.addPoint(12, 19);
		p.addPoint(5, 25);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void onClick(MouseEvent e, int ox, int oy) {
		Console.log("Info tool");
		HudPart.panel.setTool(0);
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
