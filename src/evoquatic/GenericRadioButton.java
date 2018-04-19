package evoquatic;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GenericRadioButton extends HudPart {
	int divs = 1;
	int current = 0;
	String[] strs;
	
	public GenericRadioButton(String...s) {
		strs = s;
		divs = s.length;
		super.visible = true;
		super.bg = new Color(0.125f, 0.125f, 0.125f);
	}
	
	public boolean getConsumesMouse() {return true;}
	
	public void onClick(MouseEvent e, int mx, int my) {
		int divWidth = super.width/divs;
		current = mx/divWidth;
	}
	
	public void draw(Graphics2D g, int ox, int oy) {
		int divWidth = super.width/divs;
		
		Font f = new Font("DialogInput", Font.PLAIN, 15);
		g.setFont(f);
		FontMetrics m = g.getFontMetrics(f);
		
		for(int i = 0; i < divs; i++) {
			//Highlight if div is selected
			if(current == i) {
				g.setColor(new Color(0.25f, 0.25f, 0.25f));
				g.fillRect(divWidth*(i)+ox, oy, divWidth, super.height);
			}

			//Draw a dividing line
			if(i != 0) {
				g.setColor(new Color(1f, 1f, 1f));
				g.drawLine(ox+(divWidth*i), oy, ox+(divWidth*i), oy+super.height-1);
			}
			
			//Draw the text
			g.setColor(new Color(1f, 1f, 1f));
			g.drawString(strs[i], divWidth/2+ox+(divWidth*(i))-m.stringWidth(strs[i])/2, oy+17);
		}
	}
}
