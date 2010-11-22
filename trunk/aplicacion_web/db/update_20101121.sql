CREATE TABLE attach (
  attachid INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  objecttype VARCHAR(85),
  name VARCHAR(80),
  description VARCHAR(100),
  realname VARCHAR(100),
  path VARCHAR(100),
  contenttype VARCHAR(100),
  size BIGINT(20) UNSIGNED,
  imageid BIGINT(20) UNSIGNED,
  PRIMARY KEY (attachid)
)

ALTER TABLE product ADD COLUMN imageid BIGINT(20) UNSIGNED AFTER active;
ALTER TABLE user ADD COLUMN imageid BIGINT(20) UNSIGNED AFTER active;