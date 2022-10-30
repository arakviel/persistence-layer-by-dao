package com.arakviel.persistence.filter;

public record TagFilter(int limit, int offset, String name) {

    public static TagFilterBuilder builder() {
        return new TagFilterBuilder();
    }

    public static class TagFilterBuilder {

        private int limit;
        private int offset;
        private String name;

        public TagFilterBuilder name(String name) {
            this.name = name;
            return this;
        }

        public TagFilterBuilder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public TagFilterBuilder offset(int offset) {
            this.offset = offset;
            return this;
        }

        public TagFilter build() {
            return new TagFilter(limit, offset, name);
        }
    }
}
