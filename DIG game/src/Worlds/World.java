package Worlds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import Item.BlockType;
import Item.Inventory;
import Item.InventoryItem;
import TestClasses.WorldScreen;
import TheHelpers.ResourceLoader;
import UiComponents.HotbarUI;
import starter.*;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GPoint;
import acm.graphics.GRect;

public class World extends GCompound implements ActionListener, Interfaceable{
	private MainApplication program; // you will use program to get access to

	
	private GImage testBlock;
	private GImage background;
	private GCompound bufferCompound;
	private GImage blockSelector;
	private WorldBlock lastTarget = null;
	
	private HashMap<String,Chunk> chunks;
	private Vector2 camera;
	private Timer moveTimer;
	
	private Vector2 cameraSpeed = new Vector2(32,0);
	
	private Player player;
	public boolean isVisible = false;
	private int rotation = 0;
	private GParagraph coords;
	private GParagraph vel;
	private GParagraph acc;
	private boolean editing = false;
	private long lastHit = System.currentTimeMillis();
	private GRect damageScreen;
	private int damageDuration = 0;
	private MouseEvent recentEvent = null;
	
	Vector2 mouse;
	
	private final int BLOCKSIZE = WorldBlock.BLOCKSIZE;
	int numChunks = 0;
	
	
	public void setCameraSpeed(Vector2 speed) {
		cameraSpeed = speed;
	}
	
	private void loadChunk(int x,int y) {
		if (chunks.containsKey(new Vector2(x,y).toString()) == false) {
		
			
			Chunk newChunk = new Chunk(x,y);
			
			if (newChunk.initSuccess) {
				numChunks++;
				newChunk.setLocation(x*newChunk.getBounds().getWidth(), y*newChunk.getBounds().getHeight());
				
				chunks.put(new Vector2(x,y).toString(), newChunk);
				bufferCompound.add(newChunk);
			}
			
		}
	}
	
	
	private void loadVisibleChunks() {
		//start at x = 0
		//see how many chunks can fit in width
		int horChunks = (int) Math.ceil(this.program.getWidth()/(BLOCKSIZE*Chunk.CHUNKSIZE)) + 1;
		int vertChunks = (int) Math.ceil(this.program.getHeight()/(BLOCKSIZE*Chunk.CHUNKSIZE)) + 1;
		
		int startX = (int) Math.floor(camera.getX()/(BLOCKSIZE*Chunk.CHUNKSIZE));
		int startY = (int) Math.floor(camera.getY()/(BLOCKSIZE*Chunk.CHUNKSIZE));
		for (int x = startX - 1;x<horChunks + startX + 1;x++) {
			for (int y = startY - 1; y < vertChunks + startY + 1;y++) {
				loadChunk(x,y);
			}
		}
	}
	
	public void playerDamaged() {
		damageDuration = 255;
		damageScreen.setFillColor(new Color(255,0,0,damageDuration));
	}
	
	private void init() {
		camera = new Vector2(0,program.getHeight()/2);
		mouse = new Vector2(0,0);
		chunks = new HashMap<String,Chunk>();
		bufferCompound = new GCompound();
		add(bufferCompound);
		loadVisibleChunks();
		
	}
	
	public World(MainApplication app) {
		super();
		this.program = app;
		
		background = new GImage("Sky.png");
		add(background);
		
		blockSelector = new GImage("BlockedAir.png");
		
		damageScreen = new GRect(0,0,this.program.getWidth(),this.program.getHeight());
		damageScreen.setFilled(true);
		damageScreen.setColor(new Color(255,0,0,0));
		
		
		coords = new GParagraph("Location", 20, 50);
		coords.setFont(new Font("Serif", Font.BOLD, 36));
		add(coords);
		
		vel = new GParagraph("Location", 20, 100);
		vel.setColor(new Color(0,255,0));
		vel.setFont(new Font("Serif", Font.BOLD, 36));
		//add(vel);
		
		acc = new GParagraph("Location", 20, 150);
		acc.setColor(new Color(200,10,10));
		acc.setFont(new Font("Serif", Font.BOLD, 36));
		//add(acc);
		
		init();
		
		try {
			player = new Player(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(player);
		moveTimer = new Timer(25,this);
		moveTimer.start();
	}
	
	
	private GCompound collisionBlocks;
	
	
	private int convertPointToCell(int x) {
		return (int)(camera.getX() + x)/BLOCKSIZE;
	}
	
	
	public Vector2 getPlayerPosition() {
		Vector2 results = new Vector2(0,0);
		results.setX((int)(camera.getX() + player.getBounds().getX())/BLOCKSIZE);
		results.setY((int)(camera.getY() + player.getBounds().getY())/BLOCKSIZE);
		return results;
	}
	
	public Vector2 getPlayerPositionStepped(Vector2 change) {
		Vector2 results = new Vector2(0,0);
		results.setX((int)(camera.getX() + change.getX() + player.getBounds().getX())/BLOCKSIZE);
		results.setY((int)(camera.getY() + change.getY() + player.getBounds().getY())/BLOCKSIZE);
		return results;
	}
	
	public boolean isPixelAir(Vector2 location) {
		//We need to see what block the pixel is actually in
		Vector2 locationInWorld = new Vector2((int)Math.floor(location.getX()/WorldBlock.BLOCKSIZE),(int)Math.floor(location.getY()/WorldBlock.BLOCKSIZE));
		
		return isAir(locationInWorld);
		
		//return false;
	}
	
	public boolean isAir(Vector2 location) {
		if (!chunks.containsKey(location.toString())) {
			loadChunk(location.getX(),location.getY());
		}
		
		if (chunks.containsKey(location.toString())) {
			//Check if its air
			Chunk chunk = chunks.get(location.toString());
			//System.out.println(chunk.isAir());
			return chunk.isAir();
			
		} else {
			//System.out.println("DOUBLE FAIL");
			return true;
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (!isVisible) { return; };

		Point p = program.getMousePosition();
		if (p != null) {
			mouse.setX(p.x);
			mouse.setY(p.y);
		}
		
		WorldBlock.getDataToSave();
		
		player.applyPhyics();
		remove(player);
		
		if (collisionBlocks != null) {
			remove(collisionBlocks);
		}
		

		camera.setX(player.getWorldPosition().getX() - (program.getWidth()/2) + BLOCKSIZE/2);
		camera.setY(player.getWorldPosition().getY() - (program.getHeight()/2) + BLOCKSIZE/2);
		
		//Block x and y that mouse is over
		int x = (int)(camera.getX() + mouse.getX())/BLOCKSIZE;
		int y = (int)(camera.getY() + mouse.getY())/BLOCKSIZE;
		
	
		if (camera.getX() + mouse.getX() < 0) {
			x--;
		}
		
		//This is the target chunk
		String target = new Vector2(x,y).toString();
		
		Chunk targetChunk = null;
		if (!chunks.containsKey(target)) { loadChunk(x,y); };
		targetChunk = chunks.get(target);
		
		if (lastTarget != null) {
			lastTarget.toggleSelector(false);
		}
		
		WorldBlock targetBlock = null;
		if (targetChunk.getBlock(new Vector2(0,0)) != null) {
			targetBlock = targetChunk.getBlock(new Vector2(0,0));
			targetBlock.toggleSelector(true);
			lastTarget = targetBlock;
		}
		
		
		
		Vector2 playerPosition = getPlayerPosition();
		
		
		coords.setText(playerPosition.getX() + ", " + playerPosition.getY());
		vel.setText(Math.floor(player.getVelocity().getX() * 100)/100 + ", " + Math.floor(player.getVelocity().getY() * 100)/100 + " VELOCITY");
		acc.setText(player.getAcceleration().getX() + ", " + player.getAcceleration().getY() + " ACCELERATION");
				
		

		if (!chunks.containsKey(playerPosition.toString())) { loadChunk(playerPosition.getX(),playerPosition.getY());}
		
		
		//top left corner of the player
		if (!chunks.containsKey(playerPosition.toString())) { loadChunk(playerPosition.getX(),playerPosition.getY());}
		
		
		bufferCompound.setLocation(-camera.getX(),-camera.getY());
		background.setBounds(0,0,program.getWidth(),program.getHeight());
		
		int minVisibleChunkX = (int) Math.floor(camera.getX()/(BLOCKSIZE*Chunk.CHUNKSIZE));
		
		
		ArrayList<String> deleteKeys = new ArrayList<String>();
		
		//Remove offscreen blocks/chunks
		for (Map.Entry<String, Chunk> entry : chunks.entrySet()) {
			if (entry.getValue().getXVal() < minVisibleChunkX) {
				String key = entry.getKey();
				Chunk value = entry.getValue();
				
				deleteKeys.add(key);
			}
		}
		
		//We have the keys we are about to delete, maybe we can reuse them in later implementation
		for (String key: deleteKeys) {
			bufferCompound.remove(chunks.get(key));
			chunks.remove(key);
			numChunks--;
		}
		
		
		
		loadVisibleChunks();
		add(player);
		bufferCompound.add(blockSelector);
		
		if (collisionBlocks != null) {
			add(collisionBlocks);
		}
		
		player.setSize(BLOCKSIZE, BLOCKSIZE*2);
		
		
		player.setLocation((program.getWidth()/2) - player.getWidth()/2, (program.getHeight()/2) - player.getHeight()/2);
		
		remove(coords);
		add(coords);
		
		//remove(vel);
		//add(vel);
		
		//remove(acc);
		//add(acc);
		
		blockSelector.setSize(BLOCKSIZE, BLOCKSIZE);
	//	blockSelector.setLocation(arg0);
		add(blockSelector);
		add(damageScreen);
		damageScreen.setFillColor(new Color(255,0,0,damageDuration));
		if (damageDuration > 0) {
			damageDuration-= 10;
			if (damageDuration <= 0) {
				damageDuration = 0;
			}
		}
		
	
		attemptEditing(null);
	}
	
	private void attemptEditing(MouseEvent e) {
		
		if (e != null) {
			recentEvent = e;
		}
		
		if (WorldScreen.getState() != "World"){ return;}
		
		
		if (recentEvent != null) {
			if (recentEvent.getButton() == 1) {
				editing = true;
				attemptBreaking();
			} else if(recentEvent.getButton() == 3) {
				editing = true;
				attemptBuilding();
			}
		}
	}
	
	
	
	private void attemptBreaking() {
		if (editing) {
			if (lastTarget != null) {
				if (!lastTarget.getBlockType().equals("Air")) {
					//see if enough time has passed
					if (System.currentTimeMillis() - lastHit > 250) {
						lastHit = System.currentTimeMillis();
					
						lastTarget.damage(20);
					}
				}
			}
		}
	}
	 
	private void attemptBuilding() {
		if(editing) {
			if (lastTarget!= null) {
				if (Chunk.isCollide(lastTarget.getBlockType())) {  //lastTarget.getBlockType().equals("Air")  
					if (System.currentTimeMillis() - lastHit > 250) {
						lastHit = System.currentTimeMillis();
						
						Inventory inv = Inventory.getInventory();
						HotbarUI hotbar = WorldScreen.getHotbar();
						InventoryItem tempItem = hotbar.getSelected();
						hotbar.update();
						int distance = (int) Math.floor(Math.sqrt(Math.pow((player.getWorldPosition().getX()-lastTarget.getX()),2)+Math.pow((player.getWorldPosition().getY()-lastTarget.getY()),2)));
						System.out.println("Distance:" + distance);
						//if(distance <= 4) {
							if(tempItem != null) {
								if(tempItem.getQuantity() > 0) {
									if(tempItem.getType() instanceof BlockType) {
										lastTarget.setBlock(tempItem.getName());
										//tempItem.removeFromStack(1);
										inv.removeItem(new InventoryItem(tempItem.getType(), 1));
										try {
											inv.createSaveLoc();
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										hotbar.update();
									}
								}
							}
						//}
						
					}
				}
			}
		}
	}

	@Override
	public void showContents() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mousePressed(e);
		attemptEditing(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		editing = false;
		recentEvent = null;
		player.mouseReleased(e);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mouseClicked(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		player.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		player.keyTyped(e);
	}
	
}
