package by.makouski.news.dao.impl;

import by.makouski.news.dao.ITagDAO;
import by.makouski.news.entity.Tag;
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
@Repository("tagDAO")
public class TagDAO implements ITagDAO {
    private static final String SQL_UPDATE = "UPDATE TAG SET TITLE = ? WHERE ID = ?";
    private static final String SQL_ADD = "INSERT INTO TAG(TITLE) VALUES (?)";
    private static final String SQL_DELETE = "DELETE FROM TAG WHERE ID = ?";
    private static final String SQL_SELECT = "SELECT TAG.ID, TITLE FROM TAG";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE ID = ?";
    private static final String SQL_SELECT_BY_TITILE = SQL_SELECT + " WHERE TITLE = ?";
    private static final String SQL_SELECT_BY_NEWSID = SQL_SELECT + " JOIN M2M_NEWS_TAGS ON TAG.ID " +
            "=M2M_NEWS_TAGS.TAGS_ID WHERE NEWS_ID = ?";

    @Autowired
    private BasicDataSource dataSource;

    public boolean update(Tag entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            ) {
                statement.setString(1, entity.getTitle());
                statement.setInt(2, entity.getId());
                if (statement.executeUpdate() == 1)
                    result = true;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public boolean add(Tag entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD);
                 PreparedStatement check = connection.prepareStatement(SQL_SELECT_BY_TITILE)
            ) {
                check.setString(1, entity.getTitle());
                ResultSet resultSet = check.executeQuery();
                if (resultSet.next()) {
                    result = false;
                } else {
                    statement.setString(1, entity.getTitle());
                    if(statement.executeUpdate() ==1) {
                        result = true;
                    }
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public boolean delete(Integer id) throws DAOException {
        boolean result = false;
        if (id != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
                statement.setInt(1, id);
                if (statement.executeUpdate() == 1) {
                    result = true;
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public Tag findById(Integer id) throws DAOException {
        Tag tag = null;
        if (id != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    tag.setId(resultSet.getInt("ID"));
                    tag.setTitle(resultSet.getString("TITLE"));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return tag;
    }

    public List<Tag> findAll() throws DAOException {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt("ID"));
                tag.setTitle(resultSet.getString("TITLE"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tags;
    }

    @Override
    public List<Tag> findByNewsId(Integer id) throws DAOException {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NEWSID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt("ID"));
                tag.setTitle(resultSet.getString("TITLE"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return tags;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
