package modele;

import java.util.ArrayList;

public class ThreadPoursuit implements Runnable {
	private boolean interruption=false;
	boolean moved;
	Pathfinding path;
	Poursuiveur sujet;
	
	public ThreadPoursuit(Node startNode,Node finalNode , ArrayList<Node> node, Poursuiveur sujet) {
		path=new Pathfinding(startNode, finalNode, node);
		this.sujet=sujet;
		running();
	}
	
	public ThreadPoursuit(Poursuiveur poursuiveur) {
	}

	@Override
	public void run() {
		ArrayList<Node> noeudsAVisiter=path.path();
		noeudsAVisiter.remove(0);
		while(path !=null && !isInterrupted()&&noeudsAVisiter.size()!=0){
			//System.out.println("il reste " +noeudsAVisiter.size()+ " cases a parcourir");
			Node noeudProchain= noeudsAVisiter.get(0);
			moveTo(noeudProchain);
			if(moved){
				noeudsAVisiter.remove(0);
			}
			moved=false;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Le thread n'a pas pu dormir :'(");
			}
		}
	}

	private void moveTo(Node noeudProchain) {
		//System.out.println("moveTo OK");
		if(sujet.getPositionX()-1 == noeudProchain.getPosition()[0] && sujet.getPositionY() == noeudProchain.getPosition()[1]){//gauche
			//System.out.println("Gauche!");
			moved=sujet.moveEnnemy(-1, 0);
		}else if(sujet.getPositionX() == noeudProchain.getPosition()[0] && sujet.getPositionY()-1 == noeudProchain.getPosition()[1]){//haut
			//System.out.println("Haut!");
			moved=sujet.moveEnnemy(0, -1);
		}else if(sujet.getPositionX()+1 == noeudProchain.getPosition()[0] && sujet.getPositionY() == noeudProchain.getPosition()[1]){//droite
			//System.out.println("Droite!");
			moved=sujet.moveEnnemy(1, 0);
		}else if(sujet.getPositionX() == noeudProchain.getPosition()[0] && sujet.getPositionY()+1 == noeudProchain.getPosition()[1]){//bas
			//System.out.println("Bas!");
			moved=sujet.moveEnnemy(0, 1);
		}
	}

	public boolean isInterrupted(){
		return interruption;
	}
	
	public void interrupt(){
		interruption=true;
	}
	
	public void running(){
		interruption=false;
	}

}
