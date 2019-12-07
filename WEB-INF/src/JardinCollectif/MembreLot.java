package JardinCollectif;


public class MembreLot {

	private Integer noMembre;
	private String idLot;
	private Boolean validationAdmin;

	public MembreLot(Integer noMembre, String idLot) {
		super();
		this.noMembre = noMembre;
		this.idLot = idLot;
		this.validationAdmin = false;
	}


	public Integer getIdMembre() {
		return noMembre;
	}

	public void setIdMembre(Integer noMembre) {
		this.noMembre = noMembre;
	}

	public String getIdLot() {
		return idLot;
	}

	public void setNomLot(String idLot) {
		this.idLot = idLot;
	}

	public Boolean getValidationAdmin() {
		return validationAdmin;
	}

	public void setValidationAdmin(Boolean validationAdmin) {
		this.validationAdmin = validationAdmin;
	}


}
