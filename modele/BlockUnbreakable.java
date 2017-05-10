package modele;

import java.awt.Color;

public class BlockUnbreakable extends Case implements ObstacleObserver{
	public BlockUnbreakable(int[] position,Color color){
		super(position,color);
	}
	
	public BlockUnbreakable(int i, int j) {//Deuxieme constructeur cree pour la Map aleatoire
		super(new int[]{i,j},Color.BLACK);
	}
	@Override
	public boolean isObstacle(){
		return true;
	}
	@Override
	public void collision(Obstacle o,int x,int y){
		GameObject go=(GameObject) o;
		int distX=this.getPositionX()-(go.getPositionX()+x);
		int distY=this.getPositionY()-(go.getPositionY()+y);
		//System.out.println(distX + "et" + distY);
		if(distX==0 && distY==0){
			go.setStateObstacle(true);
		}
	}
}
