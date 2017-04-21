package modele;

import java.awt.Color;
import java.util.ArrayList;

public class BlockBreakable extends Case implements ExplodableObserver,Demisable {
	private ArrayList<DemisableObserver> demisableobservers =new ArrayList<DemisableObserver>();
	public BlockBreakable (int[] position, Color color){
		super(position,color);
	}
	
	@Override
	public boolean isObstacle(){
		return true;
	}
	@Override
	public void demisableAttach(DemisableObserver po){ // le mot-cle "do" ne marche pas car c'est un mot cle special
		demisableobservers.add(po);
		
	}
	@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:demisableobservers){
			po.demise(this, null);
		}
	}
	@Override 
	public void exploded(Explodable e){
		Bomb bomb =(Bomb) e; // Downcast explodable--> Bomb
		/*boolean distX=Math.abs(this.getPositionX()-bomb.getPositionX())<=bomb.getRange();
		boolean distY=Math.abs(this.getPositionY()-bomb.getPositionY())<=bomb.getRange();*/
		boolean dist = this.distance(bomb) <=bomb.getRange();

		if(dist){
			
			this.demisableNotifyObserver();
			
		}
	}

}