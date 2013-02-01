package simulation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import util.Location;
import util.Pixmap;
import util.Sprite;
import util.Vector;


/**
 * XXX.
 * 
 * @author Robert C. Duvall
 */
public class Mass extends Sprite {    
    // reasonable default values
    public static final Dimension DEFAULT_SIZE = new Dimension(16, 16);
    public static final Pixmap DEFUALT_IMAGE = new Pixmap("mass.gif");

    private double myMass;
    private Vector myAcceleration;
    
    public static EnvironmentProperties myEnvironment ;


    /**
     * XXX.
     */
    public Mass (double x, double y, double mass) {
        super(DEFUALT_IMAGE, new Location(x, y), DEFAULT_SIZE);
        myMass = mass;
        myAcceleration = new Vector();
    }

    /**
     * XXX.
     */
    @Override
    public void update (double elapsedTime, Dimension bounds) {
        System.out.println("1");
    	Vector bounce = getBounce(bounds);
    	applyForce(bounce);
        
    	
        myEnvironment.applyEnvironment(this,bounds);
    	
    	//add gravity
    	if(bounce.getYChange() == 0){
            myEnvironment.applyGravity(this);
    	}
        
    
       
        
     
        // convert force back into Mover's velocity
        getVelocity().sum(myAcceleration);
        myAcceleration.reset();
        // move mass by velocity
      //detect position
        
        super.update(elapsedTime, bounds);
        
    }

    /**
     * XXX.
     */
    @Override
    public void paint (Graphics2D pen) {
        pen.setColor(Color.BLACK);
        pen.fillOval((int)getLeft(), (int)getTop(), (int)getWidth(), (int)getHeight());
    }

    /**
     * Use the given force to change this mass's acceleration.
     */
    public void applyForce (Vector force) {
        Vector scaled = new Vector(force);
    	scaled.scale(1/myMass);
    	myAcceleration.sum(scaled);
    }

    /**
     * Convenience method.
     */
    public double distance (Mass other) {
        // this is a little awkward, so hide it
        return new Location(getX(), getY()).distance(new Location(other.getX(), other.getY()));
    }


    // check for move out of bounds
    private Vector getBounce (Dimension bounds) {
        final double IMPULSE_MAGNITUDE = 2;
        int tolerance = 10;
        
        Vector impulse = new Vector();
        if (getLeft() < tolerance) {
            impulse = new Vector(RIGHT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getRight() -tolerance > bounds.width) {
            impulse = new Vector(LEFT_DIRECTION, IMPULSE_MAGNITUDE);
        }
        if (getTop() < tolerance) {
            impulse = new Vector(DOWN_DIRECTION, IMPULSE_MAGNITUDE);
        }
        else if (getBottom() - tolerance > bounds.height) {
            impulse = new Vector(UP_DIRECTION, IMPULSE_MAGNITUDE);
        }
        impulse.scale(getMyMass());
        impulse.scale(getVelocity().getRelativeMagnitude(impulse));
        return impulse;
    }

    
    
	public double getMyMass() {
		return myMass;
	}

	
}
