package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Arrow extends GameObject implements Runnable, Demisable,Obstacle,Attack,Observable,ObstacleObserver
,PlayerAttack {
	private Personnage perso;
	private boolean equipedByPlayer=false;
	private boolean succeededAttack=false;
	private ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();
	private ArrayList<PlayerAttackObserver> playerattackobservers = new ArrayList<PlayerAttackObserver>();
	private ArrayList<AttackObserver> attackobservers = new ArrayList<AttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private int nextX;
	private int nextY;
	public Arrow(int [] position,Color color,Personnage perso){
		super(position,color);
		setPersonnage(perso);
		int [] coordinate = this.perso.coordinateDirection(this.perso.getDirection());
		int x = coordinate[0];
		int y = coordinate[1];
		this.nextX=x;
		this.nextY=y;
		
	}
	//Redefinition de cette methode par rapport à celle dans GameObject
	public boolean getSucceededAttack(){
		return this.succeededAttack;
	}
	public void setSucceededAttack(boolean b,Player player){
		this.succeededAttack=b;
		if(this.getStateAttack()){
			InversionKeyboard inversion=new InversionKeyboard(player,5000);//duration in millisec
		}
	}
	public boolean getEquipedByPlayer(){
		return this.equipedByPlayer;
	}
	public void setEquipedByPlayer(boolean b){
		this.equipedByPlayer=b;
	}
	public Personnage getPersonnage(){
		return this.perso;
	}
	public void setPersonnage(Personnage perso){
		this.perso=perso;
	}
	public void moveArrow(int X,int Y){
		this.setStateObstacle(false);
		this.obstacleNotifyObserver(X, Y);
		if(!this.getStateObstacle()){
			this.move(X, Y);
			this.notifyObserver();
		}
		this.AttackNotifyObserver(X,Y);
		if(this.equipedByPlayer){
			this.playerAttackNotify(X, Y);
		}
	}
	public void run(){
		while(!(this.getStateObstacle()) && !(this.getStateAttack()) ){
			try{
				this.moveArrow(this.nextX,this.nextY);
				Thread.sleep(200);
			}catch(InterruptedException e){
				e.printStackTrace();
				e.getMessage();
			}
			
		}
		this.demisableNotifyObserver();	
	}
	@Override
	public boolean isObstacle(){
		return true;
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
		this.setStateDemisable(true);
	}
	@Override
	public void AttackAttach(AttackObserver ao){
		attackobservers.add(ao);
	}
	@Override 
	public void AttackNotifyObserver(int x,int y){
		for(AttackObserver ao:attackobservers){
			ao.attacked(this,x,y);
		}
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
	public void playerAttackAttach(PlayerAttackObserver pao){
		playerattackobservers.add(pao);
	}
	@Override
	public void playerAttackNotify(int x,int y){
		for(PlayerAttackObserver pao:playerattackobservers){
			pao.attackedByPlayer(this,x,y);
		}
	}
	@Override
    public void collision(Obstacle o,int x,int y){
		if(!this.getStateDemisable()){
    		GameObject go=(GameObject) o;
			int distX=this.getPositionX()-(go.getPositionX()+x);
			int distY=this.getPositionY()-(go.getPositionY()+y);
			if(distX==0 && distY==0){
				go.setStateObstacle(true);
			}
		}	
	}
}
