import controller.Controller;
import modele.Model;
import view.Keyboard;
import view.View;
public class Main { //salut
	
	public static void main(String[] args) {
		//System.out.println("start");
		Model model= new Model();
		//System.out.println("model fini");
		Controller controller = new Controller(model);
		//System.out.println("controlleur fini");
		View view = new View(controller);
		Keyboard keyboardListener=new Keyboard(view);
		model.addObserver(view);
	}		

}	


//test