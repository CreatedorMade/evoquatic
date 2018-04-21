package evoquatic;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class EPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private static ArrayList<String> console = new ArrayList<String>();
	private static int consoleLength = 0;
	private static int consoleTimer = 0;
	
	AbstractNodeObject selection = null;
	
	public static long frameDelay = 33333332L;
	
	public static void log(String s) {
		console.add(s);
		consoleLength++;
		consoleTimer = 75;
	}
	
	public double sx(int mx) {
		return ((((double) (mx))-getWidth()/2)/zoom - camX)/100;
	}
	
	public double sy(int mx) {
		return ((((double) (mx))-getHeight()/2)/zoom - camY)/100;
	}
	
	private class graphicsTicker implements Runnable {
		EPanel panel;
		
		public graphicsTicker(EPanel panel) {
			this.panel = panel;
		}
		
		public void run() {
			while(panel.isVisible()) {
				Long time = System.nanoTime();
				panel.tick();
				sync(time + frameDelay);
			}
		}
		
		private void sync(Long time) {
			while(System.nanoTime() < time) {
				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					
				}
			}
		}
	}
	
	Simulation sim;
	OpenSimplexNoise noise = new OpenSimplexNoise();
	
	double camX = 0;
	double camY = 0;
	double zoom = 1;
	double zoomTarget = 1;
	
	BaseHud baseHud = new BaseHud();
	CreationPanel createHud = new CreationPanel();
	
	boolean enableUserZooming = false;
	boolean enableRuler = false;
	
	boolean mouseDown = false;
	boolean dragging = false;
	boolean measuring = false;
	
	public EPanel(Simulation sim) {
		HudPart.panel = this;
		this.sim = sim;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		new Thread(null, new graphicsTicker(this), "GraphicsTicker").start();
	}
	
	int tick = 0;
	int mouseX = 0;
	int mouseY = 0;
	int dmx = 0;
	int dmy = 0;
	int mmx = 0;
	int mmy = 0;
	double dcx = 0;
	double dcy = 0;
	
	float playFade = 0;
	float loadFade = 0;
	
	boolean overPlay = false;
	boolean overLoad = false;
	
	float lCancelFade = 0;
	boolean overLCancel = false;
	
	float beginFade = 0;
	boolean overBegin = false;
	
	void tick() {
		
		if(consoleTimer > 0) consoleTimer--;
		else if(consoleLength > 0) {
			consoleLength--;
			console.remove(0);
			consoleTimer = 75;
		}
		
		if(!dragging) {
			dmx = mouseX;
			dmy = mouseY;
			dcx = camX;
			dcy = camY;
		} else {
			camX = dcx + (dmx - mouseX)/zoomTarget;
			camY = dcy + (dmy - mouseY)/zoomTarget;
		}
		
		if(!measuring) {
			mmx = mouseX;
			mmy = mouseY;
		}
		
		if(overPlay) playFade -= (playFade-1)/10f;
		else playFade -= playFade/10f;
		if(overLoad) loadFade -= (loadFade-1)/10f;
		else loadFade -= loadFade/10f;
		if(overLCancel) lCancelFade -= (lCancelFade-1)/10f;
		else lCancelFade -= lCancelFade/10f;
		if(overBegin) beginFade -= (beginFade-1)/10f;
		else beginFade -= beginFade/10f;
		
		//Recenter the camera if it's not in the simulation
		if(Point2D.distance(camX, camY, 0, 0) > sim.size*50) {
			double angle = Math.atan2(camY, camX);
			camX -= (camX-(Math.cos(angle)*sim.size*50))/10f;
			camY -= (camY-(Math.sin(angle)*sim.size*50))/10f;
		}
		
		zoom -= (zoom-zoomTarget)/10f;
		tick++;
		repaint();
	}
	
	public Dimension getPreferredSize() { return new Dimension(800, 600); }
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
			
		int width = getWidth();
		int height = getHeight();

		g.setBackground(new Color(64, 64, 64));
		g.clearRect(0, 0, width, height);
		
		translateGraphics(g);
		for(int i = 9; i >= 0; i--) {
			g.setColor(new Color(Color.HSBtoRGB(0.588f, 0.5f, 0.95f-((float) (i+1)/50f))));
			g.fillOval((int) Math.round(-sim.size*50*((float) (i+1)/10)), (int) Math.round(-sim.size*50*((float) (i+1)/10)), (int) Math.round(sim.size*100*((float) (i+1)/10)), (int) Math.round(sim.size*100*((float) (i+1)/10)));
		}
		g.setTransform(new AffineTransform());
		
		if(sim.state == Simulation.STATE_LOADING) {
			//Draw a spinner
			g.setStroke(new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g.setColor(new Color(1f, 1f, 1f, 0.25f));
			g.drawArc(width/2-50, height/2-50, 100, 100, -tick*48, 180);
			
			//Draw the loading text
			g.setColor(new Color(1f, 1f, 1f));
			Font f = new Font("DialogInput", Font.PLAIN, 50);
			g.setFont(f);
			g.drawString("initializing", (width-getFontMetrics(f).stringWidth("initializing"))/2, (height+20)/2);
		} else if(sim.state == Simulation.STATE_TITLE_MAIN) {
			//Add a "breathing" effect to the zoom
			zoomTarget = Math.sin((float) (tick)/100)*0.05+1;
			
			//Draw the title, a line and the subtitle
			Font f = new Font("DialogInput", Font.PLAIN, 50);
			g.setFont(f);
			g.setColor(new Color(1f, 1f, 1f, Math.min(1, (float) (tick)/60)));
			g.drawString("evoquatic", (width-getFontMetrics(f).stringWidth("evoquatic"))/2, height/2-100-(int) ((1f/Math.min(1f, (float) (tick)/60f))*50f));
			f = new Font("DialogInput", Font.PLAIN, 15);
			g.setFont(f);
			g.drawString("an aquatic evolution simulator", (width-getFontMetrics(f).stringWidth("an aquatic evolution simulator"))/2, height/2-120);
			g.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g.drawLine(width/2-(int) ((1f/Math.min(1f, (float) (tick)/60f))*120f), height/2-135, width/2+(int) ((1f/Math.min(1f, (float) (tick)/60f))*120f), height/2-135);
			
			//Draw the play and load buttons
			g.setColor(new Color(1f, 1f, 1f, playFade/8+0.125f));
			g.fillOval((int) (width/2-75-50*playFade), (int) (height/2-50*playFade), (int) (100*playFade), (int) (100*playFade));
			g.setColor(new Color(1f, 1f, 1f, loadFade/8+0.125f));
			g.fillOval((int) (width/2+75-50*loadFade), (int) (height/2-50*loadFade), (int) (100*loadFade), (int) (100*loadFade));
			g.setColor(new Color(1f, 1f, 1f));
			f = new Font("DialogInput", Font.PLAIN, 25);
			g.setFont(f);
			g.drawString("new", (width-getFontMetrics(f).stringWidth("new"))/2-75, height/2+5);
			g.drawString("open", (width-getFontMetrics(f).stringWidth("open"))/2+75, height/2+5);
		} else if(sim.state == Simulation.STATE_TITLE_OPTIONS) {
			//Add a "breathing" effect to the zoom
			zoomTarget = (Math.sin((float) (tick)/50)*0.1+1)*2.5;
			
			//Draw the page title
			Font f = new Font("DialogInput", Font.PLAIN, 50);
			g.setFont(f);
			g.setColor(new Color(1f, 1f, 1f));
			g.drawString("simulation options", (width-getFontMetrics(f).stringWidth("simulation options"))/2, height/2-250);
			f = new Font("DialogInput", Font.PLAIN, 20);
			g.setFont(f);
			g.setColor(new Color(1f, 0.75f, 0.75f));
			g.drawString("these cannot be changed", (width-getFontMetrics(f).stringWidth("these cannot be changed"))/2, height/2-225);
			
			//Draw the cancel button
			g.setColor(new Color(1f, 1f, 1f, lCancelFade/8+0.125f));
			g.fillOval((int) (width/2-50*lCancelFade)-75, (int) (height/2-50*lCancelFade)+225, (int) (100*lCancelFade), (int) (100*lCancelFade));
			g.setColor(new Color(1f, 1f, 1f));
			f = new Font("DialogInput", Font.PLAIN, 25);
			g.setFont(f);
			g.drawString("cancel", (width-getFontMetrics(f).stringWidth("cancel"))/2-75, height/2+230);
			
			//Draw the begin button
			g.setColor(new Color(1f, 1f, 1f, beginFade/8+0.125f));
			g.fillOval((int) (width/2-50*beginFade)+75, (int) (height/2-50*beginFade)+225, (int) (100*beginFade), (int) (100*beginFade));
			g.setColor(new Color(1f, 1f, 1f));
			g.drawString("create", (width-getFontMetrics(f).stringWidth("create"))/2+75, height/2+230);
			
			//Draw the hud
			createHud.x = (width-createHud.width)/2;
			createHud.y = (height-createHud.height)/2;
			createHud.render(g);
		} else if(sim.state == Simulation.STATE_TITLE_LOAD) {
			//Add a "breathing" effect to the zoom
			zoomTarget = (Math.sin((float) (tick)/100)*0.05+1)*0.5;
			
			//Draw a spinner
			g.setStroke(new BasicStroke(10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
			g.setColor(new Color(1f, 1f, 1f, 0.25f));
			g.drawArc(width/2-100, height/2-100, 200, 200, -tick*48, 180);
			
			//Draw the cancel button
			g.setColor(new Color(1f, 1f, 1f, lCancelFade/8+0.125f));
			g.fillOval((int) (width/2-50*lCancelFade), (int) (height/2-50*lCancelFade), (int) (100*lCancelFade), (int) (100*lCancelFade));
			g.setColor(new Color(1f, 1f, 1f));
			Font f = new Font("DialogInput", Font.PLAIN, 25);
			g.setFont(f);
			g.drawString("cancel", (width-getFontMetrics(f).stringWidth("cancel"))/2, height/2+5);
			
			g.setColor(new Color(0f, 0f, 0f));
			g.fillRect(width/2-350, height/2-50, 700, 100);
			g.setColor(new Color(1f, 0.25f, 0.25f));
			g.drawString("currently nonfunctional (click in center)", (width-getFontMetrics(f).stringWidth("currently nonfunctional (click in center)"))/2, height/2+5);
		} else if(sim.state == Simulation.STATE_GAME) {
			//Center the camera over the selection
			if(selection != null) {
				camX -= (camX-selection.x*100)/10f;
				camY -= (camY-selection.y*100)/10f;
			}
			
			//Draw the vector objects
			AffineTransform t = new AffineTransform();
			t.translate((getWidth()/2-camX*zoom), (getHeight()/2-camY*zoom));
			t.scale(zoom, zoom);
			g.setTransform(t);
			for(AbstractVectorObject v : sim.vectors) {
				
			}
			
			//Draw the node objects
			for(AbstractNodeObject n : sim.nodes) {
				//Determine if it's onscreen
				int sx = (int) ((n.x*100*zoom)-(camX*zoom)+(width/2));
				int sy = (int) ((n.y*100*zoom)-(camY*zoom)+(height/2));
				int ss = (int) (n.getRadius()*zoom*100);
				
				if(sx+ss >= 0 && sx-ss <= width && sy+ss >= 0 && sy-ss <= height) {
					if(ss != 0) {
						int r = (int) (n.getRadius()*100);
					
						g.setColor(n.getColor());
						g.fillOval((int) (n.x*100)-r, (int) (n.y*100)-r, r*2, r*2);
						g.setColor((n.equals(selection)) ? Color.WHITE : Color.BLACK);
						g.drawOval((int) (n.x*100)-r, (int) (n.y*100)-r, r*2, r*2);
					} else {
						g.setColor((n.equals(selection)) ? Color.WHITE : Color.BLACK);
						g.fillOval((int) (n.x*100), (int) (n.y*100), (int) (2/zoom), (int) (2/zoom));
					}
				}
			}
			
			g.setTransform(new AffineTransform());
			//Draw the ruler, if it's enabled
			g.setColor(new Color(1f, 1f, 1f));
			if(enableRuler) {
				int mw = width-400;
				
				Font f = new Font("DialogInput", Font.PLAIN, 15);
				FontMetrics m = getFontMetrics(f);
				g.setFont(f);
				
				//Centimeters
				if(zoom > 10 && zoom < mw-50) {
					g.drawLine(50+(int) (zoom), 80, 50+(int) (zoom), 100);
					g.drawString("1cm", 50+(int) (zoom)-m.stringWidth("1cm")/2, 75);
					g.drawString((int) (zoom)+"px", 50+(int) (zoom)-m.stringWidth((int) (zoom)+"px")/2, 115);
				}
				//Decimeters
				if(10 * zoom > 10 && 10*zoom < mw-50) {
					g.drawLine(50+(int) (10*zoom), 80, 50+(int) (10*zoom), 100);
					g.drawString("1dm", 50+(int) (10*zoom)-m.stringWidth("1dm")/2, 75);
					g.drawString((int) (10*zoom)+"px", 50+(int) (10*zoom)-m.stringWidth((int) (10*zoom)+"px")/2, 115);
				}
				//Meters
				if(100 * zoom > 10 && 100*zoom < mw-50) {
					g.drawLine(50+(int) (100*zoom), 80, 50+(int) (100*zoom), 100);
					g.drawString("1m", 50+(int) (100*zoom)-m.stringWidth("1m")/2, 75);
					g.drawString((int) (100*zoom)+"px", 50+(int) (100*zoom)-m.stringWidth((int) (100*zoom)+"px")/2, 115);
				}
				//Decameters
				if(1000 * zoom > 10 && 1000*zoom < mw-50) {
					g.drawLine(50+(int) (1000*zoom), 80, 50+(int) (1000*zoom), 100);
					g.drawString("1dam", 50+(int) (1000*zoom)-m.stringWidth("1dam")/2, 75);
					g.drawString((int) (1000*zoom)+"px", 50+(int) (1000*zoom)-m.stringWidth((int) (1000*zoom)+"px")/2, 115);
				}
				//Hectometers
				if(10000 * zoom > 10 && 10000*zoom < mw-50) {
					g.drawLine(50+(int) (10000*zoom), 80, 50+(int) (10000*zoom), 100);
					g.drawString("1hm", 50+(int) (10000*zoom)-m.stringWidth("1hm")/2, 75);
					g.drawString((int) (10000*zoom)+"px", 50+(int) (10000*zoom)-m.stringWidth((int) (10000*zoom)+"px")/2, 115);
				}
				//Kilometers
				if(100000 * zoom > 10 && 100000*zoom < mw-50) {
					g.drawLine(50+(int) (100000*zoom), 80, 50+(int) (100000*zoom), 100);
					g.drawString("1km", 50+(int) (100000*zoom)-m.stringWidth("1km")/2, 75);
					g.drawString((int) (100000*zoom)+"px", 50+(int) (100000*zoom)-m.stringWidth((int) (100000*zoom)+"px")/2, 115);
				}
				
				String str;
				
				double distance = Point2D.distance(sx(50), sy(90), sx(50+mw), sy(90));
				if(distance < 0.1) str = (double) (Math.round(distance*10000))/100+"cm";
				else if(distance < 1) str = (double) (Math.round(distance*1000))/100+"dm";
				else if(distance < 10) str = (double) (Math.round(distance*100))/100+"m";
				else if(distance < 100) str = (double) (Math.round(distance*10))/100+"dam";
				else if(distance < 1000) str = (double) (Math.round(distance))/100+"hm";
				else str = (double) (Math.round(distance/10))/100+"km";
				
				g.drawLine(50, 80, 50, 100);
				g.drawLine(50, 90, 50+mw, 90);
				g.drawLine(50+mw, 80, 50+mw, 100);
				g.drawString(mw+"px", 50+mw-m.stringWidth(mw+"px")/2, 115);
				g.drawString(str, 50+mw-m.stringWidth(str)/2, 75);
			}
			
			//Draw the measuring tape, if needed
			if(measuring) {
				Font f = new Font("DialogInput", Font.PLAIN, 15);
				FontMetrics m = getFontMetrics(f);
				g.setFont(f);
				
				g.drawOval(mmx-4, mmy-4, 9, 9);
				g.drawOval(mouseX-4, mouseY-4, 9, 9);
				g.drawLine(mmx, mmy, mouseX, mouseY);
				
				String str;
				
				double distance = Point2D.distance(sx(mmx), sy(mmy), sx(mouseX), sy(mouseY));
				if(distance < 1) str = (double) (Math.round(distance*10000))/100+"cm";
				else if(distance < 100) str = (double) (Math.round(distance*100))/100+"m";
				else if(distance < 1000) str = Math.round(distance)+"m";
				else str = (double) (Math.round(distance/10))/100+"km";
				
				if(mouseX > mmx) g.drawString(str, mouseX+10, mouseY-10);
				else if(mouseX <= mmx) g.drawString(str, mouseX-10-m.stringWidth(str), mouseY-10);
			}
			
			//Draw the hud elements
			baseHud.width = width;
			baseHud.height = height;
			baseHud.render(g);
		}

		g.setTransform(new AffineTransform());
		
		
		//Draw the console
		Font f = new Font("Monospaced", Font.PLAIN, 18);
		g.setFont(f);
		int i = 0;
		for(String s : console) {
			int k = console.size() - i - 1;
			g.setColor(new Color(0f, 0f, 0f, 0.25f));
			g.fillRect(0, height-(k+1)*20, getFontMetrics(f).stringWidth(s)+4, 20);
			g.setColor(new Color(1f, 1f, 1f));
			g.drawString(s, 2, height-k*20-4);
			i++;
		}
	}
	
	private void translateGraphics(Graphics2D g) {
		AffineTransform t = new AffineTransform();
		t.translate((getWidth()/2-camX*zoom), (getHeight()/2-camY*zoom));
		t.scale(zoom, zoom);
		g.transform(t);
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if(sim.state == Simulation.STATE_TITLE_MAIN) {
			overPlay = Point2D.distance(mouseX, mouseY, getWidth()/2-75, getHeight()/2) <= 50;
			overLoad = Point2D.distance(mouseX, mouseY, getWidth()/2+75, getHeight()/2) <= 50;
		} else if(sim.state == Simulation.STATE_TITLE_LOAD) {
			overLCancel = Point2D.distance(mouseX, mouseY, getWidth()/2, getHeight()/2) <= 50;
		} else if(sim.state == Simulation.STATE_TITLE_OPTIONS) {
			overLCancel = Point2D.distance(mouseX, mouseY, getWidth()/2-75, getHeight()/2+225) <= 50;
			overBegin = Point2D.distance(mouseX, mouseY, getWidth()/2+75, getHeight()/2+225) <= 50;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		mouseMoved(e);
		
		if(sim.state == Simulation.STATE_TITLE_MAIN) {
			if(overPlay) {
				sim.state = Simulation.STATE_TITLE_OPTIONS;
				createHud = new CreationPanel();
				overPlay = false;
			} else if(overLoad) {
				sim.state = Simulation.STATE_TITLE_LOAD;
				overLoad = false;
			}
		} else if(sim.state == Simulation.STATE_TITLE_LOAD) {
			if(overLCancel) {
				sim.state = Simulation.STATE_TITLE_MAIN;
				overLCancel = false;
			}
		} else if(sim.state == Simulation.STATE_TITLE_OPTIONS) {
			if(overLCancel) {
				sim.state = Simulation.STATE_TITLE_MAIN;
				overLCancel = false;
			} else if(overBegin) {
				Console.log("Creating simulation...");
				if(createHud.sizeButton.current == 0) sim.size = 250;
				else if(createHud.sizeButton.current == 1) sim.size = 500;
				else if(createHud.sizeButton.current == 2) sim.size = 1000;
				if(createHud.genrateButton.current == 0) sim.genDelay = 6000;
				else if(createHud.genrateButton.current == 1) sim.genDelay = 360;
				else if(createHud.genrateButton.current == 2) sim.genDelay = 180;
				if(createHud.foodrateButton.current == 0) sim.foodDelay = 108;
				else if(createHud.foodrateButton.current == 1) sim.foodDelay = 36;
				else if(createHud.foodrateButton.current == 2) sim.foodDelay = 12;
				if(createHud.foodsizeButton.current == 0) sim.foodSize = 1;
				else if(createHud.foodsizeButton.current == 1) sim.foodSize = 2;
				else if(createHud.foodsizeButton.current == 2) sim.foodSize = 4;
				sim.state = Simulation.STATE_GAME;
				enableUserZooming = true;
				zoomTarget = 1;
				sim.setMode(1);
				Console.log("Simulation created.");
			} else {
				createHud.click(e);
			}
		} else if(sim.state == Simulation.STATE_GAME) {
			baseHud.click(e);
			if(!baseHud.lastMouseConsumed) {
				boolean clicked = sim.click(e);
				dragging = clicked && sim.tool != 2;
				measuring = clicked && sim.tool == 2;
			}
		}
	}
	
	public void mouseEntered(MouseEvent arg0) {
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		mouseDown = false;
		dragging = false;
		measuring = false;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
		mouseDown = false;
		dragging = false;
		measuring = false;
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(enableUserZooming && !dragging && !measuring) {
			for(int i = 0; i < e.getWheelRotation(); i++) {
				zoomTarget /= 1.1;
			}
			for(int i = 0; i > e.getWheelRotation(); i--) {
				zoomTarget *= 1.1;
			}
			zoomTarget = Math.max(0.005, Math.min(1000, zoomTarget));
		}
	}
	
	public void setTool(int t) {
		sim.tool = t;
		
		baseHud.infoPanel.visible = t == 0;
		baseHud.actionPanel.visible = t == 1;
	}
}
