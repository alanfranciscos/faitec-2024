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

public class ParticipateDaoImpl implements ParticipateDao {
    private final Connection connection;

    public ParticipateDaoImpl(Connection connection) {
        this.connection = connection;
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
                ZonedDateTime aceptedAt = null;
                if (resultSet.getTimestamp("acepted_at") != null) {
                    aceptedAt = resultSet.getTimestamp("acepted_at").toInstant().atZone(ZoneId.systemDefault());
                }
                Participate participate = new Participate(
                        resultSet.getInt("id"),
                        resultSet.getInt("account_id"),
                        resultSet.getInt("meetup_id"),
                        RoleParticipateEnum.fromString(resultSet.getString("role_participant")),
                        resultSet.getBoolean("active"),
                        resultSet.getTimestamp("sended_at").toInstant().atZone(ZoneId.systemDefault()),
                        aceptedAt
                );
                participates.add(participate);
            }

            return participates;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
