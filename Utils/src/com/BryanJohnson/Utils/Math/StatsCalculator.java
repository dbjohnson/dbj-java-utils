package com.BryanJohnson.Utils.Math;

public class StatsCalculator {
	
	///////////////////
	
	public static double avg(double [] array) {
		double sum = 0;

		for (double val : array) {
			sum += val;
		}
		
		return sum / array.length;
	}

	///////////////////

	public static double std(double [] array) {
		
		double sum = 0;
		double sumSq = 0;
		for (double val : array) {
			sum += val;
			sumSq += val*val;
		}
		
		int n = array.length;
		double avg = sum/n;
		double var = sumSq/n - avg*avg;
		var *= n / (n - 1.0); // Bessel's correction
		double std = Math.sqrt(var);

		return std;
	}

	///////////////////

	public static double distStds(double val, double [] array) {
		double diff = val - StatsCalculator.avg(array);
		return Math.abs(diff) / StatsCalculator.std(array);
	}

	///////////////////

	public static double diffPct(double val, double [] array) {
		double avg = StatsCalculator.avg(array);
		double diff = val - avg ;
		return Math.abs(diff) / Math.max(val, avg);
	}

}
