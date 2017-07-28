package State;

import javax.media.opengl.awt.GLCanvas;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * State Manager: takes care of the interaction between states
 */
public class StateManager {
    private State gameState;
    private GLCanvas canvas;
    /**
     * define the game state
     * @param state the game state
     */
    public void setGameState(State state) {
        gameState = state;
    }

    /**
     * @param canvas the canvas
     */
    public void setCanvas(GLCanvas canvas) {
        this.canvas = canvas;
    }

    /**
     * go to the game state
     */
    void goToGame() {
        canvas.addGLEventListener(gameState);
    }
}
