import acm.program.ConsoleProgram;
//import bTree.bNode;
//import bTree.bNode;

/**
 * Implements a B-Tree class using a NON-RECURSIVE algorithm to objects of class aBall. 
 * This B-Tree class also organizes the balls in order while checking the status of each ball and if they're still running
 * @author Simo Benkirane and used generic B-Tree class/algorithm from Professor Ferrie
 *
 */

public class bTree extends ConsoleProgram  {
	
	// Instance variables
	
	bNode root=null;
	
	private double DELTASIZE = 0.1;
	public double X = 0;
	public double Y = 0;
	public double lastSize = 0;
	
	
	
	
/**
 * addNode method - adds a new node by descending to the leaf node
 *                  using a while loop in place of recursion.  Ugly,
 *                  yet easy to understand.
 */
	
	
	public void addNode(aBall iBall) { //add node method
		
		bNode current;
		
		

// Empty tree
		
		if (root == null) {
			root = makeNode(iBall);
		}
		
// If not empty, descend to the leaf node according to
// the input data.  
		
		else {
			current = root;
		
			
			while (true) {
				if ( iBall.getSize() < current.iBall.getSize() ) { //compares sizes
					
// New data < data at node, branch left
					
					if (current.left == null) {				// leaf node
						current.left = makeNode(iBall);		// attach new node here
						break;
					}
					else {									// otherwise
						current = current.left;				// keep traversing
					}
				}
				else {
// New data >= data at node, branch right
					
					if (current.right == null) {			// leaf node	
						current.right = makeNode(iBall);		// attach
						break;
					}
					else {									// otherwise 
						current = current.right;			// keep traversing
					}
				}
			}
		}
		
	}
	
/**
 * makeNode
 * 
 * Creates a single instance of a bNode
 * 
 * @param	int data   Data to be added
 * @return  bNode node Node created
 */
	
	bNode makeNode(aBall iBall) {
//		double X = iBall.getSize();
		bNode node = new bNode();							// create new object
		node.iBall = iBall;								// initialize data field
		node.left = null;									// set both successors
		node.right = null;									// to null
		return node;										// return handle to new object
	}
	
	
/**
 * stackBalls method - inorder traversal via call to recursive method
 */
	// hides recursion from user
	public void stackBalls() {	//stackballs that calls traverse_inorder and initiates inorder traversal 
		
		traverse_inorder(root);
	}
	
/**
 * traverse_inorder method - recursively traverses tree in order (LEFT-Root-RIGHT) and sets the position of the different balls in stacks.
 */
	
	private void traverse_inorder(bNode root) { //think of this as a loop/ inorder method
		if (root.left != null) traverse_inorder(root.left);
		
			if (root.iBall.getSize()- lastSize > DELTASIZE) { //if difference between current iball and last one is greater than DELTASIZE then creates a stack
			
				X += (lastSize + root.iBall.getSize()) ; //new stack 
				Y = root.iBall.getSize();
			}
				
				else {
				Y += lastSize + root.iBall.getSize();
				}
				lastSize = root.iBall.getSize();
				root.iBall.moveTo(X,Y);
				System.out.println(root.iBall.getSize());

		if (root.right != null) traverse_inorder(root.right);
	}
	
	
	
	public void stopAllBalls() {//stop for combobox
		stopBall (root);
	}
	
	public void stopBall (bNode root) { //traverses and interrupts the thread at every node or ball
		// stops ball at right node
		
		if (root.left != null) stopBall (root.left);
		// interrupts the thread which stops the ball
		root.iBall.interrupt();
		// stops ball at left node
		if (root.right != null) stopBall (root.right);
	}
	
	
	
	
	/**
	 * A simple bNode class for use by bTree.  
	 * Modified accordingly to support aBalls.
	 * 
	 * @author Simo Benkirane and inspired by ferrie
	 *
	 */
	class bNode { //bNode class
		aBall iBall;
		bNode left;
		bNode right;
	}
	}
	











