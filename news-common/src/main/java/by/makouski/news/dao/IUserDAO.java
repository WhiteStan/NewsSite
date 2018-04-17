package by.makouski.news.dao;

import by.makouski.news.entity.User;
import by.makouski.news.exception.DAOException;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface IUserDAO extends IBaseDAO<User> {
    User findByLogin(String login) throws DAOException;
    boolean changePassword(Integer id, String password) throws DAOException;
    boolean changeRole(Integer id, String Role) throws DAOException;
}
