INSERT INTO Run (id, title, started_on, completed_on, miles, location, version)
VALUES
(1, 'Morning Run', '2023-01-15 08:00:00', '2023-01-15 08:45:00', 5, 'OUTDOOR', 1),
(2, 'Treadmill Session', '2023-01-17 18:30:00', '2023-01-17 19:15:00', 3, 'INDOOR', 2),
(3, 'Park Run', '2023-01-20 07:00:00', '2023-01-20 07:50:00', 6, 'OUTDOOR', 3),
(4, 'Gym Track', '2023-01-22 16:00:00', '2023-01-22 16:40:00', 4, 'INDOOR', 4),
(5, 'Beach Run', '2023-01-25 09:15:00', '2023-01-25 10:30:00', 8, 'OUTDOOR', 5)
ON CONFLICT (id) DO UPDATE SET
    title = EXCLUDED.title,
    started_on = EXCLUDED.started_on,
    completed_on = EXCLUDED.completed_on,
    miles = EXCLUDED.miles,
    location = EXCLUDED.location;