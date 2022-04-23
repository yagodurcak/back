CREATE TABLE purchase_states (
id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  purchase_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchases(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;