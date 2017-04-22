
import controller.Controller;
import modele.Model;
import modele.ThrMoveEnnemy;
import view.Keyboard;
import view.View;
import modele.Ennemy;
public class Main { 
	
	public static void main(String[] args) {
		int i;
		Model model= new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		Keyboard keyboardListener=new Keyboard(view);
		model.addObserver(view);
		for ( i=1; i<=model.getPersonnages().size()-1;i++){// Lance les threads servant à faire bouger les ennemis
			// de maniere automatique
			Thread t = new Thread((Runnable) model.getPersonnages().get(i));// Cast vers Runnable
			t.start();
		}
		System.out.println(model.getObjects().get(0).getClass().getName());

	}		

}	


//un commentaire sur test