--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
INSERT IGNORE INTO `resource` (description, resourceName, version) VALUES
    ('CC security resource','ccSecResource',1),
    ('CC security permission','ccSecPermission',1),
    ('CC security role','ccSecRole',1),
    ('CC security group','ccSecGroup',1),
    ('CC security user','ccSecUser',1);
UNLOCK TABLES;



--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE,`resource` WRITE;
INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can display CC security resource', 'ccSecResource:display', 1, id FROM resource WHERE resourceName='ccSecResource';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can create CC security resource', 'ccSecResource:create', 1, id FROM resource WHERE resourceName='ccSecResource';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can remove CC security resource', 'ccSecResource:remove', 1, id FROM resource WHERE resourceName='ccSecResource';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can update CC security resource', 'ccSecResource:update', 1, id FROM resource WHERE resourceName='ccSecResource';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can display CC security permission', 'ccSecPermission:display', 1, id FROM resource WHERE resourceName='ccSecPermission';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can create CC security permission', 'ccSecPermission:create', 1, id FROM resource WHERE resourceName='ccSecPermission';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can remove CC security permission', 'ccSecPermission:remove', 1, id FROM resource WHERE resourceName='ccSecPermission';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can update CC security permission', 'ccSecPermission:update', 1, id FROM resource WHERE resourceName='ccSecPermission';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can display CC security role', 'ccSecRole:display', 1, id FROM resource WHERE resourceName='ccSecRole';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can create CC security role', 'ccSecRole:create', 1, id FROM resource WHERE resourceName='ccSecRole';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can remove CC security role', 'ccSecRole:remove', 1, id FROM resource WHERE resourceName='ccSecRole';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can update CC security role', 'ccSecRole:update', 1, id FROM resource WHERE resourceName='ccSecRole';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can display CC security group', 'ccSecGroup:display', 1, id FROM resource WHERE resourceName='ccSecGroup';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can create CC security group', 'ccSecGroup:create', 1, id FROM resource WHERE resourceName='ccSecGroup';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can remove CC security group', 'ccSecGroup:remove', 1, id FROM resource WHERE resourceName='ccSecGroup';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can update CC security group', 'ccSecGroup:update', 1, id FROM resource WHERE resourceName='ccSecGroup';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can display CC security user', 'ccSecUser:display', 1, id FROM resource WHERE resourceName='ccSecUser';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can create CC security user', 'ccSecUser:create', 1, id FROM resource WHERE resourceName='ccSecUser';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can remove CC security user', 'ccSecUser:remove', 1, id FROM resource WHERE resourceName='ccSecUser';

INSERT IGNORE INTO `permission` (description, permissionName, version, resource_id)
SELECT 'can update CC security user', 'ccSecUser:update', 1, id FROM resource WHERE resourceName='ccSecUser';
UNLOCK TABLES;



--
-- Dumping data for table `resource_permission`
--

LOCK TABLES `resource_permission` WRITE,`permission` AS p WRITE,`resource` AS r WRITE ;
INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecResource' AND p.permissionName='ccSecResource:display';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecResource' AND p.permissionName='ccSecResource:create';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecResource' AND p.permissionName='ccSecResource:remove';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecResource' AND p.permissionName='ccSecResource:update';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecPermission' AND p.permissionName='ccSecPermission:display';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecPermission' AND p.permissionName='ccSecPermission:create';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecPermission' AND p.permissionName='ccSecPermission:remove';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecPermission' AND p.permissionName='ccSecPermission:update';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecRole' AND p.permissionName='ccSecRole:display';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecRole' AND p.permissionName='ccSecRole:create';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecRole' AND p.permissionName='ccSecRole:remove';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecRole' AND p.permissionName='ccSecRole:update';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecGroup' AND p.permissionName='ccSecGroup:display';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecGroup' AND p.permissionName='ccSecGroup:create';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecGroup' AND p.permissionName='ccSecGroup:remove';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecGroup' AND p.permissionName='ccSecGroup:update';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecUser' AND p.permissionName='ccSecUser:display';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecUser' AND p.permissionName='ccSecUser:create';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecUser' AND p.permissionName='ccSecUser:remove';

INSERT IGNORE INTO `resource_permission` (resource_id, permissions_id)
SELECT r.id, p.id FROM resource AS r, permission AS p WHERE r.resourceName='ccSecUser' AND p.permissionName='ccSecUser:update';

UNLOCK TABLES;



--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
INSERT IGNORE INTO `role` (description, roleName, version) VALUES
    ('May the force be with you','Jedi',1),
    ('CC security administrator role','ccsecadmin',1),
    ('CC security reviewer role','ccsecreviewer',1);
UNLOCK TABLES;




--
-- Dumping data for table `permission_role`
--

LOCK TABLES `permission_role` WRITE,`permission` AS p WRITE,`role` AS r WRITE;
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:update' AND r.roleName='Jedi';

INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:update' AND r.roleName='Jedi';

INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:create' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:remove' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:update' AND r.roleName='ccsecadmin';

INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:create' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:remove' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:update' AND r.roleName='ccsecadmin';

INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:create' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:remove' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `permission_role` (permission_id, roles_id)
SELECT p.id, r.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:update' AND r.roleName='ccsecadmin';

UNLOCK TABLES;



--
-- Dumping data for table `role_permission`
--

LOCK TABLES `role_permission` WRITE,`permission` AS p WRITE,`role` AS r WRITE;

INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:update' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:display' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:create' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:remove' AND r.roleName='Jedi';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:update' AND r.roleName='Jedi';


INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:create' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:remove' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:update' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:create' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:remove' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:update' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:display' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:create' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:remove' AND r.roleName='ccsecadmin';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:update' AND r.roleName='ccsecadmin';


INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecResource:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecPermission:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecRole:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecGroup:display' AND r.roleName='ccsecreviewer';
INSERT IGNORE INTO `role_permission` (role_id, permissions_id)
SELECT r.id, p.id FROM permission AS p, role AS r WHERE p.permissionName='ccSecUser:display' AND r.roleName='ccsecreviewer';

UNLOCK TABLES;
