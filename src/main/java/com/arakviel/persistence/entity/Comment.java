package com.arakviel.persistence.entity;

import com.arakviel.persistence.exeption.RequiredFieldsException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

public class Comment implements Entity {
    /** Non-mandatory */
    private int id;
    /** Mandatory */
    private User user;
    /** Mandatory */
    private Post post;
    /** Mandatory */
    private String body;
    /** Non-mandatory */
    private LocalDateTime createdAt;

    public Comment(CommentBuilder commentBuilder) {
        this.id = commentBuilder.id;
        this.user = commentBuilder.user;
        this.post = commentBuilder.post;
        this.body = commentBuilder.body;
        this.createdAt = commentBuilder.createdAt;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Comment.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("post=" + post)
                .add("body='" + body + "'")
                .add("createdAt=" + createdAt)
                .toString();
    }

    public static CommentBuilder builder() {
        return new CommentBuilder();
    }

    public static class CommentBuilder {

        /** Non-mandatory */
        private int id;
        /** Mandatory */
        private User user;
        /** Mandatory */
        private Post post;
        /** Mandatory */
        private String body;
        /** Non-mandatory */
        private LocalDateTime createdAt;

        /** Non-mandatory, must be followed by methods in {@link CommentBuilder} */
        public CommentBuilder id(int id) {
            this.id = id;
            return this;
        }

        /** Mandatory, must be followed by {@link CommentBuilder#post(Post)} */
        public CommentBuilder user(User user) {
            this.user = user;
            return this;
        }

        /** Mandatory, must be followed by {@link CommentBuilder#body(String)} */
        public CommentBuilder post(Post post) {
            this.post = post;
            return this;
        }

        /** Mandatory, must be followed by methods in {@link CommentBuilder} */
        public CommentBuilder body(String body) {
            this.body = body;
            return this;
        }

        /** Non-mandatory, must be followed by methods in {@link CommentBuilder} */
        public CommentBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        /** Creates an instance of {@link Comment} */
        public Comment build() {
            if (Objects.isNull(user)
                    || Objects.isNull(post)
                    || Objects.isNull(body)
                    || body.isBlank()) {
                throw new RequiredFieldsException();
            }
            return new Comment(this);
        }
    }
}
