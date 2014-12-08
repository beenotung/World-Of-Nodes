package Nodes;

import myutils.Vector2D;

public class JointNode implements Comparable<JointNode> {
    public Node srcNode, destNode;
    public Double distance;

    public JointNode(Node srcNode, Node destNode) {
        this.srcNode = srcNode;
        this.destNode = destNode;
        distance = Vector2D.Distance(srcNode.location, destNode.location);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(JointNode other) {
        return this.distance.compareTo(other.distance);
    }
}
