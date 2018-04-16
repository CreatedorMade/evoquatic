package evoquatic;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class EPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	private class graphicsTicker implements Runnable {
		EPanel panel;
		
		public graphicsTicker(EPanel panel) {
			this.panel = panel;
		}
		
		public void run() {
			while(panel.isVisible()) {
				Long time = System.nanoTime();
				panel.tick();
				sync(time + 33333332L);
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
	
	boolean enableUserZooming = false;
	
	public EPanel(Simulation sim) {
		this.sim = sim;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		new Thread(null, new graphicsTicker(this), "GraphicsTicker").start();
	}
	
	int tick = 0;
	int mouseX = 0;
	int mouseY = 0;
	
	float playFade = 0;
	float loadFade = 0;
	
	boolean overPlay = false;
	boolean overLoad = false;
	
	float lCancelFade = 0;
	boolean overLCancel = false;
	
	float beginFade = 0;
	boolean overBegin = false;
	
	void tick() {
		
		if(overPlay) playFade -= (playFade-1)/10f;
		else playFade -= playFade/10f;
		if(overLoad) loadFade -= (loadFade-1)/10f;
		else loadFade -= loadFade/10f;
		if(overLCancel) lCancelFade -= (lCancelFade-1)/10f;
		else lCancelFade -= lCancelFade/10f;
		if(overBegin) beginFade -= (beginFade-1)/10f;
		else beginFade -= beginFade/10f;
		
		zoom -= (zoom-zoomTarget)/10;
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
			g.drawString("cancel", (width-getFontMetrics(f).stringWidth("cancel"))/2+75, height/2+230);
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
		}
	}
	
	private void translateGraphics(Graphics2D g) {
		AffineTransform t = new AffineTransform();
		t.translate(getWidth()/2-camX, getHeight()/2-camY);
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
		mouseMoved(e);
		
		if(sim.state == Simulation.STATE_TITLE_MAIN) {
			if(overPlay) {
				sim.state = Simulation.STATE_TITLE_OPTIONS;
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
				zoomTarget = 1;
				sim.state = Simulation.STATE_GAME;
			}
		}
	}
	
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(enableUserZooming) {
			for(int i = 0; i < e.getWheelRotation(); i++) {
				zoomTarget /= 1.1;
			}
			for(int i = 0; i > e.getWheelRotation(); i--) {
				zoomTarget *= 1.1;
			}
		}
	}
}
