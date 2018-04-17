package evoquatic;

public class BaseHud extends HudPart {
	Toolbar toolbar = new Toolbar();
	InfoPanel infoPanel = new InfoPanel();
	
	public BaseHud() {
		super.visible = true;
		super.children.add(toolbar);
		super.children.add(infoPanel);
		infoPanel.y = 60;
	}
	
	public void onTick() {
		toolbar.width = super.width;
		infoPanel.x = super.width-210;
	}
}
