package modele;

import java.awt.Color;
import java.util.ArrayList;

public class Coffre extends BlockBreakable{

	public Coffre(int i, int j, Color color) {
		super(new int[] {i,j}, color, 1);
	}

	public Coffre(int i, int j) {
		super(new int[] {i,j}, Color.MAGENTA, 1);
	}
	@Override
	public void demisableNotifyObserver(){
		ArrayList<GameObject> loot= new ArrayList<>();
		if(Math.random() <0.75) {
			loot.add(new Potion(position));
		}
		else{
			addEnnemy(loot);
		}
		for(DemisableObserver po:demisableobservers){
			po.demise(this, loot);
		}
	}

	private void addEnnemy(ArrayList<GameObject> loot) {
		if (Math.random()<0.5) {
			loot.add(new Archer(position));
		}
		else {
			loot.add(new Ennemy(position));
		}
	}
}
