package Worlds;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Item.BlockType;
import Item.EntityType;
import Item.Inventory;
import Item.InventoryItem;
import TheHelpers.ResourceLoader;
import UiComponents.HotbarUI;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import starter.AudioPlayer;
import starter.MainApplication;

public class WorldBlock extends GCompound{
	
	private static boolean loadedData = false; 
	static long seed = System.nanoTime();
	static int BLOCKSIZE = 64;
	private static double noise(double x, double y) {return PerlinNoise.noise(x, y, 0) + .5;}
	public static HashMap<String,String> modifiedBlocks = new HashMap<String,String>();
	private static String blockKey(int x, int y) {return "" + x + "," + y;}
	private static int getTypeDurability() {
		return 100; //Change this to return something based on the 
	}
	
	
	
	public static String getDataToSave() {
		String data = "";
		for (String key:modifiedBlocks.keySet()) {
			data = data + key + " ";
			data = data + modifiedBlocks.get(key) + "\n";
		}
		
		if (!loadedData) {
			return "NO SAVE"; //This makes it so that if they never load in the world, the save data wont be overwriten with nothing
		}
		
		return data;
	}
	
	private GImage blockImage;
	private GImage damageImage;
	private GImage selector;
	private int x;
	private int y;
	private int durability;
	
	private String blockType;
	
	
	//private static HashMap<String,Image> imgs;
	

	
	
	
	
	public void toggleSelector(boolean val) {
		if (val) {
			add(selector);
			return;
		}
		remove(selector);
	}
	
	
	
	static String blockLookUp(int x,int y) {
		
		if (loadedData == false) {
			loadedData = true;
			try {
				System.out.println("LOADED SAVED DATA");
				Scanner sc = new Scanner(new File("WorldSave"+MainApplication.dataVersion+".txt"));
				while (sc.hasNextLine()) {
					
					
					
					
					if (sc.hasNext() ) {
						String line = sc.next();
						if (sc.hasNext()) {
							String line2 = sc.next();
							modifiedBlocks.put(line, line2);
							if (sc.nextLine() == "") { break; };
						}
					} else {
						sc.close();
					}
					
				
				
					
					
				}
				loadedData = true;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
		
		String results = "Grass";
		
		if (modifiedBlocks.containsKey(blockKey(x,y))) {
			return modifiedBlocks.get(blockKey(x,y));
		}
		
		double maxHeight = (noise(.0015 * x,0) + .5 * noise(0.03 * x,0) + .25 * noise(0.0081*x,0))*128; 
				//noise(.041 * x,0) * 16;
		//System.out.println(maxHeight);
		//System.out.println(x);
		
		
		
		//Good way to increase chance with depth example:
		//double cobbleTest = noise(.23*(x+1234),.3*y)/2 + .5*noise(0.069*x,0.009*y) + ((y-maxHeight)*.015) - .85;
		
		
		//Need to make it so that the closer to the surface, the less common cobblestone is
		//Top layers cobble should be rarer
		double cobbleTest = noise(.23*(x+1234),.3*y)/2 + .5*noise(0.069*x,0.009*y) + ((y-maxHeight)*.015) - .45;
		
		if (cobbleTest > .5) {
			results = "CobbleStone";
			double lavaTest = noise(.23*(x+124),.3*y)/2 + .5*noise(0.069*x,0.009*y) + ((y-maxHeight)*.005) - .45;
			if (lavaTest > .5) {
				results = "Lava";
			}
		}
		
		
		//AIR OVERIDES ALL
		if (y < maxHeight) {
			results = "Air";
		}
		
		
		
		
		
		double oreTest = noise(.23*(x+7234354),.3*y)/2 + .5*noise(0.009*x,0.009*y); //+ ((y-maxHeight)*.015) - .45;
		if ((oreTest > .5 && oreTest <.52 )) {
			if (results != "Air") {
				results = "IronOreBlock";
			}
		
		
		}
		
		double oreTest2 = noise(.003*(x+72343564),.003*(y+noise(0.09*x,0.09*y)*50));// + .5*noise(0.009*x,0.009*y); //+ ((y-maxHeight)*.015) - .45;
		if ((oreTest2 > .5 && oreTest2 <.51 )) {
			if (results != "Air") {
				results = "DiamondOreBlock";
			}
		}
		
		
		//double airTest = noise(.003*x,.023*y) + .5*noise(0.009*x,0.069*y);
		double airTest = noise(.0023*(x+7234),.03*y)/2 + .5*noise(0.009*x,0.009*y); //+ ((y-maxHeight)*.015) - .45;
		if (!(airTest > .3 && airTest < .8)) {
			results = "CaveBackground";
		}
		
		
		
		if (results == "Grass") {
			if (blockLookUp(x,y-1) != "Air") {
				results = "Dirt";
			}
		}
		
		
		
		//TREE
		if (results == "Air") {
			if (x%10 == 0) {
				
				if (y < maxHeight && y > maxHeight - 6) {
					results = "Leaves";
				}
				
				if (y < maxHeight && y > maxHeight - 2) {
					results = "WoodLog";
				}
				
			}
		}
		
		
		return results;
	}
	
	public String getBlockType() {
		return blockType;
	}
	
	
	public BlockType stringToBlockType(String s) {
		
		switch(s){
		case "Dirt" : return BlockType.DIRT;

		case "WoodLog" : return BlockType.WOODLOG;

		case "CobbleStone" : return BlockType.COBBLESTONE;
		case "Leaves" : return BlockType.LEAVES;
		case "CaveBackground" : return BlockType.CAVEBACKGROUND;
		
		case "Lava" : return BlockType.LAVA;
		case "IronOreBlock" : return BlockType.IRONOREBLOCK;
		case "DiamondOreBlock" : return BlockType.DIAMONDOREBLOCK;
		
		//unimplemented  below

		
		/*
		case FURNACE: return "Furnace";
		case DOOR: return "Door";
		case WOOD_LOG: return "Wood Log";
		case WOOD_PLANK: return "Wood Plank";
		case DIRT: return "Dirt";
		case GRASS: return "Grass";
		case STONE: return "Stone";
		case COBBLE_STONE: return "Cobble_Stone";
		case LEAVES: return "Leaves";
		case COAL_ORE: return "Coal Ore";
		case IRON_ORE: return "Iron Ore";
		case DIAMOND_ORE: return "Diamond Ore";
		case LAVA: return "Lava";
		*/
		default: return BlockType.DIRT;

		}
		
	}
	
	public void damage(int dmg) {
		if (this.blockType.equals("Air")) { return; }
		
		String t = "grass";
		if (this.blockType == "CobbleStone" || this.blockType == "IronOreBlock" || this.blockType == "Lava" || this.blockType == "DiamondOreBlock") {
			t = "stone";
		}
		
		AudioPlayer audio = AudioPlayer.getInstance();
		
		String defaults = t + MainApplication.soundInc + ".mp3";
		audio.playSound("blockSounds", defaults);
		
		MainApplication.soundInc ++;
		if (MainApplication.soundInc > 4) {
			MainApplication.soundInc = 1;
		}
		
		
		this.durability -= dmg;
		if (this.durability<=0) {
			//I have the string of the block im breaking -> get inventory update
			//Inventory.addItem("Grass",1);
			//EntityType.
			System.out.println(Inventory.getInventory());
			Inventory.getInventory().addItem(new InventoryItem(stringToBlockType(this.blockType),1));
			//Inventory
			HotbarUI.Instance.update();
			
			setBlock("Air");
			remove(damageImage);
			
			
			damageImage = new GImage(ResourceLoader.loadResource("items/Air.png"));
		
		} else {
			double progress = 1 - (double)durability/getTypeDurability();
			int num = (int) Math.floor(progress*10);
			
				remove(damageImage);
				damageImage = new GImage(ResourceLoader.loadResource("random/break" + num + ".png"));
				damageImage.setSize(BLOCKSIZE,BLOCKSIZE);
				add(damageImage);
			
		}
	}
	
	
	public WorldBlock(int globalX,int globalY) throws IOException {
		blockImage = new GImage(ResourceLoader.loadResource("items/" + blockLookUp(globalX,globalY) + ".png")); // new GImage("items/" + blockLookUp(globalX,globalY) + ".png");
		selector = new GImage(ResourceLoader.loadResource("items/BlockedAir.png"));
		damageImage = new GImage(ResourceLoader.loadResource("items/Air.png"));
		
		this.x = globalX;
		this.y = globalY;
		
		this.blockType = blockLookUp(globalX,globalY);
		this.durability = getTypeDurability();
		
		blockImage.setSize(BLOCKSIZE, BLOCKSIZE);
		selector.setSize(BLOCKSIZE,BLOCKSIZE);
		damageImage.setSize(BLOCKSIZE,BLOCKSIZE);
		
		add(blockImage);
		add(damageImage);
	}
	
	public void setBlock(String type) {
	
			remove(blockImage);
			remove(selector);
			this.blockType = type;
			
			//If setting the block to type "Air", then add the block to the players inventory
			durability = getTypeDurability();
			
			blockImage = new GImage(ResourceLoader.loadResource( "items/" + type + ".png"));
			blockImage.setSize(BLOCKSIZE, BLOCKSIZE);
			selector.setSize(BLOCKSIZE,BLOCKSIZE);
			damageImage.setSize(BLOCKSIZE,BLOCKSIZE);
			
			modifiedBlocks.put(blockKey(x,y), type);
			add(blockImage);
			add(selector);
		
			
	}
	
	
}
