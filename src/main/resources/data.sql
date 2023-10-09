INSERT INTO user_details(id, birth_date, name)
VALUES (10001, current_date()-5000, 'Michael Slone');

INSERT INTO user_details(id, birth_date, name)
VALUES (10002, current_date()-3000, 'Joe Malone');

INSERT INTO user_details(id, birth_date, name)
VALUES (10003, current_date()-8000, 'Judy Jackson');

INSERT INTO post(id, description, l_usr_id)
VALUES (20001, 'I want to learn AWS.', 10001);

INSERT INTO post(id, description, l_usr_id)
VALUES (20002, 'What a brilliant idea.', 10001);

INSERT INTO post(id, description, l_usr_id)
VALUES (20003, 'This is awesome!', 10002);

INSERT INTO post(id, description, l_usr_id)
VALUES (20004, 'What a great something...', 10003);
