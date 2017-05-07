package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Archer extends Personnage implements Runnable {
	private Model model;
	public Archer(int life,Double dmg,int[] position,Color color,int direction,Model model){
		super(life,dmg,position,color,direction);
		this.model=model;
		Thread t=new Thread(this);
		t.start();
	}
	public Model getModel(){
		return this.model;
	}
	public void changeDirection(){
		if(this.getDirection()<4){
			this.setDirection(this.getDirection()+1);
		}
		else if (this.getDirection()==4){
			this.setDirection(1);
		}
	}
	public void shootArrow(){
		ArrayList<GameObject> gameobjects=model.getGameObjects();
		Arrow arrow=new Arrow(this.position,Color.WHITE,this);
		arrow.demisableAttach(model);
		synchronized(gameobjects){
		gameobjects.add(arrow);}
		model.notifyObserver();
		Thread t=new Thread(arrow);
		t.start();
	}
	@Override
	public Bomb dropBomb (){
		return null;
	}
	@Override
	public void utilize (GameObject object){}
	@Override
	public GameObject addItem(ArrayList<GameObject> objects, Inventaire inventaire) {
		return null;
	}
	@Override
	public void dropItem(GameObject object) {}
	@Override
	public void demisableNotifyObserver(){ //TODO:faire tomber un arc lorsque l'archer meurt
		for(DemisableObserver po:demisableobservers){
			po.demise(this, null);
			this.setStateDemisable(true);
		}
	}
	@Override
	public boolean isObstacle(){
		return true;
	}
	@Override 
	public void run(){
		while(!this.getStateDemisable()){
			try{
				this.changeDirection();
				this.shootArrow();
				Thread.sleep(2000);
			}catch(InterruptedException e){
				e.printStackTrace();
				e.getMessage();
			}
		}
		
	}
}
