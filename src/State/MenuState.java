package State;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.awt.AWTKeyAdapter;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;
import java.io.File;
import java.io.IOException;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import static java.lang.System.exit;
import static javax.media.opengl.GL.*;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * MenuState: takes care of the menu
 */
public class MenuState extends State {
    private static GLU glu;
    private Texture start;
    private Texture exit;
    private Texture red;
    private boolean select;
    private StateManager stateManager;

    public MenuState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        switch (key) {
            case KeyEvent.VK_DOWN:
                select = !select;
                break;
            case KeyEvent.VK_UP:
                select = !select;
                break;
            case KeyEvent.VK_ESCAPE:
                exit(1);
                break;
            case KeyEvent.VK_ENTER:
                if(select) {
                    stateManager.goToGame();
                }
                else {
                    exit(1);
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        select = true;
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
        String Filename = "texture/exit.jpg";
        try {
            exit = TextureIO.newTexture(new File( Filename ),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Filename = "texture/start.jpg";
        try {
            start = TextureIO.newTexture(new File( Filename ),true);
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
        if(select){
            gl.glTranslatef(-0.32f, 0.08f, 0.0f);
        }
        else {
            gl.glTranslatef(-0.32f, -0.7f, -0.5f);
        }
        gl.glScalef(1.1f, 1.1f, 1.1f);
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

        gl.glTranslatef(-0.3f, 0.1f, 0.0f);
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


        gl.glPushMatrix();
        gl.glTranslatef(-0.3f, -0.7f, -0.5f);
        exit.bind(gl);
        exit.enable(gl);
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

        gl.glFlush();
    }

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
