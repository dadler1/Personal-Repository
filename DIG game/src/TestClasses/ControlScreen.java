

package TestClasses;

//commennnt

//2
import starter.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ControlScreen extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GLabel controls;
	private GLabel jump;
	private GLabel moveLeft;
	private GLabel moveRight;
	private GLabel placeBlock;
	private GLabel destroyBlock;
	private GLabel pauseGame;
	private GButton spacebar;
	private GButton Akey;
	private GButton Dkey;
	private GImage leftClick;
	private GImage rightClick;
	private GButton esc;
	private GButton exit;
	private GImage screenBackground;
	private static final String CLICKSOUND="Minecraft-click.mp3";
	
	
	public ControlScreen(MainApplication app) throws FontFormatException, IOException {
		super();
		program = app;
	InputStream blockedFile=new FileInputStream("Minecraftia.ttf");
Font ControlsFont = Font.createFont(Font.TRUETYPE_FONT, blockedFile);
ControlsFont = ControlsFont.deriveFont(60.0f);
blockedFile.close();
InputStream blockedFile2=new FileInputStream("Minecraftia.ttf");
Font ActualControls= Font.createFont(Font.TRUETYPE_FONT, blockedFile2);
ActualControls = ActualControls.deriveFont(40.0f);
blockedFile2.close();


		//
	exit=new GButton("X", 1050, 20, 50, 50);
	exit.setFillColor(Color.red);
	exit.setColor(Color.white);
	
	controls=new GLabel("Controls", 450, 75);
	controls.setFont(ControlsFont);
	controls.setColor(Color.green);
	jump=new GLabel("Jump", 550, 200);
	jump.setFont(ActualControls);
	jump.setColor(Color.green);
	moveLeft=new GLabel("Move Left", 550, 275);
	moveLeft.setFont(ActualControls);
	moveLeft.setColor(Color.green);
	moveRight=new GLabel("Move Right", 550, 350);
	moveRight.setFont(ActualControls);
	moveRight.setColor(Color.green);
	placeBlock=new GLabel("Place Block / Interact", 500, 425);
	placeBlock.setFont(ActualControls);
	placeBlock.setColor(Color.green);
	destroyBlock=new GLabel("Destroy Block / Drag Items",415, 500);
	destroyBlock.setFont(ActualControls);
	destroyBlock.setColor(Color.green);
	pauseGame=new GLabel("Pause Game", 550, 575);
	pauseGame.setFont(ActualControls);
	pauseGame.setColor(Color.green);
	spacebar = new GButton("Spacebar", 215, 165, 250, 50);
	spacebar.setFillColor(Color.black);
	spacebar.setColor(Color.white);
	Akey=new GButton("A", 315, 235, 50, 50);
	Akey.setFillColor(Color.black);
	Akey.setColor(Color.white);
	Dkey=new GButton("D", 315, 310, 50, 50);
	Dkey.setFillColor(Color.black);
	Dkey.setColor(Color.white);

	leftClick= new GImage("leftclick.jpg", 300, 455);
	leftClick.setSize(75, 75);
	rightClick=new GImage("rightclick.jpg", 300, 370);
	rightClick.setSize(75, 75);
	esc=new GButton("esc", 300, 550, 70, 25);

	//leftClick= new GImage();
	//rightClick=new GImage();
	//esc=new GButton("esc", 190, 600, 70, 25);

	esc.setFillColor(Color.black);
	esc.setColor(Color.white);

	screenBackground=new GImage("ControlScreen.jpg", 0,0);
	screenBackground.setSize(program.getWidth(), program.getHeight());
	
	
	
	
	
	
	}

	@Override
	//look at Mini-Lab ArrayLists
	public void showContents() {
		
	program.add(screenBackground);
	program.add(controls);
	program.add(jump);
	program.add(moveLeft);
	program.add(moveRight);
	program.add(placeBlock);
	program.add(destroyBlock);
	program.add(pauseGame);
	program.add(spacebar);
	program.add(Akey);
	program.add(Dkey);
	program.add(leftClick);
	program.add(rightClick);
	program.add(esc);
	program.add(exit);
	
	

	}

	@Override
	public void hideContents() {
		program.remove(controls);
		program.remove(jump);
		program.remove(moveLeft);
		program.remove(moveRight);
		program.remove(placeBlock);
		program.remove(destroyBlock);
		program.remove(pauseGame);
		program.remove(spacebar);
		program.remove(Akey);
		program.remove(Dkey);
		program.remove(leftClick);
		program.remove(rightClick);
		program.remove(esc);
		program.remove(exit);
		program.remove(screenBackground);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj==exit) {
			program.playSound("sounds", CLICKSOUND, false);
			program.switchTo("MenuScreen");
		}
			
		}
	}

