package core;

import java.util.ArrayList;

import myutils.CanvasShell;
import myutils.Colors;
import myutils.Utils;
import myutils.Vector2D;

public class NodesFrame extends CanvasShell {
	private static final long serialVersionUID = 1L;
	private int Nnode;
	private int Nneighbour;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private int DEFAULTWIDTH = 10;
	private int DEFAULTHEIGHT = 10;

	public NodesFrame(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
		super(width, height, scale, title, nsPerTick, nsPerRender);
	}

	public void setParameter(int NNode, int Nneighbour) {
		this.Nnode = NNode;
		this.Nneighbour = Nneighbour;
		for (int iNode = 0; iNode < NNode; iNode++) {
			Vector2D location = new Vector2D(Utils.random.nextInt(WIDTH * SCALE), Utils.random.nextInt(HEIGHT
					* SCALE));
			ArrayList<Node> neighbour = new ArrayList<Node>();
			for (int ineighbour = 0; ineighbour < Nneighbour; ineighbour++) {
				neighbour.add(null);
			}
			nodes.add(new Node(location, neighbour));
		}
	}

	@Override
	protected void init() {

	}

	@Override
	protected void myTick() {

	}

	@Override
	protected void myRender() {
		screen.clear(Colors.get(0, 0, 0));
		drawNodes();
		drawLinks();
	}

	private void findLinks() {
		int x1,y1,x2,y2;
		ArrayList <Node>list;
		for (Node node1 : nodes) {
			list=new ArrayList<Node>();
			x1 = Math.round(node1.location.x);
			y1 = Math.round(node1.location.y);
			for (Node node2 : nodes) {
				if (node1.equals(node2))
					continue;
				x2 = Math.round(node2.location.x);
				y2 = Math.round(node2.location.y);
				list.add(new JointNode(node1,node2));
			}
		}
	}

	private void drawNodes() {
		int x, y;
		for (Node node : nodes) {
			x = Math.round(node.location.x);
			y = Math.round(node.location.y);
			graphics.fillOval(x, y, DEFAULTWIDTH, DEFAULTHEIGHT);
		}
	}

	private void drawLinks() {	
		int x1,y1,x2,y2;
		for (Node node : nodes) {
			 x1 = Math.round(node.location.x);
			y1 = Math.round(node.location.y);			
			for(Node neighbour:node.neighbours){
				x2=Math.round(neighbour.location.x);
				y2=Math.round(neighbour.location.y);
				graphics.drawLine(x1, y1, x2, y2);	
			}
		}
	}

	@Override
	protected void myDebugInfo() {

	}

	@Override
	protected void render() {
		myRender();

		// graphics.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		bufferStrategy.show();
	}

}
