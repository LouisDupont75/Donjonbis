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
		loot.add(new Potion(position));
		for(DemisableObserver po:demisableobservers){
			po.demise(this, loot);
		}
	}
}
