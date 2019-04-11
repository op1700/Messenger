SELECT * FROM cart;
SELECT * FROM MEMBER;
INSERT INTO member(member_id,name,id,password)
values(member_seq.nextval,"�ڿ츲","cool","1234");
SELECT * FROM *_seq;

SELECT p.product_id AS product_id, img, product_name, price,m.MEMBER_ID
FROM cart c, MEMBER m, product p
WHERE c.MEMBER_ID=m.MEMBER_ID
AND c.PRODUCT_ID=p.PRODUCT_ID
AND m.MEMBER_ID=1;