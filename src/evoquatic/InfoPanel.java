package evoquatic;

import java.awt.*;

public class InfoPanel extends HudPart {
	
	public InfoPanel() {
		super.visible = true;
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
			super.height = 45;
			String[] str = HudPart.panel.selection.getInfo();
			for(int i = 0; i < str.length; i++) {
				if(i==0) {
					g.drawString(str[0], ox+150-m.stringWidth(str[0])/2, oy+25);
				} else {
					if(i==1) g.setFont(new Font("DialogInput", Font.PLAIN, 15));
					g.drawString(str[i], ox+10, oy+50+((i-1)*15));
					super.height += 15;
				}
			}
		}
	}
}
