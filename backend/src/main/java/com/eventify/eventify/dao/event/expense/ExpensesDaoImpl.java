package com.eventify.eventify.dao.event.expense;

import com.eventify.eventify.dao.event.EventDaoImpl;
import com.eventify.eventify.models.event.expense.Expense;
import com.eventify.eventify.port.dao.expense.ExpenseDao;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpensesDaoImpl implements ExpenseDao {

    private final Connection connection;

    private static final Logger logger = Logger.getLogger(EventDaoImpl.class.getName());

    public ExpensesDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(Expense entity) {
        String sql = "INSERT INTO expanses(meetup_id, cost, created_at, about)";
        sql += " VALUES(?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, entity.getMeetup_id());
            preparedStatement.setDouble(2, entity.getCost());
            preparedStatement.setTimestamp(3, Timestamp.from(entity.getCreated_at().toInstant()));
            preparedStatement.setString(4, entity.getAbout());

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
        logger.log(Level.INFO, "Preparando para remover a entidade com id " + id);

        final String sql = "DELETE FROM expanses WHERE id = ? ;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();

            logger.log(Level.INFO, "Entidade removida com sucesso");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Expense readById(int id) {
        final String sql = "SELECT * FROM expanses WHERE id = ? ;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Expense expense = new Expense();
                expense.setId(resultSet.getInt("id"));
                expense.setMeetup_id(resultSet.getInt("meetup_id"));
                expense.setCost(resultSet.getDouble("cost"));
                expense.setCreated_at(ZonedDateTime.parse(resultSet.getString("created_at")));
                expense.setAbout(resultSet.getString("about"));
                logger.log(Level.INFO, "Entidade com id " + id + " encotnrada.");
                return expense;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List<Expense> readAll() {
        final List<Expense> expenses = new ArrayList<>();
        final String sql = "SELECT * FROM expanses;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Expense expense = new Expense();
                expense.setId(resultSet.getInt("id"));
                expense.setMeetup_id(resultSet.getInt("meetup_id"));
                expense.setCost(resultSet.getDouble("cost"));
                expense.setCreated_at(ZonedDateTime.parse(resultSet.getString("created_at")));
                expense.setAbout(resultSet.getString("about"));
                expenses.add(expense);
            }
            return expenses;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void updateInformation(int id, Expense entity) {
        String sql = "UPDATE expanses SET cost = ?, about = ?";
        sql += " WHERE id = ? ";
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, entity.getCost());
            preparedStatement.setString(2, entity.getAbout());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
