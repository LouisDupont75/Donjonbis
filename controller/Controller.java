package controller;

import modele.GameObject;
import java.util.ArrayList;

import modele.Model;
import view.Board;

public class Controller {
	private Model model;
	//private int player1=0;
	public Controller(Model model) {
		this.model=model;
	}
	public void move(int direction) {
		int oldX=model.getPlayer().getPositionX();
		int oldY=model.getPlayer().getPositionY();

		switch (direction) {
		case 1:
			if (oldY>0) {
				model.movePlayer(0, -1);}//, player1);}
			//System.out.println("personnage en " + oldX + " et " + String.valueOf(oldY-1) );
			break;
			
		case 0:
			if (oldX>0) {
				model.movePlayer(-1, 0);}//, player1);}, player1);}
			//System.out.println("personnage en " + String.valueOf(oldX-1) + " et " + oldY );
			break;
			
		case 2: //static majuscule
			if (oldX<model.getLength()-1) {
				model.movePlayer(1, 0);}//, player1);}, player1);}
			//System.out.println("personnage en " + String.valueOf(oldX+1) + " et " + oldY );
			break;
			
		case 3:
			if (oldY<model.getHeight()-1) {
				model.movePlayer(0, 1);}//, player1);}, player1);}
			//System.out.println("personnage en " + oldX + " et " + String.valueOf(oldY+1) );
			break;
			
		
		}
	}
	
	public Model getModel(){
		return this.model;
	}
	public ArrayList<GameObject> listObjectsToDraw() {
		return  model.getGameObjects();
	}
}
