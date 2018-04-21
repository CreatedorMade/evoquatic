package evoquatic;

import java.awt.*;

public class CreationPanel extends HudPart {
	GenericRadioButton sizeButton = new GenericRadioButton(".25km", ".5km", "1km");
	GenericRadioButton genrateButton = new GenericRadioButton("6/min", "10/min", "20/min");
	GenericRadioButton mutrateButton = new GenericRadioButton("5%", "15%", "35%");
	GenericRadioButton foodrateButton = new GenericRadioButton("Low", "Medium", "High");
	GenericRadioButton foodsizeButton = new GenericRadioButton("10cm", "20cm", "40cm");
	GenericRadioButton ooerButton = new GenericRadioButton("lemon", "Bepis");
	
	public CreationPanel() {
		super.visible = true;
		super.bg = new Color(0f, 0f, 0f, 0.25f);
		super.width = 500;
		super.height = 155;
		
		sizeButton.x = 11;
		sizeButton.y = 25;
		sizeButton.width = 198;
		sizeButton.height = 20;
		mutrateButton.x = 11;
		mutrateButton.y = 75;
		mutrateButton.width = 198;
		mutrateButton.height = 20;
		foodsizeButton.x = 11;
		foodsizeButton.y = 125;
		foodsizeButton.width = 198;
		foodsizeButton.height = 20;
		
		genrateButton.x = 291;
		genrateButton.y = 25;
		genrateButton.width = 198;
		genrateButton.height = 20;
		foodrateButton.x = 291;
		foodrateButton.y = 75;
		foodrateButton.width = 198;
		foodrateButton.height = 20;
		ooerButton.x = 291;
		ooerButton.y = 125;
		ooerButton.width = 198;
		ooerButton.height = 20;
		super.children.add(sizeButton);
		super.children.add(genrateButton);
		super.children.add(mutrateButton);
		super.children.add(foodrateButton);
		super.children.add(foodsizeButton);
		super.children.add(ooerButton);
	}
	
	public void draw(Graphics2D g, int ox, int oy) {
		Font f = new Font("DialogInput", Font.PLAIN, 15);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawString("world bounds", ox+57, oy+20);
		g.drawString("spawn rate", ox+344, oy+20);
		g.drawString("mutation rate", ox+53, oy+70);
		g.drawString("food amount", ox+339, oy+70);
		g.drawString("food radius", ox+69, oy+120);
		g.drawString("ooer", ox+372, oy+120);
	}
}
