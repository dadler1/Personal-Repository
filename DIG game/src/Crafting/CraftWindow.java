package Crafting;
import TestClasses.InventoryScreen;
import TestClasses.WorldScreen;
import UiComponents.HotbarUI;
import starter.Interfaceable;
import Item.Inventory;
import starter.MainApplication;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import starter.GButton;
import starter.GParagraph;

public class CraftWindow extends GCompound implements Interfaceable {
	//BACK_END_COMPONENTS
	private Recipe currentRecipe;
	private MainApplication program;
	
	//GRAPHICS COMPONENTS
	private GRect pane;
	private GRect itemBorder;
	private GImage itemImage;
	
	private GRect requirementsBorder;
	private GButton craftButton;
	
	private GImage firstRequirement;
	private GImage secondRequirement;
	
	private GRect firstRequirementBorder;
	private GRect secondRequirementBorder;
	
	private GParagraph firstRequirementQuantity;
	private GParagraph secondRequirementQuantity;
	
	
	

	
	public static final double PANE_START_X = 677.5/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double PANE_START_Y = 75/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double PANE_WIDTH = 387.5/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double PANE_LENGTH = 520/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color PANE_COLOR = Color.GRAY;
	
	
	//1280 

	
	//822/1280 => windowWidth  * results
	public static final double ITEM_IMAGE_X = 822/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double ITEM_IMAGE_Y = 150/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double ITEM_IMAGE_SIDE = 100/1155.0*MainApplication.WINDOW_WIDTH;
	
	public static final double ITEM_BORDER_X = 805/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double ITEM_BORDER_Y = 135/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double ITEM_BORDER_SIDE = 130/1155.0*MainApplication.WINDOW_WIDTH;
	public static final Color ITEM_BORDER_COLOR = Color.LIGHT_GRAY;
	
	public static final double REQUIREMENTS_BORDER_X = 705/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double REQUIREMENTS_BORDER_Y = 325/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double REQUIREMENTS_BORDER_SIDE_X = 330/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double REQUIREMENTS_BORDER_SIDE_Y = 130/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color REQUIREMENTS_BORDER_COLOR = Color.LIGHT_GRAY;
	
	public static final String CRAFT_BUTTON_TEXT = "CRAFT";
	public static final String CRAFT_BUTTON_FONT = "Arial-bold-32";
	public static final double CRAFT_BUTTON_START_X = 790/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double CRAFT_BUTTON_START_Y = 500/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double CRAFT_BUTTON_SIDE_X = 150/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double CRAFT_BUTTON_SIDE_Y = 60/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color CRAFT_BUTTON_COLOR = Color.LIGHT_GRAY;
	
	public static final double FIRST_REQUIREMENT_IMAGE_X = 780/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double FIRST_REQUIREMENT_IMAGE_Y = 370/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double FIRST_REQUIREMENT_IMAGE_SIDE = 30/1155.0*MainApplication.WINDOW_WIDTH;
	
	public static final double FIRST_REQUIREMENT_BORDER_X = 735/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double FIRST_REQUIREMENT_BORDER_Y = 365/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double FIRST_REQUIREMENT_BORDER_SIDE = 38/1155.0*MainApplication.WINDOW_WIDTH;
	public static final Color FIRST_REQUIREMENT_BORDER_COLOR = Color.GRAY;
	
	public static final double FIRST_REQUIREMENT_LABEL_X = 785/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double FIRST_REQUIREMENT_LABEL_Y = 395/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double FIRST_REQUIREMENT_LABEL_SIDE_X = 60/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double FIRST_REQUIREMENT_LABEL_SIDE_Y = 60/1155.0*MainApplication.WINDOW_HEIGHT;
	
	public static final double SECOND_REQUIREMENT_IMAGE_X = 885/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double SECOND_REQUIREMENT_IMAGE_Y = 370/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double SECOND_REQUIREMENT_IMAGE_SIDE = 30/1155.0*MainApplication.WINDOW_WIDTH;
	
	public static final double SECOND_REQUIREMENT_BORDER_X = 880/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double SECOND_REQUIREMENT_BORDER_Y = 365/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double SECOND_REQUIREMENT_BORDER_SIDE = 38/1155.0*MainApplication.WINDOW_WIDTH;
	public static final Color SECOND_REQUIREMENT_BORDER_COLOR = Color.GRAY;
	
	public static final double SECOND_REQUIREMENT_LABEL_X = 930/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double SECOND_REQUIREMENT_LABEL_Y = 395/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double SECOND_REQUIREMENT_LABEL_SIDE_X = 60/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double SECOND_REQUIREMENT_LABEL_SIDE_Y = 60/1155.0*MainApplication.WINDOW_HEIGHT;
	
	
	
	//public static final double
	
	
	
	//public static final int IMAGE_START_X;
	
	
	public CraftWindow(MainApplication program){
		this.program = program;
		pane = new GRect(PANE_START_X,PANE_START_Y,PANE_WIDTH,PANE_LENGTH);
		pane.setFilled(true);
		pane.setFillColor(PANE_COLOR);
	
		itemImage = new GImage("items/Air.png", ITEM_IMAGE_X, ITEM_IMAGE_Y);
		itemImage.setSize(ITEM_IMAGE_SIDE,ITEM_IMAGE_SIDE);
		
		itemBorder = new GRect(ITEM_BORDER_X,ITEM_BORDER_Y,ITEM_BORDER_SIDE,ITEM_BORDER_SIDE);
		itemBorder.setFilled(true);
		itemBorder.setFillColor(ITEM_BORDER_COLOR);
		
		requirementsBorder = new GRect(REQUIREMENTS_BORDER_X,REQUIREMENTS_BORDER_Y,REQUIREMENTS_BORDER_SIDE_X,REQUIREMENTS_BORDER_SIDE_Y);
		requirementsBorder.setFilled(true);
		requirementsBorder.setFillColor(REQUIREMENTS_BORDER_COLOR);
		
		craftButton = new GButton(CRAFT_BUTTON_TEXT, CRAFT_BUTTON_START_X,CRAFT_BUTTON_START_Y,CRAFT_BUTTON_SIDE_X,CRAFT_BUTTON_SIDE_Y,CRAFT_BUTTON_COLOR);
		craftButton.setButtonLabelFont(CRAFT_BUTTON_FONT);
		
		firstRequirement = new GImage("items/Air.png",FIRST_REQUIREMENT_IMAGE_X,FIRST_REQUIREMENT_IMAGE_Y);
		firstRequirement.setSize(FIRST_REQUIREMENT_IMAGE_SIDE,FIRST_REQUIREMENT_IMAGE_SIDE);
		
		firstRequirementBorder = new GRect(FIRST_REQUIREMENT_BORDER_X,FIRST_REQUIREMENT_BORDER_Y,FIRST_REQUIREMENT_BORDER_SIDE,FIRST_REQUIREMENT_BORDER_SIDE);
		firstRequirementBorder.setFillColor(FIRST_REQUIREMENT_BORDER_COLOR);
		
		secondRequirement = new GImage("items/Air.png",SECOND_REQUIREMENT_IMAGE_X, SECOND_REQUIREMENT_IMAGE_Y);
		secondRequirement.setSize(SECOND_REQUIREMENT_IMAGE_SIDE,SECOND_REQUIREMENT_IMAGE_SIDE);
		
		secondRequirementBorder = new GRect(SECOND_REQUIREMENT_BORDER_X,SECOND_REQUIREMENT_BORDER_Y,SECOND_REQUIREMENT_BORDER_SIDE,SECOND_REQUIREMENT_BORDER_SIDE);
		secondRequirementBorder.setFillColor(SECOND_REQUIREMENT_BORDER_COLOR);
		
		firstRequirementQuantity = new GParagraph(new String(), FIRST_REQUIREMENT_LABEL_X,FIRST_REQUIREMENT_LABEL_Y);
		firstRequirementQuantity.setFont(CRAFT_BUTTON_FONT);
		
		secondRequirementQuantity = new GParagraph(new String(), SECOND_REQUIREMENT_LABEL_X,SECOND_REQUIREMENT_LABEL_Y);
		secondRequirementQuantity.setFont(CRAFT_BUTTON_FONT);
		
		
		
		add(pane);
		add(itemBorder);
		add(itemImage);
		add(requirementsBorder);
		add(craftButton);
		add(firstRequirementBorder);
		add(firstRequirement);
		add(secondRequirementBorder);
		add(secondRequirement);
		add(firstRequirementQuantity);
		add(secondRequirementQuantity);
		
		

		
	}
	public void craft(Inventory inv) {
		System.out.println("Crafting");
		currentRecipe.craft(inv);
		HotbarUI hotbar = WorldScreen.getHotbar();
		try {
			inv.createSaveLoc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hotbar.update();
	}
	
	
	
	public void setRecipe(Recipe recipe) {
		this.currentRecipe = recipe;
		this.itemImage.setImage(recipe.getFilePathOfOutput());
		this.itemImage.setSize(100,100);
		this.firstRequirement.setImage(recipe.getFilePathOfInput(0));
		this.secondRequirement.setImage(recipe.getFilePathOfInput(1));
		this.firstRequirementQuantity.setText(recipe.getQuantityLabelForInput(0));
		this.secondRequirementQuantity.setText(recipe.getQuantityLabelForInput(1));
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("CRAFTING WINDOW RECEIVED");
		GObject obj = this.getElementAt(e.getX(), e.getY());
	
		if (obj == craftButton) {
			currentRecipe.craft(InventoryScreen.getInventory());
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {	
		Inventory inv = Inventory.getInventory();
		HotbarUI hotbar = WorldScreen.getHotbar();
		try {
			inv.createSaveLoc();
		} catch (IOException f) {
			// TODO Auto-generated catch block
			f.printStackTrace();
		}
		hotbar.update();
	}
	@Override
	public void mouseClicked(MouseEvent e) {	
	}
	@Override
	public void mouseDragged(MouseEvent e) {	
	}
	@Override
	public void mouseMoved(MouseEvent e) {	
	}
	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	

	@Override
	public void showContents() {
	}
	@Override
	public void hideContents() {
	}
	
}
