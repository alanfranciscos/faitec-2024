package com.eventify.eventify.dao.friend;

import com.eventify.eventify.models.friend.Friend;
import com.eventify.eventify.port.dao.friend.FriendDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendDaoImpl implements FriendDao {

    private final Connection connection;

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
}
