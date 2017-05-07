package modele;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Personnage extends GameObject implements MoveableObserver { // demisableObservable non?
	private int life; //point de vie pv
	private Double dmg;
	private int direction;
	
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
	@Override
	public void moveThing(Moveable m,int x,int y){};
	
	///

	public abstract GameObject addItem(ArrayList<GameObject> objects,Inventaire inventaire);
	public abstract Bomb dropBomb();
	public abstract void utilize(GameObject object);
	public abstract void dropItem(GameObject object);
	
}
