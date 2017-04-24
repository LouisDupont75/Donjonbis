package modele;

import java.util.ArrayList;

import modele.GameObject;
import modele.Player;

import java.awt.Color;

public class Model implements Observable,DemisableObserver {//Runnable
	
	private Inventaire inventaire;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<GameObject> gameobjects= new ArrayList<GameObject>();
	private ArrayList<Object> objects = new ArrayList<Object>();
	
	private int length=32; // Taille en carres
	private int height=16;
	private int size=42;// Taille en pixels d'un carre
	
	//
 	public Model() {
        
 		Inventaire invent = new Inventaire();
 		setInventaire(invent);
		Player player = new Player(4,1.0,new int[]{3,6},Color.GREEN,this);
		gameobjects.add(player);

		//System.out.println("player fini");
		
		Ennemy e1 = new Ennemy(2,1.0,new int[]{5,6},Color.CYAN,this,1);
		e1.demisableAttach(this);
		gameobjects.add(e1);

		//System.out.println("ennemi fini");
		
		Ennemy e2 = new Ennemy(2,1.0,new int[]{15,12},Color.CYAN,this,2);
		e2.demisableAttach(this);
		gameobjects.add(e2);

		//System.out.println("ennemi2 fini");
		
		/*Map map=new Map();
		ArrayList<Case> listeDeBlocksPourLaCarte = map.getBlocList();
		for (Case bloc:listeDeBlocksPourLaCarte) {
			gameobjects.add(bloc);
		}*/
		//TODO completer avec map[
		/*BlockBreakable block1 =new BlockBreakable(new int[]{10,2},Color.DARK_GRAY);
		block1.demisableAttach(this);
		gameobjects.add(block1);

		//System.out.println("bloc fini");
		
		BlockMoveable block2 =new BlockMoveable(new int[]{13,2},Color.GRAY);
		block2.demisableAttach(this);
		block2.moveableAttach((Player)this.getPlayer());
		gameobjects.add(block2);*/
		//fin]

		//System.out.println("bloc2 fini");
		
		Object potion =new Potion(new int []{13,2},Color.PINK);
		objects.add(potion); // A optimiser avec la méthode player.utilize
		gameobjects.add(potion); //potion c'est une case, un gameObject, il ne faut pas dupliquer les informations
		potion.demisableAttach(this);

		//System.out.println("potion fini");
		
	}
 	
 	/*public void moveEnnemy() {
 		for(GameObject element : gameobjects){
			if(element instanceof Ennemy ){
				((Ennemy)element).run();
			}
		}
 	}*/
 	
 	//contenu devrait etre dans contolleur
 	public void movePlayer(int x, int y){//, int playerNumber){
		GameObject player = gameobjects.get(0);//playerNumber));
		boolean obstacle =false;
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
		if(obstacle==false){
			player.move(x,y);
			notifyObserver();
		}
	}
	
	public void dropBomb(){ // au cas où plus tard des ennemis seraient capables d'en lancer aussi
		GameObject perso = gameobjects.get(0);
		Bomb bombdropped = perso.dropBomb();
		bombdropped.demisableAttach(this);
		for(GameObject go: gameobjects){
			if (go instanceof ExplodableObserver){
				bombdropped.explodableAttach((ExplodableObserver) go);

			}
		}    		
		//System.out.println(bombdropped.getExplodableObservers().get(0));
		gameobjects.add(bombdropped); 
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
    		gameobjects.addAll(loot);
    	}
       	notifyObserver();
    }
    
	public Inventaire getInventaire(){
		return inventaire;
	}
	public Personnage getPlayer() {
		return (Personnage) gameobjects.get(0);
	}
	public ArrayList<GameObject> getGameObjects(){
		return gameobjects;
	}
	public ArrayList<Object> getObjects(){
		return objects;
	}
	public ArrayList <modele.Object> getObjectsInventaire(){
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
