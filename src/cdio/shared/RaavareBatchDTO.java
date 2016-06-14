package cdio.shared;

import java.io.Serializable;
import java.sql.Date;

public class RaavareBatchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5064163959346205071L;

	int rbId; // i omraadet 1-99999999
	int raavareId; // i omraadet 1-99999999
	double maengde; // kan vaere negativ
	Date leveringsDato;

	public RaavareBatchDTO() {

	}

	public RaavareBatchDTO(int rbId, int raavareId, double maengde, Date levering) {
		this.leveringsDato = levering;
		this.rbId = rbId;
		this.raavareId = raavareId;
		this.maengde = maengde;
	}

	public int getRbId() {
		return rbId;
	}

	public void setRbId(int rbId) {
		this.rbId = rbId;
	}

	public int getRaavareId() {
		return raavareId;
	}

	public void setRaavareId(int raavareId) {
		this.raavareId = raavareId;
	}

	public double getMaengde() {
		return maengde;
	}
	
	public Date getLeveringsDato() {
		return leveringsDato;
	}

	public void setMaengde(double maengde) {
		this.maengde = maengde;
	}

	@Override
	public String toString() {
		return rbId + "\t" + raavareId + "\t" + maengde;
	}
}
