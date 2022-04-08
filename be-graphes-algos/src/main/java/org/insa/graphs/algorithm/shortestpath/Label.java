package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	
	private Node currentNode ;
	private boolean mark;
	private double cost;
	private Arc father;
	
	public Label(Node currentNode, boolean mark , double cost) {
		this.currentNode = currentNode;
		this.mark=mark;
		this.cost=cost;
	}
	
	public double getCost() {
		return cost;
	}
	
	public Node getcurrentNode() {
		return this.currentNode;
	}
	
	public boolean getMark() {
		return this.mark;
	}
	
	public void setFather(Arc x) {
		this.father=x;
	}
	
	public void setCost(double cost) {
		this.cost=cost;
	}
	
	public void setMark(boolean mark) {
		this.mark=mark;
	}
	
	
	public int compareTo(Label a) {
		int res;
		if(this.getCost()<a.getCost())res=-1;
		if(this.getCost()>a.getCost())res=1;
		else res=0;
		
		return res;
	}

}
