package com.arakviel.persistence.proxy;

import com.arakviel.persistence.entity.Post;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import com.arakviel.persistence.impl.TagDao;
import java.util.Collection;

public class PostsImpl implements Posts {

    @Override
    public Collection<Post> getPosts(int tagId) throws RequiredFieldsException {
        return TagDao.getInstance().findAllPosts(tagId);
    }
}
