package com.BryanJohnson.Utils.Math;

// Specific implementation for doubles to keep lightweight

public class RingBufferDouble {
	double [] m_buffer;
	int m_idx;
	
	////////////////////////
	
	public RingBufferDouble(int capacity) {
		m_buffer = new double[capacity];
	}
	
	////////////////////////
	
	public void insert(double item) {
		m_buffer[wrapIdx(m_idx++)] = item;
	}
	
	////////////////////////
	
	public double get(int idx) {
		return m_buffer[wrapIdx(idx)];
	}
	
	////////////////////////
	
	public double [] getBackingArray() {
		return m_buffer;
	}
	
	////////////////////////
	
	private int wrapIdx(int idx) {
		return idx % m_buffer.length;
	}
	
	////////////////////////
	
	public int capacity() {
		return m_buffer.length;
	}
	
	////////////////////////
	
	public int size() {
		return Math.min(capacity(), m_idx);
	}
	
	////////////////////////
	
	public boolean isFilled() {
		return m_idx >= capacity();
	}
}
