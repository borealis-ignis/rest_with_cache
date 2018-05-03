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


DROP TABLE IF EXISTS `users`;
create table `users` (
  `id` serial not null,
  `username` varchar(50) not null unique,
  `password` varchar(255) not null,
  `enabled`	tinyint(2) not null,
  
  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `authorities`;
create table `authorities` (
  `id` serial not null,
  `authority` varchar(50) NOT NULL UNIQUE,
  
  primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb4 COLLATE utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `users_to_authorities`;
create table `users_to_authorities` (
  `user_id` bigint unsigned not null,
  `authority_id` bigint unsigned not null,
  primary key (`user_id`, `authority_id`),
  foreign key (`user_id`) references `users`(`id`) on delete cascade,
  foreign key (`authority_id`) references `authorities`(`id`) on delete cascade
) ENGINE=InnoDB DEFAULT CHARACTER SET=utf8mb4 COLLATE utf8mb4_unicode_ci;

insert into `authorities` (`authority`) values ('ADMIN');
SET @admin_role_id = LAST_INSERT_ID();
insert into `authorities` (`authority`) values ('USER');
SET @user_role_id = LAST_INSERT_ID();

insert into `users` (`username`, `password`, `enabled`) values ('admin', '$2a$10$8sPyQF01PmdX7zAqu1fRre0IMueH6lDuzSystqSYAOaLZ0TCSEME2', 1);
SET @admin_user_id = LAST_INSERT_ID();
insert into `users` (`username`, `password`, `enabled`) values ('user', '$2a$10$gV.Xeo1PJXNHwxphigBSreT/wZtq0pdBCtRxdGO5ucCWRHYWeHHIC', 1);
SET @user_user_id = LAST_INSERT_ID();

insert into `users_to_authorities` (`user_id`, `authority_id`) values
(@admin_user_id, @admin_role_id),
(@admin_user_id, @user_role_id),
(@user_user_id, @user_role_id);
