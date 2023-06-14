package edu.unifor.clysman.views.login;

import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.respository.user.UserRepository;
import edu.unifor.clysman.services.auth.AuthService;
import edu.unifor.clysman.views.View;
import edu.unifor.clysman.views.home.HomeView;

import javax.swing.*;

public class LoginView extends View {
    private final AuthService authService;

    public LoginView() {
        super("Login");
        this.authService = new AuthService();
    }

    @Override
    public void initialize() {
        JPanel panel = new JPanel();
        JLabel emailLabel = new JLabel("Email");
        JTextField emailTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordTextField = new JPasswordField(20);
        JButton button = new JButton("Login");

        panel.add(emailLabel);
        panel.add(emailTextField);
        panel.add(passwordLabel);
        panel.add(passwordTextField);
        panel.add(button);

        button.addActionListener(e -> {
            String email = emailTextField.getText();
            String password = new String(passwordTextField.getPassword());
            UserModel user = this.authService.login(email, password);

            if (user != null) {
                this.redirect(new HomeView(user));
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials");
            }
        });
        this.add(panel);
    }
}
