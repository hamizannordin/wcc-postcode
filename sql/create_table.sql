CREATE TABLE IF NOT EXISTS `postcode_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postcode` varchar(8) NOT NULL,
  `latitude` decimal(10,7) NULL,
  `longitude` decimal(10,7) NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;