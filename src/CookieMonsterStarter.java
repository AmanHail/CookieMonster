import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;



public class CookieMonsterStarter
{

  private final int SIZE = 12; //Can be altered for different files. 
  private int[][] cookies = new int[SIZE][SIZE];

    
  /**
   *  Reads cookies from file
   */
  private void loadCookies(Scanner input)
  {  
    for (int row = 0;   row < SIZE;   row++)  
      for (int col = 0;   col < SIZE;   col++)  
        cookies[row][col] = input.nextInt();  
  }  

  /**
   *  Returns true if (row, col) is within the array and that position is
   *  not a barrel (-1); false otherwise.  Notice short-circuit evaluation
   *  to protect out-of-bounds errors from occuring.
   */
  private boolean goodPoint(int row, int col)  
  {  
    return row >= 0 && row<SIZE && col>= 0 && col<SIZE && cookies[row][col]>=0;  
  }

  /**
   *  Returns the largest number of cookies collected by Monster
   *  on a path from (0,0) to (SIZE-1, SIZE-1)
   */
  private int optimalPath()  
  {  
	  //LOTS OF CODE HERE!
	  
	  boolean done = false;
	  Location pos = new Location(0, 0); //current pos is top left
	  int cooks = cookies[0][0];
	  Stack<Fork> forks = new Stack<Fork>();
	  Queue<Location> path = new LinkedList<Location>();
	  Queue<Location> bestPath = new LinkedList<Location>();
	  int bestCookies = 0;
	  
	  if (!goodPoint(0, 0)) {
		  done = true;
	  }
	  path.add(new Location(pos));
	  
	  while(!done) {
	  //if can move both right and down (fork)
		  if (goodPoint(pos.getRow()+1, pos.getCol()) && goodPoint(pos.getRow(), pos.getCol()+1)) {
			  forks.add(new Fork(cooks, new Location(pos), copy(path))); //create a fork and add it to the stack
			  pos.moveDown(); //proceed down //update position
			  cooks+=cookies[pos.getRow()][pos.getCol()]; //update cookies
			  path.add(new Location(pos)); //update path
		  }
	  //else if can only move down
		  else if(goodPoint(pos.getRow()+1, pos.getCol())) {
			  pos.moveDown(); //proceed down //update position
			  cooks+=cookies[pos.getRow()][pos.getCol()]; //update cookies
			  path.add(new Location(pos)); //update path
		  }
		  
		  //else if can only move right
		  else if (goodPoint(pos.getRow(), pos.getCol()+1)) {
			  pos.moveRight();//proceed right  //update position
			  cooks+=cookies[pos.getRow()][pos.getCol()]; //update cookies
			  path.add(new Location(pos)); //update path
		  }
		  
		 //else dead end
		  else {
			  
			  if (pos.getRow() == SIZE-1 && pos.getCol() == SIZE -1) {	//are we at the end
				//see if its the best
				  System.out.println("Total: " + cooks + " Path: " + path);
			  	//(store it? output it? place in a file)
				  if (cooks > bestCookies) {
					  bestCookies = cooks;
					  bestPath = path;
					  
				  }
			  }
			  //Check stack. If empty, done (no other paths to visit)
			  if (forks.isEmpty()) {
				  done = true;
			  }

			  //If fork exists
			  else {
				  //update fields: position, path, cookies
				  Fork temp = forks.pop();
				  cooks = temp.getCookies();
				  pos = temp.getPosition();
				  path = temp.getPath();
				  //move right; update field
				  pos.moveRight();//proceed right  //update position
				  cooks+=cookies[pos.getRow()][pos.getCol()]; //update cookies
				  path.add(new Location(pos)); //update path
				  
			  }
		  }
	  }
	  
	  System.out.println(bestPath);
	  return bestCookies;
  }
  
  
  
  /**  The following is something we coded together in Ch20 work:
  *		E  is an Element Type
  * 	It is a Static method:  to activate it...
  *      in another class:  someotherQ= CoookieMonster.copy(someq);
  *      in this class:   		 newQ = copy(q);
  *      */
  public static Queue<Location> copy(Queue<Location> q){
	  
	  Queue<Location> q2 = new LinkedList<Location>();
	  
	  if (!q.isEmpty()){
		  
		   Location obj = q.remove();
		   Location first = obj;
		   q2.add(obj);
		   q.add(obj);
		   
		   while (q.peek() != first) {
			   obj = q.remove();
			   q.add(obj);
			   q2.add(obj);
		   }  
	  }
	  
	  return q2;
  }
  
  /**BONUS #2
   * Recursive solution for obtaining optimal number of cookies. Additional 
   * parameters might need to be included to track paths, or it may be fruitful
   * to create some private fields that are used within this method as required.
   * For example, we could maintain fields for bestCookieCount and bestPathFound.
   * @param row  - the destination row	  (Start is always (0,0).)
   * @param col  - the destination column
   * @return number of cookies on optimal path to (row,col) from (0,0).
   */
  private int recOptimalPath(int row, int col)
	{
		int count = 0;

		//Code here.	
		
		return count;
	}
  

  public static void main(String args[])
  {  // Adapt this as you see fit.
    String fileName;

    if (args.length >= 1)
    {
      fileName = args[0];
    }
    else
    {
      Scanner kboard = new Scanner(System.in);
      System.out.print("Enter the cookies file name: ");
      fileName = kboard.nextLine();
     }

    File file = new File(fileName);
    Scanner input = null;
    try
    {
      input = new Scanner(file);
    }
    catch (FileNotFoundException ex)
    {
      System.out.println("Cannot open " + fileName);
      System.exit(1);
    }

    CookieMonsterStarter monster = new CookieMonsterStarter();
   
    monster.loadCookies(input);
    System.out.println("Optimal path has " +
                                  monster.optimalPath() + " cookies.\n");
  }
}
