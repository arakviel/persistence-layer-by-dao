package com.arakviel.persistence.impl;

import static java.util.Objects.nonNull;

import com.arakviel.persistence.ConnectionPool;
import com.arakviel.persistence.Dao;
import com.arakviel.persistence.entity.Post;
import com.arakviel.persistence.entity.Tag;
import com.arakviel.persistence.exeption.persistence.NoResultException;
import com.arakviel.persistence.exeption.persistence.PersistenceException;
import com.arakviel.persistence.filter.PostFilter;
import com.arakviel.persistence.util.FilterSelectHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class PostDao extends Dao<Post> {

    // language=H2
    private static final String FIND_ALL_SQL =
            """
            SELECT id,
                   user_id,
                   title,
                   body,
                   created_at,
                   updated_at
              FROM posts
            """;
    // language=H2
    private static final String SAVE_SQL =
            """
            INSERT INTO posts(user_id, title, body, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?);
            """;
    // language=H2
    private static final String UPDATE_SQL =
            """
            UPDATE posts
               SET user_id = ?,
                   title = ?,
                   body = ?,
                   updated_at = ?
             WHERE id = ?;
            """;
    // language=H2
    private static final String GET_TAGS_SQL =
            """
            SELECT t.id,
                   t.name
              FROM tags AS t
                   JOIN posts_tags AS pt
                     ON t.id = pt.tag_id
             WHERE pt.post_id = ?;
            """;
    // language=H2
    private static final String ATTACH_TO_TAG_SQL =
            """
            INSERT INTO posts_tags(post_id, tag_id)
            VALUES (?, ?);
            """;
    // language=H2
    private static final String DETACH_FROM_TAG_SQL =
            """
            DELETE FROM posts_tags
                  WHERE post_id = ? AND tag_id = ?;
            """;

    /**
     * We get a filtered collection of entities.
     *
     * @param filter values of entity attributes for filtering
     * @return collection of Entities
     */
    public List<Post> findAll(final PostFilter filter) {
        List<Object> parameters = new ArrayList<>();

        var filterSelectHelper =
                new FilterSelectHelper() {
                    @Override
                    protected void initParams() {
                        if (nonNull(filter.userId())) {
                            super.whereSQL.add("user_id LIKE ?");
                            parameters.add("%" + filter.userId() + "%");
                        }
                        if (nonNull(filter.title())) {
                            super.whereSQL.add("title LIKE ?");
                            parameters.add("%" + filter.title() + "%");
                        }
                        if (nonNull(filter.title())) {
                            super.whereSQL.add("body LIKE ?");
                            parameters.add("%" + filter.title() + "%");
                        }
                        if (nonNull(filter.createdAt())) {
                            super.whereSQL.add("created_at = ?");
                            parameters.add(filter.createdAt());
                        }
                        if (nonNull(filter.updatedAt())) {
                            super.whereSQL.add("updated_at = ?");
                            parameters.add(filter.updatedAt());
                        }
                    }
                };

        String sql = filterSelectHelper.getSql(FIND_ALL_SQL);
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        return super.findAllByFilter(parameters, sql);
    }

    @Override
    public Post save(Post post) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement =
                        connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, post.getUser().getId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getBody());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getInt(1));
                post.setCreatedAt(generatedKeys.getTimestamp("created_at").toLocalDateTime());
                post.setUpdatedAt(generatedKeys.getTimestamp("updated_at").toLocalDateTime());
            }
            return post;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При збереженні запису в %s".formatted(PostDao.class.getSimpleName()));
        }
    }

    @Override
    public boolean update(Post post) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, post.getUser().getId());
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getBody());
            statement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(5, post.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При оновленні запису в %s".formatted(PostDao.class.getSimpleName()));
        }
    }

    @Override
    protected Post buildEntity(ResultSet resultSet) {
        UserDao userDao = UserDao.getInstance();
        try {
            int userId = resultSet.getInt("user_id");
            Connection connection = resultSet.getStatement().getConnection();
            return Post.builder()
                    .id(resultSet.getInt("id"))
                    .user(userDao.findOneById(userId, connection).orElseThrow())
                    .title(resultSet.getString("title"))
                    .body(resultSet.getString("body"))
                    .createdAt(resultSet.getTimestamp("created_at").toLocalDateTime())
                    .updatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime())
                    .build();
        } catch (SQLException e) {
            throw new NoResultException(
                    "Не вдалось отримати ResultSet в %s".formatted(TagDao.class.getSimpleName()));
        }
    }

    public boolean attachToTag(int postId, int tagId) {
        return super.executeByTwoParams(postId, tagId, ATTACH_TO_TAG_SQL);
    }

    public boolean detachFromTag(int postId, int tagId) {
        return super.executeByTwoParams(postId, tagId, DETACH_FROM_TAG_SQL);
    }

    @Override
    protected String getTableName() {
        return "posts";
    }

    public List<Tag> findAllTags(int postId) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement = connection.prepareStatement(GET_TAGS_SQL)) {
            statement.setInt(1, postId);
            ResultSet resultSet = statement.executeQuery();
            var tags = new ArrayList<Tag>(resultSet.getFetchSize());
            while (resultSet.next()) {
                tags.add(TagDao.getInstance().buildEntity(resultSet));
            }
            return tags;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При знаходженні всіх записів в %s".formatted(TagDao.class.getSimpleName()));
        }
    }

    private PostDao() {}

    private static class PostDaoHolder {
        public static final PostDao HOLDER_INSTANCE = new PostDao();
    }

    public static PostDao getInstance() {
        return PostDaoHolder.HOLDER_INSTANCE;
    }
}
