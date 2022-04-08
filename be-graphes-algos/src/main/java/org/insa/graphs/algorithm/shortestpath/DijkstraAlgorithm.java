package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
 

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        
        Graph graph = data.getGraph(); //Initialisation
        Label[] listLabel =new Label[graph.size()];//Creation du tableau de Label
        List<Node> listNodes= graph.getNodes();//Recuperer la liste de nodes du graph
        BinaryHeap<Label> Tas = new BinaryHeap<Label>();
        
        for (Node noeud : listNodes) {
        	listLabel[noeud.getId()] = new Label(noeud,false,1.0/0.0);
        }
        
        listLabel[data.getOrigin().getId()].setCost(0.0);
        Tas.insert(listLabel[data.getOrigin().getId()]);
        
        boolean cheminTrouve=false;
        
        while(!Tas.isEmpty() || !cheminTrouve) {
        	
        	Label x = Tas.findMin();
        	Tas.remove(x);
        	x.setMark(true);
        	notifyNodeMarked(x.getcurrentNode());
        	for( Arc a : graph.get(x.getcurrentNode().getId()).getSuccessors()) {
        		Label y = listLabel[a.getDestination().getId()];
        		
        		if(!y.getMark()) {
        			y.setCost(Math.min(y.getCost(),x.getCost()+a.getLength()));
        			if(y.getCost()==x.getCost()+a.getLength()){
        				if(y.getCost()!=1.0/0.0) {
        					Tas.insert(y);
        				}
        				Tas.insert(y);
        				notifyNodeReached(a.getDestination());
        				y.setFather(a);
        				
        			}
 
        		}
        		
        	}
        	if(x.getcurrentNode()==data.getDestination()) {
    			cheminTrouve=true;
    		}
        	
        	
        }
        
        
        
        
        
        
        
        
        
        return solution;
    }

}
