package com.vivek.puzzle.eightpuzzle;

public class Node {
	int x;
	int y;
	int value;
	
	Node(int x, int y, int value) {
		// Position of this Node in the Eight Puzzle Board
		this.x = x;
		this.y = y;
		this.value = value;
	}
	
	int getX() {
		return x;
	}
	
	int getY() {
		return y;
	}
	
	int getValue() {
		return value;
	}
	
	void setX(int x) {
		this.x = x;
	}
	
	void setY(int y) {
		this.y = y; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (value != other.value)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	public String toString() {
		return Integer.toString(value);
	}
}
