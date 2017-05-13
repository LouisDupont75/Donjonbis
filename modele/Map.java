package modele;

import java.io.Serializable;
import java.util.ArrayList;

public class Map implements Serializable{
	private ArrayList<Case> blocList= new ArrayList<Case>(); //on appelle le constructeur du truc
	private transient MapGeneration map1;
	public Map(int taille) {
		this.map1=new MapGeneration(taille);
		int[][] carte=map1.createMap();
		for(int i=0;i<carte.length;i++) {
			for(int j=0;j<carte[0].length;j++) {
				if (carte[i][j]==1) {
					blocList.add(new BlockUnbreakable(i,j));
				}
				else if (carte[i][j]==4) {
					blocList.add(new BlockBreakable(i,j));
				}
				else if (carte[i][j]==5) {
					blocList.add(new Coffre(i,j));
				}				
				/*else if(carte[i][j]==6){
					blocList.add(new Ennemy(new int[]{i,j}));
				}*/
			}
		}		
	}
	public ArrayList<Case> getBlocList() {
		return blocList;
	}

	public void setBlocList(ArrayList<Case> blocList) {
		this.blocList = blocList;
	}
	
	public String toString(){
		return "blocList size: "+blocList.size();
	}
	

}
