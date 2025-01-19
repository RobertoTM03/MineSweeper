package es.ulpgc.dis.app;

import es.ulpgc.dis.arquitecture.control.Command;
import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.view.BoardDisplay;
import es.ulpgc.dis.arquitecture.view.Observer;
import es.ulpgc.dis.arquitecture.view.SelectDifficultDialog;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame implements Observer {
    private final Map<String, Command> commands;
    private SelectDifficultDialog dialog;
    private final BoardDisplay boardDisplay;
    private Timer timer;
    private JLabel timeLabel;
    private JLabel minesLabel;
    private Component restart;

    public MainFrame() {
        setTitle("MineSweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);

        boardDisplay = createNullDisplay();

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, (Component) boardDisplay);
        add(BorderLayout.NORTH, toolbar());
        commands = new HashMap<>();
    }

    public MainFrame addCommand(String name, Command command){
        commands.put(name, command);
        return this;
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(minesCounter());
        panel.add(difficultyCombo());
        restart = restartButton();
        panel.add(restartButton());
        panel.add(timeDisplay());
        return panel;
    }

    private Component difficultyCombo() {
        JComboBox<String> combo = new JComboBox<>();
        combo.addItem("Easy");
        combo.addItem("Medium");
        combo.addItem("Difficult");
        combo.addItem("Custom");
        combo.addActionListener(
                _ -> commands.get("selectDifficult").execute()
        );
        dialog = combo::getSelectedIndex;
        return combo;
    }

    private int secondsElapsed = 0;
    private Component timeDisplay() {
        timeLabel = new JLabel();
        timer = new Timer(1000, _ -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
        });
        timer.start();
        return timeLabel;
    }

    private Component restartButton() {
        JButton jButton = new JButton();
        jButton.setText("Restart");
        jButton.addActionListener(_ -> commands.get("restartGame").execute());
        return jButton;
    }

    private Component minesCounter() {
        minesLabel = new JLabel();
        return minesLabel;
    }

    private SwingBoardDisplayPanel createNullDisplay() {
        return new SwingBoardDisplayPanel();
    }

    @Override
    public void update(MineSweeperGame mineSweeperGame) {
        minesLabel.setText("" + mineSweeperGame.getRemainingMines());
        if (mineSweeperGame.getGameStatus() != MineSweeperGame.GameStatus.Progress) {
            timer.stop();
            commands.get("finishGame").execute();
        }
    }

    public SelectDifficultDialog dialog() {
        return dialog;
    }

    public void restartTimer() {
        timer.restart();
        secondsElapsed = 0;
        timeLabel.setText("00:00");
    }

    public BoardDisplay display() {
        return boardDisplay;
    }

    public String time() {
        return timeLabel.getText();
    }

    public Component getRestartButton(){
        return restart;
    }
}