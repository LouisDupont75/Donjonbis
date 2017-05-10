package modele;

public interface PlayerAttack {
	public void playerAttackAttach(PlayerAttackObserver pao);
	public void playerAttackNotify(int x,int y);

}
