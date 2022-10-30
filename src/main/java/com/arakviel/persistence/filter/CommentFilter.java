package com.arakviel.persistence.filter;

import java.time.LocalDateTime;

public record CommentFilter(
        int limit,
        int offset,
        Integer userId,
        Integer postId,
        String body,
        LocalDateTime createdAt) {

    public static CommentFilterBuilder builder() {
        return new CommentFilterBuilder();
    }

    public static class CommentFilterBuilder {
        private int limit;
        private int offset;
        private Integer userId;
        private Integer postId;
        private String body;
        private LocalDateTime createdAt;

        public CommentFilterBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public CommentFilterBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public CommentFilterBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public CommentFilterBuilder postId(Integer postId) {
            this.postId = postId;
            return this;
        }

        public CommentFilterBuilder body(String body) {
            this.body = body;
            return this;
        }

        public CommentFilterBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public CommentFilter build() {
            return new CommentFilter(limit, offset, userId, postId, body, createdAt);
        }
    }
}
