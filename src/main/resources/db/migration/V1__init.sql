-- Initial Version without users management, only jos and order jobs

CREATE TABLE job_kinds (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE jobs (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description TEXT DEFAULT NULL,
  item varchar(100) DEFAULT NULL,
  job_kind_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_kind_id) REFERENCES job_kinds(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE photos (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  regular varchar(250) NOT NULL,
  hd varchar(250) NOT NULL,
  thumbnail varchar(250) NOT NULL,
  job_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES jobs(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE planes (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  source varchar(250) NOT NULL,
  job_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES jobs(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE job_compositions (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  job_id bigint(20) NOT NULL,
  sub_job_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES jobs(id),
  FOREIGN KEY (sub_job_id) REFERENCES jobs(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE job_orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  purchase_order_number varchar(200) NOT NULL,
  in_date date DEFAULT NULL,
  compromised_date date DEFAULT NULL,
  delivery_date date DEFAULT NULL,
  jobs_amount int DEFAULT 0,
  status varchar(100) DEFAULT NULL,
  description TEXT DEFAULT NULL,
  bill_number varchar(200) DEFAULT NULL,
  remit_number varchar(200) DEFAULT NULL,
  observations TEXT DEFAULT NULL,
  job_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_id) REFERENCES jobs(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- This version not take in account:
--    * progress: It's possible to take from the specification process
--    * material_status: It's possible to take from shopping tracking
--    * real_hours: It's possible to take from specification process
--    * justification: it's not present in no one order
--    * budgeted_hours: it's not present in the system, only for add


CREATE TABLE order_status (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(200) NOT NULL,
  description TEXT DEFAULT NULL,
  job_order_id bigint(20) NOT NULL,
  created datetime NOT NULL,
  modified datetime NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (job_order_id) REFERENCES job_orders(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;