package modele;

import java.util.ArrayList;

import modele.GameObject;
import modele.Player;

import java.awt.Color;

public class Model implements Observable,DemisableObserver,Runnable {
	
	private Inventaire inventaire;
	private ArrayList<Observeur> listObserveurs = new ArrayList<Observeur>();
	private ArrayList<Personnage> listPersonnages= new ArrayList<Personnage>();
	private ArrayList<GameObject> gameobjects= new ArrayList<GameObject>();
	private ArrayList<Object> objects = new ArrayList<Object>();
	
	//
 	public Model() {
        
 		Inventaire invent = new Inventaire();
 		setInventaire(invent);
		Player player = new Player(4,1.0,new int[]{5,5},Color.GREEN,this);
		listPersonnages.add(player);
		gameobjects.add(player);
		
		Ennemy e1 = new Ennemy(2,1.0,new int[]{5,6},Color.CYAN,this,1);
		listPersonnages.add(e1);
		e1.demisableAttach(this);
		gameobjects.add(e1);
		
		Ennemy e2 = new Ennemy1(2,1.0,new int[]{15,12},Color.CYAN,this,2);
		listPersonnages.add(e2);
		e2.demisableAttach(this);
		gameobjects.add(e2);
		
		BlockBreakable block1 =new BlockBreakable(new int[]{10,2},Color.DARK_GRAY);
		block1.demisableAttach(this);
		gameobjects.add(block1);
		
		BlockMoveable block2 =new BlockMoveable(new int[]{13,2},Color.GRAY);
		block2.demisableAttach(this);
		block2.moveableAttach((Player)this.getPlayer());
		gameobjects.add(block2);
		
		Object potion =new Potion(new int []{13,2},Color.PINK);
		objects.add(potion); // A optimiser avec la méthode player.utilize
		gameobjects.add(potion);
		potion.demisableAttach(this);
		
	}
	public Inventaire getInventaire(){
		return inventaire;
	}
	public Personnage getPlayer() {
		return listPersonnages.get(0);
	}
	public Personnage getEnnemy() {
		return listPersonnages.get(1);
	}
	public ArrayList<Personnage> getPersonnages() {
		return listPersonnages;
	}
	public ArrayList<GameObject> getGameObjects(){
		return gameobjects;
	}
	public ArrayList<Object> getObjects(){
		return objects;
	}
	public void setInventaire(Inventaire inventaire){
		this.inventaire=inventaire;
	}
	
	public void movePlayer(int x, int y, int playerNumber){
		Player player = ((Player) gameobjects.get(playerNumber));
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
	
	
	public void dropBomb(int personumber){ // au cas où plus tard des ennemis seraient capables d'en lancer aussi
		if (personumber == 0){
			
      		Player perso = (Player) listPersonnages.get(personumber);
		    Bomb bombdropped = perso.dropBomb();
		    bombdropped.demisableAttach(this);
		    for(GameObject go: gameobjects){
		    	if (go instanceof ExplodableObserver){
		    		bombdropped.explodableAttach((ExplodableObserver) go);

		        }
		    }    		System.out.println(bombdropped.getExplodableObservers().get(0));
		   
		    gameobjects.add(bombdropped); 
		    notifyObserver();
		}
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
    @Override
    public void run (){ // trouver un moyen de faire tourner les threads tous en même temps --> la boucle for ne marche pas
    	/*try {
    		
    		
    		    int count=0;
        		while(count<10){
        			moveRight(this.listPersonnages.get(i), 1);
        			Thread.sleep(200);
        			moveUp(this.listPersonnages.get(i), 1);
        			Thread.sleep(200);
        			moveLeft(this.listPersonnages.get(i), 1);
        			Thread.sleep(200);
        			moveDown(this.listPersonnages.get(i), 1);
        			Thread.sleep(200);
        			count++;
        		}
    		}
    		
    		
		 catch(InterruptedException e){
			e.printStackTrace();
		}*/
    }
	
	 
}
