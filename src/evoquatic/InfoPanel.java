package evoquatic;

import java.awt.*;

public class InfoPanel extends HudPart {
	int state = 0;
	
	public InfoPanel() {
		super.visible = true;
		super.width = 200;
		super.bg = new Color(0f, 0f, 0f, 0.25f);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void draw(Graphics2D g, int ox, int oy) {
		g.setColor(Color.WHITE);
		
		Font f = new Font("DialogInput", Font.PLAIN, 20);
		g.setFont(f);
		FontMetrics m = g.getFontMetrics(f);
		
		if(state == 0) {
			super.height = 35;
			g.drawString("null selection", ox+100-m.stringWidth("null selection")/2, oy+25);
		}
	}
}
