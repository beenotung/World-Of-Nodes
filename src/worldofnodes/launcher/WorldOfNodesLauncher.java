package worldofnodes.launcher;

import worldofnodes.gui.AntSpreadNodesFrame;
import worldofnodes.gui.BackGroundSpreadNodesFrame;
import worldofnodes.gui.NodesFrame;

import java.awt.*;

public class WorldOfNodesLauncher {
    NodesFrame frame;

    public void init() {
        frame = new AntSpreadNodesFrame(0.8, 0.8, 1);
    }

    public void start() {
        frame.start();
    }

    private void BackGroundSpreadNodesFrameTest() {
        BackGroundSpreadNodesFrame backGroundSpreadNodesFrame = new BackGroundSpreadNodesFrame(0.8, 0.8, 1);
        backGroundSpreadNodesFrame.setNumNode(20);
        backGroundSpreadNodesFrame.setNumNeighbour(3);
        backGroundSpreadNodesFrame.setNodeInitColor(Color.green);
        backGroundSpreadNodesFrame.setBackground(Color.black);
        backGroundSpreadNodesFrame.start();
    }
}
