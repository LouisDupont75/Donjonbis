package modele;
import java.awt.Color;
import java.util.ArrayList;

public class Player extends Personnage implements Moveable,AttackObserver,Creation,PlayerAttack,Obstacle,AddInventaire, PlayerMovement{
	private boolean bowEquiped=false;
	private Inventaire inventaire;
	private boolean keyboardInversion=false;
	private int direction;
	private int life;
	private ArrayList<PlayerMovementObserver> playerMovementObserverList = new ArrayList<>();
	private ArrayList<MoveableObserver> moveableobservers =new ArrayList<MoveableObserver>();
	private ArrayList<AddInventaireObserver> inventaireobservers =new ArrayList<AddInventaireObserver>();
	private ArrayList<CreationObserver> creationobservers = new ArrayList<CreationObserver>();
	private ArrayList<PlayerAttackObserver> playerattackobservers = new ArrayList<PlayerAttackObserver>();
	private ArrayList<ObstacleObserver> obstacleobservers = new ArrayList<ObstacleObserver>();
	public Player(int life,Double dmg,int[] position,Color color,int direction) {
		super(life,dmg,position,color,direction);
		Inventaire invent = new Inventaire();
 		setInventaire(invent);
	}
	public int getDirection(){
		return this.direction;
	}
	public void setDirection(int direction){
		this.direction=direction;
		if (getKeyboardInversion()){
			this.direction=switchDirection(direction);
		}
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
	public int switchDirection(int direction){
		int val=0;
		if(direction==1){
			val=3;
		}
		else if(direction==3){
			val=1;
		}
		else if(direction==0){
			val=2;
		}
		else if(direction==2){
			val=0;
		}
		return val;
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
			this.notifyPlayerMovementObserver();
		}
	}
	public void launchAttack(){
			int [] coordinate = this.coordinateDirection(this.getDirection());
			int x = coordinate[0];
			int y = coordinate[1];
			this.playerAttackNotify(x,y);
		}
	public void addToInventaire(Object object){
		object.demisableAttach(inventaire);
		this.inventaire.addObject(object);
	}
	///
	public void addItem(){
		this.addInventaireNotify();
	}
	public Inventaire getInventaire(){
		return inventaire;
	}
	public void setInventaire(Inventaire inventaire){
		this.inventaire=inventaire;
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
	@Override
	public void addPlayerMovementObserver(Model model) {
		playerMovementObserverList.add(model);
	}
	@Override
	public void notifyPlayerMovementObserver() {
		playerMovementObserverList.get(0).updatePlayerMovement();
	}
    @Override
    public void addInventaireAttach(AddInventaireObserver aio){
    	inventaireobservers.add(aio);
    }
    @Override
    public void addInventaireNotify(){
    	for(AddInventaireObserver aio:inventaireobservers){
    		aio.added(this);
    	}
    }
}
