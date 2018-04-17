package by.makouski.news.dao.impl;

import by.makouski.news.dao.IAuthorDAO;
import by.makouski.news.entity.Author;
import by.makouski.news.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislau_Makouski on 10/17/2016.
 */
@Repository("authorDAO")
public class AuthorDAO implements IAuthorDAO {
    private static final String SQL_UPDATE = "UPDATE AUTHOR SET NAME = ? WHERE ID = ?";
    private static final String SQL_ADD = "INSERT INTO AUTHOR(NAME, EXPIRED) VALUES (?, ?)";
    private static final String SQL_MAKE_EXPIRED= "UPDATE AUTHOR SET EXPIRED = ? WHERE ID = ?";
    private static final String SQL_SELECT = "SELECT AUTHOR.ID, NAME, EXPIRED FROM AUTHOR";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE ID = ?";
    private static final String SQL_SELECT_BY_NEWSID = SQL_SELECT + " JOIN M2M_NEWS_AUTHORS ON AUTHOR.ID " +
            " = M2M_NEWS_AUTHORS.AUTHOR_ID WHERE NEWS_ID = ?";

    private DataSource dataSource;

    public boolean update(Author entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            ) {
                statement.setString(1, entity.getName());
                statement.setInt(2, entity.getId());
                if (statement.executeUpdate() == 1)
                    result = true;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public boolean add(Author entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD)
            ) {
                statement.setString(1, entity.getName());
                statement.setBoolean(2, entity.isExpired());
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
        throw new UnsupportedOperationException();
    }

    public Author findById(Integer id) throws DAOException{
        Author author = null;
        if(id != null){
            try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
            {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    author.setId(resultSet.getInt("ID"));
                    author.setExpired(resultSet.getBoolean("EXPIRED"));
                    author.setName(resultSet.getString("NAME"));
                }
            }
            catch (SQLException e){
                throw new DAOException(e);
            }
        }
        return author;
    }

    public List<Author> findAll() throws DAOException{
        List<Author> authors = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT))
        {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Author author = new Author();
                author.setId(resultSet.getInt("ID"));
                author.setExpired(resultSet.getBoolean("EXPIRED"));
                author.setName(resultSet.getString("NAME"));
                authors.add(author);
            }
        }
        catch (SQLException e){
            throw new DAOException(e);
        }
        return authors;
    }

    @Override
    public boolean makeExpired(Boolean isExpired, Integer id) throws DAOException{
        boolean result = false;
        if (isExpired != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_MAKE_EXPIRED);
            ) {
                statement.setBoolean(1, isExpired);
                statement.setInt(2, id);
                if (statement.executeUpdate() == 1)
                    result = true;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    @Override
    public List<Author> findByNewsId(Integer id) throws DAOException{
        List<Author> authors = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NEWSID))
        {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Author author = new Author();
                author.setId(resultSet.getInt("ID"));
                author.setName(resultSet.getString("NAME"));
                author.setExpired(resultSet.getBoolean("EXPIRED"));
                authors.add(author);
            }
        }
        catch (SQLException e){
            throw new DAOException(e);
        }
        return authors;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
