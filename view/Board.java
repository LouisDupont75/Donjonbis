package view;
import modele.GameObject;
import modele.Personnage;
import modele.Player;

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
		this.setBackground(Color.orange);
	}
	
	public void paint(Graphics g) { // A chaque fois qu'on appelle repaint qui appelle paint, on appelle ces 2 methodes
		paintGrille(g); 
		paintObjects(g);
		paintPlayerDirection(g);
	}
	
	public void paintGrille(Graphics g){
		for(int i = 0; i<=getheight(); i++){							
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
		ArrayList<GameObject> liste=view.getGameObjects();
		synchronized(liste){
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
	}
	public void paintPlayerDirection(Graphics g){
		Player player = view.getModel().getPlayer();
		int direction=player.getDirection();
		switch(direction){
			case 1 :
				int x=player.getPositionX()+1;
				int y=player.getPositionY();
				g.setColor(Color.WHITE);
				g.fillOval(x*getsize()+15, y*getsize()+15, 10,10);
				break;
			case 2 :
				int x1=player.getPositionX();
				int y1=player.getPositionY()-1;
				g.setColor(Color.WHITE);
				g.fillOval(x1*getsize()+15, y1*getsize()+15, 10,10);
				break;
			case 3 :
				int x2=player.getPositionX()-1;
				int y2=player.getPositionY();
				g.setColor(Color.WHITE);
				g.fillOval(x2*getsize()+15, y2*getsize()+15, 10,10);
				break;
			case 4 :
				int x3=player.getPositionX();
				int y3=player.getPositionY()+1;
				g.setColor(Color.WHITE);
				g.fillOval(x3*getsize()+15, y3*getsize()+15, 10,10);
				break;
		}
	}
	public int getlength() {
		return view.getModel().getLength();
	}
	public int getheight() {
		return view.getModel().getHeight();
	}
	public int getsize() {
		return view.getModel().getSize();
	}
	public int getBoardHeight(){
		return getheight()*getsize();
	}
	public int getBoardLength(){
		return getlength()*getsize();
	}
}
