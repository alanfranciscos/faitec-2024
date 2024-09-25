package com.eventify.eventify.dao.account.password;

import com.eventify.eventify.models.account.password.AccountPasswordHistory;
import com.eventify.eventify.port.dao.account.password.AccountPasswordHistoryDao;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class AccountPasswordHistoryDaoImpl implements AccountPasswordHistoryDao {
    private final Connection connection;

    public AccountPasswordHistoryDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private AccountPasswordHistory mapResultSetToAccountPasswordHistory(ResultSet resultSet) throws SQLException {
        final AccountPasswordHistory accountPasswordHistory = new AccountPasswordHistory();
        accountPasswordHistory.setId(resultSet.getInt("id"));
        accountPasswordHistory.setAccountId(resultSet.getInt("account_id"));
        accountPasswordHistory.setPasswordFromDao(resultSet.getString("user_password"));

        Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");
        ZonedDateTime createdAtZonedDateTime = createdAtTimestamp.toInstant()
                .atZone(ZoneId.systemDefault());
        accountPasswordHistory.setCreatedAt(createdAtZonedDateTime);

        accountPasswordHistory.setActive(resultSet.getBoolean("active"));
        accountPasswordHistory.setStaging(resultSet.getBoolean("staging"));
        accountPasswordHistory.setVerificationCode(resultSet.getString("verification_code"));

        Timestamp validUntilTimestamp = resultSet.getTimestamp("created_at");
        ZonedDateTime validUntilZonedDateTime = validUntilTimestamp.toInstant()
                .atZone(ZoneId.systemDefault());
        accountPasswordHistory.setCodeValidUntil(validUntilZonedDateTime);
        return accountPasswordHistory;
    }

    @Override
    public int save(AccountPasswordHistory entity) {
       String sql = "INSERT INTO account_password(account_id, user_password, created_at, active, staging, verification_code, code_valid_until) ";
        sql += " VALUES(?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, entity.getAccountId());
            preparedStatement.setString(2, entity.getPassword());
            preparedStatement.setTime(3, Time.valueOf(entity.getCreatedAt().toString()));
            preparedStatement.setBoolean(4, entity.isActive());
            preparedStatement.setBoolean(5, entity.isStaging());
            preparedStatement.setString(6, entity.getVerificationCode());
            preparedStatement.setTime(7, Time.valueOf(entity.getCodeValidUntil().toString()))
      ;

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
        final String sql = "DELETE FROM account_password WHERE id = ?;";
    
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
    public Optional<AccountPasswordHistory> findByAccountIdAndActive(Integer accountId, boolean active) {
        final String sql = "SELECT * FROM account_password WHERE account_id = ? AND active = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setBoolean(2, active);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final AccountPasswordHistory accountPasswordHistory = mapResultSetToAccountPasswordHistory(resultSet);
                return Optional.of(accountPasswordHistory);
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
    public Optional<AccountPasswordHistory> findByAccountIdAndStaging(Integer accountId, boolean staging) {
        final String sql = "SELECT * FROM account_password WHERE account_id = ? AND staging = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, accountId);
            preparedStatement.setBoolean(2, staging);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final AccountPasswordHistory accountPasswordHistory = mapResultSetToAccountPasswordHistory(resultSet);
                return Optional.of(accountPasswordHistory);
            }

            return Optional.empty();
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
    public List<AccountPasswordHistory> findByAccountId(Integer accountId) {
        final String sql = "SELECT * FROM account_password WHERE account_id = ?;";

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, accountId);

            resultSet = preparedStatement.executeQuery();
            final List<AccountPasswordHistory> accountPasswordHistories = List.of();
            while (resultSet.next()) {
                final AccountPasswordHistory accountPasswordHistory = mapResultSetToAccountPasswordHistory(resultSet);
                accountPasswordHistories.add(accountPasswordHistory);
            }

            return accountPasswordHistories;
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

}
