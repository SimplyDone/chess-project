package chess.project;

/** This represents the colour of a piece or player. 
 *
 * @author Alex Zurad
 */
public enum ChessColour {

    WHITE("white"), BLACK("black");
    private final String colour;

    private ChessColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return colour;
    }
    
    public static ChessColour opposite(ChessColour c){
        return c == WHITE ? BLACK : WHITE;
    }

}
