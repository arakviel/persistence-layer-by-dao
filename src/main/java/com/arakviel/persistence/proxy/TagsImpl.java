package com.arakviel.persistence.proxy;

import com.arakviel.persistence.entity.Tag;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import com.arakviel.persistence.impl.PostDao;
import java.util.Collection;

public class TagsImpl implements Tags {

    @Override
    public Collection<Tag> getTags(int postId) throws RequiredFieldsException {
        return PostDao.getInstance().findAllTags(postId);
    }
}
