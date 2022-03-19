
package Crafting;

import Item.InventoryItem;
import TestClasses.InventoryScreen;
import Item.Inventory;
import java.util.ArrayList;
import Item.BlockType;
public class Recipe {
	private String name;
	private InventoryItem[] inputs;
	private InventoryItem output;
	
	public Recipe() {
		this.inputs = new InventoryItem[] {new InventoryItem(BlockType.GRASS,1), new InventoryItem(BlockType.GRASS,1)};
		this.output = new InventoryItem(BlockType.GRASS,1);
		this.name = "Grass";
	}
	public Recipe(InventoryItem[] inputs, InventoryItem output) {
		this.inputs = inputs;
		this.output = output;
		this.name = output.getName();
	}
	
	public String getName() {
		return name;
	}
	public String getFilePathOfOutput() {
		return output.getFilePath();
	}
	public String getFilePathOfInput(int inputIndex) {
		if(inputIndex<2 && inputIndex > -1) {
			if(!inputIsNull(inputIndex)) {
				return inputs[inputIndex].getFilePath();
			}
			return "items/Air.png";
			
		}
		return "items/BlockedAir.png";
	}
	
	public String getQuantityLabelForInput(int inputIndex) {
		if(inputIndex<2 && inputIndex > -1) {
			if(!inputIsNull(inputIndex)) {
				return "x" + inputs[inputIndex].getQuantity();
			}
		}
		return new String();
	}
	
	public boolean inputIsNull(int inputIndex) {
		if(inputIndex<2) {
			if(inputs[inputIndex] == null) {
				return true;
			}
		}
		return false;
	}
	
	
	public InventoryItem craft(Inventory inv) {
		ArrayList<String> foundIndexes = inv.getIndexesOfFoundIngredients(inputs);
		if(foundIndexes.isEmpty()) {
			return null;
		}
		//REMOVES REQUIREMENTS FROM INVENTORY
		for(InventoryItem currentItem: inputs) {
			if (currentItem != null) {	
				inv.removeItem(currentItem);
			}
		
		}
		//ADDS OUTPUT TO INVENTORY
		inv.addItem(output);
		InventoryScreen.getInventoryScreen().displayItems();;
		return null;
	}
	
	public InventoryItem[] getInputs() {
		return inputs;
	}
	
	public InventoryItem getOutput() {
		return output;
	}
	
	
	public String toString() {
		String requirements = new String();
		for(int i = 0; i < inputs.length; i++) {
			requirements += inputs[i];
		}
		return name + " requires " + requirements;
	}
	
	
}
