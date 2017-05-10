package modele;

public interface Creation {
	public void creationAttach(CreationObserver co);
	public void creationNotifyObserver(GameObject go);
}
