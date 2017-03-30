package modele;

import java.util.ArrayList;

public class Model implements Observable {
	
	
	ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	ArrayList<Personnage> listPersonnages= new ArrayList<Personnage>();
	
	
	public Model() {
		listPersonnages.add(new Player(4,1.0,new int[]{5,5}));
		
	}
	public void moveUp(Personnage p) {
		p.setPositionY(p.getPositionY()-1);
		System.out.println("personnage en position "+ p.getPositionX()+" "+p.getPositionY());
	    notifyObserver(); // TRES IMPORTANT CAR PERMET L'UPDATE
		
	}
	public void moveLeft(Personnage p) {
		p.setPositionX(p.getPositionX()-1);
		System.out.println("personnage en position "+ p.getPositionX()+" "+p.getPositionY());
		notifyObserver();
		
	}
	public void moveRight(Personnage p) {
		p.setPositionX(p.getPositionX()+1);
		System.out.println("personnage en position "+ p.getPositionX()+" "+p.getPositionY());
		notifyObserver();
	}
	public void moveDown(Personnage p) {
		p.setPositionY(p.getPositionY()+1);
		System.out.println("personnage en position "+ p.getPositionX()+" "+p.getPositionY());
		notifyObserver();
	}
	
	@Override
	public void addObserver(Observeur observeur) {
	     listObserveurs.add(observeur);
		
	}

	@Override
	public void deleteObserver(Observeur o) {
		listObserveurs.clear();
		
	}

	@Override
	public void notifyObserver() {
		for (Observeur observeur:listObserveurs){
			observeur.update();
		}
		
	}
	public Personnage getPlayer() {
		return listPersonnages.get(0);
	}
	public ArrayList<Personnage> getPersonnages() {
		return listPersonnages;
	}
	

	
	 
}
