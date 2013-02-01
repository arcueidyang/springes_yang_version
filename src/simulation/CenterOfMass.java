package simulation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import util.Location;
import util.Vector;

public class CenterOfMass extends Force {

	private Location myLocation;
	
	public CenterOfMass(double magnitude, double exponent) {
		super(magnitude, exponent);
	    myLocation = new Location();
	}
    
	/**
	 * calculate the center of mass of all mass points
	 * @param myMasses
	 */
	public void calculateCenter(List<Mass> myMasses){
		double xPosition = 0;
		double yPosition = 0;
		double totalMass = 0;
		
		for(Mass m : myMasses){
			double mass = Math.abs(m.getMyMass());
			xPosition += m.getX()*mass;
			yPosition += m.getY()*mass;
		    totalMass += mass;
		}
	    xPosition = xPosition/totalMass;
	    yPosition = yPosition/totalMass;
		myLocation = new Location(xPosition,yPosition);
		//System.out.println(xPosition);
		//System.out.println(yPosition);
	}
    public double getDistance(Mass m){
    	return myLocation.distance(m.getX(), m.getY());
    }

    public double getAngle(Mass m){
    	double dx = myLocation.getX()- m.getX();
    	double dy = myLocation.getY()- m.getY();
    	return Vector.angleBetween(dx, dy);
    }
    
    public Vector getForce(Mass m){
    	double angle = getAngle(m);
    	double distance = getDistance(m);
    	return super.getForce(angle, distance);
    }  
    
    public void drawCenter(Graphics2D pen){
    	pen.setColor(Color.green);
    	pen.drawRect((int)myLocation.getX()-5, (int)myLocation.getY()-5, 10, 10);
    	pen.setColor(Color.BLACK);
    }
}
