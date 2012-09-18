
import java.util.Scanner;


public class TTTNoPruning {
    
	
	
	
	
	//char BoardTypes[] = new char[9];
	private static char Empty = ' ';
    /* Higher value than any score */
	private static int Infinity = 1000000;    
	 /* Maximum moves in a game */
	private static int Maximum_Moves = 9;

	/* Nodes searched with minimax */
	private static int Total_Nodes;                        

	
	
	private static  int WinPos[][] = {
	    { 0, 1, 2 },
	    { 3, 4, 5 },
	    { 6, 7, 8 },
	    { 0, 3, 6 },
	    { 1, 4, 7 },
	    { 2, 5, 8 },
	    { 0, 4, 8 },
	    { 2, 4, 6 }
	};

	/* Array used in heuristic formula for each move. */
	 private static int goodnessFactorState[][] = {
	    {     0,   -10,  -100, -1000 },
	    {    10,     0,     0,     0 },
	    {   100,     0,     0,     0 },
	    {  1000,     0,     0,     0 }
	};


	/* Clear the board */
	private static void Initialize(BoardTypes Board) {
	    int I;
	    for (I = 0; I < 9; I++){
	    	char c = Board.getBoard(I);
	        c = Empty;
	        Board.setBoard(I,c);
	    }
	}

	/* If the game is not over, return Empty. 
	 * If the game is a tie,
	 * return 'C'
	 * else return the winner
	 * 
	 * */
	private static char Winner(BoardTypes Board) {
	    int I;
	    for (I = 0; I < 8; I++) {
	        char probable_Winner = Board.getBoard(WinPos[I][0]);
	        if (probable_Winner != Empty &&
	            probable_Winner == Board.getBoard(WinPos[I][1]) &&
	            probable_Winner == Board.getBoard(WinPos[I][2]))
	            return probable_Winner;
	    }

	    for (I = 0; I < 9; I++)
	        if (Board.getBoard(I) == Empty)
	            return Empty;

	    return 'C';
	}

	/* Return the other player */
	private static char Other(char Player) {
	    return Player == 'X' ? 'O' : 'X';
	}

	/* Make a move on the board */
	private static void Play(BoardTypes Board, int Square, char Player) {
	    Board.setBoard(Square,Player);
	}

	/* Print the board */
	private static void Print(BoardTypes Board) {
	    int I;
	    for (I = 0; I < 9; I += 3) {
	        if (I > 0)
	            System.out.println("---+---+---\n");
	        System.out.println(" "+Board.getBoard(I)+" | "+Board.getBoard(I+1)+" | "+Board.getBoard(I+2)+" \n");
	    }
	    System.out.println("\n");
	}

	/* Return a heuristic used to determine the order in which the
	   children of a node are searched */
	private static int Evaluate(BoardTypes Board, char Player) {
	    int I;
	    int Heuristic = 0;
	    for (I = 0; I < 8; I++) {
	        int J;
	        int Players = 0, Others = 0;
	        for (J = 0; J < 3; J++) {
	            char Piece = Board.getBoard(WinPos[I][J]);
	            if (Piece == Player)
	                Players++;
	            else if (Piece == Other(Player))
	                Others++;
	        }
	        Heuristic += goodnessFactorState[Players][Others];
	    }
	    return Heuristic;
	}

	/* Return the score of the best move found for a board
	   The square to move to is returned in the string, the second value ins the comma separated returned value */
	private static String Best_MoveWithoutPruning(BoardTypes Board, char Player, int Square, int Move_Nbr,
	              int Alpha, int Beta) {
	    int Best_Square = -1;
	    int Moves = 0;
	    int I;
	    MoveTypes Move_Heuristic = new MoveTypes(9);

	    Total_Nodes++;

	    /*for(int k=0;k<9;k++){
	    	System.out.println(" debug  - " + Move_Heuristic.getHeuristic(k)+ ", " + Move_Heuristic.getSquare(k));
		}*/
	    /* Find the heuristic for each move and sort moves in descending order */
	    for (I = 0; I < 9; I++) {
	        if (Board.getBoard(I) == Empty) {
	            int Heuristic;
	            int J;
	            Play(Board, I, Player);
	            Heuristic = Evaluate(Board, Player);
	            //System.out.println(" t, " + Total_Nodes + ",h " + Heuristic);
	            //Print(Board);
	            Play(Board, I, Empty);
	            for (J = Moves-1; J >= 0 &&
	                              Move_Heuristic.getHeuristic(J) < Heuristic; J--) {
	            	//System.out.println(" debug  - "+  Move_Heuristic.getHeuristic(J+1) + "," +  Move_Heuristic.getHeuristic(J) +
	            	//		"," + Move_Heuristic.getSquare((J+1))+"," +  Move_Heuristic.getSquare(J));
	                Move_Heuristic.setHeuristic((J + 1), J);
	                
	                Move_Heuristic.setSquare((J+1),J);
	            }
	            Move_Heuristic.setHeuristic((J+1),Heuristic,0);
	            Move_Heuristic.setSquare((J+1),I,0);
	            Moves++;
	        }
	        
	        	
	    }

	    int scoreToBereturned=0;
	    int max=0;
	    int min=0;
	    int maxSq=Square;
	    int minSq=Square;
	    for (I = 0; I < Moves; I++) {
	        int Score;
	        int Sq = Move_Heuristic.getSquare(I);
	        char W;

	        /* Make a move and get its score */
	        Play(Board, Sq, Player);

	        W = Winner(Board);
	        if (W == 'X')
	            Score = (Maximum_Moves + 1) - Move_Nbr;
	        else if (W == 'O')
	            Score = Move_Nbr - (Maximum_Moves + 1);
	        else if (W == 'C')
	            Score = 0;
	        else
	            Score = Integer.parseInt(Best_MoveWithoutPruning(Board, Other(Player), Square, Move_Nbr + 1,
	                              Alpha, Beta).split(",")[0].toString());
	        
	         
	        if(I==0){
	        scoreToBereturned = Score;
	        max=Score;
	        min=Score;
	        Square=Sq;
	        maxSq= Sq;
	        }else{
	        	if(Score>max){
	        		max=Score;
	        		maxSq=Sq;
	        	}
	        	else if(Score<=min){
	        		min=Score;
	        		minSq=Sq;
	        	}
	        }
	        Play(Board, Sq, Empty);
	    }
	    if (Player == 'X')
	    	return (""+ max +","+ maxSq);
	    else
	    	return (""+ min +","+ minSq);
	    
	    
	}

	
	

	/* Have the human or the computer move */
	private static void  Move(BoardTypes Board, char Player, int Move_Nbr) {
	    int Square = 0 ;

	    //int cont =0;
	    if (Player == 'X') {
	        Total_Nodes = 0;
	        String result= Best_MoveWithoutPruning(Board, 'X', Square, Move_Nbr, -Infinity, Infinity);
	        //Describe(Integer.parseInt(result.split(",")[0].toString()));
	        System.out.println(""+Total_Nodes+" nodes examined.\n"  );
	        Square=Integer.parseInt(result.split(",")[1].toString());
	        System.out.println("debug moved to " + (Square+1));
	        Play(Board, Square, 'X');
	        System.out.println("Move #" +Move_Nbr+" - X moves to " +(Square + 1) + "\n");
	        
	    } else {
	        do {
	            System.out.println("Move #" + Move_Nbr+" - What is your move (+" +Player+" 's) : ? " );
	            Scanner in2 = new Scanner(System.in);
	            Square=in2.nextInt();
	           
	            Square--;
	        } while (Board.getBoard(Square) != ' ');
	        Play(Board, Square, 'O');
	    }
	}

	/* Play a game of tic-tac-toe */
	private static void Game() {
	    char Player;
	    char me;
	    char solution[] = new char[100];
	    BoardTypes Board = new BoardTypes();
	    int Move_Nbr = 1;

	    Initialize(Board);

	    System.out.println("\n Do you want to move first? press 1 if yes else 2 : ");
	    Scanner in2 = new Scanner(System.in);
        int cont=in2.nextInt();
	   
	    if (cont==1){
	        Player = 'O';
	        
	    }
	    else{
	        Player = 'X';
	        
	    }

	    while(Winner(Board) == ' ') {
	        Print(Board);
	        Move(Board, Player, Move_Nbr);
	        Player = Other(Player);
	        Move_Nbr++;
	    }
	    Print(Board);

	    if (Winner(Board) != 'C'){
	    	if(Winner(Board)=='O')
	    		System.out.println(" You are the winner !!!!! \n" );
	    	else
	    		System.out.println(" I win, You lose !!!!! \n" );
	    }
	    else
	        System.out.println("Match Drawn.\n");
	}
	
	  public static void main(String[] args) {
	        Scanner in = new Scanner(System.in);
	        char solution[] = new char[100];

	        
	        System.out.println("Board configuration:\n");
	        System.out.println(" 1 | 2 | 3\n");
	        System.out.println("---+---+---\n");
	        System.out.println(" 4 | 5 | 6\n");
	        System.out.println("---+---+---\n");
	        System.out.println(" 7 | 8 | 9\n");
	        System.out.println("\n");
	        System.out.println("enter position's as per with board configurations : O denotes you, X denotes machine");

	        int cont=1;
	        do {
	            Game();
	            System.out.println("\nContinue ? 1 for Yes, 2 for No \n");
	            
	            Scanner in2 = new Scanner(System.in);
	            cont=in2.nextInt();
	        } while (cont == 1);
	        System.out.println("Game ends here !!");

	  }
}




