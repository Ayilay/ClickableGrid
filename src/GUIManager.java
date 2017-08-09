/**
 *  GUIManager.java
 *
 *  Renders everything within a JComponent, and communicates with the GameController Controller
 */

import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class GUIManager extends JComponent
{
    public static final boolean DEBUG = false;

    // Reference to the GameController
    private GameController gameController;

    // Mouse states updated by event listners
    private int mouseX, mouseY;

    // The coordinates of the square that the mouse is hovering over
    private int selectedSquareCol;
    private int selectedSquareRow;

    // Dimensions for the squares of the grid
    private final int PADDING = 40;
    private int space = 0;
    private int squareSize = 0;
    private int gridEdge = 0;

    public GUIManager() {
        mouseX = 0;
        mouseY = 0;

        selectedSquareCol = -1;
        selectedSquareRow = -1;

        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (selectedSquareCol != -1 && selectedSquareRow != -1)
                    gameController.toggleSquare(selectedSquareRow, selectedSquareCol);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {}
        });
    }

    public void updateGameControllerInstance(GameController instance) {
        gameController = instance;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

		// Background
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

        paintGrid(g);

        // Debug Output
        if (DEBUG) {
            g.setColor(Color.red);
            g.drawString("Width: " + getWidth(), 650, 100);
            g.drawString("Height: " + getHeight(), 650, 150);
            g.drawString("Padding: " + PADDING, 650, 200);
            g.drawString("Space: " + space, 650, 250);
            g.drawString("Square Size: " + squareSize, 650, 300);
            g.drawString("N: " + GameController.N, 650, 350);
        }
        //g.drawString(, 650, 400);

    }

    public void paintGrid(Graphics g) {
        // Calculate the dimensions of everything based on the smallest dimension
        int minDim = Math.min(getWidth(), getHeight());

        // PADDING is the space from the edges of the JFrame
        // space is the space between the squares
        // gridEdge is the x (and y) coordinate of the end of the grid of squares
        space = (minDim - 2*PADDING) / 60;
        squareSize = (minDim - 2*PADDING - (GameController.N-1)*space)/GameController.N;
        gridEdge = PADDING + GameController.N*squareSize + (GameController.N-1)*space;

        boolean someSquareSelected = false;
        for (int r = 0; r < GameController.N; r++) {
            for (int c = 0; c < GameController.N; c++) {
                int x = PADDING + r*space + r*squareSize;
                int y = PADDING + c*space + c*squareSize;

                if (mouseX > x && mouseX < x+squareSize && mouseY > y && mouseY < y+squareSize) {
                    g.setColor(Color.darkGray);

                    selectedSquareCol = c;
                    selectedSquareRow = r;

                    someSquareSelected = true;

                    if (DEBUG) {
                        g.drawString("selectedSquareCol: " + selectedSquareCol, 650, 400);
                        g.drawString("selectedSquareRow: " + selectedSquareRow, 650, 450);
                    }
                }
                else if (gameController.squareIsActive(r, c)) {
                    g.setColor(Color.red);
                }
                else {
                    g.setColor(Color.gray);

                }

                g.fillRect(x, y, squareSize, squareSize);
            }

        }

        // If no square is being hovered over, reset the selectedSquare coords
        if (!someSquareSelected) {
            selectedSquareCol = -1;
            selectedSquareRow = -1;
        }
    }

    private boolean clickedSquare(int x, int y) {
        if (x < PADDING  || y < PADDING)  return false;
        if (x > gridEdge || y > gridEdge) return false;

        if ((x-PADDING)/GameController.N <= squareSize && 
            (y-PADDING)/GameController.N <= squareSize)
            return true;

        return false;
    }
}





