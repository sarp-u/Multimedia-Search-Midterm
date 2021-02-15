package odev;

import java.math.*;

public class SQFD {
	public static void main(String args[]) {	 	   		 

	       double[][] p1representatives =  {{1, 1}, {6, 2}};
	 	   double[][] p2representatives =  {{1, 2}, {4, 3}, {4, 6}};
	 	   double[][] qrepresentatives =   {{2, 1}, {5, 2}, {2, 5}};	 	 	  
	 	   double[] p1weights = {4, 6};
	 	   double[] p2weights = {3, 4, 3};
	 	   double[] qweights = {3, 4, 3};
	 	   Signature p1 = new Signature(p1weights, p1representatives);
	 	   Signature p2 = new Signature(p2weights, p2representatives);
	 	   Signature q = new Signature(qweights, qrepresentatives);	 
	 	   


		   double dist1 = 0.0;	
		   double dist2 = 0.0;	
		   
		   dist1 = computeSQFD(q, p1);
		   dist2 = computeSQFD(q, p2);	

		   System.out.println("SQFD(Q, P1)  : " + dist1);
		   System.out.println("SQFD(Q, P2)  : " + dist2);
				
	   } // end main
							
			private static double computeSQFD(Signature q, Signature p) {
				int matrixSize = q.representatives.length + p.representatives.length; // i have defined it to calculate every points distance to each other because we have to combine our p and q matrixes into one matrix
				// if we want to calculate the Euclidean distance and gaussian similarity
				int boundary = q.representatives.length; // query objects length is needed in order to divide to 4 parts
				double[][] num = new double[matrixSize][matrixSize]; // we will need it when we return our values from gaussian similarity method. we will store it in here because we need similarity matrix in last step
				double[] weight= new double[matrixSize]; // here i have written our weight values in one array. So when we are ready to send our data into multiplyRowMatrixColumn we will be using this.
				for(int i= 0; i<matrixSize; ++i) { // it is a bit complex but i have divided our matrix in 4 part 
					for(int j=0; j<matrixSize; ++j) {
						if(i<boundary) {
							if(j<boundary) {
								num[i][j] = similarityOfTwoPoints(q.representatives[i], q.representatives[j]); // first 3x3 grid is the distance between q and q values so this is what i have done here
							}
							if(j>=boundary) {
								num[i][j] = similarityOfTwoPoints(q.representatives[i], p.representatives[j-3]); // second 2x3 grid is the distance between q and p values
							}
						}
						if(i>=boundary) {
							if(j<boundary) {
								num[i][j] = similarityOfTwoPoints(p.representatives[i-3], q.representatives[j]); // third 3x2 grid is the distance p and q values
							}
							if(j>=boundary) {
								num[i][j] = similarityOfTwoPoints(p.representatives[i-3], p.representatives[j-3]); // fourth 2x2 grid is the distance between p and p values
							}
						}
							
						}
					}
				
				// so what i have done above is basically, i have divided q and p values into 4 section with that i can calculate the distances for each and every value
				// that we need in euclidean distance and gaussian distance. After splitting this values i have send them to similarity calculation there
				// every values similarity will be returned. Last step is the store this values in a matrix so we can use it later.

				for(int i=0; i<matrixSize; i++) { // we need a single weight array in last step. so i have combined q and p weights here.
					if(i<3) {
						weight[i] = q.weights[i]; // our q.weights.length is equals to 3 if it is smaller than 3 first indices will be q weight values
					}
					if(i>=3) {
						weight[i] = -1 * p.weights[i-3]; // after we store our q weights we have to store our p weights as well and in the formula it has to be negative so we multiply it with -1.
					}
				}
				double result = multiplyRowMatrixColumn(weight, num, weight); // after i have single array that contains weight and matrix that have similarity values i sent them into last part
				// where we calculate these 3 by multiplying them. I couldn't send weight Transpose so i will get transpose value in last step.
				
				
				return result; // returns the sqfd distances that we have calculated 
			}
			
			 private static double similarityOfTwoPoints(double[] x, double[] y) {	
				 double gauss=0;	// gauss distance what we need here so i have defined a double to store this values
				 double groundDista = 0; // if we want gauss distance value we need to calculate ground distance first. So it holds ground distance here.

				 groundDista = groundDist(x,y);	// we want the similarity between x and y values but first we need euclidean distance
				 groundDista *= groundDista;
				 gauss = Math.exp(-groundDista/2);
				 //our formula is e^-1/2* euclidean distance^2. I have done that above. First i have multiplied our euclidean distance, then i did the part
				 // where we need to calculate e^. after that we get a similarity value of two points
				 	
				 return gauss; // returns similarity point. It will be used in the first part.
			}
	
			private static double groundDist(double[] p, double[] q) {
				double distance= 0; // we need euclidean distance so i have defined a value which called distance;
				double x, y = 0; // since it is actually matrix it has x and y values(matrix[x][y]) so i have calculated x and y values separately
				x = p[0] - q[0]; // for x we need to subtract values that are in the first place
				y = p[1] - q[1]; // for y we need to subtract values that are in the second place 
				distance = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
				// Euclidean distance = square root of square of x + square of y. So the idea behind the code above is actually that. 
				
				return distance; // we return euclidean distance, so we can calculate similarity of two points now.
			}
			
			public static double multiplyRowMatrixColumn(double[] r, double[][] m, double[] c) {
				double result = 0; // this is the value we will get after every calculation 
				double[][] weightT = new double[r.length][1]; // in here i got the transpose of our weight array
				double[] gecici = new double[r.length]; // i have defined here a temporary array to hold the values when we multiply weight and our similarity matrix
				
				for(int i = 0; i < r.length; i++) {
				    weightT[i][0] = r[i];	// what i have done is basically is get the transpose of weight 
				}
				
				for(int x=0; x<r.length; x++) {
					for(int y=0; y<r.length; y++) {
						gecici[x] += r[y]*m[y][x]; // i have multiplied 1d array with 2d matrix and store it's values into our temporary array
					}
				}
				for(int i=0; i<r.length; i++) {
					result += gecici[i]*weightT[i][0]; // in final step i have multiplied our temporary array with the transpose of our weight values. so now i have almost the full answer 
				}

				return Math.sqrt(result); // i have to take the square root of the value that we get.
				// Our formula is square root of weight * similaritymatrix* weightT. I did it all except taking the square root of the result
				// so i have done it in the last step here.
			}

}