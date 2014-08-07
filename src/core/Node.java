package core;

import java.util.ArrayList;

import myutils.Vector2D;

public class Node implements Cloneable {
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

	public Node clone() {
		Node result = new Node();
		result.location = this.location.clone();
		result.neighbours = (ArrayList<Node>) this.neighbours.clone();
		return result;
	}
}
