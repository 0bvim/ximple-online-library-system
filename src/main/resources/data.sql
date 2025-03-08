-- Insert sample users
INSERT INTO "User" (id, username, email)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'john_doe', 'john@example.com'),
       ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'jane_smith', 'jane@example.com'),
       ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'bob_wilson', 'bob@example.com')
ON CONFLICT (email) DO NOTHING;

-- Insert sample books
INSERT INTO Book (id, title, author, isbn, genre, amount)
VALUES ('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Clean Code', 'Robert C. Martin', '9780132350884', 'PROGRAMMING', 2),
       ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'The Hobbit', 'J.R.R. Tolkien', '9780547928227', 'FANTASY', 3),
       ('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'The Da Vinci Code', 'Dan Brown', '9780385504201', 'THRILLER', 1),
       ('6e439804-fb6e-4438-8209-f7d2d2f40e3d', 'Pride and Prejudice', 'Jane Austen', '9780141439518', 'ROMANCE', 2),
        ('ef0a1d15-106a-4229-ab80-d1723f517ee2', 'Pride', 'Jane ', '9780141419518', 'TRAVEL', 2)
ON CONFLICT (isbn) DO NOTHING;

-- Insert sample reservations
INSERT INTO Reservation (id, book_id, user_id, reservation_date, due_date, status)
VALUES ('293125cb-a934-468f-8967-7af40605a513', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2024-03-01 10:00:00', '2024-03-15 10:00:00', 'CONFIRMED'),
       ('511e71aa-66fc-474f-a4c9-ceceff9996cf', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '2024-03-02 14:30:00', '2024-03-16 14:30:00', 'COMPLETED')
ON CONFLICT (id) DO NOTHING;

-- Insert sample reviews
INSERT INTO Review (id, book_id, user_id, rating, comment, created_at)
VALUES ('72bf3dca-fa89-47e6-a70e-5d97ad1a6e28', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 5, 'Excellent programming book!', '2024-03-05 09:15:00'),
       ('2df2124d-c7d3-4edb-a16f-fa7b8c8ed142', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 4, 'Great fantasy novel!', '2024-03-06 16:45:00')
ON CONFLICT (id) DO NOTHING;