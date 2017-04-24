package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

	public class InventaireView extends JFrame {
		private View view;
		public InventaireView(View view){
			this.view =view;
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		    this.getContentPane().setBackground(Color.blue);
		    this.setBounds(1400, 0, 100, 800);
	
	
		}
	public View getView (){
		return this.view;
	}
	public ArrayList<modele.Object> getObjectsInventaire(){
		return this.getView().getObjectsInventaire();
	}
}
