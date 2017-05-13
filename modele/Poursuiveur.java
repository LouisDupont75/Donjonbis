package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Poursuiveur extends AbstractEnnemy implements CarteObserver,Attack,Observable,Obstacle {

	transient Thread thread;
	transient ThreadPoursuit poursuite;
	ArrayList<Node> node;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<AttackObserver> attackobservers = new ArrayList<AttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	
	public Poursuiveur(int[] position) {
		super(3, 2.0, position, Color.RED, 0);
		poursuite=new ThreadPoursuit(this);
		//this.thread= new Thread(new ThrMoveEnnemy(this));
		//thread.start();
	}
	
	public Poursuiveur(int life, Double dmg, int[] position, Color color, int direction) {
		super(life, dmg, position, color, direction);
		//this.thread= new Thread(new ThrMoveEnnemy(this));
		//thread.start();
	}

	@Override
	public void update(ArrayList<Node> node, int[]finishPos) {
		//System.out.println("coucou");
		Node playerNode=null;
		Node enemyNode=null;
		this.node=node;
		for(Node test:node){
			if(test.getPosition()[0]==finishPos[0] && test.getPosition()[1]==finishPos[1]){
				playerNode = test;
			}
			if(test.getPosition()[0]==this.getPositionX() && test.getPosition()[1]==this.getPositionY()){
				enemyNode=test;
			}
		}
		if(!poursuite.isInterrupted()){
			poursuite.interrupt();
		}
		poursuite=new ThreadPoursuit(enemyNode,playerNode,node,this);
		thread=new Thread(poursuite);
		thread.start();
	}

	public boolean moveEnnemy(int X, int Y){//TODO : ajouter l'attackObserver
		boolean succes = false;
		this.setStateObstacle(false);
		this.obstacleNotifyObserver(X, Y);
		if(!this.getStateObstacle()){
			this.move(X, Y);
			this.notifyObserver();
			succes=true;
		}
		this.AttackNotifyObserver(X,Y);
		return succes;
	}	
	@Override
	public void AttackAttach(AttackObserver ao){
		attackobservers.add(ao);
	}
	@Override 
	public void AttackNotifyObserver(int x,int y){
		for(AttackObserver ao:attackobservers){
			ao.attacked(this,x,y);
		}
	}
	@Override
    public void obstacleAttach(ObstacleObserver oo){
   	 obstacleobservers.add(oo);
    }
    @Override
    public void obstacleNotifyObserver(int x,int y){
   	 for(ObstacleObserver oo:obstacleobservers){
   		 oo.collision(this,x,y);
   	 }
    }
    @Override
	public void addObserver(Observeur o) {
	     listObserveurs.add(o);
	}

	@Override
	public void deleteObserver(Observeur o) {
		listObserveurs.remove(o);
	}
	@Override
	public void clearObserver(){
		listObserveurs.clear();
	}
	@Override
	public void notifyObserver() {
		for (Observeur observeur:listObserveurs){
			observeur.update();
		}
	}

}
