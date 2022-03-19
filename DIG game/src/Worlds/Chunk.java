package Worlds;

import java.util.HashMap;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import starter.MainApplication;

public class Chunk extends GCompound{
private MainApplication program; // you will use program to get access to
	
	private GImage testBlock;
	private HashMap<String,WorldBlock> blocks;
	private Vector2 location;
	private int x;
	private int y;
	public static final int CHUNKSIZE = 1;
	private final int BLOCKSIZE = WorldBlock.BLOCKSIZE;
	public boolean initSuccess = true;
	
	private static HashMap<String,Boolean> noCollideBlocks = new HashMap<String,Boolean>();
	
	static {
		String[] blocks = {"Air","BlockedAir","WoodLog","Leaves","CaveBackground"};
		for (String block:blocks) {
			noCollideBlocks.put(block, true);
		}
	}
	
	public static boolean isCollide(String block) {
		return noCollideBlocks.containsKey(block);
	}
	
	private void addBlock(Vector2 location,WorldBlock block) {
		removeBlock(location);
		
		blocks.put(location.toString(), block);
	
		if (block != null) {
			block.setLocation(location.getX() * BLOCKSIZE, BLOCKSIZE * location.getY());
			//System.out.println(block);
			add(block);
		}
		
	}
	
	public WorldBlock getBlock(Vector2 location) {
		return blocks.get(location.toString());
	}
	
	
	
	public boolean isAir() {
		if (blocks.get(new Vector2(0,0).toString()) != null) {
			String id = new Vector2(0,0).toString();
			String type = blocks.get(id).getBlockType();
			//System.out.println(id);
			if (WorldBlock.modifiedBlocks.get(location.toString()) != null) {
				type = WorldBlock.modifiedBlocks.get(location.toString());
			}
			
			return  noCollideBlocks.containsKey(type);// type.equals("Air") || type.equals("BlockedAir") || type.equals("WoodLog") || type.equals("Leaves") || type.equals("CaveBackground");
		} else {
			return false;
		}
		
		
	}
	
	void removeBlock(Vector2 location) {
		
		
		if (blocks.get(location.toString()) != null) {
			remove(blocks.get(location.toString()));
		}
		
		blocks.remove(location.toString());
	}
	
	public int getXVal() {
		return x;
	}
	
	public int getYVal() {
		return y;
	}
	
	public void setBlock(Vector2 location,WorldBlock block) {
		addBlock(location,block);
	}
	
	private void init() {
		blocks = new HashMap<String,WorldBlock>();
		for (int tx = 0;tx < CHUNKSIZE;tx++) {
			for (int ty = 0; ty < CHUNKSIZE; ty++) {
				try {
					//Sometimes the image doesnt load
					WorldBlock newBlock = new WorldBlock((x*CHUNKSIZE) + tx,(y*CHUNKSIZE) + ty);
					addBlock(new Vector2(tx, ty),newBlock);
				} catch (Exception e){
					initSuccess = false;
					System.out.println("FAILURE TO CREATE BLOCK! TOO MUCH AT ONCE?");
					removeBlock(new Vector2(tx,ty));
				}
			}
		}
	}
	
	public Chunk(int tx, int ty) {
		super();
		x = tx;
		y = ty;
		location = new Vector2(x,y);
		init();
	}
}
