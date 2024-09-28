package com.eventify.eventify.dao.event;

import com.eventify.eventify.models.event.Event;
import com.eventify.eventify.models.event.EventHeader;
import com.eventify.eventify.models.event.EventStageEnum;
import com.eventify.eventify.port.dao.event.EventDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EventDaoImpl implements EventDao {

    private final Connection connection;

    public EventDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private Event mapResultSetToEvent(ResultSet resultSet) throws SQLException {
        final Event event = new Event(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("information"),
                resultSet.getTimestamp("created_at").toInstant().atZone(ZoneId.systemDefault()),
                resultSet.getString("cep_address"),
                resultSet.getString("state_address"),
                resultSet.getString("city_address"),
                resultSet.getString("neighborhood_address"),
                resultSet.getString("number_address"),
                resultSet.getString("street_address"),
                resultSet.getTimestamp("date_start").toInstant().atZone(ZoneId.systemDefault()),
                resultSet.getTimestamp("date_end").toInstant().atZone(ZoneId.systemDefault()),
                EventStageEnum.fromString(resultSet.getString("stage")),
                resultSet.getString("pix_key")
        );

        return event;
    }


    private EventHeader mapResultSetToEventHeader(ResultSet resultSet) throws SQLException {
        final EventHeader eventHeader = new EventHeader(
                resultSet.getInt("id"),
                resultSet.getString("title"),
                resultSet.getString("information"),
                resultSet.getTimestamp("date_start").toInstant().atZone(ZoneId.systemDefault()),
                resultSet.getTimestamp("date_end").toInstant().atZone(ZoneId.systemDefault()),
                EventStageEnum.fromString(resultSet.getString("stage")),
                resultSet.getBytes("image_data")
        );

        return eventHeader;
    }

    @Override
    public List<EventHeader> listPaginatedHeaderFromUser(int limit, int offset, int accountId) {
        String sql = "SELECT M.id, M.title, M.information, M.date_start, M.date_end, M.stage, MI.image_data ";
        sql += " FROM meetup M ";
        sql += " INNER JOIN participate P on P.meetup_id = M.id ";
        sql += " INNER JOIN  meetup_image MI on MI.meetup_id = M.id ";
        sql += " WHERE account_id = ? ";
        sql += " AND MI.is_profile = true ";
        sql += " LIMIT ? OFFSET ? ;";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, limit);
            preparedStatement.setInt(3, offset);

            try (var resultSet = preparedStatement.executeQuery()) {
                final List<EventHeader> events = new ArrayList<>();
                while (resultSet.next()) {
                    final EventHeader event = mapResultSetToEventHeader(resultSet);
                    events.add(event);
                }

                return events;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
