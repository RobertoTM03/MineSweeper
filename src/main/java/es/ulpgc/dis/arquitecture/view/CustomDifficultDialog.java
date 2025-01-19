package es.ulpgc.dis.arquitecture.view;

public interface CustomDifficultDialog {
    int getMines();
    int getRows();
    int getColumns();

    void showWarning(String message);
    void showDialog();
}
