package modele;

import java.awt.Color;
import java.util.ArrayList;

public abstract class AbstractEnnemy extends Personnage  implements ExplodableObserver  {
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
	public void moveEnnemy(int x, int y){
		//TODO ajouter un comportement et notifier le modèle a l'aide d'un design pattern
	}

}