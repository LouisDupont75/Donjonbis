package modele;

import java.awt.Color;
import java.io.Serializable;


public abstract class GameObject implements Serializable{
	protected int[] position=new int[2];
	protected Color color;
	private boolean demiseState=false;
	private boolean stateObstacle=false;
	private boolean stateAttack=false;
	public GameObject(int [] position, Color color){
		setPositionX(position[0]);
		setPositionY(position[1]);
		setColor(color);
	}
	public boolean getStateDemisable(){
		return this.demiseState;
	}
	public void setStateDemisable(boolean demiseState){
		this.demiseState=demiseState;
	}
	public boolean getStateAttack(){
		return this.stateAttack;
	}
	public void setStateAttack(boolean b){
		this.stateAttack=b;
	}
	public boolean getStateObstacle(){
		return this.stateObstacle;
	}
	public void setStateObstacle(boolean b){
		this.stateObstacle=b;
	}
	public int getPositionX() {
		return this.position[0]; 
	}
	public int getPositionY() {
		return this.position[1];
	}
	
	public void setPositionX(int i) {
		this.position[0]=i;
		
	}
	public void setPositionY(int i) {
		this.position[1]=i;
	}
	
	public Color getColor(){
		return this.color;
	}
	public void setColor(Color color){
		this.color=color;
	}
	public void move(int X, int Y){
		this.setPositionX(this.getPositionX() + X);
		this.position[1] = this.getPositionY() + Y;
	}
	public double distance (GameObject A){
		return Math.sqrt(Math.pow(A.getPositionX()-this.getPositionX(),2)+Math.pow(A.getPositionY()-
				this.getPositionY(), 2));
	}
	public boolean obstacleNextPosition(GameObject go,int x,int y){
		int nextX = this.getPositionX()+x;
		int nextY = this.getPositionY()+y;
		boolean obstacle = false;
		if(go.isAtPosition(new int[]{nextX,nextY})){
				obstacle = go.isObstacle(); 
		}
		return obstacle;
	}
	public boolean objectNextPosition(GameObject go,int x, int y){//précise si le GameObject en paramètre
		// se situe à la position suivante		
				int nextX = this.getPositionX()+x;
				int nextY = this.getPositionY()+y;
				boolean b = false;
				if(go.isAtPosition(new int[]{nextX,nextY})){
					b=true;
			}
			return b;
		}
	       
	public boolean isAtPosition(int [] pos){
		return this.position[0] == pos[0] && this.position[1] == pos[1];
	}
	
	public Bomb dropBomb(){
		System.out.println("blocks ne peuvent pas déposer de bombes");
		return null;
	}
	public void effect(Player player) {// idem
	}
	public void setLife(int life){};
	public int getLife(){//choix arbitraire
		return 1;
	};
	
	public void demisableNotifyObserver(){};
	public  void demisableRemove(DemisableObserver po){};
	public  void demisableAttach(DemisableObserver po){};
	public void addObserver(Observeur o) {};
	public void AttackAttach(AttackObserver ao){};
	public void obstacleAttach(ObstacleObserver ao){};
	public void playerAttackAttach(PlayerAttackObserver pao){};
	public void moveableAttach(MoveableObserver mo){};
	public void setSucceededAttack(boolean b,Player player){};
	
	///Abstracts methods
	public abstract boolean isObstacle();
}

