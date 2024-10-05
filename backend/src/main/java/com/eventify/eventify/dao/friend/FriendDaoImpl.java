package com.eventify.eventify.dao.friend;

import com.eventify.eventify.dao.event.participate.ParticipateDaoImpl;
import com.eventify.eventify.models.event.participate.Participate;
import com.eventify.eventify.models.event.participate.RoleParticipateEnum;
import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.port.dao.friend.FriendDao;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FriendDaoImpl implements FriendDao {

    private final Connection connection;

    private static final Logger logger = Logger
            .getLogger(ParticipateDaoImpl.class.getName());

    public FriendDaoImpl(Connection connection) {
        this.connection = connection;
    }

    private Friend mapResultSetToFriend(ResultSet resultSet) throws SQLException {
        ZonedDateTime acceptedAt = resultSet.getTimestamp("acepted_at") == null
                ? null
                : resultSet.getTimestamp("acepted_at")
                .toInstant().atZone(ZoneId.systemDefault());

        return new Friend(
                resultSet.getInt("id"),
                resultSet.getInt("account_id"),
                resultSet.getInt("friend_id"),
                resultSet.getTimestamp("sended_at").toInstant().atZone(ZoneId.systemDefault()),
                acceptedAt
        );
    }

    @Override
    public Friend readById(int id) {
        String sql = "SELECT * FROM friends WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapResultSetToFriend(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Friend> readAll() {
        final List<Friend> friends = new ArrayList<>();
        final String sql = "SELECT * FROM friends;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Friend friend = new Friend();

                friend.setId(resultSet.getInt("id"));
                friend.setAccountId(resultSet.getInt("accountId"));
                friend.setFriendId(resultSet.getInt("friendId"));
                friend.setSendedAt(ZonedDateTime.parse(resultSet.getString("sendedAt")));
                friend.setAcceptedAt(ZonedDateTime.parse(resultSet.getString("acceptedAt")));

                friends.add(friend);
            }
            return friends;
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
    public List<Friend> listFriendByAccountId(final int accountId, final int limit, final int offset) {
        String sql = "SELECT * FROM friend WHERE account_id = ? or friend_id = ? LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setInt(2, accountId);
            statement.setInt(3, limit);
            statement.setInt(4, offset);
            ResultSet resultSet = statement.executeQuery();

            List<Friend> friends = new ArrayList<>();
            while (resultSet.next()) {
                friends.add(mapResultSetToFriend(resultSet));
            }

            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Friend> listPaginatedFromUserAndNotAcepted(int accountId, int limit, int offset) {
        String sql = "SELECT * FROM friend WHERE friend_id = ? AND acepted_at IS NULL";
        sql += " ORDER BY sended_at DESC ";
        sql += " LIMIT ? OFFSET ? ;";


        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            ResultSet resultSet = statement.executeQuery();

            List<Friend> friends = new ArrayList<>();
            while (resultSet.next()) {
                friends.add(mapResultSetToFriend(resultSet));
            }

            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int QuantityOfFriendsByAccountIdAndAcepted(int accountId) {
        String sql = "SELECT COUNT(1) FROM friend ";
        sql += " WHERE (account_id = ? or friend_id = ?) AND acepted_at IS NOT NULL";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);
            statement.setInt(2, accountId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int save(Friend entity) {


        String sql = "INSERT INTO friend(account_id, friend_id, sended_at, acepted_at)";
        sql += " VALUES(?, ?, ?, ?);";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, entity.getAccountId());
            preparedStatement.setInt(2, entity.getFriendId());
            preparedStatement.setTimestamp(3, Timestamp.from(entity.getSendedAt().toInstant()));
            preparedStatement.setTimestamp(4, Timestamp.from(entity.getAcceptedAt().toInstant()));

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

        final String sql = "DELETE FROM friend WHERE id = ? ;";

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
    public void updateInformation(int id, Friend entity) {

        String sql = "UPDATE friend SET acepted_at = ?";
        sql += " WHERE id = ? ";
        try {
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setTimestamp(1, Timestamp.from(entity.getAcceptedAt().toInstant()));
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
