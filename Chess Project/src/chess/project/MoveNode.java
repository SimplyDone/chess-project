package chess.project;

import chess.project.movement.Move;
import java.util.List;

/**
 *
 * @author Alex Zurad
 */
public class MoveNode {
    
    public Move m;
    public List<MoveNode> n;
    
    public MoveNode(Move m, List n){
        this.m = m;
        this.n = n;
    }
}
