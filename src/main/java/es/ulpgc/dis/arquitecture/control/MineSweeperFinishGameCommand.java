package es.ulpgc.dis.arquitecture.control;

import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;
import es.ulpgc.dis.arquitecture.view.FinishDialog;

public class MineSweeperFinishGameCommand implements Command{
    private final MineSweeperGame mineSweeperGame;
    private final MineSweeperPresenter mineSweeperPresenter;
    private final FinishDialog finishDialog;

    public MineSweeperFinishGameCommand(MineSweeperGame mineSweeperGame, FinishDialog finishDialog, MineSweeperPresenter mineSweeperPresenter){
        this.mineSweeperGame = mineSweeperGame;
        this.finishDialog = finishDialog;
        this.mineSweeperPresenter = mineSweeperPresenter;
    }

    @Override
    public void execute() {
        mineSweeperPresenter.update(mineSweeperGame);
        switch (mineSweeperGame.getGameStatus()){
            case MineSweeperGame.GameStatus.Win -> finishDialog.win();
            case MineSweeperGame.GameStatus.Lose -> finishDialog.lose();
        }
    }
}
