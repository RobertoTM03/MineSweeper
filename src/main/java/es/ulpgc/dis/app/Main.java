package es.ulpgc.dis.app;

import es.ulpgc.dis.arquitecture.control.MineSweeperDifficultyController;
import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;
import es.ulpgc.dis.arquitecture.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MineSweeperPresenter mineSweeperPresenter = MineSweeperPresenter.easyDifficulty();
        MainFrame mainFrame = new MainFrame(mineSweeperPresenter);
        mainFrame.add("selectDifficult", new MineSweeperDifficultyController(mainFrame.dialog(), mainFrame.display(), mineSweeperPresenter));
        mainFrame.setVisible(true);
    }
}