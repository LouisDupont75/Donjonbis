package modele;

import java.awt.Color;

public class Ennemy1 extends Ennemy{
	private Model model;
	private int numberEnnemy;
	public Ennemy1(int life,Double dmg,int[] position,Color color,Model model,int numberEnnemy) {
		super(life,dmg,position,color,model,numberEnnemy);		
	}
	public void move(int X, int Y){
		this.position[0] = this.getPositionX() + X;
		this.position[1] = this.getPositionY() + Y;
	}
	
	/*@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:demisableobservers){
			po.demise(this, loot);
		}*/

}
