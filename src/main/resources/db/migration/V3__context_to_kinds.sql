-- Add context to kinds for have only one table kinds
ALTER TABLE `job_kinds` ADD `context` VARCHAR(50) NOT NULL AFTER `name`;
RENAME TABLE `job_kinds` TO `kinds`;
ALTER TABLE `jobs` CHANGE `job_kind_id` `kind_id` BIGINT(20) NOT NULL;
ALTER TABLE `job_orders` ADD `kind_id` BIGINT(20) NOT NULL AFTER `job_id`;
ALTER TABLE `planes` ADD `description` VARCHAR(250) NULL DEFAULT NULL AFTER `source`;
ALTER TABLE `planes` ADD `kind_id` BIGINT(20) NOT NULL AFTER `job_id`;
