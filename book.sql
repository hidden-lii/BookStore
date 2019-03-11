/*
 Navicat Premium Data Transfer

 Source Server         : Lii
 Source Server Type    : MySQL
 Source Server Version : 100137
 Source Host           : localhost:3306
 Source Schema         : book

 Target Server Type    : MySQL
 Target Server Version : 100137
 File Encoding         : 65001

 Date: 20/12/2018 15:13:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS `book`;
CREATE DATABASE `book`;
USE `book`;
-- ----------------------------
-- Table structure for admin_info
-- ----------------------------
DROP TABLE IF EXISTS `admin_info`;
CREATE TABLE `admin_info`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pwd` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of admin_info
-- ----------------------------
INSERT INTO `admin_info` VALUES (1, 'lii', '111', 1);

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名称',
  `author` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tid` int(4) NULL DEFAULT NULL COMMENT '图书类别',
  `press` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书出版社',
  `pic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书图片',
  `num` int(4) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '图书数量',
  `price` decimal(10, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '图书价格',
  `intro` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图书简介',
  `status` int(4) NULL DEFAULT NULL COMMENT '图书状态',
  `bigpic` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书大图',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `book_info_`(`tid`) USING BTREE,
  CONSTRAINT `book_info_` FOREIGN KEY (`tid`) REFERENCES `type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES (1, '18120301', '雪中悍刀行', '烽火戏诸侯', 1, '江苏文艺出版社', 'xzhdx.jpg', 0100, 00000125.25, '该小说讲述一个关于庙堂权争与刀剑交错的时代，一个暗潮涌动粉墨登场的江湖。', 1, 'bxzhdx.jpg');
INSERT INTO `book_info` VALUES (2, '18120302', '斗罗大陆', '唐家三少', 5, '太白文艺出版社', 'dldl.jpg', 0200, 00000325.60, '唐门外门弟子唐三，因偷学内门绝学为唐门所不容，跳崖明志时却发现没有死，反而以另外一个身份来到了另一个世界，一个属于武魂的世界，名叫斗罗大陆。这里没有魔法，没有斗气，没有武术，却有神奇的武魂。小小的唐三在圣魂村开始了他的魂师修炼之路，并萌生了振兴唐门的梦想。当唐门暗器来到斗罗大陆，当唐三武魂觉醒，他能否在这片武魂的世界再铸唐门的辉煌？', 1, 'bdldl.jpg');
INSERT INTO `book_info` VALUES (3, '18120303', '将夜', '猫腻', 5, '新世纪出版社', 'jy.jpg', 0150, 00000316.50, '这本小说讲述的是一段可歌可泣可笑可爱的草根崛起史，一个物质要求宁滥勿缺的开朗少年行。小说基于修真世界，却又胜于修真，讲述了人定胜天，花开彼岸天的历史，引人深思。', 1, 'bjy.jpg');

-- ----------------------------
-- Table structure for functions
-- ----------------------------
DROP TABLE IF EXISTS `functions`;
CREATE TABLE `functions`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '功能菜单名',
  `parentid` int(4) NULL DEFAULT NULL,
  `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isleaf` bit(1) NULL DEFAULT NULL,
  `nodeorder` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '订单明细id',
  `oid` int(4) NULL DEFAULT NULL COMMENT '订单id',
  `bid` int(4) NULL DEFAULT NULL COMMENT '图书id',
  `num` int(4) NULL DEFAULT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_detail_ibfk_1`(`oid`) USING BTREE,
  INDEX `order_detail_ibfk_2`(`bid`) USING BTREE,
  CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `order_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_detail_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `book_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1, 1, 1, 2);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `uid` int(4) NULL DEFAULT NULL,
  `status` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ordertime` datetime(0) NULL DEFAULT NULL,
  `orderprice` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_info_ibfk_1`(`uid`) USING BTREE,
  CONSTRAINT `order_info_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user_info` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (1, 1, '已付款', '2018-12-13 00:00:00', 250.50);

-- ----------------------------
-- Table structure for powers
-- ----------------------------
DROP TABLE IF EXISTS `powers`;
CREATE TABLE `powers`  (
  `aid` int(4) NOT NULL,
  `fid` int(4) NOT NULL,
  PRIMARY KEY (`aid`, `fid`) USING BTREE,
  INDEX `powers_ibfk_2`(`fid`) USING BTREE,
  CONSTRAINT `powers_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `admin_info` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `powers_ibfk_2` FOREIGN KEY (`fid`) REFERENCES `functions` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of type
-- ----------------------------
INSERT INTO `type` VALUES (1, '武侠');
INSERT INTO `type` VALUES (2, '历史');
INSERT INTO `type` VALUES (3, '军事');
INSERT INTO `type` VALUES (4, '仙侠');
INSERT INTO `type` VALUES (5, '玄幻');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `userName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `password` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  `realName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户真实姓名',
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户性别',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户住址',
  `question` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密保问题',
  `answer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密保答案',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `regDate` datetime(0) NULL DEFAULT NULL COMMENT '用户注册时间',
  `status` int(4) NULL DEFAULT 1 COMMENT '用户状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'lii', '123456', '李一', '男', '山东省青岛市市北区', '你的出生年月是？', '1997-11', '847233149@qq.com', '2018-12-10 00:00:00', 1);
INSERT INTO `user_info` VALUES (2, 'ld', '1', '张三', '男', '山东省青岛市', '我的性别', '男生', '123@book.com', '2018-12-18 16:35:26', 1);

SET FOREIGN_KEY_CHECKS = 1;
