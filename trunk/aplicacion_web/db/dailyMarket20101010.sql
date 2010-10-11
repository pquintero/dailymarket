-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema dailymarket
--

CREATE DATABASE IF NOT EXISTS dailymarket;
USE dailymarket;

--
-- Definition of table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `timer` int(10) unsigned default NULL,
  `emaildeposito` varchar(60) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `configuration`
--

/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` (`id`,`timer`,`emaildeposito`) VALUES 
 (1,7,'juanmelo25@gmail.com');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;


--
-- Definition of table `groupproduct`
--

DROP TABLE IF EXISTS `groupproduct`;
CREATE TABLE `groupproduct` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `description` varchar(45) default NULL,
  `active` tinyint(3) unsigned default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupproduct`
--

/*!40000 ALTER TABLE `groupproduct` DISABLE KEYS */;
INSERT INTO `groupproduct` (`id`,`name`,`description`,`active`) VALUES 
 (1,'Lacteos ','Productos lacteos',1),
 (2,'Quesos','Quesos',1),
 (3,'Golosina','Golosina',1),
 (4,'Yerbas','Yerbas mates',1),
 (7,'Carnes','Carnes',1);
/*!40000 ALTER TABLE `groupproduct` ENABLE KEYS */;


--
-- Definition of table `groupuser`
--

DROP TABLE IF EXISTS `groupuser`;
CREATE TABLE `groupuser` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `description` varchar(45) default NULL,
  `active` tinyint(3) unsigned default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupuser`
--

/*!40000 ALTER TABLE `groupuser` DISABLE KEYS */;
INSERT INTO `groupuser` (`id`,`name`,`description`,`active`) VALUES 
 (1,'Admin','Administrador',1),
 (2,'Manager','Manager',1),
 (3,'Cajero','Cajero',1);
/*!40000 ALTER TABLE `groupuser` ENABLE KEYS */;


--
-- Definition of table `hourlyband`
--

DROP TABLE IF EXISTS `hourlyband`;
CREATE TABLE `hourlyband` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `description` varchar(45) default NULL,
  `initband` int(10) unsigned default NULL,
  `endband` int(10) unsigned default NULL,
  `active` tinyint(3) unsigned default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hourlyband`
--

/*!40000 ALTER TABLE `hourlyband` DISABLE KEYS */;
INSERT INTO `hourlyband` (`id`,`name`,`description`,`initband`,`endband`,`active`) VALUES 
 (2,'Mediodia-Tarde','Turno de la tarde',12,15,1),
 (3,'Mañana','Turno mañana',9,12,1),
 (4,'Media tarde','Media tarde',15,18,1),
 (5,'Tarde-Noche','Tarde Noche',18,21,1);
/*!40000 ALTER TABLE `hourlyband` ENABLE KEYS */;


--
-- Definition of table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `description` varchar(45) default NULL,
  `sizeofpurchase` int(10) unsigned default NULL,
  `price` double default NULL,
  `actualstock` int(10) unsigned default NULL,
  `code` varchar(45) default NULL,
  `groupproductid` int(10) unsigned default NULL,
  `repositionstock` int(11) default NULL,
  `datewithoutstock` datetime default NULL,
  `state` varchar(50) default NULL,
  `active` tinyint(3) unsigned default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` (`id`,`name`,`description`,`sizeofpurchase`,`price`,`actualstock`,`code`,`groupproductid`,`repositionstock`,`datewithoutstock`,`state`,`active`) VALUES 
 (1,'Yerba','Yerba mate',34,12.3,45,'1',4,15,NULL,'product.state.stock',1),
 (2,'Alfajor jorgito','Alfajor jorgito',20,2,12,'2',3,11,NULL,'product.state.stock',1),
 (3,'Queso rallado la serenisima','Queso rallado la serenima',100,12,23,'3',2,30,NULL,'product.state.pending',1),
 (4,'Leche Sancor','Leche sancor 1L',189,2.3,45,'4',1,54,NULL,'product.state.pending',1),
 (5,'Chicle Beldent','Chicle Beldent 6 unidades',80,2,34,'5',3,36,NULL,'product.state.pending',1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


--
-- Definition of table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(45) default NULL,
  `lastname` varchar(45) default NULL,
  `user` varchar(45) default NULL,
  `password` varchar(20) default NULL,
  `dni` varchar(20) default NULL,
  `datecreated` datetime default NULL,
  `groupuserid` int(10) unsigned default NULL,
  `email` varchar(50) default NULL,
  `receiveNotifications` tinyint(1) default '0',
  `active` tinyint(3) unsigned default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`,`name`,`lastname`,`user`,`password`,`dni`,`datecreated`,`groupuserid`,`email`,`receiveNotifications`,`active`) VALUES 
 (1,'Juan','Melo','jmelo','a','30862616','2010-09-05 12:20:49',1,'juanmelo25@hotmail.com',0,1),
 (2,'Ezequiel','Pallich','eze','a','30129121','2010-09-13 16:52:00',2,'epallich@gmail.com',1,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
