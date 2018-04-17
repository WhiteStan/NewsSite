package by.makouski.news.dao.impl;

import by.makouski.news.dao.ICommentDAO;
import by.makouski.news.entity.Comment;
import by.makouski.news.exception.DAOException;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/17/2016.
 */
@Repository("commentDAO")
public class CommentDAO implements ICommentDAO {
    private static final String SQL_UPDATE = "UPDATE COMMENTS SET CONTENT = ? WHERE ID = ?";
    private static final String SQL_ADD = "INSERT INTO COMMENTS(CONTENT, PUBLISHDATE, NEWS_ID, USER_ID) VALUES (?, " +
            "?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM COMMENTS WHERE ID = ?";
    private static final String SQL_SELECT = "SELECT ID, CONTENT, PUBLISHDATE, NEWS_ID, USER_ID FROM COMMENTS";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE ID = ?";
    private static final String SQL_SELECT_BY_NEWSID = SQL_SELECT + " WHERE NEWS_ID = ?";

    @Autowired
    private BasicDataSource dataSource;

    public boolean update(Comment entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            ) {
                statement.setString(1, entity.getContent());
                statement.setInt(2, entity.getId());
                if (statement.executeUpdate() == 1)
                    result = true;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public boolean add(Comment entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD)
            ) {
                statement.setString(1, entity.getContent());
                statement.setTimestamp(2, entity.getPublishDate());
                statement.setInt(3, entity.getNews_id());
                statement.setInt(4, entity.getUser_id());
                if (statement.executeUpdate() == 1) {
                    result = true;
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return true;
    }

    public boolean delete(Integer id) throws DAOException {
        boolean result = false;
        if (id != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_DELETE))
            {
                statement.setInt(1, id);
                if(statement.executeUpdate() == 1){
                    result = true;
                }
            }
            catch (SQLException e){
                throw new DAOException(e);
            }
        }
        return result;
    }

    public Comment findById(Integer id) throws DAOException{
        Comment comment = null;
        if(id != null){
            try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
            {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    comment.setId(resultSet.getInt("ID"));
                    comment.setContent(resultSet.getString("CONTENT"));
                    comment.setNews_id(resultSet.getInt("NEWS_ID"));
                    comment.setPublishDate(resultSet.getTimestamp("PUBLISHDATE"));
                    comment.setUser_id(resultSet.getInt("USER_ID"));
                }
            }
            catch (SQLException e){
                throw new DAOException(e);
            }
        }
        return comment;
    }

    public List<Comment> findAll() throws DAOException{
        List<Comment> comments = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT))
        {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("ID"));
                comment.setContent(resultSet.getString("CONTENT"));
                comment.setNews_id(resultSet.getInt("NEWS_ID"));
                comment.setPublishDate(resultSet.getTimestamp("PUBLISHDATE"));
                comment.setUser_id(resultSet.getInt("USER_ID"));
                comments.add(comment);
            }
        }
        catch (SQLException e){
            throw new DAOException(e);
        }
        return comments;
    }

    @Override
    public List<Comment> findByNewsId(Integer id) throws DAOException{
        List<Comment> comments = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NEWSID))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("ID"));
                comment.setContent(resultSet.getString("CONTENT"));
                comment.setNews_id(resultSet.getInt("NEWS_ID"));
                comment.setPublishDate(resultSet.getTimestamp("PUBLISHDATE"));
                comment.setUser_id(resultSet.getInt("USER_ID"));
                comments.add(comment);
            }
        }
        catch (SQLException e){
            throw new DAOException(e);
        }
        return comments;
    }

    public void setDataSource(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }
}
