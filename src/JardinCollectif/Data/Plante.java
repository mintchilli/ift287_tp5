package JardinCollectif.Data;

import java.sql.Date;

public class Plante {

	private String idPlante;
	private String nomPlante;
	private int tempsCulture;

	public Plante(String nomPlante, int tempsCulture) {
		super();
		this.nomPlante = nomPlante;
		this.tempsCulture = tempsCulture;
	}
	

	public String getIdPlante() {
		return idPlante;
	}

	public void setIdPlante(String noPlante) {
		this.idPlante = noPlante;
	}

	public String getNomPlante() {
		return nomPlante;
	}

	public void setNomPlante(String nomPlante) {
		this.nomPlante = nomPlante;
	}

	public int getTempsCulture() {
		return tempsCulture;
	}

	public void setTempsCulture(int tempsCulture) {
		this.tempsCulture = tempsCulture;
	}
	
}
