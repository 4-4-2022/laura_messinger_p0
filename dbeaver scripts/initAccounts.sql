DROP TABLE IF EXISTS accounts CASCADE;
CREATE TABLE IF NOT EXISTS accounts(
	account_uid SERIAL PRIMARY KEY,
	account_isEmployee BOOLEAN NOT NULL,
	account_isAdmin BOOLEAN NOT NULL
);

INSERT INTO accounts VALUES
(DEFAULT, TRUE, TRUE),
(DEFAULT, TRUE, FALSE),
(DEFAULT, FALSE, FALSE);

DROP TABLE IF EXISTS credentials;
CREATE TABLE IF NOT EXISTS credentials(
	account_uid SERIAL PRIMARY KEY REFERENCES accounts(account_uid),
	account_email TEXT NOT NULL,
	account_username TEXT UNIQUE NOT NULL,
	account_password TEXT NOT NULL
);

INSERT INTO credentials VALUES
(DEFAULT, 'admin@arcade.com', 'admin', 'password'),
(DEFAULT, 'employee@arcade.com', 'employee', 'password'),
(DEFAULT, 'customer@gmail.com', 'customer', 'password');

DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users(
	user_uid SERIAL PRIMARY KEY,
	account_uid INTEGER NOT NULL REFERENCES accounts(account_uid),
	user_nickname TEXT NOT NULL,
	user_isOwner BOOLEAN NOT NULL,
	UNIQUE (account_uid, user_nickname)
);

INSERT INTO users VALUES
(DEFAULT, 3, 'Mom', TRUE),
(DEFAULT, 3, 'Jimmy', FALSE),
(DEFAULT, 3, 'Susie', FALSE);

DROP TABLE IF EXISTS addresses;
CREATE TABLE IF NOT EXISTS addresses(
	account_uid INTEGER UNIQUE NOT NULL REFERENCES accounts(account_uid),
	account_street TEXT,
	account_city TEXT,
	account_state TEXT,
	account_zip TEXT,
	account_country TEXT
);

INSERT INTO addresses VALUES
(3, '123 Street Road', 'City', 'CA', '90210', 'USA');

DROP TABLE IF EXISTS balances;
CREATE TABLE IF NOT EXISTS balances(
	account_uid INTEGER UNIQUE NOT NULL REFERENCES accounts(account_uid),
	account_wallet MONEY NOT NULL,
	account_tickets INTEGER NOT NULL
);

INSERT INTO balances VALUES
(3, 50.00, 0);

DROP TABLE IF EXISTS employeeInfo;
CREATE TABLE IF NOT EXISTS employeeInfo(
	account_uid INTEGER UNIQUE NOT NULL REFERENCES accounts(account_uid),
	employee_name TEXT NOT NULL
);

INSERT INTO employeeInfo VALUES 
(1, 'Admin Peter'),
(2, 'Employee Joe');

SELECT * FROM accounts;