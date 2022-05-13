package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label implements Comparable<Label> {
	
	private double estimatedCost;
	
	public LabelStar(Node CurrentNode) {
		super(CurrentNode);
		this.estimatedCost=Double.POSITIVE_INFINITY;
	}
	
	@Override
	
	//Getters
	public double getTotalCost() {
		return this.estimatedCost + this.getCost();
	}
	
	public double getEstimatedCost() {
		return this.estimatedCost;
	}
	
	//Setters
	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost=estimatedCost;
	}
	
	//Compare to
	public int CompareTo(LabelStar label) {
		return Double.compare(this.getTotalCost(), label.getTotalCost());
		
	}

}
