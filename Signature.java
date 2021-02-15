package odev;

import java.util.*;

public class Signature {
	HashMap<double[], double[][]> map = new HashMap<double[], double[][]>();
	double[] weights;
	double[][] representatives;
	
	public Signature(double[] weights, double[][] representatives) { // if i want to use signature i have to create constructor that what i have done here. 
		map.put(weights, representatives); // Idea behind is that in real life actually our weight values mapped into our points
		// So actually with certain keys we can get the values that are mapped into that key, same goes for values as well. 
		this.weights = weights;	
		this.representatives = representatives;
		// i have defined our array and matrix values so that we can call them from here
	}
	
}