package modele;

public interface Moveable {
	public void moveableAttach(MoveableObserver mv);
	
	public void moveableNotifyObserver(int x,int y);

}
