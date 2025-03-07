-- V2__Insert_Seed_Data.sql

-- Seed data for users
INSERT INTO users (id, username, email)
VALUES
    (uuid_generate_v4(), 'john_doe', 'john.doe@example.com'),
    (uuid_generate_v4(), 'jane_smith', 'jane.smith@example.com'),
    (uuid_generate_v4(), 'robert_johnson', 'robert.johnson@example.com'),
    (uuid_generate_v4(), 'emily_wilson', 'emily.wilson@example.com'),
    (uuid_generate_v4(), 'michael_brown', 'michael.brown@example.com');

-- Seed data for books
INSERT INTO books (id, title, author, isbn, genre, publication_year, total_copies, available_copies)
VALUES
    (uuid_generate_v4(), 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'Fiction', 1925, 5, 3),
    (uuid_generate_v4(), 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'Fiction', 1960, 4, 2),
    (uuid_generate_v4(), 'Algorithms to Live By', 'Brian Christian', '9781627790369', 'Computer Science', 2016, 3, 3),
    (uuid_generate_v4(), 'Clean Code', 'Robert C. Martin', '9780132350884', 'Computer Science', 2008, 6, 4),
    (uuid_generate_v4(), 'The Hobbit', 'J.R.R. Tolkien', '9780547928227', 'Fantasy', 1937, 7, 5),
    (uuid_generate_v4(), 'Dune', 'Frank Herbert', '9780441172719', 'Science Fiction', 1965, 4, 2),
    (uuid_generate_v4(), 'Pride and Prejudice', 'Jane Austen', '9780141439518', 'Romance', 1813, 5, 4);

-- For Reservations and Reviews, we need to reference existing records
-- So we'll first create a temporary structure to store user and book IDs

-- Create temporary tables to store generated IDs
CREATE TEMPORARY TABLE temp_user_ids AS
SELECT id FROM users LIMIT 5;

CREATE TEMPORARY TABLE temp_book_ids AS
SELECT id FROM books LIMIT 7;

-- Seed data for reservations - using IDs from temporary tables
INSERT INTO reservations (id, user_id, book_id, reservation_date, due_date, return_date, status)
SELECT
    uuid_generate_v4(),
    u.id,
    b.id,
    timestamp,
    date,
    return_timestamp,
    status
FROM (
    VALUES
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 0), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 0), '2025-02-15 10:30:00'::timestamp, '2025-03-15'::date, NULL, 'BORROWED'::text),
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 1), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 1), '2025-02-10 14:45:00'::timestamp, '2025-03-10'::date, NULL, 'BORROWED'::text),
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 2), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 5), '2025-02-20 09:15:00'::timestamp, '2025-03-20'::date, NULL, 'BORROWED'::text),
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 3), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 0), '2025-01-05 11:30:00'::timestamp, '2025-02-05'::date, '2025-02-03 15:45:00'::timestamp, 'RETURNED'::text),
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 4), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 4), '2025-01-25 16:20:00'::timestamp, '2025-02-25'::date, '2025-02-20 10:15:00'::timestamp, 'RETURNED'::text),
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 0), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 3), '2025-03-01 13:10:00'::timestamp, '2025-04-01'::date, NULL, 'BORROWED'::text),
    ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 1), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 6), '2025-02-28 09:45:00'::timestamp, '2025-03-07'::date, NULL, 'RESERVED'::text)
    ) AS v(user_id, book_id, timestamp, date, return_timestamp, status)
    CROSS JOIN (SELECT 1) AS dummy(n)
    CROSS JOIN LATERAL (SELECT user_id AS u_id, book_id AS b_id) AS ids(u_id, b_id)
    INNER JOIN temp_user_ids u ON u.id = ids.u_id
    INNER JOIN temp_book_ids b ON b.id = ids.b_id;

-- Seed data for reviews
INSERT INTO reviews (id, user_id, book_id, rating, comment, created_at)
SELECT
    uuid_generate_v4(),
    u.id,
    b.id,
    rating,
    comment,
    created_at
FROM (
         VALUES
             ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 0), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 0), 5, 'A true classic that remains relevant. Beautiful prose and compelling characters.', '2025-01-10 14:30:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 1), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 1), 5, 'One of the most impactful books I''ve ever read. A must for everyone.', '2025-01-15 16:45:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 2), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 5), 4, 'Incredible world-building and political intrigue. A sci-fi masterpiece.', '2025-02-22 10:20:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 3), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 3), 5, 'Changed how I approach software development. Essential for any programmer.', '2025-02-05 09:15:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 4), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 4), 4, 'A delightful adventure that stands the test of time. Perfect introduction to fantasy.', '2025-02-21 11:30:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 0), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 2), 4, 'Fascinating insights into decision-making. Makes complex concepts accessible.', '2025-03-02 15:10:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 1), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 6), 5, 'Witty dialogue and timeless social commentary. Jane Austen at her best.', '2025-03-01 12:45:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 2), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 0), 3, 'Beautiful writing but found some characters frustrating. Still worth reading.', '2025-01-20 17:30:00'::timestamp),
     ((SELECT id FROM temp_user_ids LIMIT 1 OFFSET 3), (SELECT id FROM temp_book_ids LIMIT 1 OFFSET 4), 5, 'Read it to my children and they were captivated. Tolkien''s storytelling is magical.', '2025-02-25 19:15:00'::timestamp)
    ) AS v(user_id, book_id, rating, comment, created_at)
CROSS JOIN (SELECT 1) AS dummy(n)
CROSS JOIN LATERAL (SELECT user_id AS u_id, book_id AS b_id) AS ids(u_id, b_id)
INNER JOIN temp_user_ids u ON u.id = ids.u_id
    INNER JOIN temp_book_ids b ON b.id = ids.b_id;

-- Drop temporary tables
DROP TABLE temp_user_ids;
DROP TABLE temp_book_ids;

-- Enable UUID extension if not already enabled
-- Uncomment this if your database doesn't have the extension enabled
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";