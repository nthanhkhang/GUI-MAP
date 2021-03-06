/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Node implements Comparable<Node> {

	String id;
	double lat;
	double lon;
	Information info;

	HashMap<Node, Edge> adjlist;

	// constructor
	public Node(String id, double lat, double lon) {
		this.id = id;
		this.lat = lat;
		this.lon = lon;
		this.info = null;
		adjlist = new HashMap<Node, Edge>();
	}
	
	//creates new instance of info
	public void remember(boolean start) {
		this.info = new Information(start);
	}
	
	//removes the instance of info (after the use of info is done)
	public void forget() {
		this.info = null;
	}
	
	static public double pathLength(List<Node> list) {
		double returnnum = 0;
		for(int i = 0; i<list.size()-1; i++) {
			try {
				returnnum+=list.get(i).adjlist.get(list.get(i+1)).weight;
			}catch(NullPointerException e){
				System.out.println("pathLength: shortest path messed up. Nodes are disconnected. :(");
			}
		}
		return returnnum;
	}
	
	public String toString() {
		return id;
	}

	@Override
	public int compareTo(Node n) {
		//if either one of the node is lacking info, then it has no dist, so there is no point in comparing
		if (this.info == null || n.info == null||this.info.dist == n.info.dist) {
			return 0;
		}
		if (this.info.dist < n.info.dist) {
			return -1;
		}else {
			return 1;
		}
	}
	
	// a collection of information that the node must store when using Dijkstra's Algorithm
	public static class Information {
		boolean willvisit;
		boolean visited;
		Node prev;
		double dist;
		
		// the starting vertex must have a distance of 0 for the whole thing to work
		public Information(boolean start) {
			prev = null;
			visited  = false;
			if (start) {
				dist = 0;
				willvisit = true;
			} else {
				dist = Integer.MAX_VALUE;
				willvisit = false;
			}
		}

		public void update(Node newprev, Edge workingEdge) {
			this.prev = newprev;
			this.dist = newprev.info.dist + workingEdge.weight;
		}
		public void willvisit() {
			willvisit = true;
		}
		public void visited() {
			visited = true;
		}
		public String toString() {
			String info;
			if(prev!=null) {
				info = prev.toString();
			}else {
				info = "noprev";
			}
			return  info + " " + dist;
		}
	}
}
