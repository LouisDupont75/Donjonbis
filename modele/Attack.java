package modele;

public interface Attack {
	public void AttackAttach(AttackObserver ao);
	public void AttackNotifyObserver(int x,int y);

}
