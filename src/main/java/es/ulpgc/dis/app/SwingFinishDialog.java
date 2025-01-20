package es.ulpgc.dis.app;

import es.ulpgc.dis.arquitecture.view.FinishDialog;

import javax.swing.*;
import java.awt.*;

public class SwingFinishDialog extends JDialog implements FinishDialog {

    private final MainFrame mainFrame;
    private final JLabel finishLabel;

    public SwingFinishDialog(MainFrame mainFrame){
        super(mainFrame, "FinishGame", false);
        this.mainFrame = mainFrame;
        this.setSize(200, 100);
        this.setLayout(new BorderLayout());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        finishLabel = new JLabel("Finish message", SwingConstants.CENTER);
        this.add(finishLabel);
        JButton restartButton = (JButton) mainFrame.getRestartButton();
        restartButton.addActionListener(_ -> this.dispose());
        this.add(restartButton, BorderLayout.SOUTH);
        this.setLocationRelativeTo(mainFrame);

    }

    @Override
    public void lose() {
        String result = "You Lose!";
        this.finishLabel.setText(result);
        this.setVisible(true);
    }

    @Override
    public void win() {
        String result = "You win in " + mainFrame.time() + " !";
        finishLabel.setText(result);
        this.setVisible(true);
    }
}
