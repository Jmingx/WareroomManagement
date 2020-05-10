CREATE TABLE `SupplierInf` (

`id` int UNSIGNED NOT NULL AUTO_INCREMENT,

`name` varchar(255) CHARACTER SET utf8 NULL DEFAULT '无' COMMENT '供应商名称',

`contact` varchar(255) CHARACTER SET utf8 NULL DEFAULT '无' COMMENT '联系方式',

PRIMARY KEY (`id`) 

);



CREATE TABLE `ComponentInf` (

`id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '零件编号',

`supplierId` int UNSIGNED NULL COMMENT '外键',

`price` double UNSIGNED NULL DEFAULT 0 COMMENT '价格',

`name` varchar(255) CHARACTER SET utf8 NULL DEFAULT '无' COMMENT '零件名称',

PRIMARY KEY (`id`) 

);



CREATE TABLE `OrderInf` (

`id` int NOT NULL AUTO_INCREMENT COMMENT '订货信息编号',

`componentId` int UNSIGNED NULL COMMENT '外键',

`amount` int NULL COMMENT '订货数量',

PRIMARY KEY (`id`) 

);



CREATE TABLE `Inventory` (

`id` int NOT NULL AUTO_INCREMENT COMMENT '库存清单编号',

`componentId` int UNSIGNED NULL COMMENT '零件编号，外键',

`inventory` int UNSIGNED NULL DEFAULT 0 COMMENT '库存量',

`criticalValue` int UNSIGNED NULL DEFAULT 100 COMMENT '库存临界值',

PRIMARY KEY (`id`) 

);



CREATE TABLE `Business` (

`id` int NOT NULL AUTO_INCREMENT COMMENT '事务编号',

`componentId` int UNSIGNED NULL COMMENT '零件编号',

`business` int NULL DEFAULT 0 COMMENT '事务类型',

PRIMARY KEY (`id`) 

);


CREATE TABLE `User` (

`id` int(11) NOT NULL AUTO_INCREMENT,

`userName` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '用户名',

`password` varchar(30) CHARACTER SET utf8 NOT NULL COMMENT '密码（未加密）',

PRIMARY KEY (`id`) 

);



ALTER TABLE `ComponentInf` ADD CONSTRAINT `供应商id` FOREIGN KEY (`supplierId`) REFERENCES `SupplierInf` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `OrderInf` ADD CONSTRAINT `零件编号` FOREIGN KEY (`componentId`) REFERENCES `ComponentInf` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `Inventory` ADD CONSTRAINT `库存清单的零件编号` FOREIGN KEY (`componentId`) REFERENCES `ComponentInf` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE `Business` ADD CONSTRAINT `事务的零件编号` FOREIGN KEY (`componentId`) REFERENCES `ComponentInf` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;


ALTER TABLE `supplierInf` ADD CONSTRAINT `供应商名称` uc_supplierInf UNIQUE (`name`,`contract`);


-- ALTER TABLE `componentInf` ADD CONSTRAINT `零件名称` uc_supplierInf UNIQUE (`name`);


