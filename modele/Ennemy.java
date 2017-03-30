package modele;

import java.awt.Color;

public class Ennemy extends Personnage {
	public Ennemy(int pv,Double dmg,int[] position) {
		super(pv,dmg,position);
		setPv(4);
		setCouleur(Color.GREEN);
		setDmg(dmg);
		setPositionX(position[0]);
		setPositionY(position[1]);
	}

}
