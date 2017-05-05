package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	private View view;
	public Keyboard(View view) {
		this.view= view;		
		this.view.addKeyListener(this);// le dernier this fait référence à quoi ?
	}

	//quelles clés keyEvent
	@Override
	public void keyPressed(KeyEvent event) {
		int key = event.getKeyCode(); // L'evenement recu est stocke dans key		
		
		switch (key){   // Switch est ici un statement particulier. Il est ici prefere à la structure if-then-else 
			case KeyEvent.VK_RIGHT: 
				view.getControlleur().move(2); // 2 Droite
				break;
			case KeyEvent.VK_LEFT:
				view.getControlleur().move(0);// 0 Gauche
				break;
			case KeyEvent.VK_DOWN:
				view.getControlleur().move(3);//3bas
				break;
			case KeyEvent.VK_UP:
				view.getControlleur().move(1);//1 haut
				break;	
			case KeyEvent.VK_B:
				view.getControlleur().dropBomb();
				break;		
			case KeyEvent.VK_U:
				view.getControlleur().addItem(); 
				break;
			case KeyEvent.VK_S:
				view.getControlleur().sauvegarde();
				break;
			case KeyEvent.VK_L:
				view.getControlleur().chargerLaPartie(view);
				break;
			default: 
				System.out.println("Mauvaise touche");
				break;
				
				
			}
	}
	@Override
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	

}
