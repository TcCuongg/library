-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.2.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for library
CREATE DATABASE IF NOT EXISTS `library` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `library`;

-- Dumping structure for table library.account
CREATE TABLE IF NOT EXISTS `account` (
  `Card Number` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Name` text NOT NULL,
  `Avatar` text NOT NULL,
  `Email` text NOT NULL,
  `Phone` bigint(20) unsigned zerofill NOT NULL,
  `Address` text NOT NULL,
  `Password` text NOT NULL,
  `Level` int(10) unsigned zerofill NOT NULL,
  `Status` char(10) NOT NULL,
  `Type` char(10) NOT NULL,
  `Time Create` datetime NOT NULL,
  PRIMARY KEY (`Card Number`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.author
CREATE TABLE IF NOT EXISTS `author` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Date` datetime NOT NULL,
  `Address` text NOT NULL,
  `Phone` bigint(20) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.authorbook
CREATE TABLE IF NOT EXISTS `authorbook` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Author Id` bigint(20) unsigned zerofill NOT NULL,
  `Book Id` bigint(20) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `author` (`Author Id`) USING BTREE,
  KEY `book` (`Book Id`) USING BTREE,
  CONSTRAINT `author` FOREIGN KEY (`Author Id`) REFERENCES `author` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book` FOREIGN KEY (`Book Id`) REFERENCES `book` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.book
CREATE TABLE IF NOT EXISTS `book` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Title` text NOT NULL,
  `Category Id` bigint(20) unsigned zerofill NOT NULL,
  `Follow` bigint(20) unsigned zerofill NOT NULL,
  `Cost` bigint(20) unsigned zerofill NOT NULL,
  `Content` text NOT NULL,
  `Status` text NOT NULL,
  `Sale` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`Id`),
  KEY `category` (`Category Id`) USING BTREE,
  CONSTRAINT `category` FOREIGN KEY (`Category Id`) REFERENCES `category` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.bookstorage
CREATE TABLE IF NOT EXISTS `bookstorage` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Book Id` bigint(20) unsigned zerofill NOT NULL,
  `Storage Id` bigint(20) unsigned zerofill NOT NULL,
  `Quantity` int(10) unsigned zerofill NOT NULL,
  `Import Time` datetime NOT NULL,
  `Image` text NOT NULL,
  `Account Import Id` bigint(20) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `book1` (`Book Id`) USING BTREE,
  KEY `storage1` (`Storage Id`) USING BTREE,
  KEY `account id` (`Account Import Id`),
  CONSTRAINT `account id` FOREIGN KEY (`Account Import Id`) REFERENCES `account` (`Card Number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book1` FOREIGN KEY (`Book Id`) REFERENCES `book` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `storage1` FOREIGN KEY (`Storage Id`) REFERENCES `storage` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.buy
CREATE TABLE IF NOT EXISTS `buy` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Account Id` bigint(20) unsigned zerofill NOT NULL,
  `Book Storage Id` bigint(20) unsigned zerofill NOT NULL,
  `Time` datetime NOT NULL,
  `Status` text NOT NULL,
  `Cost` bigint(20) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `account2` (`Account Id`) USING BTREE,
  KEY `book copy1` (`Book Storage Id`) USING BTREE,
  CONSTRAINT `account2` FOREIGN KEY (`Account Id`) REFERENCES `account` (`Card Number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book copy1` FOREIGN KEY (`Book Storage Id`) REFERENCES `bookstorage` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.cart
CREATE TABLE IF NOT EXISTS `cart` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Book Storage Id` bigint(20) unsigned zerofill NOT NULL,
  `Account Id` bigint(20) unsigned zerofill NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `account1` (`Account Id`) USING BTREE,
  KEY `book copy` (`Book Storage Id`) USING BTREE,
  CONSTRAINT `account1` FOREIGN KEY (`Account Id`) REFERENCES `account` (`Card Number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `book copy` FOREIGN KEY (`Book Storage Id`) REFERENCES `bookstorage` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.category
CREATE TABLE IF NOT EXISTS `category` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `Sale` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.checkpay
CREATE TABLE IF NOT EXISTS `checkpay` (
  `Id` bigint(20) unsigned zerofill NOT NULL,
  `Account Id` bigint(20) unsigned zerofill NOT NULL,
  `Buy Id` bigint(20) unsigned zerofill NOT NULL,
  `Time Change` datetime NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.maincontent
CREATE TABLE IF NOT EXISTS `maincontent` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Type` text NOT NULL,
  `Content` text NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.notification
CREATE TABLE IF NOT EXISTS `notification` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Account Id` bigint(20) unsigned zerofill NOT NULL,
  `Main Content Id` bigint(20) unsigned zerofill NOT NULL,
  `Sent` timestamp NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `account` (`Account Id`) USING BTREE,
  KEY `content` (`Main Content Id`) USING BTREE,
  CONSTRAINT `account` FOREIGN KEY (`Account Id`) REFERENCES `account` (`Card Number`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `content` FOREIGN KEY (`Main Content Id`) REFERENCES `maincontent` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

-- Dumping structure for table library.storage
CREATE TABLE IF NOT EXISTS `storage` (
  `Id` bigint(20) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `Phone` bigint(20) unsigned zerofill NOT NULL,
  `Location` varchar(255) NOT NULL,
  `Status` varchar(255) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
