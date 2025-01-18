package es.ulpgc.dis.arquitecture.presenter;

import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.view.BoardDisplay;
import es.ulpgc.dis.arquitecture.view.BoardDisplay.Selected;
import es.ulpgc.dis.arquitecture.view.Observer;

public class MineSweeperPresenter implements Observer {

    private final MineSweeperGame mineSweeperGame;
    private final BoardDisplay boardDisplay;

    public MineSweeperPresenter(MineSweeperGame mineSweeperGame, BoardDisplay boardDisplay) {
        this.mineSweeperGame = mineSweeperGame;
        this.boardDisplay = boardDisplay;
        boardDisplay.of(selected());
        update(mineSweeperGame);
    }

    private Selected selected() {
        return (cellPosition, button) -> {
            System.out.println("pepe");
            if (button == 1) {
                mineSweeperGame.realizeMove(cellPosition.x(), cellPosition.y(), false);
            } else if (button == 3) {
                mineSweeperGame.realizeMove(cellPosition.x(), cellPosition.y(), true);
            }
        };
    }

    @Override
    public void update(MineSweeperGame mineSweeperGame) {
        boardDisplay.display(mineSweeperGame.getBoard());
    }
}
