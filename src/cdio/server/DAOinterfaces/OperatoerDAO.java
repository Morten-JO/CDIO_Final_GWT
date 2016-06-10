package cdio.server.DAOinterfaces;

import java.sql.ResultSet;
import java.util.List;

import cdio.shared.UserDTO;

public interface OperatoerDAO {
	
	UserDTO getOperatoer(int oprId) throws DALException;
	List<UserDTO> getUserList(UserDTO opr) throws DALException;
	void createOperatoer(UserDTO opr) throws DALException;
	void updateOperatoer(UserDTO opr, int id) throws DALException;
	String getUserRole(String token) throws DALException;
}
