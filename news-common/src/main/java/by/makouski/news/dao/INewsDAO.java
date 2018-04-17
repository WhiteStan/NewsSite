package by.makouski.news.dao;

import by.makouski.news.entity.News;
import by.makouski.news.entity.SearchCriteria;
import by.makouski.news.exception.DAOException;

import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface INewsDAO extends IBaseDAO<News> {
    List<News> findAllNewsBySearchCriteria(SearchCriteria searchCriteria) throws DAOException;
    boolean addTag(Integer id, Integer tagId) throws DAOException;
    boolean deleteTag(Integer id, Integer tagId) throws DAOException;
    boolean addAuthor(Integer id, Integer authorId) throws DAOException;
    boolean deleteAuthor(Integer id, Integer authorId) throws DAOException;
    Integer save(News entity) throws DAOException;
}
