package es.ulpgc.dis.arquitecture.view;

import es.ulpgc.dis.arquitecture.presenter.MineSweeperPresenter;

public interface Observer {
    void update(MineSweeperPresenter mineSweeperPresenter);
}
