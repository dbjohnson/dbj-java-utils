package com.BryanJohnson.Utils.Math;

import java.lang.reflect.Array;


public class RingBuffer<T> {
	T [] m_buffer;
	int m_idx;
	
	////////////////////////
	
	@SuppressWarnings("unchecked")
	public RingBuffer(Class<T> Tclass, int capacity) {
		m_buffer = (T[])Array.newInstance(Tclass, capacity);
	}
	
	////////////////////////
	
	public void insert(T item) {
		m_buffer[wrapIdx(m_idx++)] = item;
	}
	
	////////////////////////
	
	public T get(int idx) {
		return m_buffer[wrapIdx(idx)];
	}
	
	////////////////////////
	
	public T [] getBackingArray() {
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
