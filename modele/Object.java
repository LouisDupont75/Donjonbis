package modele;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Object extends Case implements Demisable,AddInventaireObserver {
	private ArrayList<DemisableObserver> demisableobservers =new ArrayList<DemisableObserver>();

	public Object (int [] position,Color color){
		super(position,color);
	}
	
	public abstract void effect(Player player);
	
	@Override
	public boolean isObstacle(){
		return false;
	}
	
	@Override
	public void demisableAttach(DemisableObserver po){
		demisableobservers.add(po);
	}
	@Override
	public void demisableRemove(DemisableObserver po){
		demisableobservers.remove(po);
	};
	@Override
	public void demisableNotifyObserver(){
		for(DemisableObserver po:this.demisableobservers){
			po.demise(this, null);
		}
	}
	@Override
	public void added(AddInventaire ai){
		Player player=(Player)ai;
		int distX=this.getPositionX()-player.getPositionX();
		int distY=this.getPositionY()-player.getPositionY();
		if(distX==0&&distY==0){
			this.demisableNotifyObserver();
			player.addToInventaire(this);
			
		}
	}
}
