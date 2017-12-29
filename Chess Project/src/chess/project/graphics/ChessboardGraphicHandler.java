package chess.project.graphics;

import chess.project.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChessboardGraphicHandler extends JFrame {

    private Container contents;
    private static JToggleButton[][] squares = new JToggleButton[8][8];

    //current positon
    public static Position position;
    private static Board board;
   

    public ChessboardGraphicHandler(Board board) {
        super("Chess GUI Test");
        
        this.board = board;
        position = null;
        contents = getContentPane();
        contents.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                squares[i][j] = new TileButton(new Position(i,j));
                squares[i][j].addActionListener((ActionEvent ae) -> {
                    
                    System.out.println();
                });
                //squares[i][j].setRolloverEnabled(false);
                if ((i + j) % 2 != 0) {
                    squares[i][j].setBackground(Color.black);
                } else {
                    squares[i][j].setBackground(Color.white);
                }
                contents.add(squares[i][j]);
                //add action listenser to squares    
            }
        }

        setSize(512, 512);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateBoard();
    }

    public static final void updateBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null) {
                    
                    if(board.board[i][j].isWhite()){
                        squares[i][j].setForeground(Color.RED);
                    } else {
                        squares[i][j].setForeground(Color.GREEN);
                    }
                    squares[i][j].setText(board.board[i][j].toString());
                    
                } else {
                    squares[i][j].setText("");
                }
            }
        }
    }
    
    public static void doMove(Move m){
        board.doMove(m);
        updateBoard();
    }

}
