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
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `options`
--

LOCK TABLES `options` WRITE;
/*!40000 ALTER TABLE `options` DISABLE KEYS */;
INSERT INTO `options` VALUES (67,'color','đỏ',1,'2020-05-09 14:55:05.139000','2020-05-09 15:13:02.260000',38,1),(68,'size','small',2,'2020-05-09 14:55:05.139000','2020-05-09 15:13:02.260000',38,1),(69,NULL,NULL,3,'2020-05-09 14:55:05.139000','2020-05-09 15:13:02.260000',38,1),(70,'size','big',1,'2020-05-09 15:04:39.412000','2020-05-09 15:25:58.804000',42,1),(71,'gender','đực',2,'2020-05-09 15:04:39.412000','2020-05-09 15:25:58.804000',42,1),(72,'color','trắng',3,'2020-05-09 15:04:39.412000','2020-05-09 15:25:58.804000',42,1),(73,'size','small',1,'2020-05-09 15:22:44.880000','2020-05-09 15:22:44.880000',43,1),(74,'color','đen',2,'2020-05-09 15:22:44.880000','2020-05-09 15:22:44.880000',43,1),(75,NULL,NULL,3,'2020-05-09 15:22:44.880000','2020-05-09 15:22:44.880000',43,1),(76,'size','small',1,'2020-05-09 15:23:57.327000','2020-05-09 15:23:57.327000',44,1),(77,'color','đen',2,'2020-05-09 15:23:57.327000','2020-05-09 15:23:57.327000',44,1),(78,NULL,NULL,3,'2020-05-09 15:23:57.327000','2020-05-09 15:23:57.327000',44,1),(79,'size','small',1,'2020-05-09 15:25:58.804000','2020-05-09 15:25:58.804000',45,1),(80,'color','đen',2,'2020-05-09 15:25:58.804000','2020-05-09 15:25:58.804000',45,1),(81,'type','cảnh',3,'2020-05-09 15:25:58.804000','2020-05-09 15:25:58.804000',45,1);
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
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pets`
--

LOCK TABLES `pets` WRITE;
/*!40000 ALTER TABLE `pets` DISABLE KEYS */;
INSERT INTO `pets` VALUES (30,'gà chọi ta','ta tây ta','<h1>chicken wings</h1>','2020-05-09 14:55:05','2020-05-09 15:25:59',1);
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
  `age` int(4) DEFAULT NULL,
  `age_unit` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `weight` float DEFAULT NULL,
  `weight_unit` varchar(5) COLLATE utf8mb4_bin DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `inventory_quantity` int(10) DEFAULT NULL,
  `position` int(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk_pet_idx` (`pet_id`),
  CONSTRAINT `fk_pet` FOREIGN KEY (`pet_id`) REFERENCES `pets` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variants`
--

LOCK TABLES `variants` WRITE;
/*!40000 ALTER TABLE `variants` DISABLE KEYS */;
INSERT INTO `variants` VALUES (38,30,NULL,NULL,NULL,NULL,NULL,NULL,1,'2020-05-09 14:55:05','2020-05-09 15:13:02',1),(42,30,NULL,NULL,NULL,NULL,NULL,NULL,2,'2020-05-09 15:04:39','2020-05-09 15:25:59',1),(43,30,NULL,NULL,NULL,NULL,NULL,NULL,3,'2020-05-09 15:22:45','2020-05-09 15:22:45',1),(44,30,NULL,NULL,NULL,NULL,NULL,NULL,4,'2020-05-09 15:23:57','2020-05-09 15:23:57',1),(45,30,NULL,NULL,NULL,NULL,NULL,NULL,5,'2020-05-09 15:25:59','2020-05-09 15:25:59',1);
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

-- Dump completed on 2020-05-10  8:59:30
