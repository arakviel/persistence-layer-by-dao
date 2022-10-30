package com.arakviel.persistence.filter;

import java.time.LocalDateTime;

public record PostFilter(
        int limit,
        int offset,
        Integer userId,
        String title,
        String body,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {

    public static PostFilterBuilder builder() {
        return new PostFilterBuilder();
    }

    public static class PostFilterBuilder {
        private int limit;
        private int offset;
        private Integer userId;
        private String title;
        private String body;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public PostFilterBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public PostFilterBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public PostFilterBuilder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public PostFilterBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostFilterBuilder body(String body) {
            this.body = body;
            return this;
        }

        public PostFilterBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public PostFilterBuilder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public PostFilter build() {
            return new PostFilter(limit, offset, userId, title, body, createdAt, updatedAt);
        }
    }
}
