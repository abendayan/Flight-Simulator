package Game;

import Collision.Impact;
import Collision.ObjectCollision;
import Collision.Point;
import Collision.Type;
import Elements.Ball;
import Elements.Building;
import Elements.Object;
import Elements.ObjectTextured;
import Elements.Walls.*;

import javax.media.opengl.GL2;
import javax.swing.text.Position;
import java.sql.Array;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;

/**
 * Adele Bendayan
 * 336141056
 */

/**
 * Class to represent a level
 */
public class Level extends Object {
    private WallFront front;
    private WallBack back;
    private WallLeft left;
    private WallRight right;
    private WallFloor floor;
    private WallCeiling ceiling;
    public float Xmin;
    public float Xmax;
    public float Ymin;
    public float Ymax;
    public float Zmin;
    public float Zmax;
    private ArrayList<float[]> positionL;
    private ArrayList<float[]> lightAmbient;
    private ArrayList<float[]> lightDiffuse;
    private ArrayList<float[]> lightSpecular;

    private ArrayList<Object> objects;
    private ArrayList<Ball> balls;

    /**
     * Constructor for the level
     * @param Xmin minimum for x
     * @param Xmax maximum for x
     * @param Ymin minimum for y
     * @param Ymax maximum for y
     * @param Zmin minimum for z
     * @param Zmax maximum for z
     */
    public Level(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin,
          float Zmax) {
        this.Xmin = Xmin;
        this.Xmax = Xmax;
        this.Ymin = Ymin;
        this.Ymax = Ymax;
        this.Zmin = Zmin;
        this.Zmax = Zmax;
        // initialize the lists
        balls = new ArrayList<>();
        objects = new ArrayList<>();
        positionL = new ArrayList<>();
        lightAmbient = new ArrayList<>();
        lightDiffuse = new ArrayList<>();
        lightSpecular = new ArrayList<>();
    }

    /**
     * add a ball
     * @param newBall a ball to add
     * @param gl opengl parameter
     */
    private void addBall(Ball newBall, GL2 gl) {
        newBall.makeObject(gl);
        balls.add(newBall);
    }

    /**
     * function to shoot a ball
     * @param gl opengl
     * @param position position of the plane
     */
    public void shootBall(GL2 gl, Point position){
        float[] from = new float[]{20.0f, 2.0f, 120.0f};
        float[] to = new float[] {(float)position.x, (float)position.y, (float) position.z};
        Ball ball = new Ball(from, to, gl);
        addBall(ball, gl);
    }

    /**
     * add the parameters for a light
     * @param positionL position of the light
     * @param lightAmbient parameter for ambient light
     * @param lightDiffuse parameter for diffuse light
     * @param lightSpecular parameter for specular light
     */
    public void activateLight(float[] positionL, float[] lightAmbient, float[] lightDiffuse, float[] lightSpecular) {
        this.positionL.add(positionL);
        this.lightAmbient.add(lightAmbient);
        this.lightDiffuse.add(lightDiffuse);
        this.lightSpecular.add(lightSpecular);
    }

    /**
     * Create the buildings
     * @param numberBuildings the number of building
     * @param gl opengl
     */
    public void defNumberBuilding(int numberBuildings, GL2 gl) {
        int j = 0;
        for(int i = 0; i < numberBuildings; i+=5) {
            addObject(new Building(new float[] {(15.0f + j*12.0f)*0.98f, 0.0f,
                    (15.0f + i*12.0f)*0.17f},
                    new float[]{5.0f, (float) ThreadLocalRandom.current().nextInt(8, 100), 5.0f},
                    new float[]{0.0f, 0.0f, 1.0f, 0.0f}, gl));

            addObject(new Building(new float[] {(15.0f + j*12.0f)*0.94f, 0.0f,
                    (15.0f + j*12.0f)*0.34f},
                    new float[]{5.0f, (float) ThreadLocalRandom.current().nextInt(8, 100), 5.0f},
                    new float[]{0.0f, 0.0f, 1.0f, 0.0f}, gl));

            addObject(new Building(new float[] {(15.0f + j*12.0f)*0.87f, 0.0f,
                    (15.0f + j*12.0f)*0.5f},
                    new float[]{5.0f, (float) ThreadLocalRandom.current().nextInt(8, 100), 5.0f},
                    new float[]{0.0f, 0.0f, 1.0f, 0.0f}, gl));

            addObject(new Building(new float[] {(15.0f + j*12.0f)*0.64f, 0.0f,
                    (15.0f + j*12.0f)*0.77f},
                    new float[]{5.0f, (float) ThreadLocalRandom.current().nextInt(8, 100), 5.0f},
                    new float[]{0.0f, 0.0f, 1.0f, 0.0f}, gl));

            addObject(new Building(new float[] {(15.0f + j*12.0f)*0.5f, 0.0f,
                    (15.0f + j*12.0f)*0.87f},
                    new float[]{5.0f, (float) ThreadLocalRandom.current().nextInt(8, 100), 5.0f},
                    new float[]{0.0f, 0.0f, 1.0f, 0.0f}, gl));
            j++;
        }
    }

    /**
     * Create the exit
     * @param position position of the exit
     */
    public void createExit(float position) {
        WallFloor exit = new WallFloor(position, position+20.0f, 1.0f, 1.0f,
                position+20.0f, position+50.0f);
        exit.defineTexture("runway.png", 1.0f);
        exit.defineImpact(Impact.EXIT);
        addObject(exit);
    }

    /**
     * activate the lights
     * @param gl opengl
     */
    public void lightUps(GL2 gl) {
        for(int i = 0; i < positionL.size(); i++) {
            gl.glLightfv(GL_LIGHT0 + i, GL_POSITION, positionL.get(i), 0);
            gl.glLightfv(GL_LIGHT0 + i, GL2.GL_AMBIENT, lightAmbient.get(i), 0);
            gl.glLightfv(GL_LIGHT0 + i, GL2.GL_DIFFUSE, lightDiffuse.get(i), 0);
            gl.glLightfv(GL_LIGHT0 + i, GL2.GL_SPECULAR, lightSpecular.get(i), 0);
        }
    }

    /**
     * Add an object
     * @param object the object to add
     */
    private void addObject(Object object) {
        objects.add(object);
    }

    /*
     * define the textures for the walls
     */
    public void defineTextureFront(String filename, float espaceTexture) {
        front = new WallFront(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        front.defineTexture(filename, espaceTexture);
    }

    public void defineTextureFloor(String filename, float espaceTexture) {
        floor = new WallFloor(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        floor.defineTexture(filename, espaceTexture);
    }

    public void defineTextureCeiling(String filename, float espaceTexture) {
        ceiling = new WallCeiling(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        ceiling.defineTexture(filename, espaceTexture);
    }

    public void defineTextureLeft(String filename, float espaceTexture) {
        left = new WallLeft(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        left.defineTexture(filename, espaceTexture);
    }

    public void defineTextureRight(String filename, float espaceTexture) {
        right = new WallRight(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        right.defineTexture(filename, espaceTexture);
    }

    public void defineTextureBack(String filename, float espaceTexture) {
        back = new WallBack(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        back.defineTexture(filename, espaceTexture);
    }

    /**
     * make the objects
     * @param gl opengl
     */
    @Override
    public void makeObject(GL2 gl) {
        front.makeObject(gl);
        back.makeObject(gl);
        right.makeObject(gl);
        left.makeObject(gl);
        ceiling.makeObject(gl);
        floor.makeObject(gl);
        for (Object object : objects) {
            object.makeObject(gl);
        }
        for(Ball ball : balls) {
            ball.makeObject(gl);
        }
    }

    /**
     * display all of the objects in the level
     * @param gl opengl
     */
    @Override
    public void display(GL2 gl) {
        front.display(gl);
        back.display(gl);
        left.display(gl);
        right.display(gl);
        ceiling.display(gl);
        floor.display(gl);
        for (Object object : objects) {
            if(object.exist) {
                object.display(gl);
            }
        }

        for(Ball ball : balls) {
            ball.display(gl);
        }
    }

    /**
     * collisionDetection detect if the user got a collision with an object
     * @param objectCollision the object to test if there is collision
     * @return the type of the collision
     */
    public Impact collisionDetection(ObjectCollision objectCollision) {
        Impact temp = objectCollision.impactCollision(front);
        if(temp == Impact.STOP) {
            return Impact.STOP;
        }
        temp = objectCollision.impactCollision(back);
        if(temp == Impact.STOP) {
            return Impact.STOP;
        }
        temp = objectCollision.impactCollision(ceiling);
        if(temp == Impact.STOP) {
            return Impact.STOP;
        }
        temp = objectCollision.impactCollision(left);
        if(temp == Impact.STOP) {
            return Impact.STOP;
        }

        temp = objectCollision.impactCollision(right);
        if(temp == Impact.STOP) {
            return Impact.STOP;
        }

        for(Object object : objects) {
            if(object.exist && !object.holding) {
                if(temp == Impact.CONTINUE) {
                    temp = objectCollision.impactCollision(object);
                    if(temp != Impact.CONTINUE) {
                        return temp;
                    }
                }
                else {
                    return temp;
                }
            }
        }
        return temp;
    }

    /**
     * test the collision of the balls with the plane
     * @param position
     */
    public void collisionBalls(Point position) {
        ArrayList<Ball> toDelete = new ArrayList<>();
        for(Ball ball : balls) {
            Impact impactBall = collisionDetection(ball.collisionModel); // test the collision with the objects
            Impact impactPosition = position.impactCollision(ball); // tests the collision with the plane
            if(impactPosition == Impact.HURT) {
                // if we got hurt
                position.life -= 5;
                toDelete.add(ball);
                break;
            }
            if(impactBall != Impact.CONTINUE) {
                toDelete.add(ball);
            }
        }
        // remove the balls that collided
        balls.removeAll(toDelete);
    }

    /**
     * clear a level
     */
    public void cleanUp() {
        objects.clear();
        balls.clear();
        positionL.clear();
        lightAmbient.clear();
        lightDiffuse.clear();
        lightSpecular.clear();
        front = null;
        back = null;
        left = null;
        right = null;
        floor = null;
        ceiling = null;
    }
}
