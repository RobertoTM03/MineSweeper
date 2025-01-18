package es.ulpgc.dis.arquitecture.control;

import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;
import es.ulpgc.dis.arquitecture.view.BoardDisplay;
import es.ulpgc.dis.arquitecture.view.CustomDifficultDialog;
import es.ulpgc.dis.arquitecture.view.SelectDifficultDialog;

public class MineSweeperDifficultyController implements Command{
    private final SelectDifficultDialog dialog;
    private final BoardDisplay boardDisplay;
    private final MineSweeperPresenter mineSweeperPresenter;

    public MineSweeperDifficultyController(SelectDifficultDialog dialog, BoardDisplay boardDisplay, MineSweeperPresenter mineSweeperPresenter) {
        this.dialog = dialog;
        this.boardDisplay = boardDisplay;
        this.mineSweeperPresenter = mineSweeperPresenter;
        setEasyDifficulty();
    }

    public void setEasyDifficulty() {
        mineSweeperPresenter.changeDifficulty(9, 9, 10);
    }

    public void setMediumDifficulty() {
        mineSweeperPresenter.changeDifficulty(16, 16, 40);
    }

    public void setHardDifficulty() {
        mineSweeperPresenter.changeDifficulty(16, 30, 99);
    }

    public void setCustomizedDifficulty(int rows, int columns, int mines) {
        mineSweeperPresenter.changeDifficulty(rows, columns, mines);
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

        boardDisplay.display(mineSweeperPresenter);
    }
}
