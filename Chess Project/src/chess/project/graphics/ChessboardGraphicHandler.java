package chess.project.graphics;

import chess.project.*;
import chess.project.movement.Position;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Alex Zurad, Robbie McDonnell
 */

public class ChessboardGraphicHandler extends JFrame {

    private final Container allContents;
    private final Container sideContents;
    private static JTextArea moveContents;
    private final JTextArea infoContents;
    private final Container boardContents;
    private final static JButton[][] squares = new JButton[9][9];

    private final Board board;

    public ChessboardGraphicHandler(Board board) {
        super("Chess GUI Test");

        this.board = board;

        allContents = getContentPane();
        allContents.setLayout(new BorderLayout(15,15));

        boardContents = new JPanel();
        boardContents.setLayout(new GridLayout(9, 9));
        boardContents.setPreferredSize(new Dimension(576, 576));
        
        sideContents = new JPanel();
        sideContents.setLayout(new BorderLayout(0,0));
        sideContents.setPreferredSize(new Dimension(256, 576));

        moveContents = new JTextArea("Click on a piece to display all"
                + "\nvalid moves.");
        moveContents.setPreferredSize(new Dimension(256, 432));
        moveContents.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        infoContents = new JTextArea("Current Turn: 0");
        infoContents.setPreferredSize(new Dimension(256, 144));
        infoContents.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 9; i++) {

                if (i == 0 && j == 8) {
                    
                    JTextField letter = new LetterTextField();
                    boardContents.add(letter);

                } else if (j == 8) {
                    
                    JTextField letter = new LetterTextField(
                            Character.toString((char) ((
                                    new Position(i-1, j)).getX() + 97)));
                    boardContents.add(letter);

                } else if (i == 0) {

                     JTextField letter = new LetterTextField(
                             String.valueOf(Math.abs((
                                     new Position(i-1, j)).getY() - 8)));
                     boardContents.add(letter);

                } else {

                    squares[i - 1][j] = new TileButton(
                            new Position(i - 1, j), board);

                    //squares[i][j].setRolloverEnabled(false);
                    if ((i + j) % 2 != 0) {
                        squares[i - 1][j].setBackground(Color.black);
                    } else {
                        squares[i - 1][j].setBackground(Color.white);
                    }
                    
                    boardContents.add(squares[i- 1][j]);

                }

                
            }
        }
        sideContents.add(moveContents, BorderLayout.PAGE_START);
        sideContents.add(infoContents, BorderLayout.PAGE_END);
        sideContents.setBackground(Color.white);
        
        allContents.setBackground(Color.white);
        allContents.add(boardContents, BorderLayout.CENTER);
        allContents.add(sideContents, BorderLayout.LINE_END);

        this.pack();
        //setSize(512, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        updateBoard();
    }

    public final void updateBoard() {
        
        infoContents.setText("Current Turn: " + board.getTurn() +
                "\nTurn Number: " + board.getTurnNumber() +
                "\nAuto Stalement in: " + board.getTurnTakenNumber());

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board.getBoard()[i][j] != null) {

                    if (board.getBoard()[i][j].getColour()
                            == ChessColour.WHITE) {
                        squares[i][j].setForeground(Color.GREEN);
                    } else {
                        squares[i][j].setForeground(Color.RED);
                    }
                    squares[i][j].setText(board.getBoard()[i][j].toString());

                } else {
                    squares[i][j].setText("");
                }
            }
        }
    }

    public static void updateText(String msg) {
        moveContents.setText(msg);
    }
}
