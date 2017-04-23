package modele;

import java.awt.Color;

public class BlockUnbreakable extends Case{
	public BlockUnbreakable(int[] position,Color color){
		super(position,color);
	}
	
	public BlockUnbreakable(int i, int j) {
		super(new int[]{i,j},Color.BLACK);
	}

	@Override
	public boolean isObstacle(){
		return true;
	}
}
