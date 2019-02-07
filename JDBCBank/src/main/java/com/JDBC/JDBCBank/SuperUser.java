package com.JDBC.JDBCBank;

import java.util.HashSet;

public class SuperUser extends User
{
	public SuperUser(String userNameArg, long userIDArg, HashSet<Long> accountIDsArg) 
	{
		super(userNameArg, userIDArg, accountIDsArg);	
	}

}
