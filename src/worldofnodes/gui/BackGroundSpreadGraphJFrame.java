package worldofnodes.gui;

import myutils.Utils;
import myutils.gui.Colors;
import worldofnodes.core.graph.Vertex;

/**
 * Created by beenotung on 12/5/14.
 */
public class BackGroundSpreadGraphJFrame extends GraphJFrame {
    public BackGroundSpreadGraphJFrame(double widthRate, double heightRate, int scale) {
        super(widthRate, heightRate, scale);
    }

    @Override
    protected void myRender() {
        clearScreen();
        drawNodes();
        drawLinks();
    }

    @Override
    protected void myTick() {
        // randomNodeColor();
        int index = Utils.random.nextInt(numNode);
        spread(index);
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
    }
}
