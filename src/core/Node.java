package core;

import java.awt.Color;
import java.util.ArrayList;

import myutils.Vector2D;

public class Node implements Cloneable {
	public Vector2D location;
	public ArrayList<Node> neighbours;
	public Color color=Color.BLACK;
	public int width,height;

	public Node(int width,int height) {
		this.width=width;
		this.height=height;
		location = new Vector2D();
		neighbours = new ArrayList<Node>();		
	}

	public Node(int width,int height,Vector2D location) {
		this.width=width;
		this.height=height;
		this.location = location;
		neighbours = new ArrayList<Node>();
	}

	public Node(int width,int height,Vector2D location, ArrayList<Node> neighbours) {
		this.width=width;
		this.height=height;
		this.location = location;
		this.neighbours = neighbours;
	}

	public Node clone() {
		Node result = new Node(this.width,this.height);
		result.location = this.location.clone();
		result.neighbours = (ArrayList<Node>) this.neighbours.clone();
		return result;
	}
}
