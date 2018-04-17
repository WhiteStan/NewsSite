package by.makouski.news.service;

import by.makouski.news.dao.impl.AuthorDAO;
import by.makouski.news.entity.Author;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.impl.AuthorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */

public class AuthorServiceTest {

    @Mock
    AuthorDAO authorDAO;

    @InjectMocks
    AuthorService authorService;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() throws ServiceException, DAOException{
        Author author = new Author();
        author.setName("Julia Magametova");

        Mockito.when(authorDAO.add(author)).thenReturn(true);
        Assert.assertTrue(authorService.add(author));
        Mockito.verify(authorDAO).add(author);
    }

    @Test
    public void makeExpired() throws ServiceException, DAOException{
        Mockito.when(authorDAO.makeExpired(true, 1)).thenReturn(true);
        Assert.assertTrue(authorService.makeExpired(1, true));
        Mockito.verify(authorDAO).makeExpired(true, 1);
    }
}
