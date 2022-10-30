package com.arakviel.persistence.impl;

import static java.util.Objects.nonNull;

import com.arakviel.persistence.ConnectionPool;
import com.arakviel.persistence.Dao;
import com.arakviel.persistence.entity.Comment;
import com.arakviel.persistence.exeption.persistence.NoResultException;
import com.arakviel.persistence.exeption.persistence.PersistenceException;
import com.arakviel.persistence.filter.CommentFilter;
import com.arakviel.persistence.util.FilterSelectHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class CommentDao extends Dao<Comment> {

    // language=H2
    private static final String FIND_ALL_SQL =
            """
            SELECT id,
                   user_id,
                   post_id,
                   body,
                   created_at
              FROM comments
            """;
    // language=H2
    private static final String SAVE_SQL =
            """
      INSERT INTO comments(user_id, post_id, body)
      VALUES (?, ?, ?);
      """;
    // language=H2
    private static final String UPDATE_SQL =
            """
      UPDATE comments
         SET user_id = ?,
             post_id = ?,
             body = ?
       WHERE id = ?;
      """;

    /**
     * We get a filtered collection of entities.
     *
     * @param filter values of entity attributes for filtering
     * @return collection of Entities
     */
    public List<Comment> findAll(final CommentFilter filter) {
        List<Object> parameters = new ArrayList<>();

        var filterSelectHelper =
                new FilterSelectHelper() {
                    @Override
                    protected void initParams() {
                        if (nonNull(filter.userId())) {
                            super.whereSQL.add("user_id = ?");
                            parameters.add(filter.userId());
                        }
                        if (nonNull(filter.postId())) {
                            whereSQL.add("post_id = ?");
                            parameters.add(filter.postId());
                        }
                        if (nonNull(filter.body())) {
                            whereSQL.add("body LIKE ?");
                            parameters.add("%" + filter.body() + "%");
                        }
                        if (nonNull(filter.createdAt())) {
                            whereSQL.add("created_at = ?");
                            parameters.add(filter.createdAt());
                        }
                    }
                };

        String sql = filterSelectHelper.getSql(FIND_ALL_SQL);
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        return super.findAllByFilter(parameters, sql);
    }

    @Override
    public Comment save(Comment comment) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement =
                        connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, comment.getUser().getId());
            statement.setInt(2, comment.getPost().getId());
            statement.setString(3, comment.getBody());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                comment.setId(generatedKeys.getInt("id"));
                comment.setCreatedAt(generatedKeys.getTimestamp("created_at").toLocalDateTime());
            }
            return comment;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При збереженні запису в %s".formatted(UserDao.class.getSimpleName()));
        }
    }

    @Override
    public boolean update(Comment comment) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, comment.getUser().getId());
            statement.setInt(2, comment.getPost().getId());
            statement.setString(3, comment.getBody());
            statement.setInt(4, comment.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При оновленні запису в %s".formatted(UserDao.class.getSimpleName()));
        }
    }

    @Override
    protected Comment buildEntity(ResultSet resultSet) {
        UserDao userDao = UserDao.getInstance();
        PostDao postDao = PostDao.getInstance();
        try {
            int userId = resultSet.getInt("user_id");
            int postId = resultSet.getInt("post_id");
            Connection connection = resultSet.getStatement().getConnection();
            return Comment.builder()
                    .id(resultSet.getInt("id"))
                    .user(userDao.findOneById(userId, connection).orElseThrow())
                    .post(postDao.findOneById(postId, connection).orElseThrow())
                    .body(resultSet.getString("body"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new NoResultException(
                    "Не вдалось отримати ResultSet в %s".formatted(UserDao.class.getSimpleName()));
        }
    }

    @Override
    protected String getTableName() {
        return "comments";
    }

    private CommentDao() {}

    private static class CommentDaoHolder {
        public static final CommentDao HOLDER_INSTANCE = new CommentDao();
    }

    public static CommentDao getInstance() {
        return CommentDaoHolder.HOLDER_INSTANCE;
    }
}
