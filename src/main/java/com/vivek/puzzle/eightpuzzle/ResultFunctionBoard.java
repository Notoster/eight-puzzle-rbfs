package com.vivek.puzzle.eightpuzzle;

import java.util.ArrayList;

import aima.core.agent.Action;
import aima.core.search.framework.ResultFunction;

public class ResultFunctionBoard implements ResultFunction {

	public Object result(Object s, Action a) {
		// Returns the new state of s after applying action a
		// Add to movesMade here
		// Increment the g_value when you add it
		PuzzleState puzzleState = (PuzzleState) s;
		
		PuzzleAction puzzleAction =(PuzzleAction) a;
		
		if(puzzleAction.getAction() == Moves.UP) {
			Node zeroNode = puzzleState.getZeroNode();
			
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard();
			Node upNode = puzzleBoard[zeroNodeX-1][zeroNodeY];
			zeroNode.setX(zeroNodeX - 1); 
			upNode.setX(upNode.getX() + 1);
			// Swap the upNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = upNode;
			puzzleBoard[zeroNodeX-1][zeroNodeY] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues();
			int val = upNode.getValue();
			sortedValues[val] = upNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			/*ArrayList<Moves> movesMadeSoFar = puzzleState.getMovesMade();
			newState.setMovesMode(movesMadeSoFar);*/
			////newState.addToMovesMade(Moves.UP);
			////newState.setGValue(puzzleState.getGValue() + 1);
			///newState.setGValue(newState.getNumberOfMoves());
			return newState;
		}
		else if(puzzleAction.getAction() == Moves.DOWN) {
			Node zeroNode = puzzleState.getZeroNode();
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard().clone();
			Node downNode = puzzleBoard[zeroNodeX+1][zeroNodeY];
			
			zeroNode.setX(zeroNodeX + 1); 
			downNode.setX(downNode.getX() - 1);
			// Swap the downNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = downNode;
			puzzleBoard[zeroNodeX+1][zeroNodeY] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues().clone();
			int val = downNode.getValue();
			sortedValues[val] = downNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			/*ArrayList<Moves> movesMadeSoFar = puzzleState.getMovesMade();
			newState.setMovesMode(movesMadeSoFar);*/
			////newState.addToMovesMade(Moves.DOWN);
			////newState.setGValue(puzzleState.getGValue() + 1);
			///newState.setGValue(newState.getNumberOfMoves());
			return newState;
		}
		else if(puzzleAction.getAction() == Moves.LEFT) {
			Node zeroNode = puzzleState.getZeroNode();
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard();
			Node leftNode= puzzleBoard[zeroNodeX][zeroNodeY-1];
			
			zeroNode.setY(zeroNodeY - 1); 
			leftNode.setY(leftNode.getY() + 1);
			// Swap the leftNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = leftNode;
			puzzleBoard[zeroNodeX][zeroNodeY-1] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues();
			int val = leftNode.getValue();
			sortedValues[val] = leftNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			/*ArrayList<Moves> movesMadeSoFar = puzzleState.getMovesMade();
			newState.setMovesMode(movesMadeSoFar);*/
			////newState.addToMovesMade(Moves.LEFT);
			////newState.setGValue(puzzleState.getGValue() + 1);
			///newState.setGValue(newState.getNumberOfMoves());
			return newState;
		}
		else if(puzzleAction.getAction() == Moves.RIGHT){
			Node zeroNode = puzzleState.getZeroNode();
			int zeroNodeX = zeroNode.getX();
			int zeroNodeY = zeroNode.getY();
			
			Node[][] puzzleBoard = puzzleState.getCurrentPuzzleBoard();
			Node rightNode = puzzleBoard[zeroNodeX][zeroNodeY+1];
			
			zeroNode.setY(zeroNodeY + 1); 
			rightNode.setY(rightNode.getY() - 1);
			// Swap the rightNode and ZeroNode (By doing this we have to update them)
			puzzleBoard[zeroNodeX][zeroNodeY] = rightNode;
			puzzleBoard[zeroNodeX][zeroNodeY+1] = zeroNode;
			
			Node[] sortedValues = puzzleState.getSortedValues();
			int val = rightNode.getValue();
			sortedValues[val] = rightNode;
			sortedValues[0] = zeroNode;
			PuzzleState newState = new PuzzleState(puzzleBoard, zeroNode); 
			newState.setSortedValues(sortedValues);
			/*ArrayList<Moves> movesMadeSoFar = puzzleState.getMovesMade();
			newState.setMovesMode(movesMadeSoFar);*/
			////newState.addToMovesMade(Moves.RIGHT);
			////newState.setGValue(puzzleState.getGValue() + 1);
			///newState.setGValue(newState.getNumberOfMoves());
			return newState;
		}
		return puzzleState;
	}

}
