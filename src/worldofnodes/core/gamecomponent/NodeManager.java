package worldofnodes.core.gamecomponent;

import myutils.Vector2D;
import myutils.maths.graph.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by beenotung on 12/19/14.
 */
public class NodeManager {
    public Color DEFAULT_COLOR;
    public float DEFAULT_RADIUS;

    public Vector<Node> nodes;

    public NodeManager(Color DEFAULT_COLOR, float DEFAULT_RADIUS, Vector<Node> nodes) {
        this.DEFAULT_COLOR = DEFAULT_COLOR;
        this.DEFAULT_RADIUS = DEFAULT_RADIUS;
        nodes = new Vector<>();
    }

    public void init(int numNode) {
        nodes.removeAllElements();
        for (int i = 0; i < numNode; i++)
            nodes.add(new Node(DEFAULT_RADIUS,DEFAULT_COLOR,new Vertex(new Vector2D())));
    }
}
