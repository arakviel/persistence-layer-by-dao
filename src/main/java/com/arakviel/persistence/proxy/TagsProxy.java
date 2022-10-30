package com.arakviel.persistence.proxy;

import com.arakviel.persistence.entity.Tag;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import java.util.Collection;

public class TagsProxy implements Tags {

    private Tags tags;

    @Override
    public Collection<Tag> getTags(int postId) throws RequiredFieldsException {
        if (tags == null) {
            tags = new TagsImpl();
        }
        return tags.getTags(postId);
    }
}
