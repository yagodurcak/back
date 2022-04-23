CREATE TABLE providers (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  address varchar(100) DEFAULT NULL,
  description varchar(100) DEFAULT NULL,
  contact varchar(100) DEFAULT NULL,
  phone varchar(100) DEFAULT NULL,
  mail varchar(100) DEFAULT NULL,
  cuit varchar(100) DEFAULT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;