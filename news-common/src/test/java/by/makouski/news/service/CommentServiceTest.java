package by.makouski.news.service;

import by.makouski.news.dao.impl.CommentDAO;
import by.makouski.news.entity.Comment;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.impl.CommentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public class CommentServiceTest {

    private static final String CONTENT = "Ouch!";
    private static final Integer ID = 1;
    private static final Timestamp PUBLISH_DATE = Timestamp.valueOf("2016-10-18 21:44:30");
    private static final Integer NEWS_ID = 1;
    private static final Integer USER_ID = 1;

    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private CommentService commentService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() throws DAOException, ServiceException{
        Comment comment = new Comment();
        comment.setContent(CONTENT);
        comment.setId(ID);
        comment.setPublishDate(PUBLISH_DATE);
        comment.setNews_id(NEWS_ID);
        comment.setUser_id(USER_ID);

        Mockito.when(commentDAO.add(comment)).thenReturn(true);
        Assert.assertTrue(commentService.add(comment));
        Mockito.verify(commentDAO).add(comment);
    }

    @Test
    public void deleteTest() throws DAOException, ServiceException{
        Mockito.when(commentDAO.delete(ID)).thenReturn(true);
        Assert.assertTrue(commentService.delete(ID));
        Mockito.verify(commentDAO).delete(ID);
    }

}
