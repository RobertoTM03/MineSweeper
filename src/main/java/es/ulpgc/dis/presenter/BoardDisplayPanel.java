package es.ulpgc.dis.presenter;

import es.ulpgc.dis.io.FileImageLoader;
import es.ulpgc.dis.model.Cell;
import es.ulpgc.dis.model.Image;
import es.ulpgc.dis.view.BoardDisplay;
import es.ulpgc.dis.view.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Map;

public class BoardDisplayPanel extends JPanel implements BoardDisplay, Observer {
    private Cell[][] cells;
    private MineSweeperPresenter mineSweeperPresenter = null;
    private Position selectedPosition;
    private Map<String, Image> images;

    private void loadIcons() {
        images = new FileImageLoader(new File("./src/main/resources/")).load();
    }

    public BoardDisplayPanel() {
        addMouseListener(createMouseListener());
        loadIcons();
        setDoubleBuffered(true);
    }

    @Override
    public void display(MineSweeperPresenter mineSweeperPresenter) {
        this.mineSweeperPresenter = mineSweeperPresenter;
        this.cells = mineSweeperPresenter.getBoard();
        repaint();
    }

    public int getCellWidth() {
        return (getWidth() - 10) / columns();
    }

    public int getCellHeight() {
        return (getHeight() - 10) / rows();
    }

    private int columns() {
        return cells[0].length;
    }

    private int rows() {
        return cells.length;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getCellWidth();
        int cellHeight = getCellHeight();

        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < columns(); col++) {
                int x = col * cellWidth + 5;
                int y = row * cellHeight + 5;
                Cell current = cells[row][col];

                if (selectedPosition != null && (selectedPosition.x == row && selectedPosition.y == col)){
                    paintSelectedCell(g, x, y, cellWidth, cellHeight);
                } else if (current.status() == Cell.Status.Unrevealed){
                    paintNoneRevealedCell(g, x, y, cellWidth, cellHeight);
                } else if (current.status() == Cell.Status.Revealed){
                    if (current.mine()){
                        paintMine(g, x, y, cellWidth, cellHeight);
                    } else {
                        paintCounter(g, current, x, y, cellWidth, cellHeight);
                    }
                } else {
                    paintFlag(g, x, y, cellWidth, cellHeight);
                }
            }
        }
    }

    private void paintSelectedCell(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        g.setColor(Color.green.darker());
        g.fillRect(x, y, cellWidth, cellHeight);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintFlag(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintNoneRevealedCell(g, x, y, cellWidth, cellHeight);
        paintIcon(g, "flag", x, y, cellWidth, cellHeight);
    }

    private void paintMine(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintIcon(g, "dirt", x, y, cellWidth, cellHeight);
        paintIcon(g, "mine", x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintCounter(Graphics g, Cell current, int x, int y, int cellWidth, int cellHeight) {
        paintIcon(g, "dirt", x, y, cellWidth, cellHeight);
        if (current.adjacentMines() > 0) {
            String result = "open" + current.adjacentMines();
            paintIcon(g, result, x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
        }
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintNoneRevealedCell(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintIcon(g, "grass", x, y, cellWidth, cellHeight);
        g.setColor(Color.black);
        g.drawRect(x, y, cellWidth, cellHeight);
    }

    private void paintIcon(Graphics g, String iconName, int x, int y, int cellWidth, int cellHeight) {
        Icon icon = images.get(iconName).content();
        ImageIcon scaledIcon = new ImageIcon(
                ((ImageIcon) icon).getImage().getScaledInstance(cellWidth, cellHeight, java.awt.Image.SCALE_SMOOTH)
        );
        scaledIcon.paintIcon(null, g, x, y);
    }

    private int toRow(int y){
        return (y - 5) / getCellHeight();
    }

    private int toColumn(int x){
        return (x - 5) / getCellWidth();
    }

    private Cell findCellAt(int x, int y) {
        return cells[toRow(y)][toColumn(x)];
    }

    private MouseListener createMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int button = e.getButton();
                int currentRow = toRow(e.getY());
                int currentColumn = toColumn(e.getX());
                if (button == 1){
                    System.out.println("Revealed (" + currentRow + ", " + currentColumn + ")");
                    mineSweeperPresenter.realizeMove(currentRow, currentColumn, false);
                } else if (button == 3){
                    System.out.println("Flagged  (" + currentRow + ", " + currentColumn + ")");
                    mineSweeperPresenter.realizeMove(currentRow, currentColumn, true);
                } else {
                    System.out.println("None configured");
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                selectedPosition = new Position(toRow(e.getY()), toColumn(e.getX()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedPosition = null;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }

    @Override
    public void update(MineSweeperPresenter mineSweeperPresenter) {
        cells = mineSweeperPresenter.getBoard();
        repaint();
    }

    private record Position(int x, int y){}
}