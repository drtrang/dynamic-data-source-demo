DROP TABLE IF EXISTS m_base_code;

CREATE TABLE m_base_code (
  id BIGINT(18) NOT NULL AUTO_INCREMENT COMMENT '主键',
  code_type VARCHAR(64) NOT NULL COMMENT '编码类型',
  parent_code VARCHAR(16) DEFAULT '0' COMMENT '父编码',
  code VARCHAR(16) DEFAULT NULL COMMENT '编码',
  code_value VARCHAR(256) NOT NULL COMMENT '编码值',
  code_sort INT(8) DEFAULT 0 COMMENT '排序',
  office_address INT(16) NOT NULL DEFAULT 0 COMMENT '城市编码',
  remark VARCHAR(256) DEFAULT NULL COMMENT '备注',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
);

/*Data for the table m_base_code */

INSERT INTO m_base_code (id, code_type, code, code_value, code_sort, office_address, remark, create_time, update_time)
VALUES
  (1, 'drop_reason', 'fake_work', '虚假成交', 1, 0, '默认剔除原因', now(), now()),
  (2, 'drop_reason', 'duplicate', '重复网签', 7, 0, '默认剔除原因', now(), now()),
  (3, 'drop_reason', 'other', '其它', 11, 0, '默认剔除原因', now(), now()),
  (4, 'not_target_reason', 'concentration', '集中过户', 1, 0, '默认非目标原因', now(), now()),
  (5, 'not_target_reason', 'out_area', '远郊，非作业区域', 4, 0, '默认非目标原因', now(), now()),
  (6, 'not_target_reason', 'other', '其它', 1, 0, '默认非目标原因', now(), now()),
  (7, 'deadline_modify_datetime', NULL, '2017-02-01 10:00:00,2017-02-06 18:00:00', 1, 0, '默认修改数据时间', now(), now());