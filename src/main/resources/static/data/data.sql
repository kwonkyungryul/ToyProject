INSERT INTO USERS(created_date, modified_date, username, password, role, status)
VALUES
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', 'yoon', '1234', 'ACADEMY', 'ACTIVE'),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', 'yoon2', '1234', 'ACADEMY', 'ACTIVE');


INSERT INTO ACADEMIES(created_date, modified_date, full_name, academy_name, contact, phone, user_id, status)
VALUES
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', '권경렬', '윤선생아카데미석포초점', '051-111-1111', '010-1234-1234', 1, 'ACTIVE'),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', '조아라', '윤선생아카데미석포초2호점', '051-222-2222', '010-2222-2222', 2, 'ACTIVE');