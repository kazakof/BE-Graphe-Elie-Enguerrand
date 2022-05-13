package org.insa.graphs.algorithm.shortestpath;

import java.util.* ;
import org.insa.graphs.model.*;
import org.insa.graphs.algorithm.utils.ElementNotFoundException ;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm { 
		protected final ShortestPathData data = getInputData() ;
		
		/*Graph is imported*/
		protected Graph graph = data.getGraph();
		protected int nbNodes = graph.size();

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected Label [] InitialiseLabels () {
    	
    	/*Tab initialized with node values*/
    	Label [] ArrayLabels = new Label[nbNodes] ;
    	
    	List<Node> nodes = graph.getNodes();
    	
    	for (Node node : nodes) {
    		ArrayLabels[node.getId()] = new Label(node) ;
    	}
    	
    	return ArrayLabels ;
    }
    @Override
    protected ShortestPathSolution doRun() {
    	Label [] ArrayLabels ;
    	
    	ArrayLabels = InitialiseLabels () ;
    	
    	int nb_explored = 0 ;
    	int nb_marked = 0 ;
    	
    	//Notifying observers about the first event (origin processed)
    	notifyOriginProcessed(data.getOrigin());
    	
    	/*Heap initialization */
    	BinaryHeap<Label> Tas = new BinaryHeap <>();
    	
    	Node Origin = data.getOrigin() ;
    	Label OriginLabel = ArrayLabels[Origin.getId()];
    	
    	Node Destination = data.getDestination() ;
    	Label DestinationLabel = ArrayLabels[Destination.getId()];
    	
    	/*Origin inserted in heap*/
    	OriginLabel.setCost(0);
    	Tas.insert(OriginLabel);
    	
    	Label CurrentLabel = null ;
    	ShortestPathSolution solution = null ;
    	
    	while (!DestinationLabel.getMark() && !Tas.isEmpty() && solution == null) {
    			CurrentLabel = Tas.deleteMin() ;
    			CurrentLabel.setMark(true);
    			nb_marked ++ ;
    			
    			System.out.println("Cout :" + CurrentLabel.getCost());
    			
    			/*Notify observers about the node being marked */
    			notifyNodeMarked(CurrentLabel.getcurrentNode()) ;
    			
    			List<Arc> ListSuccessors = CurrentLabel.getcurrentNode().getSuccessors();
    			
    			/*current node successors  */
    			for (Arc ArcIter : ListSuccessors) {
    				/*Test road is allowed*/
    				if (data.isAllowed(ArcIter)) {
    					Label IterDestination = ArrayLabels[ArcIter.getDestination().getId()];
    					
    					/*Notify observers about the node being reached*/
    					notifyNodeReached(ArcIter.getDestination());
    					
    					if(!IterDestination.getMark()) {
    						if (!IterDestination.getMark() && IterDestination.getCost() > CurrentLabel.getCost() + data.getCost(ArcIter)) {
    							try {
    								Tas.remove(IterDestination);
    							} catch(ElementNotFoundException e) {}
    							
    							nb_explored ++ ;
    							
    							IterDestination.setCost(CurrentLabel.getCost() + data.getCost(ArcIter));
    							IterDestination.setFather(ArcIter);
    							
    							Tas.insert(IterDestination);
    						}
    					}
    				}
    			}
    	}
    	
    	//destination has no predecessors, the solution is infeasible
    	if ((DestinationLabel.getFather()==null && (data.getOrigin().compareTo(data.getDestination()) != 0) ) || !DestinationLabel.getMark()) {
    		System.out.println("Chemin impossible") ;
    		solution = new ShortestPathSolution (data, Status.INFEASIBLE);
    	} else {
    		//Destination found, the observer is informed
    		notifyDestinationReached(data.getDestination());
    		
    		//Empty Path
    		if (data.getOrigin().compareTo(data.getDestination()) == 0) {
    			// final solution
    			System.out.println("Chemin vide");
    			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph)) ;
    		} else {
    			//predecessor path
    			ArrayList<Arc> arcs = new ArrayList<>() ;
    			
    			while (!CurrentLabel.equals(OriginLabel)) {
    				arcs.add(CurrentLabel.getFather());
    				CurrentLabel = ArrayLabels[CurrentLabel.getFather().getOrigin().getId()] ;
    			}
    			
    			//Reverse the path..
    			Collections.reverse(arcs);
    			
    			Path path = new Path(graph, arcs) ;
    			
    			if(path.isValid()) {
    				//final solution
    				solution = new ShortestPathSolution(data, Status.OPTIMAL, path);
    				System.out.println("Chemin Valide") ;
    			} else {
    				System.out.println("Chemin non valide");
    			}
    		}
    	}
    	
    	System.out.println("Nombre Noeuds visités" + nb_explored) ;
    	System.out.println("Nombre Noeuds marqués" + nb_marked) ;
    	
        return solution;
    }

}
