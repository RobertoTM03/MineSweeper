package es.ulpgc.dis.arquitecture.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minesweeper {
    private final Cell[][] board;
    private final int numberOfRows;
    private final int numberOfColumns;
    private final int numberOfMines;

    public Minesweeper(int numberOfRows, int numberOfColumns, int numberOfMines) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.numberOfMines = numberOfMines;

        this.board = new Cell[numberOfRows][numberOfColumns];
        initializeBoard();
    }

    public Cell[][] getCellMatrix() {
        return board;
    }

    private void initGameWithPrediction(int row, int column) {
        placeMines(row, column);
        calculateNumbersOfCells();
    }

    private void initializeBoard() {
        Cell nullCell = new Cell(Cell.Status.Unrevealed, false, -1);

        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                board[i][j] = nullCell;
            }
        }
    }

    private void placeMines(int row, int column) {
        List<int[]> cells = new ArrayList<>();
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (Math.abs(i - row) > 1 || Math.abs(j - column) > 1) {
                    cells.add(new int[]{i, j});
                }
            }
        }

        Random random = new Random();
        for (int i = 0; i < numberOfMines; i++) {
            int[] mine = cells.remove(random.nextInt(cells.size()));
            board[mine[0]][mine[1]] = new Cell(board[mine[0]][mine[1]].status(), true, -1);
        }
    }

    private void calculateNumbersOfCells() {
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (!board[i][j].mine()) {
                    int count = countAdjacentMines(i, j);
                    board[i][j] = new Cell(board[i][j].status(), false, count);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newColumn = column + j;

                if (isInBounds(newRow, newColumn)) {
                    if (board[newRow][newColumn].mine()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public void firstMove(int row, int column) {
        initGameWithPrediction(row, column);
        nextMove(row, column, false);
    }

    public void nextMove(int row, int column, boolean flag) {
        if (!isInBounds(row, column)) return;

        if (flag) {
            flagCell(row, column);
        } else if(board[row][column].status() != Cell.Status.Flagged) {
            if (board[row][column].mine()) {
                board[row][column] = new Cell(Cell.Status.Revealed, true, -1);
            } else {
                revealCell(row, column);
            }
        }
    }

    private boolean isInBounds(int newRow, int newColumn) {
        return newRow >= 0 && newRow < numberOfRows && newColumn >= 0 && newColumn < numberOfColumns;
    }

    private void flagCell(int row, int column) {
        Cell currentCell = board[row][column];
        if (currentCell.status() == Cell.Status.Unrevealed){
            board[row][column] = new Cell(Cell.Status.Flagged, currentCell.mine(), currentCell.adjacentMines());
        } else if (currentCell.status() == Cell.Status.Flagged) {
            board[row][column] = new Cell(Cell.Status.Unrevealed, currentCell.mine(), currentCell.adjacentMines());
        }
    }

    private void revealCell(int row, int column) {
        if (board[row][column].status() == Cell.Status.Revealed) return;
        if (board[row][column].status() == Cell.Status.Flagged) return;
        int adjacentMines = board[row][column].adjacentMines();
        board[row][column] = new Cell(Cell.Status.Revealed, false, adjacentMines);
        if (adjacentMines == 0) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (isInBounds(row + i, column + j)) revealCell(row + i, column + j);
                }
            }
        }
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }
}
