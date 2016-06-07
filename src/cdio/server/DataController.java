package cdio.server;
import daoimpl01917.*;
import daointerfaces01917.*;
import connector01917.*;

public class DataController {
	
	private OperatoerDAO oprDAO;
	private ProduktBatchDAO PBDAO;
	private ProduktBatchKompDAO PBKompDAO;
	private ReceptDAO RecDAO;
	private ReceptKompDAO RecKompDAO;
	private RollerDAO RollerDAO;
	private RaavareBatchDAO RBDAO;
	private RaavareDAO RaavareDAO;
	
	public DataController(){
		Connector conn = Connector.getInstance();
		
		oprDAO = new MYSQLOperatoerDAO();
		PBDAO = new MYSQLProduktBatchDAO();
		PBKompDAO = new MYSQLProduktBatchKompDAO();
		RecDAO = new MYSQLReceptDAO();
		RecKompDAO = new MYSQLReceptKompDAO();
		RollerDAO = new MYSQLRollerDAO();
		RBDAO = new MYSQLRaavareBatchDAO();
		RaavareDAO = new MYSQLRaavareDAO();
		
		
		
	}
	

}