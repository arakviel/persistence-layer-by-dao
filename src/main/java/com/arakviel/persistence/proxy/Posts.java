package com.arakviel.persistence.proxy;

import com.arakviel.persistence.entity.Post;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import java.util.Collection;

public interface Posts {
    Collection<Post> getPosts(int tagId) throws RequiredFieldsException;
}
