--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
INSERT INTO `resource` VALUES
    (2,'CC security resource','ccSecResource',6),
    (3,'CC security permission','ccSecPermission',6),
    (4,'CC security role','ccSecRole',6),
    (5,'CC security group','ccSecGroup',6),
    (6,'CC security user','ccSecUser',6);
UNLOCK TABLES;



--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
INSERT INTO `permission` VALUES
    (3,'can display CC security resource','ccSecResource:display',6,2),
    (4,'can create CC security resource','ccSecResource:create',6,2),
    (5,'can remove CC security resource','ccSecResource:remove',5,2),
    (6,'can update CC security resource','ccSecResource:update',5,2),
    (7,'can display CC security permission','ccSecPermission:display',6,3),
    (8,'can create CC security permission','ccSecPermission:create',5,3),
    (9,'can remove CC security permission','ccSecPermission:remove',5,3),
    (10,'can update CC security permission','ccSecPermission:update',3,3),
    (11,'can display CC security role','ccSecRole:display',3,4),
    (12,'can create CC security role','ccSecRole:create',2,4),
    (13,'can remove CC security role','ccSecRole:remove',2,4),
    (14,'can update CC security role','ccSecRole:update',2,4),
    (15,'can display CC security group','ccSecGroup:display',3,5),
    (16,'can create CC security group','ccSecGroup:create',2,5),
    (17,'can remove CC security group','ccSecGroup:remove',2,5),
    (18,'can update CC security group','ccSecGroup:update',2,5),
    (19,'can display CC security user','ccSecUser:display',4,6),
    (20,'can create CC security user','ccSecUser:create',2,6),
    (21,'can remove CC security user','ccSecUser:remove',2,6),
    (22,'can update CC security user','ccSecUser:update',2,6);
UNLOCK TABLES;



--
-- Dumping data for table `resource_permission`
--

LOCK TABLES `resource_permission` WRITE;
INSERT INTO `resource_permission` VALUES
    (2,3),(2,4),(2,5),(2,6),
    (3,7),(3,8),(3,9),(3,10),
    (4,11),(4,12),(4,13),(4,14),
    (5,15),(5,16),(5,17),(5,18),
    (6,19),(6,20),(6,21),(6,22);
UNLOCK TABLES;



--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES
    (1,'May the force be with you','Jedi',1),
    (4,'CC security administrator role','ccsecadmin',31),
    (10,'CC security reviewer role','ccsecreviewer',3);
UNLOCK TABLES;




--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE;
INSERT INTO `permission_role` VALUES
    (3,4),(3,10),(3,1),
    (4,1),
    (5,1),
    (6,1),
    (7,4),(7,10),(7,1),
    (8,1),
    (9,1),
    (10,1),
    (11,4),(11,10),(11,1),
    (12,4),(12,1),
    (13,4),(13,1),
    (14,4),(14,1),
    (15,4),(15,10),(15,1),
    (16,4),(16,1),
    (17,4),(17,1),
    (18,4),(18,1),
    (19,4),(19,10),(19,1),
    (20,4),(20,1),
    (21,4),(21,1),
    (22,4),(22,1);
UNLOCK TABLES;



--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE;
INSERT INTO `role_permission` VALUES
    (1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(1,17),(1,18),
    (1,19),(1,20),(1,21),(1,22),
    (4,3),(4,7),(4,11),(4,12),(4,13),(4,14),(4,15),(4,16),(4,17),(4,18),(4,19),(4,20),(4,21),(4,22),
    (10,3),(10,7),(10,11),(10,15),(10,19);
UNLOCK TABLES;



--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES (1,'yoda@spectral.com','Yoda','ZeOne','UdozaWTVHt237A9JX4yhFeAjgZO5XP0dS98JeRXH6B+VH4tgLMmi7QMiQM4VwVxEBozNbxNkGMt+GN0A/QEgpw==','sG´\n<97>;<ÛL®Þ^\M ¬ª','09938748','yoda',1);
UNLOCK TABLES;


LOCK TABLES `role_user` WRITE;
INSERT INTO `role_user` VALUES (1,1);
UNLOCK TABLES;
