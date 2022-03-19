package UiComponents;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import TestClasses.CraftingScreen;
import acm.graphics.GCompound;
import acm.graphics.GObject;
import acm.graphics.GRect;
import starter.Interfaceable;
import starter.MainApplication;

public class CraftingUI extends GCompound implements Interfaceable {
	private CraftingScreen ui;
	public CraftingUI(MainApplication app) {
		GRect frame = new GRect(0,0,app.getWidth(),app.getHeight());
		frame.setFilled(true);
		frame.setFillColor(new Color(0,0,0,155));
		add(frame);
		
		ui = new CraftingScreen(app);
		add(ui);
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		ui.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		ui.mouseReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		ui.mouseClicked(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		ui.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		ui.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		ui.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		ui.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		ui.keyTyped(e);
	}
}
