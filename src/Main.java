
import Game.Engine;
import com.jogamp.opengl.util.Animator;

import javax.media.opengl.awt.GLCanvas;

/**
 * Adele Bendayan
 * 336141056
 */
public class Main {

    public static void main(String[] args) {
        java.awt.Frame frame = new java.awt.Frame("Flight Simulator");	 //create a frame
        frame.setSize(800, 640);
        frame.setLayout(new java.awt.BorderLayout());

        final Animator animator = new Animator();  //create animator class that gets the GLCanvas and calls it’s display methods sequentially
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(() -> {
                    animator.stop();
                    System.exit(0);
                }).start();
            }
        });

        GLCanvas canvas = new GLCanvas(); //create a GLCanvas
        animator.add(canvas);
        final Engine gameEngine = new Engine();
        canvas.addGLEventListener(gameEngine);	//add the bounce as an event listener for GLCanvas events

        frame.add(canvas, java.awt.BorderLayout.CENTER);	//add the canvas to the frame
        frame.validate();

        frame.setVisible(true);
        animator.start();
        canvas.requestFocus();
    }
}