package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

//import java.util.Iterator;
public class Ennemy extends AbstractEnnemy implements Attack,Obstacle,Observable{
	private transient Thread t;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<AttackObserver> attackobservers = new ArrayList<AttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	public Ennemy(int life,Double dmg,int[] position,Color color,int direction) {

		super(life,dmg,position,color,direction);
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}
	
	public Ennemy(int[] position) {
		super(1,1.0,position,Color.BLUE,0);
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}
	public void moveEnnemy(int X, int Y){//TODO : ajouter l'attackObserver
		this.setStateObstacle(false);
		this.obstacleNotifyObserver(X, Y);
		if(!this.getStateObstacle()){
			this.move(X, Y);
			this.notifyObserver();
		}
		this.AttackNotifyObserver(X,Y);
	}	
	///
	@Override
	public GameObject addItem(ArrayList<GameObject> objects,Inventaire inventaire){
		return null;
	};
	@Override
	public void utilize (GameObject object){}
	@Override 
	public void dropItem(GameObject object){};
	@Override
	public void exploded(Explodable e){
		Bomb bomb =(Bomb) e; // Downcast explodable--> Bomb
		boolean dist = this.distance(bomb) <=bomb.getRange();
		if(dist){
			this.demisableNotifyObserver();
			
		}
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
