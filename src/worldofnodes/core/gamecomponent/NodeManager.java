package worldofnodes.core.gamecomponent;

import myutils.maths.graph.Vertex;

import java.awt.*;
import java.util.Vector;

/**
 * Created by beenotung on 12/19/14.
 */
public class NodeManager {
    public Color DEFAULT_COLOR = Color.green;
    public float DEFAULT_RADIUS = 10;
    public Vector<Node> nodes;
    Map map;

    public NodeManager() {
        nodes = new Vector<>();
    }

    public NodeManager(Color DEFAULT_COLOR, float DEFAULT_RADIUS) {
        this();
        this.DEFAULT_COLOR = DEFAULT_COLOR;
        this.DEFAULT_RADIUS = DEFAULT_RADIUS;
    }

    public void init(int numNode) {
        nodes.removeAllElements();
        for (int i = 0; i < numNode; i++)
            nodes.add(new Node(DEFAULT_RADIUS, DEFAULT_COLOR, new Vertex(map.range2D));
    }
}
