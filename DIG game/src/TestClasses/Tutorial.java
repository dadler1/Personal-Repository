package TestClasses;

import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import starter.*;

public class Tutorial extends GraphicsPane{
	private MainApplication program;
	
	public static final int BUTTON_WIDTH = 100;
	public static final int BUTTON_HEIGHT = 50;
	
	private GParagraph para;
	private GButton next;
	private GButton back;
	private GButton skip;
	private GObject obj;
	private int count = 0;
	
	public Tutorial(MainApplication app) {
		this.program = app;
		next = new GButton("next", program.WINDOW_WIDTH - 2 * BUTTON_WIDTH, program.WINDOW_HEIGHT - 2 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
		skip = new GButton("To World", program.WINDOW_WIDTH / 2 - BUTTON_WIDTH / 2.1, program.WINDOW_HEIGHT - 2 * BUTTON_HEIGHT, 1.5 * BUTTON_WIDTH, BUTTON_HEIGHT);
		back = new GButton("back", BUTTON_WIDTH, program.WINDOW_HEIGHT - 2 * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	public void page1() {
		String paraWords = "Welcome to DIG, a 2D creative sandblox explorer. \n"
				+ "This is a quick tutorial to give you the basic skills \n"
				+ "you will need to play this game";
		para = new GParagraph(paraWords, 25, program.WINDOW_HEIGHT / 2.3);
		para.setFont("Arail-bold-" + program.WINDOW_HEIGHT / 17);
		program.add(para);
	}
	public void page2() {
		String paraWords = "The basics: \n"
				+ "You can move left and right using the A & D keys respectivley\n"
				+ "You can jump when touching any surface by pressing spacebar\n"
				+ "You can destroy any block on your screen by holding L-click\n"
				+ "You can place blocks by pressing R-click\n"
				+ "You can open the crafting menu by pressing q\n"
				+ "And can open your inventory by pressing e";
		para = new GParagraph(paraWords, 25, program.WINDOW_HEIGHT / 4);
		para.setFont("Arail-bold-" + program.WINDOW_HEIGHT / 19);
		program.add(para);
	}
	public void page3() {
		String paraWords = "Tips: \n"
				+ "You can climb walls by holding spacebar while standing next to them\n"
				+ "Opening your Inventory pauses the game\n"
				+ "If you forget the contorls at anytime you can check the contorls tab\n"
				+ "To Craft, you simply click the desired block and press craft,\n"
				+ "if you have the required materials it will be added to your inv.\n"
				+ "While in the inventory, you can organize your items however you want.\n"
				+ "You can carry 64 of one item in each slot";
		para = new GParagraph(paraWords, 25, program.WINDOW_HEIGHT / 4);
		para.setFont("Arail-bold-" + program.WINDOW_HEIGHT / 23);
		program.add(para);
	}
	public void page4() {
		String paraWords = "Goals: \n"
				+ "There is no real goal to this game, just explore and build \n"
				+ "cool structures. Press the 'To World' button to enter your \n"
				+ "world. Have Fun!";
		para = new GParagraph(paraWords, 25, program.WINDOW_HEIGHT / 2.5);
		para.setFont("Arail-bold-" + program.WINDOW_HEIGHT / 20);
		program.add(para);
	}

	@Override
	public void showContents() {
		page1();
		program.add(next);
		program.add(skip);
		program.add(back);
	}

	@Override
	public void hideContents() {
		program.remove(para);
		program.remove(next);
		program.remove(skip);
		program.remove(back);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(obj == skip) {
			program.switchTo("WorldScreen");
		}
		if(obj == next) {
			count = count + 1;
			if(count == 1) {
				program.remove(para);
				page2();
			}
			else if(count == 2) {
				program.remove(para);
				page3();
			}
			else if(count == 3) {
				program.remove(para);
				page4();
			}
				
		}
		if(obj == back && count > 0) {
			count = count - 1;
			program.remove(para);
			if(count == 0) {
				page1();
			}
			if(count == 1) {
				page2();
			}
			if(count == 2) {
				page3();
			}
		}
	}
	
}
