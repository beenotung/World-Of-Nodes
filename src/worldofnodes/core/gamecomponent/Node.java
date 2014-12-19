package worldofnodes.core.gamecomponent;

import myutils.Utils;
import myutils.maths.graph.Vertex;

import java.awt.*;
import java.util.ArrayList;

public class Node {
    public Vertex location;
    public ArrayList<Node> neighbours;
    public Color color;
    public float radius;

    public Node(float radius, Color color, Vertex location) {
        this.radius = radius;
        this.color = color;
        this.location = location;
        neighbours = new ArrayList();
    }

    public Node(float radius, Color color, Vertex location, ArrayList<Node> neighbours) {
       this(radius,color,location);
        this.neighbours = neighbours;
    }
    
    public Node getRandomNeighbours() {
        if (neighbours.size() == 0)
            return null;
        else
            return neighbours.get(Utils.random.nextInt(neighbours.size()));
    }
}
