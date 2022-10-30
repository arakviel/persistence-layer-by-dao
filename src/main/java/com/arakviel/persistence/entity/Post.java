package com.arakviel.persistence.entity;

import com.arakviel.persistence.entity.User.UserBuilder;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import com.arakviel.persistence.proxy.Tags;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class Post implements Entity {
    /** Non-mandatory */
    private int id;
    /** Mandatory */
    private User user;
    /** Mandatory */
    private String title;
    /** Mandatory */
    private String body;
    /** Non-mandatory */
    private LocalDateTime createdAt;
    /** Non-mandatory */
    private LocalDateTime updatedAt;
    /** Non-mandatory */
    private final Tags tags;

    private Post(PostBuilder postBuilder) {
        this.id = postBuilder.id;
        this.user = postBuilder.user;
        this.title = postBuilder.title;
        this.body = postBuilder.body;
        this.createdAt = postBuilder.createdAt;
        this.updatedAt = postBuilder.updatedAt;
        this.tags = postBuilder.tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Tags getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Post.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("title='" + title + "'")
                .add("body='" + body + "'")
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .toString();
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public static class PostBuilder {

        /** Non-mandatory */
        private int id;
        /** Mandatory */
        private User user;
        /** Mandatory */
        private String title;
        /** Mandatory */
        private String body;
        /** Non-mandatory */
        private LocalDateTime createdAt;
        /** Non-mandatory */
        private LocalDateTime updatedAt;
        /** Non-mandatory */
        private Tags tags;

        /** Non-mandatory, must be followed by methods in {@link PostBuilder} */
        public PostBuilder id(int id) {
            this.id = id;
            return this;
        }

        /** Mandatory, must be followed by {@link PostBuilder#title(String)} */
        public PostBuilder user(User user) {
            this.user = user;
            return this;
        }

        /** Mandatory, must be followed by {@link PostBuilder#body(String)} */
        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        /** Mandatory, must be followed by methods in {@link UserBuilder} */
        public PostBuilder body(String body) {
            this.body = body;
            return this;
        }

        /** Non-mandatory, must be followed by methods in {@link PostBuilder} */
        public PostBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Non-mandatory, must be followed by methods in {@link PostBuilder} */
        public PostBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        /** Non-mandatory, must be followed by methods in {@link PostBuilder} */
        public PostBuilder tags(Tags tags) {
            this.tags = tags;
            return this;
        }

        /** Creates an instance of {@link Post} */
        public Post build() {
            if (Objects.isNull(user)
                    || Stream.of(title, body).anyMatch(s -> Objects.isNull(s) || s.isBlank())) {
                throw new RequiredFieldsException();
            }

            return new Post(this);
        }
    }
}
