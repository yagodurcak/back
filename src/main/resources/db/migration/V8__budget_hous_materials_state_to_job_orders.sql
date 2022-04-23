-- Add context to kinds for have only one table kinds
ALTER TABLE `job_orders` ADD `budgeted_hours` INTEGER(11) NOT NULL DEFAULT 0 AFTER `description`;
ALTER TABLE `job_orders` ADD `percentage_advance` INTEGER(11) NOT NULL DEFAULT 0 AFTER `budgeted_hours`;
