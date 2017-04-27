package modele;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Personnage extends GameObject { // 
	protected int life; //point de vie pv
	protected Double dmg;
	protected Model model; //utiliser les get et les set au lieu des protected
	//private int numberPlayer;
	/// Constructeur
	public Personnage(int life,Double dmg, int[] position,Color color) {
		super(position,color);
		setLife(life);
		setDmg(dmg);
		
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
	
	
	///
	
	public abstract Bomb dropBomb();
	public abstract void utilize(GameObject object);
	public abstract void addItem(ArrayList<GameObject> objects,Inventaire inventaire);
	public abstract void dropItem(GameObject object);
	
}
