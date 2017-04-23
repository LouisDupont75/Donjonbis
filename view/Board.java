package view;
import modele.GameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel { // Attention, variables publiques
	private View view;
	
	public Board(View view) {
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.view=view;
	}
	
	public void paint(Graphics g) { // A chaque fois qu'on appelle repaint qui appelle paint, on appelle ces 2 methodes
		paintGrille(g); 
		paintObjects(g);
	}
	
	public void paintGrille(Graphics g){
		for(int i = 0; i<=getheight(); i++){							// Vire la valeur 20 et parametrer ca
			for(int j = 0; j<=getlength(); j++){
				int x = j;
				int y = i;
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1); // SIZE -1 car : If you draw a figure that covers a given
//rectangle, that figure occupies one extra row of pixels on the right and bottom edges as compared to filling a
//figure that is bounded by that same rectangle.
				g.setColor(Color.BLACK);
				g.drawRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1); 
			}
		}
	}
	public void paintObjects(Graphics g){
		ArrayList<GameObject> liste=view.getObjectsToDraw();
		for (GameObject go : liste){
			int x = go.getPositionX();
			int y = go.getPositionY();
			Color color = go.getColor();
			g.setColor(color);
			g.fillRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1);
			g.setColor(Color.BLACK);
			g.drawRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1);
			
		}
	}
	public int getlength() {
		return view.getControlleur().getModel().getLength();//violation de la loi de Demeter
	}
	public int getheight() {
		return view.getControlleur().getModel().getHeight();
	}
	public int getsize() {
		return view.getControlleur().getModel().getSize();
	}
}
