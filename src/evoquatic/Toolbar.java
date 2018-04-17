package evoquatic;

import java.awt.*;

public class Toolbar extends HudPart {
	public Toolbar() {
		super.visible = true;
		super.height = 50;
		super.bg = new Color(0f, 0f, 0f, 0.1f);
		super.children.add(new InfoButton());
		super.children.add(new SelectButton());
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void draw(Graphics2D g, int ox, int oy) {
		g.setColor(new Color(1f, 1f, 1f));
		g.drawLine(10+HudPart.panel.sim.tool*40, 45, 40+HudPart.panel.sim.tool*40, 45);
	}
}
