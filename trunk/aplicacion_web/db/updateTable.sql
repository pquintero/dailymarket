alter table product add column datewithoutstock DATETIME AFTER groupproductid;
alter table product add column repositionstock INTEGER AFTER groupproductid;
ALTER TABLE user ADD COLUMN email VARCHAR(50) AFTER groupuserid;
ALTER TABLE user ADD COLUMN receiveNotifications BOOLEAN DEFAULT 0 AFTER email;
ALTER TABLE product ADD COLUMN state VARCHAR(50);
CREATE TABLE configuration (id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,timer INTEGER UNSIGNED,PRIMARY KEY (id));
ALTER TABLE configuration ADD COLUMN emaildeposito VARCHAR(60) AFTER timer;