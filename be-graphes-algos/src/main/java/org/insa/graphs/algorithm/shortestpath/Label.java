package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label {
	
	Node currentNode ;
	boolean mark;
	double cost;
	Arc father;
	
	public double getCost() {
		return cost;
	}

}
