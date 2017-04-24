package view;

import modele.GameObject;
import modele.Model;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import controller.Controller;
import modele.Observeur;

public class View extends JFrame implements Observeur {
	private Controller controlleur;
	private Board board;
	private Model model;
	private InventaireMap inventaire;


	public View(Controller controller,Model model) {
		board = new Board(this);
		inventaire=new InventaireMap(this);
		this.controlleur=controller;
		this.model=model;
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //pour donner l'effet "fermer la fenetre" au X rouge en haut a droite
		//this.setSize(new Dimension(board.getlength()*board.getsize()+18,board.getheight()*board.getsize()+42));// Ou Board.LENGTH est un 
// envoi de message qui nous donne acces a la dimension length
		
		// A QUOI SERVENT LE +18 ET +42 ???
		//18=> taille de la bordure horizontale de la JFRAME
		//42=> taille de la bordure verticale de la JFRAME
		
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);//Maximise la fenetre (+simple)
		this.setTitle("Donjon");
		this.setContentPane(inventaire);
		//this.setContentPane(board);// TODO Faire en sorte que le Jpanel de l'inventaire soit en overlay (transparence)
		//et non par dessus
		board.repaint();
		inventaire.repaint();
	}
	public ArrayList<modele.Object> getObjectsInventaire(){
		ArrayList<modele.Object> list=this.model.getObjectsInventaire();
		return list;
	}
	@Override
	public void update() {
		board.repaint(); // Methode qui rappelle paint()
		inventaire.repaint();
		
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
