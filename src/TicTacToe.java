import java.util.Scanner;

public class TicTacToe {
    
    public static final char SPACE = ' ', O='O';
   
    public static final char[][] MOVE_INFO = {{'1', '2', '3'},
                                              {'4', '5', '6'},
                                              {'7', '8', '9'}};

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        char[][] board = new char[3][3];
        char currPlayer = 'X';
        int currMove = 0;
        boolean errorCheck;

        fill2D(board, SPACE);
        printBoard(MOVE_INFO);
        
        while (!gameOver(board)) {
        	// Keeps input from going out of bounds
        	do {
        		errorCheck = true;
        		System.out.print("Move for " + currPlayer + "? ");
            	currMove = in.nextInt();
            	if (currMove < 0 || currMove > 9){
            		errorCheck = false;
            		System.out.println("That position does not exist, please try again.");
            	}
        	} while(errorCheck == false);
        	
            makeMove(currPlayer, currMove, board);
            setO(currPlayer, currMove, board);
            printBoard(board);
            
        }
        reportResults(board);
    }

    /**
     * Fill a given two-dimensional character array with a given
     * character
     *
     * @param a  the array to fill
     * @param c  the character to use
     */
    public static void fill2D(char[][] a, char c) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                a[i][j] = c;
            }
        }
    }

    public static final String VERT = "   *   *   ";
    public static final String HORZ = "***********";

    /**
     * Print the contents of an array representing a tic-tac-toe
     * board
     *
     * @param board  the board configuration to be printed
     */
    public static void printBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.println(VERT);
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + board[i][j] + " ");
                if (j < board[i].length - 1) {
                    System.out.print("*");
                } else {
                    System.out.println();
                }
            }
            System.out.println(VERT);
            if (i < board.length - 1) {
                System.out.println(HORZ);
            }
        }
        System.out.println();
    }

    /**
     * Determine if the game is over; i.e., if a player has won or if
     * there are no more moves possible
     *
     * @param board  current board configuration
     * @return  true if game is over, false otherwise
     */
    public static boolean gameOver(char[][] board) {
        return noMovesLeft(board)
                || isWinner('X', board)
                || isWinner('O', board);
    }

    /**
     * Determine if no more moves are possible; i.e., if there
     * are no more SPACE's left in the board array
     *
     * @param board  the current board configuration
     * @return  true if there are no more legal moves, false otherwise
     */
    public static boolean noMovesLeft(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == SPACE) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determine if a given player is a winner; i.e., has 3 marks in a
     * row, either horizontally, vertically, or diagonally
     *
     * @param player  the player to check
     * @param board   the current board configuration
     * @return  true if player has 3 marks in a row, false otherwise
     */
    public static boolean isWinner(char player, char[][] board) {
        // check rows (horizontal)
        for (int row = 0; row < 3; row++) {
            if (board[row][0] == player && board[row][1] == player
                    && board[row][2] == player) {
                return true;
            }
        }
        // check cols (vertical)
        for (int col = 0; col < 3; col++) {
            if (board[0][col] == player && board[1][col] == player
                    && board[2][col] == player) {
                return true;
            }
        }
        // check diagonals
        if (board[0][0] == player && board[1][1] == player
                && board[2][2] == player) {
            return true;
        } else if (board[0][2] == player && board[1][1] == player
                && board[2][0] == player) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mark the specified move for the specified player on the given
     * board configuration (move == 1 => [0][0], move == 2 => [0][1],
     * move == 3 => [0][2], move == 4 => [1][0], move == 5 => [1][1],
     * move == 6 => [1][2], move == 7 => [2][0], move == 8 => [2][1],
     * move == 9 => [2][2])
     *
     * @param player  the player making the move
     * @param move    the move to be made
     * @param board   the current board configuration
     */ 
    public static void makeMove(char player, int move, char[][] board) {
        int row = (move - 1) / board.length;
        int col = (move - 1) % board[row].length;
        board[row][col] = player;
    }
    
    /**
     * Make a random move for O
     *
     * @param player  the computer making move
     * @param move    the random generated move for computer to make
     * @param board   the current board configuration
     */
	public static void setO(char player, int move, char[][] board){
		int row = (int)(Math.random() * 3) +1 / board.length;
    	int col = (int)(Math.random() * 3) +1 % board[row].length;
   		board[row][col] = O;
   	}
   	
    /**
     * Report the results of the game, whether X or O won or the game
     * ended in a tie.
     *
     * @param board  the board configuration to be described
     */
    public static void reportResults(char[][] board) {
        if (isWinner('O', board)) {
            System.out.println("O is the winner!");
        } else if (isWinner('X', board)) {
            System.out.println("X is the winner!");
        } else {
            System.out.println("The game ends in a tie.");
        }
    }

}

