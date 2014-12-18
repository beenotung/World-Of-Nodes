package worldofnodes.core.graph;

import java.util.Vector;

/**
 * Created by beenotung on 12/10/14.
 */
public class EdgeManager {
    private Vector<Edge> edges = new Vector<>();

    public void addEdge(Vertex vertex1, Vertex vertex2) {
        edges.add(new Edge(vertex1, vertex2));
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public Vector<Vertex> getNeighbours(Vertex vertex) {
        Vector<Vertex> neighbours = new Vector<>();
        for (Edge edge : edges)
            if (edge.srcVertex.equals(vertex))
                neighbours.add(edge.destVertex);
            else if (edge.destVertex.equals(vertex))
                neighbours.add(edge.srcVertex);
        return neighbours;
    }
}
