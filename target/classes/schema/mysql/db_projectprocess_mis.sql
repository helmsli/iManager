CREATE DATABASE db_projectprocess_mis DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use security;

drop table if exists security_user;
create table security_user
(
   id                   int not null auto_increment,
   department_id        int comment '部门id',
   username            varchar(64) comment '登录名',
   firstname            varchar(64),
   lastname             varchar(64),
   password             varchar(64) not null comment '密码',
   salt                 varchar(32) not null comment '盐值',
   email                varchar(128) comment '邮箱',
   phone                varchar(32) comment '电话',
   createtime           timestamp default CURRENT_TIMESTAMP comment '创建时间',
   birthday              timestamp comment '生日',
   address              varchar(256) comment '地址',
   cardno               varchar(32) comment '证件号',
   cardtype             int comment '证件类型',
   sex                  int comment '性别（0：男  1：女）',
   imgurl               varchar(128) comment '图片路径',
   status               int not null comment '状态(0:有效 1：无效)',
   isreset              int comment '是否是重置状态（0未重置 1已重置）',
   isDisabled           int comment '是否禁用（0：未禁用  1：禁用）',
   remark               varchar(128) comment '扩展信息',
   primary key (id)
);
alter table security_user comment '用户表';
-- ----------------------------
-- Records of security_user
-- ----------------------------
INSERT INTO `security_user` VALUES ('1', null, 'admin', 'super', 'man', '4edcbccdeef0a129378b7b26ee4277aed7d02f12', '9a085c170a25eef7', '121212@foxmail.com', '1121', '2016-10-19 10:59:26', '2016-11-04 00:00:00', '北京市海淀区中关村软件园7号楼', '11111111', '0', '0', null, '0', '0', '0', null);
INSERT INTO `security_user` VALUES ('3', null, 'projectManager', 'projectManagerA', '', '3caaa394c80bf51c28ddfaf1b8929b0d977522e5', 'b0287eba6eaad6bb', '123456@163.com', '1365925678', '2016-12-27 21:27:10', '2016-12-27 21:19:19', '', '123456789123456789', '0', '0', '', '1', null, '0', null);
INSERT INTO `security_user` VALUES ('4', null, 'projectManagerA', 'projectManagerA', '', '58a373a29ec5608aabbe6b57485b3ebfd8a72e3e', '5620c44068ab462d', '123456@163.com', '13649312345', '2016-12-28 09:12:33', '2016-12-30 17:04:26', '', '123456789123456789', '0', '0', '', '0', null, '0', null);
INSERT INTO `security_user` VALUES ('5', null, 'offercerA', 'offercerA', '', '072e9ede600a34933bc76060bbaf8113ea90f196', '14f3753a2ee14d49', '123456@163.com', '13649312345', '2016-12-28 09:20:34', '2016-12-28 09:12:40', '', '123456789123456789', '0', '0', '', '0', null, '0', null);
INSERT INTO `security_user` VALUES ('6', null, 'committeeA', 'committeeA', '', '2f36fa7ea82b2d4ece354580d737a6983c5cf409', '42c6941e9ffecf4c', '123456@163.com', '12345678912', '2016-12-28 09:41:35', '2016-12-28 09:33:41', '', '123456789123456789', '0', '0', '', '0', null, '0', null);
INSERT INTO `security_user` VALUES ('7', null, 'departLeaderA', 'departLeaderA', '', 'bc6bf44d3f46cac1d541edfb20a71f4b642b961d', '4c8303f7ffba999b', '123456@163.com', '123456789', '2016-12-28 09:42:48', '2016-12-28 09:34:54', '', '123456789123456789', '0', '0', '', '0', null, '0', null);


create table security_department
(
   id                   int not null auto_increment comment 'id',
   parent_id            int comment '父id',
   name                 varchar(128) comment '部门名称',
   description          varchar(128) comment '描述',
   priority             int comment '优先级',
   remark               varchar(128) comment '扩展字段',
   primary key (id)
);
alter table security_department comment '部门表';
insert into security_department(id,name) values(1,'顶级部门');



drop table if exists security_role;
create table security_role
(
   id                   int not null auto_increment,
   name                 varchar(32) not null comment '角色名',
   description          varchar(128) comment '描述信息',
   parent_id            int comment '父id',
   primary key (id)
);
alter table security_role comment '角色表';
-- ----------------------------
-- Records of security_role
-- ----------------------------
INSERT INTO `security_role` VALUES ('1', 'administrator', '系统管理员', null);
INSERT INTO `security_role` VALUES ('14', 'projectManager', '项目经理', null);
INSERT INTO `security_role` VALUES ('15', 'officer', '部门管理人员', null);
INSERT INTO `security_role` VALUES ('16', 'departLeader', '部门领导', null);
INSERT INTO `security_role` VALUES ('17', 'committee', '决策委员会', null);




drop table if exists security_user_role;
create table security_user_role
(
   id                   int not null auto_increment,
   user_id              int not null comment '用户id',
   role_id              int not null comment '角色id',
   primary key (id)
);
alter table security_user_role comment '用户角色表';
-- ----------------------------
-- Records of security_user_role
-- ----------------------------
INSERT INTO `security_user_role` VALUES ('1', '1', '1');
INSERT INTO `security_user_role` VALUES ('5', '5', '15');
INSERT INTO `security_user_role` VALUES ('4', '4', '14');
INSERT INTO `security_user_role` VALUES ('6', '6', '17');
INSERT INTO `security_user_role` VALUES ('7', '7', '16');


drop table if exists security_menu;
create table security_menu
(
   id                   int not null auto_increment,
   parent_id            int comment '父id',
   name                 varchar(128) not null comment '国际化id',
   action               varchar(128) comment 'action',
   imgurl               varchar(128) comment '图标地址',
   description          varchar(128) comment '描述',
   priority             int comment '优先级',
   sn                  varchar(64) comment '菜单权限关键字',
   remark               varchar(128) comment '扩展字段',
   primary key (id)
);
alter table security_menu comment '菜单表';

INSERT INTO security_menu VALUES (3, NULL, 'MENU_USER_CENTER', '#', 'fa-user', '用户中心', 8, 'User:main', NULL);
INSERT INTO security_menu VALUES (4, 3, 'MENU_USER_INFO', '/views/userCenter/userInfo.html', '/', '用户资料', 4, 'UserInfo:main', NULL);
INSERT INTO security_menu VALUES (5, 3, 'MENU_MODIFY_PWD', '/views/userCenter/password_modify.html', '/', '修改密码', 5, 'UserPassword:main', NULL);
INSERT INTO security_menu VALUES (6, 3, 'MENU_USER_MS', '/views/userCenter/userManage.html', '/', '用户管理', 6, 'UserManage:main', NULL);
INSERT INTO security_menu VALUES (7, 3, 'MENU_ROLE_MS', '/views/userCenter/roleManage.html', '/', '角色管理', 7, 'Role:main', NULL);
INSERT INTO security_menu VALUES (10, 50, 'MENU_DEMO_DEFAULT', '/views/demo/home/home.html', NULL, '默认首页', 10, 'Demo:default', NULL);
INSERT INTO security_menu VALUES (11, 50, 'MENU_DEMO_PLIST', '/views/demo/home/product_list.html', NULL, '产品列表', 11, 'Demo:plist', NULL);
INSERT INTO security_menu VALUES (12, 50, 'MENU_DEMO_NEWS', '/views/demo/home/news_detail.html', NULL, '新闻列表', 12, 'Demo:news', NULL);
INSERT INTO security_menu VALUES (13, 50, 'MENU_DEMO_404', '/views/demo/home/404_error.html', NULL, '404页面', 13, 'Demo:404', NULL);
INSERT INTO security_menu VALUES (14, 50, 'MENU_DEMO_500', '/views/demo/home/500_error.html', NULL, '500页面', 14, 'Demo:500', NULL);
INSERT INTO security_menu VALUES (15, NULL, 'MENU_DEMO_FORM', '#', 'fa-pencil-square-o', '表单', 3, 'Demo:form', NULL);
INSERT INTO security_menu VALUES (16, 15, 'MENU_DEMO_FORM1', '/views/demo/form/form.html', NULL, '表单1', 16, 'Demo:form1', NULL);
INSERT INTO security_menu VALUES (17, 15, 'MENU_DEMO_FORM2', '/views/demo/form/form_2.html', NULL, '表单2', 17, 'Demo:form2', NULL);
INSERT INTO security_menu VALUES (18, 15, 'MENU_DEMO_FORM3', '/views/demo/form/formSubmitDemo.html', NULL, '表单3', 18, 'Demo:form3', NULL);
INSERT INTO security_menu VALUES (19, NULL, 'MENU_DEMO_TABLE', '#', 'fa-table', '表格', 4, 'Demo:table', NULL);
INSERT INTO security_menu VALUES (20, 19, 'MENU_DEMO_TABLE1', '/views/demo/table/table.html', NULL, '表格1', 20, 'Demo:table1', NULL);
INSERT INTO security_menu VALUES (21, 19, 'MENU_DEMO_TABLE2', '/views/demo/table/table_2.html', NULL, '表格2', 21, 'Demo:table2', NULL);
INSERT INTO security_menu VALUES (22, 19, 'MENU_DEMO_EDITTABLE', '/views/demo/table/table_edit.html', NULL, '编辑表格', 22, 'Demo:edittable', NULL);
INSERT INTO security_menu VALUES (23, 19, 'MENU_DEMO_RULETABLE', '/views/demo/table/table_rule.html', NULL, '规则表格', 23, 'Demo:ruleTable', NULL);
INSERT INTO security_menu VALUES (24, NULL, 'MENU_DEMO_TREE', '/views/demo/tree/tree.html', 'fa-sitemap', '树', 5, 'Demo:tree', NULL);
INSERT INTO security_menu VALUES (25, NULL, 'MENU_DEMO_STEP', '#', ' fa-hourglass-half', '步骤', 6, 'Demo:step', NULL);
INSERT INTO security_menu VALUES (26, 25, 'MENU_DEMO_STEP1', '/views/demo/step/step.html', NULL, '步骤1', 26, 'Demo:step1', NULL);
INSERT INTO security_menu VALUES (27, 25, 'MENU_DEMO_STEP2', '/views/demo/step/step_2.html', NULL, '步骤2', 27, 'Demo:step2', NULL);
INSERT INTO security_menu VALUES (28, 25, 'MENU_DEMO_STEP3', '/views/demo/step/step_3.html', NULL, '步骤3', 28, 'Demo:step3', NULL);
INSERT INTO security_menu VALUES (29, 25, 'MENU_DEMO_STEP4', '/views/demo/step/step_4.html', NULL, '步骤4', 29, 'Demo:step4', NULL);
INSERT INTO security_menu VALUES (30, 25, 'MENU_DEMO_STEP5', '/views/demo/step/step_5.html', NULL, '步骤5', 30, 'Demo:step5', NULL);
INSERT INTO security_menu VALUES (31, NULL, 'MENU_DEMO_UI', '#', 'fa-user', 'ui', 7, 'Demo:ui', NULL);
INSERT INTO security_menu VALUES (32, 31, 'MENU_DEMO_ALERT', '/views/demo/ui/alerts.html', NULL, '警告框', 32, 'Demo:alert', NULL);
INSERT INTO security_menu VALUES (33, 31, 'MENU_DEMO_BREAD', '/views/demo/ui/breadcrumbs.html', NULL, '面包屑', 33, 'Demo:bread', NULL);
INSERT INTO security_menu VALUES (34, 31, 'MENU_DEMO_BUTTON', '/views/demo/ui/buttons.html', NULL, '按钮', 34, 'Demo:button', NULL);
INSERT INTO security_menu VALUES (35, 31, 'MENU_DEMO_BADGES', '/views/demo/ui/badges.html', NULL, '徽章', 34, 'Demo:badges', NULL);
INSERT INTO security_menu VALUES (36, 31, 'MENU_DEMO_BLANK', '/views/demo/ui/blank_page.html', NULL, '空白页', 36, 'Demo:blank', NULL);
INSERT INTO security_menu VALUES (37, 31, 'MENU_DEMO_COLORS', '/views/demo/ui/colors.html', NULL, '颜色', 37, 'Demo:colors', NULL);
INSERT INTO security_menu VALUES (38, 31, 'MENU_DEMO_COLLAPSE', '/views/demo/ui/collapse.html', NULL, '折叠', 38, 'Demo:collapse', NULL);
INSERT INTO security_menu VALUES (39, 31, 'MENU_DEMO_DIALOGS', '/views/demo/ui/dialogs.html', NULL, '对话框', 37, 'Demo:dialogs', NULL);
INSERT INTO security_menu VALUES (40, 31, 'MENU_DEMO_ICONS', '/views/demo/ui/icons.html', NULL, 'icons', 40, 'Demo:icons', NULL);
INSERT INTO security_menu VALUES (41, 31, 'MENU_DEMO_ELEMENT', '/views/demo/ui/form_elemens.html', NULL, 'element', 41, 'Demo:element', NULL);
INSERT INTO security_menu VALUES (42, 31, 'MENU_DEMO_MEDIA', '/views/demo/ui/media_object.html', NULL, '媒体', 42, 'Demo:media', NULL);
INSERT INTO security_menu VALUES (43, 31, 'MENU_DEMO_MODAL', '/views/demo/ui/modal.html', NULL, '模态框', 43, 'Demo:modal', NULL);
INSERT INTO security_menu VALUES (44, 31, 'MENU_DEMO_NOTIFICATION', '/views/demo/ui/notifications.html', NULL, '通知框', 44, 'Demo:notification', NULL);
INSERT INTO security_menu VALUES (45, 31, 'MENU_DEMO_PRELOADER', '/views/demo/ui/preloader.html', NULL, '加载动画', 45, 'Demo:preloader', NULL);
INSERT INTO security_menu VALUES (46, 31, 'MENU_DEMO_PROGRESSBAR', '/views/demo/ui/progressbars.html', NULL, '进度条', 46, 'Demo:progressbar', NULL);
INSERT INTO security_menu VALUES (47, 31, 'MENU_DEMO_TABS', '/views/demo/ui/tabs.html', NULL, '选项卡', 47, 'Demo:tabs', NULL);
INSERT INTO security_menu VALUES (48, 31, 'MENU_DEMO_TOOLTIPS', '/views/demo/ui/tooltips_popovers.html', NULL, '工具提示', 48, 'Demo:tooltips', NULL);
INSERT INTO security_menu VALUES (49, 31, 'MENU_DEMO_THUMBNAILS', '/views/demo/ui/thumbnails.html', NULL, '缩略图', 49, 'Demo:thumbnails', NULL);
INSERT INTO security_menu VALUES (50, NULL, 'MENU_DEMO_HOME', '#', 'fa-home', 'DEMO主页', 2, 'Demo:home', NULL);
INSERT INTO security_menu VALUES (53, 31, 'MENU_DEMO_TABLE_ELE', '/views/demo/ui/table_elements.html', NULL, '表格元素', 53, 'Demo:tableEle', NULL);
INSERT INTO security_menu VALUES (54, 31, 'MENU_DEMO_PAGE', '/views/demo/ui/pagination.html', NULL, '分页', 54, 'Demo:page', NULL);
INSERT INTO security_menu VALUES (55, 31, 'MENU_DEMO_OTHER', '/views/demo/ui/others.html', NULL, '其他', 55, 'Demo:other', NULL);





drop table if exists security_function;
create table security_function
(
   id                   int not null auto_increment,
   menu_id              int comment '菜单id',
   name                 varchar(128) not null comment '国际化key',
   action               varchar(128) comment 'action',
   description          varchar(128) comment '描述',
   priority             int comment '优先级',
   sn                   varchar(64) comment '按钮权限关键字',
   remark               varchar(128) comment '扩展字段',
   primary key (id)
);
alter table security_function comment '按钮功能表';
INSERT INTO security_function VALUES (1, 6, 'addUser', '/management/user/create', '新增用户', 2, 'UserManage:add', '');
INSERT INTO security_function VALUES (2, 8, 'editBtn', '/', '修改按钮', 2, 'Dialog:edit', NULL);
INSERT INTO security_function VALUES (3, 8, 'deleteBtn', '/', '删除按钮', 3, 'Dialog:delete', NULL);
INSERT INTO security_function VALUES (4, 8, 'queryBtn', '/', '查询按钮', 4, 'Dialog:query', NULL);
INSERT INTO security_function VALUES (5, 2, 'printBtn', '/', '打印按钮', 5, 'Nav:print', NULL);
INSERT INTO security_function VALUES (6, 2, 'exportBtn', '/', '导出按钮', 6, 'Nav:export', NULL);
INSERT INTO security_function VALUES (7, 4, 'updateUserInfo', '/management/user/updateSelf', '修改用户信息', 2, 'UserInfo:update', NULL);
INSERT INTO security_function VALUES (8, 5, 'updatePassword', '/management/user/updatePassword', '修改密码', 1, 'UserPassword:save', NULL);
INSERT INTO security_function VALUES (9, 6, 'queryUser', '/management/user/list', '查询用户', 1, 'UserManage:query', NULL);
INSERT INTO security_function VALUES (10, 6, 'updateUser', '/management/user/update', '修改用户', 3, 'UserManage:update', NULL);
INSERT INTO security_function VALUES (11, 6, 'disableUser', '/management/user/updateDisabled', '禁用用户', 4, 'UserManage:disable', NULL);
INSERT INTO security_function VALUES (12, 6, 'deleteUser', '/management/user/delete', '删除用户', 5, 'UserManage:delete', NULL);
INSERT INTO security_function VALUES (13, 6, 'resetPassword', '/management/user/resetPassword', '重置密码', 6, 'UserManage:resetpassword', NULL);
INSERT INTO security_function VALUES (14, 7, 'addRole', '/management/role/create', '新增角色', 2, 'Role:add', NULL);
INSERT INTO security_function VALUES (15, 7, 'queryRole', '/management/role/list', '查询角色', 1, 'Role:query', NULL);
INSERT INTO security_function VALUES (16, 7, 'updateRole', '/management/role/update', '修改角色', 3, 'Role:update', NULL);
INSERT INTO security_function VALUES (17, 7, 'assignRolePermission', '/management/role/updateInfo', '配置角色权限', 4, 'Role:assignpermission', NULL);
INSERT INTO security_function VALUES (18, 7, 'addRoleUser', '/management/role/addUser', '添加用户', 5, 'Role:adduser', NULL);
INSERT INTO security_function VALUES (19, 7, 'removeRoleUser', '/management/role/removeUser', '移除用户', 6, 'Role:removeuser', NULL);
INSERT INTO security_function VALUES (20, 7, 'deleteRole', '/management/role/delete', '删除角色', 7, 'Role:delete', NULL);




drop table if exists security_role_permission;
create table security_role_permission
(
   id                   int not null auto_increment,
   role_id              int not null comment '角色id',
   resource_id          int not null comment '资源id',
   resource_type        int comment '资源类型（0 菜单  1按钮）',
   primary key (id)
);
alter table security_role_permission comment '角色权限表';


INSERT INTO security_role_permission VALUES (285, 1, 50, 0);
INSERT INTO security_role_permission VALUES (286, 1, 10, 0);
INSERT INTO security_role_permission VALUES (287, 1, 11, 0);
INSERT INTO security_role_permission VALUES (288, 1, 12, 0);
INSERT INTO security_role_permission VALUES (289, 1, 13, 0);
INSERT INTO security_role_permission VALUES (290, 1, 14, 0);
INSERT INTO security_role_permission VALUES (291, 1, 15, 0);
INSERT INTO security_role_permission VALUES (292, 1, 16, 0);
INSERT INTO security_role_permission VALUES (293, 1, 17, 0);
INSERT INTO security_role_permission VALUES (294, 1, 18, 0);
INSERT INTO security_role_permission VALUES (295, 1, 19, 0);
INSERT INTO security_role_permission VALUES (296, 1, 20, 0);
INSERT INTO security_role_permission VALUES (297, 1, 21, 0);
INSERT INTO security_role_permission VALUES (298, 1, 22, 0);
INSERT INTO security_role_permission VALUES (299, 1, 23, 0);
INSERT INTO security_role_permission VALUES (300, 1, 24, 0);
INSERT INTO security_role_permission VALUES (301, 1, 25, 0);
INSERT INTO security_role_permission VALUES (302, 1, 26, 0);
INSERT INTO security_role_permission VALUES (303, 1, 27, 0);
INSERT INTO security_role_permission VALUES (304, 1, 28, 0);
INSERT INTO security_role_permission VALUES (305, 1, 29, 0);
INSERT INTO security_role_permission VALUES (306, 1, 30, 0);
INSERT INTO security_role_permission VALUES (307, 1, 31, 0);
INSERT INTO security_role_permission VALUES (308, 1, 32, 0);
INSERT INTO security_role_permission VALUES (309, 1, 33, 0);
INSERT INTO security_role_permission VALUES (310, 1, 34, 0);
INSERT INTO security_role_permission VALUES (311, 1, 35, 0);
INSERT INTO security_role_permission VALUES (312, 1, 36, 0);
INSERT INTO security_role_permission VALUES (313, 1, 37, 0);
INSERT INTO security_role_permission VALUES (314, 1, 39, 0);
INSERT INTO security_role_permission VALUES (315, 1, 38, 0);
INSERT INTO security_role_permission VALUES (316, 1, 40, 0);
INSERT INTO security_role_permission VALUES (317, 1, 41, 0);
INSERT INTO security_role_permission VALUES (318, 1, 42, 0);
INSERT INTO security_role_permission VALUES (319, 1, 43, 0);
INSERT INTO security_role_permission VALUES (320, 1, 44, 0);
INSERT INTO security_role_permission VALUES (321, 1, 45, 0);
INSERT INTO security_role_permission VALUES (322, 1, 46, 0);
INSERT INTO security_role_permission VALUES (323, 1, 47, 0);
INSERT INTO security_role_permission VALUES (324, 1, 48, 0);
INSERT INTO security_role_permission VALUES (325, 1, 49, 0);
INSERT INTO security_role_permission VALUES (326, 1, 53, 0);
INSERT INTO security_role_permission VALUES (327, 1, 54, 0);
INSERT INTO security_role_permission VALUES (328, 1, 55, 0);
INSERT INTO security_role_permission VALUES (329, 1, 3, 0);
INSERT INTO security_role_permission VALUES (330, 1, 4, 0);
INSERT INTO security_role_permission VALUES (331, 1, 5, 0);
INSERT INTO security_role_permission VALUES (332, 1, 6, 0);
INSERT INTO security_role_permission VALUES (333, 1, 7, 0);
INSERT INTO security_role_permission VALUES (334, 1, 7, 1);
INSERT INTO security_role_permission VALUES (335, 1, 8, 1);
INSERT INTO security_role_permission VALUES (336, 1, 9, 1);
INSERT INTO security_role_permission VALUES (337, 1, 1, 1);
INSERT INTO security_role_permission VALUES (338, 1, 10, 1);
INSERT INTO security_role_permission VALUES (339, 1, 11, 1);
INSERT INTO security_role_permission VALUES (340, 1, 12, 1);
INSERT INTO security_role_permission VALUES (341, 1, 13, 1);
INSERT INTO security_role_permission VALUES (342, 1, 15, 1);
INSERT INTO security_role_permission VALUES (343, 1, 14, 1);
INSERT INTO security_role_permission VALUES (344, 1, 16, 1);
INSERT INTO security_role_permission VALUES (345, 1, 17, 1);
INSERT INTO security_role_permission VALUES (346, 1, 18, 1);
INSERT INTO security_role_permission VALUES (347, 1, 19, 1);
INSERT INTO security_role_permission VALUES (348, 1, 20, 1);





create table business
(
   id                   int not null auto_increment comment 'id',
   no                   varchar(64) comment '业务编号',
   type                 varchar(64) comment '业务类型',
   department_id        int comment '所属组织',
   dataId               varchar(64) comment '数据编号',
   dataGroupId          varchar(64) comment '数据分组编号',
   createTime           datetime comment '数据创建时间',
   createUser           int comment '数据创建操作员',
   updateTime           datetime comment '最新更新时间',
   updateUser           int comment '最新操作操作员',
   data1                text comment '数据1',
   data2                text comment '数据2',
   data3                text comment '数据3',
   remark               varchar(128) comment '备注',
   queryKey             varchar(64) comment '查询关键字',
   primary key (id)
);

alter table business comment '通用业务表';
commit;



-- ----------------------------
-- Table structure for tb_annex_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_annex_type`;
CREATE TABLE `tb_annex_type` (
  `type_id` bigint(20) NOT NULL,
  `type_name` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_annex_type
-- ----------------------------
INSERT INTO `tb_annex_type` VALUES ('1000', 'typeName2', 'description2');

-- ----------------------------
-- Table structure for tb_committee_approval
-- ----------------------------
DROP TABLE IF EXISTS `tb_committee_approval`;
CREATE TABLE `tb_committee_approval` (
  `record_id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `stage` varchar(255) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `conclusion` varchar(1024) DEFAULT NULL,
  `comments` varchar(1024) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_committee_approval
-- ----------------------------
INSERT INTO `tb_committee_approval` VALUES ('1000', '1000259', 'begin', '2016-12-20 00:00:00', 'notpass', null, '2000');
INSERT INTO `tb_committee_approval` VALUES ('3000', '1000259', 'middle', '2016-12-20 00:00:00', 'pass', null, '1000');
INSERT INTO `tb_committee_approval` VALUES ('4000', '1000259', null, '2016-12-20 00:00:00', 'pass', null, '1000');
INSERT INTO `tb_committee_approval` VALUES ('5000', '1000259', 'terminal', '2016-12-22 00:00:00', 'pass', null, '1000');

-- ----------------------------
-- Table structure for tb_departleader_approval
-- ----------------------------
DROP TABLE IF EXISTS `tb_departleader_approval`;
CREATE TABLE `tb_departleader_approval` (
  `record_id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `stage` varchar(255) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `conclusion` varchar(1024) DEFAULT NULL,
  `comments` varchar(1024) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_departleader_approval
-- ----------------------------
INSERT INTO `tb_departleader_approval` VALUES ('1000', '1000259', 'begin', '2016-12-20 00:00:00', 'pass', 'begin of departleder_appro', '1000');
INSERT INTO `tb_departleader_approval` VALUES ('2132', '1000259', 'middle', null, null, 'middle of departleder_appro', null);
INSERT INTO `tb_departleader_approval` VALUES ('3424', '1000259', 'terminal', null, null, 'terminal of departleder_appro', null);

-- ----------------------------
-- Table structure for tb_expert_review
-- ----------------------------
DROP TABLE IF EXISTS `tb_expert_review`;
CREATE TABLE `tb_expert_review` (
  `record_id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `stage` varchar(255) DEFAULT NULL,
  `time` timestamp NULL DEFAULT NULL,
  `expert_list` varchar(1024) DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `expert_score` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_expert_review
-- ----------------------------
INSERT INTO `tb_expert_review` VALUES ('1000', '1000259', 'begin', '2016-12-20 09:00:00', null, '2400', '89');
INSERT INTO `tb_expert_review` VALUES ('2000', '1000259', 'middle', '2016-12-20 10:00:00', null, '2603', '99');

-- ----------------------------
-- Table structure for tb_project
-- ----------------------------
DROP TABLE IF EXISTS `tb_project`;
CREATE TABLE `tb_project` (
  `project_id` bigint(20) NOT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `project_manager_id` bigint(20) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  `subcategory_id` bigint(20) DEFAULT NULL,
  `cycle_type` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `state_name` varchar(255) DEFAULT NULL,
  `project_task_id` varchar(255) DEFAULT NULL,
  `project_process_instance_id` varchar(100) DEFAULT NULL,
  `project_ext_info` varchar(2048) DEFAULT NULL,
  `project_task_detail` varchar(2048) DEFAULT NULL,
  `self_appraise` varchar(1024) DEFAULT NULL,
  `depart_leader_appraise` varchar(1024) DEFAULT NULL,
  `project_costs` varchar(1024) DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `complete_time` timestamp NULL DEFAULT NULL,
  `project_apply_time` timestamp NULL DEFAULT NULL,
  `telno` varchar(1024) DEFAULT NULL,
  `email` varchar(1024) DEFAULT NULL,
  `project_milestone` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_project
-- ----------------------------
INSERT INTO `tb_project` VALUES ('1000259', 'changguichun', '1', '1', null, '1', '001', '项目管理人员指定评审专家立项审批', '312509', '312505', '[{\"taskDescribe\":\"x概述项目希望解决的问题，以及计划通过何种方式达到什么目标？300字以内\",\"taskbackground\":\"x限1000字以内\",\"taskgoal\":\"x限100字以内\",\"taskDanger\":\"x1000字以内\",\"taskcreate\":\"x200字以内\",\"taskplan\":\"[{\\\"planTime\\\":\\\"x\\\",\\\"planContext\\\":\\\"x\\\",\\\"planOutPUT\\\":\\\"x\\\",\\\"planOutWord\\\":\\\"x\\\"}]\"}]', '[{\"postName\":\"x\",\"requireName\":\"x\"}]', null, null, '[{\"payName\":\"x\",\"payPrice\":\"x\",\"payNumber\":\"x\",\"paySbutotal\":\"x\",\"payRemarks\":\"3\"}]', '2016-12-29 00:00:00', '2017-12-29 00:00:00', '2016-12-29 20:09:47', '2', '2@163.com', '项目里程碑');
INSERT INTO `tb_project` VALUES ('1000260', 'chang', '1', '1', null, '1', '001', '项目管理人员指定评审专家立项审批', '312515', '312511', '[{\"taskDescribe\":\"c概述项目希望解决的问题，以及计划通过何种方式达到什么目标？300字以内\",\"taskbackground\":\"c限1000字以内\",\"taskgoal\":\"c限100字以内\",\"taskDanger\":\"c1000字以内\",\"taskcreate\":\"c200字以内\",\"taskplan\":\"[{\\\"planTime\\\":\\\"c\\\",\\\"planContext\\\":\\\"c\\\",\\\"planOutPUT\\\":\\\"c\\\",\\\"planOutWord\\\":\\\"c\\\"}]\"}]', '[{\"postName\":\"\",\"requireName\":\"\"}]', null, null, '[{\"payName\":\"\",\"payPrice\":\"\",\"payNumber\":\"\",\"paySbutotal\":\"\",\"payRemarks\":\"\"}]', '2016-12-29 00:00:00', '2017-12-29 00:00:00', '2016-12-29 20:16:37', '3', '3@163.com', '项目里程碑');
INSERT INTO `tb_project` VALUES ('1000263', '1111111111', null, '5', null, '1', '001', '项目管理人员指定评审专家立项审批', '345009', '345005', '[{\"taskDescribe\":\"q11\",\"taskbackground\":\"111111111111111\",\"taskgoal\":\"111111111\",\"taskDanger\":\"1111111111\",\"taskcreate\":\"111111111111\",\"taskplan\":\"[{}]\"}]', '[{\"postName\":\"44\",\"requireName\":\"4\"},{\"postName\":\"55\",\"requireName\":\"55\"}]', null, null, '[{\"payName\":\"222222222\",\"payPrice\":\"2\",\"payNumber\":\"2\",\"paySbutotal\":\"2\",\"payRemarks\":\"2\"},{\"payName\":\"3333\",\"payPrice\":\"3\",\"payNumber\":\"3\",\"paySbutotal\":\"3\",\"payRemarks\":\"3\"}]', '2016-12-30 00:00:00', '2017-12-30 00:00:00', '2016-12-30 17:17:41', '111', '11@qq.com<br>', '项目里程碑');
INSERT INTO `tb_project` VALUES ('1000264', '1111111111', null, '5', null, '1', '001', '项目管理人员指定评审专家立项审批', '345015', '345011', '[{\"taskDescribe\":\"q11\",\"taskbackground\":\"111111111111111\",\"taskgoal\":\"111111111\",\"taskDanger\":\"1111111111\",\"taskcreate\":\"111111111111\",\"taskplan\":\"[{}]\"}]', '[{\"postName\":\"\",\"requireName\":\"\"}]', null, null, '[{\"payName\":\"222222222\",\"payPrice\":\"2\",\"payNumber\":\"2\",\"paySbutotal\":\"2\",\"payRemarks\":\"2\"},{\"payName\":\"3333\",\"payPrice\":\"3\",\"payNumber\":\"3\",\"paySbutotal\":\"3\",\"payRemarks\":\"3\"}]', '2016-12-30 00:00:00', '2017-12-30 00:00:00', '2016-12-30 17:19:34', '111', '11@qq.com<br>', '项目里程碑');

-- ----------------------------
-- Table structure for tb_project_annex
-- ----------------------------
DROP TABLE IF EXISTS `tb_project_annex`;
CREATE TABLE `tb_project_annex` (
  `annex_id` bigint(20) NOT NULL,
  `annex_name` varchar(255) DEFAULT NULL,
  `type_id` bigint(20) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `path` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`annex_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_project_annex
-- ----------------------------
INSERT INTO `tb_project_annex` VALUES ('1', 'HLRSP21464564', '6', '555', 'https://172.18.3.212/svn/Authority_Repository/branches/process_v0.1');

-- ----------------------------
-- Table structure for tb_project_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_project_category`;
CREATE TABLE `tb_project_category` (
  `category_id` bigint(20) NOT NULL,
  `category_name` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_project_category
-- ----------------------------
INSERT INTO `tb_project_category` VALUES ('1', 'CoolMarts', 'CoolMarts');
INSERT INTO `tb_project_category` VALUES ('2', 'CamTalk', 'CamTalk');
INSERT INTO `tb_project_category` VALUES ('3', 'Lottery', 'Lottery');

-- ----------------------------
-- Table structure for tb_project_requirement
-- ----------------------------
DROP TABLE IF EXISTS `tb_project_requirement`;
CREATE TABLE `tb_project_requirement` (
  `requirement_id` bigint(20) NOT NULL,
  `category_id` bigint(255) DEFAULT NULL,
  `publish_time` timestamp NULL DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `requirement_desc` varchar(1024) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `complete_time` date DEFAULT NULL,
  `reward` bigint(20) NOT NULL,
  PRIMARY KEY (`requirement_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_project_requirement
-- ----------------------------
INSERT INTO `tb_project_requirement` VALUES ('1222', '2', '2016-12-20 00:00:00', '提交', null, '2', '2016-12-20', '111111');

-- ----------------------------
-- Table structure for tb_project_subcategory
-- ----------------------------
DROP TABLE IF EXISTS `tb_project_subcategory`;
CREATE TABLE `tb_project_subcategory` (
  `subcategory_id` bigint(20) NOT NULL,
  `subcategory_name` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`subcategory_id`),
  KEY `fk_category_id` (`category_id`),
  CONSTRAINT `fk_category_id` FOREIGN KEY (`category_id`) REFERENCES `tb_project_category` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_project_subcategory
-- ----------------------------
INSERT INTO `tb_project_subcategory` VALUES ('1', 'coolmars1', 'coolmars1', '1');
INSERT INTO `tb_project_subcategory` VALUES ('2', 'coolmars2', 'coolmars2', '1');
INSERT INTO `tb_project_subcategory` VALUES ('3', 'coolmars3', 'coolmars3', '1');
INSERT INTO `tb_project_subcategory` VALUES ('4', 'coolmars4', 'coolmars4', '1');
INSERT INTO `tb_project_subcategory` VALUES ('5', 'coolmars5', 'coolmars5', '1');
INSERT INTO `tb_project_subcategory` VALUES ('6', 'coolmars6', 'coolmars6', '1');
INSERT INTO `tb_project_subcategory` VALUES ('7', 'coolmars7', 'coolmars7', '1');
INSERT INTO `tb_project_subcategory` VALUES ('8', 'coolmars8', 'coolmars8', '1');
INSERT INTO `tb_project_subcategory` VALUES ('9', 'coolmars9', 'coolmars9', '1');
INSERT INTO `tb_project_subcategory` VALUES ('10', 'coolmars10', 'coolmars10', '1');
INSERT INTO `tb_project_subcategory` VALUES ('11', 'coolmars11', 'coolmars11', '1');
INSERT INTO `tb_project_subcategory` VALUES ('12', 'coolmars12', 'coolmars12', '1');
INSERT INTO `tb_project_subcategory` VALUES ('13', 'coolmars13', 'coolmars13', '1');
INSERT INTO `tb_project_subcategory` VALUES ('14', 'coolmars14', 'coolmars14', '1');
INSERT INTO `tb_project_subcategory` VALUES ('15', 'coolmars15', 'coolmars15', '1');
INSERT INTO `tb_project_subcategory` VALUES ('16', 'coolmars16', 'coolmars16', '1');
INSERT INTO `tb_project_subcategory` VALUES ('17', 'coolmars17', 'coolmars17', '1');
INSERT INTO `tb_project_subcategory` VALUES ('18', 'coolmars18', 'coolmars18', '1');
INSERT INTO `tb_project_subcategory` VALUES ('19', 'coolmars19', 'coolmars19', '1');
INSERT INTO `tb_project_subcategory` VALUES ('20', 'coolmars20', 'coolmars20', '1');
INSERT INTO `tb_project_subcategory` VALUES ('21', 'coolmars21', 'coolmars21', '1');
INSERT INTO `tb_project_subcategory` VALUES ('22', 'coolmars22', 'coolmars22', '1');
INSERT INTO `tb_project_subcategory` VALUES ('23', 'coolmars23', 'coolmars23', '1');
INSERT INTO `tb_project_subcategory` VALUES ('24', 'coolmars24', 'coolmars24', '1');
INSERT INTO `tb_project_subcategory` VALUES ('25', 'coolmars25', 'coolmars25', '1');
INSERT INTO `tb_project_subcategory` VALUES ('26', 'coolmars26', 'coolmars26', '1');
INSERT INTO `tb_project_subcategory` VALUES ('27', 'coolmars27', 'coolmars27', '1');
INSERT INTO `tb_project_subcategory` VALUES ('28', 'coolmars28', 'coolmars28', '1');
INSERT INTO `tb_project_subcategory` VALUES ('29', 'coolmars29', 'coolmars29', '1');
INSERT INTO `tb_project_subcategory` VALUES ('30', 'coolmars30', 'coolmars30', '1');
INSERT INTO `tb_project_subcategory` VALUES ('31', 'coolmars31', 'coolmars31', '1');
INSERT INTO `tb_project_subcategory` VALUES ('32', 'coolmars32', 'coolmars32', '1');
INSERT INTO `tb_project_subcategory` VALUES ('33', 'coolmars33', 'coolmars33', '1');
INSERT INTO `tb_project_subcategory` VALUES ('34', 'coolmars34', 'coolmars34', '1');
INSERT INTO `tb_project_subcategory` VALUES ('35', 'coolmars35', 'coolmars35', '1');
INSERT INTO `tb_project_subcategory` VALUES ('36', 'coolmars36', 'coolmars36', '1');
INSERT INTO `tb_project_subcategory` VALUES ('37', 'coolmars37', 'coolmars37', '1');
INSERT INTO `tb_project_subcategory` VALUES ('38', 'coolmars38', 'coolmars38', '1');
INSERT INTO `tb_project_subcategory` VALUES ('39', 'coolmars39', 'coolmars39', '1');
INSERT INTO `tb_project_subcategory` VALUES ('40', 'coolmars40', 'coolmars40', '1');
INSERT INTO `tb_project_subcategory` VALUES ('41', 'coolmars41', 'coolmars41', '1');
INSERT INTO `tb_project_subcategory` VALUES ('42', 'coolmars42', 'coolmars42', '1');
INSERT INTO `tb_project_subcategory` VALUES ('43', 'coolmars43', 'coolmars43', '1');
INSERT INTO `tb_project_subcategory` VALUES ('44', 'coolmars44', 'coolmars44', '1');
INSERT INTO `tb_project_subcategory` VALUES ('45', 'coolmars45', 'coolmars45', '1');
INSERT INTO `tb_project_subcategory` VALUES ('46', 'coolmars46', 'coolmars46', '1');
INSERT INTO `tb_project_subcategory` VALUES ('47', 'coolmars47', 'coolmars47', '1');
INSERT INTO `tb_project_subcategory` VALUES ('48', 'coolmars48', 'coolmars48', '1');
INSERT INTO `tb_project_subcategory` VALUES ('49', 'coolmars49', 'coolmars49', '1');
INSERT INTO `tb_project_subcategory` VALUES ('50', 'coolmars50', 'coolmars50', '1');
INSERT INTO `tb_project_subcategory` VALUES ('51', 'coolmars51', 'coolmars51', '1');
INSERT INTO `tb_project_subcategory` VALUES ('52', 'coolmars52', 'coolmars52', '1');
INSERT INTO `tb_project_subcategory` VALUES ('53', 'coolmars53', 'coolmars53', '1');
INSERT INTO `tb_project_subcategory` VALUES ('54', 'coolmars54', 'coolmars54', '1');
INSERT INTO `tb_project_subcategory` VALUES ('55', 'coolmars55', 'coolmars55', '1');
INSERT INTO `tb_project_subcategory` VALUES ('56', 'coolmars56', 'coolmars56', '1');
INSERT INTO `tb_project_subcategory` VALUES ('57', 'coolmars57', 'coolmars57', '1');
INSERT INTO `tb_project_subcategory` VALUES ('58', 'coolmars58', 'coolmars58', '1');
INSERT INTO `tb_project_subcategory` VALUES ('59', 'coolmars59', 'coolmars59', '1');
INSERT INTO `tb_project_subcategory` VALUES ('60', 'coolmars60', 'coolmars60', '1');
INSERT INTO `tb_project_subcategory` VALUES ('61', 'coolmars61', 'coolmars61', '1');
INSERT INTO `tb_project_subcategory` VALUES ('62', 'coolmars62', 'coolmars62', '1');
INSERT INTO `tb_project_subcategory` VALUES ('63', 'coolmars63', 'coolmars63', '1');
INSERT INTO `tb_project_subcategory` VALUES ('64', 'coolmars64', 'coolmars64', '1');
INSERT INTO `tb_project_subcategory` VALUES ('65', 'coolmars65', 'coolmars65', '1');
INSERT INTO `tb_project_subcategory` VALUES ('66', 'coolmars66', 'coolmars66', '1');
INSERT INTO `tb_project_subcategory` VALUES ('67', 'coolmars67', 'coolmars67', '1');
INSERT INTO `tb_project_subcategory` VALUES ('68', 'coolmars68', 'coolmars68', '1');
INSERT INTO `tb_project_subcategory` VALUES ('69', 'coolmars69', 'coolmars69', '1');
INSERT INTO `tb_project_subcategory` VALUES ('70', 'coolmars70', 'coolmars70', '1');
INSERT INTO `tb_project_subcategory` VALUES ('71', 'coolmars71', 'coolmars71', '1');
INSERT INTO `tb_project_subcategory` VALUES ('72', 'coolmars72', 'coolmars72', '1');
INSERT INTO `tb_project_subcategory` VALUES ('73', 'coolmars73', 'coolmars73', '1');
INSERT INTO `tb_project_subcategory` VALUES ('74', 'coolmars74', 'coolmars74', '1');
INSERT INTO `tb_project_subcategory` VALUES ('75', 'coolmars75', 'coolmars75', '1');
INSERT INTO `tb_project_subcategory` VALUES ('76', 'coolmars76', 'coolmars76', '1');
INSERT INTO `tb_project_subcategory` VALUES ('77', 'coolmars77', 'coolmars77', '1');
INSERT INTO `tb_project_subcategory` VALUES ('78', 'coolmars78', 'coolmars78', '1');
INSERT INTO `tb_project_subcategory` VALUES ('79', 'coolmars79', 'coolmars79', '1');
INSERT INTO `tb_project_subcategory` VALUES ('80', 'coolmars80', 'coolmars80', '1');
INSERT INTO `tb_project_subcategory` VALUES ('81', 'coolmars81', 'coolmars81', '1');
INSERT INTO `tb_project_subcategory` VALUES ('82', 'coolmars82', 'coolmars82', '1');
INSERT INTO `tb_project_subcategory` VALUES ('83', 'coolmars83', 'coolmars83', '1');
INSERT INTO `tb_project_subcategory` VALUES ('84', 'coolmars84', 'coolmars84', '1');
INSERT INTO `tb_project_subcategory` VALUES ('85', 'coolmars85', 'coolmars85', '1');
INSERT INTO `tb_project_subcategory` VALUES ('86', 'coolmars86', 'coolmars86', '1');
INSERT INTO `tb_project_subcategory` VALUES ('87', 'coolmars87', 'coolmars87', '1');
INSERT INTO `tb_project_subcategory` VALUES ('88', 'coolmars88', 'coolmars88', '1');
INSERT INTO `tb_project_subcategory` VALUES ('89', 'coolmars89', 'coolmars89', '1');
INSERT INTO `tb_project_subcategory` VALUES ('90', 'coolmars90', 'coolmars90', '1');
INSERT INTO `tb_project_subcategory` VALUES ('91', 'coolmars91', 'coolmars91', '1');
INSERT INTO `tb_project_subcategory` VALUES ('92', 'coolmars92', 'coolmars92', '1');
INSERT INTO `tb_project_subcategory` VALUES ('93', 'coolmars93', 'coolmars93', '1');
INSERT INTO `tb_project_subcategory` VALUES ('94', 'coolmars94', 'coolmars94', '1');
INSERT INTO `tb_project_subcategory` VALUES ('95', 'coolmars95', 'coolmars95', '1');
INSERT INTO `tb_project_subcategory` VALUES ('96', 'coolmars96', 'coolmars96', '1');
INSERT INTO `tb_project_subcategory` VALUES ('97', 'coolmars97', 'coolmars97', '1');
INSERT INTO `tb_project_subcategory` VALUES ('98', 'coolmars98', 'coolmars98', '1');
INSERT INTO `tb_project_subcategory` VALUES ('99', 'coolmars99', 'coolmars99', '1');
INSERT INTO `tb_project_subcategory` VALUES ('100', 'camtalk100', 'camtalk100', '2');
INSERT INTO `tb_project_subcategory` VALUES ('101', 'camtalk101', 'camtalk101', '2');
INSERT INTO `tb_project_subcategory` VALUES ('102', 'camtalk102', 'camtalk102', '2');
INSERT INTO `tb_project_subcategory` VALUES ('103', 'camtalk103', 'camtalk103', '2');
INSERT INTO `tb_project_subcategory` VALUES ('104', 'camtalk104', 'camtalk104', '2');
INSERT INTO `tb_project_subcategory` VALUES ('105', 'camtalk105', 'camtalk105', '2');
INSERT INTO `tb_project_subcategory` VALUES ('106', 'camtalk106', 'camtalk106', '2');
INSERT INTO `tb_project_subcategory` VALUES ('107', 'camtalk107', 'camtalk107', '2');
INSERT INTO `tb_project_subcategory` VALUES ('108', 'camtalk108', 'camtalk108', '2');
INSERT INTO `tb_project_subcategory` VALUES ('109', 'camtalk109', 'camtalk109', '2');
INSERT INTO `tb_project_subcategory` VALUES ('110', 'camtalk110', 'camtalk110', '2');
INSERT INTO `tb_project_subcategory` VALUES ('111', 'camtalk111', 'camtalk111', '2');
INSERT INTO `tb_project_subcategory` VALUES ('112', 'camtalk112', 'camtalk112', '2');
INSERT INTO `tb_project_subcategory` VALUES ('113', 'camtalk113', 'camtalk113', '2');
INSERT INTO `tb_project_subcategory` VALUES ('114', 'camtalk114', 'camtalk114', '2');
INSERT INTO `tb_project_subcategory` VALUES ('115', 'camtalk115', 'camtalk115', '2');
INSERT INTO `tb_project_subcategory` VALUES ('116', 'camtalk116', 'camtalk116', '2');
INSERT INTO `tb_project_subcategory` VALUES ('117', 'camtalk117', 'camtalk117', '2');
INSERT INTO `tb_project_subcategory` VALUES ('118', 'camtalk118', 'camtalk118', '2');
INSERT INTO `tb_project_subcategory` VALUES ('119', 'camtalk119', 'camtalk119', '2');
INSERT INTO `tb_project_subcategory` VALUES ('120', 'camtalk120', 'camtalk120', '2');
INSERT INTO `tb_project_subcategory` VALUES ('121', 'camtalk121', 'camtalk121', '2');
INSERT INTO `tb_project_subcategory` VALUES ('122', 'camtalk122', 'camtalk122', '2');
INSERT INTO `tb_project_subcategory` VALUES ('123', 'camtalk123', 'camtalk123', '2');
INSERT INTO `tb_project_subcategory` VALUES ('124', 'camtalk124', 'camtalk124', '2');
INSERT INTO `tb_project_subcategory` VALUES ('125', 'camtalk125', 'camtalk125', '2');
INSERT INTO `tb_project_subcategory` VALUES ('126', 'camtalk126', 'camtalk126', '2');
INSERT INTO `tb_project_subcategory` VALUES ('127', 'camtalk127', 'camtalk127', '2');
INSERT INTO `tb_project_subcategory` VALUES ('128', 'camtalk128', 'camtalk128', '2');
INSERT INTO `tb_project_subcategory` VALUES ('129', 'camtalk129', 'camtalk129', '2');
INSERT INTO `tb_project_subcategory` VALUES ('130', 'camtalk130', 'camtalk130', '2');
INSERT INTO `tb_project_subcategory` VALUES ('131', 'camtalk131', 'camtalk131', '2');
INSERT INTO `tb_project_subcategory` VALUES ('132', 'camtalk132', 'camtalk132', '2');
INSERT INTO `tb_project_subcategory` VALUES ('133', 'camtalk133', 'camtalk133', '2');
INSERT INTO `tb_project_subcategory` VALUES ('134', 'camtalk134', 'camtalk134', '2');
INSERT INTO `tb_project_subcategory` VALUES ('135', 'camtalk135', 'camtalk135', '2');
INSERT INTO `tb_project_subcategory` VALUES ('136', 'camtalk136', 'camtalk136', '2');
INSERT INTO `tb_project_subcategory` VALUES ('137', 'camtalk137', 'camtalk137', '2');
INSERT INTO `tb_project_subcategory` VALUES ('138', 'camtalk138', 'camtalk138', '2');
INSERT INTO `tb_project_subcategory` VALUES ('139', 'camtalk139', 'camtalk139', '2');
INSERT INTO `tb_project_subcategory` VALUES ('140', 'camtalk140', 'camtalk140', '2');
INSERT INTO `tb_project_subcategory` VALUES ('141', 'camtalk141', 'camtalk141', '2');
INSERT INTO `tb_project_subcategory` VALUES ('142', 'camtalk142', 'camtalk142', '2');
INSERT INTO `tb_project_subcategory` VALUES ('143', 'camtalk143', 'camtalk143', '2');
INSERT INTO `tb_project_subcategory` VALUES ('144', 'camtalk144', 'camtalk144', '2');
INSERT INTO `tb_project_subcategory` VALUES ('145', 'camtalk145', 'camtalk145', '2');
INSERT INTO `tb_project_subcategory` VALUES ('146', 'camtalk146', 'camtalk146', '2');
INSERT INTO `tb_project_subcategory` VALUES ('147', 'camtalk147', 'camtalk147', '2');
INSERT INTO `tb_project_subcategory` VALUES ('148', 'camtalk148', 'camtalk148', '2');
INSERT INTO `tb_project_subcategory` VALUES ('149', 'camtalk149', 'camtalk149', '2');
INSERT INTO `tb_project_subcategory` VALUES ('150', 'camtalk150', 'camtalk150', '2');
INSERT INTO `tb_project_subcategory` VALUES ('151', 'camtalk151', 'camtalk151', '2');
INSERT INTO `tb_project_subcategory` VALUES ('152', 'camtalk152', 'camtalk152', '2');
INSERT INTO `tb_project_subcategory` VALUES ('153', 'camtalk153', 'camtalk153', '2');
INSERT INTO `tb_project_subcategory` VALUES ('154', 'camtalk154', 'camtalk154', '2');
INSERT INTO `tb_project_subcategory` VALUES ('155', 'camtalk155', 'camtalk155', '2');
INSERT INTO `tb_project_subcategory` VALUES ('156', 'camtalk156', 'camtalk156', '2');
INSERT INTO `tb_project_subcategory` VALUES ('157', 'camtalk157', 'camtalk157', '2');
INSERT INTO `tb_project_subcategory` VALUES ('158', 'camtalk158', 'camtalk158', '2');
INSERT INTO `tb_project_subcategory` VALUES ('159', 'camtalk159', 'camtalk159', '2');
INSERT INTO `tb_project_subcategory` VALUES ('160', 'camtalk160', 'camtalk160', '2');
INSERT INTO `tb_project_subcategory` VALUES ('161', 'camtalk161', 'camtalk161', '2');
INSERT INTO `tb_project_subcategory` VALUES ('162', 'camtalk162', 'camtalk162', '2');
INSERT INTO `tb_project_subcategory` VALUES ('163', 'camtalk163', 'camtalk163', '2');
INSERT INTO `tb_project_subcategory` VALUES ('164', 'camtalk164', 'camtalk164', '2');
INSERT INTO `tb_project_subcategory` VALUES ('165', 'camtalk165', 'camtalk165', '2');
INSERT INTO `tb_project_subcategory` VALUES ('166', 'camtalk166', 'camtalk166', '2');
INSERT INTO `tb_project_subcategory` VALUES ('167', 'camtalk167', 'camtalk167', '2');
INSERT INTO `tb_project_subcategory` VALUES ('168', 'camtalk168', 'camtalk168', '2');
INSERT INTO `tb_project_subcategory` VALUES ('169', 'camtalk169', 'camtalk169', '2');
INSERT INTO `tb_project_subcategory` VALUES ('170', 'camtalk170', 'camtalk170', '2');
INSERT INTO `tb_project_subcategory` VALUES ('171', 'camtalk171', 'camtalk171', '2');
INSERT INTO `tb_project_subcategory` VALUES ('172', 'camtalk172', 'camtalk172', '2');
INSERT INTO `tb_project_subcategory` VALUES ('173', 'camtalk173', 'camtalk173', '2');
INSERT INTO `tb_project_subcategory` VALUES ('174', 'camtalk174', 'camtalk174', '2');
INSERT INTO `tb_project_subcategory` VALUES ('175', 'camtalk175', 'camtalk175', '2');
INSERT INTO `tb_project_subcategory` VALUES ('176', 'camtalk176', 'camtalk176', '2');
INSERT INTO `tb_project_subcategory` VALUES ('177', 'camtalk177', 'camtalk177', '2');
INSERT INTO `tb_project_subcategory` VALUES ('178', 'camtalk178', 'camtalk178', '2');
INSERT INTO `tb_project_subcategory` VALUES ('179', 'camtalk179', 'camtalk179', '2');
INSERT INTO `tb_project_subcategory` VALUES ('180', 'camtalk180', 'camtalk180', '2');
INSERT INTO `tb_project_subcategory` VALUES ('181', 'camtalk181', 'camtalk181', '2');
INSERT INTO `tb_project_subcategory` VALUES ('182', 'camtalk182', 'camtalk182', '2');
INSERT INTO `tb_project_subcategory` VALUES ('183', 'camtalk183', 'camtalk183', '2');
INSERT INTO `tb_project_subcategory` VALUES ('184', 'camtalk184', 'camtalk184', '2');
INSERT INTO `tb_project_subcategory` VALUES ('185', 'camtalk185', 'camtalk185', '2');
INSERT INTO `tb_project_subcategory` VALUES ('186', 'camtalk186', 'camtalk186', '2');
INSERT INTO `tb_project_subcategory` VALUES ('187', 'camtalk187', 'camtalk187', '2');
INSERT INTO `tb_project_subcategory` VALUES ('188', 'camtalk188', 'camtalk188', '2');
INSERT INTO `tb_project_subcategory` VALUES ('189', 'camtalk189', 'camtalk189', '2');
INSERT INTO `tb_project_subcategory` VALUES ('190', 'camtalk190', 'camtalk190', '2');
INSERT INTO `tb_project_subcategory` VALUES ('191', 'camtalk191', 'camtalk191', '2');
INSERT INTO `tb_project_subcategory` VALUES ('192', 'camtalk192', 'camtalk192', '2');
INSERT INTO `tb_project_subcategory` VALUES ('193', 'camtalk193', 'camtalk193', '2');
INSERT INTO `tb_project_subcategory` VALUES ('194', 'camtalk194', 'camtalk194', '2');
INSERT INTO `tb_project_subcategory` VALUES ('195', 'camtalk195', 'camtalk195', '2');
INSERT INTO `tb_project_subcategory` VALUES ('196', 'camtalk196', 'camtalk196', '2');
INSERT INTO `tb_project_subcategory` VALUES ('197', 'camtalk197', 'camtalk197', '2');
INSERT INTO `tb_project_subcategory` VALUES ('198', 'camtalk198', 'camtalk198', '2');
INSERT INTO `tb_project_subcategory` VALUES ('199', 'camtalk199', 'camtalk199', '2');
INSERT INTO `tb_project_subcategory` VALUES ('200', 'camtalk200', 'camtalk200', '2');
INSERT INTO `tb_project_subcategory` VALUES ('201', 'lottery201', 'lottery201', '3');
INSERT INTO `tb_project_subcategory` VALUES ('202', 'lottery202', 'lottery202', '3');
INSERT INTO `tb_project_subcategory` VALUES ('203', 'lottery203', 'lottery203', '3');
INSERT INTO `tb_project_subcategory` VALUES ('204', 'lottery204', 'lottery204', '3');
INSERT INTO `tb_project_subcategory` VALUES ('205', 'lottery205', 'lottery205', '3');
INSERT INTO `tb_project_subcategory` VALUES ('206', 'lottery206', 'lottery206', '3');
INSERT INTO `tb_project_subcategory` VALUES ('207', 'lottery207', 'lottery207', '3');
INSERT INTO `tb_project_subcategory` VALUES ('208', 'lottery208', 'lottery208', '3');
INSERT INTO `tb_project_subcategory` VALUES ('209', 'lottery209', 'lottery209', '3');
INSERT INTO `tb_project_subcategory` VALUES ('210', 'lottery210', 'lottery210', '3');
INSERT INTO `tb_project_subcategory` VALUES ('211', 'lottery211', 'lottery211', '3');
INSERT INTO `tb_project_subcategory` VALUES ('212', 'lottery212', 'lottery212', '3');
INSERT INTO `tb_project_subcategory` VALUES ('213', 'lottery213', 'lottery213', '3');
INSERT INTO `tb_project_subcategory` VALUES ('214', 'lottery214', 'lottery214', '3');
INSERT INTO `tb_project_subcategory` VALUES ('215', 'lottery215', 'lottery215', '3');
INSERT INTO `tb_project_subcategory` VALUES ('216', 'lottery216', 'lottery216', '3');
INSERT INTO `tb_project_subcategory` VALUES ('217', 'lottery217', 'lottery217', '3');
INSERT INTO `tb_project_subcategory` VALUES ('218', 'lottery218', 'lottery218', '3');
INSERT INTO `tb_project_subcategory` VALUES ('219', 'lottery219', 'lottery219', '3');
INSERT INTO `tb_project_subcategory` VALUES ('220', 'lottery220', 'lottery220', '3');
INSERT INTO `tb_project_subcategory` VALUES ('221', 'lottery221', 'lottery221', '3');
INSERT INTO `tb_project_subcategory` VALUES ('222', 'lottery222', 'lottery222', '3');
INSERT INTO `tb_project_subcategory` VALUES ('223', 'lottery223', 'lottery223', '3');
INSERT INTO `tb_project_subcategory` VALUES ('224', 'lottery224', 'lottery224', '3');
INSERT INTO `tb_project_subcategory` VALUES ('225', 'lottery225', 'lottery225', '3');
INSERT INTO `tb_project_subcategory` VALUES ('226', 'lottery226', 'lottery226', '3');
INSERT INTO `tb_project_subcategory` VALUES ('227', 'lottery227', 'lottery227', '3');
INSERT INTO `tb_project_subcategory` VALUES ('228', 'lottery228', 'lottery228', '3');
INSERT INTO `tb_project_subcategory` VALUES ('229', 'lottery229', 'lottery229', '3');
INSERT INTO `tb_project_subcategory` VALUES ('230', 'lottery230', 'lottery230', '3');
INSERT INTO `tb_project_subcategory` VALUES ('231', 'lottery231', 'lottery231', '3');
INSERT INTO `tb_project_subcategory` VALUES ('232', 'lottery232', 'lottery232', '3');
INSERT INTO `tb_project_subcategory` VALUES ('233', 'lottery233', 'lottery233', '3');
INSERT INTO `tb_project_subcategory` VALUES ('234', 'lottery234', 'lottery234', '3');
INSERT INTO `tb_project_subcategory` VALUES ('235', 'lottery235', 'lottery235', '3');
INSERT INTO `tb_project_subcategory` VALUES ('236', 'lottery236', 'lottery236', '3');
INSERT INTO `tb_project_subcategory` VALUES ('237', 'lottery237', 'lottery237', '3');
INSERT INTO `tb_project_subcategory` VALUES ('238', 'lottery238', 'lottery238', '3');
INSERT INTO `tb_project_subcategory` VALUES ('239', 'lottery239', 'lottery239', '3');
INSERT INTO `tb_project_subcategory` VALUES ('240', 'lottery240', 'lottery240', '3');
INSERT INTO `tb_project_subcategory` VALUES ('241', 'lottery241', 'lottery241', '3');
INSERT INTO `tb_project_subcategory` VALUES ('242', 'lottery242', 'lottery242', '3');
INSERT INTO `tb_project_subcategory` VALUES ('243', 'lottery243', 'lottery243', '3');
INSERT INTO `tb_project_subcategory` VALUES ('244', 'lottery244', 'lottery244', '3');
INSERT INTO `tb_project_subcategory` VALUES ('245', 'lottery245', 'lottery245', '3');
INSERT INTO `tb_project_subcategory` VALUES ('246', 'lottery246', 'lottery246', '3');
INSERT INTO `tb_project_subcategory` VALUES ('247', 'lottery247', 'lottery247', '3');
INSERT INTO `tb_project_subcategory` VALUES ('248', 'lottery248', 'lottery248', '3');
INSERT INTO `tb_project_subcategory` VALUES ('249', 'lottery249', 'lottery249', '3');
INSERT INTO `tb_project_subcategory` VALUES ('250', 'lottery250', 'lottery250', '3');
INSERT INTO `tb_project_subcategory` VALUES ('251', 'lottery251', 'lottery251', '3');
INSERT INTO `tb_project_subcategory` VALUES ('252', 'lottery252', 'lottery252', '3');
INSERT INTO `tb_project_subcategory` VALUES ('253', 'lottery253', 'lottery253', '3');
INSERT INTO `tb_project_subcategory` VALUES ('254', 'lottery254', 'lottery254', '3');
INSERT INTO `tb_project_subcategory` VALUES ('255', 'lottery255', 'lottery255', '3');
INSERT INTO `tb_project_subcategory` VALUES ('256', 'lottery256', 'lottery256', '3');
INSERT INTO `tb_project_subcategory` VALUES ('257', 'lottery257', 'lottery257', '3');
INSERT INTO `tb_project_subcategory` VALUES ('258', 'lottery258', 'lottery258', '3');
INSERT INTO `tb_project_subcategory` VALUES ('259', 'lottery259', 'lottery259', '3');
INSERT INTO `tb_project_subcategory` VALUES ('260', 'lottery260', 'lottery260', '3');
INSERT INTO `tb_project_subcategory` VALUES ('261', 'lottery261', 'lottery261', '3');
INSERT INTO `tb_project_subcategory` VALUES ('262', 'lottery262', 'lottery262', '3');
INSERT INTO `tb_project_subcategory` VALUES ('263', 'lottery263', 'lottery263', '3');
INSERT INTO `tb_project_subcategory` VALUES ('264', 'lottery264', 'lottery264', '3');
INSERT INTO `tb_project_subcategory` VALUES ('265', 'lottery265', 'lottery265', '3');
INSERT INTO `tb_project_subcategory` VALUES ('266', 'lottery266', 'lottery266', '3');
INSERT INTO `tb_project_subcategory` VALUES ('267', 'lottery267', 'lottery267', '3');
INSERT INTO `tb_project_subcategory` VALUES ('268', 'lottery268', 'lottery268', '3');
INSERT INTO `tb_project_subcategory` VALUES ('269', 'lottery269', 'lottery269', '3');
INSERT INTO `tb_project_subcategory` VALUES ('270', 'lottery270', 'lottery270', '3');
INSERT INTO `tb_project_subcategory` VALUES ('271', 'lottery271', 'lottery271', '3');
INSERT INTO `tb_project_subcategory` VALUES ('272', 'lottery272', 'lottery272', '3');
INSERT INTO `tb_project_subcategory` VALUES ('273', 'lottery273', 'lottery273', '3');
INSERT INTO `tb_project_subcategory` VALUES ('274', 'lottery274', 'lottery274', '3');
INSERT INTO `tb_project_subcategory` VALUES ('275', 'lottery275', 'lottery275', '3');
INSERT INTO `tb_project_subcategory` VALUES ('276', 'lottery276', 'lottery276', '3');
INSERT INTO `tb_project_subcategory` VALUES ('277', 'lottery277', 'lottery277', '3');
INSERT INTO `tb_project_subcategory` VALUES ('278', 'lottery278', 'lottery278', '3');
INSERT INTO `tb_project_subcategory` VALUES ('279', 'lottery279', 'lottery279', '3');
INSERT INTO `tb_project_subcategory` VALUES ('280', 'lottery280', 'lottery280', '3');
INSERT INTO `tb_project_subcategory` VALUES ('281', 'lottery281', 'lottery281', '3');
INSERT INTO `tb_project_subcategory` VALUES ('282', 'lottery282', 'lottery282', '3');
INSERT INTO `tb_project_subcategory` VALUES ('283', 'lottery283', 'lottery283', '3');
INSERT INTO `tb_project_subcategory` VALUES ('284', 'lottery284', 'lottery284', '3');
INSERT INTO `tb_project_subcategory` VALUES ('285', 'lottery285', 'lottery285', '3');
INSERT INTO `tb_project_subcategory` VALUES ('286', 'lottery286', 'lottery286', '3');
INSERT INTO `tb_project_subcategory` VALUES ('287', 'lottery287', 'lottery287', '3');
INSERT INTO `tb_project_subcategory` VALUES ('288', 'lottery288', 'lottery288', '3');
INSERT INTO `tb_project_subcategory` VALUES ('289', 'lottery289', 'lottery289', '3');
INSERT INTO `tb_project_subcategory` VALUES ('290', 'lottery290', 'lottery290', '3');
INSERT INTO `tb_project_subcategory` VALUES ('291', 'lottery291', 'lottery291', '3');
INSERT INTO `tb_project_subcategory` VALUES ('292', 'lottery292', 'lottery292', '3');
INSERT INTO `tb_project_subcategory` VALUES ('293', 'lottery293', 'lottery293', '3');
INSERT INTO `tb_project_subcategory` VALUES ('294', 'lottery294', 'lottery294', '3');
INSERT INTO `tb_project_subcategory` VALUES ('295', 'lottery295', 'lottery295', '3');
INSERT INTO `tb_project_subcategory` VALUES ('296', 'lottery296', 'lottery296', '3');
INSERT INTO `tb_project_subcategory` VALUES ('297', 'lottery297', 'lottery297', '3');
INSERT INTO `tb_project_subcategory` VALUES ('298', 'lottery298', 'lottery298', '3');
INSERT INTO `tb_project_subcategory` VALUES ('299', 'lottery299', 'lottery299', '3');
INSERT INTO `tb_project_subcategory` VALUES ('300', 'lottery300', 'lottery300', '3');

-- ----------------------------
-- Table structure for xw_sequence
-- ----------------------------
DROP TABLE IF EXISTS `xw_sequence`;
CREATE TABLE `xw_sequence` (
  `SEQ_NAME` varchar(64) NOT NULL,
  `INIT_VALUE` decimal(16,0) NOT NULL,
  `MAX_VALUE` decimal(16,0) NOT NULL,
  `CURRENT_VALUE` decimal(16,0) NOT NULL,
  `SEQ_STEP` decimal(8,0) NOT NULL,
  `TABLE_CIRCLE_SIZE` decimal(8,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xw_sequence
-- ----------------------------
INSERT INTO `xw_sequence` VALUES ('project_seq', '1000000', '9999999', '1000265', '1', '10');
INSERT INTO `xw_sequence` VALUES ('user_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('task_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('annex_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('cycle_report_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('expert_review_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('expert_score_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('committee_approval_seq', '1000000', '9999999', '1000000', '1', '10');
INSERT INTO `xw_sequence` VALUES ('departleader_approval_seq', '1000000', '9999999', '1000000', '1', '10');