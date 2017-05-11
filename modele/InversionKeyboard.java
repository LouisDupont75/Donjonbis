package modele;

public class InversionKeyboard implements Runnable{
	private Player player;
	private int duration;
	public InversionKeyboard(Player player,int duration){
		setPlayer(player);
		setDuration(duration);
		Thread t=new Thread(this);
		t.start();
	}
	public void setPlayer(Player player){
		this.player=player;
	}
	public Player getPlayer(){
		return this.player;
	}
	public void setDuration(int duration){
		this.duration=duration;
	}
	public int getDuration(){
		return this.duration;
	}
	@Override
	public void run(){
		int count=0;
		while(count<=this.duration/10){
			try{
				player.setKeyboardInversion(true); //de telle maniere que si plusieurs fleches le touchent,l'inversion
//clavier durera jusqu'à la fin du thread
				Thread.sleep(10);
				count++;
			}catch(InterruptedException e){
				e.printStackTrace();
				e.getMessage();
			}
		}
		player.setKeyboardInversion(false);
	}
}
