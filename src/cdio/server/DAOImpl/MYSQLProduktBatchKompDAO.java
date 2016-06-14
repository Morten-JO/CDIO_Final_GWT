package cdio.server.DAOImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio.server.Connector;
import cdio.server.DAOinterfaces.DALException;
import cdio.server.DAOinterfaces.ProduktBatchKompDAO;
import cdio.shared.ProduktBatchKompDTO;

public class MYSQLProduktBatchKompDAO implements ProduktBatchKompDAO {

	@Override
	public ProduktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException {
		try {
			CallableStatement getPBKomp = Connector.getInstance().getConnection()
					.prepareCall("call get_produktbatchkomponent(?,?)");
			getPBKomp.setInt(1, pbId);
			getPBKomp.setInt(2, rbId);
			ResultSet rs = getPBKomp.executeQuery();

			if (rs.first()) {
				int pb_id = rs.getInt(1);
				int rb_id = rs.getInt(2);
				double tara = rs.getInt(3);
				;
				double netto = rs.getInt(4);
				int opr_id = rs.getInt(5);

				ProduktBatchKompDTO newPBKomp = new ProduktBatchKompDTO(pb_id, rb_id, tara, netto, opr_id);
				return newPBKomp;
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return null;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		try {
			ResultSet rs = Connector.getInstance()
					.doQuery("SELECT * FROM view_produktbatchkomponent WHERE pbId = " + pbId);
			while (rs.next()) {
				ProduktBatchKompDTO current = new ProduktBatchKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3),
						rs.getDouble(4), rs.getInt(5));
				list.add(current);
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public List<ProduktBatchKompDTO> getProduktBatchKompList() throws DALException {
		List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		try {
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_produktbatchkomponent");
			while (rs.next()) {
				ProduktBatchKompDTO current = new ProduktBatchKompDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3),
						rs.getDouble(4), rs.getInt(5));
				list.add(current);
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		try {
			CallableStatement createPBKomp = Connector.getInstance().getConnection()
					.prepareCall("call add_produktbatchkomponent(?,?,?,?,?,?)");
			createPBKomp.setInt(1, produktbatchkomponent.getPbId());
			createPBKomp.setInt(2, produktbatchkomponent.getRbId());
			createPBKomp.setDouble(3, produktbatchkomponent.getTara());
			createPBKomp.setDouble(4, produktbatchkomponent.getNetto());
			createPBKomp.setInt(5, produktbatchkomponent.getOprId());
			createPBKomp.setInt(6, 0);
			createPBKomp.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create produktbatchkomponent, check if the database is running!");
		}
	}

	@Override
	public void updateProduktBatchKomp(ProduktBatchKompDTO produktbatchkomponent) throws DALException {
		try {
			Connector.getInstance()
					.doUpdate("UPDATE produktbatchkomponent SET  tara= " + produktbatchkomponent.getTara()
							+ ", netto =  " + produktbatchkomponent.getNetto() + ", oprId = "
							+ produktbatchkomponent.getOprId() + " WHERE pbId = " + produktbatchkomponent.getPbId()
							+ " and rbId  = " + produktbatchkomponent.getRbId() + ";");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Integer> getRbInPb(int pbID) throws DALException {
		List<Integer> list = new ArrayList<Integer>();
		try {
			ResultSet rs = Connector.getInstance().doQuery("Select rbID from produktbatchkomponent where pbId = " + pbID + ";");
			while (rs.next()){
				list.add(rs.getInt(1));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
