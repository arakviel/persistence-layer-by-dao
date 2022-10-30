package com.arakviel;

import com.arakviel.persistence.ConnectionPool;
import com.arakviel.persistence.entity.Comment;
import com.arakviel.persistence.entity.Tag;
import com.arakviel.persistence.exeption.connection.ConnectionException;
import com.arakviel.persistence.impl.CommentDao;
import com.arakviel.persistence.impl.TagDao;
import com.arakviel.persistence.util.temp.DbInitialization;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            DbInitialization.apply();

            List<Tag> tags = TagDao.getInstance().findAll();
            tags.forEach(System.out::println);

            Comment comment = CommentDao.getInstance().findOneById(5).orElseThrow();
            System.out.println(comment);

        } finally {
            try {
                ConnectionPool.closePool();
            } catch (ConnectionException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
