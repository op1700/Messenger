SELECT * FROM users;
insert into users(user_code, user_name,user_id,user_pw,user_birth,user_phone,user_email,user_nick)
values(seq_users.nextval,'미노','jin','123','884589','010-5439-2109','jin@naver.com','jin');

SELECT * FROM users_list;

INSERT INTO users_list(user_code,TYPE,friend)
values(6, '친구', 8);

select DISTINCT users.user_code, users.user_name, users.user_nick, users_list.type
from users, users_list where 
(users.user_code in (select users_list.friend from users_list where user_code = 5));

select users.user_code, users.user_name, users.user_nick, USERS_LIST.TYPE
from users
INNER JOIN USERS_LIST ON USERS_LIST.FRIEND=users.USER_CODE;



select users.user_code, users.user_name, users.user_nick, users_list.type
from users inner join users_list 
where (users.user_code in 
   (select users_list.friend 
   from users_list 
   where user_code = 5));
  
  //가족을 가져오고
select users.user_code, users.user_name, users.user_nick, users_list.type
from users, users_list
where (users.user_code in
(select users_list.friend
from users_list
where user_code = 5));


select users.user_code, users.user_name, users.user_nick, USERS_LIST.TYPE
from users
INNER JOIN USERS_LIST ON USERS_LIST.FRIEND=users.USER_CODE;

select users.user_code, users.user_name, users.user_nick, users_list.type
FROM users
INNER JOIN users_list ON users_list.friend=
(SELECT users.user_code FROM users WHERE users.USER_NICK='wind');

select distinct users.user_code, users.user_name, users.user_nick, users_list.type
from users, users_list
where (users.user_code in 
(select users_list.friend
from users_list
where user_code = 5));

select  u.user_code, u.user_name, u.user_nick, li.TYPE
FROM users u,users_list li 
WHERE u.user_code=li.FRIEND
//AND u.USER_CODE=5;


select distinct users.user_code, users.user_name, users.user_nick, users_list.type
from users, users_list
where (users.user_code in 
       (select users_list.friend
        from users_list
        where user_code = 5))
ORDER BY users.user_name;


SELECT u.user_code, u.user_name, u.user_nick, li.type
FROM users u, (SELECT USERS_LIST.USER_CODE, USERS_LIST.TYPE, USERS_LIST.FRIEND FROM USERS_LIST WHERE users_list.USER_CODE=5) li
WHERE u.USER_CODE=li.friend;

5번의 친구 목록을 불러옴
SELECT USERS_LIST.USER_CODE, USERS_LIST.TYPE, USERS_LIST.FRIEND
FROM USERS_LIST
WHERE users_list.USER_CODE=5;

DESC user_tables;
SELECT * FROM user_tables;



select users.user_code, users.user_name, users.user_nick, users_list.type
from users
join users_list
on users.user_code = users_list.friend
where users_list.user_code = 5
ORDER BY users.user_name;

