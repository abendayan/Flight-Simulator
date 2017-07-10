package Utilities;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.sun.javafx.geom.Vec2d;
import com.sun.javafx.geom.Vec3d;

import javax.media.opengl.GL2;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;
import static javax.media.opengl.GL.GL_FRONT;
import static javax.media.opengl.GL.GL_TEXTURE_2D;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_FLAT;
import static javax.media.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;

/**
 * Adele Bendayan
 * 336141056
 */
public class Parser {
    ArrayList<String> lines;
    ArrayList<String> linesTexture;
    ArrayList<ArrayList<Integer>> faces;
    ArrayList<Vec3d> vertices;
    ArrayList<Vec2d> texture;
    ArrayList<Vec3d> verticesNormal;
    Map<String, Texture> allTexture;
    Map<String, float[]> allKa;
    Map<String, float[]> allKd;
    Map<String, float[]> allKs;
    Map<String, float[]> allKe;
    Map<String, float[]> allTf;
    Map<String, Float> allNs;
    Map<String, Float> allNi;
    Map<String, Float> alld;
    Map<String, Float> allTr;
    Map<String, Float> allIllum;
    int list;

    public Parser(String fileName) {
        Path file = Paths.get(fileName);
        try {
            lines = (ArrayList<String>) Files.readAllLines(file);
        } catch (java.io.IOException e) {
            System.out.println(e);
        }
        vertices = new ArrayList<>();
        texture = new ArrayList<>();
        verticesNormal = new ArrayList<>();
        allTexture = new HashMap<>();
        allKa = new HashMap<>();
        allKe = new HashMap<>();
        allKd = new HashMap<>();
        allKs = new HashMap<>();
        allNs = new HashMap<>();
        allNi = new HashMap<>();
        alld = new HashMap<>();
        allTr = new HashMap<>();
        allTf = new HashMap<>();
        allIllum = new HashMap<>();
        faces = new ArrayList<>();
    }

    public int getList(GL2 gl) {
        parseEverything(gl);
        return list;
    }

    public ArrayList<Vec3d> getVertices() {
        return  vertices;
    }

    public ArrayList<Vec3d> getNormal() {
        return  verticesNormal;
    }

    void parseTexture() {
        String key = "";
        String[] temp;
        String tempLine;
        for (String line : linesTexture) {
            if(line.startsWith("newmtl")) {
                key = line.split("newmtl ")[1];
            }
            else if(line.startsWith("map")) {
                try {
                    if(!allTexture.containsKey(key)) {
                        allTexture.put(key,
                                TextureIO.newTexture(new File( "texture/" + line.split(" ")[1]) ,true));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                key = "";
            }
            else if(line.startsWith("Ka")){
                tempLine = line.split("Ka ")[1];
                temp = tempLine.split(" ");
                allKa.put(key,
                        new float[]{Float.parseFloat(temp[0]),Float.parseFloat(temp[1]), Float.parseFloat(temp[2])});
            }
            else if(line.startsWith("Kd")){
                tempLine = line.split("Kd ")[1];
                temp = tempLine.split(" ");
                allKd.put(key,
                        new float[]{Float.parseFloat(temp[0]),Float.parseFloat(temp[1]), Float.parseFloat(temp[2])});
            }
            else if(line.startsWith("Ks")){
                tempLine = line.split("Ks ")[1];
                temp = tempLine.split(" ");
                allKs.put(key,
                        new float[]{Float.parseFloat(temp[0]),Float.parseFloat(temp[1]), Float.parseFloat(temp[2])});
            }
            else if(line.startsWith("Ke")){
                tempLine = line.split("Ke ")[1];
                temp = tempLine.split(" ");
                allKe.put(key,
                        new float[]{Float.parseFloat(temp[0]),Float.parseFloat(temp[1]), Float.parseFloat(temp[2])});
            }
            else if(line.startsWith("Tf")){
                tempLine = line.split("Tf ")[1];
                temp = tempLine.split(" ");
                if(!allTf.containsKey(key)){
                    allTf.put(key,
                            new float[]{Float.parseFloat(temp[0]),Float.parseFloat(temp[1]), Float.parseFloat(temp[2])});
                }
            }
            else if(line.startsWith("Ns")){
                tempLine = line.split("Ns ")[1];
                temp = tempLine.split(" ");
                allNs.put(key, Float.parseFloat(temp[0]));
            }
            else if(line.startsWith("Ni")){
                tempLine = line.split("Ni ")[1];
                temp = tempLine.split(" ");
                allNi.put(key, Float.parseFloat(temp[0]));
            }
            else if(line.startsWith("d")){
                tempLine = line.split("d ")[1];
                temp = tempLine.split(" ");
                alld.put(key, Float.parseFloat(temp[0]));
            }
            else if(line.startsWith("Tr")){
                tempLine = line.split("Tr ")[1];
                temp = tempLine.split(" ");
                allTr.put(key, Float.parseFloat(temp[0]));
            }
            else if(line.startsWith("illum")){
                tempLine = line.split("illum ")[1];
                temp = tempLine.split(" ");
                allIllum.put(key, Float.parseFloat(temp[0]));
            }
        }
    }

    private void parseEverything(GL2 gl) {

        String[] temp;
        String tempLine;
        int verticeIndex;
        int textureIndex;
        int normalIndex;
        int index;
        for (String line : lines) {
            if(line.startsWith("mtllib")) {
                gl.glShadeModel(GL_FLAT);
                tempLine = line.split("mtllib ")[1];
                Path file = Paths.get("texture/" + tempLine);
                try {
                    linesTexture = (ArrayList<String>) Files.readAllLines(file);
                } catch (java.io.IOException e) {
                    System.out.println(e);
                }
                parseTexture();
                list = gl.glGenLists(1);
                gl.glNewList(list, GL2.GL_COMPILE);
                gl.glPushMatrix();

            }
            else if (line.startsWith("v ")) {
                tempLine = line.split("v ")[1];
                temp = tempLine.split(" ");
                vertices.add(new Vec3d(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]),
                        Double.parseDouble(temp[2])));
            }
            else if(line.startsWith("vt ")) {
                tempLine = line.split("vt ")[1];
                temp = tempLine.split(" ");
                texture.add(new Vec2d(Double.parseDouble(temp[0]), Double.parseDouble(temp[1])));
            }
            else if (line.startsWith("vn ")) {
                tempLine = line.split("vn ")[1];
                temp = tempLine.split(" ");
                verticesNormal.add(new Vec3d(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]),
                        Double.parseDouble(temp[2])));
            }
            else if(line.startsWith("s ")) {
                gl.glShadeModel(GL_SMOOTH);
            }
            else if(line.startsWith("f ")) {
                tempLine = line.split("f ")[1];
                temp = tempLine.split(" ");
                String[] faceS;
                index = faces.size();
                faces.add(new ArrayList<>());
                for (String aFace : temp) {
                    faceS = aFace.split("/");
                    verticeIndex = abs(Integer.parseInt(faceS[0]))-1;
                    faces.get(index).add(abs(Integer.parseInt(faceS[0]))-1);
                    if(faceS.length > 1) {
                        if(!faceS[1].equals("")) {
                            textureIndex = abs(Integer.parseInt(faceS[1]))-1;
                            gl.glTexCoord2f((float) texture.get(textureIndex).x, (float) texture.get(textureIndex).y);
                        }
                    }
                    if(faceS.length > 2) {
                        normalIndex = abs(Integer.parseInt(faceS[2]))-1;
                        gl.glNormal3f((float)verticesNormal.get(normalIndex).x, (float)verticesNormal.get(normalIndex).y,
                                (float)verticesNormal.get(normalIndex).z);
                    }
                    gl.glVertex3f((float)vertices.get(verticeIndex).x, (float)vertices.get(verticeIndex).y,
                            (float)vertices.get(verticeIndex).z);

                }
            }
            else if(line.startsWith("usemtl")) {
                String image = line.split(" ")[1];
                gl.glEnd();
                gl.glPopMatrix();

                gl.glPushMatrix();
                gl.glMaterialfv(GL_FRONT,  GL2.GL_AMBIENT, allKa.get(image), 0);
                gl.glMaterialfv(GL_FRONT,  GL2.GL_SPECULAR, allKs.get(image), 0);
                gl.glMaterialfv(GL_FRONT,  GL2.GL_DIFFUSE, allKd.get(image), 0);
                if(allTexture.containsKey(image)) {
                    allTexture.get(image).enable(gl);
                    allTexture.get(image).bind(gl);
                    gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                    gl.glTexParameteri(GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                }
                gl.glBegin(GL2.GL_QUADS);
            }
        }


        gl.glEnd();
        gl.glPopMatrix();
        gl.glEndList();
    }
}
