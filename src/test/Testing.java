package test;

import core.NodesFrame;

public class Testing {

	public static void main(String[] args) {

		int width = 800;
		int height = 600;
		height = 1024;
		width = height * 4 / 3;
		int scale = 1;
		String title = "NodesFrame";
		double nsPerTick = 1e9D / 60D;
		double nsPerRender = 1e9D / 30D;
		NodesFrame nodesFrame = new NodesFrame(width, height, scale, title, nsPerTick, nsPerRender);
		int NNode = 5000;
		int Nneighbour = 4;
		int DEFAULTWIDTH = 10;
		int DEFAULTHEIGHT = DEFAULTWIDTH;
		nodesFrame.setParameter(NNode, Nneighbour, DEFAULTWIDTH, DEFAULTHEIGHT);
		nodesFrame.start();
	}
}
