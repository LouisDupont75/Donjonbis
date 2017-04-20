package modele;

import java.util.ArrayList;
import modele.Demisable;
import modele.GameObject;

public interface DemisableObserver {
	void demise(Demisable d, ArrayList<GameObject> loot);
}
