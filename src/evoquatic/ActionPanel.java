package evoquatic;

import java.awt.*;

public class ActionPanel extends HudPart {
	
	public ActionPanel() {
		super.width = 300;
		super.bg = new Color(0f, 0f, 0f, 0.25f);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void draw(Graphics2D g, int ox, int oy) {
		g.setColor(Color.WHITE);
		
		Font f = new Font("DialogInput", Font.PLAIN, 20);
		g.setFont(f);
		FontMetrics m = g.getFontMetrics(f);
		
		if(HudPart.panel.selection == null) {
			super.height = 35;
			g.drawString("null selection", ox+150-m.stringWidth("null selection")/2, oy+25);
		} else {
			super.height = 35;
			String str = HudPart.panel.selection.getInfo()[0];
			g.drawString(str, ox+150-m.stringWidth(str)/2, oy+25);
		}
	}
}
