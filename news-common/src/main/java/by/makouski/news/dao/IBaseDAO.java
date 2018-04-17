package by.makouski.news.dao;

import by.makouski.news.exception.DAOException;

import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface IBaseDAO<T> {
   boolean update(T entity) throws DAOException;
   boolean add(T entity) throws DAOException;
   boolean delete(Integer id) throws DAOException;
   T findById(Integer id) throws DAOException;
   List<T> findAll() throws DAOException;
}
