package modele;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MapGeneration {
	int taille;
	int[][] matriceCarte;
	ArrayList<int[][]> visite=new ArrayList<int[][]>();
	ArrayList<Integer> direction=new ArrayList<Integer>();
	Random rand;
	private ArrayList<int[]> listeNonVisite = new ArrayList<>();
	public MapGeneration(int taille) {
		rand=new Random();
		this.taille=taille;
		matriceCarte= new int[this.taille][this.taille];
		direction.add(0);
		direction.add(1);
		direction.add(2);
		direction.add(3);
		
	}
	
	public int[][] createMap() {
		createBackground();
		createRooms(0);
		FileWriter filewriter=null;
		try {
			filewriter=new FileWriter(new File("./carte/carte.txt"));
			for (int i=0;i<taille;i++) {
				for(int j=0;j<taille;j++) {
					filewriter.write(String.valueOf(matriceCarte[i][j]));
				}
				filewriter.write("\n");
			}
			filewriter.close();
		}
		catch (IOException e) {
			System.out.println("erreur d'écriture dans le fichier");	
		}
		return matriceCarte;
	}

   private void createRooms(int count) {
	   if (count>500) {
		   return;
	   }
	   int width= aleatoire(10,15);
	   int height=aleatoire(10,15);
	   int x=aleatoire(1,taille-width-1);
	   int y=aleatoire(1,taille-height-1);
	   int[] rectangle={x,y,width,height};
	   if (chevauchement(rectangle)) {
		   createRooms(count+1);
	   }
	   else {
		   //System.out.println("avant makerooms");
		   makeRoom(rectangle);
		   createRooms(count+1);
		   faireDesCouloirs();
		   
	   }
   }
   
   private void faireDesCouloirs() {
	while (listeNonVisite.size()!=0) {
		Collections.shuffle(listeNonVisite);
		faireUnCouloir(listeNonVisite.get(0));
		//System.out.println("taille: "+listeNonVisite.size());
	}
}

   private void mettreDesTrois(int[] coord,int dir) {
	   for (int element=0;element<4;element++) {
		   switch (element) {
			case 0:
				if (matriceCarte[coord[0]-1][coord[1]]==0 && element!=dir){
					matriceCarte[coord[0]-1][coord[1]]=3;
					enleverElementANonVisite(new int[] {coord[0]-1,coord[1]});
				}
				break;
			case 1:
				if (matriceCarte[coord[0]][coord[1]-1]==0 && element!=dir){
					matriceCarte[coord[0]][coord[1]-1]=3;
					enleverElementANonVisite(new int[] {coord[0],coord[1]-1});
				}
				break;
			case 2:
				if (matriceCarte[coord[0]+1][coord[1]]==0 && element!=dir){
					matriceCarte[coord[0]+1][coord[1]]=3;
					enleverElementANonVisite(new int[] {coord[0]+1,coord[1]});
				}
				break;
			case 3:
				if (matriceCarte[coord[0]][coord[1]+1]==0 && element!=dir){
					matriceCarte[coord[0]][coord[1]+1]=3;
					enleverElementANonVisite(new int[] {coord[0],coord[1]+1});
				}
				break;
			}
	   }
   }

private void faireUnCouloir(int[] coordCouloir) {
	enleverElementANonVisite(coordCouloir);
	matriceCarte[coordCouloir[0]][coordCouloir[1]]=2;
	Collections.shuffle(direction);
	for (int element:direction) {
		switch (element) {
		case 0:
			if (matriceCarte[coordCouloir[0]-1][coordCouloir[1]]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0]-1,coordCouloir[1]});
			}
			break;
		case 1:
			if (matriceCarte[coordCouloir[0]][coordCouloir[1]-1]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0],coordCouloir[1]-1});
			}
			break;
		case 2:
			if (matriceCarte[coordCouloir[0]+1][coordCouloir[1]]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0]+1,coordCouloir[1]});
			}
			break;
		case 3:
			if (matriceCarte[coordCouloir[0]][coordCouloir[1]+1]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0],coordCouloir[1]+1});
			}
			break;
		}
	}
	//System.out.println("pas de 2 dispo en x= "+coordCouloir[0] +" y= "+coordCouloir[1]);
	Collections.shuffle(direction);
	boolean check=true;
	for (int element:direction) {
		switch (element) {
		case 0:
			if (matriceCarte[coordCouloir[0]-1][coordCouloir[1]]==3 && matriceCarte[coordCouloir[0]-2][coordCouloir[1]]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0]-1,coordCouloir[1]});
				check=false;
			}
			break;
		case 1:
			if (matriceCarte[coordCouloir[0]][coordCouloir[1]-1]==3 && matriceCarte[coordCouloir[0]][coordCouloir[1]-2]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0],coordCouloir[1]-1});
				check=false;
			}
			break;
		case 2:
			if (matriceCarte[coordCouloir[0]+1][coordCouloir[1]]==3 && matriceCarte[coordCouloir[0]+2][coordCouloir[1]]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0]+1,coordCouloir[1]});
				check=false;
			}
			break;
		case 3:
			if (matriceCarte[coordCouloir[0]][coordCouloir[1]+1]==3 && matriceCarte[coordCouloir[0]][coordCouloir[1]+2]==0){
				mettreDesTrois(coordCouloir,element);
				faireUnCouloir(new int[] {coordCouloir[0],coordCouloir[1]+1});
				check=false;
			}
			break;
		}
	}
	if (check){
		mettreDesUns(coordCouloir);
	}
	return;
}

private void enleverElementANonVisite(int[] coordCouloir) {
	for(int[] element:listeNonVisite){
		if(element[0]==coordCouloir[0] && element[1]==coordCouloir[1]){
			listeNonVisite.remove(listeNonVisite.indexOf(element));
			break;
		}
	}
}

private void mettreDesUns(int[] coord) {
	for (int i=0;i<4;i++) {
		switch(i) {
		case 0:
			if (matriceCarte[coord[0]-1][coord[1]]==3){
				matriceCarte[coord[0]-1][coord[1]]=1;
			}
			break;
		case 1:
			if (matriceCarte[coord[0]][coord[1]-1]==3){
				matriceCarte[coord[0]][coord[1]-1]=1;
			}
			break;
		case 2:
			if (matriceCarte[coord[0]+1][coord[1]]==3){
				matriceCarte[coord[0]+1][coord[1]]=1;
			}
			break;
		case 3:
			if (matriceCarte[coord[0]][coord[1]+1]==3){
				matriceCarte[coord[0]][coord[1]+1]=1;
			}
			break;
		}
	}
	
}

public void makeRoom(int[] rectangle) {
	   for (int i=rectangle[0];i<rectangle[2]+rectangle[0]; i++) {
		   for(int j=rectangle[1];j<rectangle[3]+rectangle[1]; j++) {
			   if (i==rectangle[0] || i==rectangle[2]+rectangle[0]-1 || j==rectangle[1] || j==rectangle[3]+rectangle[1]-1) {
				   matriceCarte[i][j]=1;
			   }
			   else {
				   matriceCarte[i][j]=2;
			   }
			   enleverElementANonVisite(new int[] {i,j});
		   }
	   }	   
   }
   
	private boolean chevauchement(int[] rectangle) {
		boolean check = false;
		for (int i=rectangle[0];i<rectangle[0]+rectangle[2];i++) {
			for(int j=rectangle[1];j<rectangle[1]+rectangle[3];j++) {
				//System.out.println(i +"   "+j);
				if (matriceCarte[i][j]==1) {
					check=true;
				}
			}
		}
		return check;
}

	private void createBackground() {
	   for (int i=0;i<taille;i++) {
		   for(int j=0;j<taille;j++) {
			   matriceCarte[i][j]=0;
			   if (i==0 || i==taille-1 || j==0 || j==taille-1) {
				   matriceCarte[i][j]=1;
			   }
			   else {
				   listeNonVisite.add(new int[] {i,j});
			   }
		   }
	   }		
   }
	
	private int aleatoire(int a,int b) {
		return rand.nextInt(b-a+1)+a; 
	}
   
}
