package chess.project;

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
