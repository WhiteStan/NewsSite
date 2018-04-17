package by.makouski.news.dao;

import by.makouski.news.entity.Comment;
import by.makouski.news.exception.DAOException;

import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public interface ICommentDAO extends IBaseDAO<Comment> {
    List<Comment> findByNewsId(Integer id) throws DAOException;
}
