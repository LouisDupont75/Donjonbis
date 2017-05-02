package modele;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Object extends Case implements Demisable {
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
		for(DemisableObserver po:demisableobservers){
			po.demise(this, null);
		}
	}

}
