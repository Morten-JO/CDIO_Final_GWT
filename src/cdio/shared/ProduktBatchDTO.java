package cdio.shared;

import java.io.Serializable;

public class ProduktBatchDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int pbId; // i omraadet 1-99999999
	int status; // 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	String startTidspunkt;
	String slutTidspunkt;
	
	public ProduktBatchDTO(){
		
	}

	public ProduktBatchDTO(int produktbatchID, int receptId, int status, String startTid, String slutTid) {
		this.pbId = produktbatchID;
		this.receptId = receptId;
		this.status= status;
		this.startTidspunkt = startTid;
		this.slutTidspunkt = slutTid;
	}

	public int getPbId() {
		return pbId;
	}

	public void setPbId(int pbId) {
		this.pbId = pbId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getReceptId() {
		return receptId;
	}

	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public String toString() {
		return pbId + "\t" + status + "\t" + receptId;
	}

	public String getStartidspunkt() {
		return startTidspunkt;
	}
	
	public String getSluttidspunkt(){
		return slutTidspunkt;
	}
	
}
