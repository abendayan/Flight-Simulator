package State;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;
import java.util.ArrayList;

/**
 * Adele Bendayan
 * 336141056
 */
public class StateManager {
    State currentState;
    State gameState;
    GLCanvas canvas;

    public void firstState(State firstState) {
        currentState = firstState;
    }

    public void setGameState(State state) {
        gameState = state;
    }

    public void setCanvas(GLCanvas canvas) {
        this.canvas = canvas;
    }

    public void goToGame(GLAutoDrawable glAutoDrawable) {
        currentState = null;
        currentState = gameState;
        canvas.addGLEventListener(gameState);
//        currentState.display(glAutoDrawable);
    }
}
