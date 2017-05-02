package modele;

import java.util.ArrayList;

public class Map {
	private ArrayList<Case> blocList= new ArrayList<Case>(); //on appelle le constructeur du truc
	private MapGeneration map1;
	public Map(int taille) {
		this.map1=new MapGeneration(taille);
		int[][] carte=map1.createMap();
		for(int i=0;i<carte.length;i++) {
			for(int j=0;j<carte[0].length;j++) {
				if (carte[i][j]==1) {
					blocList.add(new BlockUnbreakable(i,j));
				}
			}
		}		
	}
	public ArrayList<Case> getBlocList() {
		return blocList;
	}

	public void setBlocList(ArrayList<Case> blocList) {
		this.blocList = blocList;
	}
	
	

}
