package Game;

import Collision.Impact;
import Collision.Point;
import Utilities.Coordinate;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.awt.TextRenderer;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.exit;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.fixedfunc.GLLightingFunc.*;

/**
 * Adele Bendayan
 * 336141056
 */
public class Engine implements GLEventListener, KeyListener {
    private static GLU glu; //static object GLU that will be init at the init() method

    private Point position;
    private float steep = 0.05f;
    private float angle = 3.14159f / 10.0f;
    private Coordinate coordinate;

    private float fps;
    private int width;
    private int height;
    private boolean win;

    private long previousTimestamp;
    private long lastTime;
    private long startGame;
    private long currentTime;
    private float framesPerSecond;
    private Integer currentLevel;
    private ArrayList<Level> levels;

    private TextRenderer fpsText;
    private TextRenderer chronoText;

    /*
     * Gestion of keyboard events
     */

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        Point temp = new Point((float)(position.x + coordinate.Z.x), (float)(position.y + coordinate.Z.y),
                (float)(position.z + coordinate.Z.z));
        switch(key) {
            case KeyEvent.VK_ESCAPE:
                exit(1);
                break;
            case KeyEvent.VK_W: // forward
                temp.addScale(coordinate.Z, steep);
                break;
            case KeyEvent.VK_S: // backward
                temp.removeScale(coordinate.Z, steep);
                break;
            case KeyEvent.VK_D: // right
                temp.removeScale(coordinate.X, steep);
                break;
            case KeyEvent.VK_A: // left
                temp.addScale(coordinate.X, steep);
                break;
            case KeyEvent.VK_Q: // down
                temp.removeScale(coordinate.Y, steep);
                break;
            case KeyEvent.VK_E: // up
                temp.addScale(coordinate.Y, steep);
                break;
            case KeyEvent.VK_I: // rotation positif - x
                coordinate.rotateX(-angle);
                break;
            case KeyEvent.VK_K: // rotation negatif - x
                coordinate.rotateX(angle);
                break;
            case KeyEvent.VK_L: // rotation positif - y
                coordinate.rotateY(angle);
                break;
            case KeyEvent.VK_J: // rotation negatif - y
                coordinate.rotateY(-angle);
                break;
            case KeyEvent.VK_O: // rotation positif - z
                coordinate.rotateZ(angle);
                break;
            case KeyEvent.VK_U: // rotation negatif - z
                coordinate.rotateZ(-angle);
                break;
        }
        Impact impact = levels.get(currentLevel).collisionDetection(temp);
        switch(impact) {
            case CONTINUE:
                if(position.y > 0.5f) {
                    position.x = temp.x - coordinate.Z.x;
                    position.y = temp.y - coordinate.Z.y;
                    position.z = temp.z - coordinate.Z.z;
                }
                break;
            case EXIT:
                System.out.println("won");
                currentLevel++;
                if(levels.size() < currentLevel) {
                    // TODO create winning screen
                }
                win = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        levels = new ArrayList<>();
        width = 800;
        height = 640;
        win = false;
        previousTimestamp = System.currentTimeMillis();
        if (glAutoDrawable instanceof Window) {
            Window window = (Window) glAutoDrawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && glAutoDrawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) glAutoDrawable;
            new AWTKeyAdapter(this, glAutoDrawable).addTo(comp);
        }
        coordinate = new Coordinate();
        GL2 gl = glAutoDrawable.getGL().getGL2(); // get the GL from the GLAutoDrawable
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glEnable(GL_TEXTURE_2D);
        position = new Point(5.0f, 10.0f, 5.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST); //enable depth buffer
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); //define the clear color to black
        gl.glClearDepth(1.0f);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
        glu = new GLU(); //init the GLU object
        gl.glMatrixMode(GL2.GL_PROJECTION); //switch to projection matrix
        gl.glEnable(GL_NORMALIZE);
        gl.glLoadIdentity(); //init the matrix
        gl.glEnable(GL_LIGHTING);
        gl.glEnable(GL_LIGHT0);
        gl.glEnable(GL_LIGHT1);

        ////////////////////////
        // DEFINE FIRST LEVEL //
        ////////////////////////
        Level actualLevel = new Level(0.0f, 200.0f, 0.0f,200.0f, 0.0f, 200.0f);
        actualLevel.defineTextureFront("sky.jpg", 1.0f);
        actualLevel.defineTextureBack("sky.jpg", 1.0f);
        actualLevel.defineTextureRight("sky.jpg", 1.0f);
        actualLevel.defineTextureLeft("sky.jpg", 1.0f);
        actualLevel.defineTextureCeiling("sky.jpg", 1.0f);
        actualLevel.defineTextureFloor("sea.jpg", 6.0f);
        actualLevel.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        actualLevel.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        actualLevel.defNumberBuilding(10);
        actualLevel.createExit();
        actualLevel.makeObject(gl);
        levels.add(actualLevel);


        //////////////////////////
        /// DEFINE SECOND LEVEL //
        //////////////////////////
        actualLevel = new Level(0.0f, 200.0f, 0.0f,200.0f, 0.0f, 200.0f);
        actualLevel.defineTextureFront("sky.jpg", 1.0f);
        actualLevel.defineTextureBack("sky.jpg", 1.0f);
        actualLevel.defineTextureRight("sky.jpg", 1.0f);
        actualLevel.defineTextureLeft("sky.jpg", 1.0f);
        actualLevel.defineTextureCeiling("sky.jpg", 1.0f);
        actualLevel.defineTextureFloor("sea.jpg", 6.0f);
        actualLevel.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        actualLevel.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        actualLevel.defNumberBuilding(30);
        actualLevel.createExit();
        actualLevel.makeObject(gl);
        levels.add(actualLevel);


        /////////////////////////
        /// DEFINE THIRD LEVEL //
        /////////////////////////
        actualLevel = new Level(0.0f, 200.0f, 0.0f,200.0f, 0.0f, 200.0f);
        actualLevel.defineTextureFront("sky.jpg", 1.0f);
        actualLevel.defineTextureBack("sky.jpg", 1.0f);
        actualLevel.defineTextureRight("sky.jpg", 1.0f);
        actualLevel.defineTextureLeft("sky.jpg", 1.0f);
        actualLevel.defineTextureCeiling("sky.jpg", 1.0f);
        actualLevel.defineTextureFloor("sea.jpg", 6.0f);
        actualLevel.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        actualLevel.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        actualLevel.defNumberBuilding(50);
        // TODO add an enemy
        actualLevel.createExit();
        actualLevel.makeObject(gl);
        levels.add(actualLevel);


        currentLevel = 0;
        lastTime = System.currentTimeMillis();
        startGame = System.currentTimeMillis();
        currentTime = 0;
        fpsText = new TextRenderer( new Font("Arial", Font.BOLD, 10) );
        chronoText = new TextRenderer( new Font("Arial", Font.BOLD, 20) );
        fpsText.setSmoothing(true);
        framesPerSecond = 0;
        fps = 0.0f;
    }

    private void calculateFrameRate() {
        currentTime =  System.currentTimeMillis();
        ++framesPerSecond;
        if( currentTime - lastTime > 1000 ) {
            framesPerSecond = 0;
            lastTime = currentTime;
        }
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        if((float)(System.currentTimeMillis() - startGame)/1000 > 120) {
            exit(1);
        }
        calculateFrameRate();
        fps = (float)(currentTime - lastTime)/framesPerSecond;
        if(currentTime == lastTime || framesPerSecond == 0) {
            fps = 10.0f;
        }
        steep = (float)(3.9)/fps;
        angle = (float)(12.4)/fps;
        GL2 gl = glAutoDrawable.getGL().getGL2();//get the GL object
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); //clear the depth buffer and the color buffer
        gl.glLoadIdentity(); //init the matrix
        glu.gluLookAt(position.x, position.y, position.z,
                position.x + coordinate.Z.x, position.y + coordinate.Z.y, position.z + coordinate.Z.z,
                coordinate.Y.x, coordinate.Y.y, coordinate.Y.z);

        levels.get(currentLevel).lightUps(gl);
        //draw things using openGL
        gl.glEnable(GL_TEXTURE_2D);
        levels.get(currentLevel).display(gl);

        fpsText.beginRendering(width, height);
        fpsText.setColor(Color.WHITE);
        fpsText.draw("fps : " + fps, width - 100, 20);
        fpsText.endRendering();

        chronoText.beginRendering(width, height);
        chronoText.setColor(Color.WHITE);
        chronoText.draw((float)(System.currentTimeMillis() - startGame)/1000 + "s", width-120, height -40 );
        chronoText.endRendering();
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl = glAutoDrawable.getGL().getGL2(); // get the GL object
        if(i2 == 0) {
            i2 = 1;
        }
        float h = (float) i3 / (float) i2;
        width = i2;
        height = i3;
        if(width < 120) {
            width = 140;
        }
        if(height < 50) {
            height = 60;
        }
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glViewport(i, i1, i2, i3);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1, 1000);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}