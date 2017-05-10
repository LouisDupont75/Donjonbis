package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Arrow extends GameObject implements Runnable, Demisable,Obstacle,Attack,Observable {
	private Personnage perso;
	private ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();
	private ArrayList<AttackObserver> attackobservers = new ArrayList<AttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private int nextX;
	private int nextY;
	public Arrow(int [] position,Color color,Personnage perso){
		super(position,color);
		setPersonnage(perso);
		int [] coordinate = this.perso.coordinateDirection(this.perso.getDirection());
		int x = coordinate[0];
		int y = coordinate[1];
		this.nextX=x;
		this.nextY=y;
		
	}
	public Personnage getPersonnage(){
		return this.perso;
	}
	public void setPersonnage(Personnage perso){
		this.perso=perso;
	}
	public void moveArrow(int X,int Y){
		this.setStateObstacle(false);
		this.obstacleNotifyObserver(X, Y);
		if(!this.getStateObstacle()){
			this.move(X, Y);
			this.notifyObserver();
		}
		this.AttackNotifyObserver();
	}
	public void run(){
		while(!(this.getStateObstacle()) && !(this.getStateAttack()) ){
			try{
				this.moveArrow(this.nextX,this.nextY);
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
				e.getMessage();
			}
			
		}
		this.demisableNotifyObserver();	
		System.out.println("demisable declenche");
	}
	@Override
	public boolean isObstacle(){
		return false;
	}
	@Override
	public void demisableAttach(DemisableObserver po){
		demisableobservers.add(po);
	}
	@Override
	public void demisableRemove(DemisableObserver po){
		demisableobservers.remove(po);

	};
	@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:demisableobservers){
			po.demise(this, null);
		}
	}
	@Override
	public void AttackAttach(AttackObserver ao){
		attackobservers.add(ao);
	}
	@Override 
	public void AttackNotifyObserver(){
		for(AttackObserver ao:attackobservers){
			ao.attacked(this);
		}
	}
	@Override
    public void obstacleNotifyObserver(int x,int y){
   	 for(ObstacleObserver oo:obstacleobservers){
   		 oo.collision(this,x,y);
   	 	}
    }
	@Override
    public void obstacleAttach(ObstacleObserver oo){
   	 obstacleobservers.add(oo);
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
	public void notifyObserver() {
		for (Observeur observeur:listObserveurs){
			observeur.update();
		}
		
	}
}
