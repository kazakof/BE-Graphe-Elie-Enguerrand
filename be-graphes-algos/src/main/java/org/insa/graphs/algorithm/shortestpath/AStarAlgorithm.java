
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
    	
    	//Destination point
    	Point DestinationPoint = data.getDestination().getPoint();
    	
    	for (Node node : nodes) {
    		ArrayLabels[node.getId()] = new LabelStar(node) ;
    		
    		//The cost is the difference between current point and destination point
    		if (data.getMode() == AbstractInputData.Mode.LENGTH) {
    			Cost = node.getPoint().distanceTo(DestinationPoint);
    			}
  
    		ArrayLabels[node.getId()].setEstimatedCost(Cost);	
    	}
    	
    	return ArrayLabels;
    }

}
