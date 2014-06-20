package com.vivek.puzzle.eightpuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.agent.Percept;
import aima.core.agent.State;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.GoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.SimpleProblemSolvingAgent;
/**
 * Hello world!
 *
 */
public class EightPuzzle extends SimpleProblemSolvingAgent
{
	/*Node initialPuzzleBoard[][];
	Node goalPuzzleBoard[][];
	Node currentPuzzleBoard[][];*/
	
	PuzzleState initialPuzzleState; 
	PuzzleState goalPuzzleState; 
	PuzzleState currentPuzzleState;
	
	EightPuzzle(String pathToInputFile, String pathToGoalFile, int boardSize) {
		// This is an Eight Puzzle Problem and has a 3 X 3 board
		initialPuzzleState = readFromFile(pathToInputFile, boardSize);
		currentPuzzleState = initialPuzzleState;
		goalPuzzleState = readFromFile(pathToGoalFile, boardSize);
	}
	
	public PuzzleState readFromFile(String pathToInputFile, int boardSize) {
		PuzzleState puzzleState = null;
		File inputFile = new File(pathToInputFile);
		BufferedReader br = null;
		Node puzzleBoard[][] = new Node[boardSize][boardSize];
		Node zeroNode = null;
		if(inputFile.isFile()) {
			try {
				br = new BufferedReader(new FileReader(inputFile));
				String line;
				int i = 0;
				while ((line = br.readLine()) != null) {
					String numbers[] = line.split("\t");
					int j = 0;
					for(String number: numbers) {
						if(number.matches("\\d+")) {
							Node thisNode = new Node(i, j, Integer.parseInt(number));
							puzzleBoard[i][j] = thisNode;
							if(Integer.parseInt(number) == 0) {
								zeroNode = thisNode;
							}
						}
						j++;
					}
					i++;
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			} 
			finally {
				if(br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			 
		}
		else {
			System.err.println("The input file does not exist");
			puzzleState = null;
			/*initialPuzzleState = null;
			goalPuzzleState = null;
			currentPuzzleState = null;*/
		}
		/*initialPuzzleState = new PuzzleState(puzzleBoard, zeroNode);
		currentPuzzleState = initialPuzzleState;*/
		puzzleState = new PuzzleState(puzzleBoard, zeroNode);
		return puzzleState;
	}
	
	// Actions are like UP, DOWN, LEFT, RIGHT
	// STATE maintains the puzzle board and where is the '0' node currently is
	public void printBoard() {
		System.out.println("The Current Puzzle Board is :");
		Node[][] currentBoard = currentPuzzleState.getCurrentPuzzleBoard();
		for(int i=0; i<currentBoard.length; i++) {
			for(int j=0; j< currentBoard[i].length; j++) {
				System.out.print(currentBoard[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
		
		System.out.println("The Goal Puzzle Board is :");
		currentBoard = goalPuzzleState.getCurrentPuzzleBoard();
		for(int i=0; i<currentBoard.length; i++) {
			for(int j=0; j< currentBoard[i].length; j++) {
				System.out.print(currentBoard[i][j]);
				System.out.print("\t");
			}
			System.out.print("\n");
		}
	}

	@Override
	protected State updateState(Percept p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object formulateGoal() {
		
		return goalPuzzleState;
	}

	@Override
	protected Problem formulateProblem(Object goal) {
		// TODO Auto-generated method stub
		Object initialState = initialPuzzleState;
		GoalTest goalTest = new GoalStateChecker(goal);
		ActionsFunction actionsFunction = new ActionsFunctionSet();
		ResultFunction resultFunction = new ResultFunctionBoard();
		Problem eightPuzzleProblem = new Problem(initialState, actionsFunction, resultFunction, goalTest);
	
		return eightPuzzleProblem;
	}

	@Override
	protected List<Action> search(Problem problem) {
		// TODO Auto-generated method stub
		
		return search(problem, initialPuzzleState, Double.MAX_VALUE);
	}
	
	protected List<Action> search(Problem problem, PuzzleState currentState, double f_limit) {
		if(problem.getGoalTest().isGoalState(currentState)) {
			// We have obtained the solution
			// Return the list of actions needed to reach the goal
		}
		ArrayList<PuzzleState> successors = new ArrayList<PuzzleState>();
		
		/*
		 * TODO: Implement code in ActionsFunctionSet.java and ResultFunctionBoard.java
		 */
		
		Set<Action> possibleActions= problem.getActionsFunction().actions(currentState);
		for(Action action: possibleActions) {
			PuzzleAction puzzleAction = (PuzzleAction) action;
			successors.add((PuzzleState)(problem.getResultFunction().result(currentState, puzzleAction)));
		}
		
		if(successors.isEmpty()) {
			// Failure
			List<Action> tempAction = null;
			return tempAction; 
		}
		
		
		return null;
	}

	@Override
	protected void notifyViewOfMetrics() {
		// TODO Auto-generated method stub
		
	}
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        EightPuzzle eightPuzzle = new EightPuzzle("src/main/java/com/vivek/puzzle/eightpuzzle/input.txt","src/main/java/com/vivek/puzzle/eightpuzzle/goal.txt", 3);
        eightPuzzle.printBoard();
        Problem eightPuzzleProblem = eightPuzzle.formulateProblem(eightPuzzle.formulateGoal());
        eightPuzzle.search(eightPuzzleProblem);
    }
}
