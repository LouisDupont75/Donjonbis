package modele;

public class Node {
	private int cost=100000; //un grand nombre peu importe infini
	private Node previous;
	private boolean discovered;
	private int [] position;
	private int bondValue;
	
	public Node(int x,int y) {
		position = new int [] {x,y};
		discovered= false;
		previous = null;
		bondValue=1; // coûte 1 pour aller vers la case
	}
	public Node(int x, int y , int bondValue) {
		this(x, y);
		this.bondValue = bondValue; //bondValue change juste de valeur
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Node getPrevious() {
		return previous;
	}
	public void setPrevious(Node previous) {
		this.previous = previous;
	}
	public boolean isDiscovered() {
		return discovered;
	}
	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}
	public int[] getPosition() {
		return position;
	}
	public int getBondValue() {
		return bondValue;
	} 
	
	

}
