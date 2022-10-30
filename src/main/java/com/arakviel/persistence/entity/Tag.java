package com.arakviel.persistence.entity;

import com.arakviel.persistence.exeption.RequiredFieldsException;
import com.arakviel.persistence.proxy.Posts;
import java.util.Objects;
import java.util.StringJoiner;

public class Tag implements Entity {

    /** Non-mandatory */
    private int id;
    /** Mandatory */
    private String name;
    /** Non-mandatory */
    private final Posts posts;

    private Tag(TagBuilder tagBuilder) {
        this.id = tagBuilder.id;
        this.name = tagBuilder.name;
        this.posts = tagBuilder.posts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Posts getPosts() {
        return posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tag tag = (Tag) o;
        return id == tag.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("posts=" + posts)
                .toString();
    }

    public static TagBuilder builder() {
        return new TagBuilder();
    }

    public static class TagBuilder {

        /** Non-mandatory */
        private int id;
        /** Mandatory */
        private String name;
        /** Non-mandatory */
        private Posts posts;

        /** Non-mandatory, must be followed by methods in {@link TagBuilder} */
        public TagBuilder id(int id) {
            this.id = id;
            return this;
        }

        /** Mandatory, must be followed by methods in {@link TagBuilder} */
        public TagBuilder name(String name) {
            this.name = name;
            return this;
        }

        /** Non-mandatory, must be followed by methods in {@link TagBuilder} */
        public TagBuilder posts(Posts posts) {
            this.posts = posts;
            return this;
        }

        /** Creates an instance of {@link Tag} */
        public Tag build() {
            if (Objects.isNull(name) || name.isBlank()) {
                throw new RequiredFieldsException();
            }
            return new Tag(this);
        }
    }
}
