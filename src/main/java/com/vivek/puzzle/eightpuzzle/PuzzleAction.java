package com.vivek.puzzle.eightpuzzle;

import aima.core.agent.Action;

public class PuzzleAction implements Action{
	
	Moves moveToMake;
	
	PuzzleAction(Moves move) {
		this.moveToMake = move;
	}

	Moves getAction() {
		return moveToMake;
	}
	public boolean isNoOp() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String toString() {
		if(moveToMake == Moves.UP)
			return "UP";
		else if(moveToMake == Moves.DOWN)
			return "DOWN";
		else if(moveToMake == Moves.LEFT)
			return "LEFT";
		else
			return "RIGHT";
	}

}
