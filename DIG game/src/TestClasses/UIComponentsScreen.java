package TestClasses;

import java.awt.event.MouseEvent;

import Worlds.Vector2;
import Worlds.World;
import starter.*;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class UIComponentsScreen extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
									// all of the GraphicsProgram calls
	
	private Boolean visible = false;

	public UIComponentsScreen(MainApplication app) {
		this.program = app;
	}

	@Override
	public void showContents() {
		//program.add(testBlock);
		//program.add(testWorld);
		visible = true;
	}

	@Override
	public void hideContents() {
		visible = false;
	//	program.remove(testWorld);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		program.switchToMenu();
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (!visible) { return;};
		Vector2 camSpeed = new Vector2(0,0);
		int maxSpeed = 128;
		double w = program.getWidth();
		double h = program.getHeight();
		int x = e.getX();
		int y = e.getY();
		if (e.getX() < program.getWidth()/2) {
			//We need to see how much to factor in
			double dist = (w/2) - x;
			camSpeed.setX((int) (dist/(w/2) * -maxSpeed));
		} else {
			double dist = (w/2) - (w - (x));
			camSpeed.setX((int) (dist/(w/2) * maxSpeed));
		}
		
		if (e.getY() < program.getHeight()/2) {
			//We need to see how much to factor in
			double dist = (h/2) - y;
			camSpeed.setY((int) (dist/(h/2) * -maxSpeed));
		} else {
			double dist = (h/2) - (h - (y));
			camSpeed.setY((int) (dist/(h/2) * maxSpeed));
		}
		
		
		
		
	//	System.out.println(e.getX() + ", " + e.getY());
	}
}
