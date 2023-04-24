--liquibase formatted sql

--changeset i.smeyukha:CRS-1_create_links_table
CREATE TABLE IF NOT EXISTS links
(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    url TEXT NOT NULL,
    last_check_time TIMESTAMP WITH TIME ZONE NOT NULL,
    last_update_time TIMESTAMP WITH TIME ZONE
)