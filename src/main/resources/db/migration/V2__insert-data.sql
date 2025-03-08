-- Insert sample users
INSERT INTO Users (id, username, email)
VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'john_doe', 'john@example.com'),
       ('b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'jane_smith', 'jane@example.com'),
       ('c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 'bob_wilson', 'bob@example.com'),
       ('3339ddb3-98eb-4fae-b8ed-971fd8c200b9', 'alice_jones', 'alice@example.com'),
       ('d96eaa1e-fd5a-4812-9fce-153cc41fef0b', 'charlie_brown', 'charlie@example.com'),
       ('c991c44e-cbf0-4613-986c-5762fc0ff3ad', 'david_clark', 'david@example.com'),
       ('aae03643-bf59-467f-991f-466032ed3df2', 'eve_martin', 'eve@example.com'),
       ('beeb4a67-2063-47aa-833b-5b43c934a53b', 'frank_white', 'frank@example.com'),
       ('27dd474f-064d-4b5f-9b8f-f7ef0897b305', 'grace_hall', 'grace@example.com'),
       ('383d9dca-504e-4cd3-9d6e-144da6e620bb', 'henry_king', 'henry@example.com'),
       ('3a4e9c0d-2d46-4646-acc4-d1d3989c017e', 'isabel_scott', 'isabel@example.com'),
       ('edde03e5-7844-4427-a2ba-3ac97d6d59c3', 'jackson_lee', 'jackson@example.com'),
       ('f4ab6067-e640-42bc-b6ad-cfb318aa4798', 'karen_wright', 'karen@example.com'),
       ('21a616fc-7f2d-4e02-9b82-c00c0d3fc542', 'louis_green', 'louis@example.com'),
       ('c10519d9-3764-4c8e-aa05-ae2b3b45d893', 'mary_adams', 'mary@example.com')
ON CONFLICT (email) DO NOTHING;

-- Insert sample books
INSERT INTO Book (id, title, author, isbn, genre, amount)
VALUES ('d0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'Clean Code', 'Robert C. Martin', '9780132350884', 'PROGRAMMING', 2),
       ('e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'The Hobbit', 'J.R.R. Tolkien', '9780547928227', 'FANTASY', 3),
       ('f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'The Da Vinci Code', 'Dan Brown', '9780385504201', 'THRILLER', 1),
       ('6e439804-fb6e-4438-8209-f7d2d2f40e3d', 'Pride and Prejudice', 'Jane Austen', '9780141439518', 'ROMANCE', 2),
       ('ef0a1d15-106a-4229-ab80-d1723f517ee2', 'Pride', 'Jane ', '9780141419518', 'TRAVEL', 2),
       ('368e50db-d52b-4ac2-aead-88c9411007ed', '1984', 'George Orwell', '9780451524935', 'DYSTOPIAN', 4),
       ('0f8cdaff-416d-45b8-abf8-b53232fd37a7', 'To Kill a Mockingbird', 'Harper Lee', '9780061120084', 'CLASSIC', 3),
       ('71fdd148-959a-40b2-9977-66cfa1095e62', 'Moby Dick', 'Herman Melville', '9781503280786', 'ADVENTURE', 2),
       ('ee00ddcf-7746-4c4d-ae73-a3a5d0e938d8', 'War and Peace', 'Leo Tolstoy', '9780199232765', 'HISTORICAL', 1),
       ('92136c51-922d-4fc4-9a3d-be693be6f297', 'The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 'CLASSIC', 3),
       ('e89a7be1-eb6f-4f63-942a-3d2156f9dcdb', 'Brave New World', 'Aldous Huxley', '9780060850524', 'DYSTOPIAN', 2),
       ('e6e8e00b-d3d6-47ac-a910-fc57a9aab954', 'The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 'CLASSIC', 4),
       ('63eefbca-41a4-4dfa-93cd-33af27917338', 'The Lord of the Rings', 'J.R.R. Tolkien', '9780544003415', 'FANTASY', 5),
       ('ffa1139d-8ce3-42d7-b828-24f62a94a687', 'Harry Potter and the Sorcerers Stone', 'J.K. Rowling', '9780590353427', 'FANTASY', 6),
       ('f4be3316-16e8-4853-8629-b391938b1b85', 'The Alchemist', 'Paulo Coelho', '9780061122415', 'FICTION', 3)
ON CONFLICT (isbn) DO NOTHING;

-- Insert sample reservations
INSERT INTO Reservation (id, book_id, user_id, reservation_date)
VALUES ('527e3135-a3de-4bc1-81be-5a8a37525b5f', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2024-03-01 10:00:00'),
       ('8effedab-0e54-405f-ad12-5f78ea8e6cf0', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '2024-03-02 14:30:00'),
       ('598e5c91-50c8-445b-8722-426a2534d2e1', 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', '2024-03-03 11:00:00'),
       ('999fba70-cbce-4484-bbb0-84de5d7e4613', '6e439804-fb6e-4438-8209-f7d2d2f40e3d', '3339ddb3-98eb-4fae-b8ed-971fd8c200b9', '2024-03-04 12:00:00'),
       ('3b0e1488-458c-4e4a-b920-54b94fbe1da1', 'ef0a1d15-106a-4229-ab80-d1723f517ee2', 'd96eaa1e-fd5a-4812-9fce-153cc41fef0b', '2024-03-05 13:00:00'),
       ('e04ee133-2378-45d9-b876-bb6dd65f7a39', '368e50db-d52b-4ac2-aead-88c9411007ed', 'c991c44e-cbf0-4613-986c-5762fc0ff3ad', '2024-03-06 14:00:00'),
       ('ffd73066-2cef-4a8f-8fc3-b25736570c3d', '0f8cdaff-416d-45b8-abf8-b53232fd37a7', 'aae03643-bf59-467f-991f-466032ed3df2', '2024-03-07 15:00:00'),
       ('1143ca89-3acf-470f-8b34-dabf4d127279', '71fdd148-959a-40b2-9977-66cfa1095e62', 'beeb4a67-2063-47aa-833b-5b43c934a53b', '2024-03-08 16:00:00'),
       ('df449687-9ca7-4122-8f94-f8dd25b7d34e', 'ee00ddcf-7746-4c4d-ae73-a3a5d0e938d8', '27dd474f-064d-4b5f-9b8f-f7ef0897b305', '2024-03-09 17:00:00'),
       ('17745cf3-b82e-4c42-a992-7982c265b063', '92136c51-922d-4fc4-9a3d-be693be6f297', '383d9dca-504e-4cd3-9d6e-144da6e620bb', '2024-03-10 18:00:00'),
       ('494d21f3-74ce-4086-a6f8-c6933d08928f', 'e89a7be1-eb6f-4f63-942a-3d2156f9dcdb', '3a4e9c0d-2d46-4646-acc4-d1d3989c017e', '2024-03-11 19:00:00'),
       ('bad51c92-1f2f-4016-8e0f-734277cab040', 'e6e8e00b-d3d6-47ac-a910-fc57a9aab954', 'edde03e5-7844-4427-a2ba-3ac97d6d59c3', '2024-03-12 20:00:00'),
       ('b2676cbc-4af5-471d-9087-b984baa40b4a', '63eefbca-41a4-4dfa-93cd-33af27917338', 'f4ab6067-e640-42bc-b6ad-cfb318aa4798', '2024-03-13 21:00:00'),
       ('b1254a8e-519d-4ff8-95e6-7ab803deb47e', 'ffa1139d-8ce3-42d7-b828-24f62a94a687', '21a616fc-7f2d-4e02-9b82-c00c0d3fc542', '2024-03-14 22:00:00'),
       ('ae0a6ac4-0ed2-4526-828c-2673b90585f3', 'f4be3316-16e8-4853-8629-b391938b1b85', 'c10519d9-3764-4c8e-aa05-ae2b3b45d893', '2024-03-15 23:00:00')
ON CONFLICT (id) DO NOTHING;

-- Insert sample reviews
INSERT INTO Review (id, book_id, user_id, rating, comment, created_at)
VALUES ('ad1a7a4f-935c-4a39-bba1-548798a47239', 'd0eebc99-9c0b-4ef8-bb6d-6bb9bd380a14', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 5, 'Excellent programming book!', '2024-03-05 09:15:00'),
       ('c4abebf1-3167-4b00-8497-dee9fd583fef', 'e0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15', 'b0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 4, 'Great fantasy novel!', '2024-03-06 16:45:00'),
       ('add4662c-2e4b-4cb0-b1b3-fc913acc4e14', 'f0eebc99-9c0b-4ef8-bb6d-6bb9bd380a16', 'c0eebc99-9c0b-4ef8-bb6d-6bb9bd380a13', 3, 'Interesting thriller!', '2024-03-07 17:45:00'),
       ('ede2ef18-f29f-4152-9ed1-6c4946fd8688', '6e439804-fb6e-4438-8209-f7d2d2f40e3d', '3339ddb3-98eb-4fae-b8ed-971fd8c200b9', 4, 'Classic romance!', '2024-03-08 18:45:00'),
       ('d1ceb67e-68e4-4c03-af06-7c4d3d548746', 'ef0a1d15-106a-4229-ab80-d1723f517ee2', 'd96eaa1e-fd5a-4812-9fce-153cc41fef0b', 2, 'Not my type of travel book.', '2024-03-09 19:45:00'),
       ('45987a56-5ffc-4149-b8b4-d298105c1bee', '368e50db-d52b-4ac2-aead-88c9411007ed', 'c991c44e-cbf0-4613-986c-5762fc0ff3ad', 5, 'A must-read dystopian novel!', '2024-03-10 20:45:00'),
       ('2ea67c55-7e86-499d-b906-886bbba8a442', '0f8cdaff-416d-45b8-abf8-b53232fd37a7', 'aae03643-bf59-467f-991f-466032ed3df2', 4, 'A timeless classic.', '2024-03-11 21:45:00'),
       ('a1693b93-c6e2-4ab8-af87-0dd4d75b8ab7', '71fdd148-959a-40b2-9977-66cfa1095e62', 'beeb4a67-2063-47aa-833b-5b43c934a53b', 3, 'An adventurous tale.', '2024-03-12 22:45:00'),
       ('20a164fe-a3a4-48e3-b728-e0728ab13ff9', 'ee00ddcf-7746-4c4d-ae73-a3a5d0e938d8', '27dd474f-064d-4b5f-9b8f-f7ef0897b305', 5, 'A historical masterpiece.', '2024-03-13 23:45:00'),
       ('121b1fbb-dd9d-4f24-af8f-1f110735ee5a', '92136c51-922d-4fc4-9a3d-be693be6f297', '383d9dca-504e-4cd3-9d6e-144da6e620bb', 5, 'A historical masterpiece.', '2024-03-13 23:45:00'),
       ('5ae2d889-33fb-49aa-ac72-313141106896', 'e89a7be1-eb6f-4f63-942a-3d2156f9dcdb', '3a4e9c0d-2d46-4646-acc4-d1d3989c017e', 5, 'A historical masterpiece.', '2024-03-13 23:45:00'),
       ('a49abccc-1884-4e44-820e-e0191e9b33d6', 'e6e8e00b-d3d6-47ac-a910-fc57a9aab954', 'edde03e5-7844-4427-a2ba-3ac97d6d59c3', 5, 'A historical masterpiece.', '2024-03-13 23:45:00'),
       ('7f9bd2b5-a72c-48ed-bb84-18a2f9c43c1e', '63eefbca-41a4-4dfa-93cd-33af27917338', 'f4ab6067-e640-42bc-b6ad-cfb318aa4798', 5, 'A historical masterpiece.', '2024-03-13 23:45:00'),
       ('6830dfd0-f8ad-4320-9ffd-238552e230d6', 'ffa1139d-8ce3-42d7-b828-24f62a94a687', '21a616fc-7f2d-4e02-9b82-c00c0d3fc542', 5, 'A historical masterpiece.', '2024-03-13 23:45:00')
ON CONFLICT (id) DO NOTHING;
