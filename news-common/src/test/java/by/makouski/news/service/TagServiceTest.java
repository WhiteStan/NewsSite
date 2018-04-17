package by.makouski.news.service;

import by.makouski.news.dao.impl.TagDAO;
import by.makouski.news.entity.Tag;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.impl.TagService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stas on 18.10.16.
 */
public class TagServiceTest {
    private static final Integer ID = 1;
    private static final String TITLE = "Funny";
    private static final Integer SECOND_ID = 2;
    private static final String SECOND_TITLE = "Tech";

    @Mock
    private TagDAO tagDAO;

    @InjectMocks
    private TagService tagService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addTest() throws DAOException, ServiceException {
        Tag tag = new Tag();
        tag.setId(ID);
        tag.setTitle(TITLE);

        Mockito.when(tagDAO.add(tag)).thenReturn(true);
        Assert.assertTrue(tagService.add(tag));
        Mockito.verify(tagDAO).add(tag);
    }

    @Test
    public void getAllTags() throws DAOException, ServiceException{
        Tag firstTag = new Tag();
        firstTag.setId(ID);
        firstTag.setTitle(TITLE);
        Tag secondTag= new Tag();
        secondTag.setId(SECOND_ID);
        secondTag.setTitle(SECOND_TITLE);
        List<Tag> tags = new ArrayList<>();
        tags.add(firstTag);
        tags.add(secondTag);

        Mockito.when(tagDAO.findAll()).thenReturn(tags);
        Assert.assertEquals(tags, tagService.getAllTags());
        Mockito.verify(tagDAO);
    }
}
