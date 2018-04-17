package by.makouski.news.service;

import by.makouski.news.dao.impl.UserDAO;
import by.makouski.news.entity.User;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.impl.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by Stas on 18.10.16.
 */
public class UserServiceTest {

    private static final Integer ID = 1;
    private static final String LOGIN = "Ivan";
    private static final String PASSWORD = "Greenfield123";
    private static final String ROLE = "ADMIN";

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() throws ServiceException, DAOException {
        User user = new User();
        user.setId(ID);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setRole(ROLE);

        Mockito.when(userDAO.add(user)).thenReturn(true);
        Assert.assertTrue(userService.add(user));
        Mockito.verify(userDAO).add(user);
    }
    @Test
    public void changePasswordTest() throws ServiceException, DAOException{
        Mockito.when(userDAO.changePassword(ID, PASSWORD)).thenReturn(true);
        Assert.assertTrue(userService.changePassword(ID, PASSWORD));
        Mockito.verify(userDAO).changePassword(ID, PASSWORD);
    }
    @Test
    public void findByLogin() throws ServiceException, DAOException {
        User expectedUser = new User();
        expectedUser.setId(ID);
        expectedUser.setLogin(LOGIN);
        expectedUser.setPassword(PASSWORD);
        expectedUser.setRole(ROLE);

        Mockito.when(userDAO.findByLogin(LOGIN)).thenReturn(expectedUser);
        Assert.assertEquals(expectedUser, userService.findByLogin(LOGIN));
        Mockito.verify(userDAO).findByLogin(LOGIN);
    }

}
