package Worlds;

import java.awt.Color;
import java.util.HashMap;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import TheHelpers.ResourceLoader;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import starter.AudioPlayer;
import starter.Interfaceable;
import starter.MainApplication;

public class Player extends GCompound implements Interfaceable {
	private Vector2 worldPosition = new Vector2(-WorldBlock.BLOCKSIZE*0,40*WorldBlock.BLOCKSIZE);//203000);
	private World world;
	
	private Vector2D acceleration = new Vector2D(0.0,2.8);
	private Vector2D velocity = new Vector2D(0,0);
	private char c;
	private int maxAcceleration = 5;
	private int maxVelocity = 17;
	private int maxHorizontalVelocity = 40;
	private double horizontalDecay = .87;
	private int jumpPower = 30;
	private GCompound physicsNodes;
	private HashMap<Integer,Boolean> keys = new HashMap<Integer,Boolean>();
	private boolean canJump = true;
	private GImage playerSprite;
	private int walkInc = 1;
	private int hitInc = 1;
	private boolean showPhysics = false; 
	private String facing = "Right";
	
	public void setAcceloration(Vector2D acc) {
		this.acceleration = acc;
	}
	
	public Vector2 getWorldPosition() {
		return worldPosition;
	}
	
	public void setWorldPosition(Vector2 location) {
		worldPosition = location;
	}
	
	public Vector2D getAcceleration() {
		return acceleration;
	}
	
	public Vector2D getVelocity() {
		return velocity;
	}
	
	public void placeBlock() {
		System.out.println("Trying to place block");
	}
	
	
	final int HITPOINT_SIZE = 4;
	//final int HITPOINT_OFFSET = HIT
	//BOTTOM NODES ARE INDEX 2 AND 5
	private boolean[] renderCollisions(Vector2 destination) {
		if (destination.getX() < 0) {
			destination.setX(destination.getX() - WorldBlock.BLOCKSIZE);
		}
		boolean[] results = new boolean[6];
		remove(physicsNodes);
		physicsNodes = new GCompound();
		GRect topLeft = new GRect(0,0,HITPOINT_SIZE,HITPOINT_SIZE);
		topLeft.setFilled(true);
		topLeft.setFillColor(new Color(0,255,0));
		physicsNodes.add(topLeft);
		
		GRect topRight = new GRect(this.getWidth() - HITPOINT_SIZE - 1,0,HITPOINT_SIZE,HITPOINT_SIZE);
		topRight.setFilled(true);
		topRight.setFillColor(new Color(0,255,0));
		physicsNodes.add(topRight);
		
		GRect middleRight = new GRect(this.getWidth() - HITPOINT_SIZE - 1,this.getHeight()/2 - 5,HITPOINT_SIZE,HITPOINT_SIZE);
		middleRight.setFilled(true);
		middleRight.setFillColor(new Color(0,255,0));
		physicsNodes.add(middleRight);
		
		GRect middleLeft = new GRect(0,this.getHeight()/2 - 5,HITPOINT_SIZE,HITPOINT_SIZE);
		middleLeft.setFilled(true);
		middleLeft.setFillColor(new Color(0,255,0));
		physicsNodes.add(middleLeft);
		
		GRect botLeft = new GRect(0,this.getHeight() - HITPOINT_SIZE - 1,HITPOINT_SIZE,HITPOINT_SIZE);
		botLeft.setFilled(true);
		botLeft.setFillColor(new Color(0,255,0));
		physicsNodes.add(botLeft);
		
		GRect botRight = new GRect(this.getWidth() - HITPOINT_SIZE - 1,this.getHeight() - HITPOINT_SIZE - 1,HITPOINT_SIZE,HITPOINT_SIZE);
		botRight.setFilled(true);
		botRight.setFillColor(new Color(0,255,0));
		physicsNodes.add(botRight);
		
		//Collisions detected
		
		//Adding or subtracting 1 so that things can something the size of 32 can fit inside a hole of 32 perfectly
		Vector2 topLeftV = new Vector2(destination.getX() + 1,destination.getY() - WorldBlock.BLOCKSIZE/2 + 1);
		if (world.isPixelAir(topLeftV) == false) {
			results[0] = true;
			topLeft.setFillColor(new Color(255,0,0));
			topLeft.setColor(new Color(255,0,0));
		} else {
			results[0] = false;
		}
		

		Vector2 topRightV = new Vector2(destination.getX() - 1 + WorldBlock.BLOCKSIZE,destination.getY() - WorldBlock.BLOCKSIZE/2 + 1);
		if (world.isPixelAir(topRightV) == false) {
			results[3] = true;
			topRight.setFillColor(new Color(255,0,0));
			topRight.setColor(new Color(255,0,0));
		} else {
			results[3] = false;
		}
		
		Vector2 midLeftV = new Vector2(destination.getX() + 1,destination.getY() + WorldBlock.BLOCKSIZE - WorldBlock.BLOCKSIZE/2);
		if (world.isPixelAir(midLeftV) == false) {
			results[1] = true;
			middleLeft.setFillColor(new Color(255,0,0));
			middleLeft.setColor(new Color(255,0,0));
		} else {
			results[1] = false;
		}
		
		Vector2 botLeftV = new Vector2(destination.getX() + 1,destination.getY() + (WorldBlock.BLOCKSIZE*2) - WorldBlock.BLOCKSIZE/2);
		if (world.isPixelAir(botLeftV) == false) {
			results[2] = true;
			botLeft.setFillColor(new Color(255,0,0));
			botLeft.setColor(new Color(255,0,0));
		}else {
			results[2] = false;
		}
		


		
		
		Vector2 midRightV = new Vector2(destination.getX() + WorldBlock.BLOCKSIZE - 1,destination.getY() - WorldBlock.BLOCKSIZE/2 + WorldBlock.BLOCKSIZE);
		if (world.isPixelAir(midRightV) == false) {
			results[4] = true;
			middleRight.setFillColor(new Color(255,0,0));
			middleRight.setColor(new Color(255,0,0));
		} else {
			results[4] = false;
		}
		
		Vector2 botRightV = new Vector2(destination.getX() + WorldBlock.BLOCKSIZE - 1,destination.getY() - WorldBlock.BLOCKSIZE/2 + WorldBlock.BLOCKSIZE*2);
		
		if (world.isPixelAir(botRightV) == false) {
			results[5] = true;
			botRight.setFillColor(new Color(255,0,0));
			botRight.setColor(new Color(255,0,0));
		} else {
			results[5] = false;
		}
		
		
		
		return results;
	}
	
	public void applyPhyics() {
		//acceleration.setY(acceleration.getY()*.98);
		
		Vector2D lastWorldPosition  = new Vector2D(worldPosition.getX(),worldPosition.getY());
		
		boolean movingVerticle = false;
		if (keys.get(68) != null) {
			if (keys.get(68) == true) {
				//System.out.println("Moving Right");
				movingVerticle = true;
				acceleration.setX(1);
			}
		}
		
		if (keys.get(65) != null) {
			if (keys.get(65) == true) {
				//System.out.println("Moving Left");
				movingVerticle = true;
				acceleration.setX(-1);
			}
		}
		
		if (!movingVerticle) {
			velocity.setX(velocity.getX() * horizontalDecay);
			//System.out.println("Not Moving");
			acceleration.setX(0);
		}

		
		//Jumping 
		if (keys.get(32) != null) {
			if (keys.get(32) == true) {
				//System.out.println("Moving Right");
				movingVerticle = true;
				if (canJump) {
					velocity.setY(-jumpPower);
					canJump = false;
				}
				
			}
		}
		
		
		acceleration.setY(acceleration.getY() + 0);
		acceleration.setX(acceleration.getX() + 0);
		
		if (acceleration.getX() < -maxAcceleration) {
			acceleration.setX(-maxAcceleration);
		}
		
		if (acceleration.getX() > maxAcceleration) {
			acceleration.setX(maxAcceleration);
		}
		 
		
		if (acceleration.getY() < -maxAcceleration) {
			acceleration.setY(-maxAcceleration);
		}
		
		if (acceleration.getY() >maxAcceleration) {
			acceleration.setY(maxAcceleration);
		}
		
		velocity.setY(velocity.getY() + acceleration.getY());
		
		
		velocity.setX(velocity.getX() + acceleration.getX());
		
		
		if (velocity.getX() < -maxVelocity) {
			velocity.setX(-maxVelocity);
		}
		
		if (velocity.getX() > maxVelocity) {
			velocity.setX(maxVelocity);
		}
		 
		
		if (velocity.getY() < -maxHorizontalVelocity) {
			velocity.setY(-maxHorizontalVelocity);
		}
		
		if (velocity.getY() >maxHorizontalVelocity) {
			velocity.setY(maxHorizontalVelocity);
		}
		
		if (velocity.getX() < 0) {
			if (facing == "Right") {
				facing = "Left";
				playerSprite.setImage(ResourceLoader.loadResource("PlayerLeft.png"));
			}
		} else {
			if (facing == "Left") {
				facing = "Right";
				playerSprite.setImage(ResourceLoader.loadResource("Player.png"));
				
			}
		}
		
		//Debugging visible nodes to detect collision
		
		
		Vector2D destination = new Vector2D(worldPosition.getX() + velocity.getX(),worldPosition.getY() + velocity.getY());
		

		boolean c = false;
		boolean hitGround = false;
		int numCollisions  = 0;
		Vector2 resultVelocity = new Vector2(0,0);
		for (int x = 0;x<= Math.abs(velocity.getX());x++) {
			for (int y = 0; y<= Math.abs(velocity.getY());y++) {
				
				
				//Check if the location is air
				int tx = x;
				int ty = y;
				
				if (velocity.getX() < 0) {
					tx *= -1;
				}
				
				if (velocity.getY() < 0) {
					ty *= -1;
				}
				
				
				Vector2 thisStepsDestination = new Vector2(worldPosition.getX() + tx,worldPosition.getY() + ty);
				boolean[] collisions = renderCollisions(thisStepsDestination);
				String results = "";
				boolean passThisRound = true;
				int index = 0;
				
				for (boolean result:collisions) {
					results = results + " " + result;
					if (result==false) { 
						//no collision yet
						if (c == false) {
							//
						} else {
							c = true;
						}
					}  else {
						numCollisions++;
						passThisRound = false;
					}
					index++;
				}
				
				if (passThisRound && c == false) {
					resultVelocity.setX(tx);
					resultVelocity.setY(ty);
				}
			
				
				
			}
		}
		
		if (c) {
			//COLLISION DID HAPPEN
		}

		
		
		
		worldPosition.setX(worldPosition.getX() + resultVelocity.getX());
		worldPosition.setY(worldPosition.getY() + resultVelocity.getY());
		
		if (numCollisions == 0) {
			canJump = false;
		}
		
		if (lastWorldPosition.getY() == worldPosition.getY()) {
			//we hit a ground
			if (numCollisions>0) {
				//System.out.println(velocity.getY());
				
				
				if (velocity.getY()>= 40) {
					world.playerDamaged();
					
					//Hurt noise
					AudioPlayer audio = AudioPlayer.getInstance();
					
					String defaults = "hit" + hitInc + ".mp3";
					audio.playSound("randomSounds", defaults);
					hitInc++;
					if (hitInc >3) {
						hitInc = 1;
					}
				}
				
				if (!canJump) {
					AudioPlayer audio = AudioPlayer.getInstance();
					
					String defaults = "grass" + walkInc + ".mp3";
					audio.playSound("walkingBlockSounds", defaults);
					
					walkInc ++;
					if (walkInc > 6) {
						walkInc = 1;
					}
				}
				canJump = true;
				velocity.setY(5);
			}
		}
		
		if (showPhysics) {
			add(physicsNodes);
		}
		
	}
	
	public Player(World world) throws IOException {
		this.setLocation(worldPosition.getX(), worldPosition.getY());
		this.world = world;
		physicsNodes = new GCompound();

		
		playerSprite = new GImage(ResourceLoader.loadResource("Player.png"));
		add(playerSprite);
		if (showPhysics) {
			add(physicsNodes);
		}
	}

	public void setSize(int width, int height) {
		// TODO Auto-generated method stub
		this.playerSprite.setSize(width, height);
		
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		e.consume();
		// TODO Auto-generated method stub
		if (world == null) { return; };
		//System.out.println("Key pressed");
		//System.out.println(e);
		
		//System.out.println(e.getKeyChar());
		keys.put(e.getKeyCode(), true);
	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
		e.consume();
		if (world == null) { return; };
		keys.put(e.getKeyCode(), false);
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
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

}
