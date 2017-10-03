package org.se.lab;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.se.lab.User;
import org.se.lab.UserTable;
import org.se.lab.UserTableImpl;

public class UserTableTest
{
	private static final JdbcTestHelper JDBC_HELPER = new JdbcTestHelper();
	private static final JpaTestHelper JPA_HELPER = new JpaTestHelper();

	private EntityManager em = JPA_HELPER.getEntityManager("test");
    private UserTable table;
       
	@BeforeClass
	public static void init()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/createUserTable.sql");
	}
	
	@AfterClass
	public static void destroy()
	{
		JDBC_HELPER.executeSqlScript("src/test/resources/sql/dropUserTable.sql");		
	}
    
	
    @Before
    public void setUp()
    {
        table = new UserTableImpl(em);          
        
        JPA_HELPER.txBegin();        
        table.createUser(1, "homer", "simpson", "homer", "Kqq3lbODaQT4LvxsoihdknrtdSBiFOHaODQY65DJBS8=");        
        table.createUser(2, "marge","simpson", "marge", "tLgR+kBQUymuhx5S8DUnw3IMmvf7hgeBllhTXFSExB4=");
    }   

    @After
    public void tearDown()
    {
    	JPA_HELPER.txRollback();
    }


    @Test
    public void testFindById()
    {
        User homer = table.findById(1);
        
        Assert.assertEquals("homer", homer.getUsername());
        Assert.assertEquals("Kqq3lbODaQT4LvxsoihdknrtdSBiFOHaODQY65DJBS8=", homer.getPassword());        
    }
    
	@Test
	public void testUpdate()
	{
		User homer = table.findById(1);

		homer.setPassword("qgZAC2x8cgFdoY3EHaWSVZKoXYuGbBMagYt9GH9v7dw=");
		table.update(homer);

		User user = table.findById(1);
		Assert.assertEquals("homer", user.getUsername());
		Assert.assertEquals("qgZAC2x8cgFdoY3EHaWSVZKoXYuGbBMagYt9GH9v7dw=", user.getPassword());
	}

	@Test
	public void testDelete()
	{
		User homer = table.findById(1);
		table.delete(homer);
	    
		User user = table.findById(1);
	    Assert.assertNull(user);
	}

    @Test
    public void testFindAll()
    {
	    List<User> users = table.findAll();
	    
        Assert.assertEquals(2,users.size());
        Assert.assertEquals("homer", users.get(0).getUsername());
        Assert.assertEquals("marge", users.get(1).getUsername());
        for(User u : users)
        {
            System.out.println(u.getId() + ", " + u.getUsername() + ", " + u.getPassword());
        }
    }
}
