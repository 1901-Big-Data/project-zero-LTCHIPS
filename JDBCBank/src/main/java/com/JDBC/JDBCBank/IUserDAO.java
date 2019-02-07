package com.JDBC.JDBCBank;

import java.util.List;
import java.util.Optional;

public interface IUserDAO<User>
{
	Optional<User> getUserById(long id);
	
	List<User> getAllUsers();
	
	void saveUser(User user);
	
	void updateUser(User User, String [] params);
	
	void deleteUser(User User);
	
}
