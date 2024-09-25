package com.eventify.eventify.dao.account;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.port.dao.account.AccountDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao{
    private final Connection connection;

    public AccountDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account readByEmail(String email) {
       final String sql = "SELECT * FROM account WHERE email = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Account account = new Account();
                account.setId(resultSet.getInt("id"));
                account.setUsername(resultSet.getString("username"));
                account.setEmail(resultSet.getString("email"));
                try {
                    account.setImageData(resultSet.getBytes("imageData"));
                } catch (Exception e) {
                    account.setImageData(null);
                }
                try {
                    account.setVerified(resultSet.getBoolean("isVerified"));
                } catch (Exception e) {
                    account.setVerified(false);
                }

                return account;
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public int save(Account entity) {
        String sql = "INSERT INTO account(username, email, imageData, isVerified) ";
        sql += " VALUES(?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setBytes(3, entity.getImageData());
            preparedStatement.setBoolean(4, entity.isVerified());

            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                final int id = resultSet.getInt(1);
                entity.setId(id);
            }

            connection.commit();

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }

        return entity.getId();
    }

    @Override
    public void deleteById(int id) {
        final String sql = "DELETE FROM account WHERE id = ?;";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
