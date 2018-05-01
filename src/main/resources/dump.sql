DROP DATABASE IF EXISTS cars_db;
CREATE DATABASE cars_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE cars_db;

DROP TABLE IF EXISTS `cars`;
create table `cars` (
  `id`    serial       not null,
  `name`  varchar(100) not null,
  
  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb4 COLLATE utf8mb4_unicode_ci;

insert into cars (name) values ("Renault");
insert into cars (name) values ("VW");
insert into cars (name) values ("Mercedes");