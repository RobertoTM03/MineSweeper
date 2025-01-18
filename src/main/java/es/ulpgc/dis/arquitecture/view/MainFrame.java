package es.ulpgc.dis.arquitecture.view;

import es.ulpgc.dis.arquitecture.control.Command;
import es.ulpgc.dis.app.BoardDisplayPanel;
import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame implements Observer {
    private MineSweeperPresenter mineSweeperPresenter;
    private final Map<String, Command> commands;
    private SelectDifficultDialog dialog;
    private final BoardDisplay boardDisplay;
    private Timer timer;
    private JLabel jLabel;
    private JLabel label;

    public MainFrame(MineSweeperPresenter mineSweeperPresenter) {
        setTitle("MineSweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);

        boardDisplay = createNullDisplay();

        this.mineSweeperPresenter = mineSweeperPresenter;
        boardDisplay.display(mineSweeperPresenter);

        mineSweeperPresenter.addObserver((Observer) boardDisplay).addObserver(this);

        setLayout(new BorderLayout());
        add(BorderLayout.CENTER, (Component) boardDisplay);
        add(BorderLayout.NORTH, toolbar());
        commands = new HashMap<>();
    }

    public void add(String name, Command command){
        commands.put(name, command);
    }

    private Component toolbar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(minesCounter());
        panel.add(difficultyCombo());
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
                _ -> {
                    commands.get("selectDifficult").execute();
                    restartTimer();
                }
        );
        dialog = combo::getSelectedIndex;
        return combo;
    }

    private void restartTimer() {
        timer.restart();
        secondsElapsed = 0;
        jLabel.setText("00:00");
    }

    private int secondsElapsed = 0;
    private Component timeDisplay() {
        jLabel = new JLabel();
        timer = new Timer(1000, _ -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            jLabel.setText(String.format("%02d:%02d", minutes, seconds));
        });
        timer.start();
        return jLabel;
    }

    private Component restartButton() {
        JButton jButton = new JButton();
        jButton.setText("Restart");
        jButton.addActionListener(_ -> {
            mineSweeperPresenter.restartGame();
            restartTimer();
            label.setText("" + mineSweeperPresenter.getRemainingMines());
        });
        return jButton;
    }

    private Component minesCounter() {
        label = new JLabel();
        label.setText("" + mineSweeperPresenter.getRemainingMines());
        return label;
    }

    private BoardDisplayPanel createNullDisplay() {
        return new BoardDisplayPanel();
    }

    @Override
    public void update(MineSweeperPresenter mineSweeperPresenter) {
        this.mineSweeperPresenter = mineSweeperPresenter;
        label.setText("" + mineSweeperPresenter.getRemainingMines());
        if (mineSweeperPresenter.getGameStatus() != MineSweeperPresenter.GameStatus.Progress) {
            String result;
            timer.stop();
            if (mineSweeperPresenter.getGameStatus() == MineSweeperPresenter.GameStatus.Win) {
                result = "¡Has ganado!";
            } else {
                result = "¡Has perdido!";
            }
            JOptionPane.showMessageDialog(null, result, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public SelectDifficultDialog dialog() {
        return dialog;
    }

    public BoardDisplay display() {
        return boardDisplay;
    }
}