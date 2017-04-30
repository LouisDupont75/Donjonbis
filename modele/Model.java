package modele;

import java.util.ArrayList;

import modele.GameObject;
import modele.Player;

import java.awt.Color;

public class Model implements Observable,DemisableObserver {
	
	private Inventaire inventaire;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<GameObject> gameobjects= new ArrayList<GameObject>();
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();// deuxieme arraylist Gameobjects mais seulement
	//remplie d'objets destines à l'inventaire. But:utiliser le polymorphisme au maximum
	
	private int length=32; // Taille en carres
	private int height=16;
	private int size=42;// Taille en pixels d'un carre
	
	//
 	public Model() {
        
 		Inventaire invent = new Inventaire(this);
 		setInventaire(invent);
		Player player = new Player(10,1.0,new int[]{5,6},Color.GREEN,this,1);
		synchronized(gameobjects){
		gameobjects.add(player);}

		//System.out.println("player fini");
		
		Ennemy e1 = new Ennemy(1,1.0,new int[]{5,6},Color.CYAN,this,1,1);//direction arbitraire pour l'instant
		e1.demisableAttach(this);
		synchronized(gameobjects){
		gameobjects.add(0, e1);}

		//System.out.println("ennemi fini");
		
		Ennemy e2 = new Ennemy(3,1.0,new int[]{15,12},Color.CYAN,this,2,1);
		e2.demisableAttach(this);
		synchronized(gameobjects){
			gameobjects.add(0,e2);}

		//System.out.println("ennemi2 fini");
		
		/*Map map=new Map();
		ArrayList<Case> listeDeBlocksPourLaCarte = map.getBlocList();
		for (Case bloc:listeDeBlocksPourLaCarte) {
			gameobjects.add(0,bloc);
		}*/
		//TODO completer avec map[
		BlockBreakable block1 =new BlockBreakable(new int[]{10,2},Color.DARK_GRAY,1);
		block1.demisableAttach(this);
		gameobjects.add(0,block1);

		//System.out.println("bloc fini");
		
		/*BlockMoveable block2 =new BlockMoveable(new int[]{13,2},Color.GRAY);
		block2.demisableAttach(this);
		block2.moveableAttach(this.getPlayer());
		gameobjects.add(0,block2);*/
		
		
		Object potion =new Potion(new int []{13,2},Color.PINK);
		objects.add(potion); // A optimiser avec la méthode player.utilize
		synchronized(gameobjects){
			gameobjects.add(0,potion);} 
		potion.demisableAttach(this);
		potion.demisableAttach(this.inventaire);
		
		Object potion2 =new Potion(new int []{14,2},Color.ORANGE);
		objects.add(potion2); 
		synchronized(gameobjects){
			gameobjects.add(0,potion2);} 
		potion2.demisableAttach(this);
		potion2.demisableAttach(this.inventaire);

		
	}

 	public void movePlayer(int x, int y){
		GameObject player = this.getPlayer();
		boolean obstacle =false;
		synchronized(gameobjects){
		for(GameObject object : gameobjects){
			obstacle=player.obstacleNextPosition(object, x, y);
			if(obstacle==true){
				if(object instanceof Moveable){
					Moveable o = (Moveable)object;
					o.moveableNotifyObserver(x,y);
				}
				break;
			}
		}
		}
		if(obstacle==false){
			player.move(x,y);
			notifyObserver();
		}
	}
	
	public void dropBomb(){ // au cas où plus tard des ennemis seraient capables d'en lancer aussi
		GameObject perso = this.getPlayer();
		Bomb bombdropped = perso.dropBomb();
		bombdropped.demisableAttach(this);
		synchronized(gameobjects){
		for(GameObject go: gameobjects){
			if (go instanceof ExplodableObserver){
				bombdropped.explodableAttach((ExplodableObserver) go);

			}
		}  
		}
		gameobjects.add(0,bombdropped); 
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
	
    @Override
    public void demise(Demisable d ,ArrayList<GameObject> loot){
    	gameobjects.remove(d);

    	if (loot!= null){
    		gameobjects.addAll(0,loot);
    	}
       	notifyObserver();
    }
    
	public Inventaire getInventaire(){
		return inventaire;
	}
	public Player getPlayer() {
		return (Player) gameobjects.get(gameobjects.size()-1);
	}
	public ArrayList<GameObject> getGameObjects(){
		return gameobjects;
	}
	public ArrayList<GameObject> getObjects(){
		return objects;
	}
	public ArrayList <GameObject> getObjectsInventaire(){
		return this.inventaire.getObjects();
	}
	public void setInventaire(Inventaire inventaire){
		this.inventaire=inventaire;
	}
	public int getLength() {
		return length;
	}
	public int getHeight() {
		return height;
	}
	public int getSize() {
		return size;
	}
}
