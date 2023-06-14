package edu.unifor.clysman.views.home;

import edu.unifor.clysman.models.user.UserModel;
import edu.unifor.clysman.views.View;
import edu.unifor.clysman.views.order.ListOrderView;


public class HomeView extends View {
    private final UserModel user;

    public HomeView(UserModel user) {
        super("Home");
        this.user = user;
    }

    @Override
    public void initialize() {
        this.redirect(new ListOrderView(user));
    }
}
