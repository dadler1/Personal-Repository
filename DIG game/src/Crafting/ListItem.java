package Crafting;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import TestClasses.CraftingScreen;
import TheHelpers.ResourceLoader;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import starter.GParagraph;

public class ListItem extends GCompound{
	private String titleText = "Default Text";
	private String captionText = "Click to Craft";
	private int listPosition = -1;
	private GParagraph caption;
	private GParagraph titleLine;
	private GImage icon;
	private CraftingScreen parent;
	private Recipe linkedRecipe = null;
	
	public void setCaptionText(String text) {
		captionText = text;
		caption.setText(captionText);
		caption.setLocation(15  + 100,caption.getBounds().getHeight() + titleLine.getHeight());
	}
	
	public void setTitleText(String text) {
		titleText = text;
		titleLine.setText(titleText);
		titleLine.setLocation(15  + 100,titleLine.getBounds().getHeight());
	}
	
	public void setListPosition(int num) {
		this.listPosition = num;
	}
	
	public int getListPosition() {
		return this.listPosition;
	}
	
	public void setImageFromTitle() {
		icon.setImage(ResourceLoader.loadResource("items/"+titleText+".png"));
		icon.setSize(80, 80);
	}
	
	
	public void linkRecipe(Recipe recipe) {
		this.linkedRecipe = recipe;
	}
	
	public Recipe getLinkedRecipe() {
		return this.linkedRecipe;
	}
	
	
	public Recipe clickResponse() {
		String goal = "You clicked this ListItem!";
		if (this.captionText.equals(goal)){
			setCaptionText("Default Text");
		} else {
			setCaptionText(goal);
		}
		
		// We need to get the recipe for the Text
		return linkedRecipe;
	}
	
	public ListItem(ScrollPane scrollPane) {
		Random rand = new Random();
		int width = (int) (scrollPane.getWidth() - scrollPane.getScrollbarWidth());
		
		//acts as the borders
		GRect frame = new GRect(0,0,width,120);
		frame.setFilled(true);
		int min = 150;
		int max = 255;
		frame.setFillColor(new Color(rand.nextInt(max - min) + min,rand.nextInt(max - min) + min,rand.nextInt(max - min) + min));
		add(frame);
		
		//background
		GRect itemFrame = new GRect(10,10,width - 20,100);
		itemFrame.setFilled(true);
		itemFrame.setFillColor(new Color(0,0,0,155));
		add(itemFrame);
		
		
	
		Font TitleFont = null; //default
		try {
			InputStream blockedFile4=new FileInputStream("Minecraftia.ttf");
			TitleFont = Font.createFont(Font.TRUETYPE_FONT, blockedFile4);
			TitleFont = TitleFont.deriveFont(30.0f);
			blockedFile4.close();
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Font SubHeadingFont = null; //default
		try {
			InputStream blockedFile4=new FileInputStream("Minecraftia.ttf");
			SubHeadingFont = Font.createFont(Font.TRUETYPE_FONT, blockedFile4);
			SubHeadingFont = SubHeadingFont.deriveFont(20.0f);
			blockedFile4.close();
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		//omg 
		String itemRealName = "Grass";
		icon = new GImage(ResourceLoader.loadResource("items/"+itemRealName+".png"));
		icon.setSize(80,80);
		icon.setLocation(20,20);
		add(icon);
		
		titleLine = new GParagraph(titleText,0,0);// + "\n 12 3 4 5 6 7 8 9 0 - ",0,0);
		titleLine.setFont(TitleFont);
		titleLine.setColor(Color.white);
		setTitleText(titleText);
		add(titleLine);
		
		caption = new GParagraph(captionText,0,0);// + "\n 12 3 4 5 6 7 8 9 0 - ",0,0);
		caption.setFont(SubHeadingFont);
		caption.setColor(Color.white);
		setCaptionText(titleText);
		add(caption);
		
	}
}
