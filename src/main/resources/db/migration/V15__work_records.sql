CREATE TABLE work_records (
    `id` BIGINT NOT NULL AUTO_INCREMENT ,
    `worker_id` BIGINT NOT NULL ,
    `job_order_id` BIGINT NOT NULL ,
    `hours` INT(11) NOT NULL DEFAULT 0,
    `created` DATETIME NOT NULL ,
    `deleted` DATETIME ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
