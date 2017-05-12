package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Archer extends Personnage implements Runnable,ObstacleObserver,Creation,PlayerAttackObserver  {
	private Bow bow;
	private transient Thread t;
	private ArrayList<CreationObserver> creationobservers = new ArrayList<CreationObserver>();
	public Archer(int life,Double dmg,int[] position,Color color,int direction){
		super(life,dmg,position,color,direction);
		this.bow=new Bow(new int[]{this.getPositionX(),this.getPositionY()},Color.ORANGE);
		t=new Thread(this);
		t.start();
	}
	/*public Archer(int[] position) {
		super(3,1.0,position,Color.BLUE,0);
		this.bow=new Bow(new int[]{this.getPositionX(),this.getPositionY()},Color.ORANGE);
		//model.getObjects().add(bow);//Ajout à la liste d'objets destinés à l'inventaire
		//bow.demisableAttach(model);
		Thread t=new Thread(this);
		t.start();
	}*/
	public void changeDirection(){
		if(this.getDirection()<3){
			this.setDirection(this.getDirection()+1);
		}
		else if (this.getDirection()==3){
			this.setDirection(0);
		}
	}
	public void shootArrow(){
		Arrow arrow=new Arrow(this.position,Color.WHITE,this);
		creationNotifyObserver(arrow);
		Thread t=new Thread(arrow);
		t.start();
	}
	@Override
	public void demisableNotifyObserver(){ //TODO:faire tomber un arc lorsque l'archer meurt
		ArrayList<GameObject> equipment=new ArrayList<GameObject>();
		equipment.add(bow);
		for(DemisableObserver po:demisableobservers){
			po.demise(this,equipment );
		}
		this.setStateDemisable(true);
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
	@Override
	public void collision(Obstacle o,int x,int y){
		if((!this.getStateDemisable())){
			GameObject go=(GameObject) o;
			int distX=this.getPositionX()-(go.getPositionX()+x);
			int distY=this.getPositionY()-(go.getPositionY()+y);
			//System.out.println(distX + "et" + distY);
			if(distX==0 && distY==0){
				go.setStateObstacle(true);
			}
		}	
	}
	@Override
	public void creationAttach(CreationObserver co){
		creationobservers.add(co);
	}
	@Override
	public void creationNotifyObserver(GameObject go){
		for(CreationObserver co:creationobservers){
			co.initializeCreation(go);
		}
	}
	@Override
	public void attackedByPlayer(PlayerAttack pa,int x,int y){
		GameObject attacker=(GameObject)pa;
		int distX=this.getPositionX()-(attacker.getPositionX()+x);
		int distY=this.getPositionY()-(attacker.getPositionY()+y);
		if(distX==0 && distY==0){
			this.setLife(this.getLife()-1);
			attacker.setStateAttack(true);
		}
	}
}
