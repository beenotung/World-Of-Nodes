package core;

import java.util.ArrayList;
import java.util.List;

import myutils.CanvasShell;
import myutils.Colors;
import myutils.Utils;
import myutils.Vector2D;
import java.util.Collections;

public class NodesFrame extends CanvasShell {
	private static final long serialVersionUID = 1L;
	private int Nnode;
	private int Nneighbour;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private int DEFAULTWIDTH, DEFAULTHEIGHT;

	public NodesFrame(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
		super(width, height, scale, title, nsPerTick, nsPerRender);
	}

	public void setParameter(int NNode, int Nneighbour, int DEFAULTWIDTH, int DEFAULTHEIGHT) {
		this.Nnode = NNode;
		this.Nneighbour = Nneighbour;
		this.DEFAULTWIDTH = DEFAULTWIDTH;
		this.DEFAULTHEIGHT = DEFAULTHEIGHT;
		for (int iNode = 0; iNode < this.Nnode; iNode++) {
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
		screen.clear(Colors.get(0, 0, 0));
		drawNodes();
		findLinks();
		drawLinks();
	}

	@Override
	protected void myTick() {

	}

	@Override
	protected void myRender() {

	}

	private void findLinks() {
		List<JointNode> list;
		for (Node node1 : nodes) {
			list = new ArrayList<JointNode>();

			for (Node node2 : nodes) {
				if (node1.equals(node2))
					continue;

				list.add(new JointNode(node1, node2));
			}
			Collections.sort(list);

			node1.neighbours = new ArrayList<Node>();
			int index=0;
			for (int ineighbour = 0; ineighbour < Nneighbour; ineighbour++) {
				//node1.neighbours.add(list.get(ineighbour).node2);
				while (list.get(index).node2.neighbours.indexOf(node1)>=0){index++;}
				node1.neighbours.add(list.get(index).node2);				
				index++;				
			}
		}
	}

	private void drawNodes() {
		int x, y;
		int xOffset = DEFAULTWIDTH / 2;
		int yOffset = DEFAULTHEIGHT / 2;
		for (Node node : nodes) {
			x = Math.round(node.location.x);
			y = Math.round(node.location.y);
			graphics.fillOval(x - xOffset, y - yOffset, DEFAULTWIDTH, DEFAULTHEIGHT);
		}
	}

	private void drawLinks() {
		int x1, y1, x2, y2;
		for (Node node : nodes) {
			x1 = Math.round(node.location.x);
			y1 = Math.round(node.location.y);
			for (Node neighbour : node.neighbours) {
				x2 = Math.round(neighbour.location.x);
				y2 = Math.round(neighbour.location.y);
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
