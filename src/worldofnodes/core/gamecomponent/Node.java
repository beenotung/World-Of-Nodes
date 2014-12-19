package worldofnodes.core.gamecomponent;

import myutils.Utils;
import myutils.Vector2D;

import java.awt.*;
import java.util.ArrayList;

public class Node implements Cloneable {
        public Vector2D location;
    public ArrayList<Node> neighbours;
    public Color color;
    public int width, height;

    public Node(int width, int height, Color color, Vector2D location) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.location = location;
        neighbours = new ArrayList<>();
    }

    public Node(int width, int height, Color color, Vector2D location, ArrayList<Node> neighbours) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.location = location;
        this.neighbours = neighbours;
    }

    public Node clone() throws CloneNotSupportedException {
        // Vertex result = new Vertex(this.width, this.height,
        // this.color,this.location.clone(),(ArrayList<Vertex>)
        // this.neighbours.clone());
        // return result;
        return (Node) super.clone();
    }

    public Node getRandomNeighbours() {
        if (neighbours.size() == 0)
            return null;
        else
            return neighbours.get(Utils.random.nextInt(neighbours.size()));
    }
}
