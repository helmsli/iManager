
--创建sequence，设置起始值
--1、stat.security_user createtime 未给予创建时间默认值


drop table if exists stat.security_user;
DROP SEQUENCE stat.security_user;
CREATE SEQUENCE stat.security_user START WITH 2;
create table stat.security_user
(
   id                   UNSIGNED_INT not null primary key,
   department_id        UNSIGNED_INT ,
   username             varchar(64) ,
   firstname            varchar(64),
   lastname             varchar(64),
   password             varchar(64) ,
   salt                 varchar(32),
   email                varchar(128) ,
   phone                varchar(32) ,
   createtime           timestamp  ,
   birthday             timestamp ,
   address              varchar(256) ,
   cardno               varchar(32) ,
   cardtype             UNSIGNED_INT ,
   sex                  UNSIGNED_INT ,
   imgurl               varchar(128) ,
   status               UNSIGNED_INT  ,
   isreset              UNSIGNED_INT ,
   isDisabled           UNSIGNED_INT ,
   remark               varchar(128)
);
UPSERT INTO stat.security_user VALUES (1, NULL, 'admin', 'super', 'man', '4edcbccdeef0a129378b7b26ee4277aed7d02f12', '9a085c170a25eef7', '121212@foxmail.com', '1121',    '2016-10-19 10:59:26', '2016-11-4 00:00:00', '北京市海淀区中关村软件园7号楼', '11111111', 0, 0, NULL, 0, 0, 0,NULL );






drop table if exists stat.security_department;
DROP SEQUENCE stat.security_department;
CREATE SEQUENCE stat.security_department START WITH 2;
create table stat.security_department
(
   id                   UNSIGNED_INT not null primary key,
   parent_id            UNSIGNED_INT ,
   name                 varchar(128) ,
   description          varchar(128) ,
   priority             UNSIGNED_INT ,
   remark               varchar(128) 
);
UPSERT into stat.security_department(id,name) values(1,'顶级部门');






drop table if exists stat.security_role;
DROP SEQUENCE stat.security_role;
CREATE SEQUENCE stat.security_role START WITH 3;
create table stat.security_role
(
   id                   UNSIGNED_INT not null primary key,
   name                 varchar(32) ,
   description          varchar(128) ,
   parent_id            UNSIGNED_INT 
);
UPSERT INTO stat.security_role VALUES (1, 'administrator', NULL, NULL);
UPSERT INTO stat.security_role VALUES (2, 'customer', NULL, NULL);






drop table if exists stat.security_user_role;
DROP SEQUENCE stat.security_user_role;
CREATE SEQUENCE stat.security_user_role START WITH 2;
create table stat.security_user_role
(
   id                   UNSIGNED_INT not null primary key,
   user_id              UNSIGNED_INT,
   role_id              UNSIGNED_INT 
);
UPSERT INTO stat.security_user_role VALUES (1, 1, 1);







drop table if exists stat.security_menu;
DROP SEQUENCE stat.security_menu;
CREATE SEQUENCE stat.security_menu START WITH 56;
create table stat.security_menu
(
   id                   UNSIGNED_INT not null primary key,
   parent_id            UNSIGNED_INT ,
   name                 varchar(128) ,
   action               varchar(128) ,
   imgurl               varchar(128) ,
   description          varchar(128) ,
   priority             UNSIGNED_INT ,
   sn                  varchar(64) ,
   remark               varchar(128) 
);

UPSERT INTO stat.security_menu VALUES (3, NULL, 'MENU_USER_CENTER', '#', 'fa-user', '用户中心', 8, 'User:main', NULL);
UPSERT INTO stat.security_menu VALUES (4, 3, 'MENU_USER_INFO', '/views/userCenter/userInfo.html', '/', '用户资料', 4, 'UserInfo:main', NULL);
UPSERT INTO stat.security_menu VALUES (5, 3, 'MENU_MODIFY_PWD', '/views/userCenter/password_modify.html', '/', '修改密码', 5, 'UserPassword:main', NULL);
UPSERT INTO stat.security_menu VALUES (6, 3, 'MENU_USER_MS', '/views/userCenter/userManage.html', '/', '用户管理', 6, 'UserManage:main', NULL);
UPSERT INTO stat.security_menu VALUES (7, 3, 'MENU_ROLE_MS', '/views/userCenter/roleManage.html', '/', '角色管理', 7, 'Role:main', NULL);
UPSERT INTO stat.security_menu VALUES (10, 50, 'MENU_DEMO_DEFAULT', '/views/demo/home/home.html', NULL, '默认首页', 10, 'Demo:default', NULL);
UPSERT INTO stat.security_menu VALUES (11, 50, 'MENU_DEMO_PLIST', '/views/demo/home/product_list.html', NULL, '产品列表', 11, 'Demo:plist', NULL);
UPSERT INTO stat.security_menu VALUES (12, 50, 'MENU_DEMO_NEWS', '/views/demo/home/news_detail.html', NULL, '新闻列表', 12, 'Demo:news', NULL);
UPSERT INTO stat.security_menu VALUES (13, 50, 'MENU_DEMO_404', '/views/demo/home/404_error.html', NULL, '404页面', 13, 'Demo:404', NULL);
UPSERT INTO stat.security_menu VALUES (14, 50, 'MENU_DEMO_500', '/views/demo/home/500_error.html', NULL, '500页面', 14, 'Demo:500', NULL);
UPSERT INTO stat.security_menu VALUES (15, NULL, 'MENU_DEMO_FORM', '#', 'fa-pencil-square-o', '表单', 3, 'Demo:form', NULL);
UPSERT INTO stat.security_menu VALUES (16, 15, 'MENU_DEMO_FORM1', '/views/demo/form/form.html', NULL, '表单1', 16, 'Demo:form1', NULL);
UPSERT INTO stat.security_menu VALUES (17, 15, 'MENU_DEMO_FORM2', '/views/demo/form/form_2.html', NULL, '表单2', 17, 'Demo:form2', NULL);
UPSERT INTO stat.security_menu VALUES (18, 15, 'MENU_DEMO_FORM3', '/views/demo/form/formSubmitDemo.html', NULL, '表单3', 18, 'Demo:form3', NULL);
UPSERT INTO stat.security_menu VALUES (19, NULL, 'MENU_DEMO_TABLE', '#', 'fa-table', '表格', 4, 'Demo:table', NULL);
UPSERT INTO stat.security_menu VALUES (20, 19, 'MENU_DEMO_TABLE1', '/views/demo/table/table.html', NULL, '表格1', 20, 'Demo:table1', NULL);
UPSERT INTO stat.security_menu VALUES (21, 19, 'MENU_DEMO_TABLE2', '/views/demo/table/table_2.html', NULL, '表格2', 21, 'Demo:table2', NULL);
UPSERT INTO stat.security_menu VALUES (22, 19, 'MENU_DEMO_EDITTABLE', '/views/demo/table/table_edit.html', NULL, '编辑表格', 22, 'Demo:edittable', NULL);
UPSERT INTO stat.security_menu VALUES (23, 19, 'MENU_DEMO_RULETABLE', '/views/demo/table/table_rule.html', NULL, '规则表格', 23, 'Demo:ruleTable', NULL);
UPSERT INTO stat.security_menu VALUES (24, NULL, 'MENU_DEMO_TREE', '/views/demo/tree/tree.html', 'fa-sitemap', '树', 5, 'Demo:tree', NULL);
UPSERT INTO stat.security_menu VALUES (25, NULL, 'MENU_DEMO_STEP', '#', ' fa-hourglass-half', '步骤', 6, 'Demo:step', NULL);
UPSERT INTO stat.security_menu VALUES (26, 25, 'MENU_DEMO_STEP1', '/views/demo/step/step.html', NULL, '步骤1', 26, 'Demo:step1', NULL);
UPSERT INTO stat.security_menu VALUES (27, 25, 'MENU_DEMO_STEP2', '/views/demo/step/step_2.html', NULL, '步骤2', 27, 'Demo:step2', NULL);
UPSERT INTO stat.security_menu VALUES (28, 25, 'MENU_DEMO_STEP3', '/views/demo/step/step_3.html', NULL, '步骤3', 28, 'Demo:step3', NULL);
UPSERT INTO stat.security_menu VALUES (29, 25, 'MENU_DEMO_STEP4', '/views/demo/step/step_4.html', NULL, '步骤4', 29, 'Demo:step4', NULL);
UPSERT INTO stat.security_menu VALUES (30, 25, 'MENU_DEMO_STEP5', '/views/demo/step/step_5.html', NULL, '步骤5', 30, 'Demo:step5', NULL);
UPSERT INTO stat.security_menu VALUES (31, NULL, 'MENU_DEMO_UI', '#', 'fa-user', 'ui', 7, 'Demo:ui', NULL);
UPSERT INTO stat.security_menu VALUES (32, 31, 'MENU_DEMO_ALERT', '/views/demo/ui/alerts.html', NULL, '警告框', 32, 'Demo:alert', NULL);
UPSERT INTO stat.security_menu VALUES (33, 31, 'MENU_DEMO_BREAD', '/views/demo/ui/breadcrumbs.html', NULL, '面包屑', 33, 'Demo:bread', NULL);
UPSERT INTO stat.security_menu VALUES (34, 31, 'MENU_DEMO_BUTTON', '/views/demo/ui/buttons.html', NULL, '按钮', 34, 'Demo:button', NULL);
UPSERT INTO stat.security_menu VALUES (35, 31, 'MENU_DEMO_BADGES', '/views/demo/ui/badges.html', NULL, '徽章', 34, 'Demo:badges', NULL);
UPSERT INTO stat.security_menu VALUES (36, 31, 'MENU_DEMO_BLANK', '/views/demo/ui/blank_page.html', NULL, '空白页', 36, 'Demo:blank', NULL);
UPSERT INTO stat.security_menu VALUES (37, 31, 'MENU_DEMO_COLORS', '/views/demo/ui/colors.html', NULL, '颜色', 37, 'Demo:colors', NULL);
UPSERT INTO stat.security_menu VALUES (38, 31, 'MENU_DEMO_COLLAPSE', '/views/demo/ui/collapse.html', NULL, '折叠', 38, 'Demo:collapse', NULL);
UPSERT INTO stat.security_menu VALUES (39, 31, 'MENU_DEMO_DIALOGS', '/views/demo/ui/dialogs.html', NULL, '对话框', 37, 'Demo:dialogs', NULL);
UPSERT INTO stat.security_menu VALUES (40, 31, 'MENU_DEMO_ICONS', '/views/demo/ui/icons.html', NULL, 'icons', 40, 'Demo:icons', NULL);
UPSERT INTO stat.security_menu VALUES (41, 31, 'MENU_DEMO_ELEMENT', '/views/demo/ui/form_elemens.html', NULL, 'element', 41, 'Demo:element', NULL);
UPSERT INTO stat.security_menu VALUES (42, 31, 'MENU_DEMO_MEDIA', '/views/demo/ui/media_object.html', NULL, '媒体', 42, 'Demo:media', NULL);
UPSERT INTO stat.security_menu VALUES (43, 31, 'MENU_DEMO_MODAL', '/views/demo/ui/modal.html', NULL, '模态框', 43, 'Demo:modal', NULL);
UPSERT INTO stat.security_menu VALUES (44, 31, 'MENU_DEMO_NOTIFICATION', '/views/demo/ui/notifications.html', NULL, '通知框', 44, 'Demo:notification', NULL);
UPSERT INTO stat.security_menu VALUES (45, 31, 'MENU_DEMO_PRELOADER', '/views/demo/ui/preloader.html', NULL, '加载动画', 45, 'Demo:preloader', NULL);
UPSERT INTO stat.security_menu VALUES (46, 31, 'MENU_DEMO_PROGRESSBAR', '/views/demo/ui/progressbars.html', NULL, '进度条', 46, 'Demo:progressbar', NULL);
UPSERT INTO stat.security_menu VALUES (47, 31, 'MENU_DEMO_TABS', '/views/demo/ui/tabs.html', NULL, '选项卡', 47, 'Demo:tabs', NULL);
UPSERT INTO stat.security_menu VALUES (48, 31, 'MENU_DEMO_TOOLTIPS', '/views/demo/ui/tooltips_popovers.html', NULL, '工具提示', 48, 'Demo:tooltips', NULL);
UPSERT INTO stat.security_menu VALUES (49, 31, 'MENU_DEMO_THUMBNAILS', '/views/demo/ui/thumbnails.html', NULL, '缩略图', 49, 'Demo:thumbnails', NULL);
UPSERT INTO stat.security_menu VALUES (50, NULL, 'MENU_DEMO_HOME', '#', 'fa-home', 'DEMO主页', 2, 'Demo:home', NULL);
UPSERT INTO stat.security_menu VALUES (53, 31, 'MENU_DEMO_TABLE_ELE', '/views/demo/ui/table_elements.html', NULL, '表格元素', 53, 'Demo:tableEle', NULL);
UPSERT INTO stat.security_menu VALUES (54, 31, 'MENU_DEMO_PAGE', '/views/demo/ui/pagination.html', NULL, '分页', 54, 'Demo:page', NULL);
UPSERT INTO stat.security_menu VALUES (55, 31, 'MENU_DEMO_OTHER', '/views/demo/ui/others.html', NULL, '其他', 55, 'Demo:other', NULL);








drop table if exists stat.security_function;
DROP SEQUENCE stat.security_function;
CREATE SEQUENCE stat.security_function START WITH 21;
create table stat.security_function
(
   id                   UNSIGNED_INT not null primary key,
   menu_id              UNSIGNED_INT ,
   name                 varchar(128) ,
   action               varchar(128) ,
   description          varchar(128) ,
   priority             UNSIGNED_INT ,
   sn                   varchar(64)  ,
   remark               varchar(128) 
);
UPSERT INTO stat.security_function VALUES (1, 6, 'addUser', '/management/user/create', '新增用户', 2, 'UserManage:add', '');
UPSERT INTO stat.security_function VALUES (2, 8, 'editBtn', '/', '修改按钮', 2, 'Dialog:edit', NULL);
UPSERT INTO stat.security_function VALUES (3, 8, 'deleteBtn', '/', '删除按钮', 3, 'Dialog:delete', NULL);
UPSERT INTO stat.security_function VALUES (4, 8, 'queryBtn', '/', '查询按钮', 4, 'Dialog:query', NULL);
UPSERT INTO stat.security_function VALUES (5, 2, 'prUNSIGNED_INTBtn', '/', '打印按钮', 5, 'Nav:prUNSIGNED_INT', NULL);
UPSERT INTO stat.security_function VALUES (6, 2, 'exportBtn', '/', '导出按钮', 6, 'Nav:export', NULL);
UPSERT INTO stat.security_function VALUES (7, 4, 'updateUserInfo', '/management/user/updateSelf', '修改用户信息', 2, 'UserInfo:update', NULL);
UPSERT INTO stat.security_function VALUES (8, 5, 'updatePassword', '/management/user/updatePassword', '修改密码', 1, 'UserPassword:save', NULL);
UPSERT INTO stat.security_function VALUES (9, 6, 'queryUser', '/management/user/list', '查询用户', 1, 'UserManage:query', NULL);
UPSERT INTO stat.security_function VALUES (10, 6, 'updateUser', '/management/user/update', '修改用户', 3, 'UserManage:update', NULL);
UPSERT INTO stat.security_function VALUES (11, 6, 'disableUser', '/management/user/updateDisabled', '禁用用户', 4, 'UserManage:disable', NULL);
UPSERT INTO stat.security_function VALUES (12, 6, 'deleteUser', '/management/user/delete', '删除用户', 5, 'UserManage:delete', NULL);
UPSERT INTO stat.security_function VALUES (13, 6, 'resetPassword', '/management/user/resetPassword', '重置密码', 6, 'UserManage:resetpassword', NULL);
UPSERT INTO stat.security_function VALUES (14, 7, 'addRole', '/management/role/create', '新增角色', 2, 'Role:add', NULL);
UPSERT INTO stat.security_function VALUES (15, 7, 'queryRole', '/management/role/list', '查询角色', 1, 'Role:query', NULL);
UPSERT INTO stat.security_function VALUES (16, 7, 'updateRole', '/management/role/update', '修改角色', 3, 'Role:update', NULL);
UPSERT INTO stat.security_function VALUES (17, 7, 'assignRolePermission', '/management/role/updateInfo', '配置角色权限', 4, 'Role:assignpermission', NULL);
UPSERT INTO stat.security_function VALUES (18, 7, 'addRoleUser', '/management/role/addUser', '添加用户', 5, 'Role:adduser', NULL);
UPSERT INTO stat.security_function VALUES (19, 7, 'removeRoleUser', '/management/role/removeUser', '移除用户', 6, 'Role:removeuser', NULL);
UPSERT INTO stat.security_function VALUES (20, 7, 'deleteRole', '/management/role/delete', '删除角色', 7, 'Role:delete', NULL);






drop table if exists stat.security_role_permission;
DROP SEQUENCE stat.security_role_permission;
CREATE SEQUENCE stat.security_role_permission START WITH 350;
create table stat.security_role_permission
(
   id                   UNSIGNED_INT not null primary key,
   role_id              UNSIGNED_INT ,
   resource_id          UNSIGNED_INT ,
   resource_type        UNSIGNED_INT 
);
UPSERT INTO stat.security_role_permission VALUES (285, 1, 50, 0);
UPSERT INTO stat.security_role_permission VALUES (286, 1, 10, 0);
UPSERT INTO stat.security_role_permission VALUES (287, 1, 11, 0);
UPSERT INTO stat.security_role_permission VALUES (288, 1, 12, 0);
UPSERT INTO stat.security_role_permission VALUES (289, 1, 13, 0);
UPSERT INTO stat.security_role_permission VALUES (290, 1, 14, 0);
UPSERT INTO stat.security_role_permission VALUES (291, 1, 15, 0);
UPSERT INTO stat.security_role_permission VALUES (292, 1, 16, 0);
UPSERT INTO stat.security_role_permission VALUES (293, 1, 17, 0);
UPSERT INTO stat.security_role_permission VALUES (294, 1, 18, 0);
UPSERT INTO stat.security_role_permission VALUES (295, 1, 19, 0);
UPSERT INTO stat.security_role_permission VALUES (296, 1, 20, 0);
UPSERT INTO stat.security_role_permission VALUES (297, 1, 21, 0);
UPSERT INTO stat.security_role_permission VALUES (298, 1, 22, 0);
UPSERT INTO stat.security_role_permission VALUES (299, 1, 23, 0);
UPSERT INTO stat.security_role_permission VALUES (300, 1, 24, 0);
UPSERT INTO stat.security_role_permission VALUES (301, 1, 25, 0);
UPSERT INTO stat.security_role_permission VALUES (302, 1, 26, 0);
UPSERT INTO stat.security_role_permission VALUES (303, 1, 27, 0);
UPSERT INTO stat.security_role_permission VALUES (304, 1, 28, 0);
UPSERT INTO stat.security_role_permission VALUES (305, 1, 29, 0);
UPSERT INTO stat.security_role_permission VALUES (306, 1, 30, 0);
UPSERT INTO stat.security_role_permission VALUES (307, 1, 31, 0);
UPSERT INTO stat.security_role_permission VALUES (308, 1, 32, 0);
UPSERT INTO stat.security_role_permission VALUES (309, 1, 33, 0);
UPSERT INTO stat.security_role_permission VALUES (310, 1, 34, 0);
UPSERT INTO stat.security_role_permission VALUES (311, 1, 35, 0);
UPSERT INTO stat.security_role_permission VALUES (312, 1, 36, 0);
UPSERT INTO stat.security_role_permission VALUES (313, 1, 37, 0);
UPSERT INTO stat.security_role_permission VALUES (314, 1, 39, 0);
UPSERT INTO stat.security_role_permission VALUES (315, 1, 38, 0);
UPSERT INTO stat.security_role_permission VALUES (316, 1, 40, 0);
UPSERT INTO stat.security_role_permission VALUES (317, 1, 41, 0);
UPSERT INTO stat.security_role_permission VALUES (318, 1, 42, 0);
UPSERT INTO stat.security_role_permission VALUES (319, 1, 43, 0);
UPSERT INTO stat.security_role_permission VALUES (320, 1, 44, 0);
UPSERT INTO stat.security_role_permission VALUES (321, 1, 45, 0);
UPSERT INTO stat.security_role_permission VALUES (322, 1, 46, 0);
UPSERT INTO stat.security_role_permission VALUES (323, 1, 47, 0);
UPSERT INTO stat.security_role_permission VALUES (324, 1, 48, 0);
UPSERT INTO stat.security_role_permission VALUES (325, 1, 49, 0);
UPSERT INTO stat.security_role_permission VALUES (326, 1, 53, 0);
UPSERT INTO stat.security_role_permission VALUES (327, 1, 54, 0);
UPSERT INTO stat.security_role_permission VALUES (328, 1, 55, 0);
UPSERT INTO stat.security_role_permission VALUES (329, 1, 3, 0);
UPSERT INTO stat.security_role_permission VALUES (330, 1, 4, 0);
UPSERT INTO stat.security_role_permission VALUES (331, 1, 5, 0);
UPSERT INTO stat.security_role_permission VALUES (332, 1, 6, 0);
UPSERT INTO stat.security_role_permission VALUES (333, 1, 7, 0);
UPSERT INTO stat.security_role_permission VALUES (334, 1, 7, 1);
UPSERT INTO stat.security_role_permission VALUES (335, 1, 8, 1);
UPSERT INTO stat.security_role_permission VALUES (336, 1, 9, 1);
UPSERT INTO stat.security_role_permission VALUES (337, 1, 1, 1);
UPSERT INTO stat.security_role_permission VALUES (338, 1, 10, 1);
UPSERT INTO stat.security_role_permission VALUES (339, 1, 11, 1);
UPSERT INTO stat.security_role_permission VALUES (340, 1, 12, 1);
UPSERT INTO stat.security_role_permission VALUES (341, 1, 13, 1);
UPSERT INTO stat.security_role_permission VALUES (342, 1, 15, 1);
UPSERT INTO stat.security_role_permission VALUES (343, 1, 14, 1);
UPSERT INTO stat.security_role_permission VALUES (344, 1, 16, 1);
UPSERT INTO stat.security_role_permission VALUES (345, 1, 17, 1);
UPSERT INTO stat.security_role_permission VALUES (346, 1, 18, 1);
UPSERT INTO stat.security_role_permission VALUES (347, 1, 19, 1);
UPSERT INTO stat.security_role_permission VALUES (348, 1, 20, 1);







drop table if exists stat.business;
DROP SEQUENCE stat.business;
CREATE SEQUENCE stat.business START WITH 1;
create table stat.business
(
   id                   UNSIGNED_INT not null primary key ,
   no                   varchar(64) ,
   type                 varchar(64) ,
   department_id        UNSIGNED_INT ,
   dataId               varchar(64),
   dataGroupId          varchar(64) ,
   createTime           timestamp ,
   createUser           UNSIGNED_INT ,
   updateTime           timestamp ,
   updateUser           UNSIGNED_INT ,
   data1                varchar(256) ,
   data2                varchar(256) ,
   data3                varchar(256) ,
   remark               varchar(128) ,
   queryKey             varchar(64) 
);


commit;