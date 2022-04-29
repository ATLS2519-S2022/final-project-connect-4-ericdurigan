/**
 * A Connect-4 player that implements the Player interface
 * make the greedy player put the pieces randomly until there's 3 in a row or more, then
 * it becomes decisive by choosing the highest score option
 * 
 * @author Eric Durigan
 */
/*Consider all possible moves the AI could make
	For each move:
	Temporarily make the move using board.move()
	Calculate a score based on how the board is for you now that you've made the move
	Undo the move using board.unmove 
	Return the move that had the highest calculated score
	To calculate the score regarding how good a certain move would be, 
	you will write a simple heuristic evaluation function that returns 
	the difference between how many connect-4's you have and how many 
	connect-4's your opponent has.
	*/
	
	
public class GreedyPlayer implements Player
{
	
	
	int id;
	int cols;
	
    @Override
    public String name() {
        return "Greedy Boi";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id;
    	this.cols = cols;
    }

    @Override
    public void calcMove(
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move.
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        
        // Make a random valid move. UPDATE: Try all 7 slots 
        int col = 0;
        int highestScore;
        highestScore = -1000;
        int bestColumn = 0;
        //do { col = rand.nextInt(board.numCols());
        for (col = 0; col < board.numCols(); col++) {
        	
        	if (board.isValidMove(col)) {
        		board.move(col, id);
        		int score = calcScore(board, id);
        		if (score > highestScore) {
        			highestScore = score;
        			bestColumn = col;
        		}
        		board.unmove(col, id);
        	}
        
        }
        arb.setMove(bestColumn);
        
    }
    public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}
}

