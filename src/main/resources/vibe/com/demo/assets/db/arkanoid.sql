-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: arkanoid
-- ------------------------------------------------------
-- Server version	9.3.0

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
-- Table structure for table `gamesession`
--

DROP TABLE IF EXISTS `gamesession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gamesession` (
  `game_session_id` int NOT NULL,
  `player_name` varchar(50) NOT NULL,
  `is_win` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`game_session_id`),
  KEY `fkey_player_name` (`player_name`),
  CONSTRAINT `fkey_player_name` FOREIGN KEY (`player_name`) REFERENCES `player` (`player_name`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gamesession`
--

LOCK TABLES `gamesession` WRITE;
/*!40000 ALTER TABLE `gamesession` DISABLE KEYS */;
/*!40000 ALTER TABLE `gamesession` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paddle`
--

DROP TABLE IF EXISTS `paddle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paddle` (
  `paddle_id` varchar(50) NOT NULL,
  `paddle_url` varchar(100) DEFAULT 'URL',
  `paddle_name` varchar(50) NOT NULL DEFAULT 'paddle_name',
  `paddle_desc` varchar(255) DEFAULT 'paddle_desc',
  `price` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`paddle_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paddle`
--

LOCK TABLES `paddle` WRITE;
/*!40000 ALTER TABLE `paddle` DISABLE KEYS */;
INSERT INTO `paddle` VALUES ('item1','URL','paddle_name','paddle_desc',0),('item2','URL','paddle_name','paddle_desc',0),('item3','URL','paddle_name','paddle_desc',0),('item4','URL','paddle_name','paddle_desc',0),('item5','URL','paddle_name','paddle_desc',0),('item6','URL','paddle_name','paddle_desc',0),('item7','URL','paddle_name','paddle_desc',0),('item8','URL','paddle_name','paddle_desc',0),('item9','URL','paddle_name','paddle_desc',0);
/*!40000 ALTER TABLE `paddle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player`
--

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
  `username` varchar(50) NOT NULL,
  `player_name` varchar(50) NOT NULL,
  `coins` int DEFAULT '0',
  `trophies` int DEFAULT '0',
  `current_paddle_id` varchar(50) NOT NULL DEFAULT 'item1',
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `player_name` (`player_name`),
  KEY `fk_player_paddleId_paddle` (`current_paddle_id`),
  CONSTRAINT `fk_player_paddleId_paddle` FOREIGN KEY (`current_paddle_id`) REFERENCES `paddle` (`paddle_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player`
--

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES ('asbdsbada','asdasdasd',0,0,'item1','12345678'),('hoangduc','hovadu',46000,4,'item5','12345678'),('vanhuy','Alo123',142250,20,'item2','12345678'),('vanhuyabdfg','pro007asdas',0,0,'item1','12345678'),('vanhuyabdfgsad','pro007asdassadas',0,0,'item1','12345678'),('vanhuyis3','123456',55000,5,'item1','12345678'),('vanhuytnt','pro123',0,0,'item1','12345678'),('vanhuytnt1','pro007',50000,10,'item1','12345678'),('vanhuytnt2','pro0071',50000,18,'item1','12345678'),('vanhuytnt3','pro0072',50000,17,'item1','12345678'),('vanhuytnt4','pro0073',50000,11,'item1','12345678'),('vanhuytnt5','pro0074',50000,9,'item1','12345678'),('vanhuytnt6','pro0075',50000,8,'item1','12345678'),('vanhuytnt7','pro0076',50000,5,'item1','12345678');
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `playerinventory`
--

DROP TABLE IF EXISTS `playerinventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `playerinventory` (
  `player_name` varchar(50) NOT NULL,
  `paddle_id` varchar(50) NOT NULL,
  PRIMARY KEY (`player_name`,`paddle_id`),
  KEY `fk_paddle_id` (`paddle_id`),
  CONSTRAINT `fk_paddle_id` FOREIGN KEY (`paddle_id`) REFERENCES `paddle` (`paddle_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_player_name` FOREIGN KEY (`player_name`) REFERENCES `player` (`player_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `playerinventory`
--

LOCK TABLES `playerinventory` WRITE;
/*!40000 ALTER TABLE `playerinventory` DISABLE KEYS */;
INSERT INTO `playerinventory` VALUES ('123456','item1'),('Alo123','item1'),('asdasdasd','item1'),('hovadu','item1'),('pro007','item1'),('pro0071','item1'),('pro0072','item1'),('pro0073','item1'),('pro0074','item1'),('pro0075','item1'),('pro0076','item1'),('pro007asdas','item1'),('pro007asdassadas','item1'),('pro123','item1'),('Alo123','item2'),('hovadu','item2'),('pro123','item2'),('Alo123','item3'),('hovadu','item3'),('Alo123','item4'),('hovadu','item4'),('Alo123','item5'),('hovadu','item5'),('Alo123','item6'),('hovadu','item6'),('Alo123','item7'),('hovadu','item7'),('Alo123','item8'),('hovadu','item8'),('Alo123','item9'),('hovadu','item9');
/*!40000 ALTER TABLE `playerinventory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-23 17:23:38
