package controller;

import java.util.ArrayList;

import modele.Model;
import modele.Personnage;
import view.Board;

public class Controller {
	private Model model;
	public Controller(Model model) {
		this.model=model;
	}
	public void move(int direction) {
		switch (direction) {
		case 1:
			if (model.getPlayer().getPositionY()>0) {
				model.moveUp(model.getPlayer());
				}
			break;
		case 0:
			if (model.getPlayer().getPositionX()>0) {
			model.moveLeft(model.getPlayer());}
			break;
		case 2: //static majuscule
			if (model.getPlayer().getPositionX()<Board.LENGTH-1) {
			model.moveRight(model.getPlayer());}
			break;
		case 3:
			if (model.getPlayer().getPositionY()<Board.HEIGTH-1) {
			model.moveDown(model.getPlayer());}
			break;
			
		
		}
	}
	public ArrayList<Personnage> listPersonnagesToDraw() {
		return  model.getPersonnages();
	}

}
