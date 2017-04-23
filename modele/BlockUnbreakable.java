package modele;

import java.awt.Color;

public class BlockUnbreakable extends Case{
	public BlockUnbreakable(int[] position,Color color){
		super(position,color);
	}
	
	@Override
	public boolean isObstacle(){
		return true;
	}
}
