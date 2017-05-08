package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Arrow extends GameObject implements Runnable, Demisable {
	private Personnage perso;
	private Model model; // je vois pas faire autrement pour pouvoir acceder a la liste des gameobjecs necessaires 
//dans plusieurs des fonctions de cette classe
	private ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();
	private int nextX;
	private int nextY;
	public Arrow(int [] position,Color color,Personnage perso,Model model){
		super(position,color);
		setPersonnage(perso);
		this.model=model;
		int [] coordinate = this.perso.coordinateDirection(this.perso.getDirection());
		int x = coordinate[0];
		int y = coordinate[1];
		this.nextX=x;
		this.nextY=y;
		
	}
	public Personnage getPersonnage(){
		return this.perso;
	}
	public void setPersonnage(Personnage perso){
		this.perso=perso;
	}
	public boolean isCollision(){
		ArrayList<GameObject> gameobjects= model.getGameObjects();
		boolean b=false;
		synchronized(gameobjects){
			for (GameObject go:gameobjects){
				if(this.obstacleNextPosition(go,nextX,nextY)||(this.objectNextPosition(model.getPlayer(),nextX,nextY))){
					b=true;
					break;
				}
			}
		}	
		return b;
	}
	public GameObject getGoNextPos(int x, int y){
		ArrayList<GameObject> gameobjects= model.getGameObjects();
		GameObject val=null;
		synchronized(gameobjects){
			for (GameObject go:gameobjects){
				if (this.objectNextPosition(go, x, y)){
					val=go;
				}
			}
		}
		return val;
	}
	public void run(){
		while(!this.isCollision()){
			try{
				this.move(this.nextX,this.nextY);
				model.notifyObserver();
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
				e.getMessage();
			}
			
		}
		GameObject go=this.getGoNextPos(this.nextX,this.nextY);
		go.setLife(go.getLife()-1);
		this.demisableNotifyObserver();		
	}
	@Override
	public boolean isObstacle(){
		return false;
	}
	@Override
	public void demisableAttach(DemisableObserver po){
		demisableobservers.add(po);
	}
	@Override
	public void demisableRemove(DemisableObserver po){
		demisableobservers.remove(po);

	};
	@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:demisableobservers){
			po.demise(this, null);
		}
	}}
