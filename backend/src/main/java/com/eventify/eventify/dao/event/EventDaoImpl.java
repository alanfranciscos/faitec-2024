package com.eventify.eventify.dao.event;

import com.eventify.eventify.models.event.*;
import com.eventify.eventify.port.dao.event.EventDao;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventDaoImpl implements EventDao {

    private final Connection connection;

    private static final Logger logger = Logger.getLogger(EventDaoImpl.class.getName());

    public EventDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private Event mapResultSetToEvent(ResultSet resultSet) throws SQLException {
        final Event event = new Event(
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
    public List<EventExpanses> getExpansesById(int id) {
        String sql = "select created_at, about, cost from expanses where meetup_id = ?";

        try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (var resultSet = preparedStatement.executeQuery()) {
                final List<EventExpanses> expanses = new ArrayList<>();
                while (resultSet.next()) {
                    final EventExpanses expanse = new EventExpanses(
                            resultSet.getTimestamp("created_at").toInstant().atZone(ZoneId.systemDefault()),
                            resultSet.getString("about"),
                            resultSet.getDouble("cost")
                    );
                    expanses.add(expanse);
                }

                return expanses;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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


    @Override
    public int save(Event entity) {

            String sql = "INSERT INTO meetup(title, information, created_at, local_name";
            sql += ", cep_address, state_address, city_address, neighborhood_address, number_address";
            sql += ", street_address, complement_address, latitude, longitude, date_start, date_end, stage, pix_key)";
            sql += " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement preparedStatement;
            ResultSet resultSet;

            try {
                connection.setAutoCommit(false);

                preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, entity.getTitle());
                preparedStatement.setString(2, entity.getInformation());
                preparedStatement.setTimestamp(3, Timestamp.from(entity.getCreatedAt().toInstant()));
                preparedStatement.setString(4, entity.getLocalName());
                preparedStatement.setString(5, entity.getCepAddress());
                preparedStatement.setString(6, entity.getStateAddress());
                preparedStatement.setString(7, entity.getCityAddress());
                preparedStatement.setString(8, entity.getNeighborhoodAddress());
                preparedStatement.setString(9, entity.getNumberAddress());
                preparedStatement.setString(10, entity.getStreetAddress());
                preparedStatement.setString(11, entity.getComplementAddress());
                preparedStatement.setDouble(12, entity.getLatitude());
                preparedStatement.setDouble(13, entity.getLongitude());
                preparedStatement.setTimestamp(14, Timestamp.from(entity.getDateStart().toInstant()));
                preparedStatement.setTimestamp(15, Timestamp.from(entity.getDateEnd().toInstant()));
                preparedStatement.setString(16, entity.getStage().toString().toLowerCase());
                preparedStatement.setString(17, entity.getPixKey());

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

        final String sql = "DELETE FROM meetup WHERE id = ? ;";

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
    public void updateInformation(int id, Event entity) {
        String sql = "UPDATE meetup SET title = ?, information = ?, localName = ?, cepAddress = ?, stateAddress = ?, ";
        sql += "cityAddress = ?, neighborhoodAddress = ?, numberAddress = ?, streetAddress = ?, complementAddress = ?, latitude = ?, ";
        sql += "longitude = ?, dateStart = ?, dateEnd = ?, stage = ?, pixKey = ?";
        sql += " WHERE id = ? ";
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getTitle());
            preparedStatement.setString(2, entity.getInformation());
            preparedStatement.setString(3, entity.getLocalName());
            preparedStatement.setString(4, entity.getCepAddress());
            preparedStatement.setString(5, entity.getStateAddress());
            preparedStatement.setString(6, entity.getCityAddress());
            preparedStatement.setString(7, entity.getNeighborhoodAddress());
            preparedStatement.setString(8, entity.getNumberAddress());
            preparedStatement.setString(9, entity.getStreetAddress());
            preparedStatement.setString(10, entity.getComplementAddress());
            preparedStatement.setDouble(11, entity.getLatitude());
            preparedStatement.setDouble(12, entity.getLongitude());
            preparedStatement.setTimestamp(13, Timestamp.from(entity.getDateStart().toInstant()));
            preparedStatement.setTimestamp(14, Timestamp.from(entity.getDateEnd().toInstant()));
            preparedStatement.setString(15, entity.getStage().toString());
            preparedStatement.setString(16, entity.getPixKey());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
