package Game;

import Collision.Impact;
import Collision.Point;
import Collision.Type;
import Elements.ObjectTextured;
import State.State;
import Utilities.Coordinate;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.awt.AWTKeyAdapter;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.System.exit;
import static javax.media.opengl.GL.GL_DEPTH_BUFFER_BIT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.fixedfunc.GLLightingFunc.*;

/**
 * Adele Bendayan
 * 336141056
 */
public class Engine extends State {
    private static GLU glu; //static object GLU that will be init at the init() method

    private Point position;
    private float steep = 0.05f;
    private float angle = 3.14159f / 10.0f;
    private Coordinate coordinate;

    private float fps;
    private int width;
    private int height;
    private boolean win;
    private boolean dead;

    private long previousTimestamp;
    private long lastTime;
    private long startGame;
    private long currentTime;
    private float movingPlane = 0.005f;
    private float framesPerSecond;
    private Integer currentLevel;
    private ArrayList<Level> levels;

    private TextRenderer fpsText;
    private TextRenderer chronoText;
    private TextRenderer winningScreen;

    /*
     * Gestion of keyboard events
     */

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int key = keyEvent.getKeyCode();
        Point temp = new Point((float)(position.x), (float)(position.y), (float)(position.z));
        switch(key) {
            case KeyEvent.VK_ESCAPE:
                exit(1);
                break;
            case KeyEvent.VK_W: // forward
                movingPlane += 0.005f;
                temp.addScale(coordinate.Z, steep);
                break;
            case KeyEvent.VK_S: // backward
                movingPlane -= 0.005f;
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
            case KeyEvent.VK_SPACE:
                if(win) {
                    if(levels.size() <= currentLevel) {
                        exit(1);
                    }
                    win = false;
                }
                if(dead) {
                    dead = false;
                }
                break;
        }
        Impact impact = levels.get(currentLevel).collisionDetection(temp);
        impactPlane(impact, temp);
    }

    private void impactPlane(Impact impact, Point point) {
        if(position.y < 3.0f && impact != Impact.EXIT) {
            impact = Impact.DEAD;
        }
        switch(impact) {
            case CONTINUE:
                if(position.y > 0.5f) {
                    position.x = point.x;
                    position.y = point.y;
                    position.z = point.z;
                }
                break;
            case EXIT:
                movingPlane = 0.005f;
                if(!win ) {
                    win = true;
                    currentLevel++;
                    position = new Point(5.0f, 10.0f, 5.0f);
                    coordinate = new Coordinate();
                }
                break;
            case DEAD:
                movingPlane = 0.005f;
                position = new Point(5.0f, 10.0f, 5.0f);
                coordinate = new Coordinate();
                dead = true;
                break;
        }
    }

    private void movePlane() {
        Point temp = new Point((float)(position.x), (float)(position.y), (float)(position.z));
        temp.addScale(coordinate.Z, movingPlane);
        Impact impact = levels.get(currentLevel).collisionDetection(temp);
        impactPlane(impact, temp);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        System.out.println("init");
        levels = new ArrayList<>();
        width = 800;
        height = 640;
        win = false;

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
        define3D(gl);
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
        actualLevel.defineTextureFloor("macadam.jpg", 6.0f);
        actualLevel.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        actualLevel.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        actualLevel.defNumberBuilding(80, gl);
        actualLevel.createExit(130.0f);

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
        actualLevel.defineTextureFloor("macadam.jpg", 6.0f);
        actualLevel.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        actualLevel.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        actualLevel.defNumberBuilding(80, gl);
        actualLevel.createExit(120.0f);

//        actualLevel.makeObject(gl);
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
        actualLevel.defineTextureFloor("macadam.jpg", 6.0f);
        actualLevel.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        actualLevel.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        actualLevel.defNumberBuilding(150, gl);
//        ObjectTextured boatThirdLevel = new ObjectTextured(new float[] {20.0f, 1.0f, 120.0f}, new float[] {2.0f, 2.0f, 2.0f},
//                new float[] {-90.0f, 1.0f, 0.0f, 1.0f}, "ShipMoscow.obj", Type.BOX);
//        boatThirdLevel.defineImpact(Impact.DEAD);
//        actualLevel.addObject(boatThirdLevel);
        actualLevel.createExit(90.0f);
//        actualLevel.makeObject(gl);
        levels.add(actualLevel);

        currentLevel = 0;
        lastTime = System.currentTimeMillis();
        startGame = System.currentTimeMillis();
        currentTime = 0;
        fpsText = new TextRenderer( new Font("Arial", Font.BOLD, 10) );
        chronoText = new TextRenderer( new Font("Arial", Font.BOLD, 20) );
        winningScreen = new TextRenderer( new Font("Arial", Font.BOLD, 40) );
        fpsText.setSmoothing(true);
        framesPerSecond = 0;
        previousTimestamp = System.currentTimeMillis();
        fps = 0.0f;
    }

    private void define3D(GL2 gl) {
        gl.glMatrixMode(GL2.GL_PROJECTION); //switch to projection matrix
        gl.glEnable(GL_NORMALIZE);
        gl.glLoadIdentity(); //init the matrix
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();//get the GL object
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear the depth buffer and the color buffer
        gl.glLoadIdentity(); //init the matrix
        if(win) {
            if(levels.size() <= currentLevel) {
                drawText("Won the game! Congrats!", Color.WHITE);
            }
            else {
                drawText("Won the level!\n Press space to continue!", Color.WHITE);
                levels.get(currentLevel - 1).cleanUp();
                levels.get(currentLevel).makeObject(gl);
            }
        }
        else if(!dead){

            steep = 3.9f /1.5f;
            angle = 12.4f /1.5f;
            movePlane();

            glu.gluLookAt(position.x, position.y, position.z,
                    position.x + coordinate.Z.x, position.y + coordinate.Z.y, position.z + coordinate.Z.z,
                    coordinate.Y.x, coordinate.Y.y, coordinate.Y.z);

            levels.get(currentLevel).lightUps(gl);
            //draw things using openGL
            gl.glEnable(GL_TEXTURE_2D);
            levels.get(currentLevel).display(gl);

            if(currentLevel > 0) {
                levels.get(currentLevel).collisionBalls(position);
                if(position.dead) {
                    dead = true;
                }
            }

            currentTime =  System.currentTimeMillis();
            if( currentTime - lastTime > 3000 ) {
                lastTime = currentTime;
                if(currentLevel > 0) {
                    levels.get(currentLevel).shootBall(gl, position);
                }
            }

            chronoText.beginRendering(width, height);
            chronoText.setColor(Color.WHITE);
            chronoText.draw((float)(System.currentTimeMillis() - startGame)/1000 + "s", width-120, height -40 );
            chronoText.endRendering();
        }
        else {

            drawText("Lost! Press space to try again", Color.red);
        }
        gl.glFlush();
    }

    private void drawText(String text, Color color) {
        winningScreen.beginRendering(width, height);
        winningScreen.setColor(color);
        // TODO something more pretty
        winningScreen.draw(text, 40, height/2 );
        winningScreen.endRendering();
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
