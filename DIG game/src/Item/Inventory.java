package Item;

import Worlds.Vector2;
import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/*
 * File Name: 	Inventory.java
 * Author:		Daniel Adler
 * Date: 		11/1/2019
 * Desc: 		Inventory contains a Hashmap of InventoryItems accessed by a String key which is a 
 * 				set of coordinates within the Inventory describing which slot will be accessed. These 
 * 				slots range from indexes [1][1] to [5][10] where [5][i] represents the hotbar where i 
 * 				is an integer between 1 and 10.
 * 
 */

public class Inventory {
	private HashMap<String,InventoryItem> items;
	
	
	public static final int NUM_ROWS = 5;
	public static final int NUM_COLS = 10;
	public static final String fileName = "invSaveData.txt";
	private File file = new File(fileName);


	private int count = 0;
	
	private static Inventory playerInventory;
	public static int totalInventorys = 0;
	
	public static Inventory getInventory() {
		return playerInventory;
	}
	
	
	
	public Inventory() {
		totalInventorys ++;
		System.out.println("An Inventory was created " + totalInventorys);
		items = new HashMap<String,InventoryItem>();
		playerInventory = this;
		for(int x = 1; x <= NUM_ROWS; x++ ) {
			for(int y = 1; y <= NUM_COLS; y++) {
				items.put(new Vector2(x,y).toString(), null);
				//System.out.print("empty, ");
			}
			//System.out.println();
		}
		//items.put(new Vector2(1,1).toString(), new InventoryItem(ItemType.STICK,1));
	}
	
	public void print() {
		for(int x = 1; x <= NUM_ROWS; x++ ) {
			for(int y = 1; y <= NUM_COLS; y++) {
				if(items.get(new Vector2(x,y).toString())==null) {
					System.out.print("empty, ");
				} else {
					System.out.print(items.get(new Vector2(x,y).toString())+ ", ");
				}
				
				
			}
			System.out.println();
		}
	}
	private String getNextAvailableSlot() {
		for(int x = NUM_ROWS; x >0; x-- ) {
			for(int y = 1; y <=NUM_COLS; y++) {
				if(items.get(new Vector2(x,y).toString())==null) {
					return new Vector2(x,y).toString();
				}
			}
		}
		return null;
	}
	public boolean addItem(InventoryItem newItem, int row, int col) {
		if(newItem!=null && items.get(new Vector2(row,col).toString())==null) {
			items.put(new Vector2(row,col).toString(), newItem);
			return true;
		}
		return false;
		
	}
	
	public boolean addItem(InventoryItem newItem) {
		int amountToAdd = newItem.getQuantity();
		ArrayList<String> foundIndexes = getIndexesOfFoundIngredients(new InventoryItem[] {newItem});
		if(foundIndexes.isEmpty()) {
			if(getNextAvailableSlot()==null) {
				return false;
			}
			items.put(getNextAvailableSlot(), newItem);
			return true;
		}
		for(String currentIndex: foundIndexes) {
			if(items.get(currentIndex).getQuantity()<64) {
				amountToAdd = items.get(currentIndex).addToStack(amountToAdd);
				if(amountToAdd == 0)
					return true;
			}
		}
		
		if(getNextAvailableSlot()==null) {
			return false;
		}
		items.put(getNextAvailableSlot(), new InventoryItem(newItem.getType(),amountToAdd));
		return true;	
	}
	
	//TODO: DEBUG
	public boolean removeItem(InventoryItem toRemove) {
		int amountToRemove = toRemove.getQuantity();
		//System.out.println("Amount in inventory " + getAmountOf(toRemove.getType()));
		if(getAmountOf(toRemove.getType())>=amountToRemove) {
			ArrayList<String> foundIndexes = getIndexesOfFoundIngredients(new InventoryItem[] {toRemove});
			for(int i = 0; i < foundIndexes.size(); i++) {
				//System.out.println("STRING " + foundIndexes.get(i));
				/*if(items.get(currentLocation).getQuantity()>=toRemove.getQuantity()) {
					removeAmountFromSlot(currentLocation,amountToRemove);
					if(items.get(currentLocation).getQuantity() == 0) {
						items.put(currentLocation,null);
					}
					break;
				}*/
				//The current slot does not have enough items to remove
				amountToRemove = removeAmountFromSlot(foundIndexes.get(i),amountToRemove);
				if(amountToRemove == 0)
					break;
			}
			//System.out.println(amountToRemove + " left to remove");
			return true;
		}
		return false;
	}
	
	private int removeAmountFromSlot(String currentLocation, int amount) {
		int output = items.get(currentLocation).removeFromStack(amount);
		if(items.get(currentLocation).getQuantity() == 0)
			items.put(currentLocation, null);
		return output;  
		
	}
	
	public boolean removeItem(int row, int col) {
		System.out.println("Out of Bounds: " + outOfBounds(row,col));
		if(!outOfBounds(row,col) && items.get(new Vector2(row,col).toString())!=null) {
			items.put(new Vector2(row,col).toString(), null);
			System.out.println("Item removed");
			return true;
		}
		System.out.println("Item not removed.");
		return false;
	}
	
	public boolean hasEnough(InventoryItem input) {
		return false;
		//if(getAmountOf(currentInput.getType())>currentInput.getQuantity()){
		//	itemsToCheck-=1;
			
		//}
	}
	
	private boolean outOfBounds(int row, int col) {
		return row>NUM_ROWS || row<=0 || col>NUM_COLS || col<=0;
	}
	
	public boolean swapSlots(int row1, int col1, int row2, int col2) {
		if(outOfBounds(row1,col1) || outOfBounds(row2,col2)) {
			return false;
		}	
//		if(!outOfBounds(row1,col1) || !outOfBounds(row2,col2)) {
//			System.out.println("Items were not swapped. r1,c1: " + row1 + "," + col1 + " r2c2: " + row2 + "," + col2);
//			return false;
//		}
		InventoryItem tempItem = items.get(new Vector2(row1,col1).toString());
		items.put(new Vector2(row1,col1).toString(), items.get(new Vector2(row2,col2).toString()));
		items.put(new Vector2(row2,col2).toString(), tempItem);
		System.out.println("Items were swapped");
		return true;
	}
	
	public InventoryItem[] getHotBar() {
		InventoryItem[] hotbar = new InventoryItem[NUM_COLS];
		for(int x = 1; x <= NUM_COLS; x++) {
			
			hotbar[x-1] = items.get(new Vector2(5,x).toString());
		}
		return hotbar;
	}
	
	public InventoryItem getItemAtIndex(String index) {
		return items.get(index);
	}
	
	public String getSaveData() {
		String returnString = new String();
		for(String currentKey: items.keySet()) {
			if(items.get(currentKey)!=null)
				returnString+=(currentKey + " " + items.get(currentKey).getSaveData()) + " ";
			else
				returnString+="null ";
				
		}
		return returnString;
	}
	
	public void createSaveLoc() throws IOException {

		File invFile = new File(fileName);
		
		if (invFile.exists() && count > 1) {
			invFile.delete();
		}
		count = 5;
		if(!invFile.exists()) {
			invFile.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.write(getSaveData());
		writer.close();
		
		
//		try {
//			FileWriter fw = new FileWriter(fileName,false);
//			BufferedWriter writer = new BufferedWriter(fw);
//			writer.write(getSaveData());
//			writer.newLine();
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Saved Inventory");
	}
	
	//" 5 1 dirt 64 "
	public void loadSaveData() throws FileNotFoundException{
		if(!file.exists()) {
			return;
		}
		
		Scanner sc = new Scanner(file);
		while(sc.hasNext()) {
			String name, Loc;
			int amount, row, col;
			InventoryItem item;
			String temp = sc.next();
			if(!temp.equals("null")) {
				Loc = temp;
				System.out.println("loc: " + Loc);
				String newLoc = Loc.substring(0,1) + " " + Loc.substring(2);
				System.out.println(newLoc);
				Scanner s = new Scanner(newLoc);
				row = s.nextInt();
				col = s.nextInt();
				s.close();
				name = sc.next();
				amount = sc.nextInt();
				
				
				
				for (BlockType type: BlockType.values()) {
					if (type.getName().equals(name/* .toUpperCase() */)){
						System.out.println("this is why its broken isn't it block");
						item = new InventoryItem(BlockType.valueOf(name.toUpperCase()), amount);
						addItem(item, row, col);
						System.out.println(item.toString() + "  " + row + " " + col);
						break;
					}
				}
				
				for (ItemType type: ItemType.values()) {
					if (type.getName().equals(name/* .toUpperCase() */)){
						System.out.println("this is why its broken isn't it item");
						item = new InventoryItem(ItemType.valueOf(name.toUpperCase()), amount);
						addItem(item, row, col);
						System.out.println(item.toString() +"  " + row + " " + col);
						break;
					}
				}
			}
		}
		sc.close();
	}
	
	
	private boolean hasNecessaryIngredients(InventoryItem[] requirements) {
		int numItemsLeftToFind = requirements.length;
		for(int i = 0; i < requirements.length; i++) {
			for(String currentKey: items.keySet()) {
				if(items.get(currentKey)!=null) {
					if(items.get(currentKey).getName() == requirements[i].getName() && items.get(currentKey).getQuantity()>=requirements[i].getQuantity()) {
						numItemsLeftToFind--;
						break;
					}
				}
			}
		}
		System.out.println(numItemsLeftToFind);
		if(numItemsLeftToFind == 0) {
			return true;
		}
		return false;
	}
	
	public ArrayList<String> getIndexesOfFoundIngredients(InventoryItem[] requirements) {
		ArrayList<String> returnStrings = new ArrayList<String>();
		//if(hasNecessaryIngredients(requirements)) {
			for(int i = 0; i < requirements.length; i++) {
				for(String currentKey: items.keySet()) {
					if(items.get(currentKey)!=null) {
						if (requirements[i] != null) {
							if(items.get(currentKey).getName() == requirements[i].getName()) {
								returnStrings.add(currentKey);
							}
						}
						
						
					}
				}
			}
		//}
		return returnStrings;
	}
	
	
	public int getAmountOf(EntityType item) {
		int sum = 0;
		for(String currentKey: items.keySet()) {
			if(items.get(currentKey)!=null) {
				if(items.get(currentKey).getName()==item.getName()) {
					sum+=items.get(currentKey).getQuantity();
				}
			}
		}
		return sum;
	}
	
	
	//DEBUGGING
	public static void main(String[] args) {
		Inventory inv = new Inventory();
		InventoryItem coal = new InventoryItem(BlockType.COALORE, 62);
		InventoryItem coal2 = new InventoryItem(BlockType.COALORE,5);
		InventoryItem stick = new InventoryItem(ItemType.COAL,5);
		
		//InventoryItem diamond = new InventoryItem(BlockType.DIAMOND_ORE,3);
		//InventoryItem stick = new InventoryItem(ItemType.STICK,128);
		
		
		inv.addItem(coal);
		inv.addItem(coal2);
		inv.addItem(stick);
		//inv.removeItem(new InventoryItem(BlockType.COAL_ORE,67));
		System.out.println(inv.getAmountOf(BlockType.COALORE));
		//inv.swapSlots(4, 1, 4, 2);
		//inv.removeItem(4, 1);
	
		inv.print();
		//inv.hasNecessaryIngredients(new InventoryItem[] {new InventoryItem(BlockType.COAL_ORE,2),diamond});

		ArrayList<String> foundItems = inv.getIndexesOfFoundIngredients(new InventoryItem[] {new InventoryItem(BlockType.COALORE,4)});
		for(int i = 0; i < foundItems.size(); i++) {
			System.out.println(foundItems.get(i));
		}
		System.out.println();
		//System.out.println(new Vector2(10,1).toString());


		
		//for(int i = 0; i < 10; i++)
			//System.out.println(inv.getHotBar()[i]);
		
		
		
		
	}
	
}
