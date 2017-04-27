package modele;
import java.awt.Color;
import java.util.ArrayList;
public class Bomb extends Case implements Demisable,Runnable,Explodable {
	private int duration;
	private int range;
	boolean detonated =false;
	private ArrayList<DemisableObserver> demisableObservers = new ArrayList<DemisableObserver>();
	private ArrayList<ExplodableObserver> explodableObservers = new ArrayList<ExplodableObserver>();

	public Bomb (int[] position){
		super(position,Color.YELLOW);
		setDuration(3000);
		setRange(1);
	}
	public Bomb (int[] position,int duration,int range,Color color){
		super(position,color);
		setDuration(duration);
		setRange(range);
	}
	public ArrayList<ExplodableObserver> getExplodableObservers(){
		return explodableObservers;
	}
	
	public int getDuration(){
		return this.duration;
	}
	public void setDuration(int duration){
		this.duration=duration;
	}
	public int getRange(){
		return this.range;
	}
	public void setRange(int range){
		this.range=range;
	}
	////////////////////////////////
	////////////////////////////////
	@Override
	public void run() {
		int count = 0;
		while(!this.detonated  && count < this.duration/10.0){
			try {
				Thread.sleep(10);
				count = count + 1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.demisableNotifyObserver();
		this.explodableNotifyObserver();
	}
	@Override
	public void explodableAttach(ExplodableObserver eo){
		explodableObservers.add(eo);
	}
	@Override
	public void explodableNotifyObserver(){
		for(ExplodableObserver eo:explodableObservers){
			eo.exploded(this);
		}
	}
	@Override
	public void demisableAttach(DemisableObserver po) {
		demisableObservers.add(po);		
	}
	@Override
	public void demisableRemove(DemisableObserver po){};
	@Override
	synchronized public void demisableNotifyObserver() {//TODO ici changer pour faire des explosions en cercle
		ArrayList<GameObject> loot = new ArrayList<GameObject>();
		int range = this.getRange();
		int x = this.getPositionX();
		int y = this.getPositionY();
		for(int i = x-range; i <= x+range; i++){
			Explosion explosion = new Explosion(new int[]{i,y},Color.BLUE,175);// duration in millisec
			Thread thread = new Thread(explosion);
			thread.start();
			for(DemisableObserver observer : demisableObservers){
				explosion.demisableAttach(observer);// on rajoute player et Model a la liste des demisableObserver 
				// d'explosion
			}
			loot.add(explosion); // Ajoute les blocs " explosion" a la liste des objets pour l'update de la map
		}
		for(int i = y-range; i <= y+range; i++){
			Explosion explosion = new Explosion(new int[]{x,i},Color.BLUE,175);
			Thread thread = new Thread(explosion);
			thread.start();
			for(DemisableObserver observer : demisableObservers){
				explosion.demisableAttach(observer); 
			}
			loot.add(explosion);
		}
		
		for (DemisableObserver o : this.demisableObservers) { 
			o.demise(this, loot); // On supprime la bombe de la liste des objets et on rajoute les explosions
		}	
	}
	
	@Override
	public boolean isObstacle() {
		return false;
	}	
}
