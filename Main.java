
import view.Keyboard;
import modele.Model;
import view.View;

public class Main { 
	public static void main(String[] args) {
		Model model= new Model();
		View view = new View(model);
		Keyboard keyboardListener=new Keyboard(view);
		model.addObserver(view);
		view.setKeyListener(keyboardListener);
		
	}		

}	

//Notes : Question � poser : - Cast evitable dans la fonction MoveThing de Player o� l'on downcast un moveable en 
//GameObject ?
//- Le instanceof dans la m�thode MovePlayer est �vitable ?
//- Afin d'utiliser le polymorphisme au maximum , est il envisageable comme bonne pratique de d�finir des fonctions vides
//dans GameObject afin qu'elles soient plus tard utilisables par les sous classes, ( et donc sans devoir downcaster les 
//�l�ments des ArrayList pr�alablement cast�es en gameobject) ?

//-Correct d'implementer launchAttack comme �a en utilisant des artifices ? 
//--> Sinon, possibilite d'utiliser des interfaces avec patternobserver , mais necessitera un instanceof'