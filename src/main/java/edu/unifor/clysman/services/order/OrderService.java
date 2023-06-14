package edu.unifor.clysman.services.order;

import edu.unifor.clysman.database.factory.MySQLFactory;
import edu.unifor.clysman.helpers.CSV;
import edu.unifor.clysman.models.Model;
import edu.unifor.clysman.models.order.OrderModel;
import edu.unifor.clysman.models.order.OrderStatus;
import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.respository.order.OrderRepository;

import java.util.Date;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        this.orderRepository = new OrderRepository(MySQLFactory.getConnector());
    }

    public void create(String description, UserModel user) {
        Model model = new OrderModel(description, user.getId());
        this.orderRepository.save(model);
    }

    public List<OrderModel> findAll() {
        return this.orderRepository.findAll();
    }

    public List<OrderModel> findAllByUser(UserModel user) {
        return this.orderRepository.findAllByUserId(user.getId());
    }

    public void updateStatus(int id, OrderStatus status) {
        this.orderRepository.updateStatus(id, status);
    }

    public void delete(int id) {
        this.orderRepository.delete(id);
    }

    public void generateReport(UserModel user) {
        List<OrderModel> orders = this.orderRepository.findAll();

        Date date = new Date();
        String fileName = "report-" + user.getId() + "-" + date.getTime() + ".csv";
        CSV csv = new CSV(fileName);
        StringBuilder builder = new StringBuilder();

        builder.append("id,description,user_id,status,created_at\n");
        for (OrderModel order : orders) {
            builder.append(order.getId()).append(",");
            builder.append(order.getDescription()).append(",");
            builder.append(order.getUserId()).append(",");
            builder.append(order.getStatus()).append(",");
            builder.append(order.getCreatedAt()).append("\n");
        }

        csv.write(builder.toString());
    }
}
