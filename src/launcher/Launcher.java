package launcher;

import Nodes.BackGroundSpreadNodesFrame;

import java.awt.*;

public class Launcher {
    public static void main(String[] args) {
        BackGroundSpreadNodesFrame backGroundSpreadNodesFrame = new BackGroundSpreadNodesFrame(0.8, 0.8, 1);
        backGroundSpreadNodesFrame.setNumNode(20);
        backGroundSpreadNodesFrame.setNumNeighbour(3);
        backGroundSpreadNodesFrame.setNodeInitColor(Color.green);
        backGroundSpreadNodesFrame.setBackGroundColor(Color.BLACK);
        backGroundSpreadNodesFrame.start();
    }
}
