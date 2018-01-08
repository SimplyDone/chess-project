package chess.project.graphics;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 *
 * @author Alex Zurad
 */
public class LetterTextField extends JTextField {
    
    public LetterTextField(){
        this(null);
    }

    public LetterTextField(String name) {
        super(name);
        setFont(new Font("Arial", Font.PLAIN, 40));
        setHorizontalAlignment(JTextField.CENTER);
        setBackground(Color.ORANGE);
        setBorder(new LineBorder(Color.DARK_GRAY));
        setHighlighter(null);
        setEditable(false);

    }
}
