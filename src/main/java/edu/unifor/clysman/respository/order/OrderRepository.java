package edu.unifor.clysman.respository.order;

import edu.unifor.clysman.database.connector.Connector;
import edu.unifor.clysman.models.Model;
import edu.unifor.clysman.models.order.OrderModel;
import edu.unifor.clysman.models.order.OrderStatus;
import edu.unifor.clysman.respository.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class OrderRepository extends Repository<OrderModel> {
    public OrderRepository(Connector connector) {
        super(connector);
    }

    @Override
    public OrderModel findById(int id) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new OrderModel(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getInt("user_id"),
                        OrderStatus.fromString(resultSet.getString("status")),
                        resultSet.getTimestamp("created_at")
                );
            }

            connection.close();

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderModel findOne(Predicate<OrderModel> predicate) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderModel order = new OrderModel(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getInt("user_id"),
                        OrderStatus.fromString(resultSet.getString("status")),
                        resultSet.getTimestamp("created_at")
                );

                if (predicate.test(order)) {
                    return order;
                }
            }

            connection.close();

            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderModel> findAll() {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders ORDER BY created_at DESC");
            ResultSet resultSet = statement.executeQuery();
            List<OrderModel> orders = new ArrayList<>();

            while (resultSet.next()) {
                OrderModel user = new OrderModel(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getInt("user_id"),
                        OrderStatus.fromString(resultSet.getString("status")),
                        resultSet.getTimestamp("created_at")
                );
                orders.add(user);
            }

            connection.close();

            return orders;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<OrderModel> findAllByUserId(int userId) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<OrderModel> orders = new ArrayList<>();

            while (resultSet.next()) {
                OrderModel user = new OrderModel(
                        resultSet.getInt("id"),
                        resultSet.getString("description"),
                        resultSet.getInt("user_id"),
                        OrderStatus.fromString(resultSet.getString("status")),
                        resultSet.getTimestamp("created_at")
                );
                orders.add(user);
            }

            connection.close();

            return orders;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderModel save(Model model) {
        try {
            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO orders (description, user_id, status) VALUES (?, ?, ?)");
            statement.setString(1, ((OrderModel) model).getDescription());
            statement.setInt(2, ((OrderModel) model).getUserId());
            statement.setString(3, ((OrderModel) model).getStatus().toString());
            statement.executeUpdate();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");
            if (resultSet.next()) {
                model.setId(resultSet.getInt("id"));
            }

            connection.close();

            return (OrderModel) model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderModel update(int id, Model model) {
        try {
            OrderModel order = this.findById(id);
            if (order == null) {
                return null;
            }

            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE orders SET name = ?, status = ? WHERE id = ?");
            if (((OrderModel) model).getDescription() == null) {
                ((OrderModel) model).setDescription(order.getDescription());
            }

            if (((OrderModel) model).getStatus() == null) {
                ((OrderModel) model).setStatus(order.getStatus());
            }

            statement.setString(1, ((OrderModel) model).getDescription());
            statement.setString(2, ((OrderModel) model).getStatus().toString());
            statement.setInt(3, id);
            statement.executeUpdate();

            connection.close();

            return (OrderModel) model;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(int id, OrderStatus status) {
        try {
            OrderModel order = this.findById(id);
            if (order == null) {
                return;
            }

            Connection connection = this.connector.getConnection();
            PreparedStatement statement = connection.prepareStatement("UPDATE orders SET status = ? WHERE id = ?");
            statement.setString(1, status.toString());
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM orders WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
