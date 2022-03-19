package starter;

import java.awt.FontFormatException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Item.Inventory;
import TestClasses.*;
import Worlds.WorldBlock;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1155;//1280;//1155
	public static final int WINDOW_HEIGHT = 650;//720;//650
	public static final String MUSIC_FOLDER = "sounds";
	public static int soundInc = 1;// to a max of 4
	private static final String[] SOUND_FILES = { "r2d2.mp3", "somethinlikethis.mp3","Wet Hands.mp3"};
	public static final String CLICKSOUND= "Minecraft-click.mp3";
	private SomePane somePane; 
	private TestMenuPane menu;
	private CraftingScreen craftingScreen;
	public static InventoryScreen inventoryScreen;
	private MenuScreen menuScreen;
	private WorldScreen worldScreen;
	private ControlScreen controlScreen;
	public static double dataVersion = 1.011;
 	
	private int count;
	private Tutorial tutorial;

	public void init() { 
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		//addExitHook(this);
	}
	
	public void run() {
		//playSound("sounds", "r2d2.mp3", true);
		somePane = new SomePane(this);
		menu = new TestMenuPane(this);
		try {
			inventoryScreen = new InventoryScreen(this);
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		craftingScreen = new CraftingScreen(this);
		try {
			menuScreen = new MenuScreen(this);
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		worldScreen = new WorldScreen(this);
		tutorial = new Tutorial(this);
		try {
			controlScreen=new ControlScreen(this);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switchToMenu();
		switchToScreen(menuScreen);
		//And we start music 
	
		
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(){ 
			public void run() {  
				String saveData = WorldBlock.getDataToSave();
				
				if (saveData == "NO SAVE") { return; };
				
				try {
					FileWriter fw = new FileWriter("WorldSave"+dataVersion+".txt",false);
					BufferedWriter writer = new BufferedWriter(fw);
					writer.write(saveData);
					writer.newLine();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					Inventory.getInventory().createSaveLoc();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} 
		}); 
	}

	public void switchToMenu() {
		//playRandomSound();
		//AudioPlayer audio = AudioPlayer.getInstance();
		playSound("sounds", "MinecraftTheme.mp3", true);
		count++;
		switchToScreen(menu);
		
	}

	public void switchToSome() {
		//playRandomSound();
		//AudioPlayer audio = AudioPlayer.getInstance();
		playSound("sounds", "MinecraftTheme.mp3", true);
		switchToScreen(somePane);
	}
	
	public void switchTo(String screen) {
		switch (screen) {
		case "CraftingScreen":
			//switchToScreen(craftingScreen);
			break;
		case "WorldScreen":
			switchToScreen(worldScreen);
			break;
		case "MenuScreen":
			//worldScreen.showContents();
			switchToScreen(menuScreen);
			break;
		case "InventoryScreen":
			switchToScreen(inventoryScreen);
			break;
		case "ControlScreen":
			switchToScreen(controlScreen);
			break;
		case "Tutorial":
			switchToScreen(tutorial);
			break;
		default: 
			System.out.println("Not a valid screen");
		}

	}
	//
	public void playSound(String folder, String filename, boolean shouldLoop) {
		AudioPlayer audio= AudioPlayer.getInstance();
		audio.playSound(folder, filename, shouldLoop);
	}
}
