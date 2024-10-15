package com.eventify.eventify.dao.account;

import com.eventify.eventify.models.account.Account;
import com.eventify.eventify.port.dao.account.AccountDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private final Connection connection;

    public AccountDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        final Account account = new Account(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("image_data"),
                resultSet.getBoolean("is_verified")
        );
        return account;
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
                final Account account = mapResultSetToAccount(resultSet);
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
        String sql = "INSERT INTO account(username, email, image_data, is_verified) ";
        sql += " VALUES(?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getImageData());
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

    @Override
    public void updateVerificationStatus(int id, boolean isVerified) {
        final String sql = "UPDATE account SET is_verified = ? WHERE id = ?;";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setBoolean(1, isVerified);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();
            connection.commit();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Account readById(int id) {
        final String sql = "SELECT * FROM account WHERE id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Account account = mapResultSetToAccount(resultSet);
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
    public List<Account> readAll() {
        return null;
    }

    public void updateImage(int id, String imagePath) {
        final String sql = "UPDATE account SET image_data = ? WHERE id = ?;";

        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, imagePath);
            preparedStatement.setInt(2, Integer.valueOf(id));
            preparedStatement.execute();

            connection.commit();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Adiciona rastreamento da pilha
            throw new RuntimeException("Erro ao atualizar a imagem: " + e.getMessage());
        }
    }
}
