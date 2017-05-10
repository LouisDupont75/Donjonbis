package modele;

import java.awt.Color;
import java.util.ArrayList;

public class BlockBreakable extends Case implements ExplodableObserver,Demisable {
	protected ArrayList<DemisableObserver> demisableobservers =new ArrayList<DemisableObserver>();
	private int life;
	//commentaire dans block, Donjonbis sur eclipse 
	public BlockBreakable (int[] position, Color color,int life){ // commentaire ds branche test
		super(position,color);
		setLife(life);
	}
	public BlockBreakable(int i, int j) {
		super(new int[] {i,j}, Color.GRAY);
		this.life=1;
	}
	public int getLife(){
		return this.life;
	}
	public void setLife(int life){
		this.life=life;
	}
	@Override
	public boolean isObstacle(){
		return true;
	}
	@Override
	public void demisableAttach(DemisableObserver demi){ // le mot-cle "do" ne marche pas car c'est un mot cle special
		demisableobservers.add(demi);
	}
	@Override
	public void demisableRemove(DemisableObserver po){};
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
		boolean dist = this.distance(bomb) <=bomb.getRange();//TODO Changer la vue pour afficher un cercle
		if(dist){
			this.demisableNotifyObserver();
		}
	}
}
