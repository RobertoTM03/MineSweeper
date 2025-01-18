package es.ulpgc.dis.arquitecture.view;

import es.ulpgc.dis.arquitecture.model.MineSweeperGame;

public interface Observer {
    void update(MineSweeperGame mineSweeperGame);
}
