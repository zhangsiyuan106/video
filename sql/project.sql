/*
Navicat MySQL Data Transfer

Source Server         : 47.108.238.23_3306
Source Server Version : 50734
Source Host           : 47.108.238.23:3306
Source Database       : 022-project

Target Server Type    : MYSQL
Target Server Version : 50734
File Encoding         : 65001

Date: 2021-08-21 17:57:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `_id` varchar(255) NOT NULL,
  `content` mediumtext,
  `headLine` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------

-- ----------------------------
-- Table structure for follow
-- ----------------------------
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `followid` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of follow
-- ----------------------------
INSERT INTO `follow` VALUES ('1', '1', '2');
INSERT INTO `follow` VALUES ('3', '2', '4');
INSERT INTO `follow` VALUES ('4', '6', '4');
INSERT INTO `follow` VALUES ('9', '4', '6');
INSERT INTO `follow` VALUES ('10', '4', '2');
INSERT INTO `follow` VALUES ('11', '3', '4');
INSERT INTO `follow` VALUES ('12', '16', '2');
INSERT INTO `follow` VALUES ('13', '6', '2');
INSERT INTO `follow` VALUES ('14', '2', '15');
INSERT INTO `follow` VALUES ('15', '18', '15');
INSERT INTO `follow` VALUES ('16', '2', '15');
INSERT INTO `follow` VALUES ('17', '15', '2');
INSERT INTO `follow` VALUES ('18', '15', '6');
INSERT INTO `follow` VALUES ('19', '11', '16');
INSERT INTO `follow` VALUES ('20', '15', '1');
INSERT INTO `follow` VALUES ('21', '1', '10');
INSERT INTO `follow` VALUES ('22', '4', '1');
INSERT INTO `follow` VALUES ('23', '1', '10');

-- ----------------------------
-- Table structure for invited
-- ----------------------------
DROP TABLE IF EXISTS `invited`;
CREATE TABLE `invited` (
  `userid` int(11) NOT NULL,
  `inviteid` int(11) NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of invited
-- ----------------------------
INSERT INTO `invited` VALUES ('1', '3', '2021-07-14 16:51:29');
INSERT INTO `invited` VALUES ('1', '2', '2021-07-14 16:56:58');
INSERT INTO `invited` VALUES ('2', '1', '2021-07-21 16:22:37');
INSERT INTO `invited` VALUES ('4', '6', '2021-08-15 23:45:58');
INSERT INTO `invited` VALUES ('3', '4', '2021-08-18 17:31:52');
INSERT INTO `invited` VALUES ('4', '1', '2021-08-20 16:27:37');
INSERT INTO `invited` VALUES ('4', '2', '2021-08-20 16:27:49');
INSERT INTO `invited` VALUES ('15', '18', '2021-08-20 19:19:58');
INSERT INTO `invited` VALUES ('15', '2', '2021-08-20 19:22:00');
INSERT INTO `invited` VALUES ('15', '6', '2021-08-20 19:22:58');
INSERT INTO `invited` VALUES ('16', '4', '2021-08-21 12:52:59');
INSERT INTO `invited` VALUES ('16', '15', '2021-08-21 12:54:43');
INSERT INTO `invited` VALUES ('16', '11', '2021-08-21 12:55:22');
INSERT INTO `invited` VALUES ('1', '10', '2021-08-21 15:59:42');
INSERT INTO `invited` VALUES ('1', '4', '2021-08-21 16:01:05');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', 'root', '获取用户列表', 'userList', '2021-08-13 16:22:18');
INSERT INTO `log` VALUES ('2', 'root', '获取用户列表', 'userList', '2021-08-13 16:22:25');
INSERT INTO `log` VALUES ('3', 'root', '获取用户列表', 'userList', '2021-08-13 16:22:26');
INSERT INTO `log` VALUES ('4', 'root', '删除用户', 'deleteUser', '2021-08-13 16:22:31');
INSERT INTO `log` VALUES ('5', 'root', '获取用户列表', 'userList', '2021-08-13 16:22:31');
INSERT INTO `log` VALUES ('6', 'root', '获取用户列表', 'userList', '2021-08-13 16:22:39');
INSERT INTO `log` VALUES ('7', 'root', '更新用户', 'updateUser', '2021-08-13 16:22:45');
INSERT INTO `log` VALUES ('8', 'root', '获取用户列表', 'userList', '2021-08-13 16:22:45');
INSERT INTO `log` VALUES ('9', 'root', '获取用户列表', 'userList', '2021-08-13 16:25:27');
INSERT INTO `log` VALUES ('10', 'root', '获取用户列表', 'userList', '2021-08-13 16:27:16');
INSERT INTO `log` VALUES ('13', 'root', '获取用户列表', 'userList', '2021-08-13 16:27:45');
INSERT INTO `log` VALUES ('14', 'root', '获取用户列表', 'userList', '2021-08-13 16:27:48');
INSERT INTO `log` VALUES ('20', 'root', '更新用户', 'updateUser', '2021-08-13 16:28:07');
INSERT INTO `log` VALUES ('21', 'root', '获取用户列表', 'userList', '2021-08-13 16:28:07');
INSERT INTO `log` VALUES ('22', 'root', '删除评论', 'deleteComment', '2021-08-13 16:29:08');
INSERT INTO `log` VALUES ('23', 'myq', '获取用户列表', 'userList', '2021-08-13 16:33:52');
INSERT INTO `log` VALUES ('24', 'myq', '获取用户列表', 'userList', '2021-08-13 16:33:53');
INSERT INTO `log` VALUES ('25', 'myq', '获取用户列表', 'userList', '2021-08-13 16:33:53');
INSERT INTO `log` VALUES ('26', 'root', '删除评论', 'deleteComment', '2021-08-13 17:08:18');
INSERT INTO `log` VALUES ('27', 'root', '删除评论', 'deleteComment', '2021-08-13 17:08:20');
INSERT INTO `log` VALUES ('28', 'root', '删除评论', 'deleteComment', '2021-08-13 17:08:25');
INSERT INTO `log` VALUES ('29', 'root', '删除评论', 'deleteComment', '2021-08-13 17:08:27');
INSERT INTO `log` VALUES ('30', 'root', '获取用户列表', 'userList', '2021-08-13 17:08:32');
INSERT INTO `log` VALUES ('31', 'mmmmmm', '删除文章', 'deleteArticleById', '2021-08-13 17:52:30');
INSERT INTO `log` VALUES ('32', 'mmmmmm', '删除文章', 'deleteArticleById', '2021-08-13 17:53:09');
INSERT INTO `log` VALUES ('33', 'mmmmmm', '删除文章', 'deleteArticleById', '2021-08-13 17:55:16');
INSERT INTO `log` VALUES ('34', 'lyl', '删除文章', 'deleteArticleById', '2021-08-13 18:53:26');
INSERT INTO `log` VALUES ('35', 'root', '获取用户列表', 'userList', '2021-08-16 14:24:06');
INSERT INTO `log` VALUES ('36', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:16');
INSERT INTO `log` VALUES ('37', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:19');
INSERT INTO `log` VALUES ('38', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:22');
INSERT INTO `log` VALUES ('39', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:26');
INSERT INTO `log` VALUES ('40', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:28');
INSERT INTO `log` VALUES ('41', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:33');
INSERT INTO `log` VALUES ('42', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:35');
INSERT INTO `log` VALUES ('43', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:37');
INSERT INTO `log` VALUES ('44', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:42');
INSERT INTO `log` VALUES ('45', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:49');
INSERT INTO `log` VALUES ('46', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:51');
INSERT INTO `log` VALUES ('47', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:53');
INSERT INTO `log` VALUES ('48', 'null', '删除评论', 'deleteComment', '2021-08-16 15:37:56');
INSERT INTO `log` VALUES ('49', 'null', '删除评论', 'deleteComment', '2021-08-16 15:38:00');
INSERT INTO `log` VALUES ('50', 'null', '删除评论', 'deleteComment', '2021-08-16 15:38:05');
INSERT INTO `log` VALUES ('51', 'null', '删除评论', 'deleteComment', '2021-08-16 15:38:07');
INSERT INTO `log` VALUES ('52', 'null', '删除评论', 'deleteComment', '2021-08-16 15:38:09');
INSERT INTO `log` VALUES ('53', 'null', '获取用户列表', 'userList', '2021-08-16 15:41:57');
INSERT INTO `log` VALUES ('54', 'null', '获取用户列表', 'userList', '2021-08-16 15:42:25');
INSERT INTO `log` VALUES ('55', 'null', '获取用户列表', 'userList', '2021-08-16 15:42:35');
INSERT INTO `log` VALUES ('56', 'null', '获取用户列表', 'userList', '2021-08-16 15:42:37');
INSERT INTO `log` VALUES ('57', 'null', '更新用户', 'updateUser', '2021-08-16 15:42:47');
INSERT INTO `log` VALUES ('58', 'null', '获取用户列表', 'userList', '2021-08-16 15:42:47');
INSERT INTO `log` VALUES ('59', 'myq', '删除评论', 'deleteComment', '2021-08-16 17:54:08');
INSERT INTO `log` VALUES ('60', 'myq', '删除评论', 'deleteComment', '2021-08-16 17:57:19');
INSERT INTO `log` VALUES ('61', 'myq', '删除评论', 'deleteComment', '2021-08-16 18:00:29');
INSERT INTO `log` VALUES ('62', 'myq', '删除评论', 'deleteComment', '2021-08-16 18:03:34');
INSERT INTO `log` VALUES ('63', 'myq', '删除评论', 'deleteComment', '2021-08-16 20:37:13');
INSERT INTO `log` VALUES ('64', 'myq', '删除评论', 'deleteComment', '2021-08-16 20:52:50');
INSERT INTO `log` VALUES ('65', 'myq', '删除评论', 'deleteComment', '2021-08-16 20:53:12');
INSERT INTO `log` VALUES ('66', 'myq', '删除评论', 'deleteComment', '2021-08-16 20:53:58');

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=165 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_log
-- ----------------------------
INSERT INTO `operation_log` VALUES ('19', 'myq', '2021-08-18 15:54:56', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"myq\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('20', 'myq', '2021-08-18 15:55:07', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('21', 'myq', '2021-08-18 15:55:09', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=2&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('22', 'myq', '2021-08-18 15:55:10', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=3&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('23', 'myq', '2021-08-18 15:56:07', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=202&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('24', 'myq', '2021-08-18 15:56:31', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('25', 'myq', '2021-08-18 15:56:34', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"r\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('26', 'myq', '2021-08-18 15:56:38', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('27', 'myq', '2021-08-18 15:56:45', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"myq\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('28', 'root', '2021-08-18 17:38:42', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('29', 'root', '2021-08-18 17:38:55', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('30', 'root', '2021-08-18 17:38:57', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=3&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('31', 'root', '2021-08-18 17:38:57', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=2&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('32', 'root', '2021-08-18 17:38:58', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=3&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('33', 'root', '2021-08-18 17:38:58', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=4&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('34', 'root', '2021-08-18 17:39:05', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=4&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('35', 'root', '2021-08-18 17:39:08', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=5&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('36', 'root', '2021-08-18 17:39:35', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=5&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('37', 'root', '2021-08-18 17:39:46', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=4&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('38', 'root', '2021-08-18 17:40:59', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=204&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('39', 'root', '2021-08-18 17:41:45', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('40', 'root', '2021-08-18 18:14:17', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('41', 'root', '2021-08-18 18:20:30', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('42', 'root', '2021-08-18 18:21:11', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=42&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('43', 'root', '2021-08-18 18:21:37', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=35&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('44', 'root', '2021-08-18 18:24:04', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=207&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('45', 'root', '2021-08-18 18:56:37', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.152', '192.168.1.152', '后台管理');
INSERT INTO `operation_log` VALUES ('46', 'root', '2021-08-18 22:50:31', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('47', 'root', '2021-08-18 22:51:23', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('48', 'root', '2021-08-18 22:51:33', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('49', 'root', '2021-08-18 22:52:21', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('50', 'root', '2021-08-18 22:58:58', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=91&,[IP]:192.168.1.126', '192.168.1.126', '文章管理');
INSERT INTO `operation_log` VALUES ('51', 'root', '2021-08-18 22:59:24', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=205&,[IP]:192.168.1.126', '192.168.1.126', '评论管理');
INSERT INTO `operation_log` VALUES ('52', 'root', '2021-08-18 23:21:53', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('53', 'root', '2021-08-18 23:22:35', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:this=208&,[IP]:192.168.1.126', '192.168.1.126', '评论管理');
INSERT INTO `operation_log` VALUES ('54', 'root', '2021-08-18 23:25:10', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('55', 'root', '2021-08-18 23:25:13', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('56', 'root', '2021-08-18 23:25:14', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=3&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('57', 'root', '2021-08-18 23:25:15', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=5&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('58', 'root', '2021-08-18 23:25:16', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=4&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('59', 'root', '2021-08-18 23:25:37', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:this=211&,[IP]:192.168.1.126', '192.168.1.126', '评论管理');
INSERT INTO `operation_log` VALUES ('60', 'root', '2021-08-18 23:25:54', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('61', 'root', '2021-08-18 23:25:55', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=3&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('62', 'root', '2021-08-18 23:29:29', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.126', '192.168.1.126', '后台管理');
INSERT INTO `operation_log` VALUES ('63', 'root', '2021-08-18 23:29:39', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.1.126', '192.168.1.126', '用户管理');
INSERT INTO `operation_log` VALUES ('64', 'root', '2021-08-18 23:30:10', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:this=215&,[IP]:192.168.1.126', '192.168.1.126', '评论管理');
INSERT INTO `operation_log` VALUES ('65', 'root', '2021-08-19 12:56:45', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('66', 'root', '2021-08-19 12:57:07', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=216&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('67', 'root', '2021-08-19 12:59:31', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('68', 'root', '2021-08-19 13:00:04', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=217&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('69', 'root', '2021-08-19 13:02:44', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=218&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('70', 'root', '2021-08-19 13:04:30', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=219&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('71', 'root', '2021-08-19 13:05:59', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=220&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('72', 'root', '2021-08-19 13:08:41', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('73', 'root', '2021-08-19 13:08:48', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('74', 'root', '2021-08-19 13:09:50', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=221&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('75', 'root', '2021-08-19 13:10:10', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=222&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('76', 'root', '2021-08-19 13:10:39', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=223&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('77', 'root', '2021-08-19 13:12:34', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('78', 'root', '2021-08-19 13:12:49', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=225&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('79', 'root', '2021-08-19 13:13:19', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=226&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('80', 'root', '2021-08-19 13:13:53', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=227&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('81', 'root', '2021-08-19 13:14:38', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=228&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('82', 'root', '2021-08-19 13:15:44', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=229&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('83', 'root', '2021-08-19 13:16:42', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('84', 'root', '2021-08-19 13:17:01', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=230&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('85', 'root', '2021-08-19 13:17:35', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=231&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('86', 'root', '2021-08-19 13:18:02', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=232&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('87', 'root', '2021-08-19 13:18:28', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('88', 'root', '2021-08-19 13:18:43', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=233&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('89', 'root', '2021-08-19 13:19:17', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=234&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('90', 'root', '2021-08-19 13:20:18', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=235&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('91', 'root', '2021-08-19 13:20:55', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=236&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('92', 'root', '2021-08-19 13:22:41', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('93', 'root', '2021-08-19 13:22:45', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('94', 'root', '2021-08-19 13:22:53', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=237&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('95', 'root', '2021-08-19 13:23:14', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=238&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('96', 'root', '2021-08-19 13:23:53', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=239&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('97', 'root', '2021-08-19 13:26:00', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=240&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('98', 'root', '2021-08-20 09:08:12', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('99', 'root', '2021-08-20 09:10:26', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('100', 'root', '2021-08-20 09:11:56', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('101', 'root', '2021-08-20 09:15:34', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('102', 'root', '2021-08-20 09:15:42', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=86&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('103', 'root', '2021-08-20 09:16:19', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=92&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('104', 'root', '2021-08-20 09:17:18', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=93&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('105', 'root', '2021-08-20 09:17:51', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=242&,[IP]:192.168.72.1', '192.168.72.1', '评论管理');
INSERT INTO `operation_log` VALUES ('106', 'root', '2021-08-20 11:11:20', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('107', 'root', '2021-08-20 11:11:43', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=112&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('108', 'root', '2021-08-20 12:59:34', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('109', 'root', '2021-08-20 13:02:16', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('110', 'root', '2021-08-20 13:02:19', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.72.1', '192.168.72.1', '用户管理');
INSERT INTO `operation_log` VALUES ('111', 'root', '2021-08-20 13:02:36', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=115&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('112', 'root', '2021-08-20 13:04:06', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('113', 'root', '2021-08-20 13:04:30', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=114&,[IP]:192.168.72.1', '192.168.72.1', '文章管理');
INSERT INTO `operation_log` VALUES ('114', 'root', '2021-08-20 13:05:20', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('115', 'root', '2021-08-20 13:05:59', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('116', 'root', '2021-08-20 13:07:18', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('117', 'root', '2021-08-20 15:16:00', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:172.20.10.3', '172.20.10.3', '后台管理');
INSERT INTO `operation_log` VALUES ('118', 'root', '2021-08-20 15:18:48', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:172.20.10.2', '172.20.10.2', '后台管理');
INSERT INTO `operation_log` VALUES ('119', 'root', '2021-08-20 15:19:06', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=250&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('120', 'root', '2021-08-20 15:19:54', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=253&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('121', 'root', '2021-08-20 15:21:52', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=254&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('122', 'root', '2021-08-20 15:23:56', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:172.20.10.2', '172.20.10.2', '后台管理');
INSERT INTO `operation_log` VALUES ('123', 'root', '2021-08-20 15:24:13', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=255&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('124', 'root', '2021-08-20 15:24:41', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=256&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('125', 'root', '2021-08-20 15:25:21', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=247&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('126', 'root', '2021-08-20 15:26:06', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=201&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('127', 'root', '2021-08-20 15:28:41', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:172.20.10.2', '172.20.10.2', '后台管理');
INSERT INTO `operation_log` VALUES ('128', 'root', '2021-08-20 15:29:48', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=257&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('129', 'root', '2021-08-20 15:30:30', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=246&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('130', 'root', '2021-08-20 15:30:41', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=245&,[IP]:172.20.10.2', '172.20.10.2', '评论管理');
INSERT INTO `operation_log` VALUES ('131', 'root', '2021-08-20 16:38:49', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('132', 'root', '2021-08-20 19:30:08', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:172.20.10.2', '172.20.10.2', '后台管理');
INSERT INTO `operation_log` VALUES ('133', 'root', '2021-08-20 19:30:13', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('134', 'root', '2021-08-20 19:30:17', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=2&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('135', 'root', '2021-08-20 19:30:17', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=3&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('136', 'root', '2021-08-20 19:30:18', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=4&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('137', 'root', '2021-08-20 19:30:19', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=5&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('138', 'root', '2021-08-20 19:30:25', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('139', 'root', '2021-08-20 19:30:27', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"q\"&pageNo=1&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('140', 'root', '2021-08-20 19:30:30', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"q\"&pageNo=1&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('141', 'root', '2021-08-20 19:30:34', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:172.20.10.2', '172.20.10.2', '用户管理');
INSERT INTO `operation_log` VALUES ('142', 'root', '2021-08-20 19:31:44', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=127&,[IP]:172.20.10.2', '172.20.10.2', '文章管理');
INSERT INTO `operation_log` VALUES ('143', 'root', '2021-08-20 20:16:07', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.206.1', '192.168.206.1', '后台管理');
INSERT INTO `operation_log` VALUES ('144', 'root', '2021-08-20 20:16:15', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.206.1', '192.168.206.1', '用户管理');
INSERT INTO `operation_log` VALUES ('145', 'root', '2021-08-20 20:16:19', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=1&pageSize=3&,[IP]:192.168.206.1', '192.168.206.1', '用户管理');
INSERT INTO `operation_log` VALUES ('146', 'root', '2021-08-20 20:16:20', '查看用户列表', '[类名]:com.boot.controller.hswcontroller.UserController,[方法]:listUser,[参数]:str=\"\"&pageNo=6&pageSize=3&,[IP]:192.168.206.1', '192.168.206.1', '用户管理');
INSERT INTO `operation_log` VALUES ('147', 'root', '2021-08-20 20:44:47', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.206.1', '192.168.206.1', '后台管理');
INSERT INTO `operation_log` VALUES ('148', 'root', '2021-08-20 20:45:29', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=130&,[IP]:192.168.206.1', '192.168.206.1', '文章管理');
INSERT INTO `operation_log` VALUES ('149', 'root', '2021-08-20 20:51:58', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.206.1', '192.168.206.1', '后台管理');
INSERT INTO `operation_log` VALUES ('150', 'root', '2021-08-20 20:52:13', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=131&,[IP]:192.168.206.1', '192.168.206.1', '文章管理');
INSERT INTO `operation_log` VALUES ('151', 'root', '2021-08-20 20:53:40', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=132&,[IP]:192.168.206.1', '192.168.206.1', '文章管理');
INSERT INTO `operation_log` VALUES ('152', 'root', '2021-08-20 21:00:49', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.206.1', '192.168.206.1', '后台管理');
INSERT INTO `operation_log` VALUES ('153', 'root', '2021-08-20 21:02:04', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=268&,[IP]:192.168.206.1', '192.168.206.1', '评论管理');
INSERT INTO `operation_log` VALUES ('154', 'root', '2021-08-20 21:04:22', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.206.1', '192.168.206.1', '后台管理');
INSERT INTO `operation_log` VALUES ('155', 'root', '2021-08-20 21:04:31', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=269&,[IP]:192.168.206.1', '192.168.206.1', '评论管理');
INSERT INTO `operation_log` VALUES ('156', 'root', '2021-08-20 23:24:37', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('157', 'root', '2021-08-21 12:36:37', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.190', '192.168.1.190', '后台管理');
INSERT INTO `operation_log` VALUES ('158', 'root', '2021-08-21 13:05:36', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.72.1', '192.168.72.1', '后台管理');
INSERT INTO `operation_log` VALUES ('159', 'root', '2021-08-21 13:48:18', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.160', '192.168.1.160', '后台管理');
INSERT INTO `operation_log` VALUES ('160', 'root', '2021-08-21 13:48:44', '删除评论', '[类名]:com.boot.controller.lmcontroller.RealCommentController,[方法]:deleteComment,[参数]:id=271&,[IP]:192.168.1.160', '192.168.1.160', '评论管理');
INSERT INTO `operation_log` VALUES ('161', 'root', '2021-08-21 14:13:55', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.160', '192.168.1.160', '后台管理');
INSERT INTO `operation_log` VALUES ('162', 'root', '2021-08-21 16:02:57', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.160', '192.168.1.160', '后台管理');
INSERT INTO `operation_log` VALUES ('163', 'root', '2021-08-21 16:04:11', '删除文章', '[类名]:com.boot.controller.lmcontroller.ArticleController,[方法]:deleteArticleById,[参数]:id=144&,[IP]:192.168.1.160', '192.168.1.160', '文章管理');
INSERT INTO `operation_log` VALUES ('164', 'root', '2021-08-21 16:05:09', '后台登录', '[类名]:com.boot.controller.lmcontroller.UserListController,[方法]:login,[参数]:pwd=\"123456\"&username=\"root\"&,[IP]:192.168.1.160', '192.168.1.160', '后台管理');

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) NOT NULL,
  `userPassword` varchar(50) NOT NULL,
  `nickname` varchar(50) DEFAULT NULL COMMENT '网名',
  `avatar` varchar(255) DEFAULT NULL,
  `backgroundImage` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `perms` varchar(10) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '*dd1o112CB31##d162BB*B*#1/30161*', '草绿的本子', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgsFCAZ70kAAAJX7BY4AU453.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgsC2AHCsqAAC9MfjZDiM302.jpg', '19981487228', null, '男', 'whdkasjdh wiu uwidadasfff', 'admin', null, null);
INSERT INTO `user` VALUES ('2', 'zhangsiyuan', 'C03o5C500B/CB4o7BCo3AC435Ao105*B', 'zsy', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgnaGAPUTaAABI8D6PYrA052.jpg', 'http://47.108.238.23:80/group1/M00/00/01/rBYSRGEQf1CAA53wAAC-IbM4jvg331.jpg', '15680453170', null, '男', null, '普通用户', null, null);
INSERT INTO `user` VALUES ('3', 'test', '47#o2**7B1#31#2#/2076oC/64#*Bd3*', '明净的生气', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgudqAPx53AAAAAAAAAAA353.jpg', '19981487228', null, '女', 'wdasdasdsa', '普通用户', '2021-07-19 23:12:54', '2021-07-19 23:12:54');
INSERT INTO `user` VALUES ('4', 'lmxxx', '*3#o#30d3677B#*53*o/do0734322/d5', '未来的时空', 'http://47.108.238.23:80/group1/M00/00/01/rBYSRGEc7X6APKL5AAJ9WDVmbeU990.jpg', 'http://47.108.238.23:80/group1/M00/00/01/rBYSRGESX8aAewnxAAAuiCu3FyM11.webp', '19981487228', null, '女', 'xzczczxczx', '普通用户', '2021-07-20 21:23:40', '2021-07-20 21:23:40');
INSERT INTO `user` VALUES ('6', 'myq', '76416*B0C#d65*0oA474770oB#d57635', 'mmmmmm', 'http://47.108.238.23:80/group1/M00/00/01/rBYSRGEPfNiAPPQSAAJOOiMUY_g029.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', '19981487228', null, null, null, 'admin', '2021-07-23 14:32:42', '2021-07-23 14:32:42');
INSERT INTO `user` VALUES ('8', 'admin1', '*552oA72517d2066#7/3C30*d05o*/1o', 'xxxxx', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', '19981487228', null, null, null, '普通用户', '2021-08-10 19:33:43', '2021-08-10 19:33:43');
INSERT INTO `user` VALUES ('10', 'hsw1', 'd#*03dd33*//2//31/C5d050A#0#4dA5', '着急的叶片', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', '19981487228', null, '男', 'dasdasdas', '普通用户', '2021-08-13 10:00:02', '2021-08-13 10:00:02');
INSERT INTO `user` VALUES ('11', 'test3', '#*05155/d/4/7C6373do7o344d#065d*', 'test3xx', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', '19981487228', null, null, null, '普通用户', '2021-08-13 16:27:58', '2021-08-13 16:27:58');
INSERT INTO `user` VALUES ('12', 'zsy', '416B66#AC1#o#7#3#7A71A6*doA5d4/*', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgtmmAcF6oAAwBpGapg8g501.jpg', '15680453170', '912462059@qq.com', null, null, '普通用户', '2021-08-13 18:05:56', '2021-08-13 18:05:56');
INSERT INTO `user` VALUES ('13', 'test5', '2C//##4AA43#7#3AAdo#177ddCoAA4d4', 'test5', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', null, null, null, null, '普通用户', '2021-08-16 15:42:35', '2021-08-16 15:42:35');
INSERT INTO `user` VALUES ('14', 'dasdsa', '6BC1B047C6A/B*24#02C6o3325Ad0*0#', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROyAFNe5AAC9MfjZDiM973.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', '19981487228', '1109191723@qq.com', null, null, '普通用户', '2021-08-18 15:37:15', '2021-08-18 15:37:15');
INSERT INTO `user` VALUES ('15', 'test2', 'A0660#2B103*525d6B4/45#34#23/4BA', '奇妙的年代', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfjNOADBuWAAC9MfjZDiM101.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfjMKAbixgAAAJX7BY4AU282.jpg', '13684083368', '154452900@qq.com', '', '大家和呀ddddd', '普通用户', '2021-08-18 17:26:15', '2021-08-18 17:26:15');
INSERT INTO `user` VALUES ('16', 'test6', '7BB1345o1BC#4413#2/o6/55*d4*3#2#', 'xxxxxxxxx', 'http://47.108.238.23:80/group1/M00/00/01/rBYSRGEc2DeAC2Z4AABxlVKfWkQ461.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfROiAXgICAAM9djqhZ1w879.jpg', null, null, null, null, '普通用户', '2021-08-18 17:39:05', '2021-08-18 17:39:05');
INSERT INTO `user` VALUES ('17', 'test4', '64552B11#6*300*26d0063B#5o*dd/64', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgTZCAXqUpAAM9djqhZ1w339.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgTYuARd0EAAAJX7BY4AU484.jpg', '19981487228', '1109191723@qq.com', '女', 'dsadasdsa', '普通用户', '2021-08-20 09:06:58', '2021-08-20 09:06:58');
INSERT INTO `user` VALUES ('18', 'qiweikai', '#//AB40dA*073B3A#o/o143*63d701/o', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgTZCAXqUpAAM9djqhZ1w339.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfjKiALBqXAAaZeT765Bk912.jpg', '13882183772', '154452900@qq.com', null, null, '普通用户', '2021-08-20 19:05:47', '2021-08-20 19:05:47');
INSERT INTO `user` VALUES ('19', 'zsy11', '616o1doo#o155oC4d611ACC2B0C4435A', '不快的友好', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEfld-AGvVXAAC9MfjZDiM389.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEflVyARfBgAAM9djqhZ1w577.jpg', '15680453170', '912462059@qq.com', null, null, '普通用户', '2021-08-20 19:42:17', '2021-08-20 19:42:17');
INSERT INTO `user` VALUES ('20', 'zhangsan', 'BdC*41710724o/6511Cd*#2C52416AA1', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgTZCAXqUpAAM9djqhZ1w339.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEflVyARfBgAAM9djqhZ1w577.jpg', '13037769060', '2608934964@qq.com', null, null, '普通用户', '2021-08-20 20:06:49', '2021-08-20 20:06:49');
INSERT INTO `user` VALUES ('21', 'sfsf', 'C0574BAB#03/o#d5727143*CA0/BA#o*', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgTZCAXqUpAAM9djqhZ1w339.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEflVyARfBgAAM9djqhZ1w577.jpg', null, null, null, null, '普通用户', '2021-08-20 20:16:18', '2021-08-20 20:16:18');
INSERT INTO `user` VALUES ('22', 'test7', '#54odC6o5*5o/124oBo#13A6dA240Cdd', '明净的难题', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEghVCAVkF7AADx1AF-E2E368.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEghUmAWnbvAAM9djqhZ1w531.jpg', '13684083368', '1109191723@qq.com', '男', 'dasdasdasda\ndasdasdas\nasdfafasfasfas', '普通用户', '2021-08-21 12:46:45', '2021-08-21 12:46:45');
INSERT INTO `user` VALUES ('23', '你爸爸', '064551A10dBd003#17C57254B35d/d4C', null, null, null, '15608023425', '2622442852@qq.com', null, null, '普通用户', '2021-08-21 16:14:30', '2021-08-21 16:14:30');
INSERT INTO `user` VALUES ('24', '你爸爸', '064551A10dBd003#17C57254B35d/d4C', null, null, null, '15608023425', '2622442852@qq.com', null, null, '普通用户', '2021-08-21 16:14:31', '2021-08-21 16:14:31');
INSERT INTO `user` VALUES ('25', '你爸爸', '064551A10dBd003#17C57254B35d/d4C', null, null, null, '15608023425', '2622442852@qq.com', null, null, '普通用户', '2021-08-21 16:14:32', '2021-08-21 16:14:32');
INSERT INTO `user` VALUES ('26', '188', '47BdB/BBAoC2C752#CC*320*64dA#C10', null, 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgtzWAMs33AAJYk7ufPfk615.jpg', 'http://47.108.238.23:80/group1/M00/00/02/rBYSRGEgtxKAWGtIAADMEWNojUM627.jpg', '15608023425', '2622442852@qq.com', null, null, '普通用户', '2021-08-21 16:16:00', '2021-08-21 16:16:00');
