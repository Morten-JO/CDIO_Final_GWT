package cdio.shared;

import cdio.shared.UserDTO;

public interface OperatoerDAO {
	UserDTO getOperatoer(int oprId) throws DALException;
	UserDTO[] getOperatoerList() throws DALException;
	void createOperatoer(UserDTO opr) throws DALException;
	void updateOperatoer(UserDTO opr, int id) throws DALException;
}
