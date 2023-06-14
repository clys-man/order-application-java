package edu.unifor.clysman.views.order;

import edu.unifor.clysman.helpers.CSV;
import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.views.View;
import edu.unifor.clysman.views.layout.CustomTableModel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class AnalyticsView extends View {
    private final UserModel user;
    public AnalyticsView(UserModel user) {
        super("Analytics");
        this.user = user;
    }

    @Override
    public void initialize() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton button = new JButton("Voltar");
        button.addActionListener(e -> this.redirect(new ListOrderView(this.user)));
        panel.add(button);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setDialogTitle("Selecione o arquivo CSV");
        fileChooser.setApproveButtonText("Selecionar");
        fileChooser.setApproveButtonToolTipText("Selecionar arquivo");
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(csvFilter());

        fileChooser.addActionListener(e -> {
            if (e.getActionCommand().equals("ApproveSelection")) {
                CSV csv = new CSV(fileChooser.getSelectedFile().getAbsolutePath());
                CustomTableModel tableModel = new CustomTableModel(csv.getHeaders(), csv.getRows());
                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                this.add(scrollPane);
                fileChooser.setVisible(false);
                this.revalidate();
            }
        });

        panel.add(fileChooser);
        this.add(panel);
    }

    private FileFilter csvFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(java.io.File f) {
                return f.getName().toLowerCase().endsWith(".csv") || f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Arquivos CSV";
            }
        };
    }
}
