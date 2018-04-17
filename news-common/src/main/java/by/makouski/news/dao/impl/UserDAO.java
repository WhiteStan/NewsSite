package by.makouski.news.dao.impl;

import by.makouski.news.dao.IUserDAO;
import by.makouski.news.entity.User;
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
@Repository("userDAO")
public class UserDAO implements IUserDAO {
    private static final String SQL_UPDATE_PASSWORD = "UPDATE USERS SET  PASSWORD = ? WHERE ID = ?";
    private static final String SQL_UPDATE_ROLE = "UPDATE USERS SET  ROLE = ? WHERE ID = ?";
    private static final String SQL_ADD = "INSERT INTO USERS (LOGIN, PASSWORD, ROLE) VALUES (?,?,?)";
    private static final String SQL_DELETE = "DELETE FROM USERS WHERE ID = ?";
    private static final String SQL_SELECT = "SELECT ID, LOGIN, PASSWORD, ROLE FROM  USERS";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT + " WHERE ID = ?";
    private static final String SQL_SELECT_BY_LOGIN = SQL_SELECT + " WHERE LOGIN = ?";

    @Autowired
    private BasicDataSource dataSource;

    public boolean changePassword(Integer id, String password) throws DAOException {
        boolean result = false;
        if (password != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD);
            ) {
                statement.setString(1, password);
                statement.setInt(2, id);
                if (statement.executeUpdate() == 1)
                    result = true;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return result;
    }

    public boolean changeRole(Integer id, String role) throws DAOException {
        boolean result = false;
        if (role != null) {
            try (
                    Connection connection = dataSource.getConnection();
                    PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ROLE);
            ) {
                statement.setString(1, role);
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
    public boolean update(User entity) throws DAOException {
        throw new UnsupportedOperationException();
    }

    public boolean add(User entity) throws DAOException {
        boolean result = false;
        if (entity != null) {
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement statement = connection.prepareStatement(SQL_ADD)
            ) {
                statement.setString(1, entity.getLogin());
                statement.setString(2, entity.getPassword());
                statement.setString(3, entity.getRole());
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

    public User findById(Integer id) throws DAOException{
        User user = null;
        if(id != null){
            try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID))
            {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt("ID"));
                    user.setLogin(resultSet.getString("LOGIN"));
                    user.setPassword(resultSet.getString("PASSWORD"));
                    user.setRole(resultSet.getString("ROLE"));
                }
            }
            catch (SQLException e){
                throw new DAOException(e);
            }
        }
        return user;
    }

    public List<User> findAll() throws DAOException{
        List<User> users = new ArrayList<>();
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT))
        {
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setLogin(resultSet.getString("LOGIN"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setRole(resultSet.getString("ROLE"));
                users.add(user);
            }
        }
        catch (SQLException e){
            throw new DAOException(e);
        }
        return users;
    }

    public User findByLogin(String login) throws DAOException{
        User user = null;
        if(login != null){
            try(Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_LOGIN))
            {
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    user = new User();
                    user.setId(resultSet.getInt("ID"));
                    user.setLogin(resultSet.getString("LOGIN"));
                    user.setPassword(resultSet.getString("PASSWORD"));
                    user.setRole(resultSet.getString("ROLE"));
                }
            }
            catch (SQLException e){
                throw new DAOException(e);
            }
        }
        return user;
    }

    public void setDataSource(BasicDataSource dataSource){
        this.dataSource = dataSource;
    }
}
