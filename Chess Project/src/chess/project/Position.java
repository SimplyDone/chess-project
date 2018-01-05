package chess.project;

import java.io.Serializable;

/**
 * Represents a position of a piece. X -> (a-h) Y -> (1-8)
 *
 * @author Alex Zurad
 */
public class Position implements Serializable {

    private int X;
    private int Y;

    public Position(int X, int Y) {

        if (X > 7 || Y > 7) {
            throw new UnsupportedOperationException("X and Y must be between 0 and 7 X=" + X + " Y=" + Y);
        }

        this.X = X;
        this.Y = Y;
    }
    

    public void setPosition(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    @Override
    public String toString() {
        return "(" + (Character.toString((char)(X + 97))) + "," + ( Math.abs(Y-8) ) + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.X;
        hash = 37 * hash + this.Y;
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
        final Position other = (Position) obj;
        if (this.X != other.X) {
            return false;
        }
        if (this.Y != other.Y) {
            return false;
        }
        return true;
    }

}
