package view;

import modele.GameObject;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import controller.Controller;
import modele.Observeur;

public class View extends JFrame implements Observeur {
	private Controller controlleur;
	private Board board;

	public View(Controller controller) {
		board = new Board(this);
		this.controlleur=controller;
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //pour donner l'effet "fermer la fenetre" au X rouge en haut a droite
		this.setSize(new Dimension(board.getlength()*board.getsize()+18,board.getheight()*board.getsize()+42));// Ou Board.LENGTH est un 
// envoi de message qui nous donne acces a la dimension length
		
		// A QUOI SERVENT LE +18 ET +42 ???
		//18=> taille de la bordure horizontale de la JFRAME
		//42=> taille de la bordure verticale de la JFRAME
		
		this.setTitle("Donjon");
		this.setContentPane(board);
		board.repaint();
	}

	@Override
	public void update() {
		board.repaint(); // Methode qui rappelle paint()
		
	}
	
	public Controller getControlleur() {
	return this.controlleur;
	}

	public Board getBoard() {
		return board;
	}
	public ArrayList<GameObject> getObjectsToDraw() {
		ArrayList<GameObject> list= controlleur.listObjectsToDraw();
		return list;
	}
	
}
