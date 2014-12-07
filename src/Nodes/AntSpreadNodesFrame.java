package Nodes;

import myutils.Utils;
import myutils.gui.Colors;

/**
 * Created by beenotung on 12/5/14.
 */
public class AntSpreadNodesFrame extends NodesFrame {
    public static final String APPLICATION_NAME = "Ant Spread Nodes";
    public static final String VERSION = "1.0.0";
    public static final String APP_NAME = APPLICATION_NAME + " " + VERSION;

    public AntSpreadNodesFrame(double widthRate, double heightRate, int scale) {
        super(widthRate, heightRate, scale);
        frame.setTitle(APP_NAME);
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

    @Override
    protected void init() {
        super.init();
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
    }
}
