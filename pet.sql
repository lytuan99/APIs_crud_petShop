CREATE DATABASE  IF NOT EXISTS `petshop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `petshop`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: petshop
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `options`
--

DROP TABLE IF EXISTS `options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `value` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `position` int(4) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `modified_on` datetime(6) DEFAULT NULL,
  `id_variant` int(10) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_variant_idx` (`id_variant`),
  CONSTRAINT `fk_variant` FOREIGN KEY (`id_variant`) REFERENCES `variants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (34,'color','đỏ',1,'2020-03-30 14:44:28.354000','2020-03-30 14:48:57.138000',17,1),(35,'size','large',2,'2020-03-30 14:44:28.354000','2020-03-30 14:48:57.138000',17,1),(36,NULL,NULL,3,'2020-03-30 14:44:28.354000','2020-03-30 14:44:28.354000',17,1),(43,'color','tím',1,'2020-03-30 15:13:35.240000','2020-03-30 15:18:58.050000',20,1),(44,'size','small',2,'2020-03-30 15:13:35.240000','2020-03-30 15:18:58.050000',20,1),(45,'gender','male',3,'2020-03-30 15:13:35.240000','2020-03-30 15:18:58.050000',20,1),(46,'color','tím',1,'2020-03-30 16:06:53.931000','2020-03-30 16:06:53.931000',21,1),(47,'gender','male',2,'2020-03-30 16:06:53.931000','2020-03-30 16:06:53.931000',21,1),(48,NULL,NULL,3,'2020-03-30 16:06:53.931000','2020-03-30 16:06:53.931000',21,1),(49,'color','xanh',1,'2020-03-30 16:06:53.931000','2020-03-30 16:06:53.931000',22,1),(50,'gender','female',2,'2020-03-30 16:06:53.931000','2020-03-30 16:06:53.931000',22,1),(51,NULL,NULL,3,'2020-03-30 16:06:53.931000','2020-03-30 16:06:53.931000',22,1),(52,'color','nâu sữa',1,'2020-04-06 14:07:52.169000','2020-04-06 14:07:52.169000',23,1),(53,'gender','đực',2,'2020-04-06 14:07:52.169000','2020-04-06 14:07:52.169000',23,1),(54,NULL,NULL,3,'2020-04-06 14:07:52.169000','2020-04-06 14:07:52.169000',23,1);
/*!40000 ALTER TABLE `options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pets`
--

DROP TABLE IF EXISTS `pets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pets` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `tags` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `content` text COLLATE utf8_bin,
  `created_on` datetime DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pets`
--

LOCK TABLES `pets` WRITE;
/*!40000 ALTER TABLE `pets` DISABLE KEYS */;
INSERT INTO `pets` VALUES (12,'mèo anh lông ngắn','mèo, mèo anh','<h1>mèo anh ngắn</h1>','2020-03-30 14:44:28','2020-03-30 15:18:58',1),(13,'chó tây','mèo, mèo anh','<h1>mèo anh lông ngắn</h1>','2020-03-30 16:06:54','2020-03-30 16:06:54',1),(14,'mèo ba tư','mèo, mèo ba tư','<h1>mèo ba tư</h1>','2020-04-06 14:07:52','2020-04-06 14:07:52',1);
/*!40000 ALTER TABLE `pets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variants`
--

DROP TABLE IF EXISTS `variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `variants` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pet_id` int(10) DEFAULT NULL,
  `option1` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `option2` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `option3` varchar(45) COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int(4) DEFAULT NULL,
  `age_unit` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `weight_unit` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `inventory_quantity` int(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_pet_idx` (`pet_id`),
  CONSTRAINT `fk_pet` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variants`
--

LOCK TABLES `variants` WRITE;
/*!40000 ALTER TABLE `variants` DISABLE KEYS */;
INSERT INTO `variants` VALUES (17,12,'đỏ','large',NULL,4,'years',2,'kg',NULL,1,'2020-03-30 14:44:28','2020-03-30 14:48:57',1),(20,12,'tím','small','male',2,'tháng',2,'kg',NULL,3,'2020-03-30 15:13:35','2020-03-30 15:18:58',1),(21,13,'tím','male',NULL,2,'tháng',2,'kg',NULL,3,'2020-03-30 16:06:54','2020-03-30 16:06:54',1),(22,13,'xanh','female',NULL,3,'năm',2,'kg',12.20,3,'2020-03-30 16:06:54','2020-03-30 16:06:54',1),(23,14,'nâu sữa','đực',NULL,10,'tháng',2,'kg',NULL,1,'2020-04-06 14:07:52','2020-04-06 14:07:52',1);
/*!40000 ALTER TABLE `variants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'petshop'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-06 14:21:31
