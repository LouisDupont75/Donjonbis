package modele;

import java.awt.Color;

public class Potion extends Object {
	public Potion (int[] position,Color color){
		super(position,color);
	}
	
	public Potion(int[] position) {
		super(position, Color.PINK);
		if(Math.random()>=0.5){
			color=Color.ORANGE;
		}
	}

	public void effect (Player player){
		player.setLife(player.getLife() +1);
		System.out.println("j'ai " + player.getLife() + "vies");
	}
	public void utilize(Object object){}
}
