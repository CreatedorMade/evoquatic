package evoquatic;

import javax.swing.JPanel;

import java.awt.event.MouseEvent;
import java.awt.event.*;
import java.awt.*;

@SuppressWarnings("serial")
public class EPanel extends JPanel implements MouseListener {
	
	boolean mouseInFrame = false;
	Simulation sim;
	boolean doRendering = true;
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		int width = this.getWidth();
		int height = this.getHeight();
		
		if(doRendering) {
			g.setBackground(new Color(224, 255, 253));
			g.clearRect(0, 0, width, height);
			
			if(sim.state==0) {
				g.setColor(new Color(0f, 0.25f, 0.25f));
				g.fillRect(width/2-25, height/2-25, 20, 50);
				g.fillRect(width/2+5, height/2-25, 20, 50);
			}
			
			
		} else {
			g.setBackground(new Color(0f, 0f, 0f));
			g.clearRect(0, 0, width, height);
			
			g.setFont(new Font("sans-serif", Font.PLAIN, 20));
			g.setColor(new Color(1f, 0f, 0f));
			
			FontMetrics metrics = g.getFontMetrics(new Font("sans-serif", Font.PLAIN, 20));
			g.drawString("RENDERING DISABLED", (-metrics.stringWidth("RENDERING DISABLED"))/2 + width/2, ((-metrics.getHeight())/2)+metrics.getAscent()+height/2);
			
			metrics = g.getFontMetrics(new Font("sans-serif", Font.PLAIN, 10));
			g.setFont(new Font("sans-serif", Font.PLAIN, 10));
			g.drawString("(This may help with simulation speed)", (-metrics.stringWidth("(This may help with simulation speed)"))/2 + width/2, ((-metrics.getHeight())/2)+metrics.getAscent()+height/2 + 15);
		}
	}
	
	public EPanel(Simulation sim) {
		addMouseListener(this);
		this.sim = sim;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		mouseInFrame = true;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		mouseInFrame = false;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
