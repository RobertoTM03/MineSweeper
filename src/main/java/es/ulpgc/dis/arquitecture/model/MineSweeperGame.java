package es.ulpgc.dis.arquitecture.model;

import es.ulpgc.dis.arquitecture.view.Observer;

import java.util.ArrayList;
import java.util.List;

public class MineSweeperGame {
    Minesweeper minesweeper;

    private final List<Observer> observers = new ArrayList<>(); // Presenter

    private GameStatus gameStatus;
    private int remainingMines;
    private int numberOfMoves;

    public MineSweeperGame(int numberOfRows, int numberOfColumns, int numberOfMines) {
        this.minesweeper = new Minesweeper(numberOfRows, numberOfColumns, numberOfMines);

        this.remainingMines = numberOfMines;
        this.numberOfMoves = 0;

        this.gameStatus = GameStatus.Progress;
    }

    public static MineSweeperGame easyDifficulty() {
        return new MineSweeperGame(9, 9, 10);
    }

    public void changeDifficulty(int numberOfRows, int numberOfColumns, int numberOfMines) {
        this.minesweeper = new Minesweeper(numberOfRows, numberOfColumns, numberOfMines);

        this.remainingMines = numberOfMines;
        this.numberOfMoves = 0;

        this.gameStatus = GameStatus.Progress;

        updateAllObservers();
    }

    private void initStatusVariables(int numberOfMines) {
        remainingMines = numberOfMines;
        numberOfMoves = 0;
        gameStatus = GameStatus.Progress;
    }

    public void realizeMove(int row, int  column, boolean flag) {
        if (gameStatus != GameStatus.Progress || !isInBounds(row, column)) return;

        if (numberOfMoves == 0) {
            if (flag) return;
            minesweeper.firstMove(row, column);
        } else {
            Cell previuosCell = minesweeper.getCellMatrix()[row][column];
            if (flag && previuosCell.mine()) updateRemainingMines(previuosCell);
            minesweeper.nextMove(row, column, flag);
        }

        checkIfGameEnded();

        numberOfMoves++;
        updateAllObservers();
    }

    private void checkIfGameEnded() {
        if (allCellsRevealed()) gameStatus = GameStatus.Win;
        if (mineRevealed()) gameStatus = GameStatus.Loosed;
    }

    private void updateRemainingMines(Cell previuosCell) {
        if (previuosCell.status() == Cell.Status.Flagged) {
            remainingMines++;
        } else if (previuosCell.status() == Cell.Status.Unrevealed) {
            remainingMines--;
        }
    }

    private boolean isInBounds(int newRow, int newColumn) {
        return newRow >= 0 && newRow < minesweeper.getNumberOfRows() && newColumn >= 0 && newColumn < minesweeper.getNumberOfColumns();
    }

    public void restartGame() {
        int numberOfRows = minesweeper.getNumberOfRows();
        int numberOfColumns = minesweeper.getNumberOfColumns();
        int numberOfMines = minesweeper.getNumberOfMines();

        this.minesweeper = new Minesweeper(numberOfRows, numberOfColumns, numberOfMines);

        initStatusVariables(numberOfMines);

        updateAllObservers();
    }

    private boolean allCellsRevealed() {
        Cell[][] board = minesweeper.getCellMatrix();
        for (int i = 0; i < minesweeper.getNumberOfRows(); i++) {
            for (int j = 0; j < minesweeper.getNumberOfColumns(); j++) {
                Cell currenCell = board[i][j];
                if (!currenCell.mine() && currenCell.status() != Cell.Status.Revealed) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean mineRevealed() {
        Cell[][] board = minesweeper.getCellMatrix();

        for (int i = 0; i < minesweeper.getNumberOfRows(); i++) {
            for (int j = 0; j < minesweeper.getNumberOfColumns(); j++) {
                Cell currenCell = board[i][j];
                if (currenCell.mine() && currenCell.status() == Cell.Status.Revealed) {
                    return true;
                }
            }
        }
        return false;
    }

    public MineSweeperGame addObserver(Observer observer) {
        observers.add(observer);
        observer.update(this);
        return this;
    }

    private void updateAllObservers() {
        for (Observer observer : observers) observer.update(this);
    }

    public Board getBoard() {
        return () -> minesweeper.getCellMatrix();
    }

    public int getRemainingMines() {
        return remainingMines;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public enum GameStatus {
        Progress,
        Win,
        Loosed,
    }
}
