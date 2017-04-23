package modele;

import java.util.ArrayList;

public class Map {
	ArrayList<Case> blocList= new ArrayList<Case>(); //on appelle le constructeur du truc
	MapGeneration map1;
	public Map() {
		this.map1=new MapGeneration(150);
		this.blocList.add(new BlockUnbreakable(null, null)); 
		this.blocList.add(new BlockBreakable(null, null));
		
	}
	public ArrayList<Case> getBlocList() {
		return blocList;
	}

	public void setBlocList(ArrayList<Case> blocList) {
		this.blocList = blocList;
	}
	
	

}
