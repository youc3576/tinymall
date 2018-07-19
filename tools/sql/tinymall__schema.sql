drop database if exists tinymall;
drop user if exists 'tinymall'@'localhost';
create database tinymall;
use tinymall;
create user 'tinymall'@'localhost' identified by 'tinymall123456';
grant all privileges on tinymall.* to 'tinymall'@'localhost';
flush privileges;