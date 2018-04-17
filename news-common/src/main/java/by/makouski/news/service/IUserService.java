package by.makouski.news.service;

import by.makouski.news.entity.User;
import by.makouski.news.exception.ServiceException;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface IUserService {
    boolean add(User user) throws ServiceException;
    boolean delete(Integer id) throws ServiceException;
    boolean changePassword(Integer id, String password) throws ServiceException;
    boolean changeRole(Integer id, String role) throws ServiceException;
    User getUser(Integer id) throws ServiceException;
    User findByLogin(String login) throws ServiceException;
}
