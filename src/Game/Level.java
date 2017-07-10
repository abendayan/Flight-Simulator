package Game;

import Collision.Impact;
import Collision.ObjectCollision;
import Collision.Type;
import Elements.Object;
import Elements.ObjectTextured;
import Elements.Walls.*;

import javax.media.opengl.GL2;
import java.util.ArrayList;

import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_LIGHT0;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_POSITION;

/**
 * Adele Bendayan
 * 336141056
 */
public class Level extends Object {
    public WallFront front;
    public WallBack back;
    public WallLeft left;
    public WallRight right;
    public WallFloor floor;
    public WallCeiling ceiling;
    public float Xmin;
    public float Xmax;
    public float Ymin;
    public float Ymax;
    public float Zmin;
    public float Zmax;
    private int numberBuildings;
    private ArrayList<float[]> positionL;
    private ArrayList<float[]> lightAmbient;
    private ArrayList<float[]> lightDiffuse;
    private ArrayList<float[]> lightSpecular;

    public ArrayList<Object> objects;

    public Level(float Xmin, float Xmax, float Ymin, float Ymax, float Zmin,
                 float Zmax) {
        this.Xmin = Xmin;
        this.Xmax = Xmax;
        this.Ymin = Ymin;
        this.Ymax = Ymax;
        this.Zmin = Zmin;
        this.Zmax = Zmax;
        objects = new ArrayList<>();
        positionL = new ArrayList<>();
        lightAmbient = new ArrayList<>();
        lightDiffuse = new ArrayList<>();
        lightSpecular = new ArrayList<>();
    }

    public void activateLight(float[] positionL, float[] lightAmbient, float[] lightDiffuse, float[] lightSpecular) {
        this.positionL.add(positionL);
        this.lightAmbient.add(lightAmbient);
        this.lightDiffuse.add(lightDiffuse);
        this.lightSpecular.add(lightSpecular);
    }

    void defNumberBuilding(int number) {
        numberBuildings = number;

        for(int i = 0; i < numberBuildings; i++) {
            // TODO random position
            addObject(new ObjectTextured(new float[] {5.0f, 2.5f, 15.0f}, new float[]{0.5f, 0.5f, 0.5f},
                    new float[]{0.0f, 0.0f, 1.0f, 0.0f}, "SkyA.obj", Type.BOX, Impact.DEAD));
        }
    }

    void createExit() {
        // TODO find a cool object for this
        // TODO random position
    }

    void lightUps(GL2 gl) {
        for(int i = 0; i < positionL.size(); i++) {
            gl.glLightfv(GL_LIGHT0 + i, GL_POSITION, positionL.get(i), 0);
            gl.glLightfv(GL_LIGHT0 + i, GL2.GL_AMBIENT, lightAmbient.get(i), 0);
            gl.glLightfv(GL_LIGHT0 + i, GL2.GL_DIFFUSE, lightDiffuse.get(i), 0);
            gl.glLightfv(GL_LIGHT0 + i, GL2.GL_SPECULAR, lightSpecular.get(i), 0);
        }
    }

    public void addObject(Object object) {
        objects.add(object);
    }

    void defineTextureFront(String filename, float espaceTexture) {
        front = new WallFront(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        front.defineTexture(filename, espaceTexture);
    }

    void defineTextureFloor(String filename, float espaceTexture) {
        floor = new WallFloor(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        floor.defineTexture(filename, espaceTexture);
    }

    void defineTextureCeiling(String filename, float espaceTexture) {
        ceiling = new WallCeiling(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        ceiling.defineTexture(filename, espaceTexture);
    }

    void defineTextureLeft(String filename, float espaceTexture) {
        left = new WallLeft(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        left.defineTexture(filename, espaceTexture);
    }

    void defineTextureRight(String filename, float espaceTexture) {
        right = new WallRight(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        right.defineTexture(filename, espaceTexture);
    }

    void defineTextureBack(String filename, float espaceTexture) {
        back = new WallBack(Xmin, Xmax, Ymin, Ymax, Zmin, Zmax);
        back.defineTexture(filename, espaceTexture);
    }

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
    }

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
    }

    Impact collisionDetection(ObjectCollision objectCollision) {
        Impact temp = objectCollision.impactCollision(front);
        if(temp == Impact.STOP) {
            return Impact.STOP;
        }
        temp = objectCollision.impactCollision(back);
        if(temp == Impact.CONTINUE) {
//            temp = objectCollision.impactCollision(floor);
        }
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
        //temp = objectCollision.impactCollision(floor);

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
}
