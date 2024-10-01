package com.eventify.eventify.dao.event;

import com.eventify.eventify.models.event.*;
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
                resultSet.getString("local_name"),
                resultSet.getString("cep_address"),
                resultSet.getString("state_address"),
                resultSet.getString("city_address"),
                resultSet.getString("neighborhood_address"),
                resultSet.getString("number_address"),
                resultSet.getString("street_address"),
                resultSet.getString("complement_address"),
                resultSet.getDouble("latitude"),
                resultSet.getDouble("longitude"),
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

    @Override
    public int totalFromUser(int accountId) {
        String sql = "SELECT count(1) total";
        sql += " FROM meetup M ";
        sql += " INNER JOIN participate P on P.meetup_id = M.id ";
        sql += " INNER JOIN  meetup_image MI on MI.meetup_id = M.id ";
        sql += " WHERE account_id = ? ";
        sql += " AND MI.is_profile = true ";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final int total = resultSet.getInt("total");
                    return total;
                }
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasAccessToEvent(int eventId, int accountId) {
        String sql = "SELECT count(1) total";
        sql += " FROM meetup M ";
        sql += " INNER JOIN participate P on P.meetup_id = M.id ";
        sql += " WHERE account_id = ? ";
        sql += " AND M.id = ? ";
        sql += " AND P.active = true";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setInt(2, eventId);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final int total = resultSet.getInt("total");
                    return total > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EventOrganization getOrganizationById(int id) {
        String sql = "select  m.id, m.created_at , m.stage, R1.number_of_participants, a.username created_by from meetup m ";
        sql += " inner join participate p on m.id = p.meetup_id ";
        sql += " inner join management m2 on m2.participate_id = p.id ";
        sql += " inner join account a on a.id = p.account_id ";
        sql += " inner join ";
        sql += " ( ";
        sql += "        select count(*) number_of_participants from meetup m ";
        sql += "        inner join participate p on p.meetup_id = m.id ";
        sql += "        where m.id = ? ";
        sql += " ) R1 on 1 = 1 ";
        sql += " where m2.type_action = 'create' ";
        sql += " and m.id = ?";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    try {
                        final EventOrganization eventOrganization = new EventOrganization(
                                resultSet.getInt("id"),
                                resultSet.getTimestamp("created_at").toInstant().atZone(ZoneId.systemDefault()),
                                resultSet.getString("created_by"),
                                resultSet.getInt("number_of_participants"),
                                EventStageEnum.fromString(resultSet.getString("stage"))
                        );
                        return eventOrganization;
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public double totalExpenses(int eventId) {
        String sql = "select SUM(E.cost) current_expanse ";
        sql += " from meetup M ";
        sql += " inner join expanses E ON E.meetup_id = M.id ";
        sql += " where E.meetup_id = ? ";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, eventId);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final double total = resultSet.getDouble("current_expanse");
                    return total;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    @Override
    public EventDate getDateById(int id) {
        String sql = "select date_start, date_end from meetup where id = ?";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final EventDate eventDate = new EventDate(
                            resultSet.getTimestamp("date_start").toInstant().atZone(ZoneId.systemDefault()),
                            resultSet.getTimestamp("date_end").toInstant().atZone(ZoneId.systemDefault())
                    );
                    return eventDate;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Event readById(int id) {
        String sql = "SELECT * FROM meetup WHERE id = ?";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    final Event event = mapResultSetToEvent(resultSet);
                    return event;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
