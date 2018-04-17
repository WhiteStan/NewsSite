package by.makouski.news.dao;

import by.makouski.news.entity.Author;
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

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Stanislau_Makouski on 10/19/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContextConfiguration.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/testData/inputAuthorDAO.xml")
@DatabaseTearDown(value = "/testData/inputAuthorDAO.xml",
        type = DatabaseOperation.DELETE_ALL)
public class AuthorDAOTest {

    private static final String AUTHOR_NAME = "Reiner Sergey";

    @Autowired
    @Qualifier("authorDAO")
    private IAuthorDAO authorDAO;

    @Test
    @ExpectedDatabase(value = "/testData/expectedAuthorAdd.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void addTest() throws DAOException {
        Author author = new Author();
        author.setName(AUTHOR_NAME);
        Assert.assertTrue(authorDAO.add(author));
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedAuthorUpdate.xml",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateTest() throws DAOException {
        Author author = new Author();
        author.setName(AUTHOR_NAME);
        author.setId(3);
        Assert.assertTrue(authorDAO.update(author));
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedAuthorMakeExpired",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void makeExpiredTest() throws DAOException{
        Assert.assertTrue(authorDAO.makeExpired(true, 2));
    }

    @Test
    public void findAllTest() throws DAOException {
        ArrayList<Author> expectedList = new ArrayList<>();
        Author author = new Author();
        author.setId(1);
        author.setName("Iman Masterman");
        expectedList.add(author);

        author = new Author();
        author.setId(2);
        author.setName("Blandus Rida");
        expectedList.add(author);

        author = new Author();
        author.setId(3);
        author.setExpired(true);
        author.setName("Moses Othmar");
        expectedList.add(author);

        ArrayList<Author> authors = new ArrayList<>(authorDAO.findAll());
        Collections.sort(authors, (o1, o2) -> o1.getId().compareTo(o2.getId()));
        Assert.assertEquals(expectedList, authors);
    }

    @Test
    @DatabaseSetup("/testData/inputNewsDAO")
    @DatabaseTearDown(value = "/testData/inputNewsDAO",
            type = DatabaseOperation.DELETE_ALL)
    public void findByNewsIdPositiveTest() throws DAOException {
        ArrayList<Author> expectedAuthors = new ArrayList<>();
        Author author = new Author();
        author.setId(3);
        author.setName("Moses Othmar");
        expectedAuthors.add(author);

        author = new Author();
        author.setId(2);
        author.setName("Blandus Rida");
        expectedAuthors.add(author);

        ArrayList<Author> authors = new ArrayList<>(authorDAO.findByNewsId(1));
        Assert.assertEquals(expectedAuthors, authors);
    }

    @Test
    @DatabaseSetup("/testData/inputNewsDAO")
    @DatabaseTearDown(value = "/testData/inputNewsDAO",
            type = DatabaseOperation.DELETE_ALL)
    public void findByNewsIdNegativeTest() throws DAOException {
        ArrayList<Author> expectedAuthors = new ArrayList<>();
        ArrayList<Author> authors = new ArrayList<>(authorDAO.findByNewsId(0));
        Assert.assertEquals(expectedAuthors, authors);
    }
}
