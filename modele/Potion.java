package modele;

import java.awt.Color;

public class Potion extends Object {
	public Potion (int[] position,Color color){
		super(position,color);
	}
	
	public void effect (Player player){
		player.setLife(player.getLife() +1);
		System.out.println("j'ai " + player.getLife() + "vies");
		this.demisableNotifyObserver();
	}
}
