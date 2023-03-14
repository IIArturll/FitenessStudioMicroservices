INSERT INTO fitness.user_role(role)
VALUES ('ADMIN'), ('USER');

INSERT INTO fitness.user_status(status)
VALUES ('WAITING_ACTIVATION'),('ACTIVATED'),('DEACTIVATED');

INSERT INTO fitness.user(id,dt_create,dt_update,mail,fio,role,status,password)
VALUES('7d4dd98e-2d65-43d3-9380-a7cb30b29ec2','2023-03-09 17:56:06.413495+03','2023-03-09 18:04:45.66735+03',
	'sharam.artur@mail.ru','shara artur eduard',1,2,'$2a$10$9IophEK9gnXq42e43ul8qe59MiKFAWqJSNHKpAztQ60qE2mlKuGCm');