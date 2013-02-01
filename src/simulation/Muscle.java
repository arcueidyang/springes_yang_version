package simulation;

import java.awt.Dimension;

public class Muscle extends Spring {

	private double myAmplitude; 
	private double totalTime;
	private double myRestLength;
	
	public Muscle(Mass start, Mass end, double length, double kVal, double amplitude) {
		super(start, end, length, kVal);
		myAmplitude = amplitude;
		myRestLength = length;
	    totalTime = 0;
	}

	public void update(double elapsedTime, Dimension bounds){
		totalTime += elapsedTime;
		double currentLength = 0;
		double avgMass = Math.abs(getMyStart().getMyMass()) +Math.abs(getMyEnd().getMyMass());
		avgMass = avgMass/2;
		double omega = Math.sqrt(avgMass/getMyK());
		currentLength = myAmplitude *Math.sin(omega*totalTime)+myRestLength;
		this.setMyLength(currentLength);
		super.update(elapsedTime, bounds);
	}
}
