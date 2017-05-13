package modele;

import java.util.ArrayList;

public class ThreadPoursuit implements Runnable {
	private boolean interruption=false;
	Pathfinding path;
	Node noeudOccupe;
	Poursuiveur sujet;
	
	public ThreadPoursuit(Node startNode,Node finalNode , ArrayList<Node> node, Poursuiveur sujet) {
		path=new Pathfinding(startNode, finalNode, node);
		this.noeudOccupe=startNode;
		this.sujet=sujet;
		running();
	}
	
	public ThreadPoursuit(Poursuiveur poursuiveur) {
	}

	@Override
	public void run() {
		ArrayList<Node> noeudsAVisiter=path.path();
		while(path !=null && !isInterrupted()&&noeudsAVisiter.size()!=0){
			System.out.println("il reste " +noeudsAVisiter.size()+ " cases a parcourir");
			Node noeudProchain= noeudsAVisiter.get(0);
			noeudsAVisiter.remove(0);
			moveTo(noeudProchain);
			noeudOccupe=noeudProchain;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				System.out.println("Le thread n'a pas pu dormir :'(");
			}
		}
	}

	private void moveTo(Node noeudProchain) {
		//System.out.println("coucou");
		if(noeudOccupe.getPosition()[0]-1 == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1] == noeudProchain.getPosition()[1]){//gauche
			System.out.println("Gauche!");
			sujet.moveEnnemy(-1, 0);
		}else if(noeudOccupe.getPosition()[0] == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1]-1 == noeudProchain.getPosition()[1]){//haut
			System.out.println("Haut!");
			sujet.moveEnnemy(0, -1);
		}else if(noeudOccupe.getPosition()[0]+1 == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1] == noeudProchain.getPosition()[1]){//droite
			System.out.println("Droite!");
			sujet.moveEnnemy(1, 0);
		}else if(noeudOccupe.getPosition()[0] == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1]+1 == noeudProchain.getPosition()[1]){//bas
			System.out.println("Bas!");
			sujet.moveEnnemy(0, 1);
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
