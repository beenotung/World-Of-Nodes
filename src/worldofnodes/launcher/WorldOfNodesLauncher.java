package worldofnodes.launcher;

import worldofnodes.gui.AntSpreadGraphJFrame;
import worldofnodes.gui.BackGroundSpreadGraphJFrame;
import worldofnodes.gui.GraphJFrame;

import java.awt.*;

public class WorldOfNodesLauncher {
    GraphJFrame frame;

    public void init() {
        frame = new AntSpreadGraphJFrame(0.8, 0.8, 1);
    }

    public void start() {
        frame.start();
    }

    private void BackGroundSpreadNodesFrameTest() {
        BackGroundSpreadGraphJFrame backGroundSpreadNodesFrame = new BackGroundSpreadGraphJFrame(0.8, 0.8, 1);
        backGroundSpreadNodesFrame.setNumNode(20);
        backGroundSpreadNodesFrame.setNumNeighbour(3);
        backGroundSpreadNodesFrame.setNodeInitColor(Color.green);
        backGroundSpreadNodesFrame.setBackground(Color.black);
        backGroundSpreadNodesFrame.start();
    }
}
