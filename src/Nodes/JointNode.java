package Nodes;

import myutils.Vector2D;

public class JointNode implements Comparable<JointNode> {
    public Node node1, node2;
    public Double distance;

    public JointNode(Node node1, Node node2) {
        // this.node1 = node1.clone();
        // this.node2 = node2.clone();
        this.node1 = node1;
        this.node2 = node2;
        distance = Vector2D.Distance(node1.location, node2.location);
    }

    @Override
    public int compareTo(JointNode other) {
        return this.distance.compareTo(other.distance);
    }

}
