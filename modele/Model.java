package modele;

import java.util.ArrayList;

import modele.GameObject;
import modele.Player;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Model implements Observable,DemisableObserver,Serializable,Observeur,CreationObserver {
	private transient Inventaire inventaire;
	private transient ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<GameObject> gameobjects= new ArrayList<GameObject>();
	private transient Map map;
	private int tailleMap=150; //ICI changer la taille de la carte
	private transient ArrayList<GameObject> objects = new ArrayList<GameObject>();// deuxieme arraylist Gameobjects mais seulement
	//remplie d'objets destines à l'inventaire. But:utiliser le polymorphisme au maximum
	
	private int length=32; // Taille en carres
	private int height=16;
	private int size=42;// Taille en pixels d'un carre
	
	//
 	public Model() {
 		startGame();
	}
 	
 	private void startGame() {
 		//Inventaire invent = new Inventaire();
 		Inventaire invent = new Inventaire();
 		setInventaire(invent);
		Player player = new Player(10,1.0,new int[]{5,6},Color.GREEN,this,1);
		player.creationAttach(this);
		player.addObserver(this);
		synchronized(gameobjects){
		gameobjects.add(0,player);}

		//System.out.println("player fini");
		Archer archer=new Archer(1,1.0,new int[]{14,7},Color.CYAN,2);
		Ennemy e1 = new Ennemy(1,1.0,new int[]{5,9},Color.CYAN,1);//direction arbitraire pour l'instant
		Ennemy e2 = new Ennemy(3,1.0,new int[]{15,12},Color.CYAN,1);
		Object potion =new Potion(new int []{12,2},Color.PINK);
		Object potion2 =new Potion(new int []{14,2},Color.PINK);
		BlockMoveable block1 =new BlockMoveable(new int[]{19,7},Color.DARK_GRAY,1);
		objects.add(potion);
		objects.add(potion2);
		archer.demisableAttach(this);
		archer.creationAttach(this);
		e1.demisableAttach(this);
		e1.addObserver(this);
		e2.demisableAttach(this);
		e2.addObserver(this);
		potion.demisableAttach(this);
		potion2.demisableAttach(this);
		block1.demisableAttach(this);
		block1.moveableAttach(this.getPlayer());
		/*map=new Map(tailleMap);
		ArrayList<Case> listeDeBlocksPourLaCarte = map.getBlocList();***/
		synchronized(gameobjects){
			/*for (Case bloc:listeDeBlocksPourLaCarte) {
				gameobjects.add(bloc);
			}*/
			gameobjects.add(archer);
			gameobjects.add(e1);
			gameobjects.add(e2);
			gameobjects.add(potion);
			gameobjects.add(potion2);
			gameobjects.add(block1);
		}
	    this.attachInterface(e1);
	    this.attachInterface(e2);
	    this.attachInterface(block1);
	}
		//TODO completer avec map[
		

		//System.out.println("bloc fini");
		
		/*BlockMoveable block2 =new BlockMoveable(new int[]{13,2},Color.GRAY);
		block2.demisableAttach(this);
		block2.moveableAttach(this.getPlayer());
		gameobjects.add(0,block2);*/
		
	
 	private void startGame(Model model) {
 		//System.out.println("start game");
 		inventaire = model.getInventaire();
 		gameobjects = model.getGameObjects();
 		objects = model.getObjects();
 	}

	public void save() {
 		try {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("sauvegarde.txt"));
			output.writeObject(this);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("fichier non trové!");
		} catch (IOException e) {
			System.out.println("probleme avec le flux de données sortantes");
		}
		System.out.println("saved!");
 	}
	
 	public void load() {
 		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("sauvegarde.txt"));
			startGame((Model) input.readObject());
			input.close();
		} catch (IOException e) {
			System.out.println("probleme avec le flux de données entrantes");
		} catch (ClassNotFoundException e) {
			System.out.println("classe non trouvée, données probablement corrompues");
		}
		System.out.println("loaded!");
 	}
 	public void attachInterface(GameObject gameobject){// Rattache les observeurs necessaires au gameobject en parametre
 		synchronized(gameobjects){
 		for (GameObject go:gameobjects){
			if(go instanceof AttackObserver){
				gameobject.AttackAttach((AttackObserver) go);
			}
			if(go instanceof ObstacleObserver){
				gameobject.obstacleAttach((ObstacleObserver) go);
			}
		}
 	}
 }		
 	public void movePlayer(int x, int y){//TODO:le player ne doit pas pouvoir rentrer au travers des fleches
 		GameObject player = gameobjects.get(0);//playerNumber));
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
		gameobjects.add(bombdropped); 
		}
		notifyObserver();
	
		}


	/*public void getItemOnPlayerFeet() {
		//GameObject item = null;
		for (GameObject object: objects){
			if (object.isAtPosition(new int[] {getPlayer().getPositionX(),getPlayer().getPositionY()})){
				object.demisableNotifyObserver();
				inventaire.addObject(object);
				//object=item;//inutile ici, mais le joueur ne devrais pas avoir son inventaire, plutot que le modèle?
				object.demisableRemove(this);
			}
		}
		//return item;
	}*/

	/*public void addGiveItemToPlayer() {
		
	}*/
	
	@Override
	public void addObserver(Observeur o) {
	     listObserveurs.add(o);	
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
	public void update(){
		this.notifyObserver();
	}
    @Override
    public void demise(Demisable d ,ArrayList<GameObject> loot){
    	gameobjects.remove(d);

    	if (loot!= null){
    		gameobjects.addAll(loot);
    		for(GameObject go:loot){
    			if(go instanceof Object){
    				objects.add(go);
    			}
    		}
    	}
       	notifyObserver();
    }
    @Override
    public void initialize(GameObject go){
    	synchronized(gameobjects){
    	gameobjects.add(go);}
    	go.demisableAttach(this);
    	go.addObserver(this);
    	this.attachInterface(go);
    	this.notifyObserver();
    }
	public Inventaire getInventaire(){
		return inventaire;
	}
	public Player getPlayer() {
		return (Player) gameobjects.get(0);
		//return this.player; //astuce pour ne pas avoir d'erreur de cast quand le joueur meurt
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
	public Map getMap(){
		return map;
	}
	public int getTailleCarte(){
		return tailleMap;
	}

	public ArrayList<Observeur> getListObserveurs() {
		return listObserveurs;
	}
}
