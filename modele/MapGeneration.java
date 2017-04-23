package modele;
import java.util.Random;

public class MapGeneration {
	int taille;
	int[][] matriceCarte;
	Random rand;
	public MapGeneration(int taille) {
		rand=new Random();
		this.taille=taille;
		matriceCarte= new int[this.taille][this.taille];		
	}
	
	public int[][] createMap() {
		createBackground();
		createRooms(0);
		
		return matriceCarte;
	}

   private void createRooms(int count) {
	   if (count>500) {
		   return;
	   }
	   int width= aleatoire(10,15);
	   int height=aleatoire(10,15);
	   int x=aleatoire(1,taille-1);
	   int y=aleatoire(1,taille-1);
	   int[] rectangle={x,y,width,height};
	   if (chevauchement(rectangle)) {
		   createRooms(count+1);
	   }
	   else {
		   makeRoom(rectangle);
		   createRooms(count+1);
	   }
   }
   
   public void makeRoom(int[] rectangle) {
	   for (int i=rectangle[0];i>rectangle[2]+rectangle[0]; i++) {
		   for(int j=rectangle[1];j>rectangle[3]+rectangle[1]; j++) {
			   if (i==rectangle[0] || i==rectangle[2]+rectangle[0] || j==rectangle[1] || j==rectangle[3]+rectangle[1]) {
				   matriceCarte[i][j]=1;
			   }
		   }
	   }	   
   }
   
	private boolean chevauchement(int[] rectangle) {
		boolean check = false;
		for (int i=rectangle[0]-1;i<rectangle[0]+rectangle[2]+1;i++) {
			for(int j=rectangle[1]-1;j<rectangle[1]+rectangle[3]+1;j++) {
				if (matriceCarte[i][j]==1) {
					check=true;
				}
			}
		}
		return check;
}

	private void createBackground() {
	   for (int i=0;i<taille;i++) {
		   for(int j=0;j<taille;i++) {
			   matriceCarte[i][j]=0;
			   if (i==0 || i==taille-1 || j==0 || j==taille-1) {
				   matriceCarte[i][j]=1;
			   }
		   }
	   }		
   }
	
	private int aleatoire(int a,int b) {
		return rand.nextInt(b-a+1)+a; 
	}
   
}
