import controller.Controller;
import modele.Model;
import view.Keyboard;
import view.View;

public class Main {
	
	public static void main(String[] args) {
		Model model= new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		Keyboard keyboardListener=new Keyboard(view);
		model.addObserver(view);
		

	}

}
