package TheHelpers;

public class Point {
	private int x;
	private int y;
	
	public Point(int nx, int ny) {
		x = nx;
		y = ny;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String toString() {		
		return x + "," + y;
	}
}
