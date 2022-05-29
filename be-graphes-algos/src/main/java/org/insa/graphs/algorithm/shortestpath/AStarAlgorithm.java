
package org.insa.graphs.algorithm.shortestpath;

import java.util.List;
import org.insa.graphs.algorithm.AbstractInputData;

import org.insa.graphs.model.GraphStatistics;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    protected Label[] InitialiseLabels() {
    	LabelStar ArrayLabels[] = new LabelStar[nbNodes];
    	List<Node> nodes = graph.getNodes() ;
    	
    	double Cost = 0;
    	int vitesse_max=Speed();
    	
    	//Destination point
    	Point DestinationPoint = data.getDestination().getPoint();
    	
    	for (Node node : nodes) {
    		ArrayLabels[node.getId()] = new LabelStar(node) ;
    		
    		//The cost is the difference between current point and destination point
    		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
    			Cost = node.getPoint().distanceTo(DestinationPoint);
    			}
    		else {
    			Cost = 3.6 * node.getPoint().distanceTo(DestinationPoint) / vitesse_max;
    			
    		}
  
    		ArrayLabels[node.getId()].setEstimatedCost(Cost);	
    	}
    	
    	return ArrayLabels;
    }
    
    private int Speed() {
    	int MaxSpeedGraph = graph.getGraphInformation().getMaximumSpeed();
    	int Speed=0;
    	
    	if (MaxSpeedGraph ==  GraphStatistics.NO_MAXIMUM_SPEED ) {
    		Speed=MaxSpeedGraph;
    	}
    	else {
    		Speed=130;
    	}  
    	
    	return Speed ;
    }

}
