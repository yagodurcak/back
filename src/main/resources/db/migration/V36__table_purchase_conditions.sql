CREATE TABLE purchase_conditions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE purchase_orders DROP COLUMN buy_condition;
ALTER TABLE purchase_orders ADD purchase_condition_id BIGINT NULL ;
ALTER TABLE purchase_orders ADD CONSTRAINT purchase_condition_purchase_orders_FK FOREIGN KEY (purchase_condition_id) REFERENCES purchase_conditions(id);