package edu.unifor.clysman.respository.user;

import edu.unifor.clysman.database.connector.Connector;
import edu.unifor.clysman.models.Model;
import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.models.user.UserType;
import edu.unifor.clysman.respository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UserRepository extends Repository<UserModel> {
    public UserRepository(Connector connector) {
        super(connector);
    }

    @Override
    public UserModel findOne(Predicate<UserModel> predicate) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            UserModel user = null;

            while (resultSet.next()) {
                user = new UserModel(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserType.fromString(resultSet.getString("type"))
                );

                if (predicate.test(user)) {
                    break;
                }
            }

            connection.close();
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserModel findById(int id) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new UserModel(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserType.fromString(resultSet.getString("type"))
                );
            }

            connection.close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserModel> findAll() {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            List<UserModel> users = new ArrayList<>();

            while (resultSet.next()) {
                UserModel user = new UserModel(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        UserType.fromString(resultSet.getString("type"))
                );
                users.add(user);
            }

            connection.close();
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserModel save(Model model) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email, password, type) VALUES (?, ?, ?, ?)");
            statement.setString(1, ((UserModel) model).getName());
            statement.setString(2, ((UserModel) model).getEmail());
            statement.setString(3, ((UserModel) model).getPassword());
            statement.setString(4, ((UserModel) model).getType().toString());
            statement.executeUpdate();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                model.setId(resultSet.getInt("id"));
            }

            connection.close();
            return (UserModel) model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserModel update(int id, Model model) {
        try {
            UserModel user = this.findById(id);
            if (user == null) {
                return null;
            }

            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?");
            if (((UserModel) model).getName() == null) {
                ((UserModel) model).setName(user.getName());
            }

            if (((UserModel) model).getEmail() == null) {
                ((UserModel) model).setEmail(user.getEmail());
            }

            if (((UserModel) model).getPassword() == null) {
                ((UserModel) model).setPassword(user.getPassword());
            }

            statement.setString(1, ((UserModel) model).getName());
            statement.setString(2, ((UserModel) model).getEmail());
            statement.setString(3, ((UserModel) model).getPassword());
            statement.setInt(4, id);
            statement.executeUpdate();
            connection.close();

            return (UserModel) model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            if (this.findById(id) == null) {
                return;
            }

            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
