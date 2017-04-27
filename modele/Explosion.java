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
		}	
	}

	@Override
	public boolean isObstacle() {
		return false;
	}

	@Override
	public void run() {//TODO ne pas juste afficher le message d'erreur, mieux: Systeme.out.println("interruption");
		while(true){
			try {
				Thread.sleep(this.duration);
				this.demisableNotifyObserver();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
