package com.vivek.puzzle.eightpuzzle;

import java.util.Arrays;

import aima.core.agent.State;

public class PuzzleState implements State {
	Node currentPuzzleBoard[][]; // Current Puzzle Board
	Node zeroNode; // Position of the blank square
	int g_value;
	int h_value;
	int f_value;
	
	/*
	 * TODO: If necessary change the hashCode() and equals() based on f,g,h values
	 */
	PuzzleState(Node currentPuzzleBoard[][], Node zeroNode) {
		this.currentPuzzleBoard = currentPuzzleBoard;
		this.zeroNode = zeroNode;
	}
	
	Node[][] getCurrentPuzzleBoard() {
		return currentPuzzleBoard;
	}
	
	Node getZeroNode() {
		return zeroNode; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(currentPuzzleBoard);
		result = prime * result
				+ ((zeroNode == null) ? 0 : zeroNode.hashCode());
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
		PuzzleState other = (PuzzleState) obj;
		if (!Arrays.deepEquals(currentPuzzleBoard, other.currentPuzzleBoard))
			return false;
		if (zeroNode == null) {
			if (other.zeroNode != null)
				return false;
		} else if (!zeroNode.equals(other.zeroNode))
			return false;
		return true;
	}
	
}
