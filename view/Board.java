package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import modele.Personnage;

public class Board extends JPanel { // Attention, variables publiques
	public static int LENGTH=32; // Taille en carres
	public static int HEIGTH=16;
	public static int SIZE=42;// Taille en pixels d'un carre
	private View view;
	public Board(View view) {
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.view=view;
	}
	public void paint(Graphics g) { // A chaque fois qu'on appelle repaint qui appelle paint, on appelle ces 2 methodes
		paintGrille(g); 
		paintPersonnages(g);
		}
	public void paintGrille(Graphics g){
		for(int i = 0; i<=HEIGTH; i++){							// Vire la valeur 20 et parametrer ca
			for(int j = 0; j<=LENGTH; j++){
				int x = j;
				int y = i;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1); // SIZE -1 car : If you draw a figure that covers a given
//rectangle, that figure occupies one extra row of pixels on the right and bottom edges as compared to filling a
//figure that is bounded by that same rectangle.
				g.setColor(Color.BLACK);
				g.drawRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1); 
			}
		}
	}
	public void paintPersonnages(Graphics g){
		ArrayList<Personnage> liste=view.getPersonnagesToDraw();
		for (Personnage p: liste) {
			int x = p.getPositionX();
			int y = p.getPositionY();
			g.setColor(p.getCouleur());
			g.fillRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1); 
		}
	}
}
