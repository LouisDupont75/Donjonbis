package modele;

import java.awt.Color;
import java.util.ArrayList;

public class BlockMoveable extends BlockBreakable implements Moveable {
	private ArrayList<MoveableObserver> moveableobservers =new ArrayList<MoveableObserver>();

     public BlockMoveable(int [] position,Color color){
    	 super(position,color);
     }
     @Override
     public void moveableAttach(MoveableObserver mv){
    	 moveableobservers.add(mv);
     }
     @Override
     public void moveableNotifyObserver(int x,int y){
    	 for(MoveableObserver mo:moveableobservers){
    		 mo.moveThing(this,x,y);
    	 }
     }

}
