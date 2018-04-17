package by.makouski.news.dao;

import by.makouski.news.entity.User;
import by.makouski.news.exception.DAOException;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

/**
 * Created by Stanislau_Makouski on 10/24/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContextConfiguration.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/testData/inputUserDAO")
@DatabaseTearDown(value = "/testData/inputUserDAO",
        type = DatabaseOperation.DELETE_ALL)
public class UserDAOTest {
    @Autowired
    @Qualifier("userDAO")
    private IUserDAO userDAO;

    @Test
    public void findByLoginTest() throws DAOException {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setLogin("Jake");
        expectedUser.setPassword("SR20DETvsRB26DETT");
        expectedUser.setRole("USER");

        User user = userDAO.findByLogin("Jake");
        Assert.assertEquals(expectedUser,user);
    }

    @Test
    public void findByIdTest() throws DAOException {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setLogin("Jake");
        expectedUser.setPassword("SR20DETvsRB26DETT");
        expectedUser.setRole("USER");

        User user = userDAO.findById(1);
        Assert.assertEquals(expectedUser,user);
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedUserAdd",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void addTest() throws DAOException {
        User user = new User();
        user.setLogin("WhiteStan");
        user.setPassword("SR20DETvsRB26DETT");
        user.setRole("USER");
        Assert.assertTrue(userDAO.add(user));
    }
}
