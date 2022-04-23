ALTER TABLE purchase_orders MODIFY COLUMN purchase_id bigint(20) NULL ;
ALTER TABLE purchase_order_items MODIFY COLUMN purchase_order_id bigint(20) NULL ;