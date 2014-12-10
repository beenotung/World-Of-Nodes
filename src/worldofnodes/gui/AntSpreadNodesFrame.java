package worldofnodes.gui;

import myutils.Utils;
import myutils.Vector2D;
import myutils.gui.Colors;
import worldofnodes.core.Ant;
import worldofnodes.core.JointNode;
import worldofnodes.core.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by beenotung on 12/5/14.
 */
public class AntSpreadNodesFrame extends NodesFrame {
    public static final String APPLICATION_NAME = "Ant Spread Nodes";
    public static final String VERSION = "1.0.0";
    public static final String APP_NAME = APPLICATION_NAME + " " + VERSION;
    private static final int DEFAULT_NUM_NODE = 100;
    private static final int DEFAULT_NUM_NEIGHBOUR = 4;
    protected Vector<Ant> ants = new Vector<>();

    public AntSpreadNodesFrame(double widthRate, double heightRate, int scale) {
        super(widthRate, heightRate, scale);
        frame.setTitle(APP_NAME);
    }

    @Override
    protected void myRender() {
        super.myRender();
        drawAnts();
    }

    protected void drawAnts() {
        if (ants.size() == 0) return;
        int x, y, xOffset, yOffset;
        for (Ant ant : ants) {
            x = Math.round(ant.location.x);
            y = Math.round(ant.location.y);
            xOffset = ant.width / 2;
            yOffset = ant.height / 2;
            System.out.println("drawing ant: " + x + ", " + y);
            drawOval(ant.color, x - xOffset, y - yOffset, ant.width, ant.height);
        }
    }

    @Override
    protected void myTick() {
        //setRandomNodeColor();
        int index = Utils.random.nextInt(numNode);
        //spread(index);
        for (Ant ant : ants)
            ant.tick();
    }

    @Override
    protected void init() {
        setNumNode(DEFAULT_NUM_NODE);
        setNumNeighbour(DEFAULT_NUM_NEIGHBOUR);
        initNodes();
        findLinks();
        int b = 80;
        int g = (int) Math.round(b * 1.5);
        int r = (int) Math.round(g * 1.5);
        setBackground(Colors.getColor(new Colors.RGB(r, g, b)));
        render_CanvasJFrame();
    }

    protected void findLinks() {
        List<JointNode> list;
        for (Node node1 : nodes) {
            node1.neighbours = new ArrayList<>();
        }
        for (Node node1 : nodes) {
            list = new ArrayList<>();
            for (Node node2 : nodes) {
                if (node1.equals(node2))
                    continue;
                list.add(new JointNode(node1, node2));
            }
            Collections.sort(list);
            int index = 0;
            for (int iNeighbour = 0; iNeighbour < numNeighbour; iNeighbour++) {
                while (index != list.size()) {
                    if (list.get(index).destNode.neighbours.indexOf(node1) >= 0)
                        index++;
                    else
                        break;
                }
                if (index != list.size()) {
                    node1.neighbours.add(list.get(index).destNode);
                    index++;
                }
            }
        }
    }

    protected void findLinks(Node node1) {
        List<JointNode> list;
        list = new ArrayList<>();
        for (Node node2 : nodes) {
            if (node1.equals(node2))
                continue;
            list.add(new JointNode(node1, node2));
        }
        Collections.sort(list);
        for (int iNeighbour = 0; iNeighbour < numNeighbour; iNeighbour++) {
            node1.neighbours.add(list.get(iNeighbour).destNode);
            list.get(iNeighbour).destNode.neighbours.add(node1);
        }
    }

    protected void spread(int index) {
        Node node = nodes.get(index);
        for (Node neighbour : nodes.get(index).neighbours) {
            neighbour.color = node.color;
        }
    }

    @Override
    protected void myDebugInfo() {
    }

    @Override
    protected void myKeyHandling() {
        if (keyHandler.x.pressed) {
            setRandomNodeColor();
            keyHandler.x.pressed = false;
        }
        if (keyHandler.openBracket.pressed) {
            removeNode(true);
            keyHandler.add.pressed = false;
        }
        if (keyHandler.closeBracket.pressed) {
            addNode(true);
            keyHandler.add.pressed = false;
        }
        if (keyHandler.comma.pressed) {
            removeNode(false);
            keyHandler.add.pressed = false;
        }
        if (keyHandler.period.pressed) {
            addNode(false);
            keyHandler.add.pressed = false;
        }
    }

    private void setRandomNodeColor() {
        Node target = nodes.get(Utils.random.nextInt(numNode));
        target.color = Colors.getColor();
    }

    @Override
    protected void myMouseHandling() {
        Vector2D location = mouseHandler.left.locationRaw;
        boolean state = mouseHandler.left.clicked;
        if (state) {
            System.out.print("\nclicked on: ");
            System.out.println(location.toString());
            mouseHandler.left.clicked = false;
            Node clickedNode = getNearestNode(location);
            addAnt(clickedNode);
            System.out.println("closest node: " + clickedNode.toString());
        }
    }

    private void addAnt(Node srcNode) {
        ants.add(new Ant(srcNode));
    }

    private Node getNearestNode(Vector2D location) {
        if (nodes.size() == 0) return null;
        Node closestNode = nodes.firstElement();
        double closestDistance = Vector2D.Distance(closestNode.location, location);
        double deltaDistance;
        for (Node node : nodes) {
            deltaDistance = Vector2D.Distance(node.location, location);
            if (deltaDistance < closestDistance) {
                closestNode = node;
                closestDistance = deltaDistance;
            }
        }
        return closestNode;
    }
}
