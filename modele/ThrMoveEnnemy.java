package modele;

public  class ThrMoveEnnemy implements Runnable  {
	private Ennemy ennemy;
	public ThrMoveEnnemy(Ennemy ennemy){
		this.ennemy=ennemy;
	}
	
	@Override
	public void run(){
		try {
			while(true){
    			ennemy.move(1,0);
    			Thread.sleep(200);
    			ennemy.move(0,-1);
    			Thread.sleep(200);
    			ennemy.move(-1,0);
    			Thread.sleep(200);
    			ennemy.move(0,1);
    			Thread.sleep(200);
			}
		}
	 catch(InterruptedException e){
		e.printStackTrace();
	    }
	}

}
