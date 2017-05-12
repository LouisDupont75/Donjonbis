package modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventaire implements DemisableObserver,Serializable {//il ne doit pas implémenter DemisableObserver, c'est pas lui qui observe les objets qui se cassent .......
	private ArrayList<GameObject> objectslist = new ArrayList<GameObject>();//on peut mettre des blocs des ennemis et des joueurs dans le sac?
	//private Model model;
	public Inventaire(){
		//this.model=model;
	}
	
	public void addObject(GameObject object){
		objectslist.add(object);
	}
	public ArrayList<GameObject> getObjects(){
		return this.objectslist;
	}
	@Override
	public void demise(Demisable d,ArrayList<GameObject> loot){
		objectslist.remove(d);
    	if (loot!= null){
    		objectslist.addAll(loot);
    	}
       	//model.notifyObserver(); CLAIREMENT pas a l'inventaire de le faire...
	}
}
