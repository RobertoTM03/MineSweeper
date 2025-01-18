package es.ulpgc.dis.arquitecture.view;

import javax.swing.*;
import java.awt.*;

public class CustomDifficultDialog extends JDialog {
    private int mines;
    private int rows;
    private int columns;
    private final JTextField rowsField;
    private final JTextField minesField;
    private final JTextField columnsField;

    public CustomDifficultDialog(){
        this.setSize(300, 200);
        this.setLayout(new GridLayout(4, 2, 5, 5));
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.add(new JLabel("mines"));
        this.add(minesField = new JTextField());
        this.add(new JLabel("rows"));
        this.add(rowsField = new JTextField());
        this.add(new JLabel("columns"));
        this.add(columnsField = new JTextField());
        this.add(accept());
    }

    private Component accept() {
        JButton jButton = new JButton();
        jButton.setText("Accept");
        jButton.addActionListener(
            _ -> {
                try {
                    mines = Integer.parseInt(minesField.getText());
                    rows = Integer.parseInt(rowsField.getText());
                    columns = Integer.parseInt(columnsField.getText());
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CustomDifficultDialog.this,
                            "Por favor, introduce números válidos.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        );
        return jButton;
    }

    public int mines(){return mines;}
    public int rows(){return rows;}
    public int columns(){return columns;}
}
