package view;

import modele.GameObject;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.Controller;
import modele.Observeur;
import modele.Personnage;

public class View extends JFrame implements Observeur {
	private Controller controlleur;
	private Board board;

	public View(Controller controller) {
		board = new Board(this);
		this.controlleur=controller;
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //pour voir l'entierete du quadrillage, çad "Automatically
//hide and dispose the frame after invoking any registered WindowListener objects.
		this.setSize(new Dimension(Board.LENGTH*Board.SIZE+100,Board.HEIGTH*Board.SIZE+100));// Ou Board.LENGTH est un 
// envoi de message qui nous donne acces a la dimension length
		
		// A QUOI SERVENT LE +18 ET +42 ???
		
		
		this.setTitle("Donjon");
		this.setContentPane(board);
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

	public ArrayList<Personnage> getPersonnagesToDraw() {
		ArrayList<Personnage> list= controlleur.listPersonnagesToDraw();
		return list;
	}
	public ArrayList<GameObject> getObjectsToDraw() {
		ArrayList<GameObject> list= controlleur.listObjectsToDraw();
		return list;
	}

}
