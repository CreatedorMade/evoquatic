package evoquatic;

import java.awt.*;

public class CreationPanel extends HudPart {
	
	String[] sizeStrs = {"1km", "5km", "10km"};
	GenericRadioButton sizeButton = new GenericRadioButton(sizeStrs);
	
	String[] genrateStrs = {"5/min", "10/min", "20/min"};
	GenericRadioButton genrateButton = new GenericRadioButton(genrateStrs);
	
	String[] mutrateStrs = {"5%", "15%", "35%"};
	GenericRadioButton mutrateButton = new GenericRadioButton(mutrateStrs);
	
	public CreationPanel() {
		super.visible = true;
		super.bg = new Color(0f, 0f, 0f, 0.25f);
		super.width = 600;
		super.height = 300;
		
		sizeButton.x = 11;
		sizeButton.y = 10;
		sizeButton.width = 198;
		sizeButton.height = 20;
		super.children.add(sizeButton);
		super.children.add(genrateButton);
		super.children.add(mutrateButton);
		
	}
	
	public void draw(Graphics2D g, int ox, int oy) {
		
	}
}
