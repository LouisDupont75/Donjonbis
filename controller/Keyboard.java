package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import modele.Model;
import modele.Player;
import view.View;

public class Keyboard implements KeyListener {
	private View view;
	private Model model;
	public Keyboard(View view,Model model) {
		this.view= view;	
		this.model=model;
		this.view.addKeyListener(this);
	}
	public Model getModel(){
		return this.model;
	}
	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode(); // L'evenement recu est stocke dans key
		int oldX=model.getPlayer().getPositionX();
		int oldY=model.getPlayer().getPositionY();
		
		switch (key){   // Switch est ici un statement particulier. Il est ici prefere à la structure if-then-else 
			case KeyEvent.VK_RIGHT: 
				if (oldX<model.getLength()-1) {
					model.movePlayer(1, 0);
					model.getPlayer().setDirection(1);
					}
				break;
			case KeyEvent.VK_LEFT:
				if (oldX>0) {
					model.movePlayer(-1, 0);
					model.getPlayer().setDirection(3);
					}
				break;
			case KeyEvent.VK_DOWN:
				if (oldY<model.getHeight()-1) {
					model.movePlayer(0, 1);
					model.getPlayer().setDirection(4);
					}
				break;
			case KeyEvent.VK_UP:
				if (oldY>0) {
					model.movePlayer(0, -1);
					model.getPlayer().setDirection(2);
					}
				break;	
			case KeyEvent.VK_B:
				model.dropBomb();
				break;		
			case KeyEvent.VK_U:
				model.getPlayer().addItem(model.getObjects(),model.getInventaire()); 				
				break;	
			case KeyEvent.VK_A:
				model.getPlayer().launchAttack();
				break;
			default: 
				System.out.println("Mauvaise touche");
				break;
			}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
