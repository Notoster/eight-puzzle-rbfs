package com.vivek.puzzle.eightpuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import aima.core.agent.Action;
import aima.core.agent.Percept;
import aima.core.agent.State;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.GoalTest;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.SimpleProblemSolvingAgent;
/**
 * @author Vivek Venkatesh Ganesan
 * 
 * Main Class to Solve the 8-Puzzle Problem
 *
 */
public class EightPuzzle extends SimpleProblemSolvingAgent
{	
	private PuzzleState initialPuzzleState; 
	private PuzzleState goalPuzzleState; 
	
	/**
	 * 
	 * @param pathToInputFile Input File contains the initial state of the puzzle
	 * @param pathToGoalFile Goal File contains the desired goal state of the puzzle
	 * @param boardSize Specifies the dimension of the board (3 corresponds to 8 puzzle problem)
	 */
	EightPuzzle(String pathToInputFile, String pathToGoalFile, int boardSize) {
		// This is an Eight Puzzle Problem and has a 3 X 3 board
		initialPuzzleState = readFromFile(pathToInputFile, boardSize);
		goalPuzzleState = readFromFile(pathToGoalFile, boardSize);
	}
	
	/**
	 * Helper Function to read contents from the fiel
	 * @param pathToFile Path of the file to be read
	 * @param boardSize Dimension of the board
	 * @return
	 */
	public PuzzleState readFromFile(String pathToFile, int boardSize) {
		PuzzleState puzzleState = null;
		File inputFile = new File(pathToFile);
		BufferedReader br = null;
		Node puzzleBoard[][] = new Node[boardSize][boardSize];
		Node sortedValues[] = new Node[boardSize * boardSize];
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
							int thisValue = Integer.parseInt(number);
							Node thisNode = new Node(i, j, thisValue);
							puzzleBoard[i][j] = thisNode;
							if(thisValue == 0) {
								zeroNode = thisNode;
							}
							if(thisValue < boardSize * boardSize)
								sortedValues[thisValue] = thisNode;
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
		}
		puzzleState = new PuzzleState(puzzleBoard, zeroNode);
		puzzleState.setSortedValues(sortedValues);
		return puzzleState;
	}
	
	

	@Override
	protected State updateState(Percept p) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return goal	Goal to be achieved by this agent
	 */
	@Override
	protected Object formulateGoal() {
		return goalPuzzleState;
	}

	/**
	 * Formulate the problem for the Agent
	 * @param goal	Goal to be achieved by this agent
	 */
	@Override
	protected Problem formulateProblem(Object goal) {
		
		Object initialState = initialPuzzleState;
		GoalTest goalTest = new GoalStateChecker(goal);
		ActionsFunction actionsFunction = new ActionsFunctionSet();
		ResultFunction resultFunction = new ResultFunctionBoard();
		Problem eightPuzzleProblem = new Problem(initialState, actionsFunction, resultFunction, goalTest);
	
		return eightPuzzleProblem;
	}

	/**
	 * @param problem		Problem solved by this agent
	 * @return List<Action>	List of Actions taken by this agent to solve the problem
	 */
	@Override
	protected List<Action> search(Problem problem) {
		Search search = new Search(problem, initialPuzzleState, goalPuzzleState);
		TreeNode solution = search.getSolution();
		List<Action> actions = new ArrayList<Action>();
		if(solution!=null) {
			for(PuzzleAction action: solution.getActionsSoFar()) {
				actions.add((Action) action);
			}
		}
		return actions;
	}
	
	@Override
	protected void notifyViewOfMetrics() {
		
		
	}
	public static void main( String[] args )
    {
        EightPuzzle eightPuzzle = new EightPuzzle("src/main/java/com/vivek/puzzle/eightpuzzle/input.txt","src/main/java/com/vivek/puzzle/eightpuzzle/goal.txt", 3);
        Problem eightPuzzleProblem = eightPuzzle.formulateProblem(eightPuzzle.formulateGoal());
        List<Action> solution = eightPuzzle.search(eightPuzzleProblem);
        System.out.println("Number of Steps = " + solution.size());
        for(Action action: solution) {
        	System.out.println(action);
        }
    }
}
