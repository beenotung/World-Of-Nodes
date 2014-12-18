package worldofnodes.core.graph;

import myutils.Vector2D;

public class Edge implements Comparable<Edge> {
    public Vertex srcVertex, destVertex;
    public Double distance;

    public Edge(Vertex srcVertex, Vertex destVertex) {
        this.srcVertex = srcVertex;
        this.destVertex = destVertex;
        distance = Vector2D.Distance(srcVertex.location, destVertex.location);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(Edge other) {
        return this.distance.compareTo(other.distance);
    }
}
