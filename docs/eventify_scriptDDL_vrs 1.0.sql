drop database if exists eventify;
create database eventify;

\c eventify;

begin;

-- verification code is 6 but we are using 100 to make it more secure (hashed)
CREATE TABLE account ( 
    id SERIAL PRIMARY KEY,
    username VARCHAR(150) NOT NULL, 
    email VARCHAR(200) NOT NULL UNIQUE,
    hashed_password VARCHAR(1024) NOT NULL,  
    image_data BYTEA,  
    nickname VARCHAR(100) NOT NULL UNIQUE,  
    verification_code VARCHAR(100) NOT NULL,  
    code_valid_until TIMESTAMP WITH TIME ZONE NOT NULL,  
    is_verified BOOLEAN DEFAULT FALSE  
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


CREATE TABLE meetup (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL, 
    information TEXT NOT NULL, (DESCRIPTION)
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    cep_address VARCHAR(9), 
    state_address VARCHAR(200), 
    city_address VARCHAR(200), 
    neighborhood_address VARCHAR(200), 
    number_address VARCHAR(200), 
    street_address VARCHAR(200), 

    date_start TIMESTAMP WITH TIME ZONE NOT NULL, 
    date_end TIMESTAMP WITH TIME ZONE NOT NULL, 
    stage VARCHAR(200) NOT NULL
        CHECK (stage in ('created', 'started', 'finished', 'canceled')
    ),
    pix_key VARCHAR(200),
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
    role_participant VARCHAR(200) NOT NULL
        CHECK (role_participant in ('organizer', 'participant')
    ), 
    active BOOLEAN DEFAULT FALSE,
    sended_at TIMESTAMP WITH TIME ZONE NOT NULL,
    acepted_at TIMESTAMP WITH TIME ZONE,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE NO ACTION,
    FOREIGN KEY (meetup_id) REFERENCES meetup(id) ON DELETE NO ACTION,
    UNIQUE(account_id, meetup_id)
);

CREATE TABLE management (
    id SERIAL PRIMARY KEY,
    participate_id INTEGER NOT NULL,
    managment_at TIMESTAMP WITH TIME ZONE NOT NULL,  (DATE)
    type_action VARCHAR(200) NOT NULL
        CHECK (type_action in ('create', 'modify', 'delete', 'add_participant', 'remove_participant')
    ), 
    UNIQUE(participate_id, managment_at)
);

CREATE TABLE expanses (
    id SERIAL PRIMARY KEY,
    meetup_id INTEGER NOT NULL,
    cost NUMERIC(7, 2) NOT NULL, 
    about TEXT NOT NULL,
    FOREIGN KEY (meetup_id) REFERENCES meetup(id) ON DELETE CASCADE,
    UNIQUE(meetup_id, cost, about)
);

CREATE TABLE payment (
    id SERIAL PRIMARY KEY,
    account_id INTEGER NOT NULL,
    expanse_id INTEGER NOT NULL,
    paid_at TIMESTAMP WITH TIME ZONE NOT NULL, 
    value_pay NUMERIC(7, 2) NOT NULL, 
    about VARCHAR(100) NOT NULL,
    type_payment VARCHAR(200) NOT NULL
        CHECK (type_payment in ('credit_card', 'debit_card', 'pix', 'cash')
    ), 
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (expanse_id) REFERENCES expanses(id) ON DELETE CASCADE,
    UNIQUE(account_id, expanse_id)
);

COMMIT;