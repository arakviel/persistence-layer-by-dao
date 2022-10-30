INSERT INTO users(login, pass, photo)
VALUES ('arakviel', 'qwerty', 'image.jpg'),
       ('the_user1', 'qwerty', 'image.jpg'),
       ('the_user2', 'qwerty', 'image.jpg'),
       ('the_user3', 'qwerty', 'image.jpg'),
       ('the_user4', 'qwerty', 'image.jpg'),
       ('the_user5', 'qwerty', 'image.jpg'),
       ('the_user6', 'qwerty', 'image.jpg');

INSERT INTO tags(name)
VALUES ('tag1'),
       ('tag2'),
       ('tag3'),
       ('tag4'),
       ('tag5');

INSERT INTO posts(user_id, title, body, updated_at)
VALUES (1, 'Заголовок 1',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        CURRENT_TIMESTAMP),
       (1, 'Заголовок 2',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        CURRENT_TIMESTAMP),
       (2, 'Заголовок 3',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        CURRENT_TIMESTAMP),
       (3, 'Заголовок 4',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        CURRENT_TIMESTAMP),
       (5, 'Заголовок 5',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        CURRENT_TIMESTAMP),
       (6, 'Заголовок 6',
        'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',
        CURRENT_TIMESTAMP);

INSERT INTO comments(user_id, post_id, body)
VALUES (1, 1, 'Привіт світ!'),
       (1, 2, 'Привіт світ!'),
       (1, 3, 'Привіт світ!'),
       (2, 4, 'Це мій перший коментар!'),
       (2, 1, 'Це мій другий коментар!');

INSERT INTO posts_tags(post_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1),
       (2, 4),
       (3, 2),
       (3, 5),
       (5, 2),
       (6, 1),
       (6, 2),
       (6, 3),
       (6, 4),
       (6, 5);