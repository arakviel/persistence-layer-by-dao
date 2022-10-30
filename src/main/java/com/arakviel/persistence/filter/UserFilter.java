package com.arakviel.persistence.filter;

public record UserFilter(int limit, int offset, String login) {

    public static UserFilterBuilder builder() {
        return new UserFilterBuilder();
    }

    public static class UserFilterBuilder {

        private int limit;
        private int offset;
        private String login;

        public UserFilterBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserFilterBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public UserFilterBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public UserFilter build() {
            return new UserFilter(limit, offset, login);
        }
    }
}
