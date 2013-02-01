package simulation;

import java.awt.Dimension;

import util.Sprite;
import util.Vector;

public class WallRepulsion extends Force {
    
	public static final int TOP_WALL = 1;
	public static final int RIGHT_WALL = 2;
	public static final int BOTTOM_WALL = 3;
	public static final int LEFT_WALL = 4;
	
	
	private int wallID;
	
	public WallRepulsion(int wall,double magnitude, double exponent) {
		super(magnitude, exponent);
		wallID = wall;
	}

	public double getDistance(Mass m, Dimension bounds){
		double distance = 0;
		switch (wallID)
		{
			case TOP_WALL: 	distance = m.getY();
							break;
			case LEFT_WALL: 	distance = m.getX();
							break;
			case RIGHT_WALL: 	distance = bounds.getWidth() - m.getX();
							break;
			case BOTTOM_WALL: 	distance = bounds.getHeight() - m.getY();
							break;
		}	
		return distance;
	}
    
	public double getAngle(){
		double angle = 0;
		switch(wallID)
		{
		    case TOP_WALL: angle = Sprite.DOWN_DIRECTION;
		                   break;
		    case LEFT_WALL: angle = Sprite.RIGHT_DIRECTION;
                           break;
		    case BOTTOM_WALL: angle = Sprite.UP_DIRECTION;
                           break;
		    case RIGHT_WALL: angle = Sprite.LEFT_DIRECTION;
                           break;
		}
	    return angle;
	}   
	
	public Vector getForce(Mass m, Dimension bounds){
		double distance = getDistance(m, bounds);
		double angle = getAngle();
	    return super.getForce(angle, distance);
	}
}
