package modele;

import java.awt.Color;

public abstract class Personnage { // /Methodes abstraites encore a rajouter
	//point de vie pv
	protected int pv; 
	protected Color couleur;
	// dmg damage
	protected Double dmg;
	protected int[] position=new int[2];
	
	public Personnage(int pv,Double dmg, int[] position) {
		/*this.pv=3;
		this.setCouleur(Color.BLUE);
		this.dmg= 0.5;
		this.position[0]=0;
		this.position[1]=0;
		*/
		//setPv(pv);
		//setCouleur(this.couleur);
		
	}

	public int getPv() {
		return this.pv;
	}
	public Double getDmg() {
		return this.dmg;
	}
	public void setDmg(Double dmg) {
		this.dmg = dmg;
	}
	public void setPv(int pv) {
		this.pv = pv;
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

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	

}
