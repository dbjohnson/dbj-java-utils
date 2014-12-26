package com.BryanJohnson.Utils.Math;

import static org.junit.Assert.*;

import org.junit.Test;

import com.BryanJohnson.Utils.Math.RingBuffer;
import com.BryanJohnson.Utils.Math.RingBufferDouble;

public class RingBufferTests {

	@Test
	public void testInsertWraps() {
		
		int capacity = 10;
		RingBuffer<Integer> objectBuffer = new RingBuffer<Integer>(Integer.class, capacity);
		RingBufferDouble doubleBuffer = new RingBufferDouble(capacity);
		
		assertTrue(!objectBuffer.isFilled());
		assertTrue(!doubleBuffer.isFilled());
		
		for (int k = 0; k < capacity + 1; ++k) {
			objectBuffer.insert(k);
			doubleBuffer.insert(k);
		}

		assertTrue(objectBuffer.get(0) == capacity);
		assertTrue(doubleBuffer.get(0) == capacity);
		
		assertTrue(objectBuffer.isFilled());
		assertTrue(doubleBuffer.isFilled());
		
	}

}
