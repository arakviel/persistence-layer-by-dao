package com.arakviel.persistence.impl;

import static java.util.Objects.nonNull;

import com.arakviel.persistence.ConnectionPool;
import com.arakviel.persistence.Dao;
import com.arakviel.persistence.entity.User;
import com.arakviel.persistence.exeption.persistence.NoResultException;
import com.arakviel.persistence.exeption.persistence.PersistenceException;
import com.arakviel.persistence.filter.UserFilter;
import com.arakviel.persistence.util.FilterSelectHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class UserDao extends Dao<User> {

    // language=H2
    private static final String FIND_ALL_SQL =
            """
            SELECT id,
                   login,
                   pass,
                   photo
              FROM users
            """;
    // language=H2
    private static final String SAVE_SQL =
            """
            INSERT INTO users(login, pass, photo)
            VALUES (?, ?, ?);
            """;
    // language=H2
    private static final String UPDATE_SQL =
            """
            UPDATE users
               SET login = ?,
                   pass = ?,
                   photo = ?
             WHERE id = ?;
            """;

    /**
     * We get a filtered collection of entities.
     *
     * @param filter values of entity attributes for filtering
     * @return collection of Entities
     */
    public List<User> findAll(final UserFilter filter) {
        List<Object> parameters = new ArrayList<>();

        var filterSelectHelper =
                new FilterSelectHelper() {
                    @Override
                    protected void initParams() {
                        if (nonNull(filter.login())) {
                            super.whereSQL.add("login LIKE ?");
                            parameters.add("%" + filter.login() + "%");
                        }
                    }
                };

        String sql = filterSelectHelper.getSql(FIND_ALL_SQL);
        parameters.add(filter.limit());
        parameters.add(filter.offset());

        return super.findAllByFilter(parameters, sql);
    }

    @Override
    public User save(User user) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement =
                        connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhoto());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt("id"));
            }
            return user;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При збереженні запису в %s".formatted(UserDao.class.getSimpleName()));
        }
    }

    @Override
    public boolean update(final User user) {
        try (Connection connection = ConnectionPool.get();
                PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getPhoto());
            statement.setInt(4, user.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                    "При оновленні запису в %s".formatted(UserDao.class.getSimpleName()));
        }
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected User buildEntity(final ResultSet resultSet) {
        try {
            return User.builder()
                    .id(resultSet.getInt("id"))
                    .login(resultSet.getString("login"))
                    .password(resultSet.getString("pass"))
                    .photo(resultSet.getObject("photo", String.class))
                    .build();
        } catch (SQLException e) {
            throw new NoResultException(
                    "Не вдалось отримати ResultSet в %s".formatted(UserDao.class.getSimpleName()));
        }
    }

    private UserDao() {}

    private static class UserDaoHolder {
        public static final UserDao HOLDER_INSTANCE = new UserDao();
    }

    public static UserDao getInstance() {
        return UserDaoHolder.HOLDER_INSTANCE;
    }
}
