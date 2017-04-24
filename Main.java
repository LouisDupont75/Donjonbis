
import controller.Controller;
import modele.Model;
import view.InventaireView;
import view.Keyboard;
import view.View;

public class Main { 
	public static void main(String[] args) {
		//System.out.println("start");
		Model model= new Model();
		//System.out.println("model fini");
		Controller controller = new Controller(model);
		//System.out.println("controlleur fini");
		View view = new View(controller,model);
		Keyboard keyboardListener=new Keyboard(view);
		model.addObserver(view);
	}		

}	



