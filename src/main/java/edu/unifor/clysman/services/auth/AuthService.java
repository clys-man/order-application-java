package edu.unifor.clysman.services.auth;

import edu.unifor.clysman.database.factory.MySQLFactory;
import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.respository.user.UserRepository;

public class AuthService {
    private final UserRepository userRepository;

    public AuthService() {
        this.userRepository = new UserRepository(MySQLFactory.getConnector());
    }

    public UserModel login(String email, String password) {
        return this.userRepository.findOne(u -> u.getEmail().equals(email) && u.getPassword().equals(password));
    }

    public static boolean logout() {
        return true;
    }

}
