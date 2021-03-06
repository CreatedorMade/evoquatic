package evoquatic;

import java.awt.*;
import java.awt.event.*;

public class RulerButton extends HudPart {
	public RulerButton() {
		super.visible = true;
		super.width = 30;
		super.height = 30;
		super.bg = new Color(64, 64, 64);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void draw(Graphics2D g, int ox, int oy) {
		g.setColor(new Color(0, 0, 0));
		g.drawRect(ox, oy, 30, 30);
		
		g.setColor(new Color(255, 255, 255));
		g.drawLine(ox+5, oy+20, ox+5, oy+25);
		g.drawLine(ox+25, oy+20, ox+25, oy+25);
		g.drawLine(ox+5, oy+25, ox+25, oy+25);
		
		g.setFont(new Font("DialogInput", Font.PLAIN, 25));
		g.drawString("m", ox+8, oy+18);
		
		super.bg = (HudPart.panel.enableRuler) ? new Color(128, 128, 128) : new Color(64, 64, 64);
	}
	
	public void onClick(MouseEvent e, int mx, int my) {
		HudPart.panel.enableRuler = !HudPart.panel.enableRuler;
		Console.log((HudPart.panel.enableRuler) ? "Ruler on" : "Ruler off");
	}
}
