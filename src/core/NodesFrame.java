package core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import myutils.CanvasShell;
import myutils.Utils;
import myutils.Vector2D;
import java.util.Collections;

public class NodesFrame extends CanvasShell {
	private static final long serialVersionUID = 1L;
	private int Nnode;
	private int Nneighbour;
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private int DefaultNodeWidth, DefaultNodeHeight;
	private Color DefaultNodeColor, BackGroundColor;

	public NodesFrame(int width, int height, int scale, String title, double nsPerTick, double nsPerRender) {
		super(width, height, scale, title, nsPerTick, nsPerRender);
	}

	public void setParameter(int NNode, int Nneighbour, Color DefaultNodeColor, int DefaultNodeWidth,
			int DefaultNodeHeight, Color BackGroundColor) {
		this.Nnode = NNode;
		this.Nneighbour = Nneighbour;
		this.DefaultNodeColor = DefaultNodeColor;
		this.BackGroundColor = BackGroundColor;
		this.DefaultNodeWidth = DefaultNodeWidth;
		this.DefaultNodeHeight = DefaultNodeHeight;
	}

	private void initNodes(Color color) {
		for (int iNode = 0; iNode < this.Nnode; iNode++) {
			nodes.add(newNode());
		}
	}

	private void addNode(boolean completeFindLinks) {		
		nodes.add(newNode());
		Nnode++;
		if (completeFindLinks)
			findLinks();
		else
			findLinks(nodes.get(nodes.size() - 1));
		
	}

	private void removeNode(boolean completeFindLinks) {		
		if (Nnode == (Nneighbour * Nneighbour))
			return;
		Node nodeDel = nodes.get(Utils.random.nextInt(Nnode));
		if (completeFindLinks){
			nodes.remove(nodeDel);
			Nnode--;
			findLinks();
		}else{
		for (Node node : nodes) {
			if (node.equals(nodeDel))
				continue;
			if (node.neighbours.contains(nodeDel))
				node.neighbours.remove(nodeDel);
		}
		nodes.remove(nodeDel);
		Nnode--;}		
	}

	private Node newNode() {
		Vector2D location = new Vector2D(Utils.random.nextInt(WIDTH * SCALE), Utils.random.nextInt(HEIGHT
				* SCALE));
		/*
		 * ArrayList<Node> neighbour = new ArrayList<Node>(); for (int
		 * ineighbour = 0; ineighbour < Nneighbour; ineighbour++) {
		 * neighbour.add(null); } Node node=new Node(DefaultNodeWidth,
		 * DefaultNodeHeight, DefaultNodeColor, location, neighbour);
		 */
		Node node = new Node(DefaultNodeWidth, DefaultNodeHeight, DefaultNodeColor, location);
		// findLinks(node);
		return node;
	}

	@Override
	protected void init() {
		// frame.setResizable(true);
		initNodes(DefaultNodeColor);
		findLinks();
		clearScreen();
		drawNodes();
		drawLinks();
	}

	private void randomNodeColor() {
		int index = Utils.random.nextInt(Nnode);
		float r = Utils.random.nextFloat();
		float g = Utils.random.nextFloat();
		float b = Utils.random.nextFloat();
		nodes.get(index).color = new Color(r, g, b);
		spread(index);
	}

	@Override
	protected void myTick() {
		// randomNodeColor();
		int index = Utils.random.nextInt(Nnode);
		spread(index);
	}

	private void spread(int index) {
		Node node = nodes.get(index);
		for (Node neighbour : nodes.get(index).neighbours) {
			neighbour.color = node.color;
		}
	}

	@Override
	protected void myRender() {
		clearScreen();
		drawNodes();
		drawLinks();
	}

	private void clearScreen() {
		graphics.setColor(BackGroundColor);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
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
			int index = 0;
			for (int ineighbour = 0; ineighbour < Nneighbour; ineighbour++) {
				outerloop:
				// node1.neighbours.add(list.get(ineighbour).node2);
				while (list.get(index).node2.neighbours.indexOf(node1) >= 0) {
					index++;
					if ((index + 1) >= list.size())
						break outerloop;
				}
				node1.neighbours.add(list.get(index).node2);
				index++;
			}
		}
	}

	private void findLinks(Node node1) {
		List<JointNode> list;

		list = new ArrayList<JointNode>();

		for (Node node2 : nodes) {
			if (node1.equals(node2))
				continue;

			list.add(new JointNode(node1, node2));
		}
		Collections.sort(list);

		node1.neighbours = new ArrayList<Node>();
		int index = 0;
		for (int ineighbour = 0; ineighbour < Nneighbour; ineighbour++) {
			// node1.neighbours.add(list.get(ineighbour).node2);
			/*
			 * while (list.get(index).node2.neighbours.indexOf(node1) >= 0) {
			 * index++; }
			 */
			node1.neighbours.add(list.get(index).node2);
			index++;
		}

	}

	private void drawNodes() {
		int x, y;
		int xOffset = DefaultNodeWidth / 2;
		int yOffset = DefaultNodeHeight / 2;
		for (Node node : nodes) {
			x = Math.round(node.location.x);
			y = Math.round(node.location.y);
			graphics.setColor(node.color);
			graphics.fillOval(x - xOffset, y - yOffset, DefaultNodeWidth, DefaultNodeHeight);
		}
	}

	private void drawLinks() {		
		int x1, y1, x2, y2;
		for (Node node : nodes) {
			x1 = Math.round(node.location.x);
			y1 = Math.round(node.location.y);
			graphics.setColor(node.color);
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

	@Override
	protected void myKeyHandling() {
		// TODO Auto-generated method stub
		if (keyHandler.x.pressed) {
			randomNodeColor();
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

	@Override
	protected void myMouseHandling() {
		// TODO Auto-generated method stub

	}

}
