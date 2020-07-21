DROP TABLE IF EXISTS `feed_reply_like`;
DROP TABLE IF EXISTS `program_reply_like`;
DROP TABLE IF EXISTS `episode_reply_like`;
DROP TABLE IF EXISTS `feed_like`;
DROP TABLE IF EXISTS `program_like`;
DROP TABLE IF EXISTS `episode_like`;

DROP TABLE IF EXISTS `feed_reply`;
DROP TABLE IF EXISTS `program_reply`;
DROP TABLE IF EXISTS `episode_reply`;

DROP TABLE IF EXISTS `user_follow`;
DROP TABLE IF EXISTS `program_follow`;

DROP TABLE IF EXISTS `message`;
DROP TABLE IF EXISTS `alert`;

DROP TABLE IF EXISTS `episode_dibs`;
DROP TABLE IF EXISTS `feed_dibs`;

DROP TABLE IF EXISTS `feed`;
DROP TABLE IF EXISTS `episode`;
DROP TABLE IF EXISTS `program`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
    `uno` INT AUTO_INCREMENT,
    `email` VARCHAR(128) NOT NULL,
    `password` VARCHAR(128) NOT NULL,
    `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP (),
    `nick_name` VARCHAR(20) NOT NULL,
    `bio` VARCHAR(200),
    `profile_pic` VARCHAR(100),
    `is_private` BOOLEAN,
    PRIMARY KEY (`uno`),
    UNIQUE KEY (`email`)
);

CREATE TABLE `program` (
    `pno` INT AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `broadcaster` VARCHAR(20) NOT NULL,
    `genre` VARCHAR(20) NOT NULL,
    `season` INT,
    `alias` VARCHAR(20),
    `start_date` DATETIME,
    `end_date` DATETIME,
    `director` VARCHAR(30),
    `cast` VARCHAR(10000),
    `description` VARCHAR(1000),
    `broadcast_time` VARCHAR(100),
    `thumbnail` VARCHAR(200),
    PRIMARY KEY (`pno`)
);


CREATE TABLE `user_follow` (
    `follower` INT,
    `following` INT,
<<<<<<< HEAD
    PRIMARY KEY (`follower`, `following`),
=======
>>>>>>> old_feed
    CONSTRAINT FK_follower FOREIGN KEY (`follower`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE,
    CONSTRAINT FK_following FOREIGN KEY (`following`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);

CREATE TABLE `program_follow` (
    `uno` INT,
    `pno` INT,
<<<<<<< HEAD
    PRIMARY KEY (`uno`, `pno`),
=======
>>>>>>> old_feed
    CONSTRAINT FK_uno FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE,
    CONSTRAINT FK_pno1 FOREIGN KEY (`pno`)
        REFERENCES `program` (`pno`)
        ON DELETE CASCADE
);

CREATE TABLE `episode` (
    `eno` INT PRIMARY KEY AUTO_INCREMENT,
    `pno` INT,
    `episode` INT,
    `summary` VARCHAR(1000),
    `broadcast_date` DATETIME,
    `guest` VARCHAR(10000),
    `thumbnail` VARCHAR(200),
    `replay_link` VARCHAR(2000),
    CONSTRAINT FK_pno2 FOREIGN KEY (`pno`)
        REFERENCES `program` (`pno`)
        ON DELETE CASCADE
);

CREATE TABLE `feed` (
    `fno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `pno` INT,
    `eno` INT,
    `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `content` VARCHAR(10000),
    `ctype` INT NOT NULL,
    `thumbnail` VARCHAR(200),
    `tag` VARCHAR(500),
    PRIMARY KEY (`fno`),
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`pno`)
        REFERENCES `program` (`pno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`eno`)
        REFERENCES `episode` (`eno`)
        ON DELETE CASCADE
);

CREATE TABLE `episode_dibs` (
    `dno` INT PRIMARY KEY AUTO_INCREMENT,
    `uno` INT,
    `eno` INT,
    CONSTRAINT FK_uno1 FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE,
    CONSTRAINT FK_eno FOREIGN KEY (`eno`)
        REFERENCES `episode` (`eno`)
        ON DELETE CASCADE
);

CREATE TABLE `feed_dibs` (
    `dno` INT PRIMARY KEY AUTO_INCREMENT,
    `uno` INT,
    `fno` INT,
    CONSTRAINT FK_uno2 FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE,
    CONSTRAINT FK_fno FOREIGN KEY (`fno`)
        REFERENCES `feed` (`fno`)
        ON DELETE CASCADE
);



CREATE TABLE `episode_reply` (
    `erno` INT AUTO_INCREMENT,
    `eno` INT NOT NULL,
    `parent_reply` INT,
    `uno` INT NOT NULL,
    `content` VARCHAR(200) NOT NULL,
    `write_date` DATETIME DEFAULT CURRENT_TIMESTAMP (),
    PRIMARY KEY (`erno`),
    FOREIGN KEY (`eno`)
        REFERENCES `episode` (`eno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`parent_reply`)
        REFERENCES `episode_reply` (`erno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);

CREATE TABLE `feed_reply` (
    `frno` INT AUTO_INCREMENT,
    `fno` INT NOT NULL,
    `parent_reply` INT,
    `uno` INT NOT NULL,
    `content` VARCHAR(200) NOT NULL,
    `write_date` DATETIME DEFAULT CURRENT_TIMESTAMP (),
    PRIMARY KEY (`frno`),
    FOREIGN KEY (`fno`)
        REFERENCES `feed` (`fno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`parent_reply`)
        REFERENCES `feed_reply` (`frno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);

CREATE TABLE `program_reply` (
    `prno` INT AUTO_INCREMENT,
    `pno` INT NOT NULL,
    `parent_reply` INT,
    `uno` INT NOT NULL,
    `content` VARCHAR(200) NOT NULL,
    `write_date` DATETIME DEFAULT CURRENT_TIMESTAMP (),
    PRIMARY KEY (`prno`),
    FOREIGN KEY (`pno`)
        REFERENCES `program` (`pno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`parent_reply`)
        REFERENCES `program_reply` (`prno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);


CREATE TABLE `feed_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `type` INT NOT NULL,
    `fno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`fno`)
        REFERENCES `feed` (`fno`)
        ON DELETE CASCADE
);

CREATE TABLE `program_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `type` INT NOT NULL,
    `pno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`pno`)
        REFERENCES `program` (`pno`)
        ON DELETE CASCADE
);

CREATE TABLE `episode_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `type` INT NOT NULL,
    `eno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`eno`)
        REFERENCES `episode` (`eno`)
        ON DELETE CASCADE
);

CREATE TABLE `feed_reply_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `type` INT NOT NULL,
    `frno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`frno`)
        REFERENCES `feed_reply` (`frno`)
        ON DELETE CASCADE
);

CREATE TABLE `program_reply_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `type` INT NOT NULL,
    `prno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`prno`)
        REFERENCES `program_reply` (`prno`)
        ON DELETE CASCADE
);

CREATE TABLE `episode_reply_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `type` INT NOT NULL,
    `erno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`erno`)
        REFERENCES `episode_reply` (`erno`)
        ON DELETE CASCADE
);

CREATE TABLE `message` (
    `mno` INT AUTO_INCREMENT,
    `send_user` INT NOT NULL,
    `get_user` INT NOT NULL,
    `content` VARCHAR(300) NOT NULL,
    `create_date` DATETIME DEFAULT CURRENT_TIMESTAMP (),
    `read` BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (`mno`),
    FOREIGN KEY (`send_user`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);

CREATE TABLE `alert` (
    `ano` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `ctype` INT NOT NULL,
    `cno` INT NOT NULL,
    `atype` INT NOT NULL,
    PRIMARY KEY (`ano`),
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);
