package odev;

import java.util.*;

public class RangeQuery {
	/* Database with all objects */
	static ArrayList<double[]> points = new ArrayList<double[]>();
	
	static final List<Integer> nums = new ArrayList<Integer>();
	// I have defined new integer list in order to store our candidate results when dimension size equals to 2
	// With this list we have our candidate values so we can check our exact distances with the help of this list.
	
	public static void main(String[] args) {
		
		// Insert data points 
		double[] p = new double[]{1, 1, 7, 2}; points.add(p);
		p = new double[]{1, 16, 3, 4}; points.add(p);
		p = new double[]{2,1,7,4}; points.add(p);
		p = new double[]{2,8,1,7}; points.add(p);
		p = new double[]{8,6,9,1}; points.add(p);
		p = new double[]{9,10,5,8}; points.add(p);
		p = new double[]{9,13,11,4}; points.add(p);
		p = new double[]{10,8,9,2}; points.add(p);
		p = new double[]{10,12,6,4}; points.add(p);
		p = new double[]{10,14,2,2}; points.add(p);
		p = new double[]{12,14,5,3}; points.add(p);
		p = new double[]{14,12,1,8}; points.add(p);
		p = new double[]{15,16,6,9}; points.add(p);
		p = new double[]{16,1,12,6}; points.add(p);
		p = new double[]{18,14,5,1}; points.add(p);
		p = new double[]{18,16,12,6}; points.add(p);

		// Define query point
		double[] q = new double[]{7, 14, 5, 2};
		
		// Define epsilon 
		double eps = 6.0;
		
		int origDim = 4;
		int filterDim = 2;
		int size = points.size();
		
		// store the object indices
		LinkedList<Integer> toCheck = new LinkedList<Integer>();
		for(int i=0; i<size; i++) {	toCheck.add(i);	}

		// filter step
		toCheck = scanDB(toCheck,filterDim,q,eps);
		System.out.println("Size of the candidate set: " + toCheck.size());
		
		// refinement step
		toCheck = scanDB(toCheck,origDim,q,eps);
		System.out.println("Size of the result set: " + toCheck.size());
	}
	
	/**
	* @param indices : indices of the objects in the database 
	* @param dim : states how many dimensions will be used to determine the euclidean distance
	* @param q : query object
	* @param eps : epsilon for the range query
	*/
	static LinkedList<Integer> scanDB(List<Integer> indices, int dim, double[] q, double eps) {
		LinkedList<Integer> result = new LinkedList<Integer>();		
		/* TODO:
		Range query in the database {@code points}.  
		The result of the query will be stored in the variable "result".
		*/	
		if(dim == 4) {
			for(int i=0; i<nums.size(); i++) {	// nums.size will be the size of our candidates list's size so we wont be looking every 16 data point in our other list.
				double distance = dist(points.get(nums.get(i)), q, 4); // with nums.get we get our candidate value and with points.get we check for that candidate's distance in our distance function
				if(distance <= eps) {	// if distance is smaller we will be selecting this candidate as a result
					System.out.println(nums.get(i));
					result.add(nums.get(i));	// we add our results into result set
				}
			}
		}
		
		if(dim ==2) {	// Since we calculated distances for our candidate values we have to compare every distance with epsilon
			for(int i= 0; i<points.size(); i++) {	// points.size = size of our data object with this we will be looking at all values on our list
				double distance = dist(points.get(i), q, 2); // we call our distance function in order to make comparison between eps and distance
				if(distance <= eps) {	// if eps value is greater or equal than our candidates' values we will be adding them into our candidate list
					System.out.println(i);
					result.add(i);	// this requirement has fulfilled so we will add to our list
					nums.add(i);	// we also add it to our other list to keep track of which values we have picked as candidates
				}
			}
		}
		return result;
	}
	
	static double dist(double[] p, double[] q, int dim) {
		// In this step we will calculate the euclidean distances for our candidates and for our exact distances
		double distance = 0.0;
		if (dim == 2) {
			double first  = p[0] - q[0];	// We have to calculate first two value's of our array as it is requested from us.
			double second = p[1] - q[1];
			distance =Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2));	// When we calculate those two distances above, we have to make euclidean equation. 
																			// So we get the squares of our values then add together then square root in order to find distance value
		}
		
		if(dim == 4) {
			double first  = p[0] - q[0];	//Same here with one little difference if we want to calculate exact distances we have to look for all values
			double second = p[1] - q[1];	// So we subtract each data points from our query object
			double third  = p[2] - q[2];
			double fourth = p[3] - q[3];
			distance = Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2) + Math.pow(third, 2) + Math.pow(fourth, 2)); // Same euclidean equation 
		}
		
		return distance;
		
		/* TODO: 
		Computation of the euclidean distance
		*/	
	}	
	
} 
