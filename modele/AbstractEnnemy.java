package modele;

import java.awt.Color;
import java.util.ArrayList;

public abstract class AbstractEnnemy extends Personnage  implements ExplodableObserver,PlayerAttackObserver  {
	protected transient Thread t;
	protected ArrayList<DemisableObserver> demisableobservers = new ArrayList<DemisableObserver>();

	public AbstractEnnemy(int life, Double dmg, int[] position, Color color, int direction) {
		super(life, dmg, position, color, direction);
	}

	public boolean isObstacle() {
		return true;
	}

	public Bomb dropBomb() {
		return null;//les ennemis ne lachent pas encore de bombes
	}

	@Override
	public GameObject addItem(ArrayList<GameObject> objects, Inventaire inventaire) {
		return null;
	}

	@Override
	public void utilize(GameObject object) {}

	@Override
	public void dropItem(GameObject object) {}

	@Override
	public void exploded(Explodable e) {
		Bomb bomb =(Bomb) e; // Downcast explodable--> Bomb
		boolean dist = this.distance(bomb) <=bomb.getRange();
		if(dist){
			this.demisableNotifyObserver();
			
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

	public void moveEnnemy(int i, int j) {		
	}

}