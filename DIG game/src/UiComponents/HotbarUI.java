package UiComponents;
//making sure it pushed
import java.awt.Color;
import TheHelpers.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Item.*;
import Worlds.Vector2;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import starter.MainApplication;

public class HotbarUI extends GCompound{
 
	static final int HOTBAR_WIDTH = 800;
	static final int HOTBAR_HEIGHT = 80;
	
	final double boxWidth = (HOTBAR_WIDTH/10);
	final double boxHeight = HOTBAR_HEIGHT;
	
	private int selectedSlot = 1;
	private GRect background;
	private GCompound selector;
	private Inventory inv;
	private int itemSize = (int) (HOTBAR_HEIGHT / 1.5);
	private ArrayList<GCompound> item;
	public static HotbarUI Instance;
	private static InventoryItem selected;
	
	public void selectSlot(int slot) {
		int i = slot;
		if (i<1) {
			i=1;
		}
		
		if (i>10) {
			i = 10;
		}
		
		Point index = new Point(5, slot);
		selected = inv.getItemAtIndex(index.toString());
		
		System.out.println(slot);
		selector.setLocation((slot-1)*boxWidth,0);
	}
	
	public InventoryItem getSelected() {
		return selected;
	}
	
	public void displayHotbarItems() {
		item.clear();
		for(int i = 1; i < Inventory.NUM_COLS + 1; ++i) {
			Point index = new Point(5, i);
			if(inv.getItemAtIndex(index.toString()) == (null)) {
			}
			else {
				InventoryItem tempItem = inv.getItemAtIndex(index.toString());
				String name = tempItem.getName();
				int amount = tempItem.getQuantity();
				Point toDisplay = new Point((int) (boxWidth / 2 + ((i - 1) * boxWidth)), (int) (boxWidth / 2));
				
				GImage tempImg = new GImage("items/" + name + ".png");
				GCompound toAdd = new GCompound();
				tempImg.setSize(itemSize , itemSize);
				GLabel num = new GLabel("X" + amount);
				num.setFont("Arail-bold-" + (int) (itemSize / 2));
				num.setColor(Color.yellow);
				num.setLocation(0,itemSize);
				toAdd.add(tempImg);
				toAdd.add(num);
				
				toAdd.setLocation(toDisplay.getX() - (itemSize / 2), toDisplay.getY() - (itemSize / 2));
				
				if(amount < 1) {
					System.out.println("you ran out");
				}
				if(amount > 0) {
					item.add(toAdd);
				}
			}
		}
	} 
	
	public void update() {
		for(int y = 0; y < item.size(); y++) {
			remove(item.get(y));
		}
		try {
			inv.loadSaveData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		displayHotbarItems();
		for(int y = 0; y < item.size(); y++) {
			add(item.get(y));
		}
	}
	
	public HotbarUI(MainApplication app) {
		this.Instance = this;
		background = new GRect(0,0,HOTBAR_WIDTH,HOTBAR_HEIGHT);
		background.setColor(new Color(255,255,255,0));
		background.setFillColor(new Color(255,255,255,166));
		background.setFilled(true);
		add(background);
		
		item = new ArrayList<GCompound>();
		inv = Inventory.getInventory();//TODO: make it load inventory
		
		update();
		
		
		//Lets create the individual item boxes
		
		Color selectorColor = new Color(255,255,255);
		
		for (int i=0;i<10;i++) {
			GRect box = new GRect(i*boxWidth,0,boxWidth,boxHeight);
			add(box);
		}
		
		//Add the selector
		selector = new GCompound();
		double outlineThickness = 5;
		double width = boxWidth + outlineThickness;
		double height = boxHeight + outlineThickness;
		
		GRect topBorder = new GRect(-outlineThickness,-outlineThickness,width + outlineThickness,outlineThickness);
		topBorder.setColor(selectorColor);
		topBorder.setFilled(true);
		selector.add(topBorder);
		
		GRect botBorder = new GRect(-outlineThickness,boxHeight,width + outlineThickness,outlineThickness);
		botBorder.setColor(selectorColor);
		botBorder.setFilled(true);
		selector.add(botBorder);
		
		GRect leftBorder = new GRect(-outlineThickness,-outlineThickness,outlineThickness,width + outlineThickness);
		leftBorder.setColor(selectorColor);
		leftBorder.setFilled(true);
		selector.add(leftBorder);
		
		GRect rightBorder = new GRect(boxWidth,-outlineThickness,outlineThickness,width + outlineThickness);
		rightBorder.setColor(selectorColor);
		rightBorder.setFilled(true);
		selector.add(rightBorder);
		
		
		
		add(selector);
		
	}
}
