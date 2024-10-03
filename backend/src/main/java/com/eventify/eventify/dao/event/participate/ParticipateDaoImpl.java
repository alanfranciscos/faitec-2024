package com.eventify.eventify.dao.event.participate;

import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import com.eventify.eventify.port.dao.participate.ParticipateDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    public Participate readById(int id) {
        return null;
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
}
