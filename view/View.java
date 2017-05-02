package view;

import modele.GameObject;
import modele.Model;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;

import controller.Controller;
import modele.Observeur;

public class View extends JFrame implements Observeur {
	private Board board;
	private Model model; //le principe du mvc et du observeur est que view n'a pas acces a model
	private InventaireMap inventaire;
	private Controller controller;


	public View(Model model) {
		this.model=model;
		
		//Paramètres Fenetre
		this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);//Maximise la fenetre (+simple)
		this.setTitle("Donjon");

		board = new Board(this);
		inventaire=new InventaireMap(this,board);	
		/*JSplitPane contenu = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,board,inventaire);
		contenu.setOneTouchExpandable(true);
		contenu.setDividerLocation(0.5);*/
		this.getContentPane().add(board);//contenu);	
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
	public Board getBoard() {
		return board;
	}
	public Model getModel() {
		return model;
	}
	public ArrayList<GameObject> getGameObjects() {
		ArrayList<GameObject> list= model.getGameObjects();
		return list;
	}
	public void setKeyListener(KeyListener keyboard){
	    this.board.addKeyListener(keyboard); // Ajoute à l'objet map(fenetre) un ecouteur d'evenements claviers
	}
	public Controller getControlleur() {
		// TODO Auto-generated method stub
		return null;
	}
}
