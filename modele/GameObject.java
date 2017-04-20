package modele;

import java.awt.Color;
import java.util.ArrayList;


public abstract class GameObject {
	protected int[] position=new int[2];
	protected Color color;
	
	public GameObject(int [] position, Color color){
		setPositionX(position[0]);
		setPositionY(position[1]);
		setColor(color);
	}
	
	public int getPositionX() {
		return this.position[0]; 
	}
	public int getPositionY() {
		return this.position[1];
	}
	
	public void setPositionX(int i) {
		this.position[0]=i;
		
	}
	public void setPositionY(int i) {
		this.position[1]=i;
	}
	
	public Color getColor(){
		return this.color;
	}
	public void setColor(Color color){
		this.color=color;
	}
	public void move(int X, int Y){
		this.setPositionX(this.getPositionX() + X);
		this.position[1] = this.getPositionY() + Y;
	}
	public double distance (GameObject A){
		return Math.sqrt(Math.pow(A.getPositionX()-this.getPositionX(),2)+Math.pow(A.getPositionY()-
				this.getPositionY(), 2));
	}
	public boolean obstacleNextPosition(GameObject go,int x,int y){
		int nextX = this.getPositionX()+x;
		int nextY = this.getPositionY()+y;
		boolean obstacle = false;
		if(go.isAtPosition(new int[]{nextX,nextY})){
				obstacle = go.isObstacle(); 
		}
		return obstacle;
	}
	       
	public boolean isAtPosition(int [] pos){
		return this.position[0] == pos[0] && this.position[1] == pos[1];
	}
	
	public abstract boolean isObstacle();
}


