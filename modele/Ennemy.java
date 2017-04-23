package modele;

import java.awt.Color;
import java.util.ArrayList;
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
		Bomb bomb=new Bomb(position);
		return bomb;
	}
	
	public synchronized void move(int X, int Y){
		/*this.position[0] = this.getPositionX() + 2*X;
		this.position[1] = this.getPositionY() + 2*Y;*/
		int nextX=this.getPositionX() + X;
		int nextY=this.getPositionY() + Y;
		boolean obstacle = false;
		for(GameObject object : model.getObjects()){
			if(object.isAtPosition(new int[]{nextX,nextY})){
				obstacle = object.isObstacle();
			}
			if(obstacle == true){
				break;
			}
		}
		if(obstacle == false){
			this.position[0]=nextX;
			this.position[1]=nextY;
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
