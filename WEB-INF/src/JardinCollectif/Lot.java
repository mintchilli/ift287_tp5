package JardinCollectif;


public class Lot {

	private String idLot;
	private String nomLot;
	private Integer noMaxMembre;

	public Lot(String nomLot, Integer noMaxMembre) {
		super();
		this.nomLot = nomLot;
		this.noMaxMembre = noMaxMembre;
	}

	public Lot()
    {
        // TODO Auto-generated constructor stub
    }

    public String getIdLot() {
		return idLot;
	}


	public String getNomLot() {
		return nomLot;
	}

	public void setNomLot(String nomLot) {
		this.nomLot = nomLot;
	}

	public Integer getNoMaxMembre() {
		return noMaxMembre;
	}

	public void setNoMaxMembre(Integer noMaxMembre) {
		this.noMaxMembre = noMaxMembre;
	}


}
