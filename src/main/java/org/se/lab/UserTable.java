package org.se.lab;

import java.util.List;


public interface UserTable
{
	User insert(User user);
	User update(User user);
	void delete(User user);
	
	User findById(int id);
	List<User> findAll();
	
	User createUser(int id, String firstName, String lastName, String username, String password);
}
