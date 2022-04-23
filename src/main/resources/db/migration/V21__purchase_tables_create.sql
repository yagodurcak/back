CREATE TABLE sectors (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE purchases (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  buyer varchar(100) DEFAULT NULL,
  description varchar(250) DEFAULT NULL,
  bill_number varchar(50) DEFAULT NULL,
  with_purchase_order tinyint(1) DEFAULT 0,
  user_data TEXT NOT NULL,
  sector_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (sector_id) REFERENCES sectors(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE purchase_orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  emission_date date NOT NULL,
  delivery_address varchar(250) DEFAULT NULL,
  description varchar(250) DEFAULT NULL,
  estimated_delivery_date date DEFAULT NULL,
  provider_id bigint(20) NOT NULL,
  purchase_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchases(id),
  FOREIGN KEY (provider_id) REFERENCES providers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE purchase_deliveries (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  remit_number varchar(100) NOT NULL,
  description varchar(100) DEFAULT NULL,
  source varchar(100) DEFAULT NULL,
  purchase_id bigint(20) NOT NULL,
  kind_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchases(id),
  FOREIGN KEY (kind_id) REFERENCES kinds(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE purchase_order_items (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(250) NOT NULL,
  amount INT NOT NULL,
  price FLOAT NOT NULL,
  purchase_order_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE purchases_documents (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  purchase_id bigint(20) NOT NULL,
  document_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchases(id),
  FOREIGN KEY (document_id) REFERENCES documents(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE budgets (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  message TEXT DEFAULT NULL,
  selected tinyint(1) DEFAULT 0,
  sent tinyint(1) DEFAULT 0,
  purchase_id bigint(20) NOT NULL,
  provider_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchases(id),
  FOREIGN KEY (provider_id) REFERENCES providers(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE requisitions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(100) NOT NULL,
  amount INT NOT NULL,
  reason varchar(250) DEFAULT NULL,
  requesting_date date NOT NULL,
  priority INT DEFAULT 0,
  user_data TEXT NOT NULL,
  purchase_id bigint(20) DEFAULT NULL,
  requesting_sector_id bigint(20) NOT NULL,
  destination_sector_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  deleted datetime DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchases(id),
  FOREIGN KEY (requesting_sector_id) REFERENCES sectors(id),
  FOREIGN KEY (destination_sector_id) REFERENCES sectors(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
