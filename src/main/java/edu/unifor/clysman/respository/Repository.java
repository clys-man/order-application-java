package edu.unifor.clysman.respository;

import edu.unifor.clysman.database.connector.Connector;
import edu.unifor.clysman.models.Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

abstract public class Repository<T extends Model>{
    protected final Connector connector;

    public Repository(Connector connector) {
        this.connector = connector;
    }

    public abstract T findById(int id);
    public abstract T findOne(Predicate<T> predicate);
    public abstract List<T> findAll();
    public abstract T save(Model model);
    public abstract T update(int id, Model model);
    public abstract void delete(int id);
}
