package JardinCollectif.Data;

import java.util.Date;


public class PlanteLot {

	private String idLot;
	private String idPlante;
	private Date datePlantation;
	private Date dateDeRecoltePrevu;
	private int nbExemplaires;

	public PlanteLot(String idLot, String idPlante, Date datePlantation, Date dateDeRecoltePrevu, int nbExemplaires) {
		super();
		this.idLot = idLot;
		this.idPlante = idPlante;
		this.datePlantation = datePlantation;
		this.dateDeRecoltePrevu = dateDeRecoltePrevu;
		this.nbExemplaires = nbExemplaires;
	}
	

	public String getIdLot() {
		return idLot;
	}

	public void setIdLot(String idLot) {
		this.idLot = idLot;
	}

	public String getIdPlante() {
		return idPlante;
	}

	public void setIdPlante(String idPlante) {
		this.idPlante = idPlante;
	}

	public Date getDatePlantation() {
		return datePlantation;
	}

	public void setDatePlantation(Date datePlantation) {
		this.datePlantation = datePlantation;
	}

	public Date getDateDeRecoltePrevu() {
		return dateDeRecoltePrevu;
	}

	public void setDateDeRecoltePrevu(Date dateDeRecoltePrevu) {
		this.dateDeRecoltePrevu = dateDeRecoltePrevu;
	}

	public int getNbExemplaires() {
		return nbExemplaires;
	}

	public void setNbExemplaires(int nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}
	
}
