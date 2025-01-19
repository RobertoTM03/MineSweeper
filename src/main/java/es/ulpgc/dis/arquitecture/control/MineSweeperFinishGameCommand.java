package es.ulpgc.dis.arquitecture.control;

import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;
import es.ulpgc.dis.arquitecture.view.FinishDialog;

public class MineSweeperFinishGameCommand implements Command{
    private final MineSweeperGame game;
    private final MineSweeperPresenter presenter;
    private final FinishDialog finishDialog;

    public MineSweeperFinishGameCommand(MineSweeperGame game, FinishDialog finishDialog, MineSweeperPresenter presenter){
        this.game = game;
        this.finishDialog = finishDialog;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        presenter.update(game);
        switch (game.getGameStatus()){
            case MineSweeperGame.GameStatus.Win -> finishDialog.win();
            case MineSweeperGame.GameStatus.Lose -> finishDialog.lose();
        }
    }
}
