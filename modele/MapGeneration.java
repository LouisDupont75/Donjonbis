package modele;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MapGeneration{
	int taille;
	int[][] matriceCarte;
	ArrayList<int[][]> visite=new ArrayList<int[][]>(); //pour les couloirs
	ArrayList<Integer> direction=new ArrayList<Integer>();
	ArrayList<ArrayList<int[]>> frontiere= new ArrayList<>();
	Random rand;
	int nbreMaxDePortes=4;
	int nbreMinDePortes=2;
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
		faireDesCouloirs();
		faireDesPortes();
		transformerDesBlocks();
		

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

	private void transformerDesBlocks() {
		for (int i=1;i<matriceCarte.length-2;i++){
			for(int j=1;j<matriceCarte[0].length;j++){
				if(matriceCarte[i][j] == 1) {
					int x=aleatoire(1,6);
					if (x==1) {
						matriceCarte[i][j] = 4;
					}
				}
			}
		}
	}

	private boolean creuserDansLesUns(ArrayList<int[]> elmt, int indexPorte) {
		int[] point=elmt.get(indexPorte);
		boolean succes=false;
		if (matriceCarte[point[0]][point[1]-1] == 2 && matriceCarte[point[0]][point[1]+1]==2) {
			matriceCarte[point[0]][point[1]]=2;
			succes=true;
		}
		else if (matriceCarte[point[0]-1][point[1]]==2 && matriceCarte[point[0]+1][point[1]]==2) {
			matriceCarte[point[0]][point[1]]=2;
			succes=true;
		}
		return succes;
	}

	private void faireDesPortes() {
		int nombreDePortes, indexPorte;
		for (int i=0; i<frontiere.size();i++){
			ArrayList<int[]> salle=frontiere.get(i);
			nombreDePortes=aleatoire(this.nbreMinDePortes,this.nbreMaxDePortes);
			while (nombreDePortes>0 && salle.size()>0) {
				int[] rectangle=recupRectangle(salle);
				indexPorte=aleatoire(0,salle.size()-1);
				if (creuserDansLesUns(salle,indexPorte)){
					nombreDePortes--;
					retireLesIndex(indexPorte,salle,rectangle);//ici
				}
				else{
					if(salle.size()<=nombreDePortes){
						faireUnePorteDOffice(salle, rectangle);
					}
				}
			}
		}
	}
	
	private void faireUnePorteDOffice(ArrayList<int[]> salle, int[] rectangle){
		for (int[] elmt: salle){
			if(elmt[0]==rectangle[0]) {
				 do{
					 matriceCarte[elmt[0]][elmt[1]]=2;
					 elmt[0]--;
				 }while(matriceCarte[elmt[0]][elmt[1]]!=2);
			}
			else if(elmt[0]==rectangle[1]) {
				 do{
					 matriceCarte[elmt[0]][elmt[1]]=2;
					 elmt[0]++;
				 }while(matriceCarte[elmt[0]][elmt[1]]!=2);
			}
			else if(elmt[1]==rectangle[2]) {
				 do{
					 matriceCarte[elmt[0]][elmt[1]]=2;
					 elmt[1]--;
				 }while(matriceCarte[elmt[0]][elmt[1]]!=2);
			}
			else if(elmt[1]==rectangle[3]) {
				 do{
					 matriceCarte[elmt[0]][elmt[1]]=2;
					 elmt[1]++;
				 }while(matriceCarte[elmt[0]][elmt[1]]!=2);
			}
		}
	}

	private int[] recupRectangle(ArrayList<int[]> salle) {
		int xMin, xMax, yMin, yMax;
		xMin=salle.get(0)[0];
		xMax=salle.get(0)[0];
		yMin=salle.get(0)[1];
		yMax=salle.get(0)[1];
		for (int i = 1;i<salle.size();i++) {
			if (salle.get(i)[0] <= xMin){
				xMin=salle.get(i)[0];
			}
			else if (salle.get(i)[0] >= xMax){
				xMax=salle.get(i)[0];	
			}
			else if (salle.get(i)[1] <= yMin){
				yMin=salle.get(i)[1];
			}
			else if (salle.get(i)[1] >= yMax){
				yMax=salle.get(i)[1];
			}
		} // ordre donné xMin, xMax, yMin puis yMax 
		return new int[] {xMin,xMax,yMin,yMax};
	}

	private void retireLesIndex(int indexPorte,ArrayList<int[]> salle, int[] dimensionMajotanteSalle) {
		ArrayList<int[]> toRemove = new ArrayList<>();
		if (salle.get(indexPorte)[0]==dimensionMajotanteSalle[0]) {
			for (int i=0;i<salle.size();i++){
				int[]elmt=salle.get(i);
				if (elmt[0] == dimensionMajotanteSalle[0]) {
					toRemove.add(elmt);
				}
			}
		}
		else if (salle.get(indexPorte)[0]==dimensionMajotanteSalle[1]) {
			for (int i=0;i<salle.size();i++){
				int[]elmt=salle.get(i);
				if (elmt[0] == dimensionMajotanteSalle[1]) {
					toRemove.add(elmt);
				}
			}
		}
		else if (salle.get(indexPorte)[1]==dimensionMajotanteSalle[2]) {
			for (int i=0;i<salle.size();i++){
				int[]elmt=salle.get(i);
				if (elmt[1] == dimensionMajotanteSalle[2]) {
					toRemove.add(elmt);
				}
			}
		}
		else if (salle.get(indexPorte)[1]==dimensionMajotanteSalle[3]) {
			for (int i=0;i<salle.size();i++){
				int[]elmt=salle.get(i);
				if (elmt[1] == dimensionMajotanteSalle[3]) {
					toRemove.add(elmt);
				}
			}
		}
		for(int []element:toRemove){
			salle.remove(element);
			//System.out.println("taille de la salle: " + salle.size());
		}
	}

	private void createRooms(int count) {
		if (count>500) {
			return;
		}
		int width= aleatoire(10,15);
		int height=aleatoire(10,15);
		int x=aleatoire(2,taille-width-2);
		int y=aleatoire(2,taille-height-2);
		int[] rectangle={x,y,width,height};
		if (chevauchement(rectangle)) {
			createRooms(count+1);
		}
		else {
			//System.out.println("avant makerooms");
			makeRoom(rectangle);
			createRooms(count+1);
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
		ArrayList<int[]> bordure=new ArrayList<>();
		for (int i=rectangle[0];i<rectangle[2]+rectangle[0]; i++) {
			for(int j=rectangle[1];j<rectangle[3]+rectangle[1]; j++) {
				if (i==rectangle[0] || i==rectangle[2]+rectangle[0]-1 || j==rectangle[1] || j==rectangle[3]+rectangle[1]-1) {
					matriceCarte[i][j]=1;
					if (!(rectangle[0]==i && (rectangle[1]==j || rectangle[1]==j-rectangle[3]) || rectangle[0] == i-rectangle[2] && (rectangle[1]==j || rectangle[1]==j-rectangle[3]))) {
						bordure.add(new int[] {i,j});
					}
				}
				else {
					matriceCarte[i][j]=2;
					int x= aleatoire(1,84);
					if (x==1) {
						matriceCarte[i][j]=5;
					}
				}
				enleverElementANonVisite(new int[] {i,j});
			}
		}
		frontiere.add(bordure);
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

	/**
	 * crée un nombre aleatoire entre a et b
	 * @param a borne inférieure
	 * @param b borne supérieure
	 * @return l'aléatoire crée
	 */
	private int aleatoire(int a,int b) {
		return rand.nextInt(b-a+1)+a; 
	}

}
