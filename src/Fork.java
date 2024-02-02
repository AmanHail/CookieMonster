import java.util.Queue;

public class Fork {

	private int cookies;
	private Location position;
	private Queue<Location> path;
	
	public Fork(int c, Location loc, Queue<Location> p) {
		cookies = c;
		position = loc;
		path = CookieMonsterStarter.copy(p);
	}
	
	//getLast location
	
	
	public int getCookies() {
		return cookies;
	}
	public Location getPosition() {
		return position;
	}
	public Queue<Location> getPath() {
		return path;
	}
	
	public String toString() {
		return "Taking the path " + path + " to the location " + position + ", there are " + cookies + " cookies";
	}
}
