-- Insert admin user if not exists
INSERT INTO admins (username, email, password)
SELECT 'admin@artcom.com', 'admin@artcom.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a'
WHERE NOT EXISTS (SELECT 1 FROM admins WHERE username = 'admin@artcom.com');