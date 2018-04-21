package evoquatic;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PauseButton extends HudPart {
	Polygon p = new Polygon();
	
	public PauseButton() {
		super.visible = true;
		super.bg = new Color(0.75f, 0.75f, 0.75f);
		super.x = 50;
		super.y = 10;
		super.height = 30;
		super.width = 30;
		
		p.addPoint(5, 5);
		p.addPoint(11, 5);
		p.addPoint(11, 24);
		p.addPoint(5, 25);
		p.addPoint(5, 5);
		p.addPoint(18, 5);
		p.addPoint(24, 5);
		p.addPoint(24, 24);
		p.addPoint(18, 24);
		p.addPoint(18, 5);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void onClick(MouseEvent e, int ox, int oy) {
		Console.log("Simulation paused");
		HudPart.panel.sim.setMode(0);
		EPanel.frameDelay = 33333332L;
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
