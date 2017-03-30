package modele;

public interface Observable {
	public void addObserver(Observeur observeur);
	public void deleteObserver(Observeur observeur);
	public void notifyObserver();

}
