SELECT * FROM USERs;
select distinct users.user_code, users.user_name, users.user_nick, users_list.type 
from users join users_list on users.user_code = users_list.friend 
where users_list.user_code = 5
ORDER BY users.user_name;

SELECT * FROM USERS_LIST
select * from user_sequences
SELECT * FROM CHAT_MEMBERS ORDER BY CHAT_CODE, USER_CODE
SELECT * FROM CHAT_ROOM ORDER BY CHAT_CODE
DELETE FROM CHAT_ROOM
INSERT INTO CHAT_ROOM

SELECT CHAT_CODE, chat_name
FROM CHAT_ROOM
join CHAT_CODE on
(SELECT chat_code
FROM CHAT_MEMBERS mem
WHERE mem.USER_CODE=5 ORDER BY CHAT_CODE)

내 채팅 목록 가져오는거
select chat_room.chat_code, chat_room.chat_name from chat_room join chat_members on chat_room.chat_code = chat_members.chat_code where chat_members.user_code = 5 ORDER BY CHAT_CODE

SELECT USER_CODE FROM CHAT_MEMBERS mem WHERE mem.CHAT_CODE=1


VALUES (seq_chat.nextval, 'Test1')

INSERT INTO CHAT_ROOM
VALUES (seq_chat.nextval, 'Test2')

INSERT INTO CHAT_ROOM
VALUES (seq_chat.nextval, 'Test3')

INSERT INTO CHAT_ROOM(chat_code,chat_name)
VALUES (seq_chat.nextval, 'Test4')

INSERT INTO CHAT_ROOM
VALUES (seq_chat.nextval,)
select seq_chat.currval from dual


INSERT INTO CHAT_MEMBERS(CHAT_CODE, USER_CODE)
VALUES(seq_chat.currval, 5)
INSERT INTO CHAT_MEMBERS(CHAT_CODE, USER_CODE)
VALUES(seq_chat.currval, 9)


SELECT USER_NAME FROM USERS WHERE USER_CODE = 5
SELECT USER_NAME FROM USERS WHERE USER_CODE = 10

SELECT
		CHAT_CODE,
		USER_CODE,
		CASE WHEN USER_CODE IN (5, 9) THEN 1 ELSE 0 END
	FROM CHAT_MEMBERS
	ORDER BY CHAT_CODE, USER_CODE

SELECT * FROM CHAT_LOGS
SELECT * FROM CHAT_MEMBERS ORDER BY CHAT_CODE, USER_CODE

SELECT CHAT_CODE FROM(SELECT CHAT_CODE, SUM(CASE WHEN USER_CODE IN (5, 11) THEN 1 ELSE 0 END) AS TargetCnt FROM CHAT_MEMBERS GROUP BY CHAT_CODE HAVING COUNT(*)  = 2) Tbl WHERE Tbl.TargetCnt = 2

INSERT INTO CHAT_ROOM(chat_code,CHAT_NAME)


SELECT CHAT_CODE FROM CHAT_MEMBERS
WHERE CHAT_CODE = 
SELECT CHAT_CODE 
FROM CHAT_MEMBERS 
GROUP BY CHAT_CODE HAVING count(*) = 2

INSERT INTO CHAT_MEMBERS
VALUES(1, 8)

INSERT INTO CHAT_MEMBERS
VALUES(2, 9)

INSERT INTO CHAT_MEMBERS
VALUES(3, 10)

INSERT INTO CHAT_MEMBERS
VALUES(4, 8)

INSERT INTO CHAT_MEMBERS
VALUES(5, 9)

DELETE FROM CHAT_MEMBERS

select distinct users.user_code, users.user_name, users.user_nick, users_list.type 
from users join users_list on users.user_code = users_list.friend 
where users_list.user_code = 5 
ORDER BY users.user_name;

SELECT * FROM USERS_LIST;