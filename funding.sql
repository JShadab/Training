-- --------------------------------------------------------
-- Host:                         localhost
-- Server version:               5.1.53-community-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for funding
CREATE DATABASE IF NOT EXISTS `funding` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `funding`;

-- Dumping structure for table funding.agent
CREATE TABLE IF NOT EXISTS `agent` (
  `email` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `mobile` bigint(20) NOT NULL,
  `password` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL DEFAULT 'Deactivate',
  PRIMARY KEY (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table funding.agent: 3 rows
/*!40000 ALTER TABLE `agent` DISABLE KEYS */;
INSERT INTO `agent` (`email`, `first_name`, `last_name`, `mobile`, `password`, `type`, `status`) VALUES
	('abv@gmail.com', 'abc', 'abc', 8585858585, 1234, 'AGENT', 'Activate'),
	('agent@gmail.com', 'agent', 'agent', 8585858585, 12345, 'AGENT', 'Deactivate'),
	('a@gmail.com', 'a', 'a', 524631892, 12345, 'AGENT', 'Activate');
/*!40000 ALTER TABLE `agent` ENABLE KEYS */;

-- Dumping structure for table funding.bank
CREATE TABLE IF NOT EXISTS `bank` (
  `bank_id` int(50) NOT NULL,
  `bank_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`bank_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table funding.bank: 0 rows
/*!40000 ALTER TABLE `bank` DISABLE KEYS */;
/*!40000 ALTER TABLE `bank` ENABLE KEYS */;

-- Dumping structure for table funding.city
CREATE TABLE IF NOT EXISTS `city` (
  `city_id` int(50) NOT NULL,
  `city_name` varchar(50) NOT NULL,
  PRIMARY KEY (`city_name`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table funding.city: 0 rows
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
/*!40000 ALTER TABLE `city` ENABLE KEYS */;

-- Dumping structure for table funding.fixed_deposit
CREATE TABLE IF NOT EXISTS `fixed_deposit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Bank_Name` varchar(50) NOT NULL,
  `Interest_rate` double NOT NULL,
  `Min_tenure` int(11) NOT NULL,
  `Max_tenure` int(11) NOT NULL,
  `Sum` double DEFAULT NULL,
  `Details` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table funding.fixed_deposit: 1 rows
/*!40000 ALTER TABLE `fixed_deposit` DISABLE KEYS */;
INSERT INTO `fixed_deposit` (`id`, `Bank_Name`, `Interest_rate`, `Min_tenure`, `Max_tenure`, `Sum`, `Details`) VALUES
	(1, 'SBI', 5, 7, 5, 250000, 'aghjk');
/*!40000 ALTER TABLE `fixed_deposit` ENABLE KEYS */;

-- Dumping structure for table funding.insurance
CREATE TABLE IF NOT EXISTS `insurance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Bank_Name` varchar(50) NOT NULL,
  `Plan_Name` varchar(50) NOT NULL,
  `Premium` double NOT NULL,
  `Min_Term` int(11) NOT NULL,
  `Max_Term` int(11) NOT NULL,
  `Min_Age` int(11) DEFAULT NULL,
  `Max_Age` int(11) DEFAULT NULL,
  `Sum` double NOT NULL,
  `Details` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table funding.insurance: 2 rows
/*!40000 ALTER TABLE `insurance` DISABLE KEYS */;
INSERT INTO `insurance` (`id`, `Bank_Name`, `Plan_Name`, `Premium`, `Min_Term`, `Max_Term`, `Min_Age`, `Max_Age`, `Sum`, `Details`) VALUES
	(1, 'hdfc', 'dfgh', 4, 2, 1, 2, 1, 236, 'gjk'),
	(2, 'SBI', 'sbi', 1, 1, 2, 2, 5, 250000, 'gjk');
/*!40000 ALTER TABLE `insurance` ENABLE KEYS */;

-- Dumping structure for table funding.investor  table
CREATE TABLE IF NOT EXISTS `investor  table` (
  `email_id` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `is active` int(50) NOT NULL,
  PRIMARY KEY (`email_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table funding.investor  table: 0 rows
/*!40000 ALTER TABLE `investor  table` DISABLE KEYS */;
/*!40000 ALTER TABLE `investor  table` ENABLE KEYS */;

-- Dumping structure for table funding.mutual_funds
CREATE TABLE IF NOT EXISTS `mutual_funds` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Bank_Name` varchar(50) NOT NULL,
  `Interest_Rate` double NOT NULL,
  `Tenure` int(5) NOT NULL,
  `SIP` double NOT NULL,
  `Premium` double NOT NULL,
  `Name` varchar(50) NOT NULL,
  `Details` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table funding.mutual_funds: 1 rows
/*!40000 ALTER TABLE `mutual_funds` DISABLE KEYS */;
INSERT INTO `mutual_funds` (`id`, `Bank_Name`, `Interest_Rate`, `Tenure`, `SIP`, `Premium`, `Name`, `Details`) VALUES
	(1, 'HDFC', 8.35, 10, 1000, 12000, 'salar sip', 'ye fazri hai');
/*!40000 ALTER TABLE `mutual_funds` ENABLE KEYS */;

-- Dumping structure for table funding.user
CREATE TABLE IF NOT EXISTS `user` (
  `email` varchar(50) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `type` varchar(50) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- Dumping data for table funding.user: 3 rows
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`email`, `first_name`, `last_name`, `mobile`, `password`, `type`) VALUES
	('jaiswal.amitabh751@gmail.com', 'Amitabh', 'Jaiswal', '897465132', 'dfghjkl', 'USER'),
	('test@gmail.com', 'priya', 'amit', '1234567789', '12345', 'USER'),
	('admin@gmail.com', 'Admin', 'Admin', '998877665', '1234567', 'ADMIN');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table funding.user_saved_plan
CREATE TABLE IF NOT EXISTS `user_saved_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `plan_id` int(11) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table funding.user_saved_plan: 1 rows
/*!40000 ALTER TABLE `user_saved_plan` DISABLE KEYS */;
INSERT INTO `user_saved_plan` (`id`, `email`, `plan_id`, `type`) VALUES
	(1, 'test@gmail.com', 1, 'mutual_fund');
/*!40000 ALTER TABLE `user_saved_plan` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;







https://click.pstmrk.it/2sm/www.hackerrank.com%2Ftests%2Fg47fst1q8hb%2Flogin%3Fb%3DeyJ1c2VybmFtZSI6ImNvb2xzaGFkMTJAZ21haWwuY29tIiwicGFzc3dvcmQiOiI0NjhiY2Q0NiIsImhpZGUiOnRydWV9/HWGWbAE/EDcI/Vnu2JYujsO/aHJ3LXRlc3QtaW52aXRl

