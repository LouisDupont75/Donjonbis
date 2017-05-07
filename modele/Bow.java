package modele;

import java.awt.Color;

public class Bow extends Object {
	public Bow (int [] position,Color color){
		super(position,color);
	}
	public void effect(Player player){
		player.setBowEquiped(true);
	}
	
}
