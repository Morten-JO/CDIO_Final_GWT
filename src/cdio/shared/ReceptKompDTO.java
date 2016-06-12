package cdio.shared;

import java.io.Serializable;

public class ReceptKompDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4730474631743732708L;
	int receptId; // auto genereres fra 1..n
	int raavareId; // i omraadet 1-99999999
	double nomNetto; // skal vaere positiv og passende stor
	double tolerance; // skal vaere positiv og passende stor
	
	//Empty for the sake of serialization
	public ReceptKompDTO() {}

	public ReceptKompDTO(int receptId, int raavareId, double nomNetto, double tolerance) {
		this.receptId = receptId;
		this.raavareId = raavareId;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getReceptId() {
		return receptId;
	}

	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public int getRaavareId() {
		return raavareId;
	}

	public void setRaavareId(int raavareId) {
		this.raavareId = raavareId;
	}

	public double getNomNetto() {
		return nomNetto;
	}

	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public String toString() {
		return receptId + "\t" + raavareId + "\t" + nomNetto + "\t" + tolerance;
	}
}
