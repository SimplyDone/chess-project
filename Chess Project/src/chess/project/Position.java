
package chess.project;

/** Represents a position of a piece. 
 * X -> (a-h)
 * Y -> (1-8)
 *
 * @author Alex Zurad
 */
public class Position {
    
    private int X;
    private int Y;

    
    //TODO make it work with X and Y swapped
    public Position(int X, int Y){
        
        if(X > 7 || Y > 7){
            throw new UnsupportedOperationException("X and Y must be between 0 and 7 X=" + X + " Y=" + Y);
        }
        
        
        this.X = X;
        this.Y = Y;
    }
    
    public void setPosition(int X, int Y){
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
