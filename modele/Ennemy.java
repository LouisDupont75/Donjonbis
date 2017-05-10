package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

//import java.util.Iterator;
public class Ennemy extends Personnage implements Demisable,ExplodableObserver,Attack,Obstacle,ObstacleObserver,Observable {
	private transient Thread t;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<AttackObserver> attackobservers = new ArrayList<AttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	public Ennemy(int life,Double dmg,int[] position,Color color,int direction) {
		super(life,dmg,position,color,direction);
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}
	
	public boolean isObstacle (){
		return true;
	}
	
	public Bomb dropBomb (){
		return null;//les ennemis ne lachent pas encore de bombes
	}
	public void moveEnnemy(int X, int Y){//TODO : ajouter l'attackObserver
		this.setStateObstacle(false);
		this.obstacleNotifyObserver(X, Y);
		if(!this.getStateObstacle()){
			this.move(X, Y);
			this.notifyObserver();
		}
		this.AttackNotifyObserver();
		//System.out.println(obstacleobservers.size());
		/*try{
		boolean obstacle = false;
		ArrayList<GameObject> list =this.model.getGameObjects();
		synchronized (list) {
		    for (GameObject go:list ) {   
		        obstacle=this.obstacleNextPosition(go, X, Y);
				if(obstacle == true ){
					break;
				}
				else if((go==this.model.getPlayer()&& this.objectNextPosition(go,X,Y))){
					go.setLife(go.getLife()-1);
				}
				else if((go instanceof Arrow && this.objectNextPosition(go, X, Y))){
					this.setLife(this.getLife()-1);
				}
		    }
		}
		if(obstacle == false ){
			this.move(X, Y);
			model.notifyObserver();
		}
	}catch(ConcurrentModificationException e){}	*/
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
	public void AttackNotifyObserver(){
		for(AttackObserver ao:attackobservers){
			ao.attacked(this);
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
		GameObject go=(GameObject) o;
		int distX=this.getPositionX()-(go.getPositionX()+x);
		int distY=this.getPositionY()-(go.getPositionY()+y);
		//System.out.println(distX + "et" + distY);
		if(distX==0 && distY==0){
			go.setStateObstacle(true);
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
	public void notifyObserver() {
		for (Observeur observeur:listObserveurs){
			observeur.update();
		}
		
	}
}
