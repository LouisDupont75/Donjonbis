package modele;
import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import modele.DemisableObserver;

public class Player extends Personnage implements DemisableObserver,MoveableObserver {
	private Model model;
	public Player(int life,Double dmg,int[] position,Color color,Model model,int direction) {
		super(life,dmg,position,color,direction);
		this.model =model;
	}
	
	public boolean isObstacle (){
		return false;
	}
	public int[] coordinateDirection(int direction){// donne les coordonnees suivant lequel l'attaque sera exercee en
		// fonction de la direction du joueur
		int[] tab=new int[2];
		if (direction==1){
			tab[0]= 1;
			tab[1]=0;
		}
		else if (direction==2){
			tab[0]=0;
			tab[1]=-1;
		}
		else if (direction==3){
			tab[0]=-1;
			tab[1]=0;
		}
		else if (direction==4){
			tab[0]=0;
			tab[1]=1;
		}
		return tab;
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

	@Override
	public void demisableNotifyObserver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void demisableRemove(DemisableObserver po) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void demisableAttach(DemisableObserver po) {
		// TODO Auto-generated method stub
		
	}

	
}
