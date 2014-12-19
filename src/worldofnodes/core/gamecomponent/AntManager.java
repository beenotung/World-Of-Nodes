package worldofnodes.core.gamecomponent;

import myutils.maths.graph.Graph;

import java.util.Vector;

/**
 * Created by beenotung on 12/19/14.
 */
public class AntManager {
    private Vector<Ant> ants;
    private Graph graph;

    public AntManager() {
        ants = new Vector<Ant>();
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void addAnt(Ant ant) {
        ants.add(ant);
    }

    public void addNewAnt() {
        ants.add(new Ant(graph, graph.));
    }

    public void init() {
        ants.removeAllElements();

    }
}
