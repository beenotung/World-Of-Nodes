package core;

import java.util.ArrayList;

import myutils.Vector2D;

public class Node {
	public Vector2D location;
	public ArrayList<Node> neighbours;

	public Node() {
		location = new Vector2D();
		neighbours = new ArrayList<Node>();
	}

	public Node(Vector2D location) {
		this.location = location;
		neighbours = new ArrayList<Node>();
	}

	public Node(Vector2D location, ArrayList<Node> neighbours) {
		this.location = location;
		this.neighbours = neighbours;
	}
}
