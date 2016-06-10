package dto01917;

public class RollerDTO {

	private int opr_id;
	private String rolle;
	
	public RollerDTO(int opr_id, String	Rolle){
		this.opr_id = opr_id;
		this.rolle = Rolle;
	}
	
	public RollerDTO(RollerDTO Rolle){
		this.opr_id = Rolle.getOpr_id();
		this.rolle = Rolle.getRolle();
	}

	public int getOpr_id() {
		return opr_id;
	}

	public void setOpr_id(int opr_id) {
		this.opr_id = opr_id;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

	public boolean isFarmaceut() {
		return farmaceut;
	}

	public void setFarmaceut(boolean farmaceut) {
		this.farmaceut = farmaceut;
	}

	public boolean isVaerkfoerer() {
		return vaerkfoerer;
	}

	public void setVaerkfoerer(boolean vaerkfoerer) {
		this.vaerkfoerer = vaerkfoerer;
	}
	
	
	
}
