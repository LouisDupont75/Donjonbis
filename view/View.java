package view;

import modele.GameObject;
import modele.Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import controller.Controller;
import modele.Observeur;

public class View extends JFrame implements Observeur {
	private Controller controlleur;
	private Board board;
	private Model model;
	private InventaireMap inventaire;


	public View(Controller controller,Model model) {
		this.controlleur=controller;
		this.model=model;
		
		//Paramètres Fenetre
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);//Maximise la fenetre (+simple)
		//this.setSize(new Dimension(this.model.getLength()*model.getSize()+18,model.getHeight()*model.getSize()+42));
		// Ou Board.LENGTH est un  envoi de message qui nous donne acces a la dimension length
				
				// A QUOI SERVENT LE +18 ET +42 ???
				//18=> taille de la bordure horizontale de la JFRAME
				//42=> taille de la bordure verticale de la JFRAME
		this.setTitle("Donjon");

		board = new Board(this);
		inventaire=new InventaireMap(this,board);	
		JSplitPane contenu = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,board,inventaire);
		contenu.setOneTouchExpandable(true);
		contenu.setDividerLocation(0.5);
		this.getContentPane().add(contenu);	
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //pour donner l'effet "fermer la fenetre" au X rouge en haut a droite
		
		board.repaint();
		inventaire.repaint();
	}
	public ArrayList<GameObject> getObjectsInventaire(){
		ArrayList<GameObject> list=this.model.getObjectsInventaire();
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
	public Model getModel() {
		return model;
	}
	public ArrayList<GameObject> getGameObjects() {
		ArrayList<GameObject> list= controlleur.listObjectsToDraw();
		return list;
	}
	public void setKeyListener(KeyListener keyboard){
	    this.board.addKeyListener(keyboard); // Ajoute à l'objet map(fenetre) un ecouteur d'evenements claviers
	}
}
