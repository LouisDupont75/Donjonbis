
import controller.Keyboard;
import javafx.scene.control.SplitPane;
import modele.Model;
import view.View;

public class Main { 
	public static void main(String[] args) {
		Model model= new Model();
		View view = new View(model);
		Keyboard keyboardListener=new Keyboard(view,model);
		model.addObserver(view);
		view.setKeyListener(keyboardListener);
		
	}		

}	

