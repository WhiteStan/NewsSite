package by.makouski.news.service.impl;

import by.makouski.news.dao.impl.UserDAO;
import by.makouski.news.entity.User;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.IUserService;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public class UserService implements IUserService{
    private UserDAO userDAO;



    public void setUserDAO(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public boolean add(User user) throws ServiceException {
        boolean result = false;
        if (user != null) {
            try {
                if (userDAO.add(user)) {
                    result = true;
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) throws ServiceException {
        boolean result = false;
            try {
                if (userDAO.delete(id)) {
                    result = true;
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        return result;
    }

    @Override
    public boolean changePassword(Integer id, String password) throws ServiceException {
        boolean result = false;
            try {
                if (userDAO.changePassword(id, password)) {
                    result = true;
                }
            } catch (DAOException e) {
                throw new ServiceException(e);
            }
        return result;
    }

    @Override
    public boolean changeRole(Integer id, String role) throws ServiceException {
        boolean result = false;
        try {
            if (userDAO.changeRole(id, role)) {
                result = true;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User getUser(Integer id) throws ServiceException {
        User user;
        try {
            user = userDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User findByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDAO.findByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return user;
    }
}
