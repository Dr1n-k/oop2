import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dice extends JFrame {
    private JLabel diceLabel;
    private JLabel statusLabel;
    private JButton rollButton;

    private int rollCount = 0;
    private int totalSum = 0;

    public Dice() {
        // 視窗基本設定
        setTitle("骰子模擬器");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 視窗置中
        setLayout(new BorderLayout(10, 10));

        // 1. NORTH: 顯示「已擲 N 次，總和 M，平均 X.XX」
        statusLabel = new JLabel("已擲 0 次，總和 0，平均 0.00", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        add(statusLabel, BorderLayout.NORTH);

        // 2. CENTER: 顯示目前點數，字體 60pt
        diceLabel = new JLabel("-", SwingConstants.CENTER);
        diceLabel.setFont(new Font("SansSerif", Font.BOLD, 60));
        add(diceLabel, BorderLayout.CENTER);

        // 3. SOUTH: 「擲骰子」按鈕
        rollButton = new JButton("擲骰子");
        rollButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        southPanel.add(rollButton);
        add(southPanel, BorderLayout.SOUTH);

        // 監聽器：點擊按鈕後隨機產生 1-6 的點數並更新顯示
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollDice();
            }
        });
    }

    private void rollDice() {
        // 隨機產生 1 至 6 的點數
        int diceValue = (int) (Math.random() * 6) + 1;

        // 更新統計數據
        rollCount++;
        totalSum += diceValue;
        double average = (double) totalSum / rollCount;

        // 更新點數 JLabel 與顏色
        diceLabel.setText(String.valueOf(diceValue));
        if (diceValue == 6) {
            diceLabel.setForeground(new Color(0, 150, 0)); // 6 時變綠色
        } else if (diceValue == 1) {
            diceLabel.setForeground(Color.RED); // 1 時變紅色
        } else {
            diceLabel.setForeground(Color.BLACK); // 其餘黑色
        }

        // 更新上方狀態 JLabel
        statusLabel.setText(String.format("已擲 %d 次，總和 %d，平均 %.2f", rollCount, totalSum, average));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Dice().setVisible(true);
        });
    }
}