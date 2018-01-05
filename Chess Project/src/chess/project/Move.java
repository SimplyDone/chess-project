package chess.project;

import java.io.Serializable;
import java.util.Objects;

/** This class represents a move between two positions.
 *
 * @author Alex Zurad
 */
public class Move implements Serializable {
    
    private Position oldPos;
    private Position newPos;
    
    public Move(Position oldPos, Position newPos){
        this.oldPos = oldPos;
        this.newPos = newPos;
    }

    public Position getOldPosition() {
        return oldPos;
    }

    public Position getNewPosition() {
        return newPos;
    }
    
    @Override
    public String toString(){
        return oldPos + " -> " + newPos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.oldPos);
        hash = 53 * hash + Objects.hashCode(this.newPos);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Move other = (Move) obj;
        if (!Objects.equals(this.oldPos, other.oldPos)) {
            return false;
        }
        if (!Objects.equals(this.newPos, other.newPos)) {
            return false;
        }
        return true;
    }
 
}
