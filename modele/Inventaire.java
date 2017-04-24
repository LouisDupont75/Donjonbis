package modele;

import java.util.ArrayList;

public class Inventaire {
	private ArrayList<Object> objectslist = new ArrayList<Object>();
	public Inventaire(){}
	
	public void addObject(Object object){
		objectslist.add(object);
	}
	public ArrayList<modele.Object> getObjects(){
		return this.objectslist;
	}
	public void setObjects(ArrayList<Object> objectslist){
		this.objectslist=objectslist;
	}
}
