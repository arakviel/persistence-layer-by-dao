DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts_tags;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS tags;

CREATE TABLE users
    (
        PRIMARY KEY (id),
        id INT NOT NULL AUTO_INCREMENT,
        login VARCHAR(32) NOT NULL,
        CONSTRAINT users_login_key
            UNIQUE (login),
        CONSTRAINT users_login_min_length_check
            CHECK (LENGTH(login) > 5),
        pass VARCHAR(64) NOT NULL,
        photo VARCHAR(24) NULL
    );

CREATE TABLE tags
    (
        PRIMARY KEY (id),
        id INT NOT NULL AUTO_INCREMENT,
        name VARCHAR(128) NOT NULL,
        CONSTRAINT tags_name_key
            UNIQUE (name)
    );

CREATE TABLE posts
    (
        PRIMARY KEY (id),
        id INT NOT NULL AUTO_INCREMENT,
        user_id INT NOT NULL,
        CONSTRAINT posts_user_id_users_id_fkey
            FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE,
        title VARCHAR(248) NOT NULL,
        CONSTRAINT posts_title_key
            UNIQUE (title),
        CONSTRAINT posts_title_min_length_check
            CHECK (LENGTH(title) > 5),
        body TEXT NOT NULL,
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE comments
    (
        PRIMARY KEY (id),
        id INT NOT NULL AUTO_INCREMENT,
        user_id INT NOT NULL,
        CONSTRAINT comments_user_id_users_id_fkey
            FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
        post_id INT NOT NULL,
        CONSTRAINT comments_post_id_posts_id_fkey
            FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE,
        body VARCHAR(1028) NOT NULL,
        CONSTRAINT comments_body_min_length_check
            CHECK (LENGTH(body) > 10),
        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE posts_tags
    (
        PRIMARY KEY (post_id, tag_id),
        post_id INT NOT NULL
            REFERENCES posts (id),
        tag_id INT NOT NULL
            REFERENCES tags (id)
    );