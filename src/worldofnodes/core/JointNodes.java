package worldofnodes.core;

import java.util.Vector;

/**
 * Created by beenotung on 12/10/14.
 */
public class JointNodes {
    public Vector<JointNode> jointNodes = new Vector<>();

    public void addPair(Node node1, Node node2) {
        jointNodes.add(new JointNode(node1, node2));
    }

    public Vector<Node> getNeighbours(Node node) {
        Vector<Node> neighbours = new Vector<>();
        for (JointNode jointNode : jointNodes)
            if (jointNode.srcNode.equals(node))
                neighbours.add(jointNode.destNode);
            else if (jointNode.destNode.equals(node))
                neighbours.add(jointNode.srcNode);
        return neighbours;
    }
}
