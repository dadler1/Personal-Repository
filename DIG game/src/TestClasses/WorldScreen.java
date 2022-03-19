package TestClasses;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import Item.Inventory;
import UiComponents.CraftingUI;
import UiComponents.HotbarUI;
import UiComponents.InventoryUI;
import Worlds.Vector2;
import Worlds.World;
import Worlds.WorldBlock;
import starter.*;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class WorldScreen extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
									// all of the GraphicsProgram calls
	
	private boolean initialized = false;
	private World testWorld;
	private Boolean visible = false;
	
	private static HotbarUI hotbar;
	private CraftingUI crafting;
	//private InventoryUI inventory;
	private static String currentState = "World";
	
	public static HotbarUI getHotbar() {
		return hotbar;
	}

	public void exit() {
		System.out.println("ok");
	}
	
	public static String getState() {
		return currentState;
	}
	
	//Separate from constructor to reduce start up lag
	private void init() {
		if (!initialized) {
			initialized = true;
			testWorld = new World(program);

		
			crafting = new CraftingUI(program);
			
		}
		
	}
	
	
	private void changeState(String newState) {
		System.out.println(newState);
		
		
		if (newState != currentState) {
			//We need to remove the old state contents
			if(currentState == "Crafting") {
				program.remove(crafting);
				Inventory inv = Inventory.getInventory();
				HotbarUI hotbar = WorldScreen.getHotbar();
				try {
					inv.createSaveLoc();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hotbar.update();
			} else if(currentState == "Inventory") {
				//program.remove(inventory);
				//inventory.hideContents();
				program.switchTo("WorldScreen");
			}
			currentState = newState;
		}
		
		
		
		if (currentState == "Crafting") {
			program.add(crafting);
		} else if(currentState == "Inventory"){
			//program.add(inventory);
			//inventory.showContents();
			currentState = "World";
			changeState(currentState);
			InventoryScreen inv = InventoryScreen.getInventoryScreen();
			inv.update();
			
			program.switchTo("InventoryScreen");
		}
		
	}
	
	public WorldScreen(MainApplication app) {
		this.program = app;

		//testWorld = new World(program);
	}

	@Override
	public void showContents() {		
		//Try to init if not already
		init();
		
		program.add(testWorld);
		
		hotbar = new HotbarUI(program);
		hotbar.setLocation(program.getWidth()/2 - hotbar.getWidth()/2, program.getHeight() - (3*hotbar.getHeight())/2);
		
		program.add(hotbar);
		visible = true;
		testWorld.isVisible = visible;
	}

	@Override
	public void hideContents() {
		visible = false;
		testWorld.isVisible = visible;
		program.remove(testWorld);
		program.remove(hotbar);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//para.setText("you need\nto click\non the eyes\nto go back");
		GObject obj = program.getElementAt(e.getX(), e.getY());
		testWorld.mousePressed(e);
		if (currentState == "Crafting") {
		crafting.mousePressed(e);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		testWorld.mouseReleased(e);
		if (currentState == "Crafting") {crafting.mouseReleased(e);}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		testWorld.mouseDragged(e);
		if (currentState == "Crafting") {crafting.mouseDragged(e);}
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		testWorld.keyPressed(e);
		if (currentState == "Crafting") {crafting.keyPressed(e);};
	
		
		//Q is pressed : CRAFTING
		if (e.getKeyCode() == 81) {
			if (currentState == "Crafting") {
				changeState("World");
			} else {
				changeState("Crafting");
			}
			
			//System.out.println("SHOW CRAFTING");
			//program.switchToMenu();
		}
		
		if (e.getKeyCode() == 69) {
			if (currentState == "Inventory") {
				changeState("World");
			} else {
				changeState("Inventory");
			}
			//System.out.println("SHOW INVENTORY");
		}
		
		if (e.getKeyCode()  >= 49 && e.getKeyCode() <= 57) {
			hotbar.selectSlot(e.getKeyCode() - 48);
		}
		
		if (e.getKeyCode()  == 48) {
			hotbar.selectSlot(10);
		}
			
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		testWorld.keyReleased(e);
		if (currentState == "Crafting") {
			crafting.keyReleased(e);
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		testWorld.keyTyped(e);
		if (currentState == "Crafting") {
			crafting.keyTyped(e);
		}
	}

	
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentState == "Crafting") {
			crafting.mouseMoved(e);
		}
		
		if (testWorld == null) { return; };
		
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
		
		
		
		testWorld.setCameraSpeed(camSpeed);
	//	System.out.println(e.getX() + ", " + e.getY());
	}
}
