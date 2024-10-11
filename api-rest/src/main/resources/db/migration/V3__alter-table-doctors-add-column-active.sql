alter table doctors add active tinyint;
update doctors set active = 1 where active is null;
alter table doctors MODIFY active tinyint NOT NULL;