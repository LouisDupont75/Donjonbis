package modele;
import java.awt.Color;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class Player extends Personnage implements Moveable,AttackObserver,Creation,Observable,PlayerAttack,Obstacle {
	private boolean bowEquiped=false;
	private boolean keyboardInversion=false;
	private int life;
	private ArrayList<MoveableObserver> moveableobservers =new ArrayList<MoveableObserver>();
	private ArrayList<CreationObserver> creationobservers = new ArrayList<CreationObserver>();
	private ArrayList<PlayerAttackObserver> playerattackobservers = new ArrayList<PlayerAttackObserver>();
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	public Player(int life,Double dmg,int[] position,Color color,int direction) {
		super(life,dmg,position,color,direction);
		//setLife(life);
	}
	public boolean getKeyboardInversion(){
		return this.keyboardInversion;
	}
	public void setKeyboardInversion(boolean b){
		this.keyboardInversion=b;
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
	public int getLife() {
		return this.life;
	}
	public void setLife(int life){
		this.life=life;
		System.out.println("le perso a" +this.getLife()+"vies");
		if(this.getLife()<=0){
			System.out.println("GAME OVER");
			}
	}
	public void shootArrow(){
		if(this.getBowEquiped()){
			Arrow arrow=new Arrow(this.position,Color.WHITE,this);
			creationNotifyObserver(arrow);
			arrow.setEquipedByPlayer(true);
			Thread t=new Thread(arrow);
			t.start();
		}	
	}
	public void movePlayer(int X,int Y){
		this.moveableNotifyObserver(X, Y);
		this.setStateObstacle(false);
		this.obstacleNotifyObserver(X, Y);
		if(!this.getStateObstacle()){
			if(this.getKeyboardInversion()){
				this.move(-X,-Y);
			}
			else{
				this.move(X, Y);
			}
			this.notifyObserver();
		}
	}
	public void launchAttack(){ 
			int [] coordinate = this.coordinateDirection(this.getDirection());
			int x = coordinate[0];
			int y = coordinate[1];
			this.playerAttackNotify(x,y);
		}
	///
	public GameObject addItem(ArrayList<GameObject> objects,Inventaire inventaire){
	GameObject item=null;
		for (GameObject object: objects){
			if (object.isAtPosition(this.position)){
				object.demisableNotifyObserver();// Doit etre fait AVANT l'ajout dans l'inventaire sinon l'inventaire
	//supprimera l'objet qu'il vient de rajouter
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
		Thread thread = new Thread(bomb);
		thread.start();
		return bomb;
	}
	@Override
	public void attacked(Attack a,int x,int y){
		GameObject go=(GameObject) a;
		int distX=this.getPositionX()-go.getPositionX();
		int distY=this.getPositionY()-go.getPositionY();
		if(distX==0&&distY==0){
			this.setLife(this.getLife()-1);
			go.setStateAttack(true);
			go.setSucceededAttack(true, this);
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
    public void moveableAttach(MoveableObserver mv){
   	 moveableobservers.add(mv);
    }
    @Override
    public void moveableNotifyObserver(int x,int y){
   	 for(MoveableObserver mo:moveableobservers){
   		 mo.moved(this,x,y);
   	 }
    }
}
