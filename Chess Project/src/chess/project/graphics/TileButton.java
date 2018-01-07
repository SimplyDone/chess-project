package chess.project.graphics;

import chess.project.movement.Move;
import chess.project.movement.Position;
import chess.project.pieces.Piece;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 *
 * @author Alex Zurad
 */
public class TileButton extends JToggleButton {

    private Color hoverBackgroundColor = Color.cyan;
    private Color pressedBackgroundColor = Color.BLUE;

    public TileButton(Position p) {
        this(null, p);
    }

    public TileButton(String text, Position p) {
        super(text);
        super.setContentAreaFilled(false);
        setRolloverEnabled(false);
        setBorder(new LineBorder(Color.DARK_GRAY));
        setFont(new Font("Arial", Font.PLAIN, 40));
        setFocusPainted(false);

        addActionListener((ActionEvent ae) -> {
            System.out.println(p);
        });

    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            //g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }

}
