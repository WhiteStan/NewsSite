package by.makouski.news.service;

import by.makouski.news.dao.impl.AuthorDAO;
import by.makouski.news.dao.impl.CommentDAO;
import by.makouski.news.dao.impl.NewsDAO;
import by.makouski.news.dao.impl.TagDAO;
import by.makouski.news.entity.Author;
import by.makouski.news.entity.News;
import by.makouski.news.entity.Tag;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.impl.NewsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.sql.Date;

/**
 * Created by Stas on 18.10.16.
 */
public class NewsServiceTest {
    private static final Integer ID = 1;
    private static final String MAIN_TITLE = "some title";
    private static final String CONTENT = "some content";
    private static final String PHOTO = "some photo";
    private static final Date PUBLISH_DATE = Date.valueOf("2016-10-19");
    private static final String SHORT_TITLE = "some title";
    private static final String NAME = "some Name";

    @Mock
    private NewsDAO newsDAO;
    @Mock
    private CommentDAO commentDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private AuthorDAO authorDAO;

    @InjectMocks
    private NewsService newsService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deleteTest() throws ServiceException, DAOException {
        ArrayList<Integer> newsIds = new ArrayList<>();
        newsIds.add(ID);

        Mockito.when(commentDAO.findByNewsId(ID)).thenReturn(null);
        Mockito.when(commentDAO.delete(ID)).thenReturn(true);
        Mockito.when(newsDAO.delete(ID)).thenReturn(true);
        Assert.assertTrue(newsService.deleteNews(newsIds));
        Mockito.verify(newsDAO).delete(ID);
    }
    @Test
    public void addTest() throws ServiceException, DAOException {
        News news = new News();
        news.setMainTitle(MAIN_TITLE);
        news.setContent(CONTENT);
        news.setPhoto(PHOTO);
        news.setPublishDate(PUBLISH_DATE);
        news.setShortTitle(SHORT_TITLE);

        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(ID);
        tag.setTitle(MAIN_TITLE);
        tags.add(tag);
        news.setTags(tags);

        ArrayList<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(ID);
        author.setName(NAME);
        author.setExpired(false);
        authors.add(author);
        news.setAuthors(authors);

        Mockito.when(newsDAO.save(news)).thenReturn(ID);
        Mockito.when(newsDAO.addTag(ID, ID)).thenReturn(true);
        Mockito.when(newsDAO.addAuthor(ID, ID)).thenReturn(true);
        Assert.assertNotNull(newsService.add(news));
        Mockito.verify(newsDAO).save(news);
        Mockito.verify(newsDAO).addTag(ID, ID);
        Mockito.verify(newsDAO).addAuthor(ID, ID);
    }
    @Test
    public void getNewsPositiveTest() throws ServiceException, DAOException {
        News news = new News();
        news.setMainTitle(MAIN_TITLE);
        news.setContent(CONTENT);
        news.setPhoto(PHOTO);
        news.setPublishDate(PUBLISH_DATE);
        news.setShortTitle(SHORT_TITLE);

        ArrayList<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(ID);
        tag.setTitle(MAIN_TITLE);
        tags.add(tag);
        news.setTags(tags);

        ArrayList<Author> authors = new ArrayList<>();
        Author author = new Author();
        author.setId(ID);
        author.setName(NAME);
        author.setExpired(false);
        authors.add(author);
        news.setAuthors(authors);

        news.setComments(new ArrayList<>());

        Mockito.when(newsDAO.findById(ID)).thenReturn(news);
        Mockito.when(authorDAO.findByNewsId(news.getId())).thenReturn(authors);
        Mockito.when(tagDAO.findByNewsId(news.getId())).thenReturn(tags);
        Mockito.when(commentDAO.findByNewsId(news.getId())).thenReturn(new ArrayList<>());
        Assert.assertEquals(news, newsService.getNews(ID));
        Mockito.verify(newsDAO).findById(ID);
        Mockito.verify(authorDAO).findByNewsId(news.getId());
        Mockito.verify(tagDAO).findByNewsId(news.getId());
        Mockito.verify(commentDAO).findByNewsId(news.getId());
    }

    @Test
    public void getFullNewsNegativeTest() throws ServiceException, DAOException {
        Mockito.when(newsDAO.findById(ID)).thenReturn(null);
        Assert.assertNull(newsService.getNews(ID));
        Mockito.verify(newsDAO).findById(ID);
    }
}
