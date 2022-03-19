package Crafting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Worlds.Vector2;
import acm.graphics.GCompound;
import acm.graphics.GContainer;
import acm.graphics.GRect;
import starter.GParagraph;
import starter.Interfaceable;

public class ScrollPane extends GCompound implements Interfaceable {
	//BACKEND COMPONENTS
	private Recipe[] availableRecipes;
	//GRAPHICAL COMPONENTS
	private GRect frameRect;
	private GRect scrollBarBackground;
	private GRect scrollBar;
	private GCompound contents;
	
	private GRect overflow_up;
	private GRect overflow_down;
	
	private Vector2 scrollDimensions;
	private boolean draggingScrollBar = false;
	private int starty = 0;
	private int x = 0;
	private int y = 0;
	private int width;
	private int height;
	
	
	public void refresh() {
		//
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.clipRect(x,y, width + (int) scrollBar.getWidth(), height);
		super.paint(g2);
	}
	

	
	
	//will update the scrollbar size based on the contents
	public void update() {
		int contentsHeight = (int) contents.getHeight();
		System.out.println(scrollBarBackground.getHeight());
		double size = (scrollBarBackground.getHeight()/contents.getHeight())*frameRect.getHeight();
		
		if (size > scrollBarBackground.getHeight()) {
			size = scrollBarBackground.getHeight();
		}
		scrollBar.setSize(scrollBar.getWidth(),size);
	}
	
	public void setContents(GCompound compound) {
		remove(contents);
		remove(scrollBar);
		remove(scrollBarBackground);
		this.contents = compound; 
		this.contents.setLocation(frameRect.getX(),frameRect.getY());
		add(contents);
		add(scrollBarBackground);
		add(scrollBar);
		update();
	}
	
	public GCompound getContents() {
		return contents;
	}
	
	public ScrollPane(int x,int y,int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		frameRect = new GRect(x,y,width, height);
		frameRect.setFilled(true);
		frameRect.setColor(new Color(200,200,200));
		
		overflow_up = new GRect(x,0,0,0);
		overflow_up.setColor(Color.red);
		overflow_up.setFilled(true);
		
		
		scrollBarBackground = new GRect(x + frameRect.getWidth() - (frameRect.getWidth()*0.05),y,frameRect.getWidth()*.05,frameRect.getHeight());
		scrollBarBackground.setColor(new Color(155,155,155,155));
		scrollBarBackground.setFilled(true);
		
		scrollDimensions = new Vector2(width,(int) (height*1.25));
		
		scrollBar = new GRect(scrollBarBackground.getX(),y,scrollBarBackground.getWidth(),scrollBarBackground.getHeight()/2);
		scrollBar.setColor(new Color(225,225,225));
		scrollBar.setFillColor(Color.white);
		scrollBar.setFilled(true);
		
		//Default contents
		contents = new GCompound();
		
		
		
		GRect rect1 = new GRect(0,0,frameRect.getWidth()*.95,frameRect.getHeight()/4);
		rect1.setFilled(true);
		rect1.setColor(new Color(100,255,100));
		contents.add(rect1);
		
		
		GParagraph para = new GParagraph("This is a default Scroll Pane", 0, 0);
		para.setFont("Arial-Bold-36");
		para.setColor(Color.white);
		para.setLocation(rect1.getX() + rect1.getWidth()/2 - para.getWidth()/2, rect1.getY() + rect1.getHeight()/2);
		
		
		
	
		contents.add(para);
		
		
		GRect rect2 = new GRect(0,rect1.getHeight(),frameRect.getWidth()*.95,frameRect.getHeight());
		rect2.setFilled(true);
		rect2.setColor(new Color(222,222,222));
		contents.add(rect2);
		
		GRect rect3 = new GRect(0,rect2.getHeight() + rect2.getY(),frameRect.getWidth()*.95,50);
		rect3.setFilled(true);
		rect3.setColor(new Color(100,100,255));
		contents.add(rect3);
		
		GParagraph para2 = new GParagraph("This is the bottom. Nothing to see here", 0, 0);
		para2.setFont("Arial-Bold-24");
		para2.setColor(Color.white);
		para2.setLocation(rect3.getX() + rect3.getWidth()/2 - para2.getWidth()/2, rect3.getY() + rect3.getHeight()/2 + para2.getHeight()/4);
		contents.add(para2);
		contents.setLocation(x,y);
		
		update();
		
		add(frameRect);
		add(scrollBarBackground);
		add(scrollBar);
		add(contents);
		add(overflow_up);
	}
	
	public void setRecipeList(CraftCategory category) {
		this.availableRecipes = category.getAllRecipesFromCategory();
		generateRecipes(category);
	}
	
	private void generateRecipes(CraftCategory category) {
		
	}


	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public void setLocation(int x,int y) {
		super.setLocation(x, y);
	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(this.getElementAt(e.getX(), e.getY()));
		if (e.getX() < frameRect.getX() + frameRect.getWidth() && e.getX() > scrollBarBackground.getX()) {
			if (e.getY() < frameRect.getY() + frameRect.getHeight() && e.getY() > scrollBarBackground.getY()) {
				draggingScrollBar = true;
				starty = (int) (e.getY() - scrollBar.getY());
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		draggingScrollBar = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (draggingScrollBar) {
			
			//Get the position on the scrollBar
			int locY = (int) (e.getY() - scrollBar.getY());
			
			
			//If i press at y = 10, then the bar is locatated at my position - 10
			
			scrollBar.setLocation(scrollBar.getX(),  e.getY() - starty);
			if (scrollBar.getY() > scrollBarBackground.getHeight() + scrollBarBackground.getY() - scrollBar.getHeight()) {
				scrollBar.setLocation(scrollBar.getX(),scrollBarBackground.getHeight() + scrollBarBackground.getY() - scrollBar.getHeight());
			}
			
			if (scrollBar.getY() < scrollBarBackground.getY()) {
				scrollBar.setLocation(scrollBar.getX(), scrollBarBackground.getY());
			}
			
			int min = (int) scrollBarBackground.getY();
			int max = (int) (scrollBarBackground.getHeight() - scrollBar.getHeight());
			//System.out.println((-scrollBar.getY() -scrollBarBackground.getY()) + " " + max);
			//double rate = ((scrollBar.getY() - min)/max)/2;
			double rate = ((scrollBar.getY() - min)/(max));
			
			//System.out.println(scrollBar.getY() + " " + min + " " + rate);
			contents.setLocation(x, y - (rate*contents.getHeight() - (rate*scrollBarBackground.getHeight())));// -(scrollBar.getY()));
			if (scrollBarBackground.getHeight() == scrollBar.getHeight()) {
				contents.setLocation(x,y);
			}
			//contents.setLocation(x,2*y - scrollBar.getY());// -(scrollBar.getY()));
			//starty = ; 
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
			
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	public double getScrollbarWidth() {
		// TODO Auto-generated method stub
		return scrollBar.getWidth();
	}
}
