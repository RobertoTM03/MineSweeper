package es.ulpgc.dis;

import es.ulpgc.dis.control.MineSweeperDifficultyController;
import es.ulpgc.dis.presenter.MineSweeperPresenter;
import es.ulpgc.dis.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        MineSweeperPresenter mineSweeperPresenter = MineSweeperPresenter.easyDifficulty();
        MainFrame mainFrame = new MainFrame(mineSweeperPresenter);
        mainFrame.add("selectDifficult", new MineSweeperDifficultyController(mainFrame.dialog(), mainFrame.display(), mineSweeperPresenter));
        mainFrame.setVisible(true);
    }
}