package es.ulpgc.dis.app;

import es.ulpgc.dis.arquitecture.view.CustomDifficultDialog;

import javax.swing.*;
import java.awt.*;

public class SwingCustomDifficultDialog extends JDialog implements CustomDifficultDialog {
    private int mines;
    private int rows;
    private int columns;
    private final JTextField rowsField;
    private final JTextField minesField;
    private final JTextField columnsField;

    public SwingCustomDifficultDialog(){
        this.setSize(125, 175);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setModal(true);
        minesField = new JTextField();
        rowsField = new JTextField();
        columnsField = new JTextField();
        this.add(createContentPanel());
        this.add(accept(), BorderLayout.SOUTH);
        this.setResizable(false);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.add(createLabel("mines"));
        panel.add(minesField);
        panel.add(createLabel("rows"));
        panel.add(rowsField);
        panel.add(createLabel("columns"));
        panel.add(columnsField);
        return panel;
    }

    private static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
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
                    this.showWarning("Please, reintroduce a correct number");
                }
            }
        );
        return jButton;
    }
    @Override
    public int getMines() {
        return mines;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Custom difficult", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void showDialog() {
        this.setVisible(true);
    }
}
