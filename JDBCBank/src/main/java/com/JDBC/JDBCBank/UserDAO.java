package com.JDBC.JDBCBank;

import java.util.List;
import java.util.Optional;


public class UserDAO implements IUserDAO<User>
{
	private List<User> Users;
	
	public Optional<User> getUserById(long id) 
	{
		Optional<User> returnUser = Optional.empty();
		
		for (User user : Users) 
		{
			if (user.getUserID() == 0) 
			{
				returnUser = Optional.of(user);
				break;
				
			}
		}
		
		return returnUser;
	}

	public List<User> getAllUsers() 
	{
		return Users;
	}

	public void saveUser(User user) 
	{
		
		UserCompare comp = new UserCompare();
		
		for (int x = 0; x < Users.size(); x++) 
		{
			if (comp.compare(user, Users.get(x)) == 0)
			{
				Users.set(x, user);
				return;
			}
		}
		
		Users.add(user);
		
		
	}

	public void updateUser(User user, String[] params) 
	{
		// TODO Auto-generated method stub
		
	}

	public void deleteUser(User user)
	{
		UserCompare comp = new UserCompare();
		
		Users.removeIf(x -> comp.compare(user, x) == 0);
		
	}

}
