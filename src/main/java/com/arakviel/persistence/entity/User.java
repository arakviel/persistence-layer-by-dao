package com.arakviel.persistence.entity;

import com.arakviel.persistence.exeption.RequiredFieldsException;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class User implements Entity {

    /** Non-mandatory */
    private int id;
    /** Mandatory */
    private String login;
    /** Mandatory */
    private String password;
    /** Non-mandatory */
    private String photo;

    private User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.login = userBuilder.login;
        this.password = userBuilder.password;
        this.photo = userBuilder.photo;
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("login='" + login + "'")
                .add("password='" + password + "'")
                .add("photo='" + photo + "'")
                .toString();
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        /** Non-mandatory */
        private int id;
        /** Mandatory */
        private String login;
        /** Mandatory */
        private String password;
        /** Non-mandatory */
        private String photo;

        /** Non-mandatory, must be followed by methods in {@link UserBuilder} */
        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        /** Mandatory, must be followed by {@link UserBuilder#password(String)} */
        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        /** Mandatory, must be followed by methods in {@link UserBuilder} */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /** Non-mandatory, must be followed by methods in {@link UserBuilder} */
        public UserBuilder photo(String photo) {
            this.photo = photo;
            return this;
        }

        /** Creates an instance of {@link User} */
        public User build() {
            if (Stream.of(login, password).anyMatch(s -> Objects.isNull(s) || s.isBlank())) {
                throw new RequiredFieldsException();
            }
            return new User(this);
        }
    }
}
