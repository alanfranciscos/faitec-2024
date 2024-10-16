package com.eventify.eventify.dao.event.management;


import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.port.dao.event.management.ManagementDao;

import java.sql.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementDaoImpl implements ManagementDao {
    private static final Logger logger = Logger
            .getLogger(ManagementDaoImpl.class.getName());

    private final Connection connection;

    public ManagementDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int save(Management entity) {

        String sql = "INSERT INTO management(participate_id, managment_at, type_action)";
        sql += " VALUES(?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, entity.getParticipate_id());
            preparedStatement.setTimestamp(2, Timestamp.from(currentDateTime.toInstant()));
//            preparedStatement.setTimestamp(2, Timestamp.from(entity.getManagment_at().toInstant()));
            prgit pueparedStatement.setString(3, entity.getType_action());

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

        final String sql = "DELETE FROM management WHERE id = ? ;";

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
    public Management readById(int id) {
        final String sql = "SELECT * FROM management WHERE id = ? ;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Management management = new Management();
                management.setId(resultSet.getInt("id"));
                management.setParticipate_id(resultSet.getInt("participate_id"));
                management.setManagment_at(ZonedDateTime.parse(resultSet.getString("managment_at")));
                management.setType_action(resultSet.getString("type_action"));
                logger.log(Level.INFO, "Entidade com id " + id + " encontrada.");
                return management;
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
    public List<Management> readAll() {
        final List<Management> managements = new ArrayList<>();
        final String sql = "SELECT * FROM management;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Management management = new Management();
                management.setId(resultSet.getInt("id"));
                management.setParticipate_id(resultSet.getInt("participate_id"));
                management.setManagment_at(ZonedDateTime.parse(resultSet.getString("managment_at")));
                management.setType_action(resultSet.getString("type_action"));
                managements.add(management);
            }
            return managements;
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
    public void updateInformation(int id, Management entity) {

        String sql = "UPDATE management SET type_action = ?";
        sql += " WHERE id = ? ";
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getType_action().toString());
            preparedStatement.setInt(2, entity.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
