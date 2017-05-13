package modele;
import java.util.ArrayList;
import java.util.Collections;

// Basé sur l'algorithme A*
public class Pathfinding {
	//ATTRIBUTES
	private ArrayList<Node> map = new ArrayList<Node>(); //Noeuds du champ de vision
	private ArrayList<Node> reachable = new ArrayList<Node>(); //Noeuds atteignables 
	private ArrayList<Node> explored = new ArrayList<Node>();  //Noeuds dï¿½jï¿½ considï¿½rï¿½s
	private Node finishNode;
	
	//CONSTRUCTOR
	public Pathfinding(Node startNode, Node finishNode,ArrayList<Node>map){
		//System.out.println("map size: "+map.size());
		reachable.add(startNode);
		startNode.setCost(0);
		this.setFinishNode(finishNode);
		this.map = map;
	}
	/**
	 * Construit une liste de noeud correspondant au chemin suivi jusqu'a node
	 * @param node: position dont on veut connaître l'historique
	 * @return  liste des noeuds parcouru, du noeud en paramètre jusqu'au noeud de début
	 */
	private ArrayList<Node> buildThePath(Node node){
		ArrayList<Node> path=new ArrayList<Node>();
		while(node!=null){
			path.add(node);
			node = node.getPrevious();
			//System.out.println("previous node position: "+ node.getPosition()[0]+ " "+node.getPosition()[1]);
		}
		return path;
	}
	/**
	 * Trouve le chemin le plus court entre startNode et finishNode
	 * @return Liste de noeuds a parcourir pour rejoindre la destination
	 */
	public ArrayList<Node> path(){
		ArrayList<Node> path = null;
		while(reachable.size()!=0){
			Node node=chooseNode();
			//System.out.println("node position: "+node.getPosition()[0]+ " "+node.getPosition()[1]);
			//System.out.println("current node = " + node.getPosition()[0]+ " " + node.getPosition()[1]);
			//System.out.println("size of reachable = " + reachable.size());
			if(node==this.getFinishNode()){
				path = buildThePath(node);
				//System.out.println("found it! :3");
				break;
			}
			reachable.remove(node);
			explored.add(node);
			ArrayList<Node> newReachable=getAdjacent(node);
			
			//for(int i=0;i<newReachable.size()-1;i++)
			//	System.out.println("new node at "+newReachable.get(i).getPosition()[0]+ " "+newReachable.get(i).getPosition()[1]);
			
			for(Node adjacent: newReachable){
				if(!isReachable(adjacent)){
					reachable.add(adjacent);
				}
				if(node.getCost()+node.getBondValue()<adjacent.getCost()){
					adjacent.setPrevious(node);
					adjacent.setCost(node.getCost()+1);
				}
			}
		}
		//System.out.println("path size: "+path.size());
		Collections.reverse(path);
		return path;
	}
	
	private boolean isReachable(Node adjacent) {
		boolean check = false;
		for(Node node :this.reachable){
			if(adjacent==node){
				check = true;
				//System.out.println("not legit !");
			}
		}
		return check;
	}
	private boolean isExplored(Node potential) {
		boolean check = false;
		for(Node node :this.explored){
			if(potential == node){
				check=true;
				//System.out.println("not legit !");
			}
		}
		return check;
	}
	/**
	 * cherche les noeuds adjacent a node
	 * @param node noeud dont on cherche la proximité
	 * @return liste de noeuds adjacent a node
	 */
	private ArrayList<Node> getAdjacent(Node node) {
		//System.out.println("node position: "+node.getPosition()[0]+ " "+node.getPosition()[1]);
		ArrayList<Node>adjacent = new ArrayList<Node>();
		for(Node potential:this.map){
			if(((potential.getPosition()[0]==node.getPosition()[0]+1 && potential.getPosition()[1]==node.getPosition()[1])  ||
					(potential.getPosition()[0]==node.getPosition()[0]-1 && potential.getPosition()[1]==node.getPosition()[1]) ||
					(potential.getPosition()[1]==node.getPosition()[1]+1 && potential.getPosition()[0]==node.getPosition()[0]) ||
					(potential.getPosition()[1]==node.getPosition()[1]-1 && potential.getPosition()[0]==node.getPosition()[0]))&&
					(!isExplored(potential))){
				adjacent.add(potential);
			}
		}
		return adjacent;
	}
	/**
	 * choisi le noeud qui minimise la distance [début-noeud] + [noeud-fin (distance manhattan)]
	 * @return noeud le moins couteux en déplaement
	 */
	private Node chooseNode() {
		int minCost = 1000000;
		Node bestNode = null;
		for(Node node:this.reachable){
			int costStartToNode=node.getCost();
			//System.out.println(costStartToNode);
			int costNodeToGoal=calculateDistance(node,this.getFinishNode());
			int totalCost = costStartToNode + costNodeToGoal;
			if(minCost>totalCost){
				//System.out.println("reachable found!");
				minCost = totalCost;
				bestNode = node;
			}
		}
		return bestNode;
	}
	private int calculateDistance(Node node, Node finishNode) {
		int dist = 0;
		//return Math.abs(node.getPosition()[0]-finishNode.getPosition()[0])+Math.abs(node.getPosition()[1]-finishNode.getPosition()[1]);
		dist = euclideanDistance(node.getPosition(), finishNode.getPosition());
		return dist;
	}
	private int euclideanDistance(int[] position, int[] position2) {
		return (int) (Math.sqrt(Math.pow(position2[1]-position[1],2) + Math.pow(position[0]-position2[0],2)));
	}
	public Node getFinishNode() {
		return finishNode;
	}
	public void setFinishNode(Node finishNode) {
		this.finishNode = finishNode;
	}

}
