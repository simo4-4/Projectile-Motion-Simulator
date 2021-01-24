// By Simo Benkirane
// McGill Id : 260904777
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.*;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.gui.DoubleField;
import acm.gui.IntField;
import acm.program.*;
import acm.util.RandomGenerator;

/**
 * bSim Class that helps generate random balls according to a given seed while generating an instance of a bTree class and 
 * putting the generate balls in a node using a method from the bTree class
 * This also creates a GUI and allows the user to manipular different aspects of the program like bTree Stack, amount of balls, ballsize ect.. as seen in the GUI
 * @author Simohamed Benkirane, McGill Id: 260904777
 *
 */
public class bSim extends GraphicsProgram implements ItemListener,ActionListener {

	//declaring variables
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double SCALE = HEIGHT/100; 
	//public static final int NUMBALLS = 60;
	//private static final double MINSIZE = 1.0;
	//private static final double MAXSIZE = 7.0;
	
	private int i = 0;
	private double Xi = 95.0;
	private double Yi = 1.0;
	private boolean simEnable;
	public volatile static boolean isTrace;
	
	bTree myTree1 = new bTree(); //new btree instance
	
	//initializes GUI elements
	private JButton traceButton;
	JComboBox<String> combo;
	
	
	
	private JLabel sim;
	
	private JPanel Numballs;
	private JLabel balls;
	private IntField ball;
	
	private JPanel Minsize1;
	private JLabel Minsize2;
	private DoubleField Minsize3;
	
	private JPanel Maxsize1;
	private JLabel Maxsize2;
	private DoubleField Maxsize3;
	
	private JPanel Minl1;
	private JLabel Minl2;
	private DoubleField Minl3;
	
	private JPanel Maxl1;
	private JLabel Maxl2;
	private DoubleField Maxl3;
	
	private JPanel Minv1;
	private JLabel Minv2;
	private DoubleField Minv3;
	
	private JPanel Maxv1;
	private JLabel Maxv2;
	private DoubleField Maxv3;
	
	private JPanel Minth1;
	private JLabel Minth2;
	private DoubleField Minth3;
	
	private JPanel Maxth1;
	private JLabel Maxth2;
	private DoubleField Maxth3;
	
	
	
	
	
	public void itemStateChanged(ItemEvent e) { //Gives function to each element in combobox
		JComboBox source = (JComboBox)e.getSource();
		if (source==combo) { 
			
			if (combo.getSelectedIndex()==0) {
				System.out.println("bSim selected");
				
				
			}
			
			if (combo.getSelectedIndex()==1  ) {
				System.out.println("Starting simulation");

				simEnable = true;
			}
			
			
			if (combo.getSelectedIndex()==2) { 
				System.out.println("Histogramming balls"); 
				myTree1.stopAllBalls(); //calls stopAllBalls method so that the balls dont keep on moving and are kept on a stack
				
				doStack(); //calls do Stack method in bSim
				isTrace = false;
				
				
				
				
		}
			if (combo.getSelectedIndex() == 3 ) {
				System.out.println("Cleared");
				
				clear(); //calls clear method in bSim
				makeFloor(); //generates floor
				
			}
			
			if (combo.getSelectedIndex() == 4 ) {
				System.out.println("Stopped");
				
				myTree1.stopAllBalls(); //calls stopAllBalls method in bTree to interrupt thread at each ball
				
				
				
			}
			
			if (combo.getSelectedIndex() == 5 ) {
				System.exit(1); //exits Java simulation
				
				
			}
		
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Trace"))
		{
			GLabel td = new GLabel("Trace On! ");  //creates new glabel 
			td.setColor(Color.RED); //sets the color
			add(td,880,18);	
			isTrace = true;

		}
		
	}
	
	
	public void doSim() { //method to generate balls
		
		
		
	
		for (i = 0; i < ball.getValue(); i++) { //loop to generate exactly NUMBALLS  or 100 balls in this case
			
			
			
		System.out.println(i); //print i to check which ball it is
		
		double iSize = rgen.nextDouble(Minsize3.getValue(),Maxsize3.getValue());  //returns random value between set boundaries
        Color iColor = rgen.nextColor();  //returns random color 
        double iLoss = rgen.nextDouble(Minl3.getValue(),Maxl3.getValue()); 
        double iVel = rgen.nextDouble(Minv3.getValue(),Maxv3.getValue());   
        double iTheta = rgen.nextDouble(Minth3.getValue(),Maxth3.getValue());
        
        double Size = 1;
        
        
       
		aBall iBall = new aBall((Xi * SCALE),HEIGHT-(Yi-Size)*SCALE,iVel,iTheta,iSize,iColor,iLoss, this); //put random values and colors and generates a ball
		
		add(iBall.getBall()); //getBall method reads the value of myBall
		iBall.start(); //.start is a method in the Thread class that  calls the run method
		myTree1.addNode(iBall); //adds each ball as a node
		
		
		}
		
		
	
			
	}
	
	public void doStack() { //dostack method which calls stackball method in bTree using the new created bTree1
		
		
		myTree1.X = 0; //resets x
		myTree1.Y = 0; //resets y
		myTree1.lastSize = 0; //resets lastsize
		
		GLabel click = new GLabel("CR to Continue");  //creates new glabel 
		click.setColor(Color.RED); //sets the color
		add(click,880,32); //adds to a specific x and y position
		
		myTree1.stackBalls(); //applies stackBalls method to myTree
			
		click.setLabel("All Stacked!"); //prints message
			
			
		
	}
	
	public void clear() { //clears everything and resets bTree
		
		removeAll(); //removes everything on screen, this method is present in Java already 
		isTrace = false; //turns off trace 
		myTree1 = new bTree(); //creates new instance of btree with overrides and deletes the old one
	}
	
	
	
	public void makeFloor () { //method to make floor
		GRect myLine = new GRect(0, HEIGHT, WIDTH, 3); // Sets up horizontal line
		myLine.setFilled(true); //filled rectangle
		add(myLine); // actually adds it to the screen
		
	}
	
	public void run() { //used a run method so that combobox doesn't run twice everytime
		simEnable = false;
		
		while (true) {
			pause (200);
		
		if(simEnable) {
			doSim();
			combo.setSelectedIndex(0);
			simEnable=false;
		}
		}
	}
	
	public void init()
	{
		
		
		
		this.resize(WIDTH, HEIGHT + OFFSET); //resizes windows according to the values
		makeFloor(); //makes floor
		
		rgen.setSeed( (long) 424242 ); //to get fixed 'random' balls
		//Sets up GUI Swing elements
		
		combo = new JComboBox<String>(); //combobox
		combo.addItem("bSim");
		combo.addItem("Run");
		combo.addItem("Stack");
		combo.addItem("Clear");
		combo.addItem("Stop");
		combo.addItem("Quit");
		
		add(combo,NORTH);
		
		 
		
		
		traceButton = new JButton("Trace"); //trace button
		add(traceButton, SOUTH);
		
		sim = new JLabel("General Simulation Parameters");
		add(sim,EAST);
		
		
		Numballs = new JPanel();
		balls = new JLabel("NUMBALLS(1-255)");
		ball = new IntField(60, 1, 255);
		Numballs.add(balls);
		Numballs.add(ball);
		add(Numballs,EAST);
		
		
		Minsize1 = new JPanel();
		Minsize2 = new JLabel("MINSIZE(1.0-25.0)");
		Minsize3 = new DoubleField(1.0,1.0,25.0);
		Minsize1.add(Minsize2);
		Minsize1.add(Minsize3);
		add(Minsize1,EAST);
		
		Maxsize1 = new JPanel();
		Maxsize2 = new JLabel("MAXSIZE(1.0-25.0)");
		Maxsize3 = new DoubleField(7.0,1.0,25.0);
		Maxsize1.add(Maxsize2);
		Maxsize1.add(Maxsize3);
		add(Maxsize1,EAST);
		
		Minl1 = new JPanel();
		Minl2 = new JLabel("MIN LOSS(0.0-1.0)");
		Minl3 = new DoubleField(0.2,0.0,1.0);
		Minl1.add(Minl2);
		Minl1.add(Minl3);
		add(Minl1,EAST);
		
		Maxl1 = new JPanel();
		Maxl2 = new JLabel("MAX LOSS(0.0-1.0)");
		Maxl3 = new DoubleField(0.6,0.0,1.0);
		Maxl1.add(Maxl2);
		Maxl1.add(Maxl3);
		add(Maxl1,EAST);
		
		Minv1 = new JPanel();
		Minv2 = new JLabel("MIN VELOCITY(1.0-200.0)");
		Minv3 = new DoubleField(40.0,1.0,200.0);
		Minv1.add(Minv2);
		Minv1.add(Minv3);
		add(Minv1,EAST);
		
		Maxv1 = new JPanel();
		Maxv2 = new JLabel("MAX VELOCITY(1.0-200.0)");
		Maxv3 = new DoubleField(50.0,1.0,200.0);
		Maxv1.add(Maxv2);
		Maxv1.add(Maxv3);
		add(Maxv1,EAST);
		
		Minth1 = new JPanel();
		Minth2 = new JLabel("MIN THETA(1.0-180.0)");
		Minth3 = new DoubleField(80.0,1.0,180.0);
		Minth1.add(Minth2);
		Minth1.add(Minth3);
		add(Minth1,EAST);
		
		Maxth1 = new JPanel();
		Maxth2 = new JLabel("MAX THETA(1.0-180.0)");
		Maxth3 = new DoubleField(100.0,1.0,180.0);
		Maxth1.add(Maxth2);
		Maxth1.add(Maxth3);
		add(Maxth1,EAST);
		
		combo.addItemListener(this); //used for combobox 
		
		
		addMouseListeners();
		addActionListeners(); //used for trace button to know if it was clicked
		
	}
	
	
	

	
			
		
	private RandomGenerator rgen = RandomGenerator.getInstance(); //initializing random generator
		
	
		
		
		
		
		
	}
