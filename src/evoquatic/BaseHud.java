package evoquatic;

public class BaseHud extends HudPart {
	Toolbar toolbar = new Toolbar();
	InfoPanel infoPanel = new InfoPanel();
	CenterButton cb = new CenterButton();
	RulerButton rb = new RulerButton();
	
	public BaseHud() {
		super.visible = true;
		super.children.add(toolbar);
		super.children.add(infoPanel);
		super.children.add(cb);
		super.children.add(rb);
		infoPanel.y = 60;
		cb.x = 10;
		cb.y = 100;
		rb.x = 10;
		rb.y = 60;
	}
	
	public void onTick() {
		toolbar.width = super.width;
		infoPanel.x = super.width-210;
	}
}
