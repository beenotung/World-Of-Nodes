package worldofnodes.gui;

import myutils.Utils;
import myutils.Vector2D;
import myutils.gui.CanvasJFrame;
import worldofnodes.core.JointNode;
import worldofnodes.core.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public abstract class NodesFrame extends CanvasJFrame {
    public static final String APPLICATION_NAME = "NodesFrame";
    public static final String VERSION = "2.0.0";
    public static final String APP_NAME = APPLICATION_NAME + " " + VERSION;
    private static final double DEFAULT_NS_PER_TICK = 1e9D / 60D;
    private static final double DEFAULT_NS_PER_RENDER = 1e9D / 30D;
    private static final int DefaultNodeWidth = 10;
    private static final int DefaultNodeHeight = 10;
    private static final Color DefaultNodeInitColor = Color.green;
    private static final Color DefaultBackGroundColor = Color.black;
    private static Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
    protected int numNode;
    protected Vector<Node> nodes = new Vector<>();
    protected int nodeWidth, nodeHeight;
    private int numNeighbour;
    private Color nodeInitColor;

    public NodesFrame(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
        super(width, height, scale, title, nsPerTick, nsPerRender);
        frame.setTitle(APP_NAME);
    }

    public NodesFrame(double widthRate, double heightRate, int scale) {
        super((int) Math.round(screen.getWidth() * widthRate), (int) Math.round(screen.getHeight() * heightRate), scale, APP_NAME, DEFAULT_NS_PER_TICK, DEFAULT_NS_PER_RENDER);
    }

    public void setNodeInitColor(Color nodeInitColor) {
        this.nodeInitColor = nodeInitColor;
    }

    public void setNumNeighbour(int numNeighbour) {
        this.numNeighbour = numNeighbour;
    }

    public void setNumNode(int numNode) {
        this.numNode = numNode;
    }

    protected void initNodes(Color color) {
        nodes.removeAllElements();
        for (int iNode = 0; iNode < this.numNode; iNode++) {
            nodes.add(newNode());
        }
    }

    protected void addNode(boolean completeFindLinks) {
        nodes.add(newNode());
        numNode++;
        if (completeFindLinks)
            findLinks();
        else
            findLinks(nodes.get(nodes.size() - 1));
    }

    protected void removeNode(boolean completeFindLinks) {
        if (numNode == numNeighbour)
            return;
        Node nodeDel = nodes.get(Utils.random.nextInt(numNode));
        if (completeFindLinks) {
            nodes.remove(nodeDel);
            numNode--;
            findLinks();
        } else {
            for (Node node : nodes) {
                if (node.equals(nodeDel))
                    continue;
                if (node.neighbours.contains(nodeDel))
                    node.neighbours.remove(nodeDel);
            }
            nodes.remove(nodeDel);
            numNode--;
        }
    }

    private Node newNode() {
        Vector2D location = new Vector2D(Utils.random.nextInt(WIDTH * SCALE), Utils.random.nextInt(HEIGHT
                * SCALE));
        Node node = new Node(DefaultNodeWidth, DefaultNodeHeight, DefaultNodeInitColor, location);
        // findLinks(node);
        return node;
    }

    @Override
    protected void init() {
        // frame.setResizable(true);
        initNodes(DefaultNodeInitColor);
        findLinks();
        clearScreen();
        drawNodes();
        drawLinks();
    }

    private void randomNodeColor() {
        int index = Utils.random.nextInt(numNode);
        float r = Utils.random.nextFloat();
        float g = Utils.random.nextFloat();
        float b = Utils.random.nextFloat();
        nodes.get(index).color = new Color(r, g, b);
        spread(index);
    }

    @Override
    protected void myTick() {
        // randomNodeColor();
        int index = Utils.random.nextInt(numNode);
        spread(index);
    }

    private void spread(int index) {
        Node node = nodes.get(index);
        for (Node neighbour : nodes.get(index).neighbours) {
            neighbour.color = node.color;
        }
    }

    @Override
    protected void myRender() {
        clearScreen();
        drawNodes();
        drawLinks();
    }

    private void findLinks() {
        List<JointNode> list;
        for (Node node1 : nodes) {
            list = new ArrayList<>();
            for (Node node2 : nodes) {
                if (node1.equals(node2))
                    continue;
                list.add(new JointNode(node1, node2));
            }
            Collections.sort(list);
            node1.neighbours = new ArrayList<>();
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

    private void findLinks(Node node1) {
        List<JointNode> list;
        list = new ArrayList<>();
        for (Node node2 : nodes) {
            if (node1.equals(node2))
                continue;
            list.add(new JointNode(node1, node2));
        }
        Collections.sort(list);
        node1.neighbours = new ArrayList<>();
        for (int iNeighbour = 0; iNeighbour < numNeighbour; iNeighbour++) {
            node1.neighbours.add(list.get(iNeighbour).destNode);
        }
    }

    public final void drawCircle(Color color, int x, int y, int radius) {
        graphics.setColor(color);
        graphics.fillOval(x, y, radius, radius);
    }

    public final void drawOval(Color color, int x, int y, int width, int height) {
        graphics.setColor(color);
        graphics.fillOval(x, y, width, height);
    }

    protected void drawNodes() {
        int x, y, xOffset, yOffset;
        for (Node node : nodes) {
            x = Math.round(node.location.x);
            y = Math.round(node.location.y);
            xOffset = node.width / 2;
            yOffset = node.height / 2;
            graphics.setColor(node.color);
            graphics.fillOval(x - xOffset, y - yOffset, node.width, node.height);
        }
    }

    protected void drawLinks() {
        int x1, y1, x2, y2;
        for (Node node : nodes) {
            x1 = Math.round(node.location.x);
            y1 = Math.round(node.location.y);
            graphics.setColor(node.color);
            for (Node neighbour : node.neighbours) {
                x2 = Math.round(neighbour.location.x);
                y2 = Math.round(neighbour.location.y);
                graphics.drawLine(x1, y1, x2, y2);
            }
        }
    }

    protected void render_CanvasJFrame() {
        super.render();
    }

    @Override
    protected void render() {
        //super.render();
        myRender();
        // graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        bufferStrategy.show();
    }

}
