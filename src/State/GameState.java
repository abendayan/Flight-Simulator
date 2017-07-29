package State;

import Collision.Impact;
import Collision.Point;
import Collision.Type;
import Elements.ObjectTextured;
import Game.Level;
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
import javax.xml.soap.Text;
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

/**
 * Class for the game state
 */
public class GameState extends State {
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
    private boolean winAll;

    private long previousTimestamp;
    private long lastTime;
    private long startGame;
    private long endGame;
    private long currentTime;
    private float movingPlane = 0.005f;
    private float framesPerSecond;
    private Integer currentLevel;
    private ArrayList<Level> levels;

    private TextRenderer lifeText;
    private TextRenderer chronoText;
    private TextRenderer winningScreen;
    private TextRenderer foundObject;

    private Integer life;
    private Integer numberHold;

    /**
     * Constructor: initial all of the variables that are not dependent on opengl
     */
    public GameState() {
        life = 100;
        levels = new ArrayList<>();
        width = 800;
        height = 640;
        win = false;
        winAll = false;
        coordinate = new Coordinate();
        position = new Point(5.0f, 10.0f, 5.0f);
        lifeText = new TextRenderer( new Font("Arial", Font.BOLD, 20) );
        foundObject = new TextRenderer( new Font("Arial", Font.BOLD, 20) );
        chronoText = new TextRenderer( new Font("Arial", Font.BOLD, 20) );
        winningScreen = new TextRenderer( new Font("Arial", Font.BOLD, 40) );
        numberHold = 0;
    }

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
                    if(winAll) {
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

    /**
     * impactPlane: impact the plane accordingly
     * @param impact the impact
     * @param point the new position
     */
    private void impactPlane(Impact impact, Point point) {
        if(position.y < 2.0f && impact != Impact.EXIT) {
            impact = Impact.DEAD;
        }
        switch(impact) {
            case TAKE:
                numberHold++;
            case CONTINUE:
                if(position.y > 0.5f) {
                    position.x = point.x;
                    position.y = point.y;
                    position.z = point.z;
                }
                break;
            case EXIT:
                if(numberHold == 5 || currentLevel < 2) {
                    movingPlane = 0.005f;
                    if(!win ) {
                        win = true;
                        if(currentLevel + 1 < levels.size()) {
                            currentLevel++;
                        }
                        else {
                            endGame = System.currentTimeMillis();
                            winAll = true;
                        }
                        position = new Point(5.0f, 10.0f, 5.0f);
                        coordinate = new Coordinate();
                    }
                }
                break;
            case HURT:
                life -= 5;
                position.life = life;
                if(life <= 0) {
                    break;
                }
            case DEAD:
                movingPlane = 0.005f;
                position = new Point(8.0f, 10.0f, 8.0f);
                coordinate = new Coordinate();
                dead = true;
                break;
        }
    }

    /**
     * movePlane: move the plane automatically
     */
    private void movePlane() {
        Point temp = new Point((float)(position.x), (float)(position.y), (float)(position.z));
        temp.addScale(coordinate.Z, movingPlane);
        Impact impact = levels.get(currentLevel).collisionDetection(temp);
        impactPlane(impact, temp);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }


    /**
     * init: initialize all of the parameters dependent on opengl
     * @param glAutoDrawable
     */
    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        if (glAutoDrawable instanceof Window) {
            Window window = (Window) glAutoDrawable;
            window.addKeyListener(this);
        } else if (GLProfile.isAWTAvailable() && glAutoDrawable instanceof java.awt.Component) {
            java.awt.Component comp = (java.awt.Component) glAutoDrawable;
            new AWTKeyAdapter(this, glAutoDrawable).addTo(comp);
        }
        GL2 gl = glAutoDrawable.getGL().getGL2(); // get the GL from the GLAutoDrawable
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glEnable(GL_TEXTURE_2D);

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
        defineLevel(80, 130.0f, gl);
        levels.get(0).makeObject(gl);

        //////////////////////////
        /// DEFINE SECOND LEVEL //
        //////////////////////////
        defineLevel(80, 120.0f, gl);


        /////////////////////////
        /// DEFINE THIRD LEVEL //
        /////////////////////////
        defineLevel(150, 90.0f, gl);
        levels.get(2).createObjects();

        currentLevel = 0;
        lastTime = System.currentTimeMillis();
        startGame = System.currentTimeMillis();
        currentTime = 0;

        framesPerSecond = 0;
        previousTimestamp = System.currentTimeMillis();
        fps = 0.0f;
    }

    /**
     * defineLevel: create the elements necessary for a level
     * @param numberBulding number of buildings inside of the level
     * @param placeExit the place of the exit
     * @param gl opengl object to create the objects
     */
    private void defineLevel(int numberBulding, float placeExit, GL2 gl) {
        Level level = new Level(0.0f, 200.0f, 0.0f,200.0f, 0.0f, 200.0f);
        level.defineTextureFront("sky.jpg", 1.0f);
        level.defineTextureBack("sky.jpg", 1.0f);
        level.defineTextureRight("sky.jpg", 1.0f);
        level.defineTextureLeft("sky.jpg", 1.0f);
        level.defineTextureCeiling("sky.jpg", 1.0f);
        level.defineTextureFloor("macadam.jpg", 6.0f);
        level.activateLight(new float[] {5.0f, 2.0f, 5.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });
        level.activateLight(new float[] {9.0f, 0.0f, 9.0f}, new float[] { 0.05f, 0.05f, 0.05f, 1.0f },
                new float[] { 255.0f, 255.0f, 255.0f, 255.0f }, new float[] { 255.0f, 255.0f, 255.0f, 255.0f  });

        level.defNumberBuilding(numberBulding, gl);
        level.createExit(placeExit, gl);

        levels.add(level);
    }

    /**
     * Define the matrix for 3d drawing
     * @param gl opengl
     */
    private void define3D(GL2 gl) {
        gl.glMatrixMode(GL2.GL_PROJECTION); //switch to projection matrix
        gl.glEnable(GL_NORMALIZE);
        gl.glLoadIdentity(); //init the matrix
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {
    }

    /**
     * display: draw all frames the scene
     * @param glAutoDrawable
     */
    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();//get the GL object
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); //clear the depth buffer and the color buffer
        gl.glLoadIdentity(); //init the matrix
        if(win) {
            // if we won
            if(winAll) {
                // if we won the game
                drawText("Won the game! In " + (float)(endGame - startGame)/1000 + "s", Color.WHITE);
            }
            else {
                // if we won the level
                drawText("Won the level!\n Press space to continue!", Color.WHITE);
                levels.get(currentLevel - 1).cleanUp();
                levels.get(currentLevel).makeObject(gl);
            }
        }
        else if(!dead){
            // we havn't won and we are not dead
            steep = 3.9f /1.5f;
            angle = 12.4f /1.5f;
            movePlane(); // move the plane

            glu.gluLookAt(position.x, position.y, position.z,
                    position.x + coordinate.Z.x, position.y + coordinate.Z.y, position.z + coordinate.Z.z,
                    coordinate.Y.x, coordinate.Y.y, coordinate.Y.z);

            levels.get(currentLevel).lightUps(gl); // activate the lights
            //draw things using openGL
            gl.glEnable(GL_TEXTURE_2D);
            levels.get(currentLevel).display(gl); // display the level

            if(currentLevel > 0) {
                // for all the levels except the first one, check the collision with the balls
                levels.get(currentLevel).collisionBalls(position);
                life = position.life;
            }

            currentTime =  System.currentTimeMillis();
            if( currentTime - lastTime > 3000 ) {
                lastTime = currentTime;
                if(currentLevel > 0) {
                    // every 3 seconds, shoot a new ball
                    levels.get(currentLevel).shootBall(gl, position);
                }
            }

            /*
             * draw the texts
            */

            // for the chrono
            chronoText.beginRendering(width, height);
            chronoText.setColor(Color.WHITE);
            chronoText.draw((float)(System.currentTimeMillis() - startGame)/1000 + "s", width-120, height -40 );
            chronoText.endRendering();

            // for the life
            lifeText.beginRendering(width, height);
            if(life <= 50) {
                lifeText.setColor(Color.RED);
            }
            else {
                lifeText.setColor(Color.WHITE);
            }
            lifeText.draw("Life: " + life + "/100", 120, height -40);
            lifeText.endRendering();

            if(currentLevel > 1) {
                foundObject.beginRendering(width, height);
                foundObject.setColor(Color.WHITE);
                foundObject.draw("objects: " + numberHold + "/5", 120, 40 );
                foundObject.endRendering();
            }
        }
        else {
            // we are dead
            drawText("Lost! Press space to try again", Color.red);
        }
        gl.glFlush();
    }

    /**
     * function to write for the loosing or winning screen
     * @param text the text to draw
     * @param color the color of the text
     */
    private void drawText(String text, Color color) {
        winningScreen.beginRendering(width, height);
        winningScreen.setColor(color);
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
