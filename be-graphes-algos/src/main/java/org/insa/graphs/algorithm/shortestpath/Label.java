package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class Label implements Comparable<Label>{
	
	protected Node CurrentNode ;
	private Arc Father ;
	private boolean Mark ;
	private double Cost ;
	
	public Label (Node CurrentNode) {
		this.CurrentNode = CurrentNode;
		this.Father = null ;
		this.Mark = false ;
		this.Cost = Double.POSITIVE_INFINITY ;
	}
	
	/*Getters*/
	public Node getcurrentNode() {
		return this.CurrentNode ;
	}
	
	public Arc getFather() {
		return this.Father ;
	}
	
	public boolean getMark () {
		return this.Mark ;
	}
	
	public double getCost() {
		return this.Cost ;
	}
	
	/*Setters*/
	public void setFather(Arc Father) {
		this.Father = Father ;
	}
	
	public void setMark (boolean Mark) {
		this.Mark = Mark ;
	}
	
	
	public void setCost(double Cost) {
		this.Cost = Cost ;
	}
	
	@Override
	public int compareTo(Label o) {
		return Double.compare(this.getCost(), o.getCost()) ;
	}
}
