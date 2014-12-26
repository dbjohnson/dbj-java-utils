package com.BryanJohnson.Utils.Search;

public class BinaryTree<T extends Comparable<T>> {
	
	class Node {
		T value;
		Node left;
		Node right;
		
		Node(T _val) {
			value = _val; 
		}
		
		boolean pushDown(Node newNode) {
			
			if (this.left == null) {
				this.left = newNode;
				return true;
			}
			
			if (this.right == null) {
				this.right = newNode;
				return true;
			}
			
			if (newNode.value.compareTo(this.left.value) <= 0 && 
				newNode.value.compareTo(this.right.value) <= 0) {
				if (this.left.value.compareTo(this.right.value) <= 0) {
					newNode.pushDown(this.right);
					this.right = newNode;
					return true;
				}
				else {
					newNode.pushDown(this.left);
					this.left = newNode;
					return true;
				}
			}
			
			if (newNode.value.compareTo(this.left.value) <= 0) {
				newNode.pushDown(this.left);
				this.left = newNode;
				return true;
			}

			if (newNode.value.compareTo(this.right.value) <= 0) {
				newNode.pushDown(this.right);
				this.right = newNode;
				return true;
			}

			if (this.left.value.compareTo(this.right.value) <= 0) {
				return this.right.pushDown(newNode);
			}
			
			return this.left.pushDown(newNode);
		}
		
		public int getDepth(int depth) {
			int leftDepth = depth;
			if (left != null ) {
				leftDepth = left.getDepth(depth+1);
			}
			int rightDepth = depth;
			if (right != null ) {
				rightDepth = right.getDepth(depth+1);
			}
			return Math.max(leftDepth, rightDepth);
		}
		
		public void printInOrder() {
			if (this.left != null) {
				System.out.printf("%f\t", this.left.value);
			}
			
			System.out.printf("%f\t", this.value);
			
			if (this.right != null) {
				System.out.printf("%f\t", this.right.value);
			}

			System.out.printf("\n");
			
			if (this.left != null) {
				this.left.printInOrder();
			}
			if (this.right != null) {
				this.right.printInOrder();
			}
		}
		
	}
	
	Node root = null;
	
	public BinaryTree() {
		
	}
	
	public void insert(T val) {
		Node newNode = new Node(val);

		if (root == null) {
			root = newNode;
			return;
		}
		
		if (newNode.value.compareTo(root.value) <= 0) {
			newNode.left = root;
			root = newNode;
		}
		else {
			boolean inserted = root.pushDown(newNode);
			if (!inserted) {
				throw new RuntimeException("Unable to insert value!");
			}
		}
	}

	public T extract() {
		if (root == null) {
			throw new RuntimeException("Tree is empty");
		}
		
		T retVal = root.value;
		if (root.left == null) {
			root = root.right;
		}
		else if (root.right == null) {
			root = root.left;
		}
		else if (root.left.value.compareTo(root.right.value) <= 0) {
			root.left.pushDown(root.right);
			root = root.left;
		}
		else {
			root.right.pushDown(root.left);
			root = root.right;
		}
		
		return retVal;
	}
	
	public int getDepth() {
		return root.getDepth(1);
	}
	
	public void printInOrder() {
		root.printInOrder();
	}
	
}
