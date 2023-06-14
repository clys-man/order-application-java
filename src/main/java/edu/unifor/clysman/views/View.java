package edu.unifor.clysman.views;

import javax.swing.*;

public abstract class View extends JFrame {
    public final static int WIDTH = 800;
    public final static int HEIGHT = 400;

    public View() {
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.open();
    }

    public View(String title) {
        this();
        this.setTitle(title);
    }

    abstract public void initialize();

    public void close() {
        this.dispose();
    }

    public void open() {
        this.setVisible(true);
    }

    public void setView(View view) {
        this.close();
        view.open();
        view.initialize();
    }

    public void redirect(View view) {
        this.setView(view);
    }
}
