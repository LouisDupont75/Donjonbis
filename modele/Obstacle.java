package modele;

public interface Obstacle {
	public void obstacleAttach(ObstacleObserver ao);
	public void obstacleNotifyObserver(int x,int y);
}
