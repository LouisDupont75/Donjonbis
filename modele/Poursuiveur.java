package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Poursuiveur extends AbstractEnnemy implements CarteObserver {

	transient Thread thread;
	transient Runnable poursuite;
	ArrayList<Node> node;
	
	public Poursuiveur(int[] position) {
		super(3, 2.0, position, Color.RED, 0);
	}
	
	public Poursuiveur(int life, Double dmg, int[] position, Color color, int direction) {
		super(life, dmg, position, color, direction);
		this.thread= new Thread(new ThrMoveEnnemy(this));
	}

	@Override
	public void update(ArrayList<Node> node) {
		Node enemyNode=new Node(this.getPositionX(),this.getPositionY());
		this.node=node;
		poursuite=new ThreadPoursuit(enemyNode, new Node(node.get(0).getPosition()[0],node.get(0).getPosition()[1]), node,this);
		thread=new Thread(poursuite);
		/*if(thread.isAlive()){
			thread.interrupt();
		}*/
	}
}
