import javax.swing.*;
import java.awt.*;

public class Exercise1_2{
    public static void main(String[] args) {
        JFrame frame = new JFrame("骰子模擬器");
        frame.setSize(400, 320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // 修正處：將 layout 改為 setLayout
        frame.setLayout(new BorderLayout());
        
        JLabel label = new JLabel("?");
        label.setFont(new Font("Arial", Font.PLAIN, 60));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(label, BorderLayout.CENTER);
        
        JButton button = new JButton("擲骰子");
        frame.add(button, BorderLayout.SOUTH);
        
        frame.setVisible(true);
    }
}