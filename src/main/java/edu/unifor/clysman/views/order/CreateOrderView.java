package edu.unifor.clysman.views.order;

import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.services.order.OrderService;
import edu.unifor.clysman.views.View;

import javax.swing.*;

public class CreateOrderView extends View {
    private final UserModel user;
    private final OrderService orderService;

    public CreateOrderView(UserModel user) {
        super("Create Order");
        this.user = user;
        this.orderService = new OrderService();

    }

    @Override
    public void initialize() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel emailLabel = new JLabel("Descrição:");
        JTextField emailTextField = new JTextField(20);
        JButton button = new JButton("Criar");
        JButton cancelButton = new JButton("Cancelar");

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(button);
        panel.add(cancelButton);

        button.addActionListener(e -> {
            String description = emailTextField.getText();
            this.orderService.create(description, this.user);
            this.redirect(new ListOrderView(user));
        });

        cancelButton.addActionListener(e -> this.redirect(new ListOrderView(user)));

        this.add(panel);
    }
}
