package evoquatic;

import java.awt.*;

public class Toolbar extends HudPart {
	PauseButton pb = new PauseButton();
	RealtimeButton rb = new RealtimeButton();
	IncubateButton ib = new IncubateButton();
	
	public Toolbar() {
		super.visible = true;
		super.height = 50;
		super.bg = new Color(0f, 0f, 0f, 0.1f);
		super.children.add(new InfoButton());
		super.children.add(new SelectButton());
		super.children.add(new MeasureButton());
		super.children.add(pb);
		super.children.add(rb);
		super.children.add(ib);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void draw(Graphics2D g, int ox, int oy) {
		pb.x = super.width - 120;
		rb.x = super.width - 80;
		ib.x = super.width - 40;
		
		g.setColor(new Color(1f, 1f, 1f));
		g.drawLine(10+HudPart.panel.sim.tool*40, 45, 40+HudPart.panel.sim.tool*40, 45);
	}
}
