package com.BryanJohnson.Utils.Math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.BryanJohnson.Utils.Math.RingBufferDouble;
import com.BryanJohnson.Utils.Math.StatsCalculator;

public class StatsCalculatorTests {

	final double kTolerance = 1e-12;

	///////////////
	
	@Test
	public void testAvgOfConstants() {
		int n = 100;
		RingBufferDouble buffer = new RingBufferDouble(n);
		
		double val = 3;
		for (int k = 0; k < n; ++k) {
			buffer.insert(3);
		}
		
		assertEquals(val, StatsCalculator.avg(buffer.getBackingArray()), kTolerance);
	}
	
	///////////////
	
	@Test
	public void testStdOfConstants() {
		int n = 100;
		RingBufferDouble buffer = new RingBufferDouble(n);
		
		for (int k = 0; k < n; ++k) {
			buffer.insert(10);
		}
		
		assertEquals(0, StatsCalculator.std(buffer.getBackingArray()), kTolerance);
	}

	///////////////
	
	@Test
	public void testStdOfRand() {
		int n = 100;
		RingBufferDouble buffer = new RingBufferDouble(n);
		
		double sum = 0;
		double sumSq = 0;
		for (int k = 0; k < n; ++k) {
			double rand = Math.random();
			buffer.insert(rand);
			sum += rand;
			sumSq += rand*rand;
		}
		
		double avg = sum/n;
		double var = sumSq/n - avg*avg;
		var *= n / (n - 1.0); // Bessel's correction
		double std = Math.sqrt(var);
		
		assertEquals(std, StatsCalculator.std(buffer.getBackingArray()), kTolerance);
	}

	
}
