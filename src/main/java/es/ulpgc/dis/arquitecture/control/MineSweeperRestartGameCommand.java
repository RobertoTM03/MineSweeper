package es.ulpgc.dis.arquitecture.control;

import es.ulpgc.dis.app.MainFrame;
import es.ulpgc.dis.arquitecture.model.MineSweeperGame;

public class MineSweeperRestartGameCommand implements Command{
    private final MainFrame mainFrame;
    private final MineSweeperGame mineSweeperGame;

    public MineSweeperRestartGameCommand(MainFrame mainFrame, MineSweeperGame mineSweeperGame) {
        this.mainFrame = mainFrame;
        this.mineSweeperGame = mineSweeperGame;
    }

    @Override
    public void execute() {
        mineSweeperGame.restartGame();
        mainFrame.restartTimer();
    }
}
