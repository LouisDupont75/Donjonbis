package modele;

import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

//import java.util.Iterator;
public class Ennemy extends AbstractEnnemy{
	private Model model;
	private int numberEnnemy;
	public Ennemy(int life,Double dmg,int[] position,Color color,Model model,int numberEnnemy,int direction) {
		super(life,dmg,position,color,direction);
		this.numberEnnemy=numberEnnemy;
		this.model=model;
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}
	
	public Ennemy(int[] position) {
		super(1,1.0,position,Color.BLUE,0);
		numberEnnemy=1;//TODO changer, on ne sait pas quoi mettre
		t=new Thread(new ThrMoveEnnemy(this));
		t.start();
	}

	public void moveEnnemy(int X, int Y){//TODO : regler la sortie de map pour l'ennemi
		try{
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
	}catch(ConcurrentModificationException e){}	
	}

}
