package com.eventify.eventify.dao.event.participate;

import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventStageEnum;
import com.eventify.eventify.models.event.management.Management;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import com.eventify.eventify.port.dao.participate.ParticipateDao;

import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipateDaoImpl implements ParticipateDao {
    private static final Logger logger = Logger
            .getLogger(ParticipateDaoImpl.class.getName());

    private final Connection connection;

    public ParticipateDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private Participate mapResultSetToParticipate(ResultSet resultSet) throws SQLException {
        ZonedDateTime aceptedAt = resultSet.getTimestamp("acepted_at") == null
                ? null
                : resultSet.getTimestamp("acepted_at")
                .toInstant().atZone(ZoneId.systemDefault());

        return new Participate(
                resultSet.getInt("id"),
                resultSet.getInt("account_id"),
                resultSet.getInt("meetup_id"),
                RoleParticipateEnum.fromString(resultSet.getString("role_participant")),
                resultSet.getBoolean("active"),
                resultSet.getTimestamp("sended_at").toInstant().atZone(ZoneId.systemDefault()),
                aceptedAt
        );
    }

    @Override
    public Participate readById(int id){
        final String sql = "SELECT * FROM participate WHERE id = ? ;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Participate participate = new Participate();
                participate.setId(resultSet.getInt("id"));
                participate.setAccountId(resultSet.getInt("account_id"));
                participate.setEventId(resultSet.getInt("meetup_id"));
                participate.setRoleParticipate( RoleParticipateEnum.fromString(resultSet.getString("role_participant")));
                participate.setActive(resultSet.getBoolean("active"));

                OffsetDateTime offsetSendedAt = resultSet.getObject("sended_at", OffsetDateTime.class);
                OffsetDateTime offsetAceptedAt = resultSet.getObject("acepted_at", OffsetDateTime.class);
                System.out.println("DATA ENVIADA: " + offsetSendedAt);
                System.out.println("DATA ACEITADA: " + offsetAceptedAt);

                if (offsetSendedAt != null) {
                    // Converter diretamente para ZonedDateTime
                    ZonedDateTime zonedSendedAt = offsetSendedAt.toZonedDateTime();
                    participate.setSendedAt(zonedSendedAt);
                } else {
                    participate.setSendedAt(null);
                }

                if (offsetAceptedAt != null) {
                    // Converter diretamente para ZonedDateTime
                    ZonedDateTime zonedAcceptedAt = offsetAceptedAt.toZonedDateTime();
                    participate.setAcceptedAt(zonedAcceptedAt);
                } else {
                    participate.setAcceptedAt(null);
                }

                logger.log(Level.INFO, "Entidade com id " + id + " encontrada.");
                return participate;
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
    public List<Participate> readAll() {
        final List<Participate> participates = new ArrayList<>();
        final String sql = "SELECT * FROM participate;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Participate participate = new Participate();

                participate.setId(resultSet.getInt("id"));
                participate.setAccountId(resultSet.getInt("account_id"));
                participate.setEventId(resultSet.getInt("meetup_id"));
                participate.setRoleParticipate(RoleParticipateEnum.valueOf(resultSet.getString("role_participant")));

                participate.setActive(resultSet.getBoolean("active"));
                participate.setSendedAt(ZonedDateTime.parse(resultSet.getString("sended_at")));
                participate.setAcceptedAt(ZonedDateTime.parse(resultSet.getString("accepted_at")));

                participates.add(participate);
            }
            return participates;
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
    public List<Participate> listByEventId(int eventId) {
        String sql = "SELECT * FROM participate WHERE meetup_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, eventId);
            ResultSet resultSet = statement.executeQuery();

            List<Participate> participates = new ArrayList<>();
            while (resultSet.next()) {
                Participate participate = this.mapResultSetToParticipate(resultSet);
                participates.add(participate);
            }

            return participates;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Participate> listPaginatedFromUserAndNotAceptedAndNotIsOwner(int accountId, int limit, int offset) {
        String sql = "SELECT * FROM participate ";
        sql += " WHERE account_id = ? AND acepted_at IS NULL  AND role_participant NOT IN ('organizer') ";
        sql += " ORDER BY sended_at DESC";
        sql += " LIMIT ? OFFSET ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();

            List<Participate> participates = new ArrayList<>();
            while (resultSet.next()) {
                Participate participate = this.mapResultSetToParticipate(resultSet);
                participates.add(participate);
            }

            return participates;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int save(Participate entity) {
        String sql = "INSERT INTO participate(account_id, meetup_id, role_participant, active, sended_at, acepted_at)";
        sql += " VALUES(?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;
        ZonedDateTime currentDateTime = ZonedDateTime.now();

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, entity.getAccountId());
            preparedStatement.setInt(2, entity.getEventId());
            preparedStatement.setString(3, entity.getRoleParticipate().toString().toLowerCase());
            preparedStatement.setBoolean(4, entity.isActive());
            preparedStatement.setTimestamp(5, Timestamp.from(currentDateTime.toInstant()));
            preparedStatement.setTimestamp(6, Timestamp.from(currentDateTime.toInstant()));

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

        final String sql = "DELETE FROM participate WHERE id = ? ;";

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
    public void updateInformation(int id, Participate entity) {

        String sql = "UPDATE participate SET role_participant = ?, active = ?";
        sql += " WHERE id = ? ";
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getRoleParticipate().toString());
            preparedStatement.setBoolean(2, entity.isActive());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public List<Participate> readByAccountId(int accountId){
        String sql = "SELECT * FROM participate ";
        sql += "WHERE account_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();

            List<Participate> participates = new ArrayList<>();
            while (resultSet.next()) {

                final Participate participate = new Participate();

                participate.setId(resultSet.getInt("id"));
                participate.setAccountId(resultSet.getInt("account_id"));
                participate.setEventId(resultSet.getInt("meetup_id"));
                participate.setRoleParticipate(RoleParticipateEnum.fromString(resultSet.getString("role_participant")));

                participate.setActive(resultSet.getBoolean("active"));

                OffsetDateTime offsetSendedAt = resultSet.getObject("sended_at", OffsetDateTime.class);
                OffsetDateTime offsetAceptedAt = resultSet.getObject("acepted_at", OffsetDateTime.class);
                if (offsetSendedAt != null) {
                    ZonedDateTime zonedSendedAt = offsetSendedAt.toZonedDateTime();
                    participate.setSendedAt(zonedSendedAt);
                } else {
                    participate.setSendedAt(null);
                }
                if (offsetAceptedAt != null) {
                    ZonedDateTime zonedAcceptedAt = offsetAceptedAt.toZonedDateTime();
                    participate.setAcceptedAt(zonedAcceptedAt);
                } else {
                    participate.setAcceptedAt(null);
                }
                participates.add(participate);
            }

            return participates;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteByEventId(int eventId) {
        logger.log(Level.INFO, "Preparando para remover a participante com id " + eventId);

        final String sql = "DELETE FROM participate WHERE meetup_id = ? ;";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, eventId);
            preparedStatement.execute();
            preparedStatement.close();

            logger.log(Level.INFO, "Participante removida com sucesso");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
