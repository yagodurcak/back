ALTER TABLE purchase_orders DROP FOREIGN KEY purchase_orders_ibfk_1 ;
ALTER TABLE purchase_orders DROP COLUMN purchase_id;
ALTER TABLE budgets ADD purchase_order_id BIGINT NULL ;
ALTER TABLE budgets ADD CONSTRAINT budgets_purchase_orders_FK FOREIGN KEY (purchase_order_id) REFERENCES purchase_orders(id);