package by.makouski.news.dao;

import by.makouski.news.dao.impl.NewsDAO;
import by.makouski.news.entity.News;
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

import java.sql.Date;

/**
 * Created by Stanislau_Makouski on 10/24/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContextConfiguration.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/testData/inputNewsDAO")
@DatabaseTearDown(value = "/testData/inputNewsDAO",
        type = DatabaseOperation.DELETE_ALL)
public class NewsDAOTest {

    @Autowired
    @Qualifier("newsDAO")
    private NewsDAO newsDAO;

    @Test
    @ExpectedDatabase(value = "/testData/expectedNewsUpdate",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateTest() throws DAOException {
        News news = new News();
        news.setId(1);
        news.setMainTitle("new title");
        news.setShortTitle("new short title");
        news.setContent("new content");
        news.setPublishDate(Date.valueOf("2016-10-22;"));
        Assert.assertTrue(newsDAO.update(news));
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedNewsSave.xml",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void saveTest() throws DAOException {
        News news = new News();
        news.setPublishDate(Date.valueOf("2016-10-22"));
        news.setContent("Another everyday news");
        news.setShortTitle("And another short title");
        news.setMainTitle("Brand new Main Title");
        Assert.assertNotNull(newsDAO.save(news));
    }
}
