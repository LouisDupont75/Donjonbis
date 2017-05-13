package controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import modele.GameObject;
import modele.Model;
import view.View;

public class Controller {//sert a controller le joueur, pour eviter de violer la loi de Demeter, pour eviter les trop grosse cohésion entre les classes. et pace tu code pas tout seul (un code plus propre= un code plus lisible)! 
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
				model.getPlayer().movePlayer(0, -1);} // .movePlayer(0, -1);}//, player1);}
				model.getPlayer().setDirection(1); // .getPlayer().setDirection(1);
			//System.out.println("personnage en " + oldX + " et " + String.valueOf(oldY-1) );
			break;
			
		case 0:
			if (oldX>0) {
				model.getPlayer().movePlayer(-1, 0);}//, player1);}, player1);}
				model.getPlayer().setDirection(0);
			//System.out.println("personnage en " + String.valueOf(oldX-1) + " et " + oldY );
			break;
			
		case 2: //static majuscule
			if (oldX<model.getTailleCarte()-1) {
				model.getPlayer().movePlayer(1, 0);}//, player1);}, player1);}
				model.getPlayer().setDirection(2);

			//System.out.println("personnage en " + String.valueOf(oldX+1) + " et " + oldY );
			break;
			
		case 3:
			if (oldY<model.getTailleCarte()-1) {
				model.getPlayer().movePlayer(0, 1);}//, player1);}, player1);}
				model.getPlayer().setDirection(3);
			//System.out.println("personnage en " + oldX + " et " + String.valueOf(oldY+1) );
			break;
			
		
		}
	}
	
	public void shootArrow(){
		model.getPlayer().shootArrow();
	}
	public void launchAttack(){
		model.getPlayer().launchAttack();
	}
	public void addItem() {
		model.getItemOnPlayerFeet(false);//il n'y a qu'un inventaire donc pas besoin de le donner au joueur?
		//GameObject object=model.getPlayer().addItem(model.getObjects(), model.getInventaire());
	}
	public void dropItem(GameObject object){
		model.getPlayer().dropItem(object);
		object.demisableAttach(this.model);
    	model.getGameObjects().add(object);
    	model.notifyObserver();
	}
	public void dropBomb() {
		model.dropBomb();
	}
	
	public Model getModel(){
		return this.model;
	}
	public ArrayList<GameObject> listObjectsToDraw() {
		return  model.getGameObjects();
	}
	public void sauvegarde() {
		model.save();	
	}
	public void chargerLaPartie(View view) {
		model.load();
		model.addObserver(view);
	}
	public void ouvrirCoffre() {
		model.ouvrirCoffre();
		model.getItemOnPlayerFeet(true);
	}
	
}
