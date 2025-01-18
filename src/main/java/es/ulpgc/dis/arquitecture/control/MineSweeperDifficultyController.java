package es.ulpgc.dis.arquitecture.control;

import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.view.CustomDifficultDialog;
import es.ulpgc.dis.arquitecture.view.SelectDifficultDialog;

public class MineSweeperDifficultyController implements Command{
    private final SelectDifficultDialog dialog;
    private final MineSweeperGame mineSweeperGame;

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
        mineSweeperGame.changeDifficulty(rows, columns, mines);
    }

    @Override
    public void execute() {
        switch (dialog.getSelectedDifficult()){
            case 0 -> setEasyDifficulty();
            case 1 -> setMediumDifficulty();
            case 2 -> setHardDifficulty();
            default -> {
                CustomDifficultDialog customDialog = new CustomDifficultDialog();
                customDialog.setVisible(true);

                setCustomizedDifficulty(customDialog.rows(), customDialog.columns(), customDialog.mines());
            }
        }
    }
}
