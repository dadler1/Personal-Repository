package Worlds;

public class Vector2D {
	private double x;
	private double y;
	
	public void setX(double v) {
		x = v;
	}
	
	public void setY(double v) {
		y = v;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Vector2D(double x, double d) {
		this.x = x;
		this.y = d;
	}
	
	@Override
	public String toString() {
		return "" + x + ", " + y;
	}
	
	
}
