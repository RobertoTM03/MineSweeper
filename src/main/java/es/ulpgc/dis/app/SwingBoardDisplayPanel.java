package es.ulpgc.dis.app;

import es.ulpgc.dis.arquitecture.io.FileImageLoader;
import es.ulpgc.dis.arquitecture.model.Board;
import es.ulpgc.dis.arquitecture.model.Cell;
import es.ulpgc.dis.arquitecture.model.Image;
import es.ulpgc.dis.arquitecture.view.BoardDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class SwingBoardDisplayPanel extends JPanel implements BoardDisplay {
    private final Map<String, Image> images = FileImageLoader.loadIcons();
    private Cell[][] cells;
    private Position selectedPosition;
    private Selected selected = null;


    public SwingBoardDisplayPanel() {
        addMouseListener(createMouseListener());
        setDoubleBuffered(true);
    }

    @Override
    public void display(Board board) {
        this.cells = board.getContent();
        repaint();
    }

    @Override
    public void of(Selected selected) {
        this.selected = selected;
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
        //TODO effiency improvment
        super.paintComponent(g);
        int cellWidth = getCellWidth();
        int cellHeight = getCellHeight();

        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < columns(); col++) {
                int x = col * cellWidth + 5;
                int y = row * cellHeight + 5;
                Cell current = cells[row][col];

                if (selectedPosition != null && (selectedPosition.x() == row && selectedPosition.y() == col)){
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
        paintOutline(g, x, y, cellWidth, cellHeight);
    }

    private void paintFlag(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintNoneRevealedCell(g, x, y, cellWidth, cellHeight);
        paintIcon(g, "flag", x, y, cellWidth, cellHeight);
    }

    private void paintMine(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintIcon(g, "dirt", x, y, cellWidth, cellHeight);
        paintIcon(g, "mine", x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
        paintOutline(g, x, y, cellWidth, cellHeight);
    }

    private void paintCounter(Graphics g, Cell current, int x, int y, int cellWidth, int cellHeight) {
        paintIcon(g, "dirt", x, y, cellWidth, cellHeight);
        if (current.adjacentMines() > 0) {
            String result = "open" + current.adjacentMines();
            paintIcon(g, result, x + cellWidth / 4, y + cellHeight / 4, cellWidth / 2, cellHeight / 2);
        }
        paintOutline(g, x, y, cellWidth, cellHeight);
    }

    private void paintNoneRevealedCell(Graphics g, int x, int y, int cellWidth, int cellHeight) {
        paintIcon(g, "grass", x, y, cellWidth, cellHeight);
        paintOutline(g, x, y, cellWidth, cellHeight);
    }

    private static void paintOutline(Graphics g, int x, int y, int cellWidth, int cellHeight) {
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

    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            private int currentColumn;
            private int currentRow;

            @Override
            public void mousePressed(MouseEvent e) {
                currentRow = toRow(e.getY());
                currentColumn = toColumn(e.getX());
                selectedPosition = new Position(toRow(e.getY()), toColumn(e.getX()));
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentRow == toRow(e.getY()) && currentColumn == toColumn(e.getX())) {
                    selected.at(new Position(currentRow, currentColumn), e.getButton());
                }
                quitSelectedPosition();
                repaint();
            }
        };
    }

    public void quitSelectedPosition() {
        selectedPosition = null;
    }
}