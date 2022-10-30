package com.arakviel.persistence.impl;

import static java.util.Objects.nonNull;

import com.arakviel.persistence.ConnectionPool;
import com.arakviel.persistence.Dao;
import com.arakviel.persistence.entity.Post;
import com.arakviel.persistence.entity.Tag;
import com.arakviel.persistence.exeption.persistence.NoResultException;
import com.arakviel.persistence.exeption.persistence.PersistenceException;
import com.arakviel.persistence.filter.TagFilter;
import com.arakviel.persistence.proxy.PostsProxy;
import com.arakviel.persistence.util.FilterSelectHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class TagDao extends Dao<Tag> {

    // language=H2
    private static final String FIND_ALL_SQL =
            """
              SELECT id,
                     name
                FROM tags
            """;
    // language=H2
    private static final String SAVE_SQL =
            """
            INSERT INTO tags(name)
            VALUES (?);
            """;
    // language=H2
    private static final String UPDATE_SQL =
            """
            UPDATE tags
               SET name = ?
             WHERE id = ?;
            """;
    // language=H2
    private static final String GET_POSTS_SQL =
            """
            SELECT p.id,
                   p.user_id,
                   p.title,
                   p.body,
                   p.created_at,
                   p.updated_at
              FROM posts AS p
                   JOIN posts_tags AS pt
                     ON p.id = pt.post_id
             WHERE pt.tag_id = ?;
           """;
    // language=H2
    private static final String ATTACH_TO_POST_SQL =
            """
            INSERT INTO posts_tags(post_id, tag_id)
            VALUES (?, ?);
            """;
    // language=H2
    private static final String DETACH_FROM_POST_SQL =
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
    public List<Tag> findAll(final TagFilter filter) {
        List<Object> parameters = new ArrayList<>();

        var filterSelectHelper =
                new FilterSelectHelper() {
                    @Override
                    protected void initParams() {
                        if (nonNull(filter.name())) {
                            whereSQL.add("name LIKE ?");
                            parameters.add("%" + filter.name() + "%");
                        }
                    }
                };

        String sql = filterSelectHelper.getSql(FIND_ALL_SQL);
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        return super.findAllByFilter(parameters, sql);
    }

    @Override
    public Tag save(Tag tag) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement =
                        connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, tag.getName());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                tag.setId(generatedKeys.getInt("id"));
            }
            return tag;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При збереженні запису в %s".formatted(TagDao.class.getSimpleName()));
        }
    }

    @Override
    public boolean update(Tag tag) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, tag.getName());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При оновленні запису в %s".formatted(TagDao.class.getSimpleName()));
        }
    }

    @Override
    protected Tag buildEntity(ResultSet resultSet) {
        try {
            return Tag.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .posts(new PostsProxy())
                    .build();
        } catch (SQLException e) {
            throw new NoResultException(
                    "Не вдалось отримати ResultSet в %s".formatted(TagDao.class.getSimpleName()));
        }
    }

    @Override
    protected String getTableName() {
        return "tags";
    }

    public List<Post> findAllPosts(int tagId) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement = connection.prepareStatement(GET_POSTS_SQL)) {
            statement.setInt(1, tagId);
            ResultSet resultSet = statement.executeQuery();
            List<Post> posts = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                posts.add(PostDao.getInstance().buildEntity(resultSet));
            }
            return posts;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При знаходженні всіх записів в %s".formatted(TagDao.class.getSimpleName()));
        }
    }

    public boolean attachToPost(int tagId, int postId) {
        return super.executeByTwoParams(postId, tagId, ATTACH_TO_POST_SQL);
    }

    public boolean detachFromPost(int tagId, int postId) {
        return super.executeByTwoParams(postId, tagId, DETACH_FROM_POST_SQL);
    }

    private TagDao() {}

    private static class TagDaoHolder {
        public static final TagDao HOLDER_INSTANCE = new TagDao();
    }

    public static TagDao getInstance() {
        return TagDaoHolder.HOLDER_INSTANCE;
    }
}
