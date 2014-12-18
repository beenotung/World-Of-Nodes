package worldofnodes.core.graph;

import java.util.Vector;

/**
 * Created by beenotung on 12/10/14.
 */
public class VertexManager {
    private Vector<Vertex> vertexes = new Vector<>();

    public void removeVertex(Vertex vertex) {
        vertexes.remove(vertex);
    }

    public void addVertex(Vertex vertex) {
        vertexes.add(vertex);
    }

    public Vector<Vertex> getVertexes() {
        return vertexes;
    }
}
