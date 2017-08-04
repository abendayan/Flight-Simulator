package State;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;
import static javax.media.opengl.GL.GL_BLEND;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;

/**
 * Adele Bendayan
 * 336141056
 */
public class TextState extends State {
    private static GLU glu;
    private Texture start;
    private Texture background;
    private StateManager stateManager;
    private TextRenderer explain;
    private Texture red;

    public TextState(StateManager stateManager) {
        this.stateManager = stateManager;
        explain = new TextRenderer( new Font("Arial", Font.BOLD, 15) );

    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                exit(1);
                break;
            case KeyEvent.VK_ENTER:
                stateManager.goToGame();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        //noinspection Duplicates
        if (glAutoDrawable instanceof com.jogamp.newt.Window) {
            com.jogamp.newt.Window window = (com.jogamp.newt.Window) glAutoDrawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && glAutoDrawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) glAutoDrawable;
            new AWTKeyAdapter(this, glAutoDrawable).addTo(comp);
        }
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glEnable(GL_TEXTURE_2D);
        gl.glEnable(GL_BLEND);
        gl.glClearColor(255.0f, 255.0f, 255.0f, 255.0f);
        glu = new GLU(); //init the GLU object
        String Filename = "texture/start.jpg";
        try {
            start = TextureIO.newTexture(new File( Filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Filename = "texture/background.jpg";
        try {
            background = TextureIO.newTexture(new File( Filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Filename = "texture/red.jpg";
        try {
            red = TextureIO.newTexture(new File( Filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();//get the GL object
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPushMatrix();
        gl.glTranslatef(-1f, -1f, 0.0f);
        gl.glScalef(4.0f, 4.0f, 4.0f);
        background.bind(gl);
        background.enable(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(5.0f, 5.0f, 5.0f);
        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex2d(0.0f, 0.0f);

        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex2d(0.0f, 0.5f);

        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex2d(0.5f, 0.5f);

        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex2d(0.5f, 0.0f);

        gl.glEnd();
        gl.glPopMatrix();

        explain.beginRendering(600, 800);
        explain.setColor(Color.BLUE);
        explain.draw("Welcome to the flight simulator game!", 40, 700);
        explain.draw("===Levels===", 40, 660);
        explain.draw("Level 1: learn the game! You just have to find the exit", 40, 620);
        explain.draw("Level 2: a bit harder! Find the exit without getting too much shot", 40, 580);
        explain.draw("Level 3: at last! Don't get shot, and find all of the items before getting out!", 40, 540);
        explain.draw("===Controls===", 40, 500);
        explain.draw("The camera is the plane, moving the camera is moving the plane", 40, 460);
        explain.draw("I, J, K, L, O, U: the rotations", 40, 420);
        explain.draw("W: go faster", 40, 380);
        explain.draw("S: go slower", 40, 340);
        explain.draw("Press enter to start the game.", 40, 300);
        explain.endRendering();

        gl.glPushMatrix();
        gl.glTranslatef(-0.32f, -0.83f, 0.0f);
        gl.glScalef(0.9f, 0.9f, 0.9f);
        red.bind(gl);
        red.enable(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex2d(0.0f, 0.0f);

        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex2d(0.0f, 0.5f);

        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex2d(0.5f, 0.5f);

        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex2d(0.5f, 0.0f);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glPushMatrix();

        gl.glTranslatef(-0.3f, -0.8f, 0.0f);
        gl.glScalef(0.8f, 0.8f, 0.8f);
        start.bind(gl);
        start.enable(gl);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glBegin(GL2.GL_QUADS);

        gl.glColor3f(5.0f, 5.0f, 5.0f);

        gl.glTexCoord2d(0.0f, 0.0f);
        gl.glVertex2d(0.0f, 0.0f);

        gl.glTexCoord2d(0.0f, 1.0f);
        gl.glVertex2d(0.0f, 0.5f);

        gl.glTexCoord2d(1.0f, 1.0f);
        gl.glVertex2d(0.5f, 0.5f);

        gl.glTexCoord2d(1.0f, 0.0f);
        gl.glVertex2d(0.5f, 0.0f);

        gl.glEnd();
        gl.glPopMatrix();

        gl.glFlush();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl = glAutoDrawable.getGL().getGL2(); // get the GL object
        if(i2 == 0) {
            i2 = 1;
        }
        float h = (float) i3 / (float) i2;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glViewport(i, i1, i2, i3);
        gl.glLoadIdentity();
        glu.gluOrtho2D(i, i1, i2, i3);
    }
}
