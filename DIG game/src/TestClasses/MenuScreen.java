package TestClasses;
//comment.
//2
import starter.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class MenuScreen extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton playButton;
	private GButton Controls;
	private GButton Quit;
	private GImage background;
	private boolean activateMouseRelease;
	private GLabel Title; 
	private int borderSize = 100;

	public MenuScreen(MainApplication app) throws FontFormatException, IOException {
		super();
		
		program = app;
		InputStream blockedFile3=new FileInputStream("Minecraftia.ttf");
		Font Options = Font.createFont(Font.TRUETYPE_FONT, blockedFile3);
		Options= Options.deriveFont(60.0f);
		blockedFile3.close();
		
		
		InputStream blockedFile4=new FileInputStream("Minecraftia.ttf");
		Font digTitle = Font.createFont(Font.TRUETYPE_FONT, blockedFile4);
		digTitle = digTitle.deriveFont(300.0f);
		blockedFile4.close();
		
		
		background=new GImage("minecraftbackground.jpg", -borderSize, -borderSize);
		background.setSize(program.getWidth() + borderSize*2, program.getHeight() + borderSize*2);
		playButton = new GButton("Play Game", 400, 300, 400, 100);
		playButton.setFillColor(Color.lightGray);
		Title=new GLabel("DIG", 320, 280);
		Title.setColor(Color.white);
		Title.setFont(digTitle);
		Controls=new GButton("Controls", 400, 400, 400, 100);
		Controls.setFillColor(Color.lightGray);
		Quit=new GButton("Quit", 400, 500, 400, 100);
		Quit.setFillColor(Color.lightGray);
		
		activateMouseRelease =false;
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(playButton);
		program.add(Title);
		program.add(Controls);
		program.add(Quit);

		activateMouseRelease =false;
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(playButton);
		program.remove(Title);
		program.remove(Controls);
		program.remove(Quit);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {

		activateMouseRelease = true;
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == playButton) {
			playButton.setFillColor(Color.darkGray);
			program.playSound("sounds", program.CLICKSOUND, false);
	
		}else if(obj == Controls) {
			Controls.setFillColor(Color.darkGray);
			program.playSound("sounds", program.CLICKSOUND, false);
		}else if (obj==Quit) {
			Quit.setFillColor(Color.darkGray);
			program.playSound("sounds", program.CLICKSOUND, false);
		}
	}
//
	@Override
	public void mouseReleased(MouseEvent e)
	{ 
		if(activateMouseRelease == false)
			return;
		
		GObject obj = program.getElementAt(e.getX(), e.getY());
		
		if (obj == playButton) {
			//program.switchTo("WorldScreen");
			program.switchTo("Tutorial");
		}else if(obj == Controls) {
			program.switchTo("ControlScreen");
	
		}else if (obj==Quit) {
			System.exit(0);
		}

		playButton.setFillColor(Color.lightGray);
		Controls.setFillColor(Color.lightGray);
		Quit.setFillColor(Color.lightGray);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		playButton.setFillColor(Color.lightGray);
		Controls.setFillColor(Color.lightGray);
		Quit.setFillColor(Color.lightGray);
		
	
		GObject obj= program.getElementAt(e.getX(), e.getY());
		if (obj==playButton) {
			playButton.setFillColor(Color.gray);
		}else if (obj==Controls) {
			Controls.setFillColor(Color.gray);
		}else if (obj==Quit) {
			Quit.setFillColor(Color.gray);
		}
		double xPos =(e.getX() - program.getWidth())*borderSize/program.getWidth()/2;
		double yPos =(e.getY() - program.getHeight())*borderSize/program.getHeight()/2;
	
		background.setLocation(xPos,yPos);
	}
	}///