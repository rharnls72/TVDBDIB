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
DROP TABLE IF EXISTS `follow_request`;

DROP VIEW IF EXISTS `episode_reply_view`;
DROP VIEW IF EXISTS `program_reply_view`;
DROP VIEW IF EXISTS `feed_reply_view`;

DROP VIEW IF EXISTS `feed_view`;

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
    `is_certification` BOOLEAN DEFAULT FALSE, 
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
    PRIMARY KEY (`follower`, `following`),
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
    PRIMARY KEY (`uno`, `pno`),
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
    `season` INT,
    `episode` INT,
    `share_num` INT DEFAULT 0
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
    `fno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`fno`)
        REFERENCES `feed` (`fno`)
        ON DELETE CASCADE
);

CREATE TABLE `program_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `pno` INT NOT NULL,
    PRIMARY KEY (`lno`)
);

CREATE TABLE `episode_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `eno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`eno`)
        REFERENCES `episode` (`eno`)
        ON DELETE CASCADE
);

CREATE TABLE `feed_reply_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `frno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`frno`)
        REFERENCES `feed_reply` (`frno`)
        ON DELETE CASCADE
);

CREATE TABLE `program_reply_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
    `prno` INT NOT NULL,
    PRIMARY KEY (`lno`),
    FOREIGN KEY (`prno`)
        REFERENCES `program_reply` (`prno`)
        ON DELETE CASCADE
);

CREATE TABLE `episode_reply_like` (
    `lno` INT AUTO_INCREMENT,
    `uno` INT NOT NULL,
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
    `ctype` INT,
    `cno` INT,
    `atype` INT NOT NULL,
    `read` BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (`ano`),
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);

CREATE TABLE `follow_request` (
    `fno` INT AUTO_INCREMENT,
    `uno` INT,
    `follower` INT,
    PRIMARY KEY (`fno`),
    FOREIGN KEY (`uno`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE,
    FOREIGN KEY (`follower`)
        REFERENCES `user` (`uno`)
        ON DELETE CASCADE
);

CREATE VIEW `episode_reply_view` AS
    SELECT 
        er.eno AS `eno`,
        er.erno AS `erno`,
        er.parent_reply AS `parent_reply`,
        er.content AS `content`,
        er.write_date AS `write_date`,
        writer.uno AS `writer_uno`,
        writer.nick_name AS `writer_nick_name`,
        writer.profile_pic AS `writer_profile_pic`,
        IFNULL(l.like_num, 0) AS `like_num`,
        IFNULL(p.reply_num, 0) AS `reply_num`
    FROM
        `episode_reply` er
            JOIN
        `user` writer ON er.uno = writer.uno
            LEFT OUTER JOIN
        (SELECT 
            `erno`, COUNT(*) AS `like_num`
        FROM
            `episode_reply_like`
        GROUP BY `erno`) l ON er.erno = l.erno
            LEFT OUTER JOIN
        (SELECT 
            `parent_reply` AS `erno`, COUNT(*) AS `reply_num`
        FROM
            `episode_reply`
        GROUP BY `parent_reply`) p ON er.erno = p.erno;

CREATE VIEW `program_reply_view` AS
    SELECT 
        pr.pno AS `pno`,
        pr.prno AS `prno`,
        pr.parent_reply AS `parent_reply`,
        pr.content AS `content`,
        pr.write_date AS `write_date`,
        writer.uno AS `writer_uno`,
        writer.nick_name AS `writer_nick_name`,
        writer.profile_pic AS `writer_profile_pic`,
        IFNULL(l.like_num, 0) AS `like_num`,
        IFNULL(p.reply_num, 0) AS `reply_num`
    FROM
        `program_reply` pr
            JOIN
        `user` writer ON pr.uno = writer.uno
            LEFT OUTER JOIN
        (SELECT 
            `prno`, COUNT(*) AS `like_num`
        FROM
            `program_reply_like`
        GROUP BY `prno`) l ON pr.prno = l.prno
            LEFT OUTER JOIN
        (SELECT 
            `parent_reply` AS `prno`, COUNT(*) AS `reply_num`
        FROM
            `program_reply`
        GROUP BY `parent_reply`) p ON pr.prno = p.prno;

CREATE VIEW `feed_reply_view` AS
    SELECT 
        fr.fno AS `fno`,
        fr.frno AS `frno`,
        fr.parent_reply AS `parent_reply`,
        fr.content AS `content`,
        fr.write_date AS `write_date`,
        writer.uno AS `writer_uno`,
        writer.nick_name AS `writer_nick_name`,
        writer.profile_pic AS `writer_profile_pic`,
        IFNULL(l.like_num, 0) AS `like_num`,
        IFNULL(p.reply_num, 0) AS `reply_num`
    FROM
        `feed_reply` fr
            JOIN
        `user` writer ON fr.uno = writer.uno
            LEFT OUTER JOIN
        (SELECT 
            `frno`, COUNT(*) AS `like_num`
        FROM
            `feed_reply_like`
        GROUP BY `frno`) l ON fr.frno = l.frno
            LEFT OUTER JOIN
        (SELECT 
            `parent_reply` AS `frno`, COUNT(*) AS `reply_num`
        FROM
            `feed_reply`
        GROUP BY `parent_reply`) p ON fr.frno = p.frno;

CREATE VIEW `feed_view` AS
    SELECT 
        f.fno AS `fno`,
        f.uno AS `uno`,
        f.pno AS `pno`,
        f.eno AS `eno`,
        f.create_date AS `create_date`,
        f.content AS `content`,
        f.ctype AS `ctype`,
        f.thumbnail AS `thumbnail`,
        f.tag AS `tag`,
        u.nick_name AS `nick_name`,
        u.profile_pic AS `profile_pic`,
        IFNULL(r.reply_num, 0) AS `reply_num`,
        IFNULL(l.like_num, 0) AS `like_num`
    FROM
        `feed` f
            JOIN
        `user` u ON f.uno = u.uno
            LEFT OUTER JOIN
        (SELECT 
            `fno`, COUNT(*) AS `reply_num`
        FROM
            `feed_reply`
        GROUP BY `fno`) r ON f.fno = r.fno
            LEFT OUTER JOIN
        (SELECT 
            `fno`, COUNT(*) AS `like_num`
        FROM
            `feed_like`
        GROUP BY `fno`) l ON f.fno = l.fno;
