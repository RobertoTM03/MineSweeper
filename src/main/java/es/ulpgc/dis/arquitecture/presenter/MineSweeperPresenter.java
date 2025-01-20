package es.ulpgc.dis.arquitecture.presenter;

import es.ulpgc.dis.arquitecture.model.Cell;
import es.ulpgc.dis.arquitecture.model.MineSweeperGame;
import es.ulpgc.dis.arquitecture.view.BoardDisplay;
import es.ulpgc.dis.arquitecture.view.BoardDisplay.Selected;
import es.ulpgc.dis.arquitecture.view.Observer;

import static es.ulpgc.dis.arquitecture.model.MineSweeperGame.GameStatus.Lose;
import static es.ulpgc.dis.arquitecture.model.MineSweeperGame.GameStatus.Progress;

public class MineSweeperPresenter implements Observer {

    private final MineSweeperGame mineSweeperGame;
    private final BoardDisplay boardDisplay;
    private boolean progress;

    public MineSweeperPresenter(MineSweeperGame mineSweeperGame, BoardDisplay boardDisplay) {
        this.mineSweeperGame = mineSweeperGame;
        this.boardDisplay = boardDisplay;
        boardDisplay.of(selected());
        update(mineSweeperGame);
        progress = true;
    }

    private Selected selected() {
        return (cellPosition, button) -> {
            if (button == 1) {
                if (progress){
                    mineSweeperGame.realizeMove(cellPosition.x(), cellPosition.y(), false);
                    gameLost();
                    update(mineSweeperGame);
                }
            } else if (button == 3) {
                mineSweeperGame.realizeMove(cellPosition.x(), cellPosition.y(), true);
            }
        };
    }

    private void gameLost() {
        if (mineSweeperGame.getGameStatus() == Lose){
            revealAllMines();
        }
    }

    private void revealAllMines() {
        Cell[][] content = mineSweeperGame.getBoard().getContent();
        int rows = content.length;
        int columns = content[1].length;
        for (int row = 0; row < rows; row++){
            for (int column = 0; column < columns; column++){
                if (content[row][column].mine())mineSweeperGame.realizeMove(row, column, false);
            }
        }
        update(mineSweeperGame);
        progress = false;
    }

    @Override
    public void update(MineSweeperGame mineSweeperGame) {
        boardDisplay.display(mineSweeperGame.getBoard());
        if (mineSweeperGame.getGameStatus() == Progress) {
            progress = true;
        }
    }
}
