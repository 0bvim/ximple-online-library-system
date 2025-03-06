-- V1__Initial_Schema.sql
-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Users table
CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
    );

-- Books table
CREATE TABLE IF NOT EXISTS books (
                                     id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    genre VARCHAR(50),
    publication_year INTEGER,
    total_copies INTEGER NOT NULL DEFAULT 1,
    available_copies INTEGER NOT NULL DEFAULT 1,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
    );

-- Create index for book search if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_books_title') THEN
CREATE INDEX idx_books_title ON books (title);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_books_author') THEN
CREATE INDEX idx_books_author ON books (author);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_books_genre') THEN
CREATE INDEX idx_books_genre ON books (genre);
END IF;
END $$;

-- Reservations table
CREATE TABLE IF NOT EXISTS reservations (
                                            id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id),
    book_id UUID NOT NULL REFERENCES books(id),
    reservation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    due_date DATE NOT NULL,
    return_date TIMESTAMP WITH TIME ZONE,
                              status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
    );

-- Create index for reservation queries if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reservations_user_id') THEN
CREATE INDEX idx_reservations_user_id ON reservations (user_id);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reservations_book_id') THEN
CREATE INDEX idx_reservations_book_id ON reservations (book_id);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reservations_status') THEN
CREATE INDEX idx_reservations_status ON reservations (status);
END IF;
END $$;

-- Reviews table
CREATE TABLE IF NOT EXISTS reviews (
                                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id),
    book_id UUID NOT NULL REFERENCES books(id),
    rating INTEGER NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
    );

-- Create index for review queries if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reviews_book_id') THEN
CREATE INDEX idx_reviews_book_id ON reviews (book_id);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reviews_user_id') THEN
CREATE INDEX idx_reviews_user_id ON reviews (user_id);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_reviews_rating') THEN
CREATE INDEX idx_reviews_rating ON reviews (rating);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_unique_user_book_review') THEN
CREATE UNIQUE INDEX idx_unique_user_book_review ON reviews (user_id, book_id);
END IF;
END $$;

-- Event Store table for event sourcing
CREATE TABLE IF NOT EXISTS event_store (
                                           id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    event_type VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id UUID NOT NULL,
    data JSONB NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    processed BOOLEAN DEFAULT FALSE
    );

-- Create index for event queries if they don't exist
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_event_store_entity_id') THEN
CREATE INDEX idx_event_store_entity_id ON event_store (entity_id);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_event_store_event_type') THEN
CREATE INDEX idx_event_store_event_type ON event_store (event_type);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_event_store_created_at') THEN
CREATE INDEX idx_event_store_created_at ON event_store (created_at);
END IF;

    IF NOT EXISTS (SELECT 1 FROM pg_indexes WHERE indexname = 'idx_event_store_processed') THEN
CREATE INDEX idx_event_store_processed ON event_store (processed);
END IF;
END $$;