package Nodes;

import myutils.Utils;
import myutils.Vector2D;
import myutils.gui.CanvasShell;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NodesFrame extends CanvasShell {
    public static final String APPLICATION_NAME = "Nodes";
    public static final String VERSION = "2.0.0";
    public static final String APP_NAME = APPLICATION_NAME + " " + VERSION;
    private static final long serialVersionUID = 1L;
    private static final double DEFAULT_NS_PER_TICK = 1e9D / 60D;
    private static final double DEFAULT_NS_PER_RENDER = 1e9D / 3D;
    private static final int DefaultNodeWidth = 10;
    private static final int DefaultNodeHeight = 10;
    private static final Color DefaultNodeInitColor = Color.green;
    private static final Color DefaultBackGroundColor = Color.black;
    private static Rectangle screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds();
    private int numNode;
    private int numNeighbour;
    private Color nodeInitColor, backGroundColor;
    private int nodeWidth, nodeHeight;
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public NodesFrame(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
        super(width, height, scale, title, nsPerTick, nsPerRender);
    }

    public NodesFrame(double widthRate, double heightRate, int scale) {
        super((int) Math.round(screen.getWidth() * widthRate), (int) Math.round(screen.getHeight() * heightRate), scale, APP_NAME, DEFAULT_NS_PER_TICK, DEFAULT_NS_PER_RENDER);
    }

    public void setParameter(int numNode, int numNeighbour, Color DefaultNodeColor, int DefaultNodeWidth,
                             int DefaultNodeHeight, Color BackGroundColor) {
        this.numNode = numNode;
        this.numNeighbour = numNeighbour;
        this.nodeInitColor = DefaultNodeColor;
        this.backGroundColor = BackGroundColor;
        this.nodeWidth = DefaultNodeWidth;
        this.nodeHeight = DefaultNodeHeight;
    }

    private void initNodes(Color color) {
        for (int iNode = 0; iNode < this.numNode; iNode++) {
            nodes.add(newNode());
        }
    }

    private void addNode(boolean completeFindLinks) {
        nodes.add(newNode());
        numNode++;
        if (completeFindLinks)
            findLinks();
        else
            findLinks(nodes.get(nodes.size() - 1));

    }

    private void removeNode(boolean completeFindLinks) {
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
        /*
         * ArrayList<Node> neighbour = new ArrayList<Node>(); for (int
		 * countNeighbour = 0; countNeighbour < numNeighbour; countNeighbour++) {
		 * neighbour.add(null); } Node node=new Node(DefaultNodeWidth,
		 * DefaultNodeHeight, nodeInitColor, location, neighbour);
		 */
        Node node = new Node(DefaultNodeWidth, DefaultNodeHeight, nodeInitColor, location);
        // findLinks(node);
        return node;
    }

    @Override
    protected void init() {
        // frame.setResizable(true);
        initNodes(nodeInitColor);
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

    private void clearScreen() {
        graphics.setColor(backGroundColor);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void findLinks() {
        List<JointNode> list;
        for (Node node1 : nodes) {
            list = new ArrayList<JointNode>();

            for (Node node2 : nodes) {
                if (node1.equals(node2))
                    continue;

                list.add(new JointNode(node1, node2));
            }
            Collections.sort(list);

            node1.neighbours = new ArrayList<Node>();
            int index = 0;
            for (int countNeighbour = 0; countNeighbour < numNeighbour; countNeighbour++) {
                while (index != list.size()) {
                    if (list.get(index).node2.neighbours.indexOf(node1) >= 0)
                        index++;
                    else
                        break;
                }
                if (index != list.size()) {
                    node1.neighbours.add(list.get(index).node2);
                    index++;
                }
            }
        }
    }

    private void findLinks(Node node1) {
        List<JointNode> list;

        list = new ArrayList<JointNode>();

        for (Node node2 : nodes) {
            if (node1.equals(node2))
                continue;

            list.add(new JointNode(node1, node2));
        }
        Collections.sort(list);

        node1.neighbours = new ArrayList<Node>();
        for (int countNeighbour = 0; countNeighbour < numNeighbour; countNeighbour++) {
            node1.neighbours.add(list.get(countNeighbour).node2);
        }

    }

    private void drawNodes() {
        int x, y;
        int xOffset = DefaultNodeWidth / 2;
        int yOffset = DefaultNodeHeight / 2;
        for (Node node : nodes) {
            x = Math.round(node.location.x);
            y = Math.round(node.location.y);
            graphics.setColor(node.color);
            graphics.fillOval(x - xOffset, y - yOffset, DefaultNodeWidth, DefaultNodeHeight);
        }
    }

    private void drawLinks() {
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

    @Override
    protected void myDebugInfo() {

    }

    @Override
    protected void render() {
        myRender();

        // graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        bufferStrategy.show();
    }

    @Override
    protected void myKeyHandling() {
        if (keyHandler.x.pressed) {
            randomNodeColor();
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

    @Override
    protected void myMouseHandling() {

    }

}
