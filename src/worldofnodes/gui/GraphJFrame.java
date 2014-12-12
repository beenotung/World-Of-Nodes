package worldofnodes.gui;

import myutils.Utils;
import myutils.Vector2D;
import myutils.gui.CanvasJFrame;
import worldofnodes.core.graph.Edge;
import worldofnodes.core.graph.Vertex;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

public abstract class GraphJFrame extends CanvasJFrame {
    public static final String APPLICATION_NAME = "GraphJFrame";
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
    protected Vector<Vertex> vertexes = new Vector<>();
    protected int nodeWidth, nodeHeight;
    protected int numNeighbour;
    private Color nodeInitColor;

    public GraphJFrame(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
        super(width, height, scale, title, nsPerTick, nsPerRender);
        frame.setTitle(APP_NAME);
    }

    public GraphJFrame(double widthRate, double heightRate, int scale) {
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

    protected void initNodes() {
        initNodes(DefaultNodeInitColor);
    }

    protected void initNodes(Color color) {
        vertexes.removeAllElements();
        for (int iNode = 0; iNode < this.numNode; iNode++) {
            vertexes.add(newNode());
        }
    }

    protected void addNode(boolean completeFindLinks) {
        vertexes.add(newNode());
        numNode++;
        if (completeFindLinks)
            findLinks();
        else
            findLinks(vertexes.get(vertexes.size() - 1));
    }

    protected void removeNode(boolean completeFindLinks) {
        if (numNode == numNeighbour)
            return;
        Vertex vertexDel = vertexes.get(Utils.random.nextInt(numNode));
        if (completeFindLinks) {
            vertexes.remove(vertexDel);
            numNode--;
            findLinks();
        } else {
            for (Vertex vertex : vertexes) {
                if (vertex.equals(vertexDel))
                    continue;
                if (vertex.neighbours.contains(vertexDel))
                    vertex.neighbours.remove(vertexDel);
            }
            vertexes.remove(vertexDel);
            numNode--;
        }
    }

    private Vertex newNode() {
        Vector2D location = new Vector2D(Utils.random.nextInt(WIDTH * SCALE), Utils.random.nextInt(HEIGHT
                * SCALE));
        Vertex vertex = new Vertex(DefaultNodeWidth, DefaultNodeHeight, DefaultNodeInitColor, location);
        // findLinks(vertex);
        return vertex;
    }

    @Override
    protected void init() {
        // frame.setResizable(true);
        initNodes();
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
        vertexes.get(index).color = new Color(r, g, b);
        spread(index);
    }

    @Override
    protected void myTick() {
        // randomNodeColor();
        int index = Utils.random.nextInt(numNode);
        spread(index);
    }

    private void spread(int index) {
        Vertex vertex = vertexes.get(index);
        for (Vertex neighbour : vertexes.get(index).neighbours) {
            neighbour.color = vertex.color;
        }
    }

    @Override
    protected void myRender() {
        clearScreen();
        drawNodes();
        drawLinks();
    }

    protected void findLinks() {
        List<Edge> list;
        for (Vertex vertex1 : vertexes) {
            list = new ArrayList<>();
            for (Vertex vertex2 : vertexes) {
                if (vertex1.equals(vertex2))
                    continue;
                list.add(new Edge(vertex1, vertex2));
            }
            Collections.sort(list);
            vertex1.neighbours = new ArrayList<>();
            int index = 0;
            for (int iNeighbour = 0; iNeighbour < numNeighbour; iNeighbour++) {
                while (index != list.size()) {
                    if (list.get(index).destVertex.neighbours.indexOf(vertex1) >= 0)
                        index++;
                    else
                        break;
                }
                if (index != list.size()) {
                    vertex1.neighbours.add(list.get(index).destVertex);
                    index++;
                }
            }
        }
    }

    protected void findLinks(Vertex vertex1) {
        List<Edge> list;
        list = new ArrayList<>();
        for (Vertex vertex2 : vertexes) {
            if (vertex1.equals(vertex2))
                continue;
            list.add(new Edge(vertex1, vertex2));
        }
        Collections.sort(list);
        vertex1.neighbours = new ArrayList<>();
        for (int iNeighbour = 0; iNeighbour < numNeighbour; iNeighbour++) {
            vertex1.neighbours.add(list.get(iNeighbour).destVertex);
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
        for (Vertex vertex : vertexes) {
            x = Math.round(vertex.location.x);
            y = Math.round(vertex.location.y);
            xOffset = vertex.width / 2;
            yOffset = vertex.height / 2;
            graphics.setColor(vertex.color);
            graphics.fillOval(x - xOffset, y - yOffset, vertex.width, vertex.height);
        }
    }

    protected void drawLinks() {
        int x1, y1, x2, y2;
        for (Vertex vertex : vertexes) {
            x1 = Math.round(vertex.location.x);
            y1 = Math.round(vertex.location.y);
            graphics.setColor(vertex.color);
            for (Vertex neighbour : vertex.neighbours) {
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
        super.render();
        myRender();
        // graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        bufferStrategy.show();
    }

}
