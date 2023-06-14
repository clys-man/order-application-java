package edu.unifor.clysman.views.order;

import edu.unifor.clysman.models.order.OrderModel;
import edu.unifor.clysman.models.order.OrderStatus;
import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.services.order.OrderService;
import edu.unifor.clysman.views.View;
import edu.unifor.clysman.views.layout.CustomTableModel;
import edu.unifor.clysman.views.login.LoginView;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ListOrderView extends View {
    private final UserModel user;
    private final OrderService orderService;

    public ListOrderView(UserModel user) {
        this.user = user;
        this.orderService = new OrderService();
    }

    @Override
    public void initialize() {
        String title = this.user.isAdmin() ? "Admin - Pedidos" : "Cliente - Meus Pedidos";
        this.setTitle(title);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new GridLayout(1,5,5,0));
        headerPanel.setMaximumSize(new Dimension(1000, 50));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton refreshButton = new JButton("Atualizar");
        headerPanel.add(refreshButton);

        JButton sendButton = new JButton("Enviar Pedido");
        JButton inProgressButton = new JButton("Em Progresso");
        JButton cancelButton = new JButton("Cancelar Pedido");
        if (this.user.isAdmin()) {
            headerPanel.add(sendButton);
            headerPanel.add(inProgressButton);
            headerPanel.add(cancelButton);
        }

        JButton createButton = new JButton("Criar Pedido");
        if (!this.user.isAdmin()) {
            headerPanel.add(createButton);
        }

        JButton deleteButton = new JButton("Deletar Pedido");
        headerPanel.add(deleteButton);
        JButton reportButton = new JButton("Gerar CSV");
        headerPanel.add(reportButton);
        JButton analyticsButton = new JButton("Analisar CSV");
        headerPanel.add(analyticsButton);

        JButton logoutButton = new JButton("Sair");
        headerPanel.add(logoutButton);

        panel.add(headerPanel);
        this.add(panel);

        String[] columnNames = {"ID", "Descrição", "Usuário", "Status", "Data de Criação"};

        List<OrderModel> orders;
        if (this.user.isAdmin()) {
            orders = this.orderService.findAll();
        } else {
            orders = this.orderService.findAllByUser(this.user);
        }
        Object[][] data = orders.stream().map(order -> new Object[]{
                order.getId(),
                order.getDescription(),
                order.getUserId(),
                order.getStatus().toString(),
                order.getCreatedAt()
        }).toArray(Object[][]::new);

        CustomTableModel model = new CustomTableModel(columnNames, data);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        refreshButton.addActionListener(e -> this.redirect(new ListOrderView(this.user)));
        sendButton.addActionListener(e -> updateOrderStatusRow(OrderStatus.SENT, table));
        createButton.addActionListener(e -> this.redirect(new CreateOrderView(this.user)));
        inProgressButton.addActionListener(e -> updateOrderStatusRow(OrderStatus.IN_PROGRESS, table));
        cancelButton.addActionListener(e -> updateOrderStatusRow(OrderStatus.CANCELED, table));
        deleteButton.addActionListener(e -> removeOrderRow(table));
        reportButton.addActionListener(e -> generateCSVReport());
        logoutButton.addActionListener(e -> this.redirect(new LoginView()));
        analyticsButton.addActionListener(e -> this.redirect(new AnalyticsView(this.user)));

        panel.add(scrollPane);
        this.add(panel);
        this.setVisible(true);
    }

    private void updateOrderStatusRow(OrderStatus status, JTable table) {
        Object id = this.getSelectedData(table);
        if (id == null) {
            return;
        }

        this.orderService.updateStatus((int) id, status);
        this.updateRow(table, table.getSelectedRow(), new Object[]{id, null, null, status.toString()});
        this.redirect(new ListOrderView(this.user));
    }

    private Object getSelectedData(JTable table) {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecione um pedido");
            return null;
        }

        return table.getValueAt(row, 0);
    }

    private void updateRow(JTable table, int row, Object[] data) {
        for (int i = 0; i < data.length; i++) {
            table.setValueAt(data[i], row, i);
        }
        this.repaint();
    }

    private void removeOrderRow(JTable table) {
        Object id = this.getSelectedData(table);
        if (id == null) {
            return;
        }

        this.orderService.delete((int) id);
        this.redirect(new ListOrderView(this.user));
    }

    private void generateCSVReport() {
        this.orderService.generateReport(this.user);
        JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso");
    }
}
