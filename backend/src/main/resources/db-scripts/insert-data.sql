INSERT INTO account (username, email, image_data, is_verified) VALUES
('user1', 'user1@example.com', NULL, TRUE),
('user2', 'user2@example.com', NULL, TRUE),
('user3', 'user3@example.com', NULL, FALSE),
('user4', 'user4@example.com', NULL, TRUE),
('user5', 'user5@example.com', NULL, FALSE),
('user6', 'user6@example.com', NULL, TRUE),
('user7', 'user7@example.com', NULL, TRUE),
('user8', 'user8@example.com', NULL, FALSE),
('user9', 'user9@example.com', NULL, TRUE),
('user10', 'user10@example.com', NULL, FALSE),
('johndoe', 'johndoe@example.com', NULL, TRUE);

INSERT INTO account_password (account_id, user_password, created_at, active, staging, verification_code, code_valid_until) VALUES
(1, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_1', NOW() + INTERVAL '1 day'),
(2, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_2', NOW() + INTERVAL '1 day'),
(3, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_3', NOW() + INTERVAL '1 day'),
(4, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_4', NOW() + INTERVAL '1 day'),
(5, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_5', NOW() + INTERVAL '1 day'),
(6, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_6', NOW() + INTERVAL '1 day'),
(7, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_7', NOW() + INTERVAL '1 day'),
(8, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_8', NOW() + INTERVAL '1 day'),
(9, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_9', NOW() + INTERVAL '1 day'),
(10, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_10', NOW() + INTERVAL '1 day'),
(11, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_example', NOW() + INTERVAL '1 hour');

INSERT INTO friend (account_id, friend_id, sended_at, acepted_at) VALUES
(1, 2, NOW(), NOW() + INTERVAL '1 hour'),
(1, 3, NOW(), NULL),
(2, 4, NOW(), NOW() + INTERVAL '1 hour'),
(3, 5, NOW(), NULL),
(4, 6, NOW(), NOW() + INTERVAL '1 hour'),
(5, 7, NOW(), NULL),
(6, 8, NOW(), NOW() + INTERVAL '1 hour'),
(7, 9, NOW(), NULL),
(8, 10, NOW(), NOW() + INTERVAL '1 hour'),
(9, 1, NOW(), NULL);

INSERT INTO meetup (title, information, created_at, cep_address, state_address, city_address, neighborhood_address, number_address, street_address, date_start, date_end, stage, pix_key) VALUES
('Meetup 1', 'Information 1', NOW(), '12345-678', 'State 1', 'City 1', 'Neighborhood 1', '10', 'Street 1', NOW() + INTERVAL '1 day', NOW() + INTERVAL '2 days', 'created', 'pix_key_1'),
('Meetup 2', 'Information 2', NOW(), '12345-679', 'State 2', 'City 2', 'Neighborhood 2', '20', 'Street 2', NOW() + INTERVAL '2 days', NOW() + INTERVAL '3 days', 'started', 'pix_key_2'),
('Meetup 3', 'Information 3', NOW(), '12345-680', 'State 3', 'City 3', 'Neighborhood 3', '30', 'Street 3', NOW() + INTERVAL '3 days', NOW() + INTERVAL '4 days', 'finished', 'pix_key_3'),
('Meetup 4', 'Information 4', NOW(), '12345-681', 'State 4', 'City 4', 'Neighborhood 4', '40', 'Street 4', NOW() + INTERVAL '4 days', NOW() + INTERVAL '5 days', 'canceled', 'pix_key_4'),
('Meetup 5', 'Information 5', NOW(), '12345-682', 'State 5', 'City 5', 'Neighborhood 5', '50', 'Street 5', NOW() + INTERVAL '5 days', NOW() + INTERVAL '6 days', 'created', 'pix_key_5'),
('Meetup 6', 'Information 6', NOW(), '12345-683', 'State 6', 'City 6', 'Neighborhood 6', '60', 'Street 6', NOW() + INTERVAL '6 days', NOW() + INTERVAL '7 days', 'started', 'pix_key_6'),
('Meetup 7', 'Information 7', NOW(), '12345-684', 'State 7', 'City 7', 'Neighborhood 7', '70', 'Street 7', NOW() + INTERVAL '7 days', NOW() + INTERVAL '8 days', 'finished', 'pix_key_7'),
('Meetup 8', 'Information 8', NOW(), '12345-685', 'State 8', 'City 8', 'Neighborhood 8', '80', 'Street 8', NOW() + INTERVAL '8 days', NOW() + INTERVAL '9 days', 'canceled', 'pix_key_8'),
('Meetup 9', 'Information 9', NOW(), '12345-686', 'State 9', 'City 9', 'Neighborhood 9', '90', 'Street 9', NOW() + INTERVAL '9 days', NOW() + INTERVAL '10 days', 'created', 'pix_key_9'),
('Meetup 10', 'Information 10', NOW(), '12345-687', 'State 10', 'City 10', 'Neighborhood 10', '100', 'Street 10', NOW() + INTERVAL '10 days', NOW() + INTERVAL '11 days', 'started', 'pix_key_10');

INSERT INTO meetup_image (meetup_id, image_data) VALUES
(1,  '\x48656c6c6f'),
(2,  '\x48656c6c6f'),
(3,  '\x48656c6c6f'),
(4,  '\x48656c6c6f'),
(5,  '\x48656c6c6f'),
(6,  '\x48656c6c6f'),
(7,  '\x48656c6c6f'),
(8,  '\x48656c6c6f'),
(9,  '\x48656c6c6f'),
(10,  '\x48656c6c6f');

INSERT INTO participate (account_id, meetup_id, role_participant, active, sended_at, acepted_at) VALUES
(1, 1, 'organizer', TRUE, NOW(), NULL),
(2, 2, 'participant', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(3, 3, 'organizer', TRUE, NOW(), NULL),
(4, 4, 'participant', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(5, 5, 'organizer', TRUE, NOW(), NULL),
(6, 6, 'participant', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(7, 7, 'organizer', TRUE, NOW(), NULL),
(8, 8, 'participant', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(9, 9, 'participant', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(10, 10, 'participant', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(11, 1, 'participant', TRUE, NOW(), NULL),
(11, 2, 'participant', TRUE, NOW(), NULL),
(11, 3, 'participant', TRUE, NOW(), NULL),
(11, 4, 'participant', TRUE, NOW(), NULL),
(11, 5, 'participant', TRUE, NOW(), NULL),
(11, 6, 'participant', TRUE, NOW(), NULL),
(11, 7, 'participant', TRUE, NOW(), NULL),
(11, 8, 'participant', TRUE, NOW(), NULL),
(11, 9, 'organizer', TRUE, NOW(), NOW() + INTERVAL '5 hours'),
(11, 10, 'participant', TRUE, NOW(), NULL);

INSERT INTO management (participate_id, managment_at, type_action) VALUES
(1, NOW(), 'create'),
(2, NOW(), 'modify'),
(3, NOW(), 'delete'),
(4, NOW(), 'add_participant'),
(5, NOW(), 'remove_participant'),
(6, NOW(), 'create'),
(7, NOW(), 'modify'),
(8, NOW(), 'delete'),
(9, NOW(), 'add_participant'),
(10, NOW(), 'remove_participant');

INSERT INTO expanses (meetup_id, cost, about) VALUES
(1, 100.00, 'Expense for meetup 1'),
(2, 150.00, 'Expense for meetup 2'),
(3, 200.00, 'Expense for meetup 3'),
(4, 250.00, 'Expense for meetup 4'),
(5, 300.00, 'Expense for meetup 5'),
(6, 350.00, 'Expense for meetup 6'),
(7, 400.00, 'Expense for meetup 7'),
(8, 450.00, 'Expense for meetup 8'),
(9, 500.00, 'Expense for meetup 9'),
(10, 550.00, 'Expense for meetup 10');

INSERT INTO payment (account_id, expanse_id, paid_at, value_pay, about, type_payment) VALUES
(1, 1, NOW(), 100.00, 'Payment for expense 1', 'credit_card'),
(2, 2, NOW(), 150.00, 'Payment for expense 2', 'debit_card'),
(3, 3, NOW(), 200.00, 'Payment for expense 3', 'pix'),
(4, 4, NOW(), 250.00, 'Payment for expense 4', 'cash'),
(5, 5, NOW(), 300.00, 'Payment for expense 5', 'credit_card'),
(6, 6, NOW(), 350.00, 'Payment for expense 6', 'debit_card'),
(7, 7, NOW(), 400.00,    'Payment for expense 7', 'pix'),
(8, 8, NOW(), 450.00, 'Payment for expense 8', 'cash'),
(9, 9, NOW(), 500.00, 'Payment for expense 9', 'credit_card'),
(10, 10, NOW(), 550.00, 'Payment for expense 10', 'debit_card');