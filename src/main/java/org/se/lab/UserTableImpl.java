package org.se.lab;

import java.util.List;

import javax.persistence.EntityManager;

class UserTableImpl
	implements UserTable
{
	/*
	 * Constructor injection
	 */
	public UserTableImpl(EntityManager em)
	{
		this.em = em;
	}
		
	private EntityManager em;
	public EntityManager getEntityManager()
	{
		return em;
	}
	
	
	/*
	 * CRUD methods
	 */	
	
	@Override
	public User insert(User entity)
	{
		em.persist(entity);
		return entity;
	}

	@Override
	public User update(User entity)
	{
		return em.merge(entity);
	}

	@Override
	public void delete(User entity)
	{
		em.remove(entity);
	}

	@Override
	public User findById(int id)
	{
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll()
	{
		final String hql = "SELECT u FROM " + User.class.getName() + " AS u";	    
	    return em.createQuery(hql).getResultList();
	}	
	
	
	/*
	 * Factory methods
	 */

	@Override
	public User createUser(int id, String firstName, String lastName, String username, String password)
	{
		User user = new User();
		user.setId(id);
		user.setFirstname(firstName);
		user.setLastname(lastName);
		user.setUsername(username);
		user.setPassword(password);		
		insert(user);
		return user;
	}
}
