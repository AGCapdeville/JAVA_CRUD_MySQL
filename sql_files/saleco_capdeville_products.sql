-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: saleco_capdeville
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `p_id` varchar(32) COLLATE utf8mb4_general_ci NOT NULL,
  `v_id` int(11) DEFAULT NULL,
  `p_name` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `p_desc` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `p_price` float(4,2) DEFAULT NULL,
  PRIMARY KEY (`p_id`),
  KEY `v_id` (`v_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`v_id`) REFERENCES `vendors` (`v_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('1122',1001,'Airframe fasteners','Airframe fasteners by Hulkey Fasteners',4.50),('1243',1005,'Airframe fasteners','Airframe fasteners by Alum Sheeting',4.50),('1369',1003,'Airframe fasteners','Airframe fasteners by Durrable Products',4.45),('3166',1001,'Electrical Connector','Electrical Connector by Hulkey Fasteners',1.50),('4111',1002,'Bolt-nut package','Bolt-nut package by Spacetime Technologies',3.80),('4224',1005,'Bolt-nut package','Bolt-nut package by Alum Sheeting',4.20),('4312',1006,'Bolt-nut package','Bolt-nut package by Steelpin Inc.',4.00),('4569',1003,'Bolt-nut package','Bolt-nut package by Durrable Products',3.75),('5066',1001,'Shielded Cable/ft.','Shielded Cable/ft. by Hulkey Fasteners',1.20),('5125',1002,'Shielded Cable/ft.','Shielded Cable/ft. by Spacetime Technologies',1.40),('5166',1004,'Electrical Connector','Electrical Connector by Fast-Tie Aerospace',1.50),('5234',1006,'Electrical Connector','Electrical Connector by Steelpin Inc.',1.90),('5275',1003,'Shielded Cable/ft.','Shielded Cable/ft. by Durrable Products',1.25),('5319',1006,'Shielded Cable/ft.','Shielded Cable/ft. by Steelpin Inc.',1.35),('5462',1004,'Shielded Cable/ft.','Shielded Cable/ft. by Fast-Tie Aerospace',1.30),('6321',1004,'O-Ring','O-Ring by Fast-Tie Aerospace',2.70),('6431',1007,'O-Ring','O-Ring by Manley Valve',3.10),('6433',1008,'O-Ring','O-Ring by Pylon Accessories',3.20),('6489',1002,'O-Ring','O-Ring by Spacetime Technologies',3.25),('7260',1003,'Pressure Gauge','Pressure Gauge by Durrable Products',90.00),('7268',1004,'Pressure Gauge','Pressure Gauge by Fast-Tie Aerospace',95.00),('9399',1003,'Gasket','Gasket by Durrable Products',3.90),('9752',1002,'Gasket','Gasket by Spacetime Technologies',4.30),('9764',1008,'Gasket','Gasket by Pylon Accessories',4.00),('9955',1007,'Door Decal','Door Decal by Manley Valve',0.80),('9966',1001,'Hatch Decal','Hatch Decal by Hulkey Fasteners',1.00),('9967',1007,'Hatch Decal','Hatch Decal by Manley Valve',1.10),('9977',1007,'Panel Decal','Panel Decal by Manley Valve',1.25);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-28 23:05:00
