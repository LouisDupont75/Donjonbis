package modele;

public interface Demisable {
	public void demisableAttach(DemisableObserver po);
	public void demisableRemove(DemisableObserver po);
	public void demisableNotifyObserver();

}
