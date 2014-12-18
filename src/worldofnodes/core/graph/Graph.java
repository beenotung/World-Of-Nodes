package worldofnodes.core.graph;

/**
 * Created by hkpu on 12/12/2014.
 */
public class Graph {
    private EdgeManager edgeManager;
    private VertexManager vertexManager;

    public Graph() {
        super();
        vertexManager = new VertexManager();
        edgeManager = new EdgeManager();
    }

    public void initRandomly() {
        initNodes();
        findLinks();
    }

    private void initNodes() {
    }

    private void findLinks() {
    }
}
