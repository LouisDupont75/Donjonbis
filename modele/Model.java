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

public class Model implements Observable,DemisableObserver, CarteObservable,Serializable {
	private transient Inventaire inventaire;
	private transient ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<GameObject> gameobjects= new ArrayList<GameObject>();
	private transient Map map;
	private int tailleMap=30; //ICI changer la taille de la carte
	private transient ArrayList<GameObject> objects = new ArrayList<GameObject>();// deuxieme arraylist Gameobjects mais seulement
	private ArrayList<CarteObserver> carteObserveurs = new ArrayList<>();//TODO transient a enlever
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
		synchronized(gameobjects){
		gameobjects.add(0,player);}

		//System.out.println("player fini");
		Archer archer=new Archer(1,1.0,new int[]{14,7},Color.CYAN,2,this);
		archer.demisableAttach(this);
		synchronized(gameobjects){
			gameobjects.add(archer);}
		AbstractEnnemy e1 = new Ennemy(1,1.0,new int[]{5,25},Color.CYAN,this,1,1);//direction arbitraire pour l'instant
		e1.demisableAttach(this);
		synchronized(gameobjects){
		gameobjects.add(e1);}

		//System.out.println("ennemi fini");
		
		Ennemy e2 = new Ennemy(3,1.0,new int[]{15,12},Color.CYAN,this,2,1);
		e2.demisableAttach(this);
		synchronized(gameobjects){
			gameobjects.add(e2);}

		//System.out.println("ennemi2 fini");

		Poursuiveur poursuiveur = new Poursuiveur(new int[]{10,10});
		poursuiveur.demisableAttach(this);
		synchronized(gameobjects){
			gameobjects.add(poursuiveur);
			addCarteObserver(poursuiveur);
		}
		
		map=new Map(tailleMap);
		ArrayList<Case> listeDeBlocksPourLaCarte = map.getBlocList();
		for (Case bloc:listeDeBlocksPourLaCarte) {
			gameobjects.add(bloc);
			if(bloc instanceof Demisable){
				bloc.demisableAttach(this);
			}
		}
		/*BlockMoveable block2 =new BlockMoveable(new int[]{13,2},Color.GRAY);
		block2.demisableAttach(this);
		block2.moveableAttach(this.getPlayer());
		gameobjects.add(0,block2);*/
		
		
		Object potion =new Potion(new int []{12,2},Color.PINK);
		objects.add(potion); // A optimiser avec la méthode player.utilize
		synchronized(gameobjects){
			gameobjects.add(potion);} 
		potion.demisableAttach(this);
		
		Object potion2 =new Potion(new int []{14,2},Color.PINK);
		objects.add(potion2); 
		synchronized(gameobjects){
			gameobjects.add(potion2);} 
		potion2.demisableAttach(this);
	}
 	
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
 	
 	public void movePlayer(int x, int y){//, int playerNumber){
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
		}
		gameobjects.add(bombdropped); 
		notifyObserver();
	
		}



	public GameObject getItemOnPlayerFeet(boolean front) {
		GameObject item = null;
		for (int i=0; i<gameobjects.size();i++){
			GameObject object = gameobjects.get(i);
			int x = getPlayer().getPositionX();
			int y = getPlayer().getPositionY();
			if(front){
				switch(getPlayer().getDirection()){
				case 0:
					x--;
					break;
				case 1:
					y--;
					break;
				case 2:
					x++;
					break;
				case 3:
					y++;
					break;
				}
			}
			if (((front && object instanceof Coffre) || (!front && object instanceof Object) ) && object.isAtPosition(new int[] {x,y})){
				System.out.println("DEMIZZZZZZZED");
				object.demisableNotifyObserver();
				//inventaire.addObject(object);
				break;
				//object=item;//inutile ici, mais le joueur ne devrais pas avoir son inventaire, plutot que le modèle?
				//object.demisableRemove(this);
			}
		}
		return item;
	}
	
	public void ouvrirCoffre() {
		int x = getPlayer().getPositionX();
		int y = getPlayer().getPositionY();
		//System.out.println("premier: x= "+x +" y= "+y);
		switch(getPlayer().getDirection()){
		case 0:
			x--;
			break;
		case 1:
			y--;
			break;
		case 2:
			x++;
			break;
		case 3:
			y++;
			break;
		}
		//System.out.println("après: x= "+x +" y= "+y);
		for(int i=0;i<gameobjects.size();i++){
			GameObject gameO = gameobjects.get(i);
			if(gameO instanceof Coffre && gameO.getPositionX()==x && gameO.getPositionY()==y){
				gameO.demisableNotifyObserver();
				//System.out.println("coffre ouvert!");
				break;
			}
		}
	}
	
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
    public void demise(Demisable d ,ArrayList<GameObject> loot){
    	gameobjects.remove(d);

    	if (loot!= null){
    		for(GameObject element: loot){
    			if(element instanceof Demisable){
    				element.demisableAttach(this);
    			}
    		}
    		gameobjects.addAll(loot);
    	}
       	notifyObserver();
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

	@Override
	public void addCarteObserver(CarteObserver obs) {
		carteObserveurs.add(obs);
	}

	@Override
	public void deleteCarteObserver(CarteObserver obs) {
		carteObserveurs.remove(obs);
	}

	@Override
	public void sentCarte() {
		for(CarteObserver co:carteObserveurs){
			co.update(makeCarte());
		}
	}

	private ArrayList<Node> makeCarte() {
		ArrayList<Node> carteDeNode = new ArrayList<>();
		carteDeNode.add(new Node(getPlayer().getPositionX(),getPlayer().getPositionY()));
		for(int i=0;i<tailleMap;i++){
			for(int j=0;j<tailleMap;j++){
				boolean found=false;
				for(GameObject tuile:gameobjects){
					if(tuile.getPositionX()==i && tuile.getPositionY()==j && tuile.isObstacle()){
						found=true;
					}
				}
				if(found){
					carteDeNode.add(new Node(i,j,100000));
				}
				else{
					if(i!=getPlayer().getPositionX()&&j!=getPlayer().getPositionY()){
						carteDeNode.add(new Node(i,j));
					}
				}
			}
		}
		return carteDeNode;
	}
}
