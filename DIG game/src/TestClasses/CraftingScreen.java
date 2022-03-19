package TestClasses;

import Crafting.CraftCategory;
import Crafting.CraftWindow;
import Crafting.ListItem;
import Crafting.ScrollPane;
import TheHelpers.ResourceLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import starter.*;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GImage;
import Crafting.Recipe;
import Crafting.RecipeComponent;

public class CraftingScreen extends GCompound implements Interfaceable {
	
	public static final String MAT_BUTTON_TEXT = "Materials";
	public static final String MAT_BUTTON_FONT = "Arial-bold-14";
	public static final double MAT_BUTTON_START_X = 685/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double MAT_BUTTON_START_Y = 50/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double MAT_BUTTON_SIDE_X = 80/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double MAT_BUTTON_SIDE_Y = 30/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color MAT_BUTTON_COLOR = Color.LIGHT_GRAY;
	
	public static final String TOOL_BUTTON_TEXT = "Tools";
	public static final String TOOL_BUTTON_FONT = "Arial-bold-14";
	public static final double TOOL_BUTTON_START_X = 785/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double TOOL_BUTTON_START_Y = 50/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double TOOL_BUTTON_SIDE_X = 80/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double TOOL_BUTTON_SIDE_Y = 30/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color TOOL_BUTTON_COLOR = Color.LIGHT_GRAY;
	
	public static final String BLOCK_BUTTON_TEXT = "Blocks";
	public static final String BLOCK_BUTTON_FONT = "Arial-bold-14";
	public static final double BLOCK_BUTTON_START_X = 885/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double BLOCK_BUTTON_START_Y = 50/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double BLOCK_BUTTON_SIDE_X = 80/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double BLOCK_BUTTON_SIDE_Y = 30/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color BLOCK_BUTTON_COLOR = Color.LIGHT_GRAY;
	
	public static final String FOOD_BUTTON_TEXT = "Food";
	public static final String FOOD_BUTTON_FONT = "Arial-bold-14";
	public static final double FOOD_BUTTON_START_X = 985/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double FOOD_BUTTON_START_Y = 50/650.0*MainApplication.WINDOW_HEIGHT;
	public static final double FOOD_BUTTON_SIDE_X = 80/1155.0*MainApplication.WINDOW_WIDTH;
	public static final double FOOD_BUTTON_SIDE_Y = 30/650.0*MainApplication.WINDOW_HEIGHT;
	public static final Color FOOD_BUTTON_COLOR = Color.LIGHT_GRAY;
	
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	
	private GParagraph para;
	private CraftWindow window2;
	private ScrollPane scrollPane;
	private GCompound hideOverflow;
	private final Color overflowColor = Color.LIGHT_GRAY;
	private GCompound contents;
	private int scrollWidth;
	private int scrollHeight;
	private String currentCategory = "Blocks";
	
	private GButton materialsCategory;
	private GButton toolsCategory;
	private GButton blocksCategory;
	private GButton foodCategory;
	private CraftCategory selectedCategory = CraftCategory.BLOCKS;
	private String[] availableRecipes = {"WoodPlank","Stick","WoodPickaxe","StonePickaxe","IronPickaxe","DiamondPickaxe", "IronBar","Diamond", "Grass","Dirt","SnowGrass","SnowDirt"};
	
	private void addToContents(GCompound element) {
		element.setLocation(0,contents.getHeight());
		contents.add(element);
		scrollPane.setContents(contents); 
	}
	
	
	//UNUSED SO FAR
	private GCompound createListItem(Recipe recipe) {
		ListItem newListItem = new ListItem(scrollPane);
		newListItem.setTitleText(recipe.getName() + "ok");
		newListItem.setImageFromTitle();
		return newListItem;
	}
	
 
	private GCompound createListItem(String itemName) {
		
		//return new RecipeComponent(program, new Recipe());
		
		ListItem newListItem = new ListItem(scrollPane);
		newListItem.setTitleText(itemName);
		newListItem.setImageFromTitle();
		System.out.println(itemName);
		if (CraftCategory.recipes.containsKey(itemName)) {
			newListItem.linkRecipe(CraftCategory.recipes.get(itemName));
		}
		
		return newListItem;
	}

	/*
	private GCompound createListItem(String itemName) {
		ListItem newListItem = new ListItem(scrollPane);
		newListItem.setTitleText(itemName);
		return newListItem; 

	}
	*/
	 
	public CraftingScreen(MainApplication app) {
		this.program = app;
		window2 = new CraftWindow(app);
		scrollHeight = (this.program.getHeight()/4)*3;
		scrollWidth = this.program.getWidth()/2;
		int scrollY = program.getHeight()/2 - scrollHeight/2;
		int scrollX = 20;
		
		scrollPane = new ScrollPane(scrollX,scrollY,scrollWidth,scrollHeight);
		
		hideOverflow = new GCompound();
		GRect topHide = new GRect(scrollX,0,scrollPane.getWidth(),scrollY);
		topHide.setFilled(true);
		topHide.setColor(overflowColor);
		
		GRect botHide = new GRect(scrollX,scrollY + scrollHeight,scrollPane.getWidth(),this.program.getHeight() - scrollHeight);
		botHide.setFilled(true);
		botHide.setColor(overflowColor);
		
		//hideOverflow.add(topHide);
		//hideOverflow.add(botHide);
	//	scrollPane.setLocation(0,program.getHeight()/2 -  scrollPane.getHeight()/2);
		
		para = new GParagraph("this is crafting screen", 150, 300);
		para.setFont("Arial-24");
		
		GCompound testContents = new GCompound();
		
		GRect tRect = new GRect(0,0,scrollWidth,scrollHeight);
		tRect.setFillColor(Color.red);
		tRect.setFilled(true);
		
		testContents.add(tRect);
		
		GRect t1Rect = new GRect(0,scrollHeight,scrollWidth,scrollHeight);
		t1Rect.setFillColor(Color.green);
		t1Rect.setFilled(true);
		
		testContents.add(t1Rect);
		contents = testContents;
		
		
		
		contents = new GCompound();
		
		for (int i=0;i<availableRecipes.length;i++) {
			GCompound entry = createListItem(availableRecipes[i]);
			addToContents(entry);
		}
		
		
		
		scrollPane.setContents(contents);
		
		
		//GCompound entry = new GCompound();
		//entry.add(new GRect(0,0,400,400));
		
		//addToContents(entry);
		
		materialsCategory = new GButton(MAT_BUTTON_TEXT, MAT_BUTTON_START_X,MAT_BUTTON_START_Y,MAT_BUTTON_SIDE_X,MAT_BUTTON_SIDE_Y,MAT_BUTTON_COLOR);
		materialsCategory.setButtonLabelFont(MAT_BUTTON_FONT);
		toolsCategory = new GButton(TOOL_BUTTON_TEXT, TOOL_BUTTON_START_X,TOOL_BUTTON_START_Y,TOOL_BUTTON_SIDE_X,TOOL_BUTTON_SIDE_Y,TOOL_BUTTON_COLOR);
		toolsCategory.setButtonLabelFont(TOOL_BUTTON_FONT);
		blocksCategory = new GButton(BLOCK_BUTTON_TEXT, BLOCK_BUTTON_START_X,BLOCK_BUTTON_START_Y,BLOCK_BUTTON_SIDE_X,BLOCK_BUTTON_SIDE_Y,BLOCK_BUTTON_COLOR);
		blocksCategory.setButtonLabelFont(BLOCK_BUTTON_FONT);
		foodCategory = new GButton(FOOD_BUTTON_TEXT, FOOD_BUTTON_START_X,FOOD_BUTTON_START_Y,FOOD_BUTTON_SIDE_X,FOOD_BUTTON_SIDE_Y,FOOD_BUTTON_COLOR);
		foodCategory.setButtonLabelFont(FOOD_BUTTON_FONT);
		
		add(para);
		add(window2);
		add(scrollPane);
		//add(hideOverflow);
		add(materialsCategory);
		add(toolsCategory);
		add(blocksCategory);
		add(foodCategory);
	}
	
	
	@Override
	public void showContents() {}

	@Override
	public void hideContents() {}

	@Override
	public void mousePressed(MouseEvent e) {

		GObject obj = program.getElementAt(e.getX(), e.getY());
		System.out.println("DETECTED IN CRAFTING SCREEN");
		scrollPane.mousePressed(e);
		window2.mousePressed(e);
		if (obj == materialsCategory) {
			scrollPane.setRecipeList(CraftCategory.MATERIALS);
		} else if (obj == toolsCategory) {
			scrollPane.setRecipeList(CraftCategory.TOOLS);
		} else if (obj == blocksCategory) {
			scrollPane.setRecipeList(CraftCategory.BLOCKS);
		} else if (obj == foodCategory) {
			scrollPane.setRecipeList(CraftCategory.FOOD);
		} 	

		int targetX = (int) (e.getX() - contents.getX() + scrollPane.getX());
		int targetY = (int) (e.getY() - contents.getY() - scrollPane.getY());
		System.out.println(targetX +" " +targetY + "        " + contents.getY());
		if (scrollPane.getContents().getElementAt(targetX,targetY) != null){
			System.out.println("CONTENTS PRESSED");
			System.out.println(scrollPane.getContents());
			ListItem target = (ListItem) scrollPane.getContents().getElementAt(targetX,targetY);
			if (target != null) {
				Recipe recipe = target.clickResponse();
				if (recipe != null) {
					window2.setRecipe(recipe);
				}
			}

		} 
	

	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		scrollPane.mouseMoved(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		scrollPane.mouseReleased(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		scrollPane.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
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
