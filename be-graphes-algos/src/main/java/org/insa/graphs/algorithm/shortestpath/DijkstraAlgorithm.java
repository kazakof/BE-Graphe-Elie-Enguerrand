package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.AbstractSolution.Status;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph();
        Node arrive =data.getDestination();
        final int nbNodes = graph.size();
        // Initialize array of distances.
        BinaryHeap<Label> Tas=new BinaryHeap<Label>();
        Arc[] predecessorArcs=new Arc[nbNodes];
        Label[] listLabel=new Label[nbNodes];
        for(Node node: graph.getNodes()){
            int id=node.getId();
            listLabel[id]=new Label(node);
        }
        listLabel[data.getOrigin().getId()].setCost(0);
        Tas.insert(listLabel[data.getOrigin().getId()]);

        boolean pasArrive=true;
        while(!Tas.isEmpty() && pasArrive){
            Label x= Tas.deleteMin();
            x.setMark(true);
            /*if(data.getMode()==TIME){
                System.out.println("le cout du sommet marked "+x.getCout()+" secondes");
            }else{
                System.out.println("le cout du sommet marked "+x.getCout()+" metres");
            }*/
            notifyNodeMarked(x.getcurrentNode());
            for(Arc a :graph.get(x.getcurrentNode().getId()).getSuccessors()) {
                if (data.isAllowed(a)) {
                    Label y = listLabel[a.getDestination().getId()];
                    if (!y.getMark()) {
                        if (y.getCost() > x.getCost() + a.getLength()) {
                            if (y.getCost() != Double.POSITIVE_INFINITY) {//le label n'est pas dans le tas si son cout est a l'infini
                                Tas.remove(y);
                            }
                            y.setCost(x.getCost() + a.getLength());
                            Tas.insert(y);
                            notifyNodeReached(a.getDestination());
                            y.setFather(a);
                            predecessorArcs[a.getDestination().getId()] = a;
                            if (y.getcurrentNode().getId() == arrive.getId()) {
                                pasArrive = false;
                            }
                        }
                    }
                }
            }
        }
// Destination has no predecessor, the solution is infeasible...
        if (predecessorArcs[data.getDestination().getId()] == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else {
            // The destination has been found, notify the observers.
            notifyDestinationReached(data.getDestination());

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = predecessorArcs[data.getDestination().getId()];
            while (arc != null) {
                arcs.add(arc);
                arc = predecessorArcs[arc.getOrigin().getId()];
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }

}
