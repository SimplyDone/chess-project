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
    private Board board;
   

    public ChessboardGraphicHandler(Board board) {
        super("Chess GUI Test");
        
        this.board = board;
        this.contents = getContentPane();
        this.contents.setLayout(new GridLayout(8, 8));

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {

                squares[i][j] = new TileButton();

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

    public final void updateBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.board[i][j] != null) {
                    
                    if(board.getBoard()[i][j].getColour()){
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
}
