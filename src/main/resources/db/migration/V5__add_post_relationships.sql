ALTER TABLE posts
    ADD COLUMN author_id UUID NOT NULL,
    ADD COLUMN category_id UUID NOT NULL;

ALTER TABLE posts
    ADD CONSTRAINT fk_posts_author
        FOREIGN KEY (author_id)
            REFERENCES users(id);

ALTER TABLE posts
    ADD CONSTRAINT fk_posts_category
        FOREIGN KEY (category_id)
            REFERENCES categories(id);


CREATE TABLE post_tags (
   post_id UUID NOT NULL,
   tag_id UUID NOT NULL,

   CONSTRAINT pk_post_tags
       PRIMARY KEY (post_id, tag_id),

   CONSTRAINT fk_post_tags_post
       FOREIGN KEY (post_id)
        REFERENCES posts(id),

   CONSTRAINT fk_post_tags_tag
       FOREIGN KEY (tag_id)
           REFERENCES tags(id)
);


CREATE INDEX idx_posts_author_id
    ON posts(author_id);

CREATE INDEX idx_posts_category_id
    ON posts(category_id);

CREATE INDEX idx_post_tags_tag_id
    ON post_tags(tag_id);