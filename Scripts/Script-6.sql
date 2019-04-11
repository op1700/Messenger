SELECT * FROM USERS
SELECT * FROM USERS_LIST ORDER BY USER_CODE

SELECT * FROM CHAT_LOGS ORDER BY  time

SELECT * FROM CHAT_ROOM ORDER BY CHAT_CODE

SELECT * FROM CHAT_MEMBERS ORDER BY CHAT_CODE

delete from users_list where user_code =5 and friend = 7

insert INTO USERS_LIST(user_code,TYPE,FRIEND)
VALUES(5, '가족', 7)

select distinct users.user_code, users.user_name, users.user_nick, users_list.type, users.user_img, users.user_phone, users.user_email, users.user_birth  
from users join users_list on users.user_code = users_list.friend where users_list.user_code = 
5 ORDER BY users.user_name


select * from chat_logs where CHAT_CODE=7 ORDER BY time desc
(time in (select MAX(time) from chat_logs where chat_code =2));

INSERT INTO CHAT_LOGS(chat_code,USER_CODE,CHAT_LOG,time)
values(7, 8, '으 짱나영', '17:55')

INSERT INTO USERS(user_code,USER_NAME,USER_ID,USER_PW,USER_BIRTH,USER_PHONE,USER_EMAIL,USER_NICK,user_img)
values(seq_users.nextval,'강아지', 'dog', 123, 57934, 3483294, 'djdew@dewji.com' , 'dogman', '8.jpg')


select distinct users.user_code, users.user_name, users.user_nick, users_list.type, users.user_img, users.user_phone, users.user_email, users.user_birth  
from users join users_list on users.user_code = users_list.friend where users_list.user_code =5 
 ORDER BY users.user_name