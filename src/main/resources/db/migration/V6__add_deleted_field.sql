ALTER TABLE `jobs`          ADD `deleted` datetime default NULL;
ALTER TABLE `job_orders`    ADD `deleted` datetime default NULL;
ALTER TABLE `kinds`         ADD `deleted` datetime default NULL;
ALTER TABLE `pictures`      ADD `deleted` datetime default NULL;
ALTER TABLE `planes`        ADD `deleted` datetime default NULL;