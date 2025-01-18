package es.ulpgc.dis.view;

import es.ulpgc.dis.presenter.MineSweeperPresenter;

public interface Observer {
    void update(MineSweeperPresenter mineSweeperPresenter);
}
