package evoquatic;

import java.awt.*;
import java.awt.event.MouseEvent;

public class IncubateButton extends HudPart {
	Polygon p = new Polygon();
	
	public IncubateButton() {
		super.visible = true;
		super.bg = new Color(0.75f, 0.75f, 0.75f);
		super.x = 50;
		super.y = 10;
		super.height = 30;
		super.width = 30;
		
		p.addPoint(5, 5);
		p.addPoint(10, 10);
		p.addPoint(10, 5);
		p.addPoint(15, 10);
		p.addPoint(15, 5);
		p.addPoint(24, 14);
		p.addPoint(24, 15);
		p.addPoint(15, 24);
		p.addPoint(15, 19);
		p.addPoint(10, 24);
		p.addPoint(10, 19);
		p.addPoint(5, 24);
		p.addPoint(5, 19);
		p.addPoint(5, 5);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void onClick(MouseEvent e, int ox, int oy) {
		Console.log("Incubation speed - rendering slowed");
		HudPart.panel.sim.setMode(2);
		EPanel.frameDelay = 1000000000L;
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
