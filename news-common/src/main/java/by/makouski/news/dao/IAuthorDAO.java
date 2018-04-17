package by.makouski.news.dao;

import by.makouski.news.entity.Author;
import by.makouski.news.exception.DAOException;

import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public interface IAuthorDAO extends IBaseDAO<Author> {
    boolean makeExpired(Boolean isExpired, Integer id) throws DAOException;
    List<Author> findByNewsId(Integer id) throws DAOException;
}
