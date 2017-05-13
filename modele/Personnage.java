package modele;

import java.awt.Color;
import java.util.ArrayList;


public abstract class Personnage extends GameObject implements ObstacleObserver, Demisable { // demisableObservable non? MoveableObserver,
	//--> Tous les personnages peuvent mourir
	private int life; 
	private Double dmg;
	private int direction;
	protected ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();

	/// Constructeur
	public Personnage(int life,Double dmg, int[] position,Color color,int direction) {
		super(position,color);
		setLife(life);
		setDmg(dmg);
		setDirection(direction);
		
	}
	
	////
	public int getLife() {
		return this.life;
	}
	public void setLife(int life) {
		this.life=life;
		System.out.println("le perso a" +this.getLife()+"vies");
		if(this.getLife()<=0){
			this.demisableNotifyObserver();
			setStateDemisable(true);
			}
		}
	public Double getDmg() {
		return this.dmg;
	}
	public void setDmg(Double dmg) {
		this.dmg = dmg;
	}
	public int getDirection(){
		return this.direction;
	}
	public void setDirection(int direction){
		this.direction=direction;
	}
	public int[] coordinateDirection(int direction){// donne les coordonnees suivant lequel l'attaque sera exercee en
		// fonction de la direction du joueur
		int[] tab=new int[2];
		if (direction==2){
			tab[0]= 1;
			tab[1]=0;
		}
		else if (direction==1){
			tab[0]=0;
			tab[1]=-1;
		}
		else if (direction==0){
			tab[0]=-1;
			tab[1]=0;
		}
		else if (direction==3){
			tab[0]=0;
			tab[1]=1;
		}
		return tab;
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
	
	///

	public abstract GameObject addItem(ArrayList<GameObject> objects,Inventaire inventaire);
	public abstract void utilize(GameObject object);
	public abstract void dropItem(GameObject object);
	
}
