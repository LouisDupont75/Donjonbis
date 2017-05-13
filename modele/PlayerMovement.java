package modele;

public interface PlayerMovement {
	public void addPlayerMovementObserver(Model model);
	public void notifyPlayerMovementObserver();

}
