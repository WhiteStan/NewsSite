package by.makouski.news.service.impl;

import by.makouski.news.dao.impl.AuthorDAO;
import by.makouski.news.entity.Author;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.IAuthorService;

import java.util.Collection;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public class AuthorService implements IAuthorService {
    private AuthorDAO authorDAO;

    public boolean add(Author author) throws ServiceException{
        boolean result = false;
        try{
            if(author != null){
                if(authorDAO.add(author))
                {
                    result = true;
                }
            }
        }
        catch(DAOException e){
            throw new ServiceException(e);
        }
        return result;
    }
    public boolean makeExpired(Integer id, Boolean isExpired) throws ServiceException{
        boolean result = false;
        try{
            if(authorDAO.makeExpired(isExpired, id)){
                result =true;
            }
        }
        catch (DAOException e){
            throw new ServiceException(e);
        }
        return result;
    }

    public boolean change(Integer id, String name) throws ServiceException{
        boolean result = false;
        try{
                Author author = new Author();
                author.setId(id);
                author.setName(name);
                if(authorDAO.add(author))
                {
                    result = true;
                }
        }
        catch(DAOException e){
            throw new ServiceException(e);
        }
        return result;
    }
    public Collection<Author> findAll() throws ServiceException{
        Collection<Author> authors;
        try {
            authors = authorDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }

        return authors;
    }

    public void setAuthorDAO(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }
}
