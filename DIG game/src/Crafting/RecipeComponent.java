package Crafting;
import java.awt.Color;
import java.util.Random;

import acm.graphics.*;
import starter.GParagraph;
import starter.MainApplication;
public class RecipeComponent extends GCompound{
	//BACKEND COMPONENTS
	private MainApplication program;
	private Recipe recipe;
	
	//GRAPHICAL COMPONENTS
	private GImage input1Image;
	private GParagraph input1Quantity;
	private GParagraph plusSymbol;
	private GImage input2Image;
	private GParagraph input2Quantity;
	private GParagraph equalSymbol;
	private GImage outputImage;
	
	
	public RecipeComponent(MainApplication program,Recipe recipe) {
		Random rand = new Random();
		GCompound template = new GCompound();
		GRect frame = new GRect(0,0,MainApplication.WINDOW_WIDTH/2,120);
		
		this.input1Image = new GImage("items/Grass.png", 40, 35);
		input1Image.setSize(50,50);
		
		this.input1Quantity = new GParagraph("x17", 100, 70);
		input1Quantity.setFont("Arial_bold-40");
		
		//Check if the recipe input list size is greater than one before adding this
		this.plusSymbol = new GParagraph ("+", 175, 80);
		plusSymbol.setFont("Arial_bold-70");
		
		this.input2Image = new GImage("items/Grass.png", 240, 35);
		input2Image.setSize(50,50);
		
		this.input2Quantity = new GParagraph("x1", 300, 70);
		input2Quantity.setFont("Arial_bold-40");
		
		this.equalSymbol = new GParagraph ("=", 375, 80);
		equalSymbol.setFont("Arial_bold-70");
		
		this.outputImage = new GImage("items/Grass.png", 440, 35);
		outputImage.setSize(50,50);
		
		
		frame.setFilled(true);
		int min = 150;
		int max = 255;
		frame.setFillColor(new Color(rand.nextInt(max - min) + min,rand.nextInt(max - min) + min,rand.nextInt(max - min) + min));
		template.add(frame);
		
		GRect itemFrame = new GRect(10,10,MainApplication.WINDOW_WIDTH/2 - 20,100);
		itemFrame.setColor(Color.gray);
		itemFrame.setFilled(true);
		add(itemFrame);
		add(input1Image);
		add(input1Quantity);
		add(plusSymbol);
		add(input2Image);
		add(input2Quantity);
		add(equalSymbol);
		add(outputImage);
	}
}
