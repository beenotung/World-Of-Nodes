package worldofnodes.core.graph;

import myutils.Utils;
import myutils.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class Vertex implements Cloneable {
    public Vector2D location;
    public ArrayList<Vertex> neighbours;
    public Color color;
    public int width, height;

    public Vertex(int width, int height, Color color, Vector2D location) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.location = location;
        neighbours = new ArrayList<>();
    }

    public Vertex(int width, int height, Color color, Vector2D location, ArrayList<Vertex> neighbours) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.location = location;
        this.neighbours = neighbours;
    }

    public Vertex clone() throws CloneNotSupportedException {
        // Vertex result = new Vertex(this.width, this.height,
        // this.color,this.location.clone(),(ArrayList<Vertex>)
        // this.neighbours.clone());
        // return result;
        return (Vertex) super.clone();
    }

    public Vertex getRandomNeighbours() {
        if (neighbours.size() == 0)
            return null;
        else
            return neighbours.get(Utils.random.nextInt(neighbours.size()));
    }

    private Vertex newNode() {
        Vector2D location = new Vector2D(Utils.random.nextInt(WIDTH * SCALE), Utils.random.nextInt(HEIGHT
                * SCALE));
        Vertex vertex = new Vertex(DefaultNodeWidth, DefaultNodeHeight, DefaultNodeInitColor, location);
        // findLinks(vertex);
        return vertex;
    }
}
