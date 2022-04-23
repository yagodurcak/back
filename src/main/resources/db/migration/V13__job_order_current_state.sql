-- Add context to kinds for have only one table kinds
ALTER TABLE `job_orders` ADD `current_state` VARCHAR(50) NOT NULL AFTER `observations`;