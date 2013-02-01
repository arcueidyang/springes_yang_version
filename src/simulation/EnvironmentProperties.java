package simulation;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.Vector;

public class EnvironmentProperties {
	private static final String GRAVITY_KEYWORD = "gravity";
	private static final String VISCOSITY_KEYWORD = "viscosity";
	private static final String CENTEROFMASS_KEYWORD ="centerofmass";
	private static final String WALLREPULSION_KEYWORD = "wall";
	
	private Vector myGravity;
	private double myViscosity;
	private CenterOfMass myCenter;
	private List<WallRepulsion> myWalls = new ArrayList<WallRepulsion>();
	
	 public void loadEnvironment (File modelFile) {
	     reset();   
		 try {
	            Scanner input = new Scanner(modelFile);
	            while (input.hasNext()) {
	                Scanner line = new Scanner(input.nextLine());
	                if (line.hasNext()) {
	                    String type = line.next();
	                    if (GRAVITY_KEYWORD.equals(type)) {
	                        gravityCommand(line);
	                    }
	                    else if (VISCOSITY_KEYWORD.equals(type)) {
	                        viscosityCommand(line);
	                    }
	                    else if(CENTEROFMASS_KEYWORD.equals(type)){
	                    	centerOfMassCommand(line);
	                    }
	                    else if(WALLREPULSION_KEYWORD.equals(type)){
	                    	wallRepulsionCommand(line);
	                    }
	                    
	                }
	            }
	            input.close();
	        }
	        catch (FileNotFoundException e) {
	            // should not happen because File came from user selection
	            e.printStackTrace();
	        }
	 }
	 
	 
	 public Vector getMyGravity() {
		return myGravity;
	}


	public double getMyViscosity() {
		return myViscosity;
	}

	public CenterOfMass getMyCenter() {
		return myCenter;
	}


	private void gravityCommand (Scanner line) {
	    double direction = line.nextDouble();
	    double magnitude = line.nextDouble();
	    myGravity = new Vector(direction, magnitude);
	    //System.out.println(direction);
	    //System.out.println(magnitude);
	 }
	 
	private void viscosityCommand(Scanner line){
		double scale = line.nextDouble();
		myViscosity =  scale;
	 }
	private void centerOfMassCommand(Scanner line){
		double magnitude = line.nextDouble();
		double exponent = line.nextDouble();
		myCenter = new CenterOfMass(magnitude,exponent);
	} 
	
	private void wallRepulsionCommand(Scanner line){
		int wallID = line.nextInt();
		double magnitude = line.nextDouble();
		double exponent = line.nextDouble();
		myWalls.add(new WallRepulsion(wallID,magnitude,exponent));
	}
	
	public void reset(){
		myGravity = new Vector();
		myViscosity = 0.0;
		myCenter = new CenterOfMass(0,0);
		myWalls.clear();
	}
	
	public void applyGravity(Mass m){
		Vector g = new Vector(getMyGravity());
		g.scale(m.getMyMass());
		m.applyForce(g);
	}
	
	public void applyVescosity(Mass m){
		Vector vescoForce = new Vector(m.getVelocity());
		vescoForce.scale(myViscosity);
		vescoForce.negate();
		m.applyForce(vescoForce);
	}
	public void applyCenterAttraction(Mass m){
		Vector attForce  = myCenter.getForce(m);
	    m.applyForce(attForce);
	}
	public void applyRepulsionForce(Mass m, Dimension bounds){
		for(WallRepulsion wr : myWalls){
			Vector repForce = wr.getForce(m,bounds);
			m.applyForce(repForce);
		}
		
	}
	public void applyEnvironment(Mass m, Dimension bounds){
		this.applyCenterAttraction(m);
		this.applyRepulsionForce(m, bounds);
		this.applyVescosity(m);
	}
}
