package modele;

import java.awt.Color;
import java.util.ArrayList;

public class BlockMoveable extends BlockBreakable implements MoveableObserver,Obstacle,Observable {
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
     public BlockMoveable(int [] position,Color color,int life){
    	 super(position,color,life);
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
	public void moved(Moveable m,int X,int Y){
		Player player=(Player)m;
		this.setStateObstacle(false);
		int distY=this.getPositionY()-(player.getPositionY()+Y);
		int distX=this.getPositionX()-(player.getPositionX()+X);
		if(distY==0&&distX==0){
			this.obstacleNotifyObserver(X, Y);
			if(!this.getStateObstacle()){
				this.move(X, Y);
				this.notifyObserver();
			}
		}	
	}
}
