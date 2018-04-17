package by.makouski.news.service.impl;

import by.makouski.news.dao.impl.CommentDAO;
import by.makouski.news.entity.Comment;
import by.makouski.news.exception.DAOException;
import by.makouski.news.exception.ServiceException;
import by.makouski.news.service.ICommentService;

import java.sql.Timestamp;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public class CommentService implements ICommentService {
    private CommentDAO commentDAO;

    @Override
    public boolean add(Comment comment) throws ServiceException {
        boolean result = false;
        try {
            if (commentDAO.add(comment)) {
                result = true;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) throws ServiceException {
        boolean result;
        try {
            result = commentDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public boolean change(Integer id, String content, Timestamp publishDate) throws ServiceException {
        boolean result = false;
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setId(id);
            comment.setPublishDate(publishDate);
            if (commentDAO.update(comment)) {
                result = true;
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public void setCommentDAO(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }
}
