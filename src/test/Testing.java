package test;

import core.NodesFrame;

public class Testing {

	public static void main(String[] args) {

		int width = 800;
		int height = 600;
		int scale = 1;
		String title = "NodesFrame";
		double nsPerTick = 1e9D / 60D;
		double nsPerRender = 1e9D / 30D;
		NodesFrame nodesFrame = new NodesFrame(width, height, scale, title, nsPerTick, nsPerRender);
		int NNode=100;
		int Nneighbour=1;
		nodesFrame.setParameter(NNode, Nneighbour);
		nodesFrame.start();
	}

}
