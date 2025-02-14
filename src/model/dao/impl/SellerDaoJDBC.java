package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private final Connection conn = DB.getConnection();
    private ResultSet rs;
    private PreparedStatement stmt;

    @Override
    public void insert(Seller seller) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO seller "
                    + "(name, email, birthdate, basesalary, departmentID)"
                    + "VALUES ?, ?, ?, ?, ?");
            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, seller.getBirthDate());
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDep().getId());

            int rows = stmt.executeUpdate();

            conn.commit();
            System.out.println("Done! Rows: " + rows);

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbException("Error -> Transaction rolled back. Caused by: " + e.getMessage());
            }
            throw new DbException(e.getMessage());
        } finally {
            DB.closeConnection();
            DB.closeStatement(stmt);
        }
    }

    @Override
    public void update(Seller seller) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("UPDATE seller "
                    + "SET (name, email, birthdate, basesalary, departmentID)"
                    + "VALUES ?, ?, ?, ?, ?"
                    + "WHERE id = ?");

            stmt.setString(1, seller.getName());
            stmt.setString(2, seller.getEmail());
            stmt.setDate(3, seller.getBirthDate());
            stmt.setDouble(4, seller.getBaseSalary());
            stmt.setInt(5, seller.getDep().getId());
            stmt.setInt(6, seller.getId());

            conn.commit();

            int rows = stmt.executeUpdate();

            System.out.println("Done! Rows: " + rows);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbException("Error -> Transaction rolled back. Caused by: " + e.getMessage());
            }
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("DELETE FROM seller "
                    + "WHERE id = ?");

            stmt.setInt(1, id);

            conn.commit();
            int rows = stmt.executeUpdate();
            System.out.println("Done! Rows: " + rows);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new DbException("Error -> Transaction rolled back. Caused by: " + e.getMessage());
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public Seller findById(Integer id) {
        return null;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
