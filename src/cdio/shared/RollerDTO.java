package cdio.shared;

public class RollerDTO {

	private int opr_id;
	private String rolle;

	public RollerDTO(int opr_id, String rolle) {
		this.opr_id = opr_id;
		this.rolle = rolle;
	}

	public RollerDTO(RollerDTO rolle) {
		this.opr_id = rolle.getOpr_id();
		this.rolle = rolle.getRolle();
	}

	public int getOpr_id() {
		return opr_id;
	}

	public void setOpr_id(int opr_id) {
		this.opr_id = opr_id;
	}

	public String getRolle() {
		return rolle;
	}

	public void setRolle(String rolle) {
		this.rolle = rolle;
	}

}
