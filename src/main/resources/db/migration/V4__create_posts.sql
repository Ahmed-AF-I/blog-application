CREATE TABLE posts (
   id UUID NOT NULL,
   title VARCHAR(255) NOT NULL,
   content TEXT NOT NULL,
   status VARCHAR(50) NOT NULL,
   reading_time INTEGER NOT NULL,
   created_at TIMESTAMP NOT NULL,
   updated_at TIMESTAMP NOT NULL,
   CONSTRAINT pk_posts PRIMARY KEY (id)
);