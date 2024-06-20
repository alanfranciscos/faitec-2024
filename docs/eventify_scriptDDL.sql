drop database if exists eventify;
create database eventify;

\c eventify;

begin;

-- verification code is 6 but we are using 100 to make it more secure (hashed)
CREATE TABLE account ( --Changed
    id SERIAL PRIMARY KEY,
    username VARCHAR(150) NOT NULL, --CHANGED
    email VARCHAR(200) NOT NULL UNIQUE,
    hashed_password VARCHAR(1024) NOT NULL,  --CHANGED
    image_data BYTEA,  --CHANGED
    nickname VARCHAR(100) NOT NULL UNIQUE,  --CHANGED
    verification_code VARCHAR(100) NOT NULL,  --CHANGED
    code_valid_until TIMESTAMP WITH TIME ZONE NOT NULL,  --CHANGED
    is_verified BOOLEAN DEFAULT FALSE  --CHANGED
);

CREATE TABLE FRIEND (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    sended_at TIMESTAMP WITH TIME ZONE NOT NULL,
    acepted_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (friend_id) REFERENCES account(id) ON DELETE CASCADE,
    UNIQUE(account_id, friend_id)
);

-- parece ter duas linhas mas é uma só
-- name changed
CREATE TABLE meetup (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL, --CHANGED
    information TEXT NOT NULL, --CHANGED(DESCRIPTION)
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    cep_address VARCHAR(9), --CHANGED
    state_address VARCHAR(200), --CHANGED
    city_address VARCHAR(200), --CHANGED
    neighborhood_address VARCHAR(200), --CHANGED
    number_address VARCHAR(200), --CHANGED
    street_address VARCHAR(200), --CHANGED

    date_start TIMESTAMP WITH TIME ZONE NOT NULL, --CHANGED
    date_end TIMESTAMP WITH TIME ZONE NOT NULL, --CHANGED
    pix_key VARCHAR(200), --CHANGED
    UNIQUE(title, information, created_at)
);

CREATE TABLE meetup_image (
    id SERIAL PRIMARY KEY,
    meetup_id INTEGER NOT NULL,
    image_data BYTEA NOT NULL,
    FOREIGN KEY (meetup_id) REFERENCES meetup(id) ON DELETE CASCADE,
    UNIQUE(meetup_id, image_data)
);

CREATE TABLE participate (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    meetup_id INTEGER NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    sended_at TIMESTAMP WITH TIME ZONE NOT NULL,
    acepted_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE NO ACTION,
    FOREIGN KEY (meetup_id) REFERENCES meetup(id) ON DELETE NO ACTION,
    UNIQUE(account_id, meetup_id)
);

CREATE TABLE management (
    id SERIAL PRIMARY KEY,
    participate_id INTEGER NOT NULL,
    managment_at TIMESTAMP WITH TIME ZONE NOT NULL, --CHANGED (DATE)
    type_action VARCHAR(200) NOT NULL
        CHECK (type_action in ('create', 'modify', 'delete', 'add_participant', 'remove_participant')
    ), --CHANGED
    UNIQUE(participate_id, managment_at)
);

CREATE TABLE expanses (
    id SERIAL PRIMARY KEY,
    meetup_id INTEGER NOT NULL,
    cost NUMERIC(7, 2) NOT NULL, --CHANGED
    about TEXT NOT NULL,
    FOREIGN KEY (meetup_id) REFERENCES meetup(id) ON DELETE CASCADE,
    UNIQUE(meetup_id, cost, about)
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    expanse_id INTEGER NOT NULL,
    paid_at TIMESTAMP WITH TIME ZONE NOT NULL, --CHANGED
    value_pay NUMERIC(7, 2) NOT NULL, --CHANGED
    about VARCHAR(100) NOT NULL,
    type_payment VARCHAR(200) NOT NULL
        CHECK (type_payment in ('credit_card', 'debit_card', 'pix', 'cash')
    ), --CHANGED
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (expanse_id) REFERENCES expanses(id) ON DELETE CASCADE,
    UNIQUE(account_id, expanse_id)
);

COMMIT;