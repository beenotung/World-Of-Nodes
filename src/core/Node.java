package core;

import java.awt.Color;
import java.util.ArrayList;
import myutils.Vector2D;

public class Node implements Cloneable{
	public Vector2D location;
	public ArrayList<Node> neighbours;
	public Color color;
	public int width, height;

	public Node(int width, int height, Color color, Vector2D location) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.location = location;
		neighbours = new ArrayList<Node>();
	}

	public Node(int width, int height, Color color, Vector2D location, ArrayList<Node> neighbours) {
		this.width = width;
		this.height = height;
		this.color = color;
		this.location = location;
		this.neighbours = neighbours;
	}

	public Node clone() throws CloneNotSupportedException {
		// Node result = new Node(this.width, this.height,
		// this.color,this.location.clone(),(ArrayList<Node>)
		// this.neighbours.clone());
		// return result;
		return (Node) super.clone();
	}

	
}
