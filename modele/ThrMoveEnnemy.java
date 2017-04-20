package modele;

public  class ThrMoveEnnemy extends Thread {
	private Model model;
	private int numberEnnemy;
	public ThrMoveEnnemy (Model model,int numberEnnemy){
		this.model = model;
		this.numberEnnemy=numberEnnemy;
	}
	
	/*public void run(){
		try {
		    int count=0;
    		while(count<10){
    			model.moveRight(model.getPersonnages().get(numberEnnemy), 1);
    			Thread.sleep(200);
    			model.moveUp(model.getPersonnages().get(numberEnnemy), 1);
    			Thread.sleep(200);
    			model.moveLeft(model.getPersonnages().get(numberEnnemy), 1);
    			Thread.sleep(200);
    			model.moveDown(model.getPersonnages().get(numberEnnemy), 1);
    			Thread.sleep(200);
    			count++;
    		}
		}
		
		
	 catch(InterruptedException e){
		e.printStackTrace();
	    }
	}*/

}
