package com.vivek.puzzle.eightpuzzle;

import aima.core.search.framework.GoalTest;

public class GoalStateChecker implements GoalTest {
	
	Object goalState; 
	
	GoalStateChecker(Object goalState) {
		this.goalState = goalState;
	}
	public boolean isGoalState(Object state) {
		// TODO Auto-generated method stub
		if(state.equals(goalState))
			return true;
		else
			return false;
	}

}
