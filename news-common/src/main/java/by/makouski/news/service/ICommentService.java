package by.makouski.news.service;

import by.makouski.news.entity.Comment;
import by.makouski.news.exception.ServiceException;

import java.sql.Timestamp;

/**
 * Created by Stanislau_Makouski on 10/13/2016.
 */
public interface ICommentService {
    boolean add(Comment comment) throws ServiceException;
    boolean delete(Integer id) throws ServiceException;
    boolean change(Integer id, String content, Timestamp publishDate) throws ServiceException;
}
