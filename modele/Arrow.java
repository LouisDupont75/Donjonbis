package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Arrow extends GameObject implements Runnable, Demisable {
	private Archer archer;
	private ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();
	private int nextX;
	private int nextY;
	public Arrow(int [] position,Color color,Archer archer){
		super(position,color);
		setArcher(archer);
		int [] coordinate = this.archer.coordinateDirection(this.archer.getDirection());
		int x = coordinate[0];
		int y = coordinate[1];
		this.nextX=x;
		this.nextY=y;
		
	}
	public Archer getArcher(){
		return this.archer;
	}
	public void setArcher(Archer archer){
		this.archer=archer;
	}
	public boolean isCollision(){
		ArrayList<GameObject> gameobjects= this.archer.getModel().getGameObjects();
		boolean b=false;
		synchronized(gameobjects){
			for (GameObject go:gameobjects){
				if(this.obstacleNextPosition(go,nextX,nextY)||(this.objectNextPosition(archer.getModel().getPlayer(),nextX,nextY))){
					b=true;
					break;
				}
			}
		}	
		return b;
	}
	public GameObject getGoNextPos(int x, int y){
		ArrayList<GameObject> gameobjects= this.archer.getModel().getGameObjects();
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
				archer.getModel().notifyObserver();
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
