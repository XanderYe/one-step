/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : one_step

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 01/07/2020 16:44:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `expired` tinyint(11) NULL DEFAULT NULL,
  `auto` tinyint(255) NULL DEFAULT NULL,
  `expiration_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '每月黑钻等级礼包', 'http://dnf.qq.com/act/blackDiamond/gift.shtml', 0, 1, NULL);
INSERT INTO `activity` VALUES (2, '觉醒.新深渊来袭—地下城与勇士—心悦俱乐部', 'http://xinyue.qq.com/act/a20200303dnfbjbb/index_h5.html', 1, 1, NULL);
INSERT INTO `activity` VALUES (3, '地下城与勇士心悦游戏特权专区-腾讯游戏', 'http://xinyue.qq.com/act/a20181101rights/', 0, 1, NULL);
INSERT INTO `activity` VALUES (6, '百级来袭 邀你来战-DNF官网-腾讯游戏', 'http://dnf.qq.com/cp/a20200228video/index.html', 1, 1, NULL);

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `qq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `character` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `auth_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 580 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for payload
-- ----------------------------
DROP TABLE IF EXISTS `payload`;
CREATE TABLE `payload`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_id` int(11) NULL DEFAULT NULL,
  `interface_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `method` int(11) NULL DEFAULT NULL,
  `headers` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `times` int(11) NULL DEFAULT NULL,
  `timeout` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payload
-- ----------------------------
INSERT INTO `payload` VALUES (1, 1, 'http://dnf.game.qq.com/mtask/bindRole/bind', 0, NULL, 'r=${random}&sArea=${areaId}&sRoleId=${characterNo}&serviceType=dnf&channelId=1', '黑钻绑定', 1, 1);
INSERT INTO `payload` VALUES (2, 1, 'http://dnf.game.qq.com/mtask/lottery/', 0, NULL, 'r=${random}&serviceType=dnf&channelId=1&actIdList=44c24e', '黑钻礼包领取', 1, 1);
INSERT INTO `payload` VALUES (3, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'classname=3&iActivityId=290824&iFlowId=649342&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue&sServiceType=tgclub', '心悦深渊来袭投票', 1, 2);
INSERT INTO `payload` VALUES (4, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, '', 'iActivityId=290824&iFlowId=649341&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=${random}&sServiceDepartment=xinyue&sServiceType=tgclub', '心悦深渊来袭登录送抽奖', 1, 7);
INSERT INTO `payload` VALUES (5, 2, 'https://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649015&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭抽奖', 2, 7);
INSERT INTO `payload` VALUES (6, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=0&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭签到第1天', 1, 7);
INSERT INTO `payload` VALUES (7, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=1&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue\r\n', '心悦深渊来袭签到第2天', 1, 7);
INSERT INTO `payload` VALUES (8, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=2&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue\r\n', '心悦深渊来袭签到第3天', 1, 7);
INSERT INTO `payload` VALUES (9, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=3&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue\r\n', '心悦深渊来袭签到第4天', 1, 7);
INSERT INTO `payload` VALUES (10, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=4&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭签到第5天', 1, 7);
INSERT INTO `payload` VALUES (11, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=5&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue\r\n', '心悦深渊来袭签到第6天', 1, 7);
INSERT INTO `payload` VALUES (12, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&qd=6&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649020&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭签到第7天', 1, 7);
INSERT INTO `payload` VALUES (13, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649021&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭在线30min', 1, 7);
INSERT INTO `payload` VALUES (14, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&zx=3&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649023&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭累计3天', 1, 7);
INSERT INTO `payload` VALUES (15, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&zx=6&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649023&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭累计6天', 1, 7);
INSERT INTO `payload` VALUES (16, 2, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=290824&sServiceDepartment=xinyue&sSDID=625e6f7497f422cb913afd6e5e24e253&sMiloTag=${sMiloTag}&_=${random}', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&zx=9&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290824&iFlowId=649023&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200303dnfbjbb%2Findex_h5.html&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&sServiceDepartment=xinyue', '心悦深渊来袭累计9天', 1, 1);
INSERT INTO `payload` VALUES (19, 6, 'http://x6m5.ams.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dnf&iActivityId=290134&sServiceDepartment=group_3&sSDID=47786e4e3b93c3215f505b62458d6fb7&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=dnf&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290134&iFlowId=647547&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228video%2F&eas_refer=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228videom%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=group_3&xhrPostKey=${random}', '腾讯视频在线30min', 1, 2);
INSERT INTO `payload` VALUES (20, 6, 'http://x6m5.ams.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dnf&iActivityId=290134&sServiceDepartment=group_3&sSDID=47786e4e3b93c3215f505b62458d6fb7&sMiloTag=AMS-MILO-290134-647550-o0315695355-1586230700454-qn97Xk&isXhrPost=true', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=dnf&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290134&iFlowId=647550&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228video%2F&eas_refer=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228videom%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=group_3&xhrPostKey=${random}\r\n', '腾讯视频在线3天', 1, 2);
INSERT INTO `payload` VALUES (21, 6, 'http://x6m5.ams.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dnf&iActivityId=290134&sServiceDepartment=group_3&sSDID=47786e4e3b93c3215f505b62458d6fb7&sMiloTag=AMS-MILO-290134-647556-o0315695355-1586230708889-2Oi65G&isXhrPost=true', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=dnf&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290134&iFlowId=647556&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228video%2F&eas_refer=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228videom%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=group_3&xhrPostKey=${random}', '腾讯视频在线6天', 1, 2);
INSERT INTO `payload` VALUES (22, 6, 'http://x6m5.ams.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dnf&iActivityId=290134&sServiceDepartment=group_3&sSDID=47786e4e3b93c3215f505b62458d6fb7&sMiloTag=AMS-MILO-290134-647557-o0315695355-1586230849207-kZiFBu&isXhrPost=true', 1, NULL, 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=dnf&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=290134&iFlowId=647557&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228video%2F&eas_refer=http%3A%2F%2Fdnf.qq.com%2Fcp%2Fa20200228videom%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=group_3&xhrPostKey=${random}', '腾讯视频在线9天', 1, 1);
INSERT INTO `payload` VALUES (26, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512437\r\n&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务1(双倍6分)', 1, 5);
INSERT INTO `payload` VALUES (27, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512435\r\n&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务2(双倍6分)', 1, 5);
INSERT INTO `payload` VALUES (28, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512424\r\n\r\n&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务3(免做3分)', 1, 5);
INSERT INTO `payload` VALUES (29, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512398\r\n&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务1(3分)', 1, 5);
INSERT INTO `payload` VALUES (30, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512400\r\n&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务2(3分)', 1, 5);
INSERT INTO `payload` VALUES (31, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512399\r\n\r\n\r\n&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务3(2分)', 1, 5);
INSERT INTO `payload` VALUES (32, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512397&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务4(2分)', 1, 5);
INSERT INTO `payload` VALUES (33, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, NULL, 'iActivityId=166962&iFlowId=512389&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223', '心悦任务5(1分)', 1, 5);
INSERT INTO `payload` VALUES (34, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, '', 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=514385&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D1a5648d1-978f-4d26-8f72-c0c69bc5ba15%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_158674916698941', '心悦组队任务', 1, 5);
INSERT INTO `payload` VALUES (35, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, '', 'gameId=&sArea=&iSex=&sRoleId=&iGender=&lqlevel=1&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=673270&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_158683349923746', '心悦特邀月礼包', 1, 5);
INSERT INTO `payload` VALUES (36, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, '', 'gameId=&sArea=&iSex=&sRoleId=&iGender=&lqlevel=5&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=673269&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_158683349923746', '心悦心悦1月礼包', 1, 5);
INSERT INTO `payload` VALUES (37, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, '', 'gameId=&sArea=&iSex=&sRoleId=&iGender=&lqlevel=1&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=513581&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_158683350188058', '心悦特邀周礼包', 1, 5);
INSERT INTO `payload` VALUES (38, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, '', 'gameId=&sArea=&iSex=&sRoleId=&iGender=&lqlevel=1&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=513573&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_158683350188058', '心悦心悦1周礼包', 1, 5);
INSERT INTO `payload` VALUES (39, 3, 'http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true', 1, '', 'gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=513585&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_158683350431316', '心悦月领取周期礼包达到5个', 1, 2);

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES (1, '1.3', '强烈建议更新！修改活动接口地址和更新接口地址；修复接口报错导致程序无法启动的bug', '2020-04-04 19:22:00');
INSERT INTO `version` VALUES (10, '1.4', '更新活动领取结果显示，增加提示', '2020-04-07 14:21:08');
INSERT INTO `version` VALUES (11, '1.5', '增加异步执行功能，添加执行次数', '2020-04-08 16:09:11');
INSERT INTO `version` VALUES (12, '1.6', '添加多线程领取功能，限制线程为10条', '2020-04-10 16:38:05');
INSERT INTO `version` VALUES (13, '1.7', '修复心悦积分访问失败的bug', '2020-04-11 11:30:43');
INSERT INTO `version` VALUES (14, '1.8', '修复心悦来袭签到少一次的问题；添加捐赠', '2020-04-13 11:25:27');
INSERT INTO `version` VALUES (15, '1.9', '新增心悦批量兑换功能，在菜单栏的工具中', '2020-04-14 10:37:33');
INSERT INTO `version` VALUES (16, '2.0', '修复心悦兑换获取不到勇士币和成就点的bug；添加下载进度条', '2020-04-15 09:24:12');
INSERT INTO `version` VALUES (17, '2.1', '移动检查更新方法到登录界面，不用登录也能更新', '2020-04-16 08:59:12');
INSERT INTO `version` VALUES (18, '2.2', '活动增加自动和手动分类', '2020-04-21 13:25:31');
INSERT INTO `version` VALUES (19, '2.3', '支持心悦活动角色绑定功能', '2020-04-22 13:43:03');
INSERT INTO `version` VALUES (20, '2.4', '修复一些bug', '2020-04-24 09:26:11');
INSERT INTO `version` VALUES (21, '2.5', '优化接口、更新授权、添加公告', '2020-05-22 17:12:51');
INSERT INTO `version` VALUES (25, '2.6', '增加道聚城功能', '2020-05-29 16:46:10');
INSERT INTO `version` VALUES (26, '2.6', '修复授权时间显示错误的bug', '2020-05-30 10:01:56');
INSERT INTO `version` VALUES (27, '2.8', '增加道聚城许愿功能', '2020-06-03 15:22:58');
INSERT INTO `version` VALUES (28, '2.9', '修改道聚城许愿道具', '2020-06-04 08:59:32');
INSERT INTO `version` VALUES (29, '3.0', '道聚城支持lol许愿', '2020-06-05 09:49:34');

SET FOREIGN_KEY_CHECKS = 1;
