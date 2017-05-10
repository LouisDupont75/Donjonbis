package modele;

public interface Observable {
	public void addObserver(Observeur observeur);
	public void deleteObserver(Observeur o);
	public void clearObserver();
	public void notifyObserver();

}
