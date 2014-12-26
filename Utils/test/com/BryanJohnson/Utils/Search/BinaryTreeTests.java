package com.BryanJohnson.Utils.Search;

import static org.junit.Assert.*;

import org.junit.Test;

import com.BryanJohnson.Utils.Search.BinaryTree;

public class BinaryTreeTests {

	@Test
	public void testReturnsSorted() {
		BinaryTree<Double> tree = new BinaryTree<Double>();
		
		int n = 10;
		for (int k = 0; k < n; ++k) {
			tree.insert(Math.random());
		}

		System.out.printf("Tree depth: %d\n", tree.getDepth());
		tree.printInOrder();
		
		double lastValue = -Double.MAX_VALUE;
		for (int k = 0; k < n; ++k) {
			double val = tree.extract();
//			System.out.printf("%1.2f\n", val);
			if (val < lastValue) {
				fail("Tree is not sorted in ascending order!");
			}
			lastValue = val;
		}
		
		
	}

}
