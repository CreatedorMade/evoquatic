package evoquatic;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class EFrame extends JFrame implements ActionListener {
	
	boolean doRendering = true;
	
	SimThread thread;
	EPanel panel;
	Simulation sim;
	
	JPanel hPanel = new JPanel();
	JLabel hLabel = new JLabel("Initializing...");
	JLabel sLabel = new JLabel("ur mom gay");
	
	
	JPanel fPanel = new JPanel();
	JButton pauseButton = new JButton("Pause");
	JButton realButton = new JButton("Run");
	JButton incubateButton = new JButton("Incubate");
	JButton hideButton = new JButton("Disable Rendering");
	JButton showButton = new JButton("Enable Rendering");
	
	public EFrame(EPanel panel) {
		this.panel = panel;
		sim = panel.sim;
		thread = new SimThread(this);
		
		setLayout(new BorderLayout());
		
		hPanel.setLayout(new BorderLayout());
		hPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(), new EmptyBorder(5, 5, 5, 5)));
		hPanel.add(hLabel, BorderLayout.WEST);
		hPanel.add(sLabel, BorderLayout.EAST);
		add(hPanel, BorderLayout.PAGE_START);
		
		fPanel.setBorder(new EtchedBorder());
		
		fPanel.add(pauseButton);
		pauseButton.addActionListener(this);
		fPanel.add(realButton);
		realButton.addActionListener(this);
		fPanel.add(incubateButton);
		incubateButton.addActionListener(this);
		fPanel.add(hideButton);
		hideButton.addActionListener(this);
		fPanel.add(showButton);
		showButton.addActionListener(this);
		
		add(fPanel, BorderLayout.PAGE_END);
		
		add(panel, BorderLayout.CENTER);
		panel.validate();
		
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } 
		catch (Exception e) { System.err.println("Unable to use System L&F. Reverting to Metal..."); }
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		update();
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pauseButton) {
			sim.state = 0;
			thread.stop();
		} else if(e.getSource() == realButton) {
			sim.state = 1;
			thread.realtime();
		} else if(e.getSource() == incubateButton) {
			sim.state = 2;
			thread.incubate();
		} else if(e.getSource() == hideButton) doRendering = false;
		else if(e.getSource() == showButton) doRendering = true;
		
		panel.doRendering = doRendering;
		
		update();
	}
	
	public void update() {
		panel.repaint();
		
		String str = "";
		
		if(sim.state == 0) str += "Simulation paused";
		if(sim.state == 1) str += "Simulation running (realtime)";
		if(sim.state == 2) str += "Simulation running (incubation)";
		
		if(!doRendering) str += ", rendering disabled";
		
		hLabel.setText(str);
	}
}
