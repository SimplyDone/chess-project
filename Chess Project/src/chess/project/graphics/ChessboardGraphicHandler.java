package chess.project.graphics;

import chess.project.*;

import java.awt.*;
import javax.swing.*;

public class ChessboardGraphicHandler extends JFrame {

    final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    private Container contents;
    private JButton[][] squares = new JButton[8][8];

    //current positon
    private Position position = new Position(0, 0);
    private Rectangle r = new Rectangle(10, 10);

    public ChessboardGraphicHandler(Board board) {
        super("Chess GUI Test");
        contents = getContentPane();
        contents.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

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
        updateBoard(board);
    }

    public final void updateBoard(Board b) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (b.board[i][j] != null) {
                    
                    if(b.board[i][j].isWhite()){
                        squares[i][j].setForeground(Color.RED);
                    } else {
                        squares[i][j].setForeground(Color.GREEN);
                    }
                    squares[i][j].setText(b.board[i][j].toString());
                    
                } else {
                    squares[i][j].setText("");
                }
            }
        }
    }

}
