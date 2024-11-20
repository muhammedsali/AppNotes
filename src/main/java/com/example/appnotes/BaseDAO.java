package com.example.appnotes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
    protected Connection connection;

    public BaseDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    protected abstract String getTableName();

    public void add(T entity) {
        // Implemented by subclasses
    }

    public void update(T entity) {
        // Implemented by subclasses
    }

    public void delete(int id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Record deleted: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T findById(int id) {
        // Implemented by subclasses
        return null;
    }

    public List<T> findAll() {
        // Implemented by subclasses
        return null;
    }
}
