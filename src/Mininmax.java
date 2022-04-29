/**
 * A Connect-4 player that uses the minimax strategy to look several steps into the future.
 * Initialize a maximum search depth to be 1
 * While there is time remaining to calculate your move and your 
 * current search depth is <= the number of moves remaining
 * Do a minimax search to the depth of your maximum search variable
 * Sets move as the best move found so far
 * Increments maximum search depth
 * 
 * @author Eric Durigan
 */

/*
	minimax(player,board)
    if(game over in current board position)
        return winner
    children = all legal moves for player from this board
    if(max's turn)
        return maximal score of calling minimax on all the children
    else (min's turn)
        return minimal score of calling minimax on all the children
*/
public class Mininmax implements Player {

	int id;
	int opp_id;
	int cols;

    @Override
    public String name() {
        return "Minimax";
    }

    @Override
    public void init(int id, int msecPerMove, int rows, int cols) {
    	this.id = id;
    	opp_id = 3-id;
    	this.cols = cols;
    }

    @Override
    public void calcMove(
        Connect4Board board, int oppMoveCol, Arbitrator arb) 
        throws TimeUpException {
        // Make sure there is room to make a move
        if (board.isFull()) {
            throw new Error ("Complaint: The board is full!");
        }
        int col = 0;
        int maxDepth = 1;
      
        
        while (!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
        	//run minimax
        	  int highestScore = -1000;
              int bestColumn = -1000;
        	
        	 for (col = 0; col < board.numCols(); col++) {
             	
             	if (board.isValidMove(col)) {
             		board.move(col, id);
             		int score = minimax(board, maxDepth - 1, false, arb);
             		if (score > highestScore) {
             			highestScore = score;
             			bestColumn = col;
             		}
             		board.unmove(col, id);
             	}
        	 }
        	
        	//run the first level of minimax, and update best move as youre updating best score
        	arb.setMove(bestColumn);
        	maxDepth++;
        
        }
    }

    public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb) {
				
    	if (depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board,id)-calcScore(board,3-id);
    	}
    	
    	int highestScore;
    	int bestScore = 0;
    	
    	if(isMaximizing) {
    		highestScore = -1000;
    		for(int col = 0; col < board.numCols(); col++) {
    			if (board.isValidMove(col)){
	    			board.move(col, id);
	    			int currentScore = minimax(board, depth-1, false, arb);
	    			bestScore = Math.max(highestScore, currentScore);
	    			board.unmove(col, id);
    			}
    		}
    		return bestScore;
    	}	
    			
    	else { 
    		highestScore = 1000;
    		for(int col = 0; col < board.numCols(); col++) {
    			if (board.isValidMove(col)){
    			board.move(col, opp_id);
    			int currentScore = minimax(board, depth-1, true, arb);
    			bestScore = Math.min(highestScore, currentScore);
    			board.unmove(col, opp_id);
    			}
    		}
    		return bestScore;
    	}
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
