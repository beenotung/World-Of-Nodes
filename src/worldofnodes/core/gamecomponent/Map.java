package worldofnodes.core.gamecomponent;

import myutils.maths.graph.Graph;
import myutils.maths.graph.Range2D;

import java.awt.*;

/**
 * Created by beenotung on 12/19/14.
 */
public class Map {
    public Range2D<Integer> range2D;
    public Color backgroundColor;
    public NodeManager nodeManager;
    public AntManager antManager;
    private Graph graph;

    public Map(Range2D<Integer> range2D, Color backgroundColor, NodeManager nodeManager, AntManager antManager) {
        this.range2D = range2D;
        this.backgroundColor = backgroundColor;
        this.nodeManager = nodeManager;
        this.antManager = antManager;
    }

    public void init(int numNode) {
        graph.init();
        nodeManager.init(numNode);
        antManager.init();
    }
}
