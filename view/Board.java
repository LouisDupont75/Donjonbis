package view;
import modele.GameObject;
import modele.Personnage;
import modele.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
		if(!getPlayer().getStateDemisable()){
			paintPlayerDirection(g);
		}
		//paintFieldOfVision(g);
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
	public void paintObjects(Graphics g){//all except player
		int xCentre = getPlayerPositionX();
		int yCentre = getPlayerPositionY();
		ArrayList<GameObject> liste=view.getGameObjects();
		if (xCentre<getlength()/2) {
			xCentre = getlength()/2;
		}
		else if (xCentre>getTailleCarte()-getlength()/2){
			xCentre=getTailleCarte()-getlength()/2;
		}
		if (yCentre<getheight()/2) {
			yCentre=getheight()/2;
		}
		else if (yCentre>getTailleCarte()-getheight()/2) {
			yCentre=getTailleCarte()-getheight()/2;
		}
		synchronized(liste){
		for (int i=1;i<liste.size();i++){
			GameObject go=liste.get(i);
			int x = go.getPositionX()-(xCentre-getlength()/2);
			int y = go.getPositionY()-(yCentre-getheight()/2);
			Color color = go.getColor();
			g.setColor(color);
			g.fillRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1);
			g.setColor(Color.BLACK);
			g.drawRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1);
			
			}
		}
	}
	public int[] paintPlayer(Graphics g){
		Personnage player = getPlayer();
		int x=getlength()/2;
		int y=getheight()/2;
		if(getPlayerPositionX()<getlength()/2){
			x = player.getPositionX();
		}
		else if(getPlayerPositionX()>getTailleCarte()-getlength()/2){
			x = player.getPositionX()-getTailleCarte()+getlength();
		}
		if(getPlayerPositionY()<getheight()/2){
			y = player.getPositionY();
		}
		else if(getPlayerPositionY()>getTailleCarte()-getheight()/2){
			y= player.getPositionY()-getTailleCarte()+getheight();
		}
		Color color = player.getColor();
		g.setColor(color);
		g.fillRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1);
		g.setColor(Color.BLACK);
		g.drawRect(x*getsize(), y*getsize(), getsize()-1, getsize()-1);
		return new int[]{x,y};
	}
	
	public void paintPlayerDirection(Graphics g){
		Player player = view.getControlleur().getModel().getPlayer();
		int[] coordinate=this.paintPlayer(g);
	    int playerposX=coordinate[0];
	    int playerposY=coordinate[1];
		int direction=player.getDirection();
		switch(direction){
			case 0 :
				int x=playerposX-1;
				int y=playerposY;
				g.setColor(Color.WHITE);
				g.fillOval(x*getsize()+15, y*getsize()+15, 10,10);
				break;
			case 1 :
				int x1=playerposX;
				int y1=playerposY-1;
				g.setColor(Color.WHITE);
				g.fillOval(x1*getsize()+15, y1*getsize()+15, 10,10);
				break;
			case 2 :
				int x2=playerposX+1;
				int y2=playerposY;
				g.setColor(Color.WHITE);
				g.fillOval(x2*getsize()+15, y2*getsize()+15, 10,10);
				break;
			case 3 :
				int x3=playerposX;
				int y3=playerposY+1;
				g.setColor(Color.WHITE);
				g.fillOval(x3*getsize()+15, y3*getsize()+15, 10,10);
				break;
		}
	}
	public int getlength() {//manque une majuscule
		return view.getControlleur().getModel().getLength();//violation de la loi de Demeter
	}
	public int getheight() {
		return view.getControlleur().getModel().getHeight();
	}
	public int getsize() {
		return view.getControlleur().getModel().getSize();
	}
	public int getBoardHeight(){
		return getheight()*getsize();
	}
	public int getBoardLength(){
		return getlength()*getsize();
	}
	private Personnage getPlayer(){
		return view.getControlleur().getModel().getPlayer();
	}
	private int getPlayerPositionX(){
		return getPlayer().getPositionX();
	}
	private int getPlayerPositionY(){
		return getPlayer().getPositionY();
	}
	private int getTailleCarte(){
		return view.getControlleur().getModel().getTailleCarte();
	}
}
