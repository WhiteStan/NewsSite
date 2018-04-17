package by.makouski.news.dao;

import by.makouski.news.entity.Comment;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by Stanislau_Makouski on 10/22/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:testContextConfiguration.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
@DatabaseSetup("/testData/inputCommentDAO")
@DatabaseTearDown(value = "/testData/inputCommentDAO",
        type = DatabaseOperation.DELETE_ALL)
public class CommentDAOTest {
    private static final String COMMENT_CONTENT = "expected comment";
    private static final Timestamp COMMENT_PUBLISHDATE = Timestamp.valueOf("2016-10-23 18:00:33");
    private static final Integer COMMENT_USER_ID = 1;
    private static final Integer COMMENT_NEWS_ID = 2;

    @Autowired
    @Qualifier("commentDAO")
    private ICommentDAO commentDAO;

    @Test
    @ExpectedDatabase(value = "/testData/expectedCommentAdd",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL)
    public void addTest() throws DAOException {
        Comment comment = new Comment();
        comment.setContent(COMMENT_CONTENT);
        comment.setPublishDate(COMMENT_PUBLISHDATE);
        comment.setUser_id(COMMENT_USER_ID);
        comment.setNews_id(COMMENT_NEWS_ID);
        Assert.assertTrue(commentDAO.add(comment));
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedCommentUpdate",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    @DatabaseTearDown(type = DatabaseOperation.DELETE_ALL)
    public void updatePositiveTest() throws DAOException {
        Comment comment = new Comment();
        comment.setId(3);
        comment.setContent(COMMENT_CONTENT);
        Assert.assertTrue(commentDAO.update(comment));
    }

    @Test
    @ExpectedDatabase(value = "/testData/inputCommentDAO",
            assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void updateNegativeTest() throws DAOException {
        Comment comment = new Comment();
        comment.setId(0);
        comment.setContent(COMMENT_CONTENT);
        Assert.assertFalse(commentDAO.update(comment));
    }

    @Test
    @ExpectedDatabase(value = "/testData/expectedCommentDelete",
    assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void deleteTest() throws DAOException {
        Assert.assertTrue(commentDAO.delete(3));
    }

    @Test
    public void findByNewsIdTest() throws DAOException {
        ArrayList<Comment> expectedComments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setId(1);
        comment.setContent("some comment");
        comment.setPublishDate(Timestamp.valueOf("2016-10-22 20:47:33"));
        comment.setNews_id(1);
        comment.setUser_id(1);
        expectedComments.add(comment);

        comment = new Comment();
        comment.setId(2);
        comment.setContent("different comment");
        comment.setPublishDate(Timestamp.valueOf("2016-10-22 20:47:33"));
        comment.setNews_id(1);
        comment.setUser_id(2);
        expectedComments.add(comment);

        ArrayList<Comment> comments = new ArrayList<>(commentDAO.findByNewsId(1));
        Collections.sort(comments, (o1, o2) -> (o1.getId().compareTo(o2.getId())));
        Assert.assertEquals(expectedComments, comments);
    }
}
