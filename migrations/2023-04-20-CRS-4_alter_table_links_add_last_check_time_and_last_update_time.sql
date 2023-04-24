--liquibase formatted sql

--changeset i.smeyukha:CRS-4_alter_table_links_add_last_check_time_and_last_update_time
ALTER TABLE IF EXISTS links
    ADD COLUMN IF NOT EXISTS last_check_time TIMESTAMP WITH TIME ZONE NOT NULL,
    ADD COLUMN IF NOT EXISTS last_update_time TIMESTAMP WITH TIME ZONE;
