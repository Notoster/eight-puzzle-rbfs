package com.vivek.puzzle.eightpuzzle;

import aima.core.search.framework.GoalTest;

public class GoalStateChecker implements GoalTest {
	
	PuzzleState goalState; 
	
	GoalStateChecker(Object goalState) {
		this.goalState = (PuzzleState)goalState;
	}
	public boolean isGoalState(Object state) {
		// TODO Auto-generated method stub
		PuzzleState thisState = (PuzzleState) state;
		if(thisState.equals(goalState))
			return true;
		else
			return false;
	}

}
