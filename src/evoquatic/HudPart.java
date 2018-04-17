package evoquatic;

import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class HudPart {
	
	public static EPanel panel;
	
	public ArrayList<HudPart> children = new ArrayList<HudPart>();
	
	public int x = 0;
	public int y = 0;
	public int width = 0;
	public int height = 0;
	public boolean visible = false;
	public boolean lastMouseConsumed = false;
	
	public boolean getConsumesMouse() {
		return false;
	}
	
	public Color bg = new Color(0f, 0f, 0f, 0f);
	
	public void onTick() {}
	
	public void onClick(MouseEvent e, int mx, int my) {}
	
	public final void click(MouseEvent e) {
		int mx = e.getX() - x;
		int my = e.getY() - y;
		
		boolean consumed = false;
		for(HudPart h : children) {
			if(h.visible && mx > h.x && mx < h.x+h.width && my > h.y && my < h.y+h.height) {
				h.click(e, x, y);
				if(h.getConsumesMouse()) {
					consumed = true;
					break;
				}
			}
		}
		
		lastMouseConsumed = consumed;
		if(!consumed) onClick(e, mx, my);
	}
	
	public final void click(MouseEvent e, int ox, int oy) {
		int mx = e.getX() - x - ox;
		int my = e.getY() - y - oy;
		
		boolean consumed = false;
		for(HudPart h : children) {
			if(mx > h.x && mx < h.x+h.width && my > h.y && my < h.y+h.height) {
				h.click(e, x + ox, y + oy);
				if(h.getConsumesMouse()) {
					consumed = true;
					break;
				}
			}
		}
		
		lastMouseConsumed = consumed;
		if(!consumed) onClick(e, mx, my);
	}
	
	public final void render(Graphics2D g) {
		if(visible) {
			onTick();
			
			g.setColor(bg);
			g.fillRect(x, y, width, height);
			
			for(int i = children.size()-1; i >= 0; i--) {
				children.get(i).render(g, x, y);
			}
			draw(g, x, y);
		}
	}
	
	public final void render(Graphics2D g, int ox, int oy) {
		if(visible) {
			onTick();
			
			g.setColor(bg);
			g.fillRect(x+ox, y+oy, width, height);
			
			for(int i = children.size()-1; i >= 0; i--) {
				children.get(i).render(g, x, y);
			}
			draw(g, x+ox, y+oy);
		}
	}
	
	public void draw(Graphics2D g, int ox, int oy) {
		
	}
}
