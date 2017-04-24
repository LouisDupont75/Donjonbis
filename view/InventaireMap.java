package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import modele.Model;
import javax.swing.JPanel;

import modele.GameObject;

public class InventaireMap extends JPanel {
	public static int LENGTH=4; // Taille en carres
	public static int HEIGTH=16;
	public static int SIZE=42;
	private View view;
	
	public InventaireMap(View view) {
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setBackground(Color.orange);
		this.view=view;
	}
	
	public void paint(Graphics g) { 
		paintGrille(g); 
		paintObjects(g);
		}
	public void paintGrille(Graphics g){
		for(int i = 0; i<=HEIGTH; i++){	
			for(int j = 33; j<=33+LENGTH; j++){
				int x = j;
				int y = i;
				g.setColor(Color.BLUE);
				g.fillRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1); 
				g.setColor(Color.BLACK);
				g.drawRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1); 
			}
		}
	}
	public void paintObjects(Graphics g){
		ArrayList<modele.Object> liste = this.view.getObjectsInventaire();
		int x = 33;
		int y = 0;
		for (modele.Object go : liste){
			Color color = go.getColor();
			g.setColor(color);
			g.fillRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1);
			g.setColor(Color.BLACK);
			g.drawRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1);
			x++;
			if(x==37){
				x=33;
				y++;
			}
		}
	}
	/*public boolean isOccupied(int x, int y){
		boolean answer=false;
		if(this.setOccupied(x,y)){
			answer=true;
		}
		return answer;
	}
	public boolean setOccupied(int x , int y){
		int i;
		int j;
		if (i==x&j==y)
		
	}*/
}
