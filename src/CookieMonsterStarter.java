import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * HONOR PLEDGE: All work here is honestly obtained and is my own.  Signed:  Aman Haileyesus
 * @author haileyesusa
 * Date of Completion:  4/5/2023
 * Assignment:  Cookie Monster Lab
 * 
 * Attribution: Leah Dorrien
 * 
 * General Description: Takes a 2d array of integers finds the optimal path from 
 *  the upper left corner (0,0) to the lower right corner
 *  (SIZE-1,SIZE-1) in a cookie array, avoiding any negative numbers.
 *  Prints all possible paths with the total number of cookies as well as the best path/cookies
 * 
 * Advanced:  I implemented a moveDown and moveRight method in my Location object in
 *  		  order to only use 1 position variable. To add locations to the paths, I created 
 *  		  a copy constructor in the Location object as well
 * 
 * Errata:  
 *
 */

/**
 *  In this program Cookie Monster finds the optimal path from 
 *  the upper left corner (0,0) to the lower right corner
 *  (SIZE-1,SIZE-1) in a cookie array.  The elements of
 *  the array contain cookies (a non-negative number) or barrels
 *  (-1).  On each step Cookie Monster can only go down or
 *  to the right.  He is not allowed to step on barrels. 
 *  The optimal path contains the largest number of cookies.
 *   
 *  The program prompts the user for a file name,
 *  reads the cookie array from the file, and reports the
 *  number of cookies on the optimal path. Assumed size of the
 *  grid of values i 12 x 12, stored in row-major order.
 *  
 *  Bonus:  Adapt the program to read 2 ints from the file first
 *  representing numRows and numCols, and then read all values into
 *  a 2-d array.  (Consult FloodFill project for an example.)
 *  
 *  Bonus#2: Write a recursive solution that finds the optimal cookie
 *  total for the problem.   (For mega bonus, write a recursive solution
 *  that reports the Optimal Path formed with that total.)
 *  
 *  The program also reports the actual optimal path, location
 *  by location in effective formatting.
 *  
 *  Finally, the program will output every successful path found,
 *  along with the total cookies along that path.
 */

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