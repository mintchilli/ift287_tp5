package JardinCollectif;

import java.util.ArrayList;
import JardinCollectif.Connexion;
import JardinCollectif.LotAccess;
import JardinCollectif.PlanteAccess;

public class PlantManager {
	
	Connexion conn;

	public PlantManager(Connexion cx) {
		conn = cx;
	}

	public ArrayList<String> getPlantesParLot(String nomLot) {
		LotAccess la = new LotAccess(conn);
		PlanteAccess pa = new PlanteAccess(conn);
		int idLot = la.getLotid(nomLot);
		
		return pa.getPlantesPourLot(idLot);
	}
}
