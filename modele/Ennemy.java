package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

//import java.util.Iterator;
public class Ennemy extends Personnage implements Demisable,ExplodableObserver {
	private Model model;
	private int numberEnnemy;
	private Thread t;
	protected ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();
	public Ennemy(int life,Double dmg,int[] position,Color color,Model model,int numberEnnemy) {
		super(life,dmg,position,color);
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
	
	public void moveEnnemy(int X, int Y){
		boolean obstacle = false;
		ArrayList<GameObject> list =model.getGameObjects();
		synchronized (list) {
		    for (Iterator it = list.iterator(); it.hasNext(); ) {   //TODO Solve the ConcurrentModificationException
		        GameObject f = (GameObject) it.next();
		        obstacle=this.obstacleNextPosition(f, X, Y);
				if(obstacle == true ){
					break;
				}
		    }
		}
		/*for(GameObject object : model.getGameObjects()){
				obstacle=this.obstacleNextPosition(object, X, Y);
				if(obstacle == true ){
					break;
				}
			}*/
		
		if(obstacle == false ){
			this.move(X, Y);
			model.notifyObserver();
		}
	}	
	///
	@Override
	public void demisableAttach(DemisableObserver po){
		demisableobservers.add(po);
	}
	@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:demisableobservers){
			//System.out.println(po.getClass().getName());
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
	/*@Override
	public void run (){ c'est dans ThrMoveennemy qu'il faut modifier pour faire bouger les ennemis différemment ou utiliser ta classe thread pour le déplacement des ennemis
		try {
			//int count=1;
    		while(true){
    			move(1,0);
    			model.notifyObserver();
    			Thread.sleep(500);
    			move(0,1);
    			model.notifyObserver();
    			Thread.sleep(500);
    			move(-1,0);
    			model.notifyObserver();
    			Thread.sleep(500);
    			move(0,-1);
    			model.notifyObserver();
    			Thread.sleep(500);  
    			//count++;
    		}
		}
		catch(InterruptedException e){
			System.out.println("Ennemi numéro"+ numberEnnemy + "a été stoppé.");
	    }
	}*/

}
