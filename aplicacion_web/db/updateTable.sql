alter table product add column datewithoutstock DATETIME AFTER groupproductid;
alter table product add column repositionstock INTEGER AFTER groupproductid;
ALTER TABLE user ADD COLUMN email VARCHAR(50) AFTER groupuserid;
ALTER TABLE user ADD COLUMN receiveNotifications BOOLEAN DEFAULT 0 AFTER email;