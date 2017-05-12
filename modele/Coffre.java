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
		if(Math.random() <0.01) {
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
			Archer archer= new Archer(1,1.0,position,Color.CYAN,2);
			loot.add(archer);
		}
		else {
			Ennemy ennemy=new Ennemy(position);
			loot.add(ennemy);
		}
	}
}
