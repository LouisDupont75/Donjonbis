package modele;
import java.awt.Color;
import java.util.ArrayList;
import modele.DemisableObserver;

public class Player extends Personnage implements DemisableObserver,MoveableObserver {
	public Player(int life,Double dmg,int[] position,Color color,Model model) {
		super(life,dmg,position,color);
		this.model =model;
		
	}
	
	public boolean isObstacle (){
		return false;
	}
		///
	
    public void utilize(Object object){ 
		object.effect(this);
	}
    
    public void addItem(ArrayList<Object> objects,Inventaire inventaire){
    	for (Object object: objects){
			if (object.isAtPosition(position)){
				inventaire.addObject(object);
				object.demisableNotifyObserver();
			}
		}
    }
	@Override
	public void demise(Demisable d,ArrayList<GameObject> go){}
	
	@Override
	public Bomb dropBomb(){
		Bomb bomb = null;
		bomb = new Bomb(position); //duration in millisec
		bomb.demisableAttach(this); // on rajoute le player à la liste des DemisableObserver, meme si on a encore rien
		// ecrit dans demise() pour player
		Thread thread = new Thread(bomb);
		thread.start();
		return bomb;

	}
	@Override
	public void moveThing(Moveable m,int x,int y){
		BlockBreakable block=(BlockBreakable) m;
		boolean obstacle =false;
		synchronized(model.getGameObjects()){
		for(GameObject object : model.getGameObjects()){
			obstacle=block.obstacleNextPosition(object, x, y);
			if(obstacle==true){
				break;
			}
		}
		if(obstacle== false){
			block.move(x, y);
			this.move(x, y);
			model.notifyObserver();
		}
		
	}
	}
	
	

}
