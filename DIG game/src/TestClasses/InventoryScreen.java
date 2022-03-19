//commit maiking sure it worked
//800 by 400
package TestClasses;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import TheHelpers.Point;
import starter.*;
import acm.graphics.*;
import Item.*;

public class InventoryScreen extends GraphicsPane {
	private MainApplication program; 
	
	private ArrayList<Point> centers;
	private ArrayList<GCompound> item;
	private GImage exit;
	private ArrayList<GLine> grid;
	private HashMap<String, Point> pointToCenter;
	private HashMap<String, Point> centerToPoint;
	private GRect rect;
	private int size = 25;
	private double itemSize = program.WINDOW_HEIGHT / 8.66666666;
	private double xCo = program.WINDOW_HEIGHT / 72;
	private double yCo = program.WINDOW_WIDTH / 6.91892;
	private double spaceWidth = program.WINDOW_WIDTH / 115.5;
	private GObject obj;
	private int lastX;
	private int lastY;
	private int startX;
	private int startY;
	private boolean allowedToMove = true;
	private static Inventory inv;
	private int count = 0;
	private boolean displayed = false;
	private static InventoryScreen invScreen;

	public InventoryScreen(MainApplication app) throws IOException {
		this.program = app;
		invScreen = this;
		
		inv = new Inventory();
//		InventoryItem testItem1 = new InventoryItem(BlockType.DIRT, 63);
//		InventoryItem testItem2 = new InventoryItem(BlockType.GRASS, 36);
//		InventoryItem testItem3 = new InventoryItem(BlockType.DIRT, 3);
//		inv.addItem(testItem1);
//		inv.addItem(testItem2);
//		inv.addItem(testItem3);
		
//		if(!new File(Inventory.fileName).exists()) {
//			inv.createSaveLoc();
//			System.out.println("file doesn't exist");
//		}
		
		inv.createSaveLoc();
		inv.loadSaveData();
//		inv.addItem(new InventoryItem(ItemType.COAL,5));
//		System.out.println();
//		inv.print();
//		String testr = inv.getSaveData();
//		System.out.println(testr);
//		String toTest = "null null null null 5,1 dirt 64 5,2 dirt 64";
//		inv.loadSaveData(toTest);
//		System.out.println();
//		inv.print();
		
		
		centers = new ArrayList<Point>();
		pointToCenter = new HashMap<String, Point>();
		centerToPoint = new HashMap<String, Point>();
		grid = new ArrayList<GLine>();
		item = new ArrayList<GCompound>();
		exit = new GImage("helpers/close_button.png");
		exit.setSize(25, 25);
		exit.setLocation(app.WINDOW_WIDTH - size, 5);
		createInvGrid();
		createHotbarGrid();
		initilizeCenters();
		initilizePointToCenter();
		highlightSelectedSlot();
		
		//center to point test code
		Point testMe = new Point(54,61);
		Point toPrint = centerToPoint.get(testMe.toString());
//		System.out.println();
//		System.out.println();
//		System.out.println("here:               " + toPrint.toString());
//		System.out.println();
		
		displayItems();
		
//		System.out.println();
//		getClosestCenterTest();
//		System.out.println();
		
		////test code
//		GLine testLine1 = new GLine(0, 647, app.WINDOW_WIDTH, 647);
//		testLine1.setColor(Color.blue);
//		app.add(testLine1);
//		GLine testLine2 = new GLine(60, 0, 60, app.WINDOW_HEIGHT);
//		testLine2.setColor(Color.green);
//		app.add(testLine2);
	}
	
	public void createInvGrid() {
		for(int i = 0; i < 5; ++i) {
			GLine xLine = new GLine(0, (program.WINDOW_HEIGHT / 5 - xCo) * i, program.WINDOW_WIDTH, (program.WINDOW_HEIGHT / 5 - xCo) * i);
			grid.add(xLine);
		}
		for(int x = 0; x < 11; ++x) {
			GLine yLine = new GLine((program.WINDOW_WIDTH / spaceWidth) * x, 0, (program.WINDOW_WIDTH / spaceWidth) * x, program.WINDOW_HEIGHT - yCo);
			grid.add(yLine);
		}
	}
	
	public void createHotbarGrid() {
		for(int i = 4; i < 6; ++i) {
			GLine xLine = new GLine(0, (program.WINDOW_HEIGHT / 5) * i, program.WINDOW_WIDTH, (program.WINDOW_HEIGHT / 5) * i);
			xLine.setColor(Color.red);
			grid.add(xLine);
		}
		for(int x = 0; x < 11; ++x) {
			GLine yLine = new GLine((program.WINDOW_WIDTH / spaceWidth) * x, (program.WINDOW_HEIGHT / 5) * 4, (program.WINDOW_WIDTH / spaceWidth) * x, program.WINDOW_HEIGHT);
			yLine.setColor(Color.red);
			grid.add(yLine);
		}
	}
	
	public void highlightSelectedSlot() {
		rect = new GRect(0, program.WINDOW_HEIGHT - 130, program.WINDOW_WIDTH / spaceWidth , 130);
		rect.setColor(Color.cyan);
	}
	
	public Point getCenterFromHash(String Loc) {//takes a string that represents location of the item in your inventory
		Point tempPoint;						//then returns the center Point that corresponds to it
		Point returnPoint;
		if(inv == null) {
			tempPoint = new Point(-1,-1);
			System.out.println("inventory is empty");
			return tempPoint;
		}
		
		int num1 = Loc.charAt(0);
		int num2;
		if(Loc.length() == 4) {
			num2 = Loc.charAt(2) * 10 + Loc.charAt(3);
		}
		else {
			num2 = Loc.charAt(2);
		}
		
		tempPoint = new Point(num1,num2);
		returnPoint = pointToCenter.get(tempPoint.toString());
		return returnPoint;
	}
	
	public boolean addItem(InventoryItem tempItem) {
		String name = tempItem.getName();
		int amount = tempItem.getQuantity();
		GImage temp = new GImage("items/" + name + ".png");
		GCompound toAdd = new GCompound();
		temp.setSize(itemSize, itemSize);
		Point loc = getNextOpenSpace();
		if(loc.getX() == -1 && loc.getY() == -1) {
			System.out.println("ERROR: not enough inv space");
			return false;
		}
		temp.setLocation(loc.getX() - (itemSize / 2), loc.getY() - (itemSize / 2));
		GLabel num = new GLabel("X" + amount);
		num.setFont("Arail-bold-" + (int) (itemSize / 6));
		num.setLocation(loc.getX() + (itemSize / 2), loc.getY() + (itemSize / 2));
		toAdd.add(num);
		toAdd.add(temp);
		
		item.add(toAdd);
		showContents();
		return true;
	}
	
	public void update() {
		try {
			inv.loadSaveData();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		displayItems();
	}
	
	public void displayItems() {//given a point in the inventory hashmap (the key), display the item it corresponds to.
		item.clear();
		for(int x = 1; x < Inventory.NUM_ROWS + 1; ++x) {
			for(int y = 1; y < Inventory.NUM_COLS + 1; ++y) {
				Point index = new Point(x,y);
				if(inv.getItemAtIndex(index.toString())==(null)){
					//System.out.println("empty spot in inv ***");
				}
				else {
					InventoryItem tempItem = inv.getItemAtIndex(index.toString());
					String name = tempItem.getName();
					int amount = tempItem.getQuantity();
					Point toDisplay = pointToCenter.get(index.toString());
					
					GImage tempImg = new GImage("items/" + name + ".png");
					GCompound toAdd = new GCompound();
					tempImg.setSize(itemSize, itemSize);
					GLabel num = new GLabel("X" + amount);
					//tempImg.setLocation(toDisplay.getX() - (itemSize / 2), toDisplay.getY() - (itemSize / 2));
					num.setFont("Arail-bold-" + (int) (itemSize / 6));
					//num.setLocation(toDisplay.getX() + (itemSize / 2), toDisplay.getY() + (itemSize / 2));
					num.setLocation(itemSize,itemSize);
					
					toAdd.add(num);
					toAdd.add(tempImg);
					toAdd.setLocation(toDisplay.getX() - (itemSize / 2), toDisplay.getY() - (itemSize / 2));
					
					item.add(toAdd);
				}
			}
		}
	}
	
	public void initilizeCenters() {
		for(int y = (int) (program.WINDOW_HEIGHT / 10.588); y < program.WINDOW_HEIGHT / 1.44; y = (int) (y + (program.WINDOW_HEIGHT / 5.454545))) {//TODO fix this 68, 500, 132
			for(int x = (int) (program.WINDOW_WIDTH / 21.334); x < program.WINDOW_WIDTH; x = (int) (x + (program.WINDOW_WIDTH / 9.9225))) {//   60, 1222, 129
				Point temp = new Point(x,y);
				centers.add(temp);
			}
		}
		for(int i = (int) (program.WINDOW_WIDTH / 21.334); i < program.WINDOW_WIDTH; i = (int) (i + (program.WINDOW_WIDTH / 9.9225))) {//   60, 1222, 129
			Point temp = new Point(i, (int) (program.WINDOW_HEIGHT / 1.11283));//         647
			centers.add(temp);
		}
	}
	
	public void initilizePointToCenter() {
		//System.out.println("ptc:" + "\t\t" + "ctp:");
		for(int x = 1; x < Inventory.NUM_ROWS + 1; ++x) {
			for(int y = 1; y < Inventory.NUM_COLS + 1; ++y) {
				String ptcStr = new Point(x,y).toString();
				String ctpStr = centers.get(count).toString();
				Point pt = new Point(x,y);
				pointToCenter.put(ptcStr, centers.get(count));
				centerToPoint.put(ctpStr, pt);
				//System.out.println(ptcStr + " " + centers.get(count).toString() + "\t" + ctpStr + " " + pt.toString());
				count = count + 1;
			}
		}
		System.out.println();
		count = 0;
	}
	
	public Point getNextOpenSpace() {
		
		for(int i = 0; i < 50; i++) {
			GObject obj = program.getElementAt(centers.get(i).getX(), centers.get(i).getY());
			if(obj == null) {
				System.out.println(i + "           "+ centers.get(i).getX() + " " + centers.get(i).getY());
				return centers.get(i);
			}
		}
		Point temp = new Point(-1, -1);
		return temp;
	}
	
	@Override
	public void showContents() {
		for(int x = 0; x < grid.size(); x++) {
			program.add(grid.get(x));
		}
		for(int y = 0; y < item.size(); y++) {
			program.add(item.get(y));
		}
		program.add(exit);
		//program.add(rect);
		displayed = true;
	}

	@Override
	public void hideContents() {
		
		for(int x = 0; x < grid.size(); x++) {
			program.remove(grid.get(x));
		}
		for(int y = 0; y < item.size(); y++) {
			program.remove(item.get(y));
		}
		program.remove(exit);
		program.remove(rect);
		displayed = false;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//hotbar selector test
		//rect.move(program.WINDOW_WIDTH / spaceWidth, 0);
		
		//test Code:
		System.out.println("here:        " + e.getX() + " " + e.getY());
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj != null) {
			System.out.println(obj.getClass() + " " + obj.getLocation());
		}
		
		obj = program.getElementAt(e.getX(), e.getY());
		
		if(obj == exit)
		{
			allowedToMove = false;
		}
		for(int x = 0; x < grid.size(); x++) {
			if(obj == grid.get(x)) {
				allowedToMove = false;
			}
		}
		lastX = e.getX();
		lastY = e.getY();
		if(obj != null) {
			startX = (int) obj.getX();
			startY = (int) obj.getY();
		}
		
		if(obj == exit) {
		try {
			inv.createSaveLoc();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		program.switchTo("WorldScreen");
		hideContents();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		if(obj != null && allowedToMove){//and is an image/ not x
			obj.move(-1 * (lastX - e.getX()), -1 * (lastY - e.getY()));
		}
		lastX = e.getX();
		lastY = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		lastX = e.getX();
		lastY = e.getY();
		
		if (obj == null || allowedToMove == false) { 
			allowedToMove = true;
			return; 
		}
//		for(int y = 0; y < centers.size(); y++) {
//			dist = Math.sqrt((centers.get(y).getX()-lastX)*(centers.get(y).getX()-lastX) + (centers.get(y).getY()-lastY)*(centers.get(y).getY()-lastY));
//			if(dist < shortestEnd) {
//				System.out.println("num times through: " + y);
//				newLoc = program.getElementAt(centers.get(y).getX(), centers.get(y).getY());
//				obj.setLocation(centers.get(y).getX() - (itemSize / 1.3), centers.get(y).getY() - (itemSize / 1.25));
//				shortestEnd = dist;
//			}
//			
////			if(newLoc != null) {
////				dist = Math.sqrt((centers.get(y).getX()-startX)*(centers.get(y).getX()-startX) + (centers.get(y).getY()-startY)*(centers.get(y).getY()-startY));
////				if(dist < shortestStart) {
////					shortestStart = dist;
////					obj.setLocation(centers.get(y).getX() - (itemSize / 1.4), centers.get(y).getY() - (itemSize / 1.25));
////				}
////			}
//		}
		
//		if(newLoc != null) {
//			obj.setLocation(startX, startY);
//			System.out.println("reset, Location: " + newLoc.getLocation() + " class: " + obj.getClass());
//		}
//		else {
//			obj.setLocation(endPoint.getX() - (itemSize / 1.3), endPoint.getY() - (itemSize / 1.25));
//		}

		Point startPoint = getClosestCenter(startX, startY);
		Point endPoint = getClosestCenter(lastX, lastY);
		
		int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
		Point temp1 = centerToPoint.get(startPoint.toString());
		System.out.println("start:" + startPoint.toString());
		System.out.println("end:" + endPoint.toString());
		x1 = temp1.getX();
		y1 = temp1.getY();
		System.out.println("x1: " + x1 + " y1: " + y1);
		Point temp2 = centerToPoint.get(endPoint.toString());
		x2 = temp2.getX();
		y2 = temp2.getY();
		System.out.println("x2: " + x2 + " y2: " + y2);
		
		
		inv.swapSlots(x1, y1, x2, y2);
		hideContents();
		displayItems();
		showContents();
		obj = null;
		allowedToMove = true;
		System.out.println();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {//TODO: talk to Ian about how to implement
		
		if(e.getKeyCode() == 69) {
			try {
				inv.createSaveLoc();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			program.switchTo("WorldScreen");
			hideContents();
		}
	}
	
	public Point getClosestCenter(int x, int y) {
		Point returnPoint;
		int iVal = 0;
		double dist = 0;
		double shortestLength = 999999;
		for(int i = 0; i < centers.size(); ++i) {
			dist = Math.abs(Math.sqrt((centers.get(i).getX()-x)*(centers.get(i).getX()-x) + (centers.get(i).getY()-y)*(centers.get(i).getY()-y)));
			if(i == 0) {
				shortestLength = dist;
				iVal = i;
			}
			else if(shortestLength > dist){
				shortestLength = dist;
				iVal = i;
			}
		}
		returnPoint = new Point(centers.get(iVal).getX(),centers.get(iVal).getY());
		return returnPoint;
	}
	
	public void getClosestCenterTest() {
		System.out.println("getClosestCenterTest: ");
		
		Point test1 = new Point(1000,100);
		Point ans1 = getClosestCenter(test1.getX(), test1.getY());
		System.out.println(ans1);
		
		Point test2 = new Point(50,50);
		Point ans2 = getClosestCenter(test2.getX(), test2.getY());
		System.out.println(ans2);
		
		Point test3 = new Point(500,500);
		Point ans3 = getClosestCenter(test3.getX(), test3.getY());
		System.out.println(ans3);
		
		Point test4 = new Point(700,150);
		Point ans4 = getClosestCenter(test4.getX(), test4.getY());
		System.out.println(ans4);
	}
	
	public static Inventory getInventory() {
		return inv;
	}

	public static InventoryScreen getInventoryScreen() {
		return invScreen;
	}
}
