package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

//import java.util.Iterator;
public class Ennemy extends AbstractEnnemy implements ExplodableObserver,Attack,Obstacle,ObstacleObserver,Observable
,PlayerAttackObserver{
	private transient Thread t;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<AttackObserver> attackobservers = new ArrayList<AttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	public Ennemy(int life,Double dmg,int[] position,Color color,int direction) {

		super(life,dmg,position,color,direction);
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
		System.out.println("thread ennemi initie");
	}
	
	public Ennemy(int[] position) {
		super(1,1.0,position,Color.CYAN,0);
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}
	public void moveEnnemy(int X, int Y){
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
    public void collision(Obstacle o,int x,int y){
		if(!this.getStateDemisable()){
    		GameObject go=(GameObject) o;
			int distX=this.getPositionX()-(go.getPositionX()+x);
			int distY=this.getPositionY()-(go.getPositionY()+y);
			//System.out.println(distX + "et" + distY);
			if(distX==0 && distY==0){
				go.setStateObstacle(true);
			}
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
	@Override
	public void attackedByPlayer(PlayerAttack pa,int x,int y){
		GameObject attacker=(GameObject)pa;
		int distX=this.getPositionX()-(attacker.getPositionX()+x);
		int distY=this.getPositionY()-(attacker.getPositionY()+y);
		if(distX==0 && distY==0){
			this.setLife(this.getLife()-1);
			attacker.setStateAttack(true);

		}
	}
}
