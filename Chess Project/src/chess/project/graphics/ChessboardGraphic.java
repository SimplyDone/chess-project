package chess.project.graphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessboardGraphic extends JPanel{
    
    public void paint(Graphics g){
        
        g.fillRect(0, 0, 512, 512);
        
        for(int i = 0; i <= 512; i+=128){
            for(int j = 0; j <= 512; j+=128){
                g.clearRect(i, j, 64, 64);
            }
        }
        
        for(int i = 64; i <= 512; i+=128){
            for(int j = 64; j <= 512; j+=128){
                g.clearRect(i, j, 64, 64);
            }
        }
    }
 
    public static void drawChessboard(){
        JFrame frame = new JFrame();
        frame.setSize(518,540);
        frame.getContentPane().add(new ChessboardGraphic());
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }  
}