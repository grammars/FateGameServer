-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2015 年 03 月 22 日 08:47
-- 服务器版本: 5.5.24-log
-- PHP 版本: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `fategame`
--

-- --------------------------------------------------------

--
-- 表的结构 `account`
--

CREATE TABLE IF NOT EXISTS `account` (
  `uid` varchar(48) NOT NULL,
  `playeruid0` bigint(20) DEFAULT NULL,
  `playeruid1` bigint(20) DEFAULT NULL,
  `playeruid2` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `account`
--

INSERT INTO `account` (`uid`, `playeruid0`, `playeruid1`, `playeruid2`) VALUES
('10086', 5183264947007324160, 0, 0);

-- --------------------------------------------------------

--
-- 表的结构 `dual`
--

CREATE TABLE IF NOT EXISTS `dual` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提供DBCP的validationQuery';

--
-- 转存表中的数据 `dual`
--

INSERT INTO `dual` (`id`) VALUES
(1);

-- --------------------------------------------------------

--
-- 表的结构 `names`
--

CREATE TABLE IF NOT EXISTS `names` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `names`
--

INSERT INTO `names` (`uid`, `name`) VALUES
(1, '剣侠客');

-- --------------------------------------------------------

--
-- 表的结构 `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `uid` bigint(20) NOT NULL,
  `account` varchar(48) DEFAULT NULL,
  `alive` tinyint(1) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `voc` tinyint(4) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `pkMode` int(11) NOT NULL,
  `exp` bigint(20) DEFAULT NULL,
  `mapId` int(11) DEFAULT NULL,
  `mapX` int(11) DEFAULT NULL,
  `mapY` int(11) DEFAULT NULL,
  `direction` tinyint(4) DEFAULT NULL,
  `look` varchar(16) DEFAULT NULL,
  `weaponLook` varchar(16) DEFAULT NULL,
  `attriData` text,
  `buffData` text NOT NULL,
  `skillData` text,
  `practiceData` varchar(128) DEFAULT NULL,
  `taskData` text,
  `cdData` text,
  `lastLoginTime` datetime DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `bagData` text NOT NULL,
  `equipData` text NOT NULL,
  `warehouseData` text NOT NULL,
  `log` text,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `player`
--

INSERT INTO `player` (`uid`, `account`, `alive`, `name`, `sex`, `voc`, `level`, `pkMode`, `exp`, `mapId`, `mapX`, `mapY`, `direction`, `look`, `weaponLook`, `attriData`, `buffData`, `skillData`, `practiceData`, `taskData`, `cdData`, `lastLoginTime`, `createTime`, `bagData`, `equipData`, `warehouseData`, `log`) VALUES
(5183264947007324160, '10086', 1, '剣侠客', 1, 1, 0, 0, 0, 1001, 47, 51, 5, '1000', '', '{"equip":[0,0,0,0,0,0,0,0,0,0],"skill":[0,0,0,0,0,0,0,0,0,0],"base":[2550,25000,150,56,0.6000000238418579,20,5,15,10,5],"current":[2550,19584,150,56,0.6000000238418579,20,5,15,10,5],"buff":[0,0,0,0,0,0,0,0,0,0]}', '[]', '[{"id":101,"level":1},{"id":102,"level":1},{"id":103,"level":1},{"id":104,"level":1}]', '{"exp":0,"lev":0}', '{"items":[{"taskId":1,"passZone":false,"state":1,"gotGoodsNum":0,"killMonsterNum":0,"talkedNpc":false}],"finishIds":[]}', '{"skill":[],"goods":[]}', '2015-03-22 16:43:31', '2015-03-22 16:43:31', '[]', '[]', '[]', '');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
