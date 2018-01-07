package chess.project.graphics;

import chess.project.*;
import chess.project.movement.Position;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ChessboardGraphicHandler extends JFrame {

    private final Container allContents;
    private static JTextArea infoContents;
    private final Container boardContents;
    private final static JToggleButton[][] squares = new JToggleButton[8][8];

    private final Board board;

    public ChessboardGraphicHandler(Board board) {
        super("Chess GUI Test");

        this.board = board;
        
        allContents = getContentPane();
        allContents.setLayout(new BorderLayout());
        
        boardContents = new JPanel();
        boardContents.setLayout(new GridLayout(8, 8));
        boardContents.setPreferredSize(new Dimension(512,512));
        
        infoContents = new JTextArea("A wild thing has appeared!");
        infoContents.setPreferredSize(new Dimension(256, 512));
        infoContents.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < 8; i++) {

                squares[i][j] = new TileButton(new Position(i, j), board);

                //squares[i][j].setRolloverEnabled(false);
                if ((i + j) % 2 != 0) {
                    squares[i][j].setBackground(Color.black);
                } else {
                    squares[i][j].setBackground(Color.white);
                }


                boardContents.add(squares[i][j]);
                //add action listenser to squares    
            }
        }
        
        allContents.add(boardContents, BorderLayout.CENTER);
        allContents.add(infoContents, BorderLayout.LINE_END);

        
        this.pack();
        //setSize(512, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateBoard();
    }

    public final void updateBoard() {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] != null) {

                    if (board.getBoard()[i][j].getColour() == ChessColour.WHITE) {
                        squares[i][j].setForeground(Color.RED);
                    } else {
                        squares[i][j].setForeground(Color.GREEN);
                    }
                    squares[i][j].setText(board.getBoard()[i][j].toString());

                } else {
                    squares[i][j].setText("");
                }
            }
        }
    }
    
    public static void updateText(String msg){
        infoContents.setText(msg);
    }
}
