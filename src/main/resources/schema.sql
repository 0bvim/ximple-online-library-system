-- Drop tables if they exist (including dependent objects)
DROP TABLE IF EXISTS Review CASCADE;
DROP TABLE IF EXISTS Reservation CASCADE;
DROP TABLE IF EXISTS Book CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

-- Create the User table if it does not exist
CREATE TABLE IF NOT EXISTS Users
(
    id       UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);

-- Create the Book table if it does not exist
CREATE TABLE IF NOT EXISTS Book
(
    id     UUID PRIMARY KEY,
    title  VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn   VARCHAR(13)  NOT NULL UNIQUE,
    genre  VARCHAR(50)  NOT NULL,
    amount INTEGER DEFAULT 1
);

-- Create the Reservation table if it does not exist
CREATE TABLE IF NOT EXISTS Reservation
(
    id               UUID PRIMARY KEY,
    book_id          UUID        NOT NULL,
    user_id          UUID        NOT NULL,
    reservation_date TIMESTAMP   NOT NULL,
    due_date         TIMESTAMP   NOT NULL,
    status           VARCHAR(20) NOT NULL,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Book (id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);

-- Create the Review table if it does not exist
CREATE TABLE IF NOT EXISTS Review
(
    id         UUID PRIMARY KEY,
    book_id    UUID NOT NULL,
    user_id    UUID NOT NULL,
    rating     INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment    VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_book FOREIGN KEY (book_id) REFERENCES Book (id) ON DELETE CASCADE,
    CONSTRAINT fk_review_user FOREIGN KEY (user_id) REFERENCES Users (id) ON DELETE CASCADE
);