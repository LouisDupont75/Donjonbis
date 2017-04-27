package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import modele.Model;
import modele.Personnage;

import javax.swing.JPanel;

import modele.GameObject;

public class InventaireMap extends JPanel implements MouseListener {
	public static int LENGTH=3; // Taille en carres
	public static int HEIGTH=16;
	public static int SIZE=42;
	private View view;
	private Board board;
	
	public InventaireMap(View view,Board board) {
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.setBackground(Color.blue);
		this.view=view;
		this.board=board;
		addMouseListener(this);
	}
	
	public void paint(Graphics g) { 
		paintGrille(g); 
		paintObjects(g);
		}
	public void paintGrille(Graphics g){
		for(int i = 0; i<=HEIGTH; i++){	
			for(int j = 0; j<=LENGTH; j++){
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
		int x = 0;
		int y = 0;
		for (modele.Object go : liste){
			Color color = go.getColor();
			g.setColor(color);
			g.fillRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1);
			g.setColor(Color.BLACK);
			g.drawRect(x*SIZE, y*SIZE, SIZE-1, SIZE-1);
			x++;
			if(x==4){
				x=0;
				y++;
			}
		}
	}
	public int currentCase(int x, int y){ // Renvoie la case de l'inventaire correspondant a la coordonnee du clic
		int posX=x/SIZE; // division entière effectuee
		int posY=y/SIZE;
		return 4*(posY)+posX; // formule donnant le numéro de la case (numerotation pour un arraylist)
	}
	///
	@Override
	public void mouseClicked(MouseEvent ev) throws IndexOutOfBoundsException { //TODO : gestion de l'exception outofbounds lorsque l'on clique sur
//une case dont le numero est plus grand que le taille maximale actuelle de l'arraylist de l'inventaire
		/// -->Check si gestion correcte
		///TODO 2: Faire disparaitre l'objet de l'inventaire lorsque utilisé
		try{
		int x=ev.getX();
		int y=ev.getY();
		GameObject player = this.view.getGameObjects().get(0);
		player.utilize(this.view.getModel().getInventaire().getObjects().get(currentCase(x,y)));
		// utilisation de l'objet par le joueur
		System.out.println(currentCase(x,y));
		System.out.println("clic en " + x + " et " + y);
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
			e.getMessage();
		}
	}
		

	public void mousePressed(MouseEvent ev){}
	public void mouseReleased(MouseEvent ev){}
	public void mouseEntered(MouseEvent ev){}
	public void mouseExited(MouseEvent ev){}

}