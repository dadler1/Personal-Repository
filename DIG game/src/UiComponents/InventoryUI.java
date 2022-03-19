package UiComponents;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import TestClasses.InventoryScreen;
import acm.graphics.GCompound;
import acm.graphics.GRect;
import starter.Interfaceable;
import starter.MainApplication;

public class InventoryUI extends GCompound implements Interfaceable{
	private InventoryScreen inventory;
	public InventoryUI(MainApplication app) throws IOException {
		GRect frame = new GRect(0,0,app.getWidth(),app.getHeight());
		frame.setFilled(true);
		frame.setFillColor(new Color(0,0,0,155));
		add(frame);
		
		inventory = new InventoryScreen(app);
		inventory.showContents();
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
