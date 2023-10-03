INSERT INTO USERS(created_date, modified_date, username, password, role, status)
VALUES
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', 'yoon', '$2a$10$aMjDZYj.qOC8MpMtNBA7o.d69GUfDFtuVPpooW6il6Z9S2gb1oLuW', 'ACADEMY', 'ACTIVE'),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', 'yoon2', '$2a$10$aMjDZYj.qOC8MpMtNBA7o.d69GUfDFtuVPpooW6il6Z9S2gb1oLuW', 'ACADEMY', 'ACTIVE'),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', 'khh', '$2a$10$aMjDZYj.qOC8MpMtNBA7o.d69GUfDFtuVPpooW6il6Z9S2gb1oLuW', 'PARENTS', 'ACTIVE'),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', 'pjh', '$2a$10$aMjDZYj.qOC8MpMtNBA7o.d69GUfDFtuVPpooW6il6Z9S2gb1oLuW', 'PARENTS', 'ACTIVE');


INSERT INTO ACADEMIES(created_date, modified_date, full_name, academy_name, contact, phone, user_id, code, status)
VALUES
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', '권경렬', '윤선생아카데미석포초점', '051-111-1111', '010-1234-1234', 1, '4B2A-7F38-E923', 'ACTIVE'),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', '조아라', '윤선생아카데미석포초2호점', '051-222-2222', '010-2222-2222', 2, 'D041-A8EC-4026', 'ACTIVE');


INSERT INTO PARENTS(created_date, modified_date, full_name, contact, user_id)
VALUES
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', '김호현', '010-1234-1234', 3),
    ('2023-09-20 22:48:00', '2023-09-20 22:48:00', '박진협', '010-2222-2222', 4);