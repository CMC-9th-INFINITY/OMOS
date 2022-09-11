DROP TABLE IF EXISTS `block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `block` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `report_type` varchar(255) NOT NULL,
                         `from_user_id` bigint NOT NULL,
                         `to_user_id` bigint DEFAULT NULL,
                         `post_id` bigint DEFAULT NULL,
                         `report_id` bigint DEFAULT NULL,
                         `created_date` datetime NOT NULL,
                         `modified_date` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `blockpost_idx` (`post_id`),
                         KEY `blocktouserid_idx` (`to_user_id`),
                         KEY `blockfromuserid_idx` (`from_user_id`),
                         KEY `blockreportid_idx` (`report_id`),
                         CONSTRAINT `blockfromuserid` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `blockpost` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `blockreportid` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE,
                         CONSTRAINT `blocktouserid` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `block`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `posts` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `created_date` datetime NOT NULL,
                         `modified_date` datetime DEFAULT NULL,
                         `category_id` varchar(100) NOT NULL,
                         `cnt` int NOT NULL,
                         `contents` varchar(2200) DEFAULT NULL,
                         `is_public` tinyint(1) NOT NULL,
                         `title` varchar(255) NOT NULL,
                         `music_id` varchar(100) NOT NULL,
                         `user_id` bigint NOT NULL,
                         `image_url` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `rgr_idx` (`music_id`),
                         KEY `FKam8ar6luvp8afhfu20gfsydo9` (`user_id`),
                         CONSTRAINT `FKam8ar6luvp8afhfu20gfsydo1` FOREIGN KEY (`music_id`) REFERENCES `music` (`id`),
                         CONSTRAINT `FKam8ar6luvp8afhfu20gfsydo9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=431 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `music`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `music` (
                         `id` varchar(100) NOT NULL,
                         `created_date` datetime NOT NULL,
                         `modified_date` datetime DEFAULT NULL,
                         `artist_id` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `music`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `report` (
                          `id` bigint NOT NULL AUTO_INCREMENT,
                          `from_user_id` bigint NOT NULL,
                          `to_user_id` bigint DEFAULT NULL,
                          `post_id` bigint DEFAULT NULL,
                          `report_reason` varchar(255) DEFAULT NULL,
                          `created_date` datetime NOT NULL,
                          `modified_date` datetime DEFAULT NULL,
                          `report_type` varchar(255) NOT NULL,
                          PRIMARY KEY (`id`),
                          KEY `reporttouserId_idx` (`to_user_id`),
                          KEY `reportfromuserid_idx` (`from_user_id`),
                          KEY `reportpostid_idx` (`post_id`),
                          CONSTRAINT `reportfromuserid` FOREIGN KEY (`from_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                          CONSTRAINT `reportpostid` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE,
                          CONSTRAINT `reporttouserId` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `refresh_token` (
                                 `user_email` varchar(255) NOT NULL,
                                 `created_date` datetime NOT NULL,
                                 `modified_date` datetime DEFAULT NULL,
                                 `token` varchar(255) DEFAULT NULL,
                                 PRIMARY KEY (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--


DROP TABLE IF EXISTS `scrap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `scrap` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `post_id` bigint NOT NULL,
                         `user_id` bigint NOT NULL,
                         `created_date` datetime NOT NULL,
                         `modified_date` datetime DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKbm7dj0r5wxmesdfkyyoqyspv5` (`post_id`),
                         KEY `FKgt91kwgqa4f4oaoi9ljgy75mw` (`user_id`),
                         CONSTRAINT `FKbm7dj0r5wxmesdfkyyoqyspv5` FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
                         CONSTRAINT `FKgt91kwgqa4f4oaoi9ljgy75mw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scrap`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `authority` varchar(255) DEFAULT NULL,
                        `email` varchar(255) NOT NULL,
                        `nickname` varchar(255) NOT NULL,
                        `password` varchar(255) DEFAULT NULL,
                        `profile_url` varchar(255) DEFAULT NULL,
                        `created_date` datetime NOT NULL,
                        `modified_date` datetime DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=199 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--