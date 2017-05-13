package modele;

public  class ThrMoveEnnemy implements Runnable  {
	private AbstractEnnemy ennemy;
	public ThrMoveEnnemy(Ennemy ennemy){
		this.ennemy=ennemy;
	}
	
	public ThrMoveEnnemy(Poursuiveur poursuiveur) {
		this.ennemy=poursuiveur;
	}

	@Override
	public void run(){
		try {
			while(!ennemy.getStateDemisable()){
    			ennemy.moveEnnemy(1,0);
    			Thread.sleep(400);
    			ennemy.moveEnnemy(0,-1);
    			Thread.sleep(400);
    			ennemy.moveEnnemy(-1,0);
    			Thread.sleep(400);
    			ennemy.moveEnnemy(0,1);
    			Thread.sleep(400);
			}
		}
	 catch(InterruptedException e){
		e.printStackTrace();
	    }
	}

}
