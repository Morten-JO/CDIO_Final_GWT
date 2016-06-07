package daointerfaces01917;

import java.util.List;

import dto01917.UserDTO;

public interface OperatoerDAO {
	UserDTO getOperatoer(int oprId) throws DALException;
	List<UserDTO> getOperatoerList() throws DALException;
	void createOperatoer(UserDTO opr) throws DALException;
	void updateOperatoer(UserDTO opr, int id) throws DALException;
}
