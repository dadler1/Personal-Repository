package TestClasses;


import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import starter.*;


public class TestMenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
	// all of the GraphicsProgram calls
	private GButton crafting;
	private GButton inventory;
	private GButton menu;
	private GButton world;

	private final int b_width = (int) (1280*.8);
	private final int b_height = (int) (720*.225);

	public TestMenuPane(MainApplication app) {
		super();
		program = app;


		crafting = new GButton("CraftingScreen",0,0,b_width,b_height);
		inventory = new GButton("InventoryScreen",0,b_height,b_width,b_height);
		menu = new GButton("MenuScreen",0,b_height*2,b_width,b_height);
		world = new GButton("WorldScreen",0,b_height*3,b_width,b_height);
	}

	@Override
	public void showContents() {
		//program.add(rect);
		program.add(world);
		program.add(menu);
		program.add(inventory);
		program.add(crafting);
	}

	@Override
	public void hideContents() {
		//program.remove(rect);
		program.remove(world);
		program.remove(menu);
		program.remove(inventory);
		program.remove(crafting);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == world) {
			//program.switchToSome();
		}

		if (obj == world) {
			program.switchTo("WorldScreen");
		}

		if (obj == menu) {
			program.switchTo("MenuScreen");
		}

		if (obj == inventory) {
			program.switchTo("InventoryScreen");
		}

		if (obj == crafting) {
			program.switchTo("CraftingScreen");
		}
	}
}
