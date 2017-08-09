/**
 * GameController.java
 *
 * Contains the main thread and contains the state of the "game"
 */

import javax.swing.JComponent;

public class GameController
{
    public static final int N = 5; // Tweak me :)
    private boolean[][] grid;

    // Reference to the GUIManager
    private GUIManager guiManager;

    public GameController(GUIManager guiManager) {
        grid = new boolean[N][N];

        this.guiManager = guiManager;
        guiManager.updateGameControllerInstance(this);
    }

    public void start() {
        new Thread(new Runnable() {
            private final long updatePeriod = 1;
            private long lastMillis = 0;

            // The main loop
            public void run() {
                lastMillis = System.currentTimeMillis();
                while (true) {
                    // Controls the "fps" of the application
                    if (System.currentTimeMillis() - lastMillis > updatePeriod) {
                        // Paint the graphics
                        guiManager.repaint();

                        lastMillis += updatePeriod;
                    }

                }
            }
        }).start();
    }

    public void toggleSquare(int r, int c) {
        grid[r][c] = !grid[r][c];
    }
    
    public boolean squareIsActive(int r, int c) {
        return grid[r][c];
    }
}
