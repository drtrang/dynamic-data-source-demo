CREATE DATABASE IF NOT EXISTS `dynamic_master` DEFAULT CHARACTER SET utf8;

USE `dynamic_master`;

/*Table structure for table `m_base_code` */

DROP TABLE IF EXISTS `m_base_code`;

CREATE TABLE `m_base_code` (
  `id` BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code_type` VARCHAR(64) NOT NULL COMMENT '编码类型',
  `parent_code` VARCHAR(16) NOT NULL DEFAULT '0' COMMENT '父编码',
  `code` VARCHAR(16) DEFAULT NULL COMMENT '编码',
  `code_value` VARCHAR(256) DEFAULT NULL COMMENT '编码值',
  `code_sort` INT(8) DEFAULT NULL COMMENT '排序',
  `office_address` INT(16) NOT NULL DEFAULT '0' COMMENT '城市编码',
  `remark` VARCHAR(256) DEFAULT NULL COMMENT '备注',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='基础数据表';

/*Data for the table `m_base_code` */

INSERT  INTO `m_base_code`(`id`,`code_type`,`parent_code`,`code`,`code_value`,`code_sort`,`office_address`,`remark`,`create_time`,`update_time`)
VALUES
  (1,'drop_reason','master','fake_work','虚假成交',1,0,'默认剔除原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (2,'drop_reason','master','duplicate','重复网签',7,0,'默认剔除原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (3,'drop_reason','master','other','其它',11,0,'默认剔除原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (4,'not_target_reason','master','concentration','集中过户',1,0,'默认非目标原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (5,'not_target_reason','master','out_area','远郊，非作业区域',4,0,'默认非目标原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (6,'not_target_reason','master','other','其它',1,0,'默认非目标原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (7,'deadline_modify_datetime','master',NULL,'2017-02-01 10:00:00,2017-02-06 18:00:00',1,0,'默认修改数据时间','2017-03-21 19:24:23','2017-03-21 19:24:23');



CREATE DATABASE IF NOT EXISTS `dynamic_slave` DEFAULT CHARACTER SET utf8;

USE `dynamic_slave`;

/*Table structure for table `m_base_code` */

DROP TABLE IF EXISTS `m_base_code`;

CREATE TABLE `m_base_code` (
  `id` BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `code_type` VARCHAR(64) NOT NULL COMMENT '编码类型',
  `parent_code` VARCHAR(16) NOT NULL DEFAULT '0' COMMENT '父编码',
  `code` VARCHAR(16) DEFAULT NULL COMMENT '编码',
  `code_value` VARCHAR(256) DEFAULT NULL COMMENT '编码值',
  `code_sort` INT(8) DEFAULT NULL COMMENT '排序',
  `office_address` INT(16) NOT NULL DEFAULT '0' COMMENT '城市编码',
  `remark` VARCHAR(256) DEFAULT NULL COMMENT '备注',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='基础数据表';

/*Data for the table `m_base_code` */

INSERT  INTO `m_base_code`(`id`,`code_type`,`parent_code`,`code`,`code_value`,`code_sort`,`office_address`,`remark`,`create_time`,`update_time`)
VALUES
  (1,'drop_reason','slave','fake_work','虚假成交',1,0,'默认剔除原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (2,'drop_reason','slave','duplicate','重复网签',7,0,'默认剔除原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (3,'drop_reason','slave','other','其它',11,0,'默认剔除原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (4,'not_target_reason','slave','concentration','集中过户',1,0,'默认非目标原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (5,'not_target_reason','slave','out_area','远郊，非作业区域',4,0,'默认非目标原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (6,'not_target_reason','slave','other','其它',1,0,'默认非目标原因','2017-03-21 19:24:23','2017-03-21 19:24:23'),
  (7,'deadline_modify_datetime','slave',NULL,'2017-02-01 10:00:00,2017-02-06 18:00:00',1,0,'默认修改数据时间','2017-03-21 19:24:23','2017-03-21 19:24:23');