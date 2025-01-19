package es.ulpgc.dis.app;

import es.ulpgc.dis.arquitecture.control.MineSweeperDifficultyController;
import es.ulpgc.dis.arquitecture.control.MineSweeperFinishGameCommand;
import es.ulpgc.dis.arquitecture.control.MineSweeperRestartGameCommand;
import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;

public class Main {
    public static void main(String[] args) {
        MineSweeperGame mineSweeperGame = MineSweeperGame.easyDifficulty();

        MainFrame mainFrame = new MainFrame();
        mainFrame.addCommand("selectDifficult", new MineSweeperDifficultyController(mainFrame.dialog(), mineSweeperGame))
                .addCommand("restartGame", new MineSweeperRestartGameCommand(mainFrame, mineSweeperGame));

        MineSweeperPresenter mineSweeperPresenter = new MineSweeperPresenter(mineSweeperGame, mainFrame.display());
        SwingFinishDialog finishDialog = new SwingFinishDialog(mainFrame);
        mainFrame.addCommand("finishGame", new MineSweeperFinishGameCommand(mineSweeperGame, finishDialog, mineSweeperPresenter));
        mineSweeperGame.addObserver(mineSweeperPresenter)
                .addObserver(mainFrame);

        mainFrame.setVisible(true);
    }
}