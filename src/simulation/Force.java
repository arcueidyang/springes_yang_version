package simulation;

import util.Vector;

public class Force {
      private double myMagnitude;
      private double myExponent;
      
      public Force(double magnitude, double exponent){
    	  myMagnitude = magnitude;
    	  myExponent = exponent;
      }

	public double getMyMagnitude() {
		
		return myMagnitude;
	}

	public double getMyExponent() {
		return myExponent;
	}
      
    public Vector getForce(double angle, double distance){
    	Vector repel = new Vector(angle, myMagnitude);		
		if (myExponent == 2)
		{
			double scaleamount = 1 / Math.pow(distance / 100, 2);
			repel.scale(scaleamount);
		}
		// else constant force
		return repel;
    }
    
}
