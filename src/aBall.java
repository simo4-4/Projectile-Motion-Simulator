// By Simo Benkirane
// McGill Id : 260904777

/**
 * 
 */

/** locl **/

/**
 * 
 */

import java.awt.Color;


import acm.graphics.*;
import acm.program.*;


/**
 * aBall class that uses Newtonian mechanics to create a bouncing that takes into account air resistance, drag, gravity
 * @author Simo Benkirane, McGill Id: 260904777
 *
 */


public class aBall extends Thread{
	
	private double Xi;
	private double Yi;
	private double Vo;
	private double theta;
	private double bSize;
	private double bLoss; 
	Color ballColor;
	private double SCALE = 6;
	private static final double Pi = 3.14;
	private static final double g = 9.8;
	private static final double k = 0.0001;
	private static final double TICK = 0.1;
	private static final double ETHR = 0.01;
	private GOval myBall;
	bSim link;
	double ScrY  ; 
	double ScrX ;;
	

	public aBall (double Xi, double Yi, double Vo, double theta, double bSize, Color bColor, double bLoss, bSim link) { //constructor
		this.Xi = Xi; // Get simulation parameters
		this.Yi = Yi ;  //this. = referring to the object you have
		this.Vo = Vo; 
		this.theta = theta; 
		this.bSize = bSize; 
		ballColor = bColor; 
		this.bLoss = bLoss;
		this.link = link;
		
		
		
		
		
		
		
		myBall = new GOval(Xi, Yi - (2 * bSize * SCALE), 2 * SCALE * bSize, 2 * SCALE * bSize); //uses GOval to contruct aBall 
		myBall.setFilled(true); 
		myBall.setFillColor(bColor);
		
		
	}
	
	public GOval getBall() { //getter to be able to access it from outside the class
		return myBall;
	}
	
	public double getSize() {
		return bSize;
	}
	
	
	
	

	public void moveTo(double x, double y) {
		x = (int) (( x - bSize) * SCALE);
		y = (int) (600 - (y + bSize ) * SCALE);;
		myBall.setLocation(x, y);
	}	
	
	public void trace() { //trace method inspired by Prof Ferrie
		
		GOval pt = new GOval(ScrX + bSize * SCALE, Math.min(ScrY, 600 - bSize * SCALE),1,1);
		pt.setFilled(true);
		pt.setColor(ballColor);
		
		System.out.println("ScrX: "+ ScrX+" ScrY: "+ScrY);
		
		link.add(pt);
	}
	
		
	public void run() { //main method
		

		
		
		
		
		double Vt = g / (4 * Pi * bSize * bSize * k); // Terminal Velocity
		double Vox = Vo * Math.cos(theta * Pi / 180.0); // X component of initial velocity
		double Voy = Vo * Math.sin(theta * Pi / 180.0); // Y component of initial velocity
		double Ylast = 0;
		double time = 0;
		double Vy = 0;
		 
		double Y = Yi  ; 
		double Vx = 0;
		double KEy = 0.5 * Voy * Voy;
		double X = 0;
		
		double KEx = 0.5 * Vox * Vox;
		
		double Xlast = 0;
		double Xo = 0;									
		double tEnergy = KEx + KEy;
		double tLast = tEnergy;
		

		while (true) { //only runs if condition is true and ends thread
			
			tLast = tEnergy; //last kinetic total energy
			tEnergy = KEy + KEx;  //total  kinetic energy 
			
			

			X = Vox * Vt / g * (1 - Math.exp(-g * time / Vt));
			
			Y =  (bSize + Vt / g * (Voy + Vt) * (1 - Math.exp(-g * time / Vt)) - Vt * time) ;
			Vx = (X - Xlast) / TICK; // horizontal velocity
			Vy = (Y - Ylast) / TICK; // vertical velocity
			
			
      
				Xlast = X; //saves previous x
				Ylast = Y; //saves previous y


			if ((Y  <= bSize)  && (Vy < 0)) {  // bounce 
				//if (true) 
					
				KEx = 0.5 * Vx * Vx * (1.0 - bLoss); // Kinetic energy in X direction after collision
				KEy = 0.5 * Vy * Vy * (1.0 - bLoss); // Kinetic energy in Y direction after collision
				
				if (theta <= 90) {
				Vox = Math.sqrt(2.0 * KEx);// Resulting horizontal velocity
				
				}
				else {
					Vox = -Math.sqrt(2.0 * KEx);
				
				}
				Voy = Math.sqrt(2.0 * KEy); // Resulting vertical velocity

				time = 0; //resets time
				Y = bSize; //sets ball on ground
				Xo += X; //add previous displacement
				X = 0; //resets X
				Xlast = X; //saves previous X
				Ylast = Y; // saves previous Y

			
			}
			ScrX = (int) ( Xi + bSize*2 + (Xo + X - bSize) * SCALE); //adds displacement to previous X
			ScrY = (int) (600 - (Y + bSize ) * SCALE);
			myBall.setLocation(ScrX, ScrY); // Screen units
			time += 0.1;
			
			if(bSim.isTrace) {
				trace();
			}
			
			try {
				// pause for 40 milliseconds
				Thread.sleep(40);
				}
			
			
			
			catch (InterruptedException e) { //allows me to stop balls
				
				
				
				try {
					
					Thread.currentThread().wait(); //used with stopAllBalls method in bTree
				} catch (InterruptedException ee) {
					ee.printStackTrace();
				}
			}
			
			
			if (tEnergy < ETHR || tEnergy > tLast) break; //breaks according to condition

			

		}
		
	}

	

	

	
}
