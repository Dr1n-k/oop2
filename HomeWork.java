import javax.swing.*;
import java.awt.*;

public class HomeWork extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // 1. 天空與草地：上半部天藍色，下半部綠色
        g2.setColor(new Color(135, 206, 235)); // 天藍色
        g2.fillRect(0, 0, width, (int)(height * 0.7));
        
        g2.setColor(new Color(65, 152, 72)); // 草地綠色
        g2.fillRect(0, (int)(height * 0.7), width, (int)(height * 0.3));

        // 2. 太陽與光芒：黃色實心圓 + for 迴圈配合三角函數畫出光芒
        int sunX = 410, sunY = 90, sunRadius = 60;
        g2.setColor(Color.YELLOW);
        g2.fillOval(sunX - sunRadius / 2, sunY - sunRadius / 2, sunRadius, sunRadius);
        
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < 8; i++) {
            double angle = i * Math.PI / 4;
            int x1 = (int) (sunX + (sunRadius / 2 + 5) * Math.cos(angle));
            int y1 = (int) (sunY + (sunRadius / 2 + 5) * Math.sin(angle));
            int x2 = (int) (sunX + (sunRadius / 2 + 22) * Math.cos(angle));
            int y2 = (int) (sunY + (sunRadius / 2 + 22) * Math.sin(angle));
            g2.drawLine(x1, y1, x2, y2);
        }

        // 3. 雲朵：至少 3 個互相重疊的白色 fillOval
        g2.setColor(Color.WHITE);
        g2.fillOval(140, 100, 80, 45);
        g2.fillOval(180, 90, 95, 50);
        g2.fillOval(225, 105, 75, 40);

        // 4. 房屋主體：fillRect + drawRect
        int houseX = 200, houseY = 200, houseW = 120, houseH = 100;
        g2.setColor(new Color(255, 235, 205)); // 淺膚木色
        g2.fillRect(houseX, houseY, houseW, houseH);
        g2.setColor(Color.BLACK);
        g2.drawRect(houseX, houseY, houseW, houseH);

        // 5. 屋頂：只能用 drawLine 畫出三角形（三條線段）
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(260, 140, 180, 200); // 左斜線
        g2.drawLine(260, 140, 340, 200); // 右斜線
        g2.drawLine(180, 200, 340, 200); // 底線

        // 6. 門：fillRoundRect，門把用小的 fillOval
        int doorX = 240, doorY = 230, doorW = 40, doorH = 70;
        g2.setColor(new Color(139, 69, 19)); // 棕色
        g2.fillRoundRect(doorX, doorY, doorW, doorH, 15, 15);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(doorX, doorY, doorW, doorH, 15, 15);
        // 門把
        g2.setColor(Color.YELLOW);
        g2.fillOval(doorX + 28, doorY + 36, 6, 6);

        // 7. 窗戶：至少 2 扇，fillRect + drawRect，並用 drawLine 畫十字窗口
        drawWindow(g2, 212, 215, 32, 32);
        drawWindow(g2, 276, 215, 32, 32);

        // 8. 畫面角落簽名（學號與姓名）
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2.drawString("學號: A1143311 姓名: 林琮倫", 20, 380);
    }

    // 輔助方法：繪製帶有十字窗框的窗戶
    private void drawWindow(Graphics2D g2, int x, int y, int w, int h) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, w, h);
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(x, y, w, h);
        // 十字窗框
        g2.drawLine(x + w / 2, y, x + w / 2, y + h);
        g2.drawLine(x, y + h / 2, x + w, y + h / 2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("小房子風景畫 - HomeWork");
        HomeWork panel = new HomeWork();
        panel.setPreferredSize(new Dimension(500, 400));
        
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // 視窗置中
        frame.setVisible(true);
    }
}