import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import starter.GButton;
import starter.GraphicsPane;
import starter.MainApplication;

//james
public class PauseMenu extends GraphicsPane{

	private MainApplication program;
	
	private static final double SAVE_LOC_X = 50;
	private static final double SAVE_LOC_Y = 50;
	private static final int SAVE_HEIGHT = 50;
	private static final int SAVE_WIDTH = 200;
	private static final double CONTROLS_LOC_X = 50;
	private static final double CONTROLS_LOC_Y = 50;
	private static final int CONTROLS_HEIGHT = 50;
	private static final int CONTROLS_WIDTH = 200;
	private static final double EXIT_LOC_X = 50;
	private static final double EXIT_LOC_Y = 50;
	private static final int EXIT_HEIGHT = 50;
	private static final int EXIT_WIDTH = 200;
	
	
	private GButton saveAndExit;
	private GButton controls;
	private GButton exitScreen;
	
	public PauseMenu(MainApplication app) {
		program = app;
		saveAndExit = new GButton("Save and Exit", SAVE_LOC_X, SAVE_LOC_Y, SAVE_WIDTH, SAVE_HEIGHT);
		controls = new GButton("Controls", CONTROLS_LOC_X, CONTROLS_LOC_Y, CONTROLS_WIDTH, CONTROLS_HEIGHT);
		exitScreen = new GButton("Back to Game", EXIT_LOC_X, EXIT_LOC_Y, EXIT_WIDTH, EXIT_HEIGHT);
		
	}
	
	@Override
	public void showContents() {
		program.add(saveAndExit);
		program.add(controls);
		program.add(exitScreen);
	}
	@Override
	public void hideContents() {
		program.remove(saveAndExit);
		program.remove(controls);
		program.remove(exitScreen);
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		//TODO: implement what happens when each button is pressed
		
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == saveAndExit) {
			program.switchToMenu();
		}
	}
}
