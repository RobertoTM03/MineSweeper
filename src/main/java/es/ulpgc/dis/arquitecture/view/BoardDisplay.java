package es.ulpgc.dis.arquitecture.view;

import es.ulpgc.dis.arquitecture.model.Board;

public interface BoardDisplay {
    void display(Board board);
    void of(Selected selected);

    record Position(int x, int y) {
    }

    interface Selected {
        void at(Position cellPosition, int button);
    }
}
