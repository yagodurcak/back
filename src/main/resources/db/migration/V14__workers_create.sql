CREATE TABLE workers (
    `id` BIGINT NOT NULL AUTO_INCREMENT ,
    `name` VARCHAR(50) NOT NULL ,
    `created` DATETIME NOT NULL ,
    `deleted` DATETIME ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
