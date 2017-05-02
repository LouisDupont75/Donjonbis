package modele;

import java.util.ArrayList;

public class Inventaire implements DemisableObserver {
	private ArrayList<GameObject> objectslist = new ArrayList<GameObject>();
	private Model model;
	public Inventaire(Model model){
		this.model=model;
	}
	
	public void addObject(GameObject object){
		objectslist.add(object);
	}
	public ArrayList<GameObject> getObjects(){
		return this.objectslist;
	}
	public void setObjects(ArrayList<GameObject> objectslist){
		this.objectslist=objectslist;
	}
	@Override
	public void demise(Demisable d,ArrayList<GameObject> loot){
		objectslist.remove(d);
    	if (loot!= null){
    		objectslist.addAll(loot);
    	}
       	model.notifyObserver();
	}
}
