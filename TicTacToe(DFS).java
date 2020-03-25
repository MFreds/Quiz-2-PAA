import java.util.Scanner;

public class TicTacToe {

	public char[][] board;
	public boolean xTurn;

	public int aiTurn;
  
  // 2.
	public static void printBoard(char[][] board) {
		System.out.println();
		for (int i = 0; i < board.length; i++) {
			System.out.print("   " + board[i][0]);
			for (int a = 1; a < board[i].length; a++)
				System.out.print("|" + board[i][a]);
			System.out.println();
			System.out.print("   ");
			if (i < board.length - 1)
				System.out.print("-----");
			System.out.println(); 
		}
		System.out.println();
	}

  // 5.
	public int gameResult(char[][] board) {

		char color;
		int consecutive;

		// first loop
		for (int i = 0; i < board.length; i++) {
			consecutive = 0;
			color = '?';
			for (int a = 0; a < board[i].length; a++)
				if (board[i][a] == color)
					consecutive++;
				else if (board[i][a] == 'X' || board[i][a] == 'O') {
					consecutive = 1;
					color = board[i][a];
				}
			if (consecutive == 3)
				return color == 'X' ? 1:-1;
		}

		// second loop
		for (int a = 0; a < board[0].length; a++) {
			consecutive = 0;
			color = '?';
			for (int i = 0; i < board.length; i++)
				if (board[i][a] == color)
					consecutive++;
				else if (board[i][a] == 'X' || board[i][a] == 'O') {
					consecutive = 1;
					color = board[i][a];
				}
			if (consecutive == 3)
				return color == 'X' ? 1:-1;
		}

		// third loop
		consecutive = 0;
		color = '?';
		for (int i = 0, a = 0; i < board.length; i++, a++)
			if (board[i][a] == color)
				consecutive++;
			else if (board[i][a] == 'X' || board[i][a] == 'O') {
				consecutive = 1;
				color = board[i][a];
			}
		if (consecutive == 3)
			return color == 'X' ? 1:-1;

		// fourth loop
		consecutive = 0;
		color = '?';
		for (int i = board.length - 1, a = 0; i >= 0; i--, a++)
			if (board[i][a] == color)
				consecutive++;
			else if (board[i][a] == 'X' || board[i][a] == 'O') {
				consecutive = 1;
				color = board[i][a];
			}
		if (consecutive == 3)
			return color == 'X' ? 1:-1;

		return 0;
	}

  // 9.
	public void playMoveAI() {
		char[][] boardCopy = new char[board.length][board[0].length];
		for (int i = 0; i < board.length; i++)
			for (int a = 0; a < board[i].length; a++)
				boardCopy[i][a] = board[i][a];

		int[] aiAnalysis = alternateFindBestMove(boardCopy, xTurn);
		board[aiAnalysis[1]][aiAnalysis[2]] = xTurn ? 'X':'O';
	}

  // 10.
	public int[][] possibleMoves(char[][] board) {
		int numPossibleMoves = 0;
		for (int i = 0; i < board.length; i++)
			for (int a = 0; a < board[i].length; a++)
				if (board[i][a] == ' ')
					numPossibleMoves++;

		int[][] possibleMoves = new int[numPossibleMoves][2];
		for (int i = 0; i < board.length; i++)
			for (int a = 0; a < board[i].length; a++)
				if (board[i][a] == ' ') {
					possibleMoves[numPossibleMoves-1][0] = i;
					possibleMoves[numPossibleMoves-1][1] = a;
					numPossibleMoves--;
				}
		return possibleMoves;
	}

  // 11.
	public int[] findBestMove(char[][] board, boolean xTurn) {

		if (gameOver(board))
			return new int[] {gameResult(board), -1, -1};

		int[][] possibleMoves = possibleMoves(board);

		int bestX = possibleMoves[0][0], bestY = possibleMoves[0][1], result = xTurn ? -1:1;

		for (int i = 0; i < possibleMoves.length; i++) {
			board[possibleMoves[i][0]][possibleMoves[i][1]] = xTurn ? 'X':'O';
			int tempResult = findBestMove(board, !xTurn)[0];
			board[possibleMoves[i][0]][possibleMoves[i][1]] = ' ';

			if ((xTurn && tempResult > result) || (!xTurn && tempResult < result)) {
				bestX = possibleMoves[i][0];
				bestY = possibleMoves[i][1];
				result = tempResult;
			}
			else if (tempResult == result && Math.random() > 1f / possibleMoves.length) {
				bestX = possibleMoves[i][0];
				bestY = possibleMoves[i][1];
				result = tempResult;
			}
		}

		return new int[] {result, bestX, bestY};
	}

}
