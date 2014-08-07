package test;

import java.awt.Color;

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
		double nsPerRender = 1e9D / 15D;
		NodesFrame nodesFrame = new NodesFrame(width, height, scale, title, nsPerTick, nsPerRender);
		int NNode = 20;
		int Nneighbour = 3;
		Color DefaultNodeColor=Color.GREEN;
		int DEFAULTWIDTH = 10;
		int DEFAULTHEIGHT = DEFAULTWIDTH;
		Color BackGroundColor=Color.BLACK;
		nodesFrame.setParameter(NNode, Nneighbour, DefaultNodeColor,DEFAULTWIDTH, DEFAULTHEIGHT,BackGroundColor);
		nodesFrame.start();
	}
}
