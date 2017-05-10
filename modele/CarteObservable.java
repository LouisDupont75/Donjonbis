package modele;

public interface CarteObservable {
	public void addCarteObserver(CarteObserver obs);
	public void deleteCarteObserver(CarteObserver obs);
	public void sentCarte();
}
