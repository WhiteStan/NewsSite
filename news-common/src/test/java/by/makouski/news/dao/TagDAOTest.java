package by.makouski.news.dao;

import by.makouski.news.entity.Tag;
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
 * Created by Stanislau_Makouski on 10/23/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContextConfiguration.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/testData/inputTagDAO")
@DatabaseTearDown(value = "/testData/inputTagDAO",
        type = DatabaseOperation.DELETE_ALL)
public class TagDAOTest {
    @Autowired
    @Qualifier("tagDAO")
    private ITagDAO tagDAO;

    @Test
    @ExpectedDatabase(value = "/testData/expectedTagAdd",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void addPositiveTest() throws DAOException {
        Tag tag = new Tag();
        tag.setTitle("java");
        Assert.assertTrue(tagDAO.add(tag));
    }

    @Test
    @ExpectedDatabase(value = "/testData/inputTagDAO",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void addNegativeTest() throws DAOException{
        Tag tag = new Tag();
        tag.setTitle("funny");
        Assert.assertFalse(tagDAO.add(tag));
    }

    @Test
    @DatabaseSetup("/testData/inputNewsDAO")
    @DatabaseTearDown(value = "/testData/inputNewsDAO",
    type = DatabaseOperation.DELETE_ALL)
    public void findByNewsIdPositiveTest() throws DAOException {
        ArrayList<Tag> expectedList = new ArrayList<>();
        Tag tag = new Tag();
        tag.setTitle("funny");
        tag.setId(1);
        expectedList.add(tag);

        tag = new Tag();
        tag.setTitle("scary");
        tag.setId(2);
        expectedList.add(tag);

        ArrayList<Tag> tags = new ArrayList<>(tagDAO.findByNewsId(2));
        Collections.sort(tags, ((o1, o2) -> o1.getId().compareTo(o2.getId())));
        Assert.assertEquals(expectedList, tags);
    }

    @Test
    public void findAllTest() throws DAOException {
        ArrayList<Tag> expectedList = new ArrayList<Tag>();
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTitle("funny");
        expectedList.add(tag);

        tag = new Tag();
        tag.setId(2);
        tag.setTitle("scary");
        expectedList.add(tag);

        ArrayList<Tag> tags = new ArrayList(tagDAO.findAll());
        Collections.sort(tags, (o1, o2) -> o1.getId().compareTo(o2.getId()));
        Assert.assertEquals(expectedList, tags);
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedTagDelete",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteTest() throws DAOException {
        Assert.assertTrue(tagDAO.delete(2));
    }
}
