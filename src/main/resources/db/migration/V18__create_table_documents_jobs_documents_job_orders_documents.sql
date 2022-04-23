CREATE TABLE documents (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  source varchar(250) NOT NULL,
  description varchar(250) DEFAULT NULL,
  kind_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  deleted datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (kind_id) REFERENCES kinds(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE jobs_documents (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  job_id bigint(20) NOT NULL,
  document_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES jobs(id),
  FOREIGN KEY (document_id) REFERENCES documents(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE job_orders_documents (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  job_order_id bigint(20) NOT NULL,
  document_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_order_id) REFERENCES job_orders(id),
  FOREIGN KEY (document_id) REFERENCES documents(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;