package cdio.server.DAOImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio.server.Connector;
import cdio.server.DAOinterfaces.DALException;
import cdio.server.DAOinterfaces.ProduktBatchDAO;
import cdio.shared.ProduktBatchDTO;

public class MYSQLProduktBatchDAO implements ProduktBatchDAO {

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		try {
			CallableStatement getPB = Connector.getInstance().getConnection()
					.prepareCall("call get_produktbatch(?)");
			getPB.setInt(1, pbId);
			ResultSet rs = getPB.executeQuery();

			if (rs.first()) {
				int pb_recept = rs.getInt(2);
				int pb_status = rs.getInt(3);
				String startTid = rs.getString(4);
				String slutTid = rs.getString(5);
				ProduktBatchDTO newpb = new ProduktBatchDTO(pbId, pb_recept, pb_status, startTid, slutTid);
				newpb.setPbId(pbId);
				return newpb;
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return null;
	}

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		List<ProduktBatchDTO> list = new ArrayList<ProduktBatchDTO>();
		try {
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM produktbatch");
			while (rs.next()) {
				ProduktBatchDTO current = new ProduktBatchDTO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getString(5));
				current.setPbId(rs.getInt(1));
				list.add(current);
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		try {
			CallableStatement createPB = Connector.getInstance().getConnection()
					.prepareCall("call add_produktbatch(?,?)");
			createPB.setInt(1, produktbatch.getStatus());
			createPB.setInt(2, produktbatch.getReceptId());
			createPB.execute();

		} catch (SQLException e) {
			System.err.println("Could not create produktbatch, check if the database is running!");
		}
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException {
		try {
			Connector.getInstance().doUpdate("UPDATE produktbatch SET  status = " + produktbatch.getStatus()
					+ ", receptId = " + produktbatch.getReceptId() + "   WHERE pbId = " + produktbatch.getPbId());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}