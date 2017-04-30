package modele;
import java.awt.Color;
import java.util.ArrayList;
import modele.DemisableObserver;

public class Player extends Personnage implements DemisableObserver,MoveableObserver {
	private Model model;
	private int direction;
	public Player(int life,Double dmg,int[] position,Color color,Model model,int direction) {
		super(life,dmg,position,color,direction);
		this.model =model;
	}
	
	public boolean isObstacle (){
		return false;
	}
	///
	@Override
    public void utilize(GameObject object){ 
		object.effect(this);
		object.demisableNotifyObserver();
	}
    @Override
    public void addItem(ArrayList<GameObject> objects,Inventaire inventaire){
    	for (GameObject object: objects){
			if (object.isAtPosition(this.position)){
				object.demisableNotifyObserver();// Doit etre fait AVANT l'ajout dans l'inventaire sinon l'inventaire
//supprimera l'objet qu'il vient de rajouter
				inventaire.addObject(object);
				object.demisableRemove(this.model);
				model.notifyObserver();
			}
		}
    }
    @Override
    public void dropItem(GameObject object){
    	object.setPositionX(this.getPositionX());
    	object.setPositionY(this.getPositionY());
    	object.demisableNotifyObserver();//disparait de l'inventaire
    	object.demisableAttach(this.model);
    	model.getGameObjects().add(0,object);
    	model.notifyObserver();
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
		GameObject block=(GameObject) m;
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
