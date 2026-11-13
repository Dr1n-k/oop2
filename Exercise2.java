import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exercise2 extends JFrame {
    // 元件宣告
    private JComboBox<String> categoryComboBox;
    private JTextField sourceField;
    private JComboBox<String> sourceUnitComboBox;
    private JTextField targetField;
    private JComboBox<String> targetUnitComboBox;
    private JButton convertButton;

    // 單位選項陣列
    private final String[] categories = {"長度", "重量", "溫度"};
    private final String[] lengthUnits = {"公尺", "公分", "英吋", "英尺"};
    private final String[] weightUnits = {"公斤", "公克", "磅", "盎司"};
    private final String[] tempUnits = {"攝氏", "華氏", "克氏"};

    public Exercise2() {
        // 視窗基本設定
        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 視窗置中
        setLayout(new BorderLayout(10, 10));

        // 1. NORTH: 選擇換算類型的 JComboBox
        categoryComboBox = new JComboBox<>(categories);
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        northPanel.add(new JLabel("換算類型："));
        northPanel.add(categoryComboBox);
        add(northPanel, BorderLayout.NORTH);

        // 2. CENTER: 兩列，每列一個 JTextField + 一個 JComboBox
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        // 第一列：來源數值與單位
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        sourceField = new JTextField(12);
        sourceUnitComboBox = new JComboBox<>(lengthUnits);
        row1.add(new JLabel("來源："));
        row1.add(sourceField);
        row1.add(sourceUnitComboBox);

        // 第二列：目標數值與單位
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        targetField = new JTextField(12);
        targetField.setEditable(false); // 結果欄位設為唯讀
        targetUnitComboBox = new JComboBox<>(lengthUnits);
        row2.add(new JLabel("目標："));
        row2.add(targetField);
        row2.add(targetUnitComboBox);

        centerPanel.add(row1);
        centerPanel.add(row2);
        add(centerPanel, BorderLayout.CENTER);

        // 3. SOUTH: 「換算」按鈕
        convertButton = new JButton("換算");
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        southPanel.add(convertButton);
        add(southPanel, BorderLayout.SOUTH);

        // 監聽器 1：切換換算類型時，更新兩個單位 JComboBox 的選項
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUnitComboBoxes();
            }
        });

        // 監聽器 2：點擊「換算」按鈕執行計算
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });
    }

    // 根據選擇的類型更新單位下拉選單
    private void updateUnitComboBoxes() {
        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        String[] unitsToSet;

        switch (selectedCategory) {
            case "重量":
                unitsToSet = weightUnits;
                break;
            case "溫度":
                unitsToSet = tempUnits;
                break;
            case "長度":
            default:
                unitsToSet = lengthUnits;
                break;
        }

        // 更新下拉選單內容並清空先前的輸入與結果
        sourceUnitComboBox.setModel(new DefaultComboBoxModel<>(unitsToSet));
        targetUnitComboBox.setModel(new DefaultComboBoxModel<>(unitsToSet));
        sourceField.setText("");
        targetField.setText("");
    }

    // 執行單位換算邏輯
    private void performConversion() {
        try {
            double inputValue = Double.parseDouble(sourceField.getText().trim());
            String category = (String) categoryComboBox.getSelectedItem();
            String fromUnit = (String) sourceUnitComboBox.getSelectedItem();
            String toUnit = (String) targetUnitComboBox.getSelectedItem();
            double result = 0.0;

            if ("長度".equals(category)) {
                result = convertLength(inputValue, fromUnit, toUnit);
            } else if ("重量".equals(category)) {
                result = convertWeight(inputValue, fromUnit, toUnit);
            } else if ("溫度".equals(category)) {
                result = convertTemperature(inputValue, fromUnit, toUnit);
            }

            // 格式化輸出至小數點後四位（去除多餘的尾數零）
            targetField.setText(String.format("%.4f", result).replaceAll("0*$", "").replaceAll("\\.$", ""));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "請輸入有效的數字！", "輸入錯誤", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 長度換算（以「公尺」為基準單位）
    private double convertLength(double value, String from, String to) {
        double inMeters = switch (from) {
            case "公分" -> value * 0.01;
            case "英吋" -> value * 0.0254;
            case "英尺" -> value * 0.3048;
            default -> value; // 公尺
        };

        return switch (to) {
            case "公分" -> inMeters / 0.01;
            case "英吋" -> inMeters / 0.0254;
            case "英尺" -> inMeters / 0.3048;
            default -> inMeters; // 公尺
        };
    }

    // 重量換算（以「公斤」為基準單位）
    private double convertWeight(double value, String from, String to) {
        double inKg = switch (from) {
            case "公克" -> value * 0.001;
            case "磅" -> value * 0.45359237;
            case "盎司" -> value * 0.02834952;
            default -> value; // 公斤
        };

        return switch (to) {
            case "公克" -> inKg / 0.001;
            case "磅" -> inKg / 0.45359237;
            case "盎司" -> inKg / 0.02834952;
            default -> inKg; // 公斤
        };
    }

    // 溫度換算（以「攝氏」為基準單位）
    private double convertTemperature(double value, String from, String to) {
        double inCelsius = switch (from) {
            case "華氏" -> (value - 32) * 5.0 / 9.0;
            case "克氏" -> value - 273.15;
            default -> value; // 攝氏
        };

        return switch (to) {
            case "華氏" -> (inCelsius * 9.0 / 5.0) + 32;
            case "克氏" -> inCelsius + 273.15;
            default -> inCelsius; // 攝氏
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Exercise2().setVisible(true);
        });
    }
}