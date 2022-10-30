package com.arakviel.persistence.proxy;

import com.arakviel.persistence.entity.Post;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import java.util.Collection;

public class PostsProxy implements Posts {
    private Posts posts;

    @Override
    public Collection<Post> getPosts(int tagId) throws RequiredFieldsException {
        if (posts == null) {
            posts = new PostsImpl();
        }
        return posts.getPosts(tagId);
    }
}
