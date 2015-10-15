--
-- Table structure for table `resource`
--

CREATE TABLE IF NOT EXISTS `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `resourceName` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jeo0yrchon5lix9ypg6vnh0si` (`resourceName`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;



--
-- Table structure for table `permission`
--

CREATE TABLE IF NOT EXISTS `permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `permissionName` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q6i0u274rak3u1e7cq4q1mtoe` (`permissionName`),
  KEY `FK_l6eonguenqgqw1els6hxffesf` (`resource_id`),
  CONSTRAINT `FK_l6eonguenqgqw1els6hxffesf` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=latin1;



--
-- Table structure for table `resource_permission`
--

CREATE TABLE IF NOT EXISTS `resource_permission` (
  `resource_id` bigint(20) NOT NULL,
  `permissions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`resource_id`,`permissions_id`),
  UNIQUE KEY `UK_59urcn2rl8co0fjy2ha32ywb5` (`permissions_id`),
  CONSTRAINT `FK_lgkthvak7tvqwry7nvb5cqjug` FOREIGN KEY (`resource_id`) REFERENCES `resource` (`id`),
  CONSTRAINT `FK_59urcn2rl8co0fjy2ha32ywb5` FOREIGN KEY (`permissions_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bgeqjb5opmijvwc14fbtaj4xx` (`roleName`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;



--
-- Table structure for table `permission_role`
--

CREATE TABLE IF NOT EXISTS `permission_role` (
  `permission_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`permission_id`,`roles_id`),
  KEY `FK_lsapggcn0w1ri5s2lksvf62ul` (`roles_id`),
  CONSTRAINT `FK_t6bkjjjna8rd1j9u839oodgxf` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FK_lsapggcn0w1ri5s2lksvf62ul` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `role_permission`
--

CREATE TABLE IF NOT EXISTS `role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permissions_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`permissions_id`),
  KEY `FK_bd67q67cmk1xk23k1wagx1h72` (`permissions_id`),
  CONSTRAINT `FK_j89g87bvih4d6jbxjcssrybks` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_bd67q67cmk1xk23k1wagx1h72` FOREIGN KEY (`permissions_id`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `ccGroup`
--

CREATE TABLE IF NOT EXISTS `ccGroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `groupName` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_na3en0yepqax1ye9d56ctubfq` (`groupName`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;



--
-- Table structure for table `ccGroup_role`
--

CREATE TABLE IF NOT EXISTS `ccGroup_role` (
  `ccGroup_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ccGroup_id`,`roles_id`),
  KEY `FK_qb91nmn00h6718i23wrqa7in9` (`roles_id`),
  CONSTRAINT `FK_hp19qkddnpsjsii7a0o1l73b9` FOREIGN KEY (`ccGroup_id`) REFERENCES `ccGroup` (`id`),
  CONSTRAINT `FK_qb91nmn00h6718i23wrqa7in9` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `role_ccGroup`
--

CREATE TABLE IF NOT EXISTS `role_ccGroup` (
  `role_id` bigint(20) NOT NULL,
  `groups_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`groups_id`),
  KEY `FK_t3svt8w2h68wxn0v5ei0flp8` (`groups_id`),
  CONSTRAINT `FK_pporr8406806c976njp09ahvw` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_t3svt8w2h68wxn0v5ei0flp8` FOREIGN KEY (`groups_id`) REFERENCES `ccGroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userEmail` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `passwordSalt` tinyblob,
  `phone` varchar(255) DEFAULT NULL,
  `userName` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_clwfrrius9hco0e47payxn3di` (`userEmail`,`userName`),
  UNIQUE KEY `UK_3l8gb2p80bem5h71r6qt7wbem` (`userEmail`),
  UNIQUE KEY `UK_4bakctviobmdk6ddh2nwg08c2` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;



--
-- Table structure for table `role_user`
--

CREATE TABLE IF NOT EXISTS `role_user` (
  `role_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`users_id`),
  KEY `FK_kfajhki6vd9okapq5eov6tk9c` (`users_id`),
  CONSTRAINT `FK_1p4td69hdcg4iaau4pvj20m3h` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_kfajhki6vd9okapq5eov6tk9c` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `user_role`
--

CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  KEY `FK_5k3dviices5fr7560hvc81x4r` (`roles_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_5k3dviices5fr7560hvc81x4r` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `ccGroup_user`
--

CREATE TABLE IF NOT EXISTS `ccGroup_user` (
  `ccGroup_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL,
  PRIMARY KEY (`ccGroup_id`,`users_id`),
  KEY `FK_lb285h22wn74phyp3f42x4dca` (`users_id`),
  CONSTRAINT `FK_5a47d2g7imd7xsbbxx4xqug19` FOREIGN KEY (`ccGroup_id`) REFERENCES `ccGroup` (`id`),
  CONSTRAINT `FK_lb285h22wn74phyp3f42x4dca` FOREIGN KEY (`users_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `user_ccGroup`
--

CREATE TABLE IF NOT EXISTS `user_ccGroup` (
  `user_id` bigint(20) NOT NULL,
  `groups_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`groups_id`),
  KEY `FK_rdtpu60s8qt2df926d8v36c0` (`groups_id`),
  CONSTRAINT `FK_ax25vw9yc4061nqscplqv2if3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_rdtpu60s8qt2df926d8v36c0` FOREIGN KEY (`groups_id`) REFERENCES `ccGroup` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `preference`
--

CREATE TABLE IF NOT EXISTS `preference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pkey` varchar(255) DEFAULT NULL,
  `pvalue` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ro87rogww8hoobbwya2nn16xk` (`user_id`),
  CONSTRAINT `FK_ro87rogww8hoobbwya2nn16xk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;



--
-- Table structure for table `user_preference`
--

CREATE TABLE IF NOT EXISTS `user_preference` (
  `user_id` bigint(20) NOT NULL,
  `preferences_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`preferences_id`),
  UNIQUE KEY `UK_1hsmbum1i2qmgt7mtyvjk6vr4` (`preferences_id`),
  CONSTRAINT `FK_s5oeayykfc7bpkpdwyrffwcqx` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_1hsmbum1i2qmgt7mtyvjk6vr4` FOREIGN KEY (`preferences_id`) REFERENCES `preference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `uxpermission`
--

CREATE TABLE IF NOT EXISTS `uxpermission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permissionName` varchar(255) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_l681vgaghr2xm7mdis5tq5vox` (`permissionName`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;