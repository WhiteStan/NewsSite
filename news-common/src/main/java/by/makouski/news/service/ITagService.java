package by.makouski.news.service;

import by.makouski.news.entity.Tag;
import by.makouski.news.exception.ServiceException;

import java.util.Collection;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface ITagService {
    boolean add(Tag tag) throws ServiceException;
    boolean delete(Integer id) throws ServiceException;
    boolean change(Integer id, String title) throws ServiceException;
    Collection<Tag> getAllTags() throws ServiceException;
}
