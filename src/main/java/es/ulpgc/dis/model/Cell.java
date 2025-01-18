package es.ulpgc.dis.model;

public record Cell(Status status, Boolean mine, int adjacentMines) {
    public enum Status {
        Unrevealed,
        Revealed,
        Flagged
    }
}
