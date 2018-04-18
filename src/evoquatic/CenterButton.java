package evoquatic;

import java.awt.*;
import java.awt.event.*;

public class CenterButton extends HudPart {
	public CenterButton() {
		super.visible = true;
		super.width = 30;
		super.height = 30;
		super.bg = new Color(128, 128, 128);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void draw(Graphics2D g, int ox, int oy) {
		g.setColor(new Color(0, 0, 0));
		g.drawRect(ox, oy, width, height);
		
		g.setColor(new Color(255, 255, 255));
		g.drawLine(ox+5, oy+5, ox+25, oy+25);
		g.drawLine(ox+5, oy+25, ox+25, oy+5);
	}
	
	public void onClick(MouseEvent e, int mx, int my) {
		Console.log("Camera centered");
		HudPart.panel.camX = 0;
		HudPart.panel.camY = 0;
	}
}
