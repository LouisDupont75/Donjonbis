package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

//import java.util.Iterator;
public class Ennemy extends Personnage implements Demisable,ExplodableObserver {
	private Model model;
	private int numberEnnemy;
	private Thread t;
	protected ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();
	public Ennemy(int life,Double dmg,int[] position,Color color,Model model,int numberEnnemy,int direction) {
		super(life,dmg,position,color,direction);
		this.numberEnnemy=numberEnnemy;
		this.model=model;
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}
	
	
	public boolean isObstacle (){
		return true;
	}
	
	public Bomb dropBomb (){
		return null;//les ennemis ne lachent pas encore de bombes
	}
	
	public void moveEnnemy(int X, int Y){//TODO : regler la sortie de map pour l'ennemi
		try{
		boolean obstacle = false;
		ArrayList<GameObject> list =model.getGameObjects();
		synchronized (list) {
		    for (GameObject go:list ) {   
		        obstacle=this.obstacleNextPosition(go, X, Y);
				if(obstacle == true ){
					break;
				}
		    }
		}
		if(obstacle == false ){
			this.move(X, Y);
			model.notifyObserver();
		}
	}catch(ConcurrentModificationException e){}	
	}	
	///
	@Override
	public void addItem(ArrayList<GameObject> objects,Inventaire inventaire){};
	@Override
	public void utilize (GameObject object){}
	@Override 
	public void dropItem(GameObject object){};
	@Override
	public void demisableAttach(DemisableObserver po){
		demisableobservers.add(po);
	}
	@Override
	public void demisableRemove(DemisableObserver po){};
	@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:demisableobservers){
			po.demise(this, null);
		}
	}
	@Override
	public void exploded(Explodable e){
		Bomb bomb =(Bomb) e; // Downcast explodable--> Bomb
		boolean dist = this.distance(bomb) <=bomb.getRange();
		if(dist){
			this.demisableNotifyObserver();
			
		}
	}

}
