package com.arakviel.persistence.proxy;

import com.arakviel.persistence.entity.Tag;
import com.arakviel.persistence.exeption.RequiredFieldsException;
import java.util.Collection;

public interface Tags {
    Collection<Tag> getTags(int postId) throws RequiredFieldsException;
}
