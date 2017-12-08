package chess.project;

/** Represents a chessboard.
 *
 * @author Alex Zurad, Robbie McDonnell
 */
public class Board {
        
    private final Piece board[][]; 
    private boolean is_white_checked = false;
    private boolean is_black_checked = true;
    private boolean checkmate = false;

    public Board(){
        
        board = new Piece[8][8];
        initializeBoard();
                
        //TODO think of how we aare going to represent the various 
        //elements (pieces, board, possible moves, rules, the player,
        //the ai, ai logic, graphics, 
        
    }
    
    /** Called when a new board instance is created.
     * 
     */
    public void initializeBoard(){
        
        //black
        board[0][0] = new Rook(false, new Position(0,0));
        board[0][7] = new Rook(false, new Position(0,7));
        
        board[0][1] = new Knight(false, new Position(0,1));
        board[0][6] = new Knight(false, new Position(0,6));
        
        board[0][2] = new Bishop(false, new Position(0,2));
        board[0][5] = new Bishop(false, new Position(0,5));
        
        board[0][3] = new Queen(false, new Position(0,3));
        board[0][4] = new King(false, new Position(0,4));
        
        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn(false, new Position(1,i));
        }
        
        //white
        
        board[7][0] = new Rook(true, new Position(7,0));
        board[7][7] = new Rook(true, new Position(7,7));
        
        board[7][1] = new Knight(true, new Position(7,1));
        board[7][6] = new Knight(true, new Position(7,6));
        
        board[7][2] = new Bishop(true, new Position(7,2));
        board[7][5] = new Bishop(true, new Position(7,5));
        
        board[7][3] = new Queen(true, new Position(7,3));
        board[7][4] = new King(true, new Position(7,4));
        
        for(int i = 0; i < 8; i++){
            board[6][i] = new Pawn(true, new Position(7,i));
        }
        
        
            
    }
        
        
    
        //get_board() {
        //    return board;
        //}
        
        public boolean get_checkmate(){
            if(checkmate){
                if(is_white_checked)
                    System.out.println("black wins");
                else
                    System.out.println("white wins");

            }
            return checkmate;
        }// if bool checkmate is being implemented elsewhere return void
        
        //check_move(){
        
        //}
        
        //do_move(){
            //perform the move
        //}
        
        
    

}
