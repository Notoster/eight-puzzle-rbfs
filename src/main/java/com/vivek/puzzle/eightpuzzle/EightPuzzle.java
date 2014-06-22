package com.vivek.puzzle.eightpuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	
	public int heuristicCostEstimate(PuzzleState currentState, PuzzleState goalState, int movesMadeSoFar) {
		// Manhattan heuristic function
		// The sum of the distances (sum of the vertical and horizontal distance) from the blocks to their goal positions, 
		// plus the number of moves made so far to get to the state.
		
		int manhattanDistance = 0;
		Node[][] currentPuzzleBoard = currentState.getCurrentPuzzleBoard();
		for(int i=0; i< currentPuzzleBoard.length; i++) {
			for(int j=0; j < currentPuzzleBoard[i].length; j++) {
				int val = currentPuzzleBoard[i][j].getValue();
				int sumOfDistances = Math.abs(goalState.getNodeWithValue(val).getX() - currentPuzzleBoard[i][j].getX()) + Math.abs(goalState.getNodeWithValue(val).getY() - currentPuzzleBoard[i][j].getY()); 
				if(val!=0)
					manhattanDistance = manhattanDistance + sumOfDistances;
			}
		}
		
		int cost = manhattanDistance + movesMadeSoFar;
		//int cost = manhattanDistance +  currentState.getNumberOfMoves();
		////int cost = manhattanDistance +  currentState.getGValue();
		return cost;
	}
	
	EightPuzzle(String pathToInputFile, String pathToGoalFile, int boardSize) {
		// This is an Eight Puzzle Problem and has a 3 X 3 board
		initialPuzzleState = readFromFile(pathToInputFile, boardSize);
		goalPuzzleState = readFromFile(pathToGoalFile, boardSize);
		////initialPuzzleState.setGValue(0);
		////initialPuzzleState.setHValue(heuristicCostEstimate(initialPuzzleState, goalPuzzleState));
		////initialPuzzleState.setFValue(initialPuzzleState.getGValue() + initialPuzzleState.getHValue());
		currentPuzzleState = initialPuzzleState;
	}
	
	public PuzzleState readFromFile(String pathToInputFile, int boardSize) {
		PuzzleState puzzleState = null;
		File inputFile = new File(pathToInputFile);
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
			/*initialPuzzleState = null;
			goalPuzzleState = null;
			currentPuzzleState = null;*/
		}
		/*initialPuzzleState = new PuzzleState(puzzleBoard, zeroNode);
		currentPuzzleState = initialPuzzleState;*/
		puzzleState = new PuzzleState(puzzleBoard, zeroNode);
		puzzleState.setSortedValues(sortedValues);
		return puzzleState;
	}
	
	// Actions are like UP, DOWN, LEFT, RIGHT
	// STATE maintains the puzzle board and where is the '0' node currently is
	public void printBoard() {
		System.out.println(currentPuzzleState);
		
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
		System.out.println("Heuristic = " + heuristicCostEstimate(initialPuzzleState, goalPuzzleState, 0));
		
		TreeNode root = new TreeNode(initialPuzzleState, 0);
		int heuristic = heuristicCostEstimate(initialPuzzleState, goalPuzzleState, root.getDepth());
		root.setH(heuristic);
		root.setF(root.getDepth() + root.getH());
		System.out.println(search(problem, root, root.getDepth(), Integer.MAX_VALUE));
		return null;
		//return search(problem, initialPuzzleState, Integer.MAX_VALUE);
	}
	
	protected TreeNode search(Problem problem, TreeNode N, int V, int B) {
		if(N.getF() > B)
			return N;
		if(problem.getGoalTest().isGoalState(N.getPuzzleState())) {
			System.out.println("Obtained Goal State");
			System.out.println(N);
			System.exit(0);
		}
		
		Set<Action> possibleActions= problem.getActionsFunction().actions(N.getPuzzleState());
		//System.out.println("Possible Actions = " + possibleActions);
		for(Action action: possibleActions) {
			PuzzleAction puzzleAction = (PuzzleAction) action;
			PuzzleState tempPuzzleState = null;
			try {
				tempPuzzleState = (PuzzleState)(problem.getResultFunction().result((PuzzleState)N.getPuzzleState().clone(), puzzleAction));
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TreeNode childNode = new TreeNode(tempPuzzleState, V+1);
			ArrayList<PuzzleAction> getActionSoFar = (ArrayList<PuzzleAction>) N.getActionsSoFar().clone();
			getActionSoFar.add(puzzleAction);
			childNode.setActionsSoFar(getActionSoFar);
			int heuristic = heuristicCostEstimate(childNode.getPuzzleState(), goalPuzzleState, childNode.getDepth());
			childNode.setH(heuristic);
			childNode.setF(childNode.getDepth() + childNode.getH());
			//System.out.println(childNode);
			N.addChild(childNode);
		}
		TreeSet<TreeNode> nodeQueue = (TreeSet<TreeNode>) N.getChildren().clone();
		//System.out.println(" Node Queue = " + nodeQueue);
		if(nodeQueue.isEmpty()) 
			return null; // failure
		while(!nodeQueue.isEmpty()) 
		{
			TreeNode best = nodeQueue.pollFirst();
			TreeNode alternative;
			if(best.getF() > B) {
				return best;
			}
			if(nodeQueue.size() >= 1) {
				alternative = nodeQueue.first();
			}
			else {
				alternative = null;
			}

			int min  = B;
			if(alternative!=null) {
				if(alternative.getF() <= B) {
					min = alternative.getF();
				}
			}
			
			TreeNode retNode = search(problem, best, best.getDepth(), min);
			if(retNode != null) {
				nodeQueue.add(retNode);
			}
		}
		return null;
	}
	
	@Override
	protected void notifyViewOfMetrics() {
		// TODO Auto-generated method stub
		
	}
	public static void main( String[] args )
    {
        EightPuzzle eightPuzzle = new EightPuzzle("src/main/java/com/vivek/puzzle/eightpuzzle/input.txt","src/main/java/com/vivek/puzzle/eightpuzzle/goal.txt", 3);
        Problem eightPuzzleProblem = eightPuzzle.formulateProblem(eightPuzzle.formulateGoal());
        eightPuzzle.search(eightPuzzleProblem);
    }
}
