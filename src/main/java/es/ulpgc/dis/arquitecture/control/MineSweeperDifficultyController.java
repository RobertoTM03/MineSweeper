package es.ulpgc.dis.arquitecture.control;

import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.app.SwingCustomDifficultDialog;
import es.ulpgc.dis.arquitecture.view.CustomDifficultDialog;
import es.ulpgc.dis.arquitecture.view.SelectDifficultDialog;

public class MineSweeperDifficultyController implements Command {
    private final SelectDifficultDialog dialog;
    private final MineSweeperGame mineSweeperGame;
    private CustomDifficultDialog customDialog;

    public MineSweeperDifficultyController(SelectDifficultDialog dialog, MineSweeperGame mineSweeperGame) {
        this.dialog = dialog;
        this.mineSweeperGame = mineSweeperGame;
        setEasyDifficulty();
    }

    public void setEasyDifficulty() {
        mineSweeperGame.changeDifficulty(9, 9, 10);
    }

    public void setMediumDifficulty() {
        mineSweeperGame.changeDifficulty(16, 16, 40);
    }

    public void setHardDifficulty() {
        mineSweeperGame.changeDifficulty(16, 30, 99);
    }

    public void setCustomizedDifficulty(int rows, int columns, int mines) {
        int totalCells = rows * columns;

        if (rows < 9 || columns < 9 || 24 < rows || 30 < columns) {
            customDialog.showWarning("Invalid rows or columns (9 <= rows <= 24 and 9 <= columns <= 30)");
            return;
        }

        int minMines = (int) Math.ceil(totalCells * 0.1);
        int maxMines = (int) Math.floor(totalCells * 0.9);

        if (mines < minMines || mines > maxMines) {
            customDialog.showWarning("Ethe amount of mines must be between " + minMines + " & " + maxMines +
                    " for a board of " + rows + "x" + columns + ".");
            return;
        }

        mineSweeperGame.changeDifficulty(rows, columns, mines);
    }

    @Override
    public void execute() {
        switch (dialog.getSelectedDifficult()) {
            case 0 -> setEasyDifficulty();
            case 1 -> setMediumDifficulty();
            case 2 -> setHardDifficulty();
            default -> {
                customDialog = new SwingCustomDifficultDialog();
                customDialog.showDialog();

                int rows = customDialog.getRows();
                int columns = customDialog.getColumns();
                int mines = customDialog.getMines();

                setCustomizedDifficulty(rows, columns, mines);
            }
        }
    }
}
