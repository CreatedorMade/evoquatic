package evoquatic;

import java.awt.*;

public class BaseHud extends HudPart {
	Toolbar toolbar = new Toolbar();
	InfoPanel infoPanel = new InfoPanel();
	ActionPanel actionPanel = new ActionPanel();
	CenterButton cb = new CenterButton();
	RulerButton rb = new RulerButton();
	
	public BaseHud() {
		super.visible = true;
		super.children.add(toolbar);
		super.children.add(infoPanel);
		super.children.add(actionPanel);
		super.children.add(cb);
		super.children.add(rb);
		infoPanel.y = 60;
		actionPanel.y = 60;
		cb.x = 10;
		cb.y = 100;
		rb.x = 10;
		rb.y = 60;
	}
	
	public void onTick() {
		toolbar.width = super.width;
		infoPanel.x = super.width-310;
		actionPanel.x = super.width-310;
	}
	
	public void draw(Graphics2D g, int ox, int oy) {
	}
}
