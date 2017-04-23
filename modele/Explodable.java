package modele;

public interface Explodable {
	public void explodableAttach(ExplodableObserver eo);
	public void explodableNotifyObserver();
}
