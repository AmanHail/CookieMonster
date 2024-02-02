
public class Location {

	private int row;
	private int col;

	public Location(int r, int c) {
		row = r;
		col = c;
	}
	public Location(Location other) {
		row = other.getRow();
		col = other.getCol();
	}

	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	public void moveDown() {
		row++;
	}
	public void moveRight() {
		col++;
	}
	
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}
