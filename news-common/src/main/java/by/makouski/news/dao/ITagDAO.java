package by.makouski.news.dao;

import by.makouski.news.entity.Tag;
import by.makouski.news.exception.DAOException;

import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/18/2016.
 */
public interface ITagDAO extends IBaseDAO<Tag> {
    List<Tag> findByNewsId(Integer id) throws DAOException;
}
