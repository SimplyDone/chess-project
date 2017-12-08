package chess.project;

/** Represents a chessboard.
 *
 * @author Alex Zurad
 */
public class Board {
    
    private int aThing = 32;
    
    private int board[][] = new int[8][8]; // change int to 'peice'
    private boolean is_white_checked = false;
    private boolean is_black_checked = true;
    private boolean checkmate = false;
    // i broke it
    public Board(){
                
        //TODO think of how we aare going to represent the various 
        //elements (pieces, board, possible moves, rules, the player,
        //the ai, ai logic, graphics, 
        
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
