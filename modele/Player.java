package modele;
import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Player extends Personnage implements MoveableObserver {
	private Model model;
	private boolean bowEquiped=false;
	public Player(int life,Double dmg,int[] position,Color color,Model model,int direction) {
		super(life,dmg,position,color,direction);
		this.model =model;
	}
	public boolean getBowEquiped(){
		return this.bowEquiped;
	}
	public void setBowEquiped(boolean b){
		this.bowEquiped=b;
	}
	public boolean isObstacle (){
		return false;
	}
	public void shootArrow(){
		if(this.getBowEquiped()){
			ArrayList<GameObject> gameobjects=model.getGameObjects();
			Arrow arrow=new Arrow(this.position,Color.WHITE,this,this.model);
			arrow.demisableAttach(model);
			synchronized(gameobjects){
			gameobjects.add(arrow);}
			model.notifyObserver();
			Thread t=new Thread(arrow);
			t.start();
		}	
	}
	public void launchAttack(){ 
		int [] coordinate = this.coordinateDirection(this.getDirection());
		int x = coordinate[0];
		int y = coordinate[1];
		try{
			synchronized(model.getGameObjects()){
				for(GameObject go:model.getGameObjects()){
					int distanceX=go.getPositionX()-this.getPositionX();
					int distanceY=go.getPositionY()-this.getPositionY();

					if(distanceX==x & distanceY==y){
						go.setLife(go.getLife()-1);

						if (go.getLife()<=0){
							go.demisableNotifyObserver();
						}
					}		
				}
			}
		}
		catch(ConcurrentModificationException e){}
	}
	///fonction pas utilisée v 
	public GameObject addItem(ArrayList<GameObject> objects,Inventaire inventaire){// comment lever l'exception du
//NullPointer ici ? 
//TODO: corriger le fait qu'il faille deux demisablenotify pour faire disparaitre l'objet.. Duplication qlq part?
	GameObject item=null;
		for (GameObject object: objects){
			if (object.isAtPosition(this.position)){
				object.demisableNotifyObserver();// Doit etre fait AVANT l'ajout dans l'inventaire sinon l'inventaire
	//supprimera l'objet qu'il vient de rajouter
				object.demisableNotifyObserver();
				object.demisableAttach(inventaire);
				inventaire.addObject(object);
				item=object;
			}
		}
	return item;
	}
	@Override
    public void utilize(GameObject object){ 
		object.effect(this);
	}
    @Override
    public void dropItem(GameObject object){
    	object.setPositionX(this.getPositionX());
    	object.setPositionY(this.getPositionY());
    	object.demisableNotifyObserver();//disparait de l'inventaire
    }
	
	@Override
	public Bomb dropBomb(){
		Bomb bomb = null;
		bomb = new Bomb(position,3000,1,Color.YELLOW); //duration in millisec
		//bomb.demisableAttach(this); // on rajoute le player à la liste des DemisableObserver, meme si on a encore rien
		// ecrit dans demise() pour player
		Thread thread = new Thread(bomb);
		thread.start();
		return bomb;

	}
	@Override
	public void moveThing(Moveable m,int x,int y){
		GameObject block=(GameObject) m; // Cast Evitable ?
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
