package modele;

import java.awt.Color;
import java.util.ArrayList;
public class Explosion extends GameObject implements Runnable, Demisable {

	private int duration;
	
	private ArrayList<DemisableObserver> observers = new ArrayList<DemisableObserver>();
	
	public Explosion(int [] position,Color color, int duration) {
		super(position, color);
		setDuration(duration);
	}
	public int getDuration(){
		return this.duration;
	}
	public void setDuration(int duration){
		this.duration=duration;
	}

	////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void demisableAttach(DemisableObserver po) {
		observers.add(po);		
	}
	@Override
	public void demisableRemove(DemisableObserver po){};
	@Override
	public void demisableNotifyObserver() {
		for (DemisableObserver o : observers) {
			o.demise(this, null);
			System.out.println("explosion out");
		}	
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	@Override
	public void run() {//TODO Résoudre le problème des explosions d'autres objets à la place des cases "Explosion" 
		/*while(true){
			try {
				Thread.sleep(this.duration);
				this.demisableNotifyObserver();//Les cases explosions disparaissent au bout de la duree du thread
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		int count=0;
		while(count<duration/100.0){
			try {
				Thread.sleep(100);
				count+=1;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	

		} 				this.demisableNotifyObserver();

	}
}
