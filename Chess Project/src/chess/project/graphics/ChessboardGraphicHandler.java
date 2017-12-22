package chess.project.graphics;

import chess.project.Position;
import java.awt.*;
import javax.swing.*;

public class ChessboardGraphicHandler extends JFrame {

    private Container contents;

    private JButton[][] squares = new JButton[8][8];

    //current positon
    private Position position = new Position(0, 0);

    private Rectangle r = new Rectangle(10, 10);

    public ChessboardGraphicHandler() {
        super("Chess GUI Test");
        contents = getContentPane();
        contents.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                squares[i][j] = new JButton();
                if ((i + j) % 2 != 0) {
                    squares[i][j].setBackground(Color.black);
                }
                contents.add(squares[i][j]);
                //add action listenser to squares
                
            }
        }

        setSize(512,512);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
