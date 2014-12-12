package worldofnodes.gui;

import myutils.Utils;
import myutils.Vector2D;
import myutils.gui.Colors;
import worldofnodes.core.animal.Ant;
import worldofnodes.core.graph.Edge;
import worldofnodes.core.graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Created by beenotung on 12/5/14.
 */
public class AntSpreadGraphJFrame extends GraphJFrame {
    public static final String APPLICATION_NAME = "Ant Spread Nodes";
    public static final String VERSION = "1.0.0";
    public static final String APP_NAME = APPLICATION_NAME + " " + VERSION;
    private static final int DEFAULT_NUM_NODE = 100;
    private static final int DEFAULT_NUM_NEIGHBOUR = 4;
    protected Vector<Ant> ants = new Vector<>();

    public AntSpreadGraphJFrame(double widthRate, double heightRate, int scale) {
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
        List<Edge> list;
        for (Vertex vertex1 : vertexes) {
            vertex1.neighbours = new ArrayList<>();
        }
        for (Vertex vertex1 : vertexes) {
            list = new ArrayList<>();
            for (Vertex vertex2 : vertexes) {
                if (vertex1.equals(vertex2))
                    continue;
                list.add(new Edge(vertex1, vertex2));
            }
            Collections.sort(list);
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
        for (int iNeighbour = 0; iNeighbour < numNeighbour; iNeighbour++) {
            vertex1.neighbours.add(list.get(iNeighbour).destVertex);
            list.get(iNeighbour).destVertex.neighbours.add(vertex1);
        }
    }

    protected void spread(int index) {
        Vertex vertex = vertexes.get(index);
        for (Vertex neighbour : vertexes.get(index).neighbours) {
            neighbour.color = vertex.color;
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
        Vertex target = vertexes.get(Utils.random.nextInt(numNode));
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
            Vertex clickedVertex = getNearestNode(location);
            for (int i = 0; i < 50; i++)
                addAnt(clickedVertex);
            System.out.println("closest node: " + clickedVertex.toString());
        }
    }

    private void addAnt(Vertex srcVertex) {
        ants.add(new Ant(srcVertex));
    }

    private Vertex getNearestNode(Vector2D location) {
        if (vertexes.size() == 0) return null;
        Vertex closestVertex = vertexes.firstElement();
        double closestDistance = Vector2D.Distance(closestVertex.location, location);
        double deltaDistance;
        for (Vertex vertex : vertexes) {
            deltaDistance = Vector2D.Distance(vertex.location, location);
            if (deltaDistance < closestDistance) {
                closestVertex = vertex;
                closestDistance = deltaDistance;
            }
        }
        return closestVertex;
    }
}
