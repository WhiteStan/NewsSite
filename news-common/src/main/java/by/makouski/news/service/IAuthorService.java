package by.makouski.news.service;

import by.makouski.news.entity.Author;
import by.makouski.news.exception.ServiceException;

import java.util.Collection;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface IAuthorService {
    boolean add(Author author) throws ServiceException;
    boolean makeExpired(Integer id, Boolean isExpired) throws ServiceException;
    boolean change(Integer id, String name) throws ServiceException;
    Collection<Author> findAll() throws ServiceException;
}
