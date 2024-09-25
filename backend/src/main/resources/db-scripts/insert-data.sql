INSERT INTO account (username, email, image_data, is_verified) VALUES ('johndoe', 'johndoe@example.com', NULL, TRUE);

INSERT INTO account_password (account_id, user_password, created_at, active, staging, verification_code, code_valid_until) VALUES (1, '$2a$12$NT184t2tjpjm1JCQ2zpEXu/wQhKklLXSkmaJiPlw06iae4gGXYCnq', NOW(), TRUE, FALSE, 'verification_code_example', NOW() + INTERVAL '1 hour');
