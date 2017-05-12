package modele;

import java.util.ArrayList;

public class ThreadPoursuit implements Runnable {
	
	Pathfinding path;
	Node noeudOccupe;
	GameObject sujet;
	
	public ThreadPoursuit(Node startNode,Node finalNode , ArrayList<Node> node, GameObject sujet) {
		path=new Pathfinding(startNode, finalNode, node);
		this.sujet=sujet;
		Thread t=new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		ArrayList<Node> noeudsAVisiter=path.path();
		for(Node noeudProchain : noeudsAVisiter){
			moveTo(noeudProchain);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Le thread n'a pas pu dormir :'(");
			}
		}
	}

	private void moveTo(Node noeudProchain) {
		System.out.println("moveTo OK");
		if(noeudOccupe.getPosition()[0]-1 == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1] == noeudProchain.getPosition()[1]){//gauche
			sujet.move(-1, 0);
		}else if(noeudOccupe.getPosition()[0] == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1]-1 == noeudProchain.getPosition()[1]){//haut
			sujet.move(0, -1);
		}else if(noeudOccupe.getPosition()[0]+1 == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1] == noeudProchain.getPosition()[1]){//droite
			sujet.move(1, 0);
		}else if(noeudOccupe.getPosition()[0] == noeudProchain.getPosition()[0] && noeudOccupe.getPosition()[1]+1 == noeudProchain.getPosition()[1]){//bas
			sujet.move(0, 1);
		}
	}

}
