package com.nhom22.findhostel.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAccountDAO {
    /*private static final String DATABASE_URL = "jdbc:sqlite:/data/data/com.nhom22.findhostel/databases/findhostel.db";
    private Connection connection;

    public UserAccountDAO() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DATABASE_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUserAccount(User_Account user) {
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO user_accounts (username, password, email) VALUES (?, ?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(stmt);
        }

        return false;
    }

    public boolean updateUserAccount(User_Account user) {
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE user_accounts SET password = ?, email = ? WHERE username = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, user.getPassword());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getUsername());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(stmt);
        }

        return false;
    }

    public boolean deleteUserAccount(String username) {
        PreparedStatement stmt = null;

        try {
            String query = "DELETE FROM user_accounts WHERE username = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, username);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(stmt);
        }

        return false;
    }

    private void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
*/
    // Các phương thức khác liên quan đến truy vấn dữ liệu, nếu cần
}
