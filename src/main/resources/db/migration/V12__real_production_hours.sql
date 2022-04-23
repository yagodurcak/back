-- Add context to kinds for have only one table kinds
ALTER TABLE `job_orders` ADD `real_hours_production` INTEGER(11) DEFAULT 0 AFTER `remit_number`;
