package Worlds;

public class Vector2 {
	private int x;
	private int y;
	
	public void setX(int v) {
		x = v;
	}
	
	public void setY(int v) {
		y = v;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "" + x + "," + y;
	}
	
	
}
