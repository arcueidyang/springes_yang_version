package simulation;

import java.awt.Dimension;

public class FixedMass extends Mass {

	public FixedMass(double x, double y, double mass) {
		super(x, y, mass);
	}
	
    @Override
    public void update(double elapsedTime, Dimension bounds){
    	//super.update(elapsedTime, bounds);
        System.out.println("2");
    }

}
