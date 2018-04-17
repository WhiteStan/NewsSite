package by.makouski.news.dao.impl;

import by.makouski.news.dao.INewsDAO;
import by.makouski.news.entity.News;
import by.makouski.news.entity.SearchCriteria;
import by.makouski.news.exception.DAOException;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/17/2016.
 */
@Repository("newsDAO")
public class NewsDAO implements INewsDAO {

    private static final String SQL_UPDATE = "UPDATE NEWS SET MAINTITLE = ?, SHORTTITLE = ?, CONTENT = ?," +
            "PUBLISHDATE = ?, PHOTO = ? WHERE ID = ?";
    private static final String SQL_ADD = "INSERT INTO NEWS(MAINTITLE, SHORTTITLE, CONTENT, PUBLISHDATE, PHOTO) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_DELETE_LINKED_COMMENTS = "DELETE FROM COMMENTS WHERE NEWS_ID = ?";
    private static final String SQL_DELETE_LINKED_TAGS = "DELETE FROM M2M_NEWS_TAGS WHERE NEWS_ID = ?";
    private static final String SQL_DELETE_LINKED_AUTHORS = "DELETE FROM M2M_NEWS_AUTHORS WHERE NEWS_ID = ?";
    private static final String SQL_DELETE_NEWS = "DELETE FROM NEWS WHERE ID = ?";

    private static final String SQL_SELECT = "SELECT MAINTITLE, SHORTTITLE, CONTENT, PUBLISHDATE, PHOTO FROM  NEWS";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE ID = ?";
    private static final String SQL_ADD_TAG = "INSERT INTO M2M_NEWS_TAG(NEWS_ID, TAGS_ID) VALUES (?, ?)";
    private static final String SQL_ADD_AUTHOR = "INSERT INTO M2M_NEWS_AUTHORS(NEWS_ID, AUTHOR_ID) VALUES (?, ?)";

    @Autowired
    private BasicDataSource dataSource;

    public boolean update(News entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            ) {
                statement.setString(1, entity.getMainTitle());
                statement.setString(2, entity.getShortTitle());
                statement.setString(3, entity.getContent());
                statement.setDate(4, entity.getPublishDate());
                statement.setString(5, entity.getPhoto());
                statement.setInt(6, entity.getId());
                if (statement.executeUpdate() == 1)
                    result = true;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public boolean add(News entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public Integer save(News entity) throws DAOException {
        Integer result = null;
        if (entity != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD,
                         new String[] { "ID" })
            ) {
                statement.setString(1, entity.getMainTitle());
                statement.setString(2, entity.getShortTitle());
                statement.setString(3, entity.getContent());
                statement.setDate(4, entity.getPublishDate());
                statement.setString(5, entity.getPhoto());
                if (statement.executeUpdate() == 1) {
                    ResultSet resultSet = statement.getGeneratedKeys();
                    resultSet.next();
                    result = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public boolean delete(Integer id) throws DAOException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement delete = null;
        PreparedStatement deleteAuthors = null;
        PreparedStatement deleteTags = null;
        PreparedStatement deleteComments = null;
        if (id != null) {
            try {
                connection = dataSource.getConnection();
                delete = connection.prepareStatement(SQL_DELETE_NEWS);
                deleteAuthors = connection.prepareStatement(SQL_DELETE_LINKED_AUTHORS);
                deleteTags = connection.prepareStatement(SQL_DELETE_LINKED_TAGS);
                deleteComments = connection.prepareStatement(SQL_DELETE_LINKED_COMMENTS);
                connection.setAutoCommit(false);
                deleteAuthors.setInt(1, id);
                deleteAuthors.executeUpdate();
                deleteTags.setInt(1, id);
                deleteTags.executeUpdate();
                deleteComments.setInt(1, id);
                deleteComments.executeUpdate();
                delete.setInt(1, id);
                if (delete.executeUpdate() == 1) {
                    connection.commit();
                    result = true;
                }
            } catch (SQLException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        throw new DAOException(ex);
                    }
                }
                throw new DAOException(e);
            } finally {
                try {
                    if (deleteAuthors != null) {
                        deleteAuthors.close();
                    }
                    if (deleteTags != null) {
                        deleteTags.close();
                    }
                    if (deleteComments != null) {
                        deleteComments.close();
                    }
                    if (delete != null) {
                        delete.close();
                    }
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new DAOException(e);
                }
            }
        }
        return result;
    }

    public News findById(Integer id) throws DAOException {
        News news = null;
        if (id != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    news.setId(resultSet.getInt("ID"));
                    news.setContent(resultSet.getString("CONTENT"));
                    news.setMainTitle(resultSet.getString("MAINTITLE"));
                    news.setShortTitle(resultSet.getString("SHORTTITLE"));
                    news.setPhoto(resultSet.getString("PHOTO"));
                    news.setPublishDate(resultSet.getDate("PUBLISHDATE"));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return news;
    }

    public List<News> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<News> findAllNewsBySearchCriteria(SearchCriteria searchCriteria) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addTag(Integer id, Integer tagId) throws DAOException {
        boolean result = false;
        if (id != null && tagId != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD_TAG)) {
                statement.setInt(1, id);
                statement.setInt(2, tagId);
                if (statement.executeUpdate() == 1) {
                    result = true;
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public boolean deleteTag(Integer id, Integer tagId) throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAuthor(Integer id, Integer authorId) throws DAOException {
        boolean result = false;
        if (id != null && authorId != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD_AUTHOR)) {
                statement.setInt(1, id);
                statement.setInt(2, authorId);
                if (statement.executeUpdate() == 1) {
                    result = true;
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public boolean deleteAuthor(Integer id, Integer authorId) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public void setDataSource(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }
}
